package unhappycodings.thoriumreactors.common.blockentity.turbine;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import unhappycodings.thoriumreactors.common.blockentity.turbine.base.TurbineFrameBlockEntity;
import unhappycodings.thoriumreactors.common.registration.ModBlockEntities;

public class TurbineRotorBlockEntity extends TurbineFrameBlockEntity {

    public TurbineRotorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.TURBINE_ROTOR.get(), pPos, pBlockState);
    }

}
