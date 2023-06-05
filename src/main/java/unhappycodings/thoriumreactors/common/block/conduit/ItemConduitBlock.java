package unhappycodings.thoriumreactors.common.block.conduit;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import unhappycodings.thoriumreactors.common.registration.ModBlocks;

import java.util.function.Function;

public class ItemConduitBlock extends Block {
    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty EAST = BooleanProperty.create("east");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty WEST = BooleanProperty.create("west");
    public static final BooleanProperty UP = BooleanProperty.create("up");
    public static final BooleanProperty DOWN = BooleanProperty.create("down");

    public static final VoxelShape SHAPE_CORE = Block.box(6, 6, 6, 10, 10, 10);
    public static final VoxelShape SHAPE_NORTH = Block.box(6, 6, 0, 10, 10, 6);
    public static final VoxelShape SHAPE_EAST = Block.box(10, 6, 6, 16, 10, 10);
    public static final VoxelShape SHAPE_SOUTH = Block.box(6, 6, 10, 10, 10, 16);
    public static final VoxelShape SHAPE_WEST = Block.box(0, 6, 6, 6, 10, 10);
    public static final VoxelShape SHAPE_UP = Block.box(6, 10, 6, 10, 16, 10);
    public static final VoxelShape SHAPE_DOWN = Block.box(6, 0, 6, 10, 6, 10);

    public ItemConduitBlock() {
        super(Properties.of(Material.STONE));
        this.registerDefaultState(this.stateDefinition.any().setValue(NORTH, false).setValue(EAST, false).setValue(SOUTH, false).setValue(WEST, false).setValue(UP, false).setValue(DOWN, false));
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return Shapes.or(SHAPE_CORE, pState.getValue(NORTH) ? SHAPE_NORTH : Shapes.empty(),
                pState.getValue(EAST) ? SHAPE_EAST : Shapes.empty(),
                pState.getValue(SOUTH) ? SHAPE_SOUTH : Shapes.empty(),
                pState.getValue(WEST) ? SHAPE_WEST : Shapes.empty(),
                pState.getValue(UP) ? SHAPE_UP : Shapes.empty(),
                pState.getValue(DOWN) ? SHAPE_DOWN : Shapes.empty());
    }

    @Override
    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
        super.neighborChanged(pState, pLevel, pPos, pBlock, pFromPos, pIsMoving);
        BlockState newState;
        newState = pState.setValue(NORTH, isRelative(pLevel, pPos, Direction.NORTH)).setValue(EAST, isRelative(pLevel, pPos, Direction.EAST)).setValue(SOUTH, isRelative(pLevel, pPos, Direction.SOUTH)).setValue(WEST, isRelative(pLevel, pPos, Direction.WEST)).setValue(UP, isRelative(pLevel, pPos, Direction.UP)).setValue(DOWN, isRelative(pLevel, pPos, Direction.DOWN));
        pLevel.setBlockAndUpdate(pPos, newState);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(NORTH, isRelative(pContext, Direction.NORTH)).setValue(EAST, isRelative(pContext, Direction.EAST)).setValue(SOUTH, isRelative(pContext, Direction.SOUTH)).setValue(WEST, isRelative(pContext, Direction.WEST)).setValue(UP, isRelative(pContext, Direction.UP)).setValue(DOWN, isRelative(pContext, Direction.DOWN));
    }

    public boolean isRelative(BlockPlaceContext pContext, Direction direction) {
        return isRelative(pContext.getLevel(), pContext.getClickedPos(), direction);
    }

    public boolean isRelative(Level level, BlockPos pos, Direction direction) {
        return level.getBlockState(pos.relative(direction)).is(ModBlocks.ITEM_CONDUIT_BLOCK.get());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(NORTH, EAST, SOUTH, WEST, UP, DOWN);
    }

}
