package unhappycodings.thoriumreactors.common.blockentity.turbine;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import unhappycodings.thoriumreactors.common.registration.*;

public class TurbineControllerBlockEntity extends BlockEntity {
    public int turbineHeight;
    public boolean assembled;
    public String warning = "";

    public TurbineControllerBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.TURBINE_CONTROLLER.get(), pPos, pBlockState);
    }

    public void tick() {

    }

    public int getTurbineHeight() {
        return turbineHeight;
    }

    public void setTurbineHeight(int height) {
        turbineHeight = height;
    }

    public BlockState getState(BlockPos pos) {
        return this.level.getBlockState(pos);
    }

    public boolean isAssembled() {
        return assembled;
    }

    public void setAssembled(boolean assembled) {
        this.assembled = assembled;
    }

    public String getWarning() {
        return warning;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }

    @NotNull
    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag nbt = new CompoundTag();
        nbt.putBoolean("Assembled", isAssembled());
        nbt.putInt("Height", getTurbineHeight());
        return nbt;
    }

    @Override
    public void handleUpdateTag(final CompoundTag tag) {
        setAssembled(tag.getBoolean("Assembled"));
        setTurbineHeight(tag.getInt("Height"));
    }

    @Override
    public void saveAdditional(@NotNull CompoundTag nbt) {
        nbt.putBoolean("Assembled", isAssembled());
        nbt.putInt("Height", getTurbineHeight());
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        setAssembled(nbt.getBoolean("Assembled"));
        setTurbineHeight(nbt.getInt("Height"));
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

    @Override
    public AABB getRenderBoundingBox() {
        return new AABB(getBlockPos().offset(-10, -10, -10), getBlockPos().offset(11, 11, 11));
    }

}
