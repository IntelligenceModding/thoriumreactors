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
import net.minecraft.util.RandomSource;
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
import unhappycodings.thoriumreactors.common.block.MachineFluidEvaporationBlock;
import unhappycodings.thoriumreactors.common.container.MachineFluidEvaporatorContainer;
import unhappycodings.thoriumreactors.common.energy.IEnergyCapable;
import unhappycodings.thoriumreactors.common.energy.ModEnergyStorage;
import unhappycodings.thoriumreactors.common.registration.ModBlockEntities;
import unhappycodings.thoriumreactors.common.registration.ModItems;
import unhappycodings.thoriumreactors.common.registration.ModSounds;
import unhappycodings.thoriumreactors.common.util.EnergyUtil;
import unhappycodings.thoriumreactors.common.util.ParticleUtil;

public class MachineFluidEvaporationBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer, MenuProvider, IEnergyCapable {
    public static final int MAX_POWER = 100000;
    public static final int MAX_TRANSFER = 250;
    public static final int MAX_RECIPE_TIME = 400;
    public static final int PRODUCTION = 135;
    public static final int MAX_WATER_IN = 6000;
    public static final int MAX_WATER_TRANSFER = 100;

    private LazyOptional<ModEnergyStorage> lazyEnergyHandler = LazyOptional.empty();
    private LazyOptional<FluidTank> lazyFluidInHandler = LazyOptional.empty();
    private final LazyOptional<? extends IItemHandler>[] itemHandler = SidedInvWrapper.create(this, Direction.values());
    public NonNullList<ItemStack> items;
    int recipeTime = 0;
    int maxRecipeTime = 0;
    int waterIn = 0;
    boolean inputDump;

    public MachineFluidEvaporationBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.FLUID_EVAPORATION_BLOCK.get(), pPos, pBlockState);
        items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        this.lazyEnergyHandler = LazyOptional.of(() -> ENERGY_STORAGE);
        this.lazyFluidInHandler = LazyOptional.of(() -> FLUID_TANK_IN);
    }

    private final ModEnergyStorage ENERGY_STORAGE = new ModEnergyStorage(MAX_POWER, MAX_TRANSFER) {
        @Override
        public void onEnergyChanged() {
            setChanged();
            this.energy = ENERGY_STORAGE.getEnergyStored();
        }
    };

    private final FluidTank FLUID_TANK_IN = new FluidTank(MAX_WATER_IN) {
        @Override
        protected void onContentsChanged() {
            setChanged();
            waterIn = FLUID_TANK_IN.getFluidAmount();
        }

        @Override
        public @NotNull FluidStack getFluid() {
            return new FluidStack(Fluids.WATER, waterIn);
        }

        @Override
        public int getFluidAmount() {
            return waterIn;
        }

        @Override
        public int fill(FluidStack resource, FluidAction action) {
            int canFill = getMaxWaterIn() - getWaterIn();
            int filled = Math.min(canFill, MAX_WATER_TRANSFER); // canFill > MAX_WATER_TRANSFER ? MAX_WATER_TRANSFER : canFill

            if (action == FluidAction.EXECUTE)
                if (items.get(0).hasCraftingRemainingItem())
                    setWaterIn(getWaterIn() + 1000);
                else
                    setWaterIn(getWaterIn() + Math.min(filled, resource.getAmount()));
            return Math.min(filled, resource.getAmount());
        }

        @Override
        public @NotNull FluidStack drain(int maxDrain, FluidAction action) {
            return FluidStack.EMPTY;
        }

        @Override
        public boolean isFluidValid(FluidStack stack) {
            return stack.getFluid() == Fluids.WATER;
        }

    };

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        Direction facing = this.getBlockState().getValue(MachineElectrolyticSaltSeparatorBlock.FACING);
        if (cap == ForgeCapabilities.ENERGY && supportsEnergy() && side != null && this.getBlockState().getValue(MachineFluidEvaporationBlock.FACING).getOpposite() == side) {
            return lazyEnergyHandler.cast();
        }
        if (cap == ForgeCapabilities.FLUID_HANDLER && side != null) {
            if (facing.getClockWise() == side) return lazyFluidInHandler.cast();
        }
        if (cap == ForgeCapabilities.ITEM_HANDLER && !isRemoved() && side != null) {
            if (side == facing.getCounterClockWise())
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
        return this.getBlockState().getValue(MachineFluidEvaporationBlock.FACING).getOpposite() == direction;
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
            this.level.playSound(null, getBlockPos(), ModSounds.MACHINE_FLUID_EVAPORATION.get(), SoundSource.BLOCKS, 0.09f,1f);
        }

        // Fluid Input Slot
        items.get(0).getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).ifPresent(storage -> {
            if (storage.getFluidInTank(0).getFluid() == Fluids.WATER && storage.getTankCapacity(0) > 0) {
                tryFillTank(storage, FLUID_TANK_IN, items.get(0).hasCraftingRemainingItem() ? storage.getTankCapacity(0) : getMaxWaterTransfer(), items.get(0).hasCraftingRemainingItem());
            }
        });

        // Energy Input Slot
        items.get(2).getCapability(ForgeCapabilities.ENERGY).ifPresent(storage -> EnergyUtil.tryDischargeItem(storage, ENERGY_STORAGE, getMaxInput()));

        int neededEnergy = 65;
        int neededWater = 1500;

        if (hasRecipeNeeds(neededWater / MAX_RECIPE_TIME, neededEnergy)) {
            if (getMaxRecipeTime() == 0) {
                setMaxRecipeTime(MAX_RECIPE_TIME);
                setRecipeTime(MAX_RECIPE_TIME);
                if (!getState()) setState(true);
            }
            ItemStack outputSlot = getItem(1);
            if (getRecipeTime() > 0 && getMaxRecipeTime() > 0 && outputSlot.getCount() + 1 <= outputSlot.getMaxStackSize() &&
                    (outputSlot.isEmpty() || outputSlot.is(ModItems.SODIUM.get()))) {
                if (!getState()) setState(true);
                setEnergy(getEnergy() - neededEnergy);
                setWaterIn(getWaterIn() - neededWater / MAX_RECIPE_TIME);
                setRecipeTime(getRecipeTime() - 1);
                if (getRecipeTime() == 0) {
                    setMaxRecipeTime(0);
                    setItem(1, new ItemStack(ModItems.SODIUM.get(), outputSlot.getCount() + 1));
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

    public boolean hasRecipeNeeds(int water, int energy) {
        return getWaterIn() >= water && getEnergy() >= energy;
    }

    public void setState(boolean state) {
        level.setBlock(getBlockPos(), getBlockState().setValue(MachineElectrolyticSaltSeparatorBlock.POWERED, state), 3);
    }

    public boolean getState() {
        return getBlockState().getValue(MachineElectrolyticSaltSeparatorBlock.POWERED);
    }

    public int getMaxWaterTransfer() {
        return MAX_WATER_TRANSFER;
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

    public int getWaterIn() {
        return waterIn;
    }

    public void setWaterIn(int waterIn) {
        this.waterIn = waterIn;
    }

    public int getMaxWaterIn() {
        return MAX_WATER_IN;
    }

    public void setFluid(FluidStack stack) {
        FLUID_TANK_IN.setFluid(stack);
    }

    public FluidStack getFluid() {
        return FLUID_TANK_IN.getFluidInTank(0);
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

    public boolean isInputDump() {
        return inputDump;
    }

    public void setInputDump(boolean inputDump) {
        this.inputDump = inputDump;
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
        nbt.putInt("WaterIn", getWaterIn());
        nbt.putBoolean("InputDump", isInputDump());
        return nbt;
    }

    @Override
    public void handleUpdateTag(final CompoundTag tag) {
        setEnergy(tag.getInt("Energy"));
        setRecipeTime(tag.getInt("RecipeTime"));
        setMaxRecipeTime(tag.getInt("MaxRecipeTime"));
        setWaterIn(tag.getInt("WaterIn"));
        setInputDump(tag.getBoolean("InputDump"));
    }

    @Override
    public void saveAdditional(@NotNull CompoundTag nbt) {
        nbt.putInt("Energy", getEnergy());
        nbt.putInt("RecipeTime", getRecipeTime());
        nbt.putInt("MaxRecipeTime", getMaxRecipeTime());
        nbt.putInt("WaterIn", getWaterIn());
        nbt.putBoolean("InputDump", isInputDump());
        ContainerHelper.saveAllItems(nbt, this.items, true);
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        this.items.clear();
        ContainerHelper.loadAllItems(nbt, this.items);
        setEnergy(nbt.getInt("Energy"));
        setRecipeTime(nbt.getInt("RecipeTime"));
        setMaxRecipeTime(nbt.getInt("MaxRecipeTime"));
        setWaterIn(nbt.getInt("WaterIn"));
        setInputDump(nbt.getBoolean("InputDump"));
    }

    @NotNull
    @Override
    protected Component getDefaultName() {
        return Component.translatable("block.thoriumreactors.fluid_evaporation_block");
    }

    @NotNull
    @Override
    protected AbstractContainerMenu createMenu(int pContainerId, @NotNull Inventory pInventory) {
        return new MachineFluidEvaporatorContainer(pContainerId, pInventory, getBlockPos(), getLevel(), getContainerSize());
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
        return switch (pSide) {
            case NORTH, EAST, SOUTH, WEST -> new int[]{1};
            default -> new int[]{};
        };
    }

    @Override
    public boolean canPlaceItemThroughFace(int pIndex, ItemStack pItemStack, @Nullable Direction pDirection) {
        return false;
    }

    @Override
    public boolean canTakeItemThroughFace(int pIndex, ItemStack pStack, Direction pDirection) {
        Direction facing = this.getBlockState().getValue(MachineElectrolyticSaltSeparatorBlock.FACING);
        return facing.getCounterClockWise() == pDirection;
    }
}
