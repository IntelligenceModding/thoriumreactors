package unhappycodings.thoriumreactors.common.blockentity.thermal;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import unhappycodings.thoriumreactors.common.blockentity.thermal.base.ThermalFrameBlockEntity;
import unhappycodings.thoriumreactors.common.registration.ModBlockEntities;

public class ThermalHeatSinkBlockEntity extends ThermalFrameBlockEntity {

    public ThermalHeatSinkBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.THERMAL_HEAT_SINK.get(), pPos, pBlockState);
    }

}