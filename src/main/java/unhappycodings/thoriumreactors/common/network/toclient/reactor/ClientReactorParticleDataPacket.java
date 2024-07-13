package unhappycodings.thoriumreactors.common.network.toclient.reactor;


import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;
import org.joml.Vector3f;
import unhappycodings.thoriumreactors.common.enums.ParticleType;
import unhappycodings.thoriumreactors.common.network.base.IPacket;

import java.util.Random;

public class ClientReactorParticleDataPacket implements IPacket {
    private final BlockPos pos;
    private final ParticleType type; // 0-3000
    private final long xOffset, zOffset, yOffset; // 0-3000

    public ClientReactorParticleDataPacket(BlockPos pos, ParticleType type, long xOffset, long zOffset, long yOffset) {
        this.pos = pos;
        this.type = type;
        this.xOffset = xOffset;
        this.zOffset = zOffset;
        this.yOffset = yOffset;
    }

    public static ClientReactorParticleDataPacket decode(FriendlyByteBuf buffer) {
        return new ClientReactorParticleDataPacket(buffer.readBlockPos(), buffer.readEnum(ParticleType.class), buffer.readLong(), buffer.readLong(), buffer.readLong());
    }

    @SuppressWarnings("ConstantConditions")
    public void handle(NetworkEvent.Context context) {
        LocalPlayer player = Minecraft.getInstance().player;

        DustParticleOptions particleOne = new DustParticleOptions(new Vector3f(Vec3.fromRGB24(type == ParticleType.REACTOR ? 4960000 : type == ParticleType.TURBINE ? 16777215 : type == ParticleType.THERMAL ? 6757901 : 0).toVector3f()), 1.0F);
        DustParticleOptions particleTwo = new DustParticleOptions(new Vector3f(Vec3.fromRGB24(type == ParticleType.REACTOR ? 4443136 : type == ParticleType.TURBINE ? 16777215 : type == ParticleType.THERMAL ? 7152401 : 0).toVector3f()), 1.0F);
        DustParticleOptions particleThree = new DustParticleOptions(new Vector3f(Vec3.fromRGB24(type == ParticleType.REACTOR ? 5279232 : type == ParticleType.TURBINE ? 16777215 : type == ParticleType.THERMAL ? 7481365 : 0).toVector3f()), 1.0F);
        Random random = new Random();
        for (float x = 0.0f; x <= xOffset; x += 0.5f) {
            for (float y = 0.0f; y <= yOffset; y += 0.5f) {
                for (float z = 0.0f; z <= zOffset; z += 0.5f) {
                    float value = random.nextFloat();
                    player.level().addParticle(value < 0.33 ? particleOne : value < 0.66 ? particleTwo : particleThree, pos.getX() + x, pos.getY() + y, pos.getZ() + z, 0, 0, 0);
                }
            }
        }
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(pos);
        buffer.writeEnum(type);
        buffer.writeLong(xOffset);
        buffer.writeLong(yOffset);
        buffer.writeLong(zOffset);
    }
}
