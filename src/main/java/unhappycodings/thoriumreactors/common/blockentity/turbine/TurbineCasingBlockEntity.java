package unhappycodings.thoriumreactors.common.blockentity.turbine;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import unhappycodings.thoriumreactors.common.blockentity.turbine.base.TurbineFrameBlockEntity;
import unhappycodings.thoriumreactors.common.registration.ModBlockEntities;

public class TurbineCasingBlockEntity extends TurbineFrameBlockEntity {

    public TurbineCasingBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.TURBINE_CASING.get(), pPos, pBlockState);
    }

}