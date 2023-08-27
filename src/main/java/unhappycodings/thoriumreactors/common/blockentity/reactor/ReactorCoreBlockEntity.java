package unhappycodings.thoriumreactors.common.blockentity.reactor;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import unhappycodings.thoriumreactors.common.blockentity.reactor.base.ReactorFrameBlockEntity;
import unhappycodings.thoriumreactors.common.registration.ModBlockEntities;

public class ReactorCoreBlockEntity extends ReactorFrameBlockEntity {

    public ReactorCoreBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.REACTOR_CORE.get(), pPos, pBlockState);
    }

}
