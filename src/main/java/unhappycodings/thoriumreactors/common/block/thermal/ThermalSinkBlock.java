package unhappycodings.thoriumreactors.common.block.thermal;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class ThermalSinkBlock extends Block {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public static final VoxelShape NORTH_SOUTH = Shapes.or(Block.box(0, 3, 13.25, 16, 10, 14.25), Block.box(0, 0, 0, 16, 3, 16),
            Block.box(0, 3, 0, 16, 10, 1), Block.box(0, 3, 15, 16, 10, 16),
            Block.box(0, 3, 1.75, 16, 10, 2.75), Block.box(0, 3, 3.75, 16, 11, 4.75),
            Block.box(0, 3, 11.25, 16, 11, 12.25), Block.box(0, 3, 9.5, 16, 11, 10.5),
            Block.box(0, 3, 5.75, 16, 11, 6.75), Block.box(0, 3, 7.5, 16, 11, 8.5));

    public static final VoxelShape EAST_WEST = Shapes.or(Block.box(13.25, 3, 0, 14.25, 10, 16), Block.box(0, 0, 0, 16, 3, 16),
            Block.box(0, 3, 0, 1, 10, 16), Block.box(15, 3, 0, 16, 10, 16),
            Block.box(1.75, 3, 0, 2.75, 10, 16), Block.box(3.75, 3, 0, 4.75, 11, 16),
            Block.box(11.25, 3, 0, 12.25, 11, 16), Block.box(9.5, 3, 0, 10.5, 11, 16),
            Block.box(5.75, 3, 0, 6.75, 11, 16), Block.box(7.5, 3, 0, 8.5, 11, 16));
    private static final Map<Direction, VoxelShape> AABBS = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, NORTH_SOUTH, Direction.SOUTH, NORTH_SOUTH, Direction.WEST, EAST_WEST, Direction.EAST, EAST_WEST));

    public ThermalSinkBlock() {
        super(BlockBehaviour.Properties.of(Material.METAL).strength(5f));
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection());
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable BlockGetter pLevel, @NotNull List<Component> pTooltip, @NotNull TooltipFlag pFlag) {
        pTooltip.add(Component.literal("Heatsinks absorb heat to the air!").withStyle(ChatFormatting.ITALIC));
        pTooltip.add(Component.literal(" "));
        pTooltip.add(Component.literal("Thermal Conductivity: ").withStyle(Style.EMPTY.withColor(0x5BC0BE))
                .append(Component.literal(538 + " W/b").withStyle(ChatFormatting.WHITE)).append(Component.literal(" (Watt/block)").withStyle(ChatFormatting.DARK_GRAY)));
        pTooltip.add(Component.literal("Thermal Absorption:  ").withStyle(Style.EMPTY.withColor(0x5BC0BE))
                .append(Component.literal((int) Math.floor(538 * 1.341) + " W/b").withStyle(ChatFormatting.WHITE)).append(Component.literal(" (Watt/block)").withStyle(ChatFormatting.DARK_GRAY)));
        pTooltip.add(Component.literal("Used with controllers, conducters").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.DARK_GRAY));
        pTooltip.add(Component.literal("and valves to form a heatsink!").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.DARK_GRAY));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return AABBS.get(pState.getValue(FACING));
    }
}
