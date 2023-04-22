package unhappycodings.thoriumreactors.common.block.thermal;

import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ThermalValveBlock extends Block {
    public static final DirectionProperty FACING = DirectionalBlock.FACING;

    public ThermalValveBlock() {
        super(BlockBehaviour.Properties.of(Material.METAL));
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        pTooltip.add(Component.literal("Valves supply the heatsink with heated liquid!").withStyle(ChatFormatting.ITALIC));
        pTooltip.add(Component.literal(" "));
        pTooltip.add(Component.literal("Thermal Conductivity: ").withStyle(Style.EMPTY.withColor(0x5BC0BE))
                .append(Component.literal(538 + " W/b").withStyle(ChatFormatting.WHITE)).append(Component.literal(" (Watt/block)").withStyle(ChatFormatting.DARK_GRAY)));
        pTooltip.add(Component.literal("Thermal Absorption:  ").withStyle(Style.EMPTY.withColor(0x5BC0BE))
                .append(Component.literal((int) Math.floor(538 * 0.030) + " W/b").withStyle(ChatFormatting.WHITE)).append(Component.literal(" (Watt/block)").withStyle(ChatFormatting.DARK_GRAY)));
        pTooltip.add(Component.literal("Used with controllers, conducters").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.DARK_GRAY));
        pTooltip.add(Component.literal("and heatsinks to form a heatsink!").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.DARK_GRAY));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getNearestLookingDirection().getOpposite());
    }
}
