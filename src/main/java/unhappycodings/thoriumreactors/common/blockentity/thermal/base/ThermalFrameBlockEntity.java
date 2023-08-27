package unhappycodings.thoriumreactors.common.blockentity.thermal.base;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class ThermalFrameBlockEntity extends BlockEntity {
    public BlockPos controllerPos = new BlockPos(0, 0, 0);

    public ThermalFrameBlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    @NotNull
    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag nbt = new CompoundTag();
        nbt.put("ControllerPos", parsePosToTag(getControllerPos()));
        return nbt;
    }

    @Override
    public void handleUpdateTag(final CompoundTag tag) {
        setControllerPos(BlockEntity.getPosFromTag(tag.getCompound("ControllerPos")));
    }

    @Override
    public void saveAdditional(@NotNull CompoundTag nbt) {
        nbt.put("ControllerPos", parsePosToTag(getControllerPos()));
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        setControllerPos(BlockEntity.getPosFromTag(nbt.getCompound("ControllerPos")));
    }

    public BlockPos getControllerPos() {
        return controllerPos;
    }

    public void setControllerPos(BlockPos pos) {
        this.controllerPos = pos;
    }

    public CompoundTag parsePosToTag(BlockPos pos) {
        CompoundTag position = new CompoundTag();
        position.putInt("x", pos.getX());
        position.putInt("y", pos.getY());
        position.putInt("z", pos.getZ());
        return position;
    }

}
