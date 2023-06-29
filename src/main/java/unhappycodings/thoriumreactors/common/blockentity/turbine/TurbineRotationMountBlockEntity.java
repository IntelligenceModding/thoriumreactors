package unhappycodings.thoriumreactors.common.blockentity.turbine;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import unhappycodings.thoriumreactors.common.blockentity.turbine.base.TurbineFrameBlockEntity;
import unhappycodings.thoriumreactors.common.registration.ModBlockEntities;

public class TurbineRotationMountBlockEntity extends TurbineFrameBlockEntity {

    public TurbineRotationMountBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.TURBINE_ROTATION_MOUNT.get(), pPos, pBlockState);
    }

}
