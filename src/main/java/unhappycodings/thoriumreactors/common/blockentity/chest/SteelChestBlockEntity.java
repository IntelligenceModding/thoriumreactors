package unhappycodings.thoriumreactors.common.blockentity.chest;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.CompoundContainer;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.common.container.chest.SteelChestContainer;
import unhappycodings.thoriumreactors.common.registration.ModBlockEntities;

public class SteelChestBlockEntity extends ChestBlockEntity {
    private net.minecraftforge.common.util.LazyOptional<?> itemHandler = net.minecraftforge.common.util.LazyOptional.of(() -> createUnSidedHandler());
    private final ChestLidController chestLidController;
    private final ContainerOpenersCounter counter;
    public NonNullList<ItemStack> items;
    public int openers = 0;
    public float angle;

    public SteelChestBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityType.CHEST, pPos, pBlockState);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        this.counter = new ContainerOpenersCounter() {
            @Override
            protected void onOpen(@NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull BlockState pState) {
                getLevel().playLocalSound(pPos.getX() + 0.5, pPos.getY() + 0.5, pPos.getZ() + 0.5, SoundEvents.CHEST_OPEN, SoundSource.BLOCKS, 1f, 1f, false);
            }

            @Override
            protected void onClose(@NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull BlockState pState) {
                getLevel().playLocalSound(pPos.getX() + 0.5, pPos.getY() + 0.5, pPos.getZ() + 0.5, SoundEvents.CHEST_CLOSE, SoundSource.BLOCKS, 1f, 1f, false);
            }

            @Override
            protected void openerCountChanged(@NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull BlockState pState, int pCount, int pOpenCount) {
                SteelChestBlockEntity.this.signalOpenCount(pLevel, pPos, pState, pCount, pOpenCount);
                openers = pOpenCount;

                triggerEvent(1, counter.getOpenerCount());
            }

            @Override
            protected boolean isOwnContainer(@NotNull Player player) {
                if (!(player.containerMenu instanceof ChestMenu)) {
                    return false;
                } else {
                    Container container = ((ChestMenu) player.containerMenu).getContainer();
                    return container == SteelChestBlockEntity.this || container instanceof CompoundContainer && ((CompoundContainer) container).contains(SteelChestBlockEntity.this);
                }
            }
        };
        this.chestLidController = new ChestLidController();
    }

    public <T extends BlockEntity> void lidAnimateTick() {
        chestLidController.tickLid();
    }

    public void tick() {
        lidAnimateTick();
    }

    @Override
    public void setChanged() {
        super.setChanged();

        if (!this.getLevel().isClientSide && this.getLevel() != null && this.getLevel() instanceof ServerLevel w)
            w.getChunkSource().blockChanged(getBlockPos());
    }

    @Override
    public void startOpen(@NotNull Player pPlayer) {
        if (!this.remove && !pPlayer.isSpectator()) {
            this.counter.incrementOpeners(pPlayer, level, getBlockPos(), getBlockState());
            setChanged();
        }
    }

    @Override
    public void stopOpen(@NotNull Player pPlayer) {
        if (!this.remove && !pPlayer.isSpectator()) {
            this.counter.decrementOpeners(pPlayer, level, getBlockPos(), getBlockState());
            setChanged();
        }
    }

    @Override
    public boolean triggerEvent(int pId, int pType) {
        if (pId == 1) {
            this.chestLidController.shouldBeOpen(pType > 0);
            return true;
        } else {
            return super.triggerEvent(pId, pType);
        }
    }

    @NotNull
    @Override
    public BlockEntityType<?> getType() {
        return ModBlockEntities.STEEL_CHEST_BLOCK.get();
    }

    @Override
    public int getContainerSize() {
        return 11 * 7;
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
    public boolean stillValid(@NotNull Player player) {
        if (this.level.getBlockEntity(this.worldPosition) != this) {
            return false;
        } else {
            return !(player.distanceToSqr((double) this.worldPosition.getX() + 0.5D, (double) this.worldPosition.getY() + 0.5D, (double) this.worldPosition.getZ() + 0.5D) > 64.0D);
        }
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag nbt) {
        ContainerHelper.saveAllItems(nbt, this.items, true);
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        this.items.clear();
        ContainerHelper.loadAllItems(nbt, this.items);
    }

    @NotNull
    @Override
    protected Component getDefaultName() {
        return Component.literal("");
    }

    @NotNull
    @Override
    protected AbstractContainerMenu createMenu(int pContainerId, @NotNull Inventory pInventory) {
        return new SteelChestContainer(pContainerId, pInventory, getBlockPos(), getLevel(), getContainerSize());
    }

    @Override
    public float getOpenNess(float pPartialTicks) {
        return this.chestLidController.getOpenness(pPartialTicks);
    }

    @Override
    public boolean canOpen(Player pPlayer) {
        return true;
    }

    @Override
    public void clearContent() {
        items.clear();
    }

    @Override
    public void recheckOpen() {
        if (!this.remove) {
            this.counter.recheckOpeners(this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }

    @Override
    protected void signalOpenCount(Level pLevel, BlockPos pPos, BlockState pState, int pEventId, int pEventParam) {
        Block block = pState.getBlock();
        pLevel.blockEvent(pPos, block, 1, pEventParam);
    }

    @Override
    protected net.minecraftforge.items.IItemHandler createUnSidedHandler() {
        return new net.minecraftforge.items.wrapper.InvWrapper(this);
    }

    @Override
    public <T> net.minecraftforge.common.util.LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> cap, @org.jetbrains.annotations.Nullable net.minecraft.core.Direction side) {
        if (!this.remove && cap == net.minecraftforge.common.capabilities.ForgeCapabilities.ITEM_HANDLER)
            return itemHandler.cast();
        return super.getCapability(cap, side);
    }

}
