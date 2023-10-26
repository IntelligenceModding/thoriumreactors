package unhappycodings.thoriumreactors.common.event;

import dan200.computercraft.shared.command.text.ChatHelpers;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.ServerLifecycleHooks;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.client.config.ClientConfig;

@Mod.EventBusSubscriber(modid = ThoriumReactors.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEvents {

    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!hasPlayedBefore(event.getEntity())) {
            if (ClientConfig.showFirstJoinWelcomeMessage.get()) {
                sendWelcomeMessage(event);
            }
            if (event.getEntity().getUUID().equals("a2bb5fa4-cb70-4234-83c1-5302d7043c5f") && event.getResult() != Event.Result.DENY) {
                ServerLifecycleHooks.getCurrentServer().getPlayerList().broadcastSystemMessage(Component.literal("Oh, look! " + event.getEntity().getName().getString() + ", the designer of ThoriumReactors joined!"), false);
            }
        }

    }

    private static void sendWelcomeMessage(PlayerEvent.PlayerLoggedInEvent event) {
        event.getEntity().sendSystemMessage(Component.literal("Thank you for using ").append(Component.literal("ThoriumReactors-" + ModList.get().getModFileById(ThoriumReactors.MOD_ID).versionString()).withStyle(Style.EMPTY.withColor(0x43E100))).append("!").withStyle(Style.EMPTY.withColor(0x42AF00)));
        event.getEntity().sendSystemMessage(Component.literal("If any problems occur, report them ").append(ChatHelpers.link(Component.literal("here!").withStyle(Style.EMPTY.withColor(0x43E100)), new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/UnhappyCodings/thoriumreactors"), Component.literal("https://github.com/UnhappyCodings/thoriumreactors"))).withStyle(Style.EMPTY.withColor(0x42AF00)));
    }

    private static boolean hasPlayedBefore(Player player) {
        CompoundTag tag = player.getPersistentData().getCompound(Player.PERSISTED_NBT_TAG);
        String playedBefore = "thorium_played_before";
        if(tag.getBoolean(playedBefore)) {
            return true;
        } else {
            tag.putBoolean(playedBefore, true);
            player.getPersistentData().put(Player.PERSISTED_NBT_TAG, tag);
            return false;
        }
    }

}
