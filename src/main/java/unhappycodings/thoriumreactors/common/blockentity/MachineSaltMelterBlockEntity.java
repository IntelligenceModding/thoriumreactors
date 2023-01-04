package unhappycodings.thoriumreactors.common.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import unhappycodings.thoriumreactors.common.block.MachineElectrolyticSaltSeparatorBlock;
import unhappycodings.thoriumreactors.common.block.MachineSaltMelterBlock;
import unhappycodings.thoriumreactors.common.container.MachineSaltMelterContainer;
import unhappycodings.thoriumreactors.common.energy.IEnergyCapable;
import unhappycodings.thoriumreactors.common.energy.ModEnergyStorage;
import unhappycodings.thoriumreactors.common.registration.ModBlockEntities;
import unhappycodings.thoriumreactors.common.registration.ModFluids;
import unhappycodings.thoriumreactors.common.registration.ModItems;
import unhappycodings.thoriumreactors.common.registration.ModSounds;
import unhappycodings.thoriumreactors.common.util.EnergyUtil;

public class MachineSaltMelterBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer, MenuProvider, IEnergyCapable {
    public static final int MAX_POWER = 100000;
    public static final int MAX_TRANSFER = 250;
    public static final int MAX_RECIPE_TIME = 400;
    public static final int PRODUCTION = 135;
    public static final int MAX_MOLTEN_SALT_OUT = 10000;
    public static final int MAX_MOLTEN_SALT_TRANSFER = 100;

    private LazyOptional<ModEnergyStorage> lazyEnergyHandler = LazyOptional.empty();
    private LazyOptional<FluidTank> lazyFluidOutHandler = LazyOptional.empty();
    private final LazyOptional<? extends IItemHandler>[] itemHandler = SidedInvWrapper.create(this, Direction.values());
    public NonNullList<ItemStack> items;
    int recipeTime = 0;
    int maxRecipeTime = 0;
    int moltenSaltOut = 0;

    public MachineSaltMelterBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.SALT_MELTER_BLOCK.get(), pPos, pBlockState);
        items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        this.lazyEnergyHandler = LazyOptional.of(() -> ENERGY_STORAGE);
        this.lazyFluidOutHandler = LazyOptional.of(() -> FLUID_TANK_OUT);
    }

    private final ModEnergyStorage ENERGY_STORAGE = new ModEnergyStorage(MAX_POWER, MAX_TRANSFER) {
        @Override
        public void onEnergyChanged() {
            setChanged();
            this.energy = ENERGY_STORAGE.getEnergyStored();
        }
    };

    private final FluidTank FLUID_TANK_OUT = new FluidTank(MAX_MOLTEN_SALT_OUT) {
        @Override
        protected void onContentsChanged() {
            setChanged();
            moltenSaltOut = FLUID_TANK_OUT.getFluidAmount();
        }

        @Override
        public @NotNull FluidStack getFluid() {
            return new FluidStack(ModFluids.FLOWING_MOLTEN_SALT.get(), moltenSaltOut);
        }

        @Override
        public int getFluidAmount() {
            return moltenSaltOut;
        }

        @Override
        public int fill(FluidStack resource, FluidAction action) {
            int canFill = getMaxMoltenSaltOut() - getMoltenSaltOut();
            int filled = Math.min(canFill, MAX_MOLTEN_SALT_TRANSFER);

            if (action == FluidAction.EXECUTE)
                setMoltenSaltOut(getMoltenSaltOut() + Math.min(filled, resource.getAmount()));
            return Math.min(filled, resource.getAmount());
        }

        @Override
        public @NotNull FluidStack drain(FluidStack resource, FluidAction action) {
            int canDrain = getMoltenSaltOut();
            int filled = Math.min(canDrain, MAX_MOLTEN_SALT_OUT);
            if (action == FluidAction.EXECUTE) setMoltenSaltOut(getMoltenSaltOut() - filled);
            return new FluidStack(ModFluids.SOURCE_MOLTEN_SALT.get(), filled);
        }

        @Override
        public @NotNull FluidStack drain(int maxDrain, FluidAction action) {
            int canDrain = getMoltenSaltOut() - MAX_MOLTEN_SALT_TRANSFER > 0 ? MAX_MOLTEN_SALT_TRANSFER : getMoltenSaltOut();
            return new FluidStack(ModFluids.SOURCE_MOLTEN_SALT.get(), canDrain);
        }

        @Override
        public boolean isFluidValid(FluidStack stack) {
            return stack.getFluid() == ModFluids.SOURCE_MOLTEN_SALT.get();
        }

    };

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        Direction facing = this.getBlockState().getValue(MachineElectrolyticSaltSeparatorBlock.FACING);
        if (cap == ForgeCapabilities.ENERGY && supportsEnergy() && side != null && this.getBlockState().getValue(MachineSaltMelterBlock.FACING).getOpposite() == side) {
            return lazyEnergyHandler.cast();
        }
        if (cap == ForgeCapabilities.FLUID_HANDLER && side != null) {
            if (side == Direction.DOWN) return lazyFluidOutHandler.cast();
        }
        if (cap == ForgeCapabilities.ITEM_HANDLER && !isRemoved() && side != null) {
            if (side == facing.getCounterClockWise() || side == facing.getClockWise())
                return itemHandler[side.get3DDataValue()].cast();
            return LazyOptional.empty();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public boolean canInputEnergy() {
        return true;
    }

    @Override
    public boolean canInputEnergy(Direction direction) {
        return this.getBlockState().getValue(MachineSaltMelterBlock.FACING).getOpposite() == direction;
    }

    @Override
    public boolean canOutputEnergy() {
        return false;
    }

    @Override
    public boolean canOutputEnergy(Direction direction) {
        return false;
    }

    public void tick() {
        // Play Sounds
        if (getState() && getRecipeTime() % 20 == 0) {
            this.level.playSound(null, getBlockPos(), ModSounds.MACHINE_SALT_MELTER.get(), SoundSource.BLOCKS, 0.09f, 1f);
        }

        // Energy Input Slot
        items.get(2).getCapability(ForgeCapabilities.ENERGY).ifPresent(storage -> EnergyUtil.tryDischargeItem(storage, ENERGY_STORAGE, getMaxInput()));

        int neededEnergy = 100;

        if (hasRecipeNeeds(neededEnergy) || getMaxRecipeTime() > 0) {
            if (getMaxRecipeTime() == 0 && hasRecipeNeeds(0)) {
                setMaxRecipeTime(MAX_RECIPE_TIME);
                setRecipeTime(MAX_RECIPE_TIME);
                items.get(0).shrink(1);
                items.get(1).shrink(1);
                if (!getState()) setState(true);
            }
            if (getRecipeTime() > 0 && getMaxRecipeTime() > 0) {
                if (!getState()) setState(true);
                setEnergy(getEnergy() - neededEnergy);
                setRecipeTime(getRecipeTime() - 1);
                if (getRecipeTime() == 0) {
                    setMaxRecipeTime(0);
                    setMoltenSaltOut(getMoltenSaltOut() + 50);
                }
            } else {
                if (getState())
                    setState(false);
            }
        } else {
            if (getState())
                setState(false);
        }
    }

    public void tryFillTank(IFluidHandlerItem other, FluidTank fluidTank, int transferRate, boolean hasCraftingRemainder) {
        int toSend = fluidTank.fill(new FluidStack(Fluids.WATER, transferRate), IFluidHandler.FluidAction.SIMULATE);
        FluidStack sent = other.drain(toSend, IFluidHandler.FluidAction.EXECUTE);
        if (hasCraftingRemainder) {
            if (fluidTank.getFluidInTank(0).getAmount() + transferRate > fluidTank.getTankCapacity(0)) return;
            sent = new FluidStack(Fluids.WATER, transferRate);
        }
        if (sent.getAmount() > 0) fluidTank.fill(sent, IFluidHandler.FluidAction.EXECUTE);
        if (hasCraftingRemainder) items.set(0, new ItemStack(items.get(0).getItem().getCraftingRemainingItem()));
    }

    public boolean hasRecipeNeeds(int neededEnergy) {
        return items.get(0).is(ModItems.SODIUM.get()) && items.get(1).is(ModItems.POTASSIUM.get()) && getEnergy() - neededEnergy >= 0;
    }

    public void setState(boolean state) {
        level.setBlock(getBlockPos(), getBlockState().setValue(MachineElectrolyticSaltSeparatorBlock.POWERED, state), 3);
    }

    public boolean getState() {
        return getBlockState().getValue(MachineElectrolyticSaltSeparatorBlock.POWERED);
    }

    public int getMaxWaterTransfer() {
        return MAX_MOLTEN_SALT_TRANSFER;
    }

    public int getRecipeTime() {
        return recipeTime;
    }

    public void setRecipeTime(int recipeTime) {
        this.recipeTime = recipeTime;
    }

    public int getMaxRecipeTime() {
        return maxRecipeTime;
    }

    public void setMaxRecipeTime(int maxRecipeTime) {
        this.maxRecipeTime = maxRecipeTime;
    }

    public int getMoltenSaltOut() {
        return moltenSaltOut;
    }

    public void setMoltenSaltOut(int moltenSaltOut) {
        this.moltenSaltOut = moltenSaltOut;
    }

    public int getMaxMoltenSaltOut() {
        return MAX_MOLTEN_SALT_OUT;
    }

    public void setFluid(FluidStack stack) {
        FLUID_TANK_OUT.setFluid(stack);
    }

    public FluidStack getFluid() {
        return FLUID_TANK_OUT.getFluidInTank(0);
    }

    @Override
    public void setEnergy(int energy) {
        ENERGY_STORAGE.setEnergy(energy);
    }

    public int getEnergy() {
        return ENERGY_STORAGE.getEnergyStored();
    }

    public int getCapacity() {
        return ENERGY_STORAGE.getMaxEnergyStored();
    }

    public int getMaxInput() {
        return MAX_TRANSFER;
    }

    public boolean supportsEnergy() {
        return getEnergyCapacity() > 0;
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        if (getLevel().isClientSide && net.getDirection() == PacketFlow.CLIENTBOUND) handleUpdateTag(pkt.getTag());
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @NotNull
    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag nbt = new CompoundTag();
        nbt.putInt("Energy", getEnergy());
        nbt.putInt("RecipeTime", getRecipeTime());
        nbt.putInt("MaxRecipeTime", getMaxRecipeTime());
        nbt.putInt("MoltenSaltOut", getMoltenSaltOut());
        return nbt;
    }

    @Override
    public void handleUpdateTag(final CompoundTag tag) {
        setEnergy(tag.getInt("Energy"));
        setRecipeTime(tag.getInt("RecipeTime"));
        setMaxRecipeTime(tag.getInt("MaxRecipeTime"));
        setMoltenSaltOut(tag.getInt("MoltenSaltOut"));
    }

    @Override
    public void saveAdditional(@NotNull CompoundTag nbt) {
        nbt.putInt("Energy", getEnergy());
        nbt.putInt("RecipeTime", getRecipeTime());
        nbt.putInt("MaxRecipeTime", getMaxRecipeTime());
        nbt.putInt("MoltenSaltOut", getMoltenSaltOut());
        ContainerHelper.saveAllItems(nbt, this.items, true);
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        this.items.clear();
        ContainerHelper.loadAllItems(nbt, this.items);
        setEnergy(nbt.getInt("Energy"));
        setRecipeTime(nbt.getInt("RecipeTime"));
        setMaxRecipeTime(nbt.getInt("MaxRecipeTime"));
        setMoltenSaltOut(nbt.getInt("MoltenSaltOut"));
    }

    @NotNull
    @Override
    protected Component getDefaultName() {
        return Component.translatable("block.thoriumreactors.salt_melter_block");
    }

    @NotNull
    @Override
    protected AbstractContainerMenu createMenu(int pContainerId, @NotNull Inventory pInventory) {
        return new MachineSaltMelterContainer(pContainerId, pInventory, getBlockPos(), getLevel(), getContainerSize());
    }

    @Override
    public int getContainerSize() {
        return 3;
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemStack : items) {
            if (itemStack.isEmpty()) return true;
        }
        return false;
    }

    @NotNull
    @Override
    public ItemStack getItem(int index) {
        if (index < 0 || index >= items.size()) {
            return ItemStack.EMPTY;
        }
        return items.get(index);
    }

    @NotNull
    @Override
    public ItemStack removeItem(int index, int count) {
        return ContainerHelper.removeItem(items, index, count);
    }

    @NotNull
    @Override
    public ItemStack removeItemNoUpdate(int index) {
        return ContainerHelper.takeItem(items, index);
    }

    @Override
    public void setItem(int index, @NotNull ItemStack stack) {
        items.set(index, stack);
        if (stack.getCount() > getMaxStackSize()) {
            stack.setCount(getMaxStackSize());
        }
    }

    @Override
    public boolean stillValid(Player player) {
        if (this.level.getBlockEntity(this.worldPosition) != this) {
            return false;
        } else {
            return !(player.distanceToSqr((double) this.worldPosition.getX() + 0.5D, (double) this.worldPosition.getY() + 0.5D, (double) this.worldPosition.getZ() + 0.5D) > 64.0D);
        }
    }

    @Override
    public void clearContent() {
        items.clear();
    }

    @Override
    public long getEnergyStored() {
        return ENERGY_STORAGE.getEnergyStored();
    }

    @Override
    public long getEnergyCapacity() {
        return MAX_POWER;
    }

    @Override
    public long getMaxEnergyTransfer() {
        return PRODUCTION;
    }

    @Override
    public int getEnergyDrain() {
        return 0;
    }

    @Override
    public long removeEnergy(long energy, boolean simulate) {
        return ENERGY_STORAGE.extractEnergy((int) energy, simulate);
    }

    @Override
    public long addEnergy(long energy, boolean simulate) {
        return ENERGY_STORAGE.receiveEnergy((int) energy, simulate);
    }

    @Override
    public void setCapacity(int capacity) {

    }

    @Override
    public int[] getSlotsForFace(Direction pSide) {
        if (pSide == this.getBlockState().getValue(MachineElectrolyticSaltSeparatorBlock.FACING).getClockWise()) return new int[]{1};
        if (pSide == this.getBlockState().getValue(MachineElectrolyticSaltSeparatorBlock.FACING).getCounterClockWise()) return new int[]{0};
        return new int[]{};
    };

    @Override
    public boolean canPlaceItemThroughFace(int pIndex, ItemStack pItemStack, @Nullable Direction pDirection) {
        Direction facing = this.getBlockState().getValue(MachineElectrolyticSaltSeparatorBlock.FACING);
        return facing.getCounterClockWise() == pDirection || facing.getClockWise() == pDirection;
    }

    @Override
    public boolean canTakeItemThroughFace(int pIndex, ItemStack pStack, Direction pDirection) {
        return false;
    }
}
