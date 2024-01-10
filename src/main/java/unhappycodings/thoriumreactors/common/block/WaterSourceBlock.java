package unhappycodings.thoriumreactors.common.block;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import unhappycodings.thoriumreactors.common.registration.ModBlockEntities;
import unhappycodings.thoriumreactors.common.util.FormattingUtil;

import java.util.List;

public class WaterSourceBlock extends BaseEntityBlock {

    public WaterSourceBlock() {
        super(Properties.copy(Blocks.STONE).strength(5f).noOcclusion());
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> pTooltip, @NotNull TooltipFlag pFlag) {
        pTooltip.add(Component.literal(Fluids.WATER.getFluidType().getDescription().getString() + ": ").withStyle(FormattingUtil.hex(0x0ACECE)).append(Component.translatable(FormattingUtil.getTranslatable("machines.tooltip.infinite")).withStyle(ChatFormatting.GRAY)));
        pTooltip.add(Component.translatable(FormattingUtil.getTranslatable("machines.tooltip.capacity")).withStyle(FormattingUtil.hex(0x3BA3D3)).append(Component.translatable(FormattingUtil.getTranslatable("machines.tooltip.infinite")).withStyle(ChatFormatting.GRAY)));
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
        return ModBlockEntities.WATER_SOURCE_BLOCK.get().create(pos, state);
    }

}
