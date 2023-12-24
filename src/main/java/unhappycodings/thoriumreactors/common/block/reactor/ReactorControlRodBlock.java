package unhappycodings.thoriumreactors.common.block.reactor;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import unhappycodings.thoriumreactors.common.block.reactor.base.ReactorFrameBlock;
import unhappycodings.thoriumreactors.common.blockentity.reactor.ReactorControlRodBlockEntity;

public class ReactorControlRodBlock extends ReactorFrameBlock {

    public ReactorControlRodBlock() {
        super(Properties.copy(Blocks.IRON_BLOCK).strength(5f));
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return new ReactorControlRodBlockEntity(pPos, pState);
    }

}
