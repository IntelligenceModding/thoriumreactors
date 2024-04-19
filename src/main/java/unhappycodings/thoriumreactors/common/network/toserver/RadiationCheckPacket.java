package unhappycodings.thoriumreactors.common.network.toserver;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import net.minecraftforge.network.NetworkEvent;
import unhappycodings.thoriumreactors.common.capability.RadiationSavedData;
import unhappycodings.thoriumreactors.common.network.base.IPacket;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RadiationCheckPacket implements IPacket {
    private final ChunkPos pos;

    public RadiationCheckPacket(ChunkPos pos) {
        this.pos = pos;
    }

    public static RadiationCheckPacket decode(FriendlyByteBuf buffer) {
        return new RadiationCheckPacket(buffer.readChunkPos());
    }

    @SuppressWarnings("ConstantConditions")
    public void handle(NetworkEvent.Context context) {
        ServerPlayer player = context.getSender();
        ServerLevel level = player.level().getServer().overworld();
        RadiationSavedData cache = RadiationSavedData.get(level);
        CompoundTag tag = cache.save(new CompoundTag());

        tag.getList("chunks", Tag.TAG_COMPOUND).stream().map(CompoundTag.class::cast).filter(compoundTag -> compoundTag.toString().contains("\"" + pos.x + "#" + pos.z + "\"")).forEach(compoundTag -> {
            CompoundTag data = compoundTag.getCompound(pos.x + "#" + pos.z);
            long currentTimestamp = new Date().getTime();
            long timestamp = data.getLong("timestamp");

            cache.addBlockToCache(pos, currentTimestamp, data.getFloat("strength") - ((currentTimestamp - timestamp) / 1000f * 0.0764f));
        });
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeChunkPos(pos);
    }
}
