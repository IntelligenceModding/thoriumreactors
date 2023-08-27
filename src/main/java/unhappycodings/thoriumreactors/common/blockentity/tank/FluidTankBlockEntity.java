package unhappycodings.thoriumreactors.common.blockentity.tank;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import unhappycodings.thoriumreactors.common.blockentity.ModFluidTank;
import unhappycodings.thoriumreactors.common.container.tank.FluidTankContainer;
import unhappycodings.thoriumreactors.common.network.PacketHandler;
import unhappycodings.thoriumreactors.common.network.toclient.reactor.ClientFluidTankRenderDataPacket;

import java.util.List;

public class FluidTankBlockEntity extends BlockEntity implements MenuProvider {
    public int capacity = 0;
    private final boolean isCreative;
    private final ModFluidTank FLUID_TANK_IN;
    private LazyOptional<FluidTank> lazyFluidInHandler = LazyOptional.empty();

    public FluidTankBlockEntity(BlockPos pPos, BlockState pBlockState, int capacity, BlockEntityType<FluidTankBlockEntity> type) {
        super(type, pPos, pBlockState);
        this.isCreative = capacity == -1;
        this.capacity = capacity;
        if (this.isCreative) this.capacity = Integer.MAX_VALUE;
        FLUID_TANK_IN = new ModFluidTank(capacity, true, true, 0, FluidStack.EMPTY);
    }

    public void tick() {
        if (isCreative) {
            boolean isAir = FLUID_TANK_IN.getFluid().getAmount() < Integer.MAX_VALUE;
            FLUID_TANK_IN.setFluid(new FluidStack(isAir ? FluidStack.EMPTY : FLUID_TANK_IN.getFluid(), Integer.MAX_VALUE));
        }
        updateRenderData();
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
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
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
        List<ServerPlayer> players = level.getEntitiesOfClass(ServerPlayer.class, new AABB(p.getX() + -18, p.getY() + -18, p.getZ() + -18, p.getX() + 18, p.getY() + 18, p.getZ() + 18));

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

    @NotNull
    @Override
    public Component getDisplayName() {
        return Component.translatable(this.getBlockState().getBlock().getDescriptionId());
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, @NotNull Inventory pPlayerInventory, @NotNull Player pPlayer) {
        return new FluidTankContainer(pContainerId, pPlayerInventory, getBlockPos(), getLevel(), getContainerSize());
    }
}
