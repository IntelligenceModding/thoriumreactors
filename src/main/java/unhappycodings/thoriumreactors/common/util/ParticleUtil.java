package unhappycodings.thoriumreactors.common.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;

public class ParticleUtil {

    public static void renderSmokeParticles(BlockPos pos, RandomSource randomSource, Level level) {
        renderParticles(pos, randomSource, level, ParticleTypes.SMOKE);
    }

    public static void renderCloudParticles(BlockPos pos, RandomSource randomSource, Level level) {
        renderParticles(pos, randomSource, level, ParticleTypes.CLOUD);
    }

    public static void renderParticles(BlockPos pos, RandomSource randomSource, Level level, ParticleOptions types) {
        for (int j2 = 0; j2 < 5; ++j2) {
            double d12 = (double) pos.getX() + randomSource.nextDouble() * 0.6D + 0.2D;
            double d18 = (double) pos.getY() + 1D;
            double d25 = (double) pos.getZ() + randomSource.nextDouble() * 0.6D + 0.2D;
            level.addParticle(types, d12, d18, d25, 0.0D, types == ParticleTypes.CLOUD ? 0.1D : 0.0D, 0.0D);
        }
    }

    public static void renderBurnParticles(BlockPos pos, RandomSource randomSource, Level level, Direction direction) {
        double d0 = pos.getX() + 0.5D;
        double d1 = pos.getY();
        double d2 = pos.getZ() + 0.5D;
        if (randomSource.nextDouble() < 0.1D)
            level.playLocalSound(d0, d1, d2, SoundEvents.FURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
        Direction.Axis directionAxis = direction.getAxis();
        double d4 = randomSource.nextDouble() * 0.6D - 0.3D;
        double d5 = directionAxis == Direction.Axis.X ? (double) direction.getStepX() * 0.52D : d4;
        double d6 = randomSource.nextDouble() * 6.0D / 16.0D;
        double d7 = directionAxis == Direction.Axis.Z ? (double) direction.getStepZ() * 0.52D : d4;
        level.addParticle(ParticleTypes.SMOKE, d0 + d5, d1 + d6, d2 + d7, 0.0D, 0.0D, 0.0D);
        level.addParticle(ParticleTypes.FLAME, d0 + d5, d1 + d6, d2 + d7, 0.0D, 0.0D, 0.0D);
    }

}
