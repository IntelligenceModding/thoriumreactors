package unhappycodings.thoriumreactors.common.block.conduit;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.commands.ItemCommands;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import org.antlr.v4.runtime.misc.Triple;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import unhappycodings.thoriumreactors.common.blockentity.ThoriumCraftingTableBlockEntity;
import unhappycodings.thoriumreactors.common.blockentity.conduit.ItemConduitBlockEntity;
import unhappycodings.thoriumreactors.common.blockentity.tank.FluidTankBlockEntity;
import unhappycodings.thoriumreactors.common.registration.ModBlocks;
import unhappycodings.thoriumreactors.common.registration.ModItems;

import java.util.Arrays;
import java.util.List;

public class ItemConduitBlock extends BaseEntityBlock {
    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty EAST = BooleanProperty.create("east");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty WEST = BooleanProperty.create("west");
    public static final BooleanProperty UP = BooleanProperty.create("up");
    public static final BooleanProperty DOWN = BooleanProperty.create("down");
    public static final BooleanProperty TICKING = BooleanProperty.create("ticking");

    public static final VoxelShape SHAPE_CORE = Block.box(6, 6, 6, 10, 10, 10);
    public static final VoxelShape SHAPE_NORTH = Block.box(6, 6, 0, 10, 10, 6);
    public static final VoxelShape SHAPE_EAST = Block.box(10, 6, 6, 16, 10, 10);
    public static final VoxelShape SHAPE_SOUTH = Block.box(6, 6, 10, 10, 10, 16);
    public static final VoxelShape SHAPE_WEST = Block.box(0, 6, 6, 6, 10, 10);
    public static final VoxelShape SHAPE_UP = Block.box(6, 10, 6, 10, 16, 10);
    public static final VoxelShape SHAPE_DOWN = Block.box(6, 0, 6, 10, 6, 10);

    public ItemConduitBlock() {
        super(Properties.of(Material.STONE));
        this.registerDefaultState(this.stateDefinition.any().setValue(TICKING, false).setValue(NORTH, false).setValue(EAST, false).setValue(SOUTH, false).setValue(WEST, false).setValue(UP, false).setValue(DOWN, false));
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        if (pContext instanceof EntityCollisionContext) {
            EntityCollisionContext ctx = (EntityCollisionContext) pContext;
            if (ctx.getEntity() instanceof Player player) {
                if (player.level.isClientSide) {
                    Triple<VoxelShape, Direction, Double> values = getTargetShape(pState, pLevel, pPos, player);
                    if (values.b != null)
                        return values.a;
                }
            }
        }

        return Shapes.or(SHAPE_CORE, pState.getValue(NORTH) ? SHAPE_NORTH : Shapes.empty(),
                pState.getValue(EAST) ? SHAPE_EAST : Shapes.empty(),
                pState.getValue(SOUTH) ? SHAPE_SOUTH : Shapes.empty(),
                pState.getValue(WEST) ? SHAPE_WEST : Shapes.empty(),
                pState.getValue(UP) ? SHAPE_UP : Shapes.empty(),
                pState.getValue(DOWN) ? SHAPE_DOWN : Shapes.empty());
    }

    public Triple<VoxelShape, Direction, Double> getTargetShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, Player player) {
        Vec3 start = player.getEyePosition(1F);
        Vec3 end = start.add(player.getLookAngle().normalize().scale(getBlockReachDistance(player)));

        VoxelShape selection = null;
        Direction direction = null;
        double shortest = Double.MAX_VALUE;

        double d = checkShape(pState, pLevel, pPos, start, end, SHAPE_CORE, null);
        if (d < shortest) shortest = d;
        if (!(pLevel instanceof LevelAccessor)) return new Triple<>(selection, direction, d);

        for (int i = 0; i < Direction.values().length; i++) {
            Triple<VoxelShape, BooleanProperty, Direction> shape = SHAPES.get(i);
            d = checkShape(pState, pLevel, pPos, start, end, shape.a, shape.b);
            if (d < shortest) {
                shortest = d;
                selection = shape.a;
                direction = shape.c;
            }
        }

        return new Triple<>(selection, direction, shortest);
    }


    private static final List<Triple<VoxelShape, BooleanProperty, Direction>> SHAPES = Arrays.asList(
            new Triple<>(SHAPE_NORTH, NORTH, Direction.NORTH),
            new Triple<>(SHAPE_SOUTH, SOUTH, Direction.SOUTH),
            new Triple<>(SHAPE_WEST, WEST, Direction.WEST),
            new Triple<>(SHAPE_EAST, EAST, Direction.EAST),
            new Triple<>(SHAPE_UP, UP, Direction.UP),
            new Triple<>(SHAPE_DOWN, DOWN, Direction.DOWN)
    );

    private double checkShape(BlockState state, BlockGetter world, BlockPos pos, Vec3 start, Vec3 end, VoxelShape shape, BooleanProperty direction) {
        if (direction != null && !state.getValue(direction)) {
            return Double.MAX_VALUE;
        }
        BlockHitResult blockRayTraceResult = world.clipWithInteractionOverride(start, end, pos, shape, state);
        if (blockRayTraceResult == null) {
            return Double.MAX_VALUE;
        }
        return blockRayTraceResult.getLocation().distanceTo(start);
    }

    public float getBlockReachDistance(Player player) {
        float distance = (float) player.getAttribute(net.minecraftforge.common.ForgeMod.REACH_DISTANCE.get()).getValue();
        return player.isCreative() ? distance : distance - 0.5F;
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
        return this.defaultBlockState().setValue(NORTH, isRelative(pContext, Direction.NORTH)).setValue(TICKING, false).setValue(EAST, isRelative(pContext, Direction.EAST)).setValue(SOUTH, isRelative(pContext, Direction.SOUTH)).setValue(WEST, isRelative(pContext, Direction.WEST)).setValue(UP, isRelative(pContext, Direction.UP)).setValue(DOWN, isRelative(pContext, Direction.DOWN));
    }

    public boolean isRelative(BlockPlaceContext pContext, Direction direction) {
        return isRelative(pContext.getLevel(), pContext.getClickedPos(), direction);
    }

    public boolean isRelative(Level level, BlockPos pos, Direction direction) {
        BlockState state = level.getBlockState(pos.relative(direction));
        BlockEntity entity = level.getBlockEntity(pos.relative(direction));
        return state.is(ModBlocks.ITEM_CONDUIT_BLOCK.get()) || (entity != null && entity.getCapability(ForgeCapabilities.ITEM_HANDLER, direction).isPresent());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(NORTH, EAST, SOUTH, WEST, UP, DOWN, TICKING);
    }

    @SuppressWarnings("deprecation")
    @NotNull
    @Override
    public RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new ItemConduitBlockEntity(pos, state);
    }

    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState blockState, @NotNull BlockEntityType<T> type) {
        return level.isClientSide || !blockState.getValue(ItemConduitBlock.TICKING) ? null : (a, b, c, blockEntity) -> ((ItemConduitBlockEntity) blockEntity).tick();
    }

}
