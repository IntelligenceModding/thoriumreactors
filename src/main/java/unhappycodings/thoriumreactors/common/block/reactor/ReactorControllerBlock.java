package unhappycodings.thoriumreactors.common.block.reactor;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import unhappycodings.thoriumreactors.common.block.reactor.base.ReactorFrameBlock;
import unhappycodings.thoriumreactors.common.block.thermal.ThermalControllerBlock;
import unhappycodings.thoriumreactors.common.blockentity.reactor.ReactorControllerBlockEntity;
import unhappycodings.thoriumreactors.common.blockentity.reactor.base.ReactorFrameBlockEntity;
import unhappycodings.thoriumreactors.common.enums.ParticleTypeEnum;
import unhappycodings.thoriumreactors.common.multiblock.ReactorMultiblocks;
import unhappycodings.thoriumreactors.common.network.PacketHandler;
import unhappycodings.thoriumreactors.common.network.toclient.reactor.ClientReactorParticleDataPacket;
import unhappycodings.thoriumreactors.common.registration.ModBlocks;
import unhappycodings.thoriumreactors.common.util.CalculationUtil;

import java.util.ArrayList;
import java.util.List;

public class ReactorControllerBlock extends ReactorFrameBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final BooleanProperty SCRAMMED = BooleanProperty.create("scrammed");

    public ReactorControllerBlock() {
        super(Properties.of(Material.METAL).strength(5f));
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(POWERED, false).setValue(SCRAMMED, false));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite()).setValue(POWERED, false).setValue(SCRAMMED, false);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, POWERED, SCRAMMED);
    }

    @SuppressWarnings("deprecation")
    @NotNull
    @Override
    public InteractionResult use(@NotNull BlockState state, @NotNull Level levelIn, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand interactionHand, @NotNull BlockHitResult hitResult) {

        ReactorControllerBlockEntity entity = (ReactorControllerBlockEntity) levelIn.getBlockEntity(pos);
        if (player.level.isClientSide) return InteractionResult.SUCCESS;
        if (!entity.isAssembled()) {
            Direction facing = state.getValue(FACING);
            boolean canBeAssembled = false;
            int height = 1;

            for (int i = 4; i <= 6; i++) {
                List<Block> reactorBlocks = CalculationUtil.getBlocks(pos.relative(facing.getClockWise(), 2).relative(Direction.DOWN, 1), pos.relative(facing.getCounterClockWise(), 2).relative(facing.getOpposite(), 4).relative(Direction.UP, i - 2), levelIn);

                if (ReactorMultiblocks.isReactor(ReactorMultiblocks.getReactorFromHeight(i), reactorBlocks)) {
                    canBeAssembled = true;
                    height = i - 1;
                }
            }

            if (entity.isAssembled() != canBeAssembled) {
                entity.valvePos = new ArrayList<>(4);
                List<BlockPos> reactorPositions = CalculationUtil.getBlockPositions(pos.relative(facing.getClockWise(), 2).relative(Direction.DOWN, 1), pos.relative(facing.getCounterClockWise(), 2).relative(facing.getOpposite(), 4).relative(Direction.UP, 4), levelIn);
                for (BlockPos reactorPosition : reactorPositions) {
                    if (levelIn.getBlockEntity(reactorPosition) instanceof ReactorFrameBlockEntity reactorFrameBlockEntity)
                        reactorFrameBlockEntity.setControllerPos(pos);
                    if (levelIn.getBlockState(reactorPosition).is(ModBlocks.REACTOR_VALVE.get()))
                        entity.valvePos.add(reactorPosition);
                }

                if (entity.valvePos.size() != 4) return InteractionResult.CONSUME;

                entity.setAssembled(canBeAssembled);
                entity.setReactorHeight(height);
                entity.setReactorCapacity((3 * 3 * (entity.getReactorHeight() - 1) * 1000) - 1000);
                levelIn.setBlockAndUpdate(pos, state.setValue(POWERED, canBeAssembled));

                long x = facing == Direction.WEST || facing == Direction.EAST ? 3 : 5, y = 3;
                if (x == 3) y = 5;
                for (Player loopPlayer : levelIn.players()) {
                    PacketHandler.sendToClient(new ClientReactorParticleDataPacket(addParticleOffset(pos, state.getValue(ReactorControllerBlock.FACING)), ParticleTypeEnum.REACTOR, x, height, y), (ServerPlayer) loopPlayer);
                }
            }
        } else {
            MenuProvider namedContainerProvider = this.getMenuProvider(state, levelIn, pos);

            if (namedContainerProvider != null) {
                if (player instanceof ServerPlayer serverPlayerEntity)
                    NetworkHooks.openScreen(serverPlayerEntity, namedContainerProvider, pos);
                return InteractionResult.SUCCESS;
            }
        }
        return super.use(state, levelIn, pos, player, interactionHand, hitResult);
    }

    public BlockPos addParticleOffset(BlockPos pos, Direction direction) {
        return switch (direction) {
            case WEST -> pos.offset(0, -1, -2);
            case EAST -> pos.offset(-2, -1, -2);
            case SOUTH -> pos.offset(-2, -1, -2);
            default -> pos.offset(-2, -1, 0);
        };
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new ReactorControllerBlockEntity(pos, state);
    }

    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState blockState, @NotNull BlockEntityType<T> type) {
        if (level.isClientSide || !blockState.getValue(ThermalControllerBlock.POWERED)) return null;
        return (a, b, c, blockEntity) -> ((ReactorControllerBlockEntity) blockEntity).tick();
    }

}
