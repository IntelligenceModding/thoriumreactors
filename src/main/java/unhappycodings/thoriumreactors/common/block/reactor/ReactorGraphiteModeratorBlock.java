package unhappycodings.thoriumreactors.common.block.reactor;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import unhappycodings.thoriumreactors.common.registration.ModFluids;
import unhappycodings.thoriumreactors.common.registration.ModItems;
import unhappycodings.thoriumreactors.common.registration.ModKeyBindings;
import unhappycodings.thoriumreactors.common.util.FormattingUtil;

import java.util.List;
import java.util.Optional;

public class ReactorGraphiteModeratorBlock extends Block implements BucketPickup, LiquidBlockContainer {
    public static final BooleanProperty SALTLOGGED = BooleanProperty.create("saltlogged");
    public static final VoxelShape ALL = Block.box(4, 0, 4, 11.5, 16, 11.5);

    public ReactorGraphiteModeratorBlock() {
        super(Properties.of(Material.STONE).strength(5f));
        this.registerDefaultState(this.defaultBlockState().setValue(SALTLOGGED, false));
    }

    @Override
    public boolean canPlaceLiquid(@NotNull BlockGetter pLevel, @NotNull BlockPos pPos, BlockState pState, @NotNull Fluid pFluid) {
        return !pState.getValue(SALTLOGGED) && pFluid == ModFluids.SOURCE_MOLTEN_SALT.get();
    }

    @Override
    public boolean placeLiquid(@NotNull LevelAccessor pLevel, @NotNull BlockPos pPos, BlockState pState, @NotNull FluidState pFluidState) {
        if (!pState.getValue(SALTLOGGED) && pFluidState.getType() == ModFluids.SOURCE_MOLTEN_SALT.get()) {
            if (!pLevel.isClientSide()) {
                pLevel.setBlock(pPos, pState.setValue(SALTLOGGED,true), 3);
                pLevel.scheduleTick(pPos, pFluidState.getType(), pFluidState.getType().getTickDelay(pLevel));
            }
            return true;
        } else {
            return false;
        }
    }

    @NotNull
    @Override
    public ItemStack pickupBlock(@NotNull LevelAccessor pLevel, @NotNull BlockPos pPos, BlockState pState) {
        if (pState.getValue(SALTLOGGED)) {
            pLevel.setBlock(pPos, pState.setValue(SALTLOGGED, false), 3);
            if (!pState.canSurvive(pLevel, pPos)) {
                pLevel.destroyBlock(pPos, true);
            }
            return new ItemStack(ModItems.MOLTEN_SALT_BUCKET.get());
        } else {
            return ItemStack.EMPTY;
        }
    }

    @NotNull
    @Override
    public Optional<SoundEvent> getPickupSound() {
        return ModFluids.SOURCE_MOLTEN_SALT.get().getPickupSound();
    }

    @SuppressWarnings("deprecation")
    @NotNull
    @Override
    public VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        return ALL;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(SALTLOGGED, pContext.getLevel().getFluidState(pContext.getClickedPos()).getType() == ModFluids.SOURCE_MOLTEN_SALT.get());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(SALTLOGGED);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable BlockGetter pLevel, @NotNull List<Component> pTooltip, @NotNull TooltipFlag pFlag) {
        if (ModKeyBindings.SHOW_DESCRIPTION.isDown()) {
            pTooltip.add(Component.translatable(asBlock().getDescriptionId() + "_description").withStyle(ChatFormatting.GRAY));
        } else {
            pTooltip.add(Component.literal("Hold ").withStyle(ChatFormatting.GRAY).append(Component.literal(ModKeyBindings.SHOW_DESCRIPTION.getKey().getDisplayName().getString()).withStyle(FormattingUtil.hex(0x55D38A))).append(Component.literal(" for a block description.").withStyle(ChatFormatting.GRAY)));
        }
    }

    @SuppressWarnings("deprecation")
    @NotNull
    @Override
    public FluidState getFluidState(@NotNull BlockState pState) {
        return pState.getValue(SALTLOGGED) ? ModFluids.SOURCE_MOLTEN_SALT.get().defaultFluidState() : super.getFluidState(pState);
    }
}
