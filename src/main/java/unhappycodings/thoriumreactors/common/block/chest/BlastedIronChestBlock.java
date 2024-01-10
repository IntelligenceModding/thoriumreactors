package unhappycodings.thoriumreactors.common.block.chest;

import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import unhappycodings.thoriumreactors.client.config.ClientConfig;
import unhappycodings.thoriumreactors.common.blockentity.chest.BlastedIronChestBlockEntity;
import unhappycodings.thoriumreactors.common.blockentity.turbine.TurbineControllerBlockEntity;
import unhappycodings.thoriumreactors.common.registration.ModBlockEntities;
import unhappycodings.thoriumreactors.common.registration.ModKeyBindings;
import unhappycodings.thoriumreactors.common.util.FormattingUtil;
import unhappycodings.thoriumreactors.common.util.KeyBindingUtil;
import unhappycodings.thoriumreactors.common.util.LootUtil;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class BlastedIronChestBlock extends BaseEntityBlock {
    protected static final VoxelShape AABB = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 14.0D, 15.0D);
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public BlastedIronChestBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    public static DoubleBlockCombiner.Combiner<BlastedIronChestBlockEntity, Float2FloatFunction> opennessCombiner(final LidBlockEntity pLid) {
        return new DoubleBlockCombiner.Combiner<BlastedIronChestBlockEntity, Float2FloatFunction>() {
            public Float2FloatFunction acceptDouble(BlastedIronChestBlockEntity p_51633_, BlastedIronChestBlockEntity p_51634_) {
                return (p_51638_) -> {
                    return Math.max(p_51633_.getOpenNess(p_51638_), p_51634_.getOpenNess(p_51638_));
                };
            }

            public Float2FloatFunction acceptSingle(BlastedIronChestBlockEntity p_51631_) {
                Objects.requireNonNull(p_51631_);
                return p_51631_::getOpenNess;
            }

            public Float2FloatFunction acceptNone() {
                LidBlockEntity var10000 = pLid;
                Objects.requireNonNull(var10000);
                return var10000::getOpenNess;
            }
        };
    }

    @SuppressWarnings("deprecation")
    @NotNull
    @Override
    public List<ItemStack> getDrops(@NotNull BlockState pState, LootContext.Builder pBuilder) {
        return Collections.singletonList(LootUtil.getLoot(pBuilder.getParameter(LootContextParams.BLOCK_ENTITY), this));
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        return LootUtil.getLoot(level.getBlockEntity(pos), this);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable BlockGetter pLevel, @NotNull List<Component> pTooltip, @NotNull TooltipFlag pFlag) {
        CompoundTag tag = pStack.getOrCreateTag().getCompound("BlockEntityTag");
        if (KeyBindingUtil.isKeyPressed(ModKeyBindings.SHOW_DETAILS) && ClientConfig.showBlockDetails.get()) {
            ListTag listtag = tag.getList("Items", 10);
            if (!listtag.isEmpty()) {
                pTooltip.add(Component.translatable(FormattingUtil.getTranslatable("machines.tooltip.contains_items")).withStyle(ChatFormatting.GRAY));
            }
        } else if (KeyBindingUtil.isKeyPressed(ModKeyBindings.SHOW_DESCRIPTION) && ClientConfig.showBlockDescription.get()) {
            pTooltip.add(Component.translatable(asBlock().getDescriptionId() + "_description").withStyle(ChatFormatting.GRAY));
        } else {
            if (ClientConfig.showBlockDetails.get())
                pTooltip.add(Component.translatable(FormattingUtil.getTranslatable("machines.tooltip.hold")).withStyle(ChatFormatting.GRAY).append(Component.literal(ModKeyBindings.SHOW_DETAILS.getKey().getDisplayName().getString()).withStyle(FormattingUtil.hex(0x7ED355))).append(Component.translatable(FormattingUtil.getTranslatable("machines.tooltip.for_details")).withStyle(ChatFormatting.GRAY)));
            if (ClientConfig.showBlockDescription.get())
                pTooltip.add(Component.translatable(FormattingUtil.getTranslatable("machines.tooltip.hold")).withStyle(ChatFormatting.GRAY).append(Component.literal(ModKeyBindings.SHOW_DESCRIPTION.getKey().getDisplayName().getString()).withStyle(FormattingUtil.hex(0x55D38A))).append(Component.translatable(FormattingUtil.getTranslatable("machines.tooltip.for_description")).withStyle(ChatFormatting.GRAY)));
        }
    }

    @SuppressWarnings("deprecation")
    @NotNull
    @Override
    public RenderShape getRenderShape(@NotNull BlockState pState) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @SuppressWarnings("deprecation")
    @NotNull
    @Override
    public InteractionResult use(@NotNull BlockState state, @NotNull Level levelIn, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand interactionHand, @NotNull BlockHitResult hitResult) {
        MenuProvider namedContainerProvider = this.getMenuProvider(state, levelIn, pos);
        if (namedContainerProvider != null) {
            if (player instanceof ServerPlayer serverPlayerEntity)
                NetworkHooks.openScreen(serverPlayerEntity, namedContainerProvider, pos);
            PiglinAi.angerNearbyPiglins(player, true);
            return InteractionResult.SUCCESS;
        }

        return super.use(state, levelIn, pos, player, interactionHand, hitResult);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }

    @SuppressWarnings("deprecation")
    @NotNull
    public VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        return AABB;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return ModBlockEntities.BLASTED_IRON_CHEST_BLOCK.get().create(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return pLevel instanceof ServerLevel ? null : (a, b, c, blockEntity) -> ((BlastedIronChestBlockEntity) blockEntity).tick();
    }
}