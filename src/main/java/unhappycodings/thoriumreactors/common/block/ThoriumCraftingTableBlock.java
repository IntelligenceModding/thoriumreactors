package unhappycodings.thoriumreactors.common.block;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import unhappycodings.thoriumreactors.common.registration.ModBlockEntities;

import java.util.List;

public class ThoriumCraftingTableBlock extends BaseEntityBlock {
    public static final VoxelShape ALL = Shapes.or(Block.box(0, 12, 0, 16, 16, 16), Block.box(0, 0, 0, 3, 12, 3),
            Block.box(13, 0, 0, 16, 12, 3), Block.box(13, 0, 13, 16, 12, 16),
            Block.box(0, 0, 13, 3, 12, 16));

    public ThoriumCraftingTableBlock() {
        super(Properties.of(Material.WOOD));
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
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
    public void appendHoverText(ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        pTooltip.add(Component.literal("5x5 crafting area for most recipes."));
        pTooltip.add(Component.literal("Just Enough Items is highly recommended!").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.DARK_GRAY));
    }

    @NotNull
    @Override
    public RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return ModBlockEntities.CRAFTING_TABLE.get().create(pos, state);
    }
}
