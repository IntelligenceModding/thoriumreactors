package unhappycodings.thoriumreactors.common.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import unhappycodings.thoriumreactors.common.block.MachineGeneratorBlock;
import unhappycodings.thoriumreactors.common.container.MachineGeneratorContainer;
import unhappycodings.thoriumreactors.common.registration.ModBlockEntities;

public class MachineGeneratorBlockEntity extends BaseContainerBlockEntity implements MenuProvider {
    public NonNullList<ItemStack> items;
    public static final float MAX_POWER = 75000f;
    public static final float PRODUCTION = 135f;
    float currentProduction = 0;
    float energy = 0;
    float maxFuel = 0;
    float fuel = 0;

    public MachineGeneratorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.GENERATOR_BLOCK.get(), pPos, pBlockState);
        items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
    }

    public void tick () {
        ItemStack input = getItem(getInputSlot());
        if (getFuel() <= 0 && ForgeHooks.getBurnTime(input, null) > 0 && getEnergy() != MAX_POWER) {
            addFuel(input);
            setMaxFuel(getFuel());
            input.shrink(1);
        }
        if (getFuel() > 0) {
            setState(true);
            if ((getEnergy() + PRODUCTION <= MAX_POWER))
                setEnergy(getEnergy() + getCurrentProduction());
            else if (getEnergy() > 0)
                setEnergy(MAX_POWER);
            currentProduction = PRODUCTION;
            setFuel(getFuel() - 1);
        } else {
            setState(false);
            currentProduction = 0;
        }

    }

    public void addFuel(ItemStack stack) {
        setFuel(getFuel() + ForgeHooks.getBurnTime(stack, null));
    }

    public void setState(boolean state) {
        level.setBlock(getBlockPos(), getBlockState().setValue(MachineGeneratorBlock.POWERED, state), 3);
    }

    public float getMaxFuel() {
        return maxFuel;
    }

    public void setMaxFuel(float maxFuel) {
        this.maxFuel = maxFuel;
    }

    public float getFuel() {
        return fuel;
    }

    public void setFuel(float fuel) {
        this.fuel = fuel;
    }

    public float getEnergy() {
        return energy;
    }

    public void setEnergy(float energy) {
        this.energy = energy;
    }

    public float getCapacity() {
        return MAX_POWER;
    }

    public float getMaxProduction() {
        return PRODUCTION;
    }

    public float getCurrentProduction() {
        return currentProduction;
    }

    public void setCurrentProduction(float currentProduction) {
        this.currentProduction = currentProduction;
    }

    public int getInputSlot() {
        return 0;
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
        nbt.putFloat("Fuel", getFuel());
        nbt.putFloat("MaxFuel", getMaxFuel());
        nbt.putFloat("Energy", getEnergy());
        nbt.putFloat("Production", getCurrentProduction());
        return nbt;
    }

    @Override
    public void handleUpdateTag(final CompoundTag tag) {
        setFuel(tag.getFloat("Fuel"));
        setMaxFuel(tag.getFloat("MaxFuel"));
        setEnergy(tag.getFloat("Energy"));
        setCurrentProduction(tag.getFloat("Production"));
    }

    @Override
    public void saveAdditional(@NotNull CompoundTag nbt) {
        nbt.putFloat("Fuel", getFuel());
        nbt.putFloat("MaxFuel", getMaxFuel());
        nbt.putFloat("Energy", getEnergy());
        nbt.putFloat("Production", getCurrentProduction());
        ContainerHelper.saveAllItems(nbt, this.items, true);
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        this.items.clear();
        ContainerHelper.loadAllItems(nbt, this.items);
        setFuel(nbt.getFloat("Fuel"));
        setMaxFuel(nbt.getFloat("MaxFuel"));
        setEnergy(nbt.getFloat("Energy"));
        setCurrentProduction(nbt.getFloat("Production"));
    }

    @NotNull
    @Override
    protected Component getDefaultName() {
        return Component.translatable("block.thoriumreactors.generator_block");
    }

    @NotNull
    @Override
    protected AbstractContainerMenu createMenu(int pContainerId, @NotNull Inventory pInventory) {
        return new MachineGeneratorContainer(pContainerId, pInventory, getBlockPos(), getLevel(), getContainerSize());
    }

    @Override
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

}
