package unhappycodings.thoriumreactors.common.blockentity.tank;

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
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import unhappycodings.thoriumreactors.common.blockentity.ModFluidTank;
import unhappycodings.thoriumreactors.common.container.tank.FluidTankContainer;
import unhappycodings.thoriumreactors.common.network.PacketHandler;
import unhappycodings.thoriumreactors.common.network.toclient.reactor.ClientFluidTankRenderDataPacket;

import java.util.List;

public class FluidTankBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer, MenuProvider {
    public int capacity = 0;
    private final boolean isCreative;
    private LazyOptional<FluidTank> lazyFluidInHandler = LazyOptional.empty();
    private final LazyOptional<? extends IItemHandler>[] itemHandler = SidedInvWrapper.create(this, Direction.values());
    private final ModFluidTank FLUID_TANK_IN;
    public NonNullList<ItemStack> items;

    public FluidTankBlockEntity(BlockPos pPos, BlockState pBlockState, int capacity, BlockEntityType<FluidTankBlockEntity> type) {
        super(type, pPos, pBlockState);
        this.isCreative = capacity == -1;
        this.capacity = capacity;
        if (this.isCreative) this.capacity = Integer.MAX_VALUE;
        FLUID_TANK_IN = new ModFluidTank(capacity, true, true, 0, FluidStack.EMPTY);
        items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
    }

    public void tick() {
        if (isCreative) {
            boolean isAir = FLUID_TANK_IN.getFluid().getAmount() < Integer.MAX_VALUE;
            FLUID_TANK_IN.setFluid(new FluidStack(isAir ? FluidStack.EMPTY : FLUID_TANK_IN.getFluid(), Integer.MAX_VALUE));
        }
        updateRenderData();

        if (level.getGameTime() % 10 == 0 && getItem(0).is(Items.BUCKET) && getItem(1).isEmpty() && getFluidAmountIn() > 0) {
            ItemStack stack = getItem(0).copy();
            if (!stack.is(Items.BUCKET)) return;

            stack.setCount(1);
            stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).ifPresent(storage -> {
                int amount = storage.fill(getFluidIn().copy(), IFluidHandler.FluidAction.SIMULATE);
                if (amount > 0) {
                    getItem(0).shrink(1);
                    setItem(1, getFluidIn().getFluid().getBucket().getDefaultInstance());
                    if (!this.isCreative) {
                        getFluidIn().shrink(amount);
                    }
                }
            });
        }
    }

    @Override
    public void onLoad() {
        super.onLoad();
        updateBlock();
        this.lazyFluidInHandler = LazyOptional.of(() -> FLUID_TANK_IN);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.FLUID_HANDLER && side != null) {
            return lazyFluidInHandler.cast();
        }
        if (cap == ForgeCapabilities.ITEM_HANDLER && !isRemoved() && side != null) {
            return itemHandler[0].cast();
        }
        return super.getCapability(cap, side);
    }

    @NotNull
    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag nbt = new CompoundTag();
        nbt.put("Fluid", FLUID_TANK_IN.writeToNBT(new CompoundTag()));
        return nbt;
    }

    @Override
    public void handleUpdateTag(final CompoundTag tag) {
        FLUID_TANK_IN.readFromNBT(tag.getCompound("Fluid"));
    }

    @Override
    public void saveAdditional(@NotNull CompoundTag nbt) {
        nbt.put("Fluid", FLUID_TANK_IN.writeToNBT(new CompoundTag()));
        ContainerHelper.saveAllItems(nbt, this.items, true);
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        ContainerHelper.loadAllItems(nbt, this.items);
        FLUID_TANK_IN.readFromNBT(nbt.getCompound("Fluid"));
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

    public void updateBlock() {
        if (level != null && !level.isClientSide) {
            BlockState state = level.getBlockState(worldPosition);
            level.sendBlockUpdated(worldPosition, state, state, 2);
            setChanged(level, getBlockPos(), state);
        }
    }

    public void updateRenderData() {
        BlockPos p = getBlockPos();
        List<ServerPlayer> players = level.getEntitiesOfClass(ServerPlayer.class, new AABB(p.getX() -18, p.getY() -18, p.getZ() -18, p.getX() + 18, p.getY() + 18, p.getZ() + 18));

        for (ServerPlayer player : players) {
            PacketHandler.sendToClient(new ClientFluidTankRenderDataPacket(getBlockPos(), getFluidIn()), player);
        }
    }

    public FluidStack getFluidIn() {
        return FLUID_TANK_IN.getFluid();
    }

    public void setFluidIn(FluidStack stack) {
        FLUID_TANK_IN.setFluid(stack);
    }

    public int getFluidCapacityIn() {
        return FLUID_TANK_IN.getCapacity();
    }

    public int getFluidSpaceIn() {
        return FLUID_TANK_IN.getSpace();
    }

    public ModFluidTank getFluidTank() {
        return FLUID_TANK_IN;
    }

    public int getFluidAmountIn() {
        return FLUID_TANK_IN.getFluidAmount();
    }

    public int getContainerSize() {
        return 2;
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
    public int @NotNull [] getSlotsForFace(@NotNull Direction pSide) {
        return new int[]{0, 1};
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, @NotNull ItemStack stack, @Nullable Direction direction) {
        return index == 0;
    }

    @Override
    public boolean canTakeItemThroughFace(int index, @NotNull ItemStack stack, @NotNull Direction direction) {
        return index == 1;
    }

    @NotNull
    @Override
    protected Component getDefaultName() {
        return Component.translatable(this.getBlockState().getBlock().getDescriptionId());
    }

    @NotNull
    @Override
    protected AbstractContainerMenu createMenu(int i, @NotNull Inventory inventory) {
        return new FluidTankContainer(i, inventory, getBlockPos(), getLevel(), getContainerSize());
    }

}
