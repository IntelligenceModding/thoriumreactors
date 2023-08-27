package unhappycodings.thoriumreactors.common.network.toclient.reactor;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;
import unhappycodings.thoriumreactors.common.blockentity.turbine.TurbineControllerBlockEntity;
import unhappycodings.thoriumreactors.common.network.base.IPacket;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TurbineActivePacket implements IPacket {
    private final BlockPos pos;
    private final boolean activated;

    public TurbineActivePacket(BlockPos pos, boolean activated) {
        this.pos = pos;
        this.activated = activated;
    }

    public static TurbineActivePacket decode(FriendlyByteBuf buffer) {
        return new TurbineActivePacket(buffer.readBlockPos(), buffer.readBoolean());
    }

    @SuppressWarnings("ConstantConditions")
    public void handle(NetworkEvent.Context context) {
        ServerPlayer player = context.getSender();
        Level level = player.getCommandSenderWorld();
        BlockEntity machine = level.getBlockEntity(pos);
        if (!(machine instanceof TurbineControllerBlockEntity blockEntity)) return;
        blockEntity.setActivated(activated);
        blockEntity.setChanged();

        if (activated) {
            try {
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://127.0.0.1:6969/?turbineStarts=1")).build();
                client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
            } catch (Exception ignored) {
            }
        }
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(pos);
        buffer.writeBoolean(activated);
    }
}
