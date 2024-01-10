package unhappycodings.thoriumreactors.common.block;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import unhappycodings.thoriumreactors.client.config.ClientConfig;
import unhappycodings.thoriumreactors.common.blockentity.ThoriumCraftingTableBlockEntity;
import unhappycodings.thoriumreactors.common.registration.ModKeyBindings;
import unhappycodings.thoriumreactors.common.util.FormattingUtil;
import unhappycodings.thoriumreactors.common.util.KeyBindingUtil;
import unhappycodings.thoriumreactors.common.util.LootUtil;

import java.util.Collections;
import java.util.List;

public class ThoriumCraftingTableBlock extends BaseEntityBlock {
    public static final VoxelShape ALL = Shapes.or(Block.box(0, 12, 0, 16, 16, 16), Block.box(0, 0, 0, 3, 12, 3),
            Block.box(13, 0, 0, 16, 12, 3), Block.box(13, 0, 13, 16, 12, 16),
            Block.box(0, 0, 13, 3, 12, 16));

    public ThoriumCraftingTableBlock() {
        super(Properties.copy(Blocks.STONE).strength(5f));
    }

    @SuppressWarnings("deprecation")
    @NotNull
    @Override
    public List<ItemStack> getDrops(@NotNull BlockState pState, LootParams.@NotNull Builder pParams) {
        return Collections.singletonList(LootUtil.getLoot(pParams.getParameter(LootContextParams.BLOCK_ENTITY), this));
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        return LootUtil.getLoot(level.getBlockEntity(pos), this);
    }

    @SuppressWarnings("deprecation")
    @NotNull
    @Override
    public VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        return ALL;
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
    public RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new ThoriumCraftingTableBlockEntity(pos, state);
    }
}
