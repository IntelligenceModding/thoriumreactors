package unhappycodings.thoriumreactors.common.block.machine;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
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
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import unhappycodings.thoriumreactors.common.blockentity.machine.MachineElectrolyticSaltSeparatorBlockEntity;
import unhappycodings.thoriumreactors.common.registration.ModBlockEntities;
import unhappycodings.thoriumreactors.common.registration.ModKeyBindings;
import unhappycodings.thoriumreactors.common.util.FormattingUtil;
import unhappycodings.thoriumreactors.common.util.ParticleUtil;

import java.util.List;

public class MachineElectrolyticSaltSeparatorBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    public MachineElectrolyticSaltSeparatorBlock() {
        super(Properties.of(Material.STONE).strength(5f));
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(POWERED, false));
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
        return state.getValue(POWERED) ? 6 : 0;
    }

    @Override
    public void animateTick(BlockState pState, @NotNull Level level, @NotNull BlockPos pos, @NotNull RandomSource randomSource) {
        if (pState.getValue(POWERED)) {
            ParticleUtil.renderSmokeParticles(pos, randomSource, level);
            CompoundTag tag = level.getBlockEntity(pos).saveWithFullMetadata();
            if (tag.getBoolean("InputDump") || tag.getBoolean("OutputDump"))
                ParticleUtil.renderParticles(pos, randomSource, level, ParticleTypes.DOLPHIN);
        }
    }


    @SuppressWarnings("deprecation")
    @NotNull
    @Override
    public InteractionResult use(@NotNull BlockState state, @NotNull Level levelIn, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand interactionHand, @NotNull BlockHitResult hitResult) {
        MenuProvider namedContainerProvider = this.getMenuProvider(state, levelIn, pos);
        if (namedContainerProvider != null) {
            if (player instanceof ServerPlayer serverPlayerEntity)
                NetworkHooks.openScreen(serverPlayerEntity, namedContainerProvider, pos);
            return InteractionResult.SUCCESS;
        }

        return super.use(state, levelIn, pos, player, interactionHand, hitResult);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite()).setValue(POWERED, false);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable BlockGetter pLevel, @NotNull List<Component> pTooltip, @NotNull TooltipFlag pFlag) {
        CompoundTag tag = pStack.getOrCreateTag().getCompound("BlockEntityTag");
        if (tag.get("RecipeTime") == null) {
            pTooltip.add(Component.literal("Not Placed Yet").withStyle(FormattingUtil.hex(0xCE1F0A)));
            return;
        }
        if (ModKeyBindings.SHOW_DETAILS.isDown()) {
            pTooltip.add(Component.literal("Energy Buffer: ").withStyle(FormattingUtil.hex(0x3FD023)).append(Component.literal(FormattingUtil.formatEnergy(Integer.parseInt(String.valueOf(tag.get("Energy"))))).withStyle(ChatFormatting.GRAY)));
            pTooltip.add(Component.literal("Recipe status: ").withStyle(FormattingUtil.hex(0x3BA3D3)).append(Component.literal("Electrolysis " + FormattingUtil.formatPercentNum(tag.getInt("RecipeTime"), tag.getInt("MaxRecipeTime"))).withStyle(ChatFormatting.GRAY)));

            if (!tag.getCompound("FluidIn").isEmpty()) {
                FluidStack fluidIn = FluidStack.loadFluidStackFromNBT(tag.getCompound("FluidIn"));
                pTooltip.add(fluidIn.isEmpty() ? Component.literal("Empty").withStyle(FormattingUtil.hex(0x0ACECE)) : Component.literal(fluidIn.getFluid().getFluidType().getDescription().getString() + ": ").withStyle(FormattingUtil.hex(0x0ACECE)).append(Component.literal(fluidIn.getAmount() + " ").withStyle(ChatFormatting.GRAY)).append(Component.literal("mb").withStyle(FormattingUtil.hex(0x0ACECE))));
            }
            if (!tag.getCompound("FluidOut").isEmpty()) {
                FluidStack fluidOut = FluidStack.loadFluidStackFromNBT(tag.getCompound("FluidOut"));
                pTooltip.add(fluidOut.isEmpty() ? Component.literal("Empty").withStyle(FormattingUtil.hex(0x80ebff)) : Component.literal(fluidOut.getFluid().getFluidType().getDescription().getString() + ": ").withStyle(FormattingUtil.hex(0x80ebff)).append(Component.literal(fluidOut.getAmount() + " ").withStyle(ChatFormatting.GRAY)).append(Component.literal("mb").withStyle(FormattingUtil.hex(0x80ebff))));
            }

            ListTag listtag = tag.getList("Items", 10);
            if (!listtag.isEmpty()) {
                pTooltip.add(Component.literal("Inventory contains items!").withStyle(ChatFormatting.GRAY));
            }
        } else if (ModKeyBindings.SHOW_DESCRIPTION.isDown()) {
            pTooltip.add(Component.translatable(asBlock().getDescriptionId() + "_description").withStyle(ChatFormatting.GRAY));
        } else {
            pTooltip.add(Component.literal("Hold ").withStyle(ChatFormatting.GRAY).append(Component.literal(ModKeyBindings.SHOW_DETAILS.getKey().getDisplayName().getString()).withStyle(FormattingUtil.hex(0x7ED355))).append(Component.literal(" for further details.").withStyle(ChatFormatting.GRAY)));
            pTooltip.add(Component.literal("Hold ").withStyle(ChatFormatting.GRAY).append(Component.literal(ModKeyBindings.SHOW_DESCRIPTION.getKey().getDisplayName().getString()).withStyle(FormattingUtil.hex(0x55D38A))).append(Component.literal(" for a block description.").withStyle(ChatFormatting.GRAY)));
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, POWERED);
    }

    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState blockState, @NotNull BlockEntityType<T> type) {
        return level.isClientSide ? null : (a, b, c, blockEntity) -> ((MachineElectrolyticSaltSeparatorBlockEntity) blockEntity).tick();
    }

    @SuppressWarnings("deprecation")
    @NotNull
    @Override
    public RenderShape getRenderShape(@NotNull BlockState pState) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return ModBlockEntities.ELECTROLYTIC_SALT_SEPARATOR_BLOCK.get().create(pos, state);
    }
}
