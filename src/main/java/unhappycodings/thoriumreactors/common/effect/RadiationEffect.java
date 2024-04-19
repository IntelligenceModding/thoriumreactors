package unhappycodings.thoriumreactors.common.effect;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import unhappycodings.thoriumreactors.common.capability.RadiationSavedData;
import unhappycodings.thoriumreactors.common.registration.ModSounds;
import unhappycodings.thoriumreactors.common.util.RadiationUtil;

import java.util.concurrent.atomic.AtomicBoolean;

public class RadiationEffect extends MobEffect {
    private float strength = 0;

    public RadiationEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if (pLivingEntity.level().getGameTime() % 10 == 0 && !pLivingEntity.level().isClientSide) {
            Player player = (Player) pLivingEntity;
            CompoundTag tag = RadiationSavedData.get(player.level().getServer().overworld()).save(new CompoundTag());
            ChunkPos chunkPos = player.chunkPosition();

            AtomicBoolean changed = new AtomicBoolean(false);
            tag.getList("chunks", Tag.TAG_COMPOUND).stream().map(CompoundTag.class::cast).filter(compoundTag -> compoundTag.toString().contains("\"" + chunkPos.x + "#" + chunkPos.z + "\"")).forEach(compoundTag -> {
                this.strength = compoundTag.getCompound(chunkPos.x + "#" + chunkPos.z).getFloat("strength");
                changed.set(true);
            });

        }

        if (pLivingEntity.level().isClientSide() && strength > 0) {
            if (strength < 500 && pLivingEntity.level().getGameTime() % (4 * 20) == 0) {
                pLivingEntity.playSound(ModSounds.GEIGER_SINGLE.get(), 1f, 1f);
            } else if (strength >= 500 && strength < 1000 && pLivingEntity.level().getGameTime() % 20 == 0) {
                pLivingEntity.playSound(ModSounds.GEIGER_DOUBLE.get(), 1f, 1f);
            } else if (strength >= 1000 && strength < 1500 && pLivingEntity.level().getGameTime() % 20 == 0) {
                pLivingEntity.playSound(ModSounds.GEIGER_TRIPLE.get(), 1f, 1f);
            } else if (strength >= 1500 && pLivingEntity.level().getGameTime() % 20 == 0) {
                pLivingEntity.playSound(ModSounds.GEIGER_TRIPLE.get(), 1f, 1f);
                pLivingEntity.playSound(ModSounds.GEIGER_QUADRUPLE.get(), 1f, 1f);
            }
        }

        super.applyEffectTick(pLivingEntity, pAmplifier);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}
