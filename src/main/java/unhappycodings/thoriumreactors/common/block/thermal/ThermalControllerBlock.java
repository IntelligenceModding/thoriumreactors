package unhappycodings.thoriumreactors.common.block.thermal;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.*;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import unhappycodings.thoriumreactors.common.block.thermal.base.ThermalFrameBlock;
import unhappycodings.thoriumreactors.common.blockentity.thermal.ThermalControllerBlockEntity;
import unhappycodings.thoriumreactors.common.blockentity.thermal.base.ThermalFrameBlockEntity;
import unhappycodings.thoriumreactors.common.enums.ParticleTypeEnum;
import unhappycodings.thoriumreactors.common.multiblock.ThermalMultiblocks;
import unhappycodings.thoriumreactors.common.network.PacketHandler;
import unhappycodings.thoriumreactors.common.network.toclient.reactor.ClientReactorParticleDataPacket;
import unhappycodings.thoriumreactors.common.registration.ModBlocks;
import unhappycodings.thoriumreactors.common.util.CalculationUtil;

import java.util.ArrayList;
import java.util.List;

public class ThermalControllerBlock extends ThermalFrameBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    public ThermalControllerBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(5f));
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(POWERED, false));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite()).setValue(POWERED, false);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, POWERED);
    }

    @SuppressWarnings("deprecation")
    @NotNull
    @Override
    public InteractionResult use(@NotNull BlockState state, @NotNull Level levelIn, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand interactionHand, @NotNull BlockHitResult hitResult) {
        ThermalControllerBlockEntity entity = (ThermalControllerBlockEntity) levelIn.getBlockEntity(pos);
        if (player.level().isClientSide) return InteractionResult.SUCCESS;
        if (!entity.isAssembled()) {
            Direction facing = state.getValue(FACING);
            boolean canBeAssembled = false;
            List<Block> thermalBlocks = CalculationUtil.getBlocks(pos.relative(facing.getClockWise(), 2).relative(Direction.DOWN, 1), pos.relative(facing.getCounterClockWise(), 2).relative(facing.getOpposite(), 2).relative(Direction.UP, 1), levelIn);

            if (ThermalMultiblocks.isThermal(ThermalMultiblocks.getThermalFromDirection(facing), thermalBlocks))
                canBeAssembled = true;

            if (entity.isAssembled() != canBeAssembled) {
                entity.valvePos = new ArrayList<>(4);
                List<BlockPos> thermalPositions = CalculationUtil.getBlockPositions(pos.relative(facing.getClockWise(), 2).relative(Direction.DOWN, 1), pos.relative(facing.getCounterClockWise(), 2).relative(facing.getOpposite(), 2).relative(Direction.UP, 1), levelIn);
                for (BlockPos turbinePosition : thermalPositions) {
                    if (levelIn.getBlockEntity(turbinePosition) instanceof ThermalFrameBlockEntity thermalFrameBlockEntity)
                        thermalFrameBlockEntity.setControllerPos(pos);
                    if (levelIn.getBlockState(turbinePosition).is(ModBlocks.THERMAL_VALVE.get()))
                        entity.valvePos.add(turbinePosition);
                }

                if (entity.valvePos.size() != 4) return InteractionResult.CONSUME;

                entity.setAssembled(canBeAssembled);
                levelIn.setBlockAndUpdate(pos, state.setValue(POWERED, canBeAssembled));

                long x = facing == Direction.WEST || facing == Direction.EAST ? 3 : 5, y = 3;
                if (x == 3) y = 5;
                for (Player loopPlayer : levelIn.players()) {
                    PacketHandler.sendToClient(new ClientReactorParticleDataPacket(addParticleOffset(pos.relative(Direction.DOWN, 1), state.getValue(ThermalControllerBlock.FACING)), ParticleTypeEnum.REACTOR, x, 3, y), (ServerPlayer) loopPlayer);
                }
            }
        }
        return InteractionResult.CONSUME;
    }

    public BlockPos addParticleOffset(BlockPos pos, Direction direction) {
        return switch (direction) {
            case WEST -> pos.offset(0, 0, -2);
            case EAST -> pos.offset(-2, 0, -2);
            case SOUTH -> pos.offset(-2, 0, -2);
            default -> pos.offset(-2, 0, 0);
        };
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new ThermalControllerBlockEntity(pos, state);
    }

    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState blockState, @NotNull BlockEntityType<T> type) {
        if (level.isClientSide || !blockState.getValue(ThermalControllerBlock.POWERED)) return null;
        return (a, b, c, blockEntity) -> ((ThermalControllerBlockEntity) blockEntity).tick();
    }

}
