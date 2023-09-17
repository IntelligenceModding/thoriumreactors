package unhappycodings.thoriumreactors.common.util;

import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundStopSoundPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class SoundUtil {

    public static void stopSound(SoundEvent event, SoundSource source, BlockPos p, Level level) {
        List<ServerPlayer> players = level.getEntitiesOfClass(ServerPlayer.class, new AABB(p.getX() -18, p.getY() -18, p.getZ() -18, p.getX() + 18, p.getY() + 18, p.getZ() + 18));

        if (!players.isEmpty()) {
            ClientboundStopSoundPacket clientboundstopsoundpacket = new ClientboundStopSoundPacket(event.getLocation(), source);
            for (ServerPlayer serverplayer : players)
                serverplayer.connection.send(clientboundstopsoundpacket);
        }
    }

}
