package unhappycodings.thoriumreactors.common.block.thermal;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.*;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import unhappycodings.thoriumreactors.common.block.thermal.base.ThermalFrameBlock;
import unhappycodings.thoriumreactors.common.blockentity.thermal.ThermalHeatSinkBlockEntity;

import java.util.Map;

public class ThermalSinkBlock extends ThermalFrameBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public static final VoxelShape EAST_WEST = Shapes.or(Block.box(0, 3, 13.25, 16, 15.5, 14.25),
            Block.box(0, 0, 0, 16, 3, 16),
            Block.box(0, 3, 0, 16, 15, 1),
            Block.box(0, 3, 15, 16, 15, 16),
            Block.box(0, 3, 1.75, 16, 15.5, 2.75),
            Block.box(0, 3, 3.75, 16, 16, 4.75),
            Block.box(0, 3, 11.25, 16, 16, 12.25),
            Block.box(0, 3, 9.25, 16, 15.75, 10.25),
            Block.box(0, 3, 5.75, 16, 15.75, 6.75),
            Block.box(0, 3, 7.5, 16, 16, 8.5));


    public static final VoxelShape NORTH_SOUTH = Shapes.or(Block.box(1.75, 3, 0, 2.75, 15.5, 16),
            Block.box(0, 0, 0, 16, 3, 16),
            Block.box(15, 3, 0, 16, 15, 16),
            Block.box(0, 3, 0, 1, 15, 16),
            Block.box(13.25, 3, 0, 14.25, 15.5, 16),
            Block.box(11.25, 3, 0, 12.25, 16, 16),
            Block.box(3.75, 3, 0, 4.75, 16, 16),
            Block.box(5.75, 3, 0, 6.75, 15.75, 16),
            Block.box(9.25, 3, 0, 10.25, 15.75, 16),
            Block.box(7.5, 3, 0, 8.5, 16, 16));

    private static final Map<Direction, VoxelShape> AABBS = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, NORTH_SOUTH, Direction.SOUTH, NORTH_SOUTH, Direction.WEST, EAST_WEST, Direction.EAST, EAST_WEST));

    public ThermalSinkBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(5f));
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }

    @NotNull
    @Override
    public VoxelShape getShape(BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        return AABBS.get(pState.getValue(FACING));
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return new ThermalHeatSinkBlockEntity(pPos, pState);
    }
}
