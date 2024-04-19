package unhappycodings.thoriumreactors.common.capability;

import com.google.common.collect.Maps;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ColumnPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.minecraftforge.event.level.LevelEvent;
import org.jetbrains.annotations.Nullable;
import unhappycodings.thoriumreactors.ThoriumReactors;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RadiationSavedData extends SavedData {

    // destinationCoordinateCache is (src -> dest) [DestWorld, [SrcPos, DestPos]]
    private final Map<ChunkPos, Map.Entry<Long, Float>> radiationCache = new HashMap<>();
    private static final String IDENTIFIER = ThoriumReactors.MOD_ID + "_radiation";

    private RadiationSavedData() {
        this.setDirty();
    }

    public static RadiationSavedData get(ServerLevel level) {
        ServerLevel server = level.getServer().overworld();
        DimensionDataStorage storage = server.getDataStorage();
        return storage.computeIfAbsent(RadiationSavedData::load, RadiationSavedData::new, IDENTIFIER);
    }

    public void addBlockToCache(ChunkPos chunkPos, Long timestamp, Float strength) {
        if (this.radiationCache.containsKey(chunkPos) && this.radiationCache.get(chunkPos).getValue() < strength) {
            this.radiationCache.remove(chunkPos);
        }
        this.radiationCache.putIfAbsent(chunkPos, Map.entry(timestamp, strength));
        this.setDirty();
    }

    public void clear(ServerLevel level) {
        ServerLevel server = level.getServer().overworld();
        DimensionDataStorage storage = server.getDataStorage();
        storage.set(IDENTIFIER, new RadiationSavedData());

        this.radiationCache.values().clear();

        this.setDirty();
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        ListTag dcc = new ListTag();
        this.radiationCache.forEach((chunkPos, map) -> {
            CompoundTag ct = new CompoundTag();
            CompoundTag values = new CompoundTag();
            values.putLong("timestamp", map.getKey());
            values.putFloat("strength", map.getValue());

            ct.put(chunkPos.x + "#" + chunkPos.z, values);
            dcc.add(ct);
        });

        tag.put("chunks", dcc);
        return tag;
    }

    public static RadiationSavedData load(CompoundTag tag) {
        RadiationSavedData cache = new RadiationSavedData();
        tag.getList("chunks", Tag.TAG_COMPOUND).stream().map(CompoundTag.class::cast).forEach(compoundTag -> {
            String[] chunkPosString = compoundTag.getAllKeys().toString().replace("[", "").replace("]", "").split("#");
            ChunkPos chunkPos = new ChunkPos(Integer.parseInt(chunkPosString[0]), Integer.parseInt(chunkPosString[1]));
            CompoundTag currentData = compoundTag.getCompound(chunkPosString[0] + "#" + chunkPosString[1]);

            cache.addBlockToCache(chunkPos, currentData.getLong("timestamp"), currentData.getFloat("strength"));
        });
        return cache;
    }
}