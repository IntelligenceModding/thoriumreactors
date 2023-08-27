package unhappycodings.thoriumreactors.common.blockentity.base;

import net.minecraft.core.BlockPos;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MachineContainerBlockEntity extends BaseContainerBlockEntity {

    protected MachineContainerBlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    public boolean isSpaceAbove() {
        return this.getLevel().getBlockState(this.getBlockPos().above()).is(Blocks.AIR);
    }

    public FluidStack getFluidIn() {
        return FluidStack.EMPTY;
    }

    public void setFluidIn(FluidStack stack) {
    }

    public int getFluidCapacityIn() {
        return 0;
    }

    public int getFluidSpaceIn() {
        return 0;
    }

    public int getFluidAmountIn() {
        return 0;
    }

    public FluidStack getFluidOut() {
        return FluidStack.EMPTY;
    }

    public void setFluidOut(FluidStack stack) {
    }

    public int getFluidCapacityOut() {
        return 0;
    }

    public int getFluidSpaceOut() {
        return 0;
    }

    public int getFluidAmountOut() {
        return 0;
    }

    public int getNeededEnergy() {
        return 0;
    }

    public int getFluidAmountNeeded() {
        return 0;
    }

    public int getEnergy() {
        return 0;
    }

    public boolean getState() {
        return true;
    }

    public int getRedstoneMode() {
        return 0;
    }

    public void setRedstoneMode(int amount) {
    }

    public boolean isPowerable() {
        return false;
    }

    public void setPowerable(boolean powerable) {
    }

    public boolean isInputDump() {
        return false;
    }

    public void setInputDump(boolean inputDump) {
    }

    public boolean isOutputDump() {
        return false;
    }

    public void setOutputDump(boolean inputDump) {
    }

    @Override
    protected Component getDefaultName() {
        return null;
    }

    @Override
    protected AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory) {
        return null;
    }

    @Override
    public int getContainerSize() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public ItemStack getItem(int pSlot) {
        return null;
    }

    @Override
    public ItemStack removeItem(int pSlot, int pAmount) {
        return null;
    }

    @Override
    public ItemStack removeItemNoUpdate(int pSlot) {
        return null;
    }

    @Override
    public void setItem(int pSlot, @NotNull ItemStack pStack) {
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
    public boolean stillValid(Player pPlayer) {
        return false;
    }

    @Override
    public void clearContent() {

    }
}
