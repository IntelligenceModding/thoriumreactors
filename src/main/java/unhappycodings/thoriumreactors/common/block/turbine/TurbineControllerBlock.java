package unhappycodings.thoriumreactors.common.block.turbine;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import unhappycodings.thoriumreactors.client.config.ClientConfig;
import unhappycodings.thoriumreactors.common.blockentity.turbine.TurbineControllerBlockEntity;
import unhappycodings.thoriumreactors.common.blockentity.turbine.base.TurbineFrameBlockEntity;
import unhappycodings.thoriumreactors.common.enums.ParticleType;
import unhappycodings.thoriumreactors.common.multiblock.TurbineMultiblocks;
import unhappycodings.thoriumreactors.common.network.PacketHandler;
import unhappycodings.thoriumreactors.common.network.toclient.reactor.ClientReactorParticleDataPacket;
import unhappycodings.thoriumreactors.common.registration.ModBlocks;
import unhappycodings.thoriumreactors.common.registration.ModKeyBindings;
import unhappycodings.thoriumreactors.common.util.AdvancementUtil;
import unhappycodings.thoriumreactors.common.util.CalculationUtil;
import unhappycodings.thoriumreactors.common.util.FormattingUtil;
import unhappycodings.thoriumreactors.common.util.KeyBindingUtil;

import java.util.List;

public class TurbineControllerBlock extends BaseEntityBlock {
    private static final float NICKEL_MODIFIER = 1f;
    private static final float NIOB_MODIFIER = 1.5f;
    private static final float MOLYBDENUM_MODIFIER = 2f;

    private static final float NICKEL_BLOCK_VALUE = NICKEL_MODIFIER / 8;
    private static final float NIOB_BLOCK_VALUE = NIOB_MODIFIER / 8;
    private static final float MOLYBDENUM_BLOCK_VALUE = MOLYBDENUM_MODIFIER / 8;
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    public TurbineControllerBlock() {
        super(Properties.copy(Blocks.IRON_BLOCK).strength(5f));
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

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable BlockGetter pLevel, @NotNull List<Component> pTooltip, @NotNull TooltipFlag pFlag) {
        if (KeyBindingUtil.isKeyPressed(ModKeyBindings.SHOW_DESCRIPTION) && ClientConfig.showBlockDescription.get()) {
            pTooltip.add(Component.translatable(asBlock().getDescriptionId() + "_description").withStyle(ChatFormatting.GRAY));
        } else {
            if (ClientConfig.showBlockDescription.get())
                pTooltip.add(Component.translatable(FormattingUtil.getTranslatable("machines.tooltip.hold")).withStyle(ChatFormatting.GRAY).append(Component.literal(ModKeyBindings.SHOW_DESCRIPTION.getKey().getDisplayName().getString()).withStyle(FormattingUtil.hex(0x55D38A))).append(Component.translatable(FormattingUtil.getTranslatable("machines.tooltip.for_description")).withStyle(ChatFormatting.GRAY)));
        }
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        BlockPos rotorPos = pos.relative(state.getValue(TurbineControllerBlock.FACING).getOpposite(), 2);
        boolean horizontal = false;
        if (level.getBlockState(pos.relative(Direction.UP, 1)).is(ModBlocks.TURBINE_ROTATION_MOUNT.get())) {
            rotorPos = pos;
            horizontal = true;
        }

        for (int i = 0; i < 9; i++) {
            BlockPos loopPos = rotorPos.relative(horizontal ? state.getValue(TurbineControllerBlock.FACING) : Direction.UP, i);
            BlockState loopState = level.getBlockState(loopPos);
            if (loopState.is(ModBlocks.TURBINE_ROTOR.get())) {
                level.setBlockAndUpdate(loopPos, loopState.setValue(TurbineRotorBlock.RENDERING, false));
            }
        }

        return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
    }

    @SuppressWarnings("deprecation")
    @NotNull
    @Override
    public InteractionResult use(@NotNull BlockState state, @NotNull Level levelIn, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand interactionHand, @NotNull BlockHitResult hitResult) {
        TurbineControllerBlockEntity entity = (TurbineControllerBlockEntity) levelIn.getBlockEntity(pos);
        if (player.level().isClientSide || interactionHand == InteractionHand.OFF_HAND) return InteractionResult.SUCCESS;
        if (!entity.isAssembled()) {
            Direction facing = state.getValue(FACING);

            int turbineSize = 0;
            boolean canBeAssembled = false;
            boolean horizontal = false;
            for (int i = 8; i >= 5; i--) {
                List<Block> turbineBlocks = CalculationUtil.getBlocks(pos.relative(facing.getClockWise(), 2).relative(Direction.DOWN, 1), pos.relative(facing.getCounterClockWise(), 2).relative(facing.getOpposite(), 4).relative(Direction.UP, i), levelIn);

                if (TurbineMultiblocks.isTurbine(TurbineMultiblocks.getTurbineFromSize(i), turbineBlocks)) {
                    canBeAssembled = true;
                    turbineSize = i;
                    break;
                }
            }

            if (!canBeAssembled) {
                for (int i = 8; i >= 5; i--) {
                    List<Block> turbineBlocks = CalculationUtil.getBlocks(pos.relative(facing.getClockWise(), 2).relative(Direction.DOWN, 1), pos.relative(facing.getCounterClockWise(), 2).relative(facing.getOpposite(), i + 1).relative(Direction.UP, 3), levelIn);

                    if (TurbineMultiblocks.isTurbine(TurbineMultiblocks.getHorizontalTurbineFromSize(i, facing), turbineBlocks)) {
                        canBeAssembled = true;
                        turbineSize = i;
                        horizontal = true;
                        break;
                    }
                }
            }

            if (entity.isAssembled() != canBeAssembled) {
                List<BlockPos> turbinePositions;
                List<Block> turbineBlocks;

                if (!horizontal) {
                    turbinePositions = CalculationUtil.getBlockPositions(pos.relative(facing.getClockWise(), 2).relative(Direction.DOWN, 1), pos.relative(facing.getCounterClockWise(), 2).relative(facing.getOpposite(), 4).relative(Direction.UP, turbineSize), levelIn);
                    turbineBlocks = CalculationUtil.getBlocks(pos.relative(facing.getClockWise(), 2).relative(Direction.DOWN, 1), pos.relative(facing.getCounterClockWise(), 2).relative(facing.getOpposite(), 4).relative(Direction.UP, turbineSize), levelIn);
                } else {
                    turbinePositions = CalculationUtil.getBlockPositions(pos.relative(facing.getClockWise(), 2).relative(Direction.DOWN, 1), pos.relative(facing.getCounterClockWise(), 2).relative(facing.getOpposite(), turbineSize + 1).relative(Direction.UP, 3), levelIn);
                    turbineBlocks = CalculationUtil.getBlocks(pos.relative(facing.getClockWise(), 2).relative(Direction.DOWN, 1), pos.relative(facing.getCounterClockWise(), 2).relative(facing.getOpposite(), turbineSize + 1).relative(Direction.UP, 3), levelIn);
                }

                int turbineCount = 0;
                for (BlockPos turbinePosition : turbinePositions) {
                    BlockState blockState = levelIn.getBlockState(turbinePosition);
                    if (blockState.is(ModBlocks.TURBINE_ROTOR.get())) {
                        if (blockState.getValue(TurbineRotorBlock.BLADES) == 8 || (!horizontal || (facing == Direction.WEST || facing == Direction.NORTH ? turbineCount >= turbineSize - 2 : turbineCount < 2))) {
                            levelIn.setBlockAndUpdate(turbinePosition, blockState.setValue(TurbineRotorBlock.RENDERING, true));
                        } else {
                            return InteractionResult.FAIL;
                        }
                        turbineCount++;
                    }
                    if (blockState.is(ModBlocks.TURBINE_VALVE.get())) entity.setValvePos(turbinePosition);
                    if (blockState.is(ModBlocks.TURBINE_POWER_PORT.get())) entity.setPowerPortPos(turbinePosition);

                    if (levelIn.getBlockEntity(turbinePosition) instanceof TurbineFrameBlockEntity turbineFrameBlock) {
                        turbineFrameBlock.setControllerPos(pos);
                    }
                }

                entity.setAssembled(canBeAssembled);
                levelIn.setBlockAndUpdate(pos, state.setValue(POWERED, canBeAssembled));

                if (facing == Direction.SOUTH)
                    pos = pos.relative(facing.getOpposite(), turbineSize - 3);
                for (Player curPlayer : levelIn.players())
                    PacketHandler.sendToClient(new ClientReactorParticleDataPacket(addParticleOffset(pos, state.getValue(TurbineControllerBlock.FACING), horizontal, turbineSize), ParticleType.TURBINE, facing == Direction.EAST ||facing == Direction.WEST ? (horizontal ? turbineCount + 2 : 5) : 5, 5, facing == Direction.NORTH ||facing == Direction.SOUTH ? (horizontal ? turbineCount + 2 : 5) : 5), (ServerPlayer) curPlayer);
                entity.setTurbineHeight(turbineSize + 1);

                List<Block> moderatorBlocks = TurbineMultiblocks.getTurbineModeratorBLocks(TurbineMultiblocks.getHorizontalTurbineFromSize(turbineSize, facing), turbineBlocks);

                float moderatorModifier = 0f;
                for (Block moderatorBlock : moderatorBlocks) {
                    if (moderatorBlock.getStateDefinition().any().is(ModBlocks.NICKEL_BLOCK.get())) {
                        moderatorModifier += NICKEL_BLOCK_VALUE;
                    } else if (moderatorBlock.getStateDefinition().any().is(ModBlocks.NIOB_BLOCK.get())) {
                        moderatorModifier += NIOB_BLOCK_VALUE;
                    } else if (moderatorBlock.getStateDefinition().any().is(ModBlocks.MOLYBDENUM_BLOCK.get())) {
                        moderatorModifier += MOLYBDENUM_BLOCK_VALUE;
                    }
                }

                entity.setEnergyModifier(moderatorModifier);

                if (!levelIn.isClientSide)
                    AdvancementUtil.awardAdvancement((ServerLevel) levelIn, (ServerPlayer) player, "turbine_assembled", "impossible");

            }
        } else {
            entity.setAssembled(false);
            entity.setCoilsEngaged(false);
            entity.setActivated(false);
            levelIn.setBlockAndUpdate(pos, state.setValue(POWERED, false));

        }
        return InteractionResult.CONSUME;
    }

    public BlockPos addParticleOffset(BlockPos pos, Direction direction, boolean horizontal, int turbineSize) {
        return switch (direction) {
            case WEST -> pos.offset(0, -1, -2);
            case EAST -> horizontal ? pos.offset(-turbineSize - 1, -1, -2) : pos.offset(-4, -1, -2);
            case SOUTH -> pos.offset(-2, -1, -4);
            default -> pos.offset(-2, -1, 0);
        };
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
        return new TurbineControllerBlockEntity(pos, state);
    }

    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState blockState, @NotNull BlockEntityType<T> type) {
        if (level.isClientSide || !blockState.getValue(TurbineControllerBlock.POWERED)) return null;
        return (a, b, c, blockEntity) -> ((TurbineControllerBlockEntity) blockEntity).tick();
    }

}
