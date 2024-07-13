package unhappycodings.thoriumreactors.common.event;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.NoteBlockEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forgespi.language.IModInfo;
import net.minecraftforge.forgespi.locating.IModFile;
import net.minecraftforge.server.ServerLifecycleHooks;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.common.capability.RadiationSavedData;
import unhappycodings.thoriumreactors.common.network.PacketHandler;
import unhappycodings.thoriumreactors.common.network.toclient.machine.MachineClientDumpModePacket;
import unhappycodings.thoriumreactors.common.network.toserver.RadiationCheckPacket;
import unhappycodings.thoriumreactors.common.registration.ModCommands;
import unhappycodings.thoriumreactors.common.registration.ModDamageSources;
import unhappycodings.thoriumreactors.common.registration.ModEffects;
import unhappycodings.thoriumreactors.common.registration.ModItems;
import unhappycodings.thoriumreactors.common.util.RadiationUtil;

import java.util.Date;
import java.util.List;

@Mod.EventBusSubscriber(modid = ThoriumReactors.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEvents {

    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!hasPlayedBefore(event.getEntity())) {
            if (event.getEntity().getUUID().equals("a2bb5fa4-cb70-4234-83c1-5302d7043c5f") && event.getResult() != Event.Result.DENY) {
                ServerLifecycleHooks.getCurrentServer().getPlayerList().broadcastSystemMessage(Component.literal("Oh, look! " + event.getEntity().getName().getString() + ", the designer of ThoriumReactors joined!"), false);
            }

            if (ModList.get().isLoaded("fusion")) {
                List<IModInfo> mods = ModList.get().getMods();

                if (!mods.stream().filter((iModInfo -> iModInfo.getModId().equals("fusion"))).findFirst().get().getConfig().getConfigElement("authors").get().toString().equals("SuperMartjn642")) {
                    event.getEntity().sendSystemMessage(Component.literal("Warning!").withStyle(Style.EMPTY.withColor(ChatFormatting.DARK_RED)));
                    event.getEntity().sendSystemMessage(Component.literal("You do not have the right Fusion (Connected Textures) Mod installed! This will break the mods appearance and functionality!").withStyle(Style.EMPTY.withColor(ChatFormatting.RED)));
                    event.getEntity().sendSystemMessage(Component.literal("Click here to download and make sure to delete other mods with the same ModID! (As the fusion alloy mod by skrallexy)").withStyle(Style.EMPTY.withColor(ChatFormatting.RED).withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.curseforge.com/minecraft/mc-mods/fusion-connected-textures"))));
                }
            }
        }

    }

    private static boolean hasPlayedBefore(Player player) {
        CompoundTag tag = player.getPersistentData().getCompound(Player.PERSISTED_NBT_TAG);
        String playedBefore = "thorium_played_before";
        if (tag.getBoolean(playedBefore)) {
            return true;
        } else {
            tag.putBoolean(playedBefore, true);
            player.getPersistentData().put(Player.PERSISTED_NBT_TAG, tag);
            return false;
        }
    }

    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        ModCommands.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void onPlayerDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            player.getPersistentData().getCompound(Player.PERSISTED_NBT_TAG).remove(RadiationUtil.RADIATION_DATA_NAME);
            player.setGlowingTag(false);
        }
    }

    @SubscribeEvent
    public static void onPlayerRadiationTick(TickEvent.PlayerTickEvent event) {
        ChunkPos chunkPos = event.player.chunkPosition();

        // Send packet to update radiation in chunk
        if (chunkPos.x != Math.floor(event.player.xo / 16) || chunkPos.z != Math.floor(event.player.zo / 16))
            PacketHandler.sendToServer(new RadiationCheckPacket(chunkPos));
        if (event.player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.HAZMAT_SUIT_HELMET.get()) && event.player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.HAZMAT_SUIT_CHESTPLATE.get()) && event.player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.HAZMAT_SUIT_LEGGINGS.get()) && event.player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.HAZMAT_SUIT_BOOTS.get()))
            return;
        if (event.player.level().getGameTime() % (int) (RadiationUtil.RADIATION_CHECK_DELAY_SECONDS * 20) != 0 || event.player.level().isClientSide)
            return;

        // Get player data and subtract radiation from body
        ServerPlayer player = (ServerPlayer) event.player;
        ServerLevel level = player.level().getServer().overworld();
        CompoundTag playerData = player.getPersistentData().getCompound(Player.PERSISTED_NBT_TAG);


        // Add radiation exposure from chunk to player
        if (event.phase == TickEvent.Phase.START && !event.player.isSpectator() && !event.player.isCreative()) {
            RadiationSavedData cache = RadiationSavedData.get(level);
            CompoundTag tag = cache.save(new CompoundTag());

            tag.getList("chunks", Tag.TAG_COMPOUND).stream().map(CompoundTag.class::cast).filter(compoundTag -> compoundTag.toString().contains("\"" + chunkPos.x + "#" + chunkPos.z + "\"")).forEach(compoundTag -> {
                float exposure = (compoundTag.getCompound(chunkPos.x + "#" + chunkPos.z).getFloat("strength") / 3600) * RadiationUtil.RADIATION_CHECK_DELAY_SECONDS;

                playerData.putFloat(RadiationUtil.RADIATION_DATA_NAME, (playerData.contains(RadiationUtil.RADIATION_DATA_NAME) ? playerData.getFloat(RadiationUtil.RADIATION_DATA_NAME) : 0) + (player.isUnderWater() ? exposure / 10 : exposure));
                player.getPersistentData().put(Player.PERSISTED_NBT_TAG, playerData);
            });
        }

        // Check for contamination and adding effects to player
        float contamination = playerData.getFloat(RadiationUtil.RADIATION_DATA_NAME);
        if (contamination > 1f) {
            addPlayerEffects(contamination, player, level);
        }
    }

    private static void addPlayerEffects(float contamination, Player player, Level level) {
        int random = (int) (Math.random() * 100);
        player.addEffect(new MobEffectInstance(ModEffects.RADIATION.get(), (int) (20 * (contamination / 0.0764f)), 0, false, false));
        player.setGlowingTag(contamination > 250);

        if (contamination > 100 && random < 2f) {
            player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 5 * 20, 0, false, false));
            player.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 5 * 20, 0, false, false));
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 5 * 20, 1, false, false));
        }

        if (contamination > 250 && random < 5)
            player.hurt(level.damageSources().source(ModDamageSources.OVERDOSIS), 1f);

        if (contamination > 250 && random > 85)
            player.addEffect(new MobEffectInstance(MobEffects.HUNGER, 10 * 20, 0, false, false));

        if (contamination > 1000 && random < 3)
            player.hurt(level.damageSources().source(ModDamageSources.OVERDOSIS), player.getHealth() / 3f);

        if (contamination > 2000)
            player.hurt(level.damageSources().source(ModDamageSources.OVERDOSIS), player.getHealth());

    }

}