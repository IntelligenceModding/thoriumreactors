package unhappycodings.thoriumreactors.common.event;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.ServerLifecycleHooks;
import unhappycodings.thoriumreactors.ThoriumReactors;

@Mod.EventBusSubscriber(modid = ThoriumReactors.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEvents {

    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!event.getEntity().getUUID().equals("a2bb5fa4-cb70-4234-83c1-5302d7043c5f") && event.getResult() != Event.Result.DENY && !hasPlayedBefore(event.getEntity())) {
            ServerLifecycleHooks.getCurrentServer().getPlayerList().broadcastSystemMessage(Component.literal("Oh, look! " + event.getEntity().getName().getString() + ", the designer of ThoriumReactors joined!"), false);
        }
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
