package unhappycodings.thoriumreactors.common.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import unhappycodings.thoriumreactors.client.config.ClientConfig;
import unhappycodings.thoriumreactors.common.enums.UpgradeItemType;
import unhappycodings.thoriumreactors.common.registration.ModKeyBindings;
import unhappycodings.thoriumreactors.common.util.FormattingUtil;
import unhappycodings.thoriumreactors.common.util.KeyBindingUtil;

import java.util.List;

public class UpgradeItem extends Item {
    public final UpgradeItemType type;

    public UpgradeItem(Properties pProperties, UpgradeItemType type) {
        super(pProperties);
        this.type = type;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltip, @NotNull TooltipFlag pIsAdvanced) {
        if (KeyBindingUtil.isKeyPressed(ModKeyBindings.SHOW_DESCRIPTION) && ClientConfig.showBlockDescription.get()) {
            pTooltip.add(Component.translatable(this.getDescriptionId() + "_description").withStyle(ChatFormatting.GRAY));
        } else {
            if (ClientConfig.showBlockDescription.get())
                pTooltip.add(Component.translatable(FormattingUtil.getTranslatable("machines.tooltip.hold")).withStyle(ChatFormatting.GRAY).append(Component.literal(ModKeyBindings.SHOW_DESCRIPTION.getKey().getDisplayName().getString()).withStyle(FormattingUtil.hex(0x55D38A))).append(Component.translatable(FormattingUtil.getTranslatable("machines.tooltip.for_description")).withStyle(ChatFormatting.GRAY)));
        }
    }
}
