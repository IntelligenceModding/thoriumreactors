package unhappycodings.thoriumreactors.common.item;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import unhappycodings.thoriumreactors.common.util.FormattingUtil;
import unhappycodings.thoriumreactors.common.util.RadiationUtil;

import java.util.List;

public class DosiMeterItem extends Item {

    public DosiMeterItem(Properties pProperties) {
        super(pProperties);
    }

    @NotNull
    @Override
    public Rarity getRarity(@NotNull ItemStack pStack) {
        return Rarity.UNCOMMON;
    }

    @NotNull
    @Override
    public InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        if (level.isClientSide) return InteractionResultHolder.success(player.getItemInHand(hand));

        CompoundTag playerData = player.getPersistentData().getCompound(Player.PERSISTED_NBT_TAG);
        final float radiation = playerData.contains(RadiationUtil.RADIATION_DATA_NAME) ? playerData.getFloat(RadiationUtil.RADIATION_DATA_NAME) : 0;
        player.sendSystemMessage(Component.literal("Self Contamination: ").withStyle(ChatFormatting.GRAY).append(Component.literal(radiation + " uSv").withStyle(FormattingUtil.hex(0x55D38A))));

        return super.use(level, player, hand);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag isAdvanced) {
        tooltipComponents.add(Component.literal("Measures contamination of you.").withStyle(ChatFormatting.GRAY));

        super.appendHoverText(stack, level, tooltipComponents, isAdvanced);
    }
}
