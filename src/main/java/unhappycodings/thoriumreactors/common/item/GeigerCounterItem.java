package unhappycodings.thoriumreactors.common.item;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import unhappycodings.thoriumreactors.common.capability.RadiationSavedData;
import unhappycodings.thoriumreactors.common.util.FormattingUtil;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class GeigerCounterItem extends Item {

    public GeigerCounterItem(Properties pProperties) {
        super(pProperties);
    }

    @NotNull
    @Override
    public Rarity getRarity(@NotNull ItemStack pStack) {
        return Rarity.UNCOMMON;
    }

    @NotNull
    @Override
    public InteractionResult useOn(@NotNull UseOnContext context) {
        if (context.getLevel().isClientSide) return InteractionResult.SUCCESS;

        Player player = context.getPlayer();
        RadiationSavedData cache = RadiationSavedData.get((ServerLevel) context.getLevel());
        CompoundTag tag = cache.save(new CompoundTag());
        ChunkPos chunkPos = context.getLevel().getChunk(context.getClickedPos()).getPos();
        AtomicReference<Float> radiation = new AtomicReference<>((float) 0);

        tag.getList("chunks", Tag.TAG_COMPOUND).stream().map(CompoundTag.class::cast).filter(compoundTag -> compoundTag.toString().contains("\"" + chunkPos.x + "#" + chunkPos.z + "\"")).forEach(compoundTag -> {
            radiation.set(compoundTag.getCompound(chunkPos.x + "#" + chunkPos.z).getFloat("strength"));
        });

        player.sendSystemMessage(Component.literal("Surface Contamination: ").withStyle(ChatFormatting.GRAY).append(Component.literal(radiation.get() + " uSv/h").withStyle(FormattingUtil.hex(0x55D38A))));

        return InteractionResult.SUCCESS;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag isAdvanced) {
        tooltipComponents.add(Component.literal("Measures contamination of surfaces as blocks.").withStyle(ChatFormatting.GRAY));

        super.appendHoverText(stack, level, tooltipComponents, isAdvanced);
    }
}
