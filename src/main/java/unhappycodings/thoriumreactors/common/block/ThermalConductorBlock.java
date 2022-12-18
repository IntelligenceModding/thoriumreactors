package unhappycodings.thoriumreactors.common.block;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ThermalConductorBlock extends Block {

    public ThermalConductorBlock() {
        super(BlockBehaviour.Properties.of(Material.METAL));
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable BlockGetter pLevel, @NotNull List<Component> pTooltip, @NotNull TooltipFlag pFlag) {

        pTooltip.add(Component.literal("Very good heat conductor!").withStyle(ChatFormatting.ITALIC));
        pTooltip.add(Component.literal(" "));
        pTooltip.add(Component.literal("Thermal Conductivity: ").withStyle(Style.EMPTY.withColor(0x5BC0BE))
                .append(Component.literal(538 + " W/b").withStyle(ChatFormatting.WHITE)).append(Component.literal(" (Watt/block)").withStyle(ChatFormatting.DARK_GRAY)));
        pTooltip.add(Component.literal("Thermal Absorption:  ").withStyle(Style.EMPTY.withColor(0x5BC0BE))
                .append(Component.literal((int)Math.floor(538 * 0.028) + " W/b").withStyle(ChatFormatting.WHITE)).append(Component.literal(" (Watt/block)").withStyle(ChatFormatting.DARK_GRAY)));
        pTooltip.add(Component.literal("Used with heatsinks, controllers").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.DARK_GRAY));
        pTooltip.add(Component.literal("and valves to form a heatsink!").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.DARK_GRAY));
    }

}
