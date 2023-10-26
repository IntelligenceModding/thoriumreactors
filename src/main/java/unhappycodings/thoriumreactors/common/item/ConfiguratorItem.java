package unhappycodings.thoriumreactors.common.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import unhappycodings.thoriumreactors.common.blockentity.reactor.ReactorControllerBlockEntity;
import unhappycodings.thoriumreactors.common.registration.ModBlocks;
import unhappycodings.thoriumreactors.common.util.FormattingUtil;

import java.util.List;

public class ConfiguratorItem extends Item {
    public static final ToolAction WRENCH = ToolAction.get("wrench");

    public ConfiguratorItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
        return toolAction == WRENCH;
    }

    @NotNull
    @Override
    public Rarity getRarity(@NotNull ItemStack pStack) {
        return Rarity.RARE;
    }

    @SuppressWarnings("ConstantConditions")
    @NotNull
    @Override
    public InteractionResult useOn(@NotNull UseOnContext context) {
        Level level = context.getLevel();
        if (level.isClientSide) return InteractionResult.CONSUME;
        Player player = context.getPlayer();
        ItemStack item = context.getItemInHand();
        BlockPos pos = context.getClickedPos();
        BlockState state = context.getLevel().getBlockState(context.getClickedPos());
        if (context.getHand() != InteractionHand.MAIN_HAND)
            return InteractionResult.CONSUME;
        if (state.is(ModBlocks.TURBINE_CONTROLLER_BLOCK.get())) {
            item = new ItemStack(item.getItem());
            CompoundTag posTag = new CompoundTag();
            writePos(posTag, pos);
            item.getOrCreateTag().put("turbinePos", posTag);
            player.displayClientMessage(Component.translatable(FormattingUtil.getTranslatable("items.text.turbine_saved_to_configurator")).withStyle(FormattingUtil.hex(0x7ED355)), false);
        } else if (state.is(ModBlocks.THERMAL_CONTROLLER.get())) {
            item = new ItemStack(item.getItem());
            CompoundTag posTag = new CompoundTag();
            writePos(posTag, pos);
            item.getOrCreateTag().put("thermalPos", posTag);
            player.displayClientMessage(Component.translatable(FormattingUtil.getTranslatable("items.text.thermal_saved_to_configurator")).withStyle(FormattingUtil.hex(0x7ED355)), false);
        } else {
            if (level.getBlockEntity(pos) instanceof ReactorControllerBlockEntity entity) {
                if (item.getOrCreateTag().contains("turbinePos")) {
                    entity.addTurbinePos(BlockEntity.getPosFromTag(item.getOrCreateTag().getCompound("turbinePos")));
                    player.displayClientMessage(Component.translatable(FormattingUtil.getTranslatable("items.text.turbine_saved_to_reactor")).withStyle(FormattingUtil.hex(0x55D38A)), false);
                }
                if (item.getOrCreateTag().contains("thermalPos")) {
                    entity.setThermalPos(BlockEntity.getPosFromTag(item.getOrCreateTag().getCompound("thermalPos")));
                    player.displayClientMessage(Component.translatable(FormattingUtil.getTranslatable("items.text.thermal_saved_to_reactor")).withStyle(FormattingUtil.hex(0x55D38A)), false);
                }
            }
        }
        player.setItemSlot(EquipmentSlot.MAINHAND, item);
        return InteractionResult.FAIL;
    }

    public static void writePos(CompoundTag nbt, BlockPos pos) {
        nbt.putInt("x", pos.getX());
        nbt.putInt("y", pos.getY());
        nbt.putInt("z", pos.getZ());
    }

    public static BlockPos readPos(CompoundTag nbt) {
        BlockPos pos = new BlockPos(nbt.getInt("x"), nbt.getInt("y"), nbt.getInt("z"));
        return pos;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag isAdvanced) {
        if (stack.getOrCreateTag().contains("turbinePos")) {
            String pos = stack.getOrCreateTag().get("turbinePos").getAsString().replace("{", "").replace("}", "").replace(",", " ");
            tooltipComponents.add(Component.translatable(FormattingUtil.getTranslatable("items.text.turbine_selected")).append(Component.literal(pos).withStyle(ChatFormatting.GRAY)).withStyle(FormattingUtil.hex(0x55D38A)));
        }
        if (stack.getOrCreateTag().contains("thermalPos")) {
            String pos = stack.getOrCreateTag().get("thermalPos").getAsString().replace("{", "").replace("}", "").replace(",", " ");
            tooltipComponents.add(Component.translatable(FormattingUtil.getTranslatable("items.text.thermal_selected")).append(Component.literal(pos).withStyle(ChatFormatting.GRAY)).withStyle(FormattingUtil.hex(0x55D38A)));
        }
        super.appendHoverText(stack, level, tooltipComponents, isAdvanced);
    }

}
