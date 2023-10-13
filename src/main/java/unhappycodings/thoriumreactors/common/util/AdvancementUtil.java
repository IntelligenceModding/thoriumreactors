package unhappycodings.thoriumreactors.common.util;

import net.minecraft.advancements.Advancement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import unhappycodings.thoriumreactors.ThoriumReactors;

public class AdvancementUtil {

    public static void awardAdvancement(ServerLevel levelIn, ServerPlayer player, String advancement, String criterion) {
        Advancement adv = levelIn.getServer().getAdvancements().getAdvancement(new ResourceLocation(ThoriumReactors.MOD_ID, advancement));
        PlayerAdvancements advancements = player.getAdvancements();
        if (!advancements.getOrStartProgress(adv).hasProgress())
            player.getAdvancements().award(adv, criterion);

    }

}
