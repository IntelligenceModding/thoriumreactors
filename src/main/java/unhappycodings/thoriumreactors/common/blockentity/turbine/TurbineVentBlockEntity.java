package unhappycodings.thoriumreactors.common.blockentity.turbine;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import unhappycodings.thoriumreactors.common.blockentity.turbine.base.TurbineFrameBlockEntity;
import unhappycodings.thoriumreactors.common.registration.ModBlockEntities;

public class TurbineVentBlockEntity extends TurbineFrameBlockEntity {

    public TurbineVentBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.TURBINE_VENT.get(), pPos, pBlockState);
    }

}
