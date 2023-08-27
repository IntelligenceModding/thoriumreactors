package unhappycodings.thoriumreactors.common.blockentity.thermal;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import unhappycodings.thoriumreactors.common.blockentity.thermal.base.ThermalFrameBlockEntity;
import unhappycodings.thoriumreactors.common.registration.ModBlockEntities;

public class ThermalConductorBlockEntity extends ThermalFrameBlockEntity {

    public ThermalConductorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.THERMAL_CONDUCTOR.get(), pPos, pBlockState);
    }

}