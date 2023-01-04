package unhappycodings.thoriumreactors.common.block;

import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ThermalControllerBlock extends Block {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    public ThermalControllerBlock() {
        super(BlockBehaviour.Properties.of(Material.METAL));
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(POWERED, false));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite()).setValue(POWERED, false);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable BlockGetter pLevel, @NotNull List<Component> pTooltip, @NotNull TooltipFlag pFlag) {
        pTooltip.add(Component.literal("Controllers provide an overview to control!").withStyle(ChatFormatting.ITALIC));
        pTooltip.add(Component.literal(" "));
        pTooltip.add(Component.literal("Thermal Conductivity: ").withStyle(Style.EMPTY.withColor(0x5BC0BE))
                .append(Component.literal(538 + " W/b").withStyle(ChatFormatting.WHITE)).append(Component.literal(" (Watt/block)").withStyle(ChatFormatting.DARK_GRAY)));
        pTooltip.add(Component.literal("Thermal Absorption:  ").withStyle(Style.EMPTY.withColor(0x5BC0BE))
                .append(Component.literal((int) Math.floor(538 * 0.028) + " W/b").withStyle(ChatFormatting.WHITE)).append(Component.literal(" (Watt/block)").withStyle(ChatFormatting.DARK_GRAY)));
        pTooltip.add(Component.literal("Used with heatsinks, conducters").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.DARK_GRAY));
        pTooltip.add(Component.literal("and valves to form a heatsink!").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.DARK_GRAY));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, POWERED);
    }

}
