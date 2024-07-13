package unhappycodings.thoriumreactors.common.util;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import unhappycodings.thoriumreactors.common.capability.RadiationSavedData;
import unhappycodings.thoriumreactors.common.registration.ModEffects;

import javax.annotation.Nullable;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class RadiationUtil {
    public static final float RADIATION_CHECK_DELAY_SECONDS = 2f;
    public static final String RADIATION_DATA_NAME = "thoriumreactors_radiation_contamination";

    public static int clearPlayer(CommandSourceStack source, @Nullable Player argumentPlayer) {
        Player player = argumentPlayer == null ? source.getPlayer() : argumentPlayer;

        player.getPersistentData().getCompound(Player.PERSISTED_NBT_TAG).remove(RadiationUtil.RADIATION_DATA_NAME);
        player.removeEffect(ModEffects.RADIATION.get());

        source.sendSuccess(() -> Component.literal("Cleared radiation contamination of " + player.getDisplayName().getString() + "!"), true);
        return 1;
    }

    public static int addPlayer(CommandSourceStack source, @Nullable Player argumentPlayer, float strenght) {
        Player player = argumentPlayer == null ? source.getPlayer() : argumentPlayer;

        CompoundTag playerData = player.getPersistentData().getCompound(Player.PERSISTED_NBT_TAG);
        final float radiation = (playerData.contains(RadiationUtil.RADIATION_DATA_NAME) ? playerData.getFloat(RadiationUtil.RADIATION_DATA_NAME) + strenght : 0);

        playerData.putFloat(RadiationUtil.RADIATION_DATA_NAME, radiation);
        player.getPersistentData().put(Player.PERSISTED_NBT_TAG, playerData);

        source.sendSuccess(() -> Component.literal("Added radiation contamination of " + strenght + "mSv to " + player.getDisplayName().getString() + "! Total: " + radiation + "mSv!"), true);
        return 1;
    }

    public static int setPlayer(CommandSourceStack source, @Nullable Player argumentPlayer, float strenght) {
        Player player = argumentPlayer == null ? source.getPlayer() : argumentPlayer;

        CompoundTag playerData = player.getPersistentData().getCompound(Player.PERSISTED_NBT_TAG);
        playerData.putFloat(RadiationUtil.RADIATION_DATA_NAME, strenght);
        player.getPersistentData().put(Player.PERSISTED_NBT_TAG, playerData);

        source.sendSuccess(() -> Component.literal("Set radiation contamination of " + player.getDisplayName().getString() + " to " + strenght + "mSv!"), true);
        return 1;
    }

    public static int removePlayer(CommandSourceStack source, @Nullable Player argumentPlayer, float strenght) {
        Player player = argumentPlayer == null ? source.getPlayer() : argumentPlayer;

        CompoundTag playerData = player.getPersistentData().getCompound(Player.PERSISTED_NBT_TAG);
        final float radiation = (playerData.contains(RadiationUtil.RADIATION_DATA_NAME) ? playerData.getFloat(RadiationUtil.RADIATION_DATA_NAME) - strenght : 0);

        playerData.putFloat(RadiationUtil.RADIATION_DATA_NAME, radiation);
        player.getPersistentData().put(Player.PERSISTED_NBT_TAG, playerData);

        source.sendSuccess(() -> Component.literal("Removed radiation contamination of " + strenght + "mSv from " + player.getDisplayName().getString() + "! Total: " + (radiation) + "mSv!"), true);
        return 1;
    }

    public static int clearChunkData(CommandSourceStack source) {
        int count = clearChunkData(source.getPlayer());

        source.sendSuccess(() -> Component.literal("Remove radiation of a total of " + count + " chunks!"), true);
        return 1;
    }

    public static int clearChunkData(ServerPlayer player) {
        ServerLevel level = player.getServer().overworld();
        RadiationSavedData cache = RadiationSavedData.get(level);

        int count = cache.save(new CompoundTag()).getList("chunks", 10).size();
        cache.clear(level);

        return count;
    }

    public static int setChunkData(CommandSourceStack source, int radius, float strength) {
        ServerLevel level = source.getServer().overworld();

        int count = setChunkData(source.getPlayer().getOnPos(), level, radius, strength);

        source.sendSuccess(() -> Component.literal("Added radiation to a total of " + count + " chunks!"), true);
        return 1;
    }

    public static int setChunkData(BlockPos blockPos, ServerLevel level, int radius, float strength) {
        Stream<ChunkPos> chunkList = ChunkPos.rangeClosed(new ChunkPos(blockPos), radius);
        RadiationSavedData cache = RadiationSavedData.get(level);
        AtomicInteger count = new AtomicInteger();

        chunkList.forEach(current -> {
            BlockPos pos = level.getChunk(blockPos).getPos().getBlockAt(0, 0, 0);
            if (pos.closerToCenterThan(new Vec3(current.x * 16, 0, current.z * 16), radius * 16 + 1)) {
                cache.addBlockToCache(current, new Date().getTime(), getRadiationForDistance((int) Math.sqrt(pos.distToCenterSqr(new Vec3(current.x * 16, 0, current.z * 16))), radius, strength));
                count.getAndIncrement();
            }
        });

        return count.get();
    }

    public static float getRadiationForDistance(int distance, int radius, float max) {
        if (distance < 14) {
            return max;
        }

        int[] chars = new int[radius + 1];
        for (int i = 1; i <= radius; i++) {
            chars[i] = i;
        }

        // Flipping the array here
        for (int i = 0, n = chars.length; i < n / 2; i++) {
            int tmp = chars[i];
            chars[i] = chars[n - i - 1];
            chars[n - i - 1] = tmp;
        }

        float partial = max / radius;
        for (int i = 1; i <= radius; i++) {
            if (distance >= (i * 16 - 8) && distance <= (i * 16 + 8)) {
                return chars[i] * partial;
            }
        }

        return 0;
    }


}
