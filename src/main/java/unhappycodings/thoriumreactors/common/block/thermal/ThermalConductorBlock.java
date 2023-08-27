package unhappycodings.thoriumreactors.common.block.thermal;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import unhappycodings.thoriumreactors.common.block.thermal.base.ThermalFrameBlock;
import unhappycodings.thoriumreactors.common.blockentity.thermal.ThermalConductorBlockEntity;

public class ThermalConductorBlock extends ThermalFrameBlock {

    public ThermalConductorBlock() {
        super(BlockBehaviour.Properties.of(Material.METAL).strength(5f));
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return new ThermalConductorBlockEntity(pPos, pState);
    }
}
