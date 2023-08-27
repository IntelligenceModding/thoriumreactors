package unhappycodings.thoriumreactors.common.block.reactor;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import unhappycodings.thoriumreactors.common.block.reactor.base.ReactorFrameBlock;
import unhappycodings.thoriumreactors.common.blockentity.reactor.ReactorGlassBlockEntity;

public class ReactorGlassBlock extends ReactorFrameBlock {

    public ReactorGlassBlock() {
        super(BlockBehaviour.Properties.of(Material.GLASS).noOcclusion().sound(SoundType.GLASS).strength(3f));
    }

    @SuppressWarnings("deprecation")
    @NotNull
    @Override
    public VoxelShape getVisualShape(@NotNull BlockState pState, @NotNull BlockGetter pReader, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        return Shapes.empty();
    }

    @SuppressWarnings("deprecation")
    @Override
    public float getShadeBrightness(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos) {
        return 1.0F;
    }

    @Override
    public boolean propagatesSkylightDown(@NotNull BlockState pState, @NotNull BlockGetter pReader, @NotNull BlockPos pPos) {
        return true;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean skipRendering(@NotNull BlockState pState, BlockState pAdjacentBlockState, @NotNull Direction pSide) {
        return pAdjacentBlockState.is(this) || super.skipRendering(pState, pAdjacentBlockState, pSide);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return new ReactorGlassBlockEntity(pPos, pState);
    }

}
