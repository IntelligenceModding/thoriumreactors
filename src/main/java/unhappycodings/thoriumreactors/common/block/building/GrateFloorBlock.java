package unhappycodings.thoriumreactors.common.block.building;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GrateFloorBlock extends Block {
    protected static final VoxelShape TOP = Block.box(0, 14, 0, 16, 16, 16);
    protected static final VoxelShape BOTTOM = Block.box(0, 0, 0, 16, 2, 16);
    public static final EnumProperty<SlabType> TYPE = SlabBlock.TYPE;

    public GrateFloorBlock(Properties pProperties) {
        super(pProperties.noOcclusion());
        this.registerDefaultState(this.defaultBlockState().setValue(TYPE, SlabType.TOP));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(TYPE);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockPos blockpos = pContext.getClickedPos();
        BlockState blockstate = this.defaultBlockState().setValue(TYPE, SlabType.BOTTOM);
        Direction direction = pContext.getClickedFace();
        return direction == Direction.DOWN || direction != Direction.UP && pContext.getClickLocation().y - (double)blockpos.getY() > 0.5 ? blockstate.setValue(TYPE, SlabType.TOP) : blockstate;
    }

    @SuppressWarnings("deprecation")
    @NotNull
    @Override
    public VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        return pState.getValue(TYPE) == SlabType.BOTTOM ? BOTTOM : TOP;
    }

}
