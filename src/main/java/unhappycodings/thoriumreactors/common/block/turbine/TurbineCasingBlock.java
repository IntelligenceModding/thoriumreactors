package unhappycodings.thoriumreactors.common.block.turbine;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import unhappycodings.thoriumreactors.common.block.turbine.base.TurbineFrameBlock;
import unhappycodings.thoriumreactors.common.blockentity.turbine.TurbineCasingBlockEntity;

public class TurbineCasingBlock extends TurbineFrameBlock {

    public TurbineCasingBlock() {
        super(Properties.of(Material.METAL).strength(5f));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new TurbineCasingBlockEntity(pos, state);
    }

}
