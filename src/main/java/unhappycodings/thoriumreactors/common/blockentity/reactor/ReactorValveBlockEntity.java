package unhappycodings.thoriumreactors.common.blockentity.reactor;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import unhappycodings.thoriumreactors.common.block.reactor.ReactorValveBlock;
import unhappycodings.thoriumreactors.common.blockentity.ModFluidTank;
import unhappycodings.thoriumreactors.common.blockentity.reactor.base.ReactorFrameBlockEntity;
import unhappycodings.thoriumreactors.common.registration.ModBlockEntities;

public class ReactorValveBlockEntity extends ReactorFrameBlockEntity implements WorldlyContainer {
    public static final int MAX_FLUID_IN = 1000;
    private final ModFluidTank FLUID_TANK_IN = new ModFluidTank(MAX_FLUID_IN, true, true, 0, FluidStack.EMPTY);

    private final LazyOptional<IItemHandlerModifiable>[] itemHandler = SidedInvWrapper.create(this, Direction.values());
    private LazyOptional<FluidTank> lazyFluidInHandler = LazyOptional.empty();
    public BlockPos reactorCorePosition = new BlockPos(0, 0, 0);
    public NonNullList<ItemStack> items;

    public ReactorValveBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.REACTOR_VALVE.get(), pPos, pBlockState);
        items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
    }

    public ModFluidTank getFluidTank() {
        return FLUID_TANK_IN;
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

    public int getFluidAmountIn() {
        return FLUID_TANK_IN.getFluidAmount();
    }

    @Override
    public void onLoad() {
        super.onLoad();
        updateBlock();
        this.lazyFluidInHandler = LazyOptional.of(() -> FLUID_TANK_IN);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        Direction facing = this.getBlockState().getValue(ReactorValveBlock.FACING);
        if (cap == ForgeCapabilities.ITEM_HANDLER && !isRemoved() && side != null) {
            if (facing == side) return itemHandler[side.get3DDataValue()].cast();
        }
        if (cap == ForgeCapabilities.FLUID_HANDLER && side != null && side == getBlockState().getValue(ReactorValveBlock.FACING)) {
            return lazyFluidInHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @NotNull
    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag nbt = super.getUpdateTag();
        nbt.put("ReactorCorePosition", parsePosToTag(getReactorCorePosition()));
        nbt.put("FluidIn", FLUID_TANK_IN.writeToNBT(new CompoundTag()));
        return nbt;
    }

    @Override
    public void handleUpdateTag(final CompoundTag tag) {
        setReactorCorePosition(BlockEntity.getPosFromTag(tag.getCompound("ReactorCorePosition")));
        FLUID_TANK_IN.readFromNBT(tag.getCompound("FluidIn"));
        super.handleUpdateTag(tag);
    }

    @Override
    public void saveAdditional(@NotNull CompoundTag nbt) {
        nbt.put("ReactorCorePosition", parsePosToTag(getReactorCorePosition()));
        nbt.put("FluidIn", FLUID_TANK_IN.writeToNBT(new CompoundTag()));
        ContainerHelper.saveAllItems(nbt, this.items, true);
        super.saveAdditional(nbt);
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        ContainerHelper.loadAllItems(nbt, this.items);
        setReactorCorePosition(BlockEntity.getPosFromTag(nbt.getCompound("ReactorCorePosition")));
        FLUID_TANK_IN.readFromNBT(nbt.getCompound("FluidIn"));
        super.load(nbt);
    }

    public CompoundTag parsePosToTag(BlockPos pos) {
        CompoundTag position = new CompoundTag();
        position.putInt("x", pos.getX());
        position.putInt("y", pos.getY());
        position.putInt("z", pos.getZ());
        return position;
    }

    public BlockPos getReactorCorePosition() {
        return reactorCorePosition;
    }

    public void setReactorCorePosition(BlockPos reactorCorePosition) {
        this.reactorCorePosition = reactorCorePosition;
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

    @Override
    public int[] getSlotsForFace(Direction pSide) {
        return new int[1];
    }

    @Override
    public boolean canPlaceItemThroughFace(int pIndex, ItemStack pItemStack, @Nullable Direction pDirection) {
        return true;
    }

    @Override
    public boolean canTakeItemThroughFace(int pIndex, ItemStack pStack, Direction pDirection) {
        return true;
    }

    @Override
    public int getContainerSize() {
        return 1;
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
    public boolean stillValid(Player pPlayer) {
        return false;
    }

    @Override
    public void clearContent() {

    }
}
