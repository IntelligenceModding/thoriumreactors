package unhappycodings.thoriumreactors.common.block.turbine;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import unhappycodings.thoriumreactors.common.blockentity.turbine.TurbineControllerBlockEntity;
import unhappycodings.thoriumreactors.common.blockentity.turbine.base.TurbineFrameBlockEntity;
import unhappycodings.thoriumreactors.common.enums.ParticleTypeEnum;
import unhappycodings.thoriumreactors.common.multiblock.TurbineMultiblocks;
import unhappycodings.thoriumreactors.common.network.PacketHandler;
import unhappycodings.thoriumreactors.common.network.toclient.reactor.ClientReactorParticleDataPacket;
import unhappycodings.thoriumreactors.common.registration.ModBlocks;
import unhappycodings.thoriumreactors.common.registration.ModKeyBindings;
import unhappycodings.thoriumreactors.common.util.CalculationUtil;
import unhappycodings.thoriumreactors.common.util.FormattingUtil;

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
        super(Properties.of(Material.METAL).strength(5f));
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
        if (ModKeyBindings.SHOW_DESCRIPTION.isDown()) {
            pTooltip.add(Component.translatable(asBlock().getDescriptionId() + "_description").withStyle(ChatFormatting.GRAY));
        } else {
            pTooltip.add(Component.translatable(FormattingUtil.getTranslatable("machines.tooltip.hold")).withStyle(ChatFormatting.GRAY).append(Component.literal(ModKeyBindings.SHOW_DESCRIPTION.getKey().getDisplayName().getString()).withStyle(FormattingUtil.hex(0x55D38A))).append(Component.translatable(FormattingUtil.getTranslatable("machines.tooltip.for_description")).withStyle(ChatFormatting.GRAY)));
        }
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        BlockPos rotorPos = pos.relative(state.getValue(TurbineControllerBlock.FACING).getOpposite(), 2);

        for (int i = 0; i < 9; i++) {
            BlockPos loopPos = rotorPos.relative(Direction.UP, i);
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
        if (player.level.isClientSide || interactionHand == InteractionHand.OFF_HAND) return InteractionResult.SUCCESS;
        if (!entity.isAssembled()) {
            Direction facing = state.getValue(FACING);

            int turbineSize = 0;
            boolean canBeAssembled = false;
            for (int i = 8; i >= 5; i--) {
                List<Block> turbineBlocks = CalculationUtil.getBlocks(pos.relative(facing.getClockWise(), 2).relative(Direction.DOWN, 1), pos.relative(facing.getCounterClockWise(), 2).relative(facing.getOpposite(), 4).relative(Direction.UP, i), levelIn);

                if (TurbineMultiblocks.isTurbine(TurbineMultiblocks.getTurbineFromSize(i), turbineBlocks)) {
                    canBeAssembled = true;
                    turbineSize = i;
                    break;
                }
            }

            if (entity.isAssembled() != canBeAssembled) {
                List<BlockPos> turbinePositions = CalculationUtil.getBlockPositions(pos.relative(facing.getClockWise(), 2).relative(Direction.DOWN, 1), pos.relative(facing.getCounterClockWise(), 2).relative(facing.getOpposite(), 4).relative(Direction.UP, turbineSize), levelIn);

                int turbineCount = 0;
                for (BlockPos turbinePosition : turbinePositions) {
                    BlockState blockState = levelIn.getBlockState(turbinePosition);
                    if (blockState.is(ModBlocks.TURBINE_ROTOR.get())) {
                        if (blockState.getValue(TurbineRotorBlock.BLADES) == 8 || turbineCount < 2) {
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
                for (Player curPlayer : levelIn.players())
                    PacketHandler.sendToClient(new ClientReactorParticleDataPacket(addParticleOffset(pos, state.getValue(TurbineControllerBlock.FACING)), ParticleTypeEnum.TURBINE, 5, 9, 5), (ServerPlayer) curPlayer);
                entity.setTurbineHeight(turbineSize + 1);

                List<Block> turbineBlocks = CalculationUtil.getBlocks(pos.relative(facing.getClockWise(), 2).relative(Direction.DOWN, 1), pos.relative(facing.getCounterClockWise(), 2).relative(facing.getOpposite(), 4).relative(Direction.UP, turbineSize), levelIn);
                List<Block> moderatorBlocks = TurbineMultiblocks.getTurbineModeratorBLocks(TurbineMultiblocks.getTurbineFromSize(turbineSize), turbineBlocks);

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

            }
        } else {
            entity.setAssembled(false);
            entity.setCoilsEngaged(false);
            entity.setActivated(false);
            levelIn.setBlockAndUpdate(pos, state.setValue(POWERED, false));

        }
        return InteractionResult.CONSUME;
    }

    public BlockPos addParticleOffset(BlockPos pos, Direction direction) {
        return switch (direction) {
            case WEST -> pos.offset(0, -1, -2);
            case EAST -> pos.offset(-4, -1, -2);
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
