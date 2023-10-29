package unhappycodings.thoriumreactors.common.network.toclient.thermal;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;
import unhappycodings.thoriumreactors.common.blockentity.thermal.ThermalControllerBlockEntity;
import unhappycodings.thoriumreactors.common.network.base.IPacket;

public class ClientThermalConversionsPacket implements IPacket {
    private final BlockPos pos;
    private final int conversions;

    public ClientThermalConversionsPacket(BlockPos pos, int conversions) {
        this.pos = pos;
        this.conversions = conversions;
    }

    public static ClientThermalConversionsPacket decode(FriendlyByteBuf buffer) {
        return new ClientThermalConversionsPacket(buffer.readBlockPos(), buffer.readInt());
    }

    @SuppressWarnings("ConstantConditions")
    public void handle(NetworkEvent.Context context) {
        LocalPlayer player = Minecraft.getInstance().player;
        BlockEntity machine = player.level().getBlockEntity(pos);
        if (!(machine instanceof ThermalControllerBlockEntity blockEntity)) return;
        blockEntity.setConversions(conversions);
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(pos);
        buffer.writeInt(conversions);
    }
}
