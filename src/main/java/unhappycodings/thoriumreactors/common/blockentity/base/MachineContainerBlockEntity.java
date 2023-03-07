package unhappycodings.thoriumreactors.common.blockentity.base;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class MachineContainerBlockEntity extends BaseContainerBlockEntity {

    protected MachineContainerBlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    public void setWaterIn(int amount) {
    }

    public void setWaterOut(int amount) {
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

    public int getWaterIn() {
        return 0;
    }

    public int getWaterOut() {
        return 0;
    }

    public int getRedstoneMode() {
        return 0;
    }

    public void setRedstoneMode(int amount) {
    }

    public void setPowerable(boolean powerable) {
    }

    public boolean isPowerable() {
        return false;
    }

    public void setInputDump(boolean inputDump) {}

    public boolean isInputDump() {
        return false;
    }

    public void setOutputDump(boolean inputDump) {}

    public boolean isOutputDump() {
        return false;
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
    public void setItem(int pSlot, ItemStack pStack) {

    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return false;
    }

    @Override
    public void clearContent() {

    }
}
