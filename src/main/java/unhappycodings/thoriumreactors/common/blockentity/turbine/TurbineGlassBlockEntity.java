package unhappycodings.thoriumreactors.common.blockentity.turbine;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.common.blockentity.turbine.base.TurbineFrameBlockEntity;
import unhappycodings.thoriumreactors.common.registration.ModBlockEntities;

public class TurbineGlassBlockEntity extends TurbineFrameBlockEntity {

    public TurbineGlassBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.TURBINE_GLASS.get(), pPos, pBlockState);
    }

}