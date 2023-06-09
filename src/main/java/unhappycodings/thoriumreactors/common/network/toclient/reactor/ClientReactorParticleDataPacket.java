package unhappycodings.thoriumreactors.common.network.toclient.reactor;

import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;
import unhappycodings.thoriumreactors.common.enums.ReactorParticleTypeEnum;
import unhappycodings.thoriumreactors.common.network.base.IPacket;

import java.util.Random;

public class ClientReactorParticleDataPacket implements IPacket {
    private final BlockPos pos;
    private final ReactorParticleTypeEnum type; // 0-3000
    private final long xOffset, zOffset, yOffset; // 0-3000

    public ClientReactorParticleDataPacket(BlockPos pos, ReactorParticleTypeEnum type, long xOffset, long zOffset, long yOffset) {
        this.pos = pos;
        this.type = type;
        this.xOffset = xOffset;
        this.zOffset = zOffset;
        this.yOffset = yOffset;
    }

    public static ClientReactorParticleDataPacket decode(FriendlyByteBuf buffer) {
        return new ClientReactorParticleDataPacket(buffer.readBlockPos(), buffer.readEnum(ReactorParticleTypeEnum.class), buffer.readLong(), buffer.readLong(), buffer.readLong());
    }

    @SuppressWarnings("ConstantConditions")
    public void handle(NetworkEvent.Context context) {
        LocalPlayer player = Minecraft.getInstance().player;

        DustParticleOptions REACTOR_1 = new DustParticleOptions(new Vector3f(Vec3.fromRGB24(4960000)), 1.0F);
        DustParticleOptions REACTOR_2 = new DustParticleOptions(new Vector3f(Vec3.fromRGB24(4443136)), 1.0F);
        DustParticleOptions REACTOR_3 = new DustParticleOptions(new Vector3f(Vec3.fromRGB24(5279232)), 1.0F);
        Random random = new Random();
        for (float x = 0.0f; x <= xOffset; x += 0.5f) {
            for (float y = 0.0f; y <= yOffset; y += 0.5f) {
                for (float z = 0.0f; z <= zOffset; z += 0.5f) {
                    float value = random.nextFloat();
                    player.level.addParticle(value < 0.33 ? REACTOR_1 : value < 0.66 ? REACTOR_2 : REACTOR_3, pos.getX() + x, pos.getY() + y, pos.getZ() + z, 0, 0, 0);
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
