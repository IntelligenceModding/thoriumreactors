package unhappycodings.thoriumreactors.common.block.thermal;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.sounds.SoundSource;
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
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import unhappycodings.thoriumreactors.common.blockentity.thermal.ThermalValveBlockEntity;
import unhappycodings.thoriumreactors.common.enums.ThermalValveTypeEnum;
import unhappycodings.thoriumreactors.common.registration.ModItems;
import unhappycodings.thoriumreactors.common.registration.ModSounds;

import java.util.List;

public class ThermalValveBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = DirectionalBlock.FACING;
    public static final EnumProperty<ThermalValveTypeEnum> TYPE = EnumProperty.create("type", ThermalValveTypeEnum.class);

    public ThermalValveBlock() {
        super(BlockBehaviour.Properties.of(Material.METAL).strength(5f));
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(TYPE, ThermalValveTypeEnum.COOLANT_INPUT));
    }

    @SuppressWarnings("deprecation")
    @NotNull
    @Override
    public InteractionResult use(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, Player pPlayer, @NotNull InteractionHand pHand, @NotNull BlockHitResult pHit) {
        if (pPlayer.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.CONFIGURATOR.get())) {
            if (!pLevel.isClientSide) {
                pLevel.setBlockAndUpdate(pPos, pState.setValue(TYPE, pState.getValue(TYPE).next()));
            } else {
                pPlayer.sendSystemMessage(Component.literal("Input Mode: " + pState.getValue(TYPE).next().getSerializedName()).withStyle(ChatFormatting.GREEN));
                pPlayer.playNotifySound(ModSounds.DIGITALBEEP_5.get(), SoundSource.BLOCKS, 1f, 1f);
            }
            return InteractionResult.SUCCESS;
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, TYPE);
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
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite()).setValue(TYPE, ThermalValveTypeEnum.COOLANT_INPUT);
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
        return new ThermalValveBlockEntity(pos, state);
    }
}
