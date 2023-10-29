package unhappycodings.thoriumreactors.common.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.common.network.base.IPacket;
import unhappycodings.thoriumreactors.common.network.toclient.machine.*;
import unhappycodings.thoriumreactors.common.network.toclient.reactor.*;
import unhappycodings.thoriumreactors.common.network.toclient.thermal.ClientThermalConversionsPacket;
import unhappycodings.thoriumreactors.common.network.toclient.turbine.ClientTurbineControllerDataPacket;
import unhappycodings.thoriumreactors.common.network.toserver.MachineChangedPacket;
import unhappycodings.thoriumreactors.common.network.toserver.MachineDumpModePacket;
import unhappycodings.thoriumreactors.common.network.toserver.MachinePowerablePacket;
import unhappycodings.thoriumreactors.common.network.toserver.MachineRedstoneModePacket;
import unhappycodings.thoriumreactors.common.network.toserver.reactor.ReactorControllerChangedPacket;
import unhappycodings.thoriumreactors.common.network.toserver.reactor.ReactorControllerCopyTurbinePacket;
import unhappycodings.thoriumreactors.common.network.toserver.reactor.ReactorControllerRemoveTurbinePacket;
import unhappycodings.thoriumreactors.common.network.toserver.reactor.ReactorControllerStatePacket;

import java.util.Optional;
import java.util.function.Function;

public class PacketHandler {

    private static final String PROTOCOL_VERSION = ModLoadingContext.get().getActiveContainer().getModInfo().getVersion().toString();

    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(new ResourceLocation(ThoriumReactors.MOD_ID, "main_channel"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

    private static int index = 0;

    public static void init() {
        registerServerToClient(ClientElectrolyticSaltSeparatorDataPacket.class, ClientElectrolyticSaltSeparatorDataPacket::decode);
        registerServerToClient(ClientFluidEvaporatorDataPacket.class, ClientFluidEvaporatorDataPacket::decode);
        registerServerToClient(MachineClientDumpModePacket.class, MachineClientDumpModePacket::decode);
        registerServerToClient(ClientSaltMelterDataPacket.class, ClientSaltMelterDataPacket::decode);
        registerServerToClient(ClientGeneratorDataPacket.class, ClientGeneratorDataPacket::decode);
        registerServerToClient(ClientConcentratorDataPacket.class, ClientConcentratorDataPacket::decode);
        registerServerToClient(ClientDecomposerDataPacket.class, ClientDecomposerDataPacket::decode);
        registerServerToClient(ClientUraniumOxidizerDataPacket.class, ClientUraniumOxidizerDataPacket::decode);
        registerServerToClient(ClientFluidEnricherDataPacket.class, ClientFluidEnricherDataPacket::decode);
        registerServerToClient(ClientFluidCentrifugeDataPacket.class, ClientFluidCentrifugeDataPacket::decode);
        registerServerToClient(ClientCrystallizerDataPacket.class, ClientCrystallizerDataPacket::decode);
        registerServerToClient(ClientBlastFurnaceDataPacket.class, ClientBlastFurnaceDataPacket::decode);
        registerServerToClient(ClientReactorControllerDataPacket.class, ClientReactorControllerDataPacket::decode);
        registerServerToClient(ClientReactorRenderDataPacket.class, ClientReactorRenderDataPacket::decode);
        registerServerToClient(ClientEnergyTankRenderDataPacket.class, ClientEnergyTankRenderDataPacket::decode);
        registerServerToClient(ClientFluidTankRenderDataPacket.class, ClientFluidTankRenderDataPacket::decode);
        registerServerToClient(ClientReactorParticleDataPacket.class, ClientReactorParticleDataPacket::decode);
        registerServerToClient(ClientTurbineControllerDataPacket.class, ClientTurbineControllerDataPacket::decode);
        registerServerToClient(ClientThermalConversionsPacket.class, ClientThermalConversionsPacket::decode);

        registerClientToServer(ReactorOpenContainerPacket.class, ReactorOpenContainerPacket::decode);
        registerClientToServer(ReactorControllerScramPacket.class, ReactorControllerScramPacket::decode);
        registerClientToServer(ReactorControllerRemoveTurbinePacket.class, ReactorControllerRemoveTurbinePacket::decode);
        registerClientToServer(ReactorControllerCopyTurbinePacket.class, ReactorControllerCopyTurbinePacket::decode);
        registerClientToServer(TurbineCoilsPacket.class, TurbineCoilsPacket::decode);
        registerClientToServer(TurbineActivePacket.class, TurbineActivePacket::decode);
        registerClientToServer(TurbineFlowPacket.class, TurbineFlowPacket::decode);
        registerClientToServer(ReactorControllerRodInsertPacket.class, ReactorControllerRodInsertPacket::decode);
        registerClientToServer(ReactorControllerLoadPacket.class, ReactorControllerLoadPacket::decode);
        registerClientToServer(ReactorControllerTemperaturePacket.class, ReactorControllerTemperaturePacket::decode);
        registerClientToServer(ReactorControllerStatePacket.class, ReactorControllerStatePacket::decode);
        registerClientToServer(ReactorControllerChangedPacket.class, ReactorControllerChangedPacket::decode);
        registerClientToServer(MachineRedstoneModePacket.class, MachineRedstoneModePacket::decode);
        registerClientToServer(MachinePowerablePacket.class, MachinePowerablePacket::decode);
        registerClientToServer(MachineDumpModePacket.class, MachineDumpModePacket::decode);
        registerClientToServer(MachineChangedPacket.class, MachineChangedPacket::decode);
    }

    public static <MSG extends IPacket> void registerServerToClient(Class<MSG> packet, Function<FriendlyByteBuf, MSG> decode) {
        CHANNEL.registerMessage(index++, packet, IPacket::encode, decode, IPacket::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
    }

    public static <MSG extends IPacket> void registerClientToServer(Class<MSG> packet, Function<FriendlyByteBuf, MSG> decode) {
        CHANNEL.registerMessage(index++, packet, IPacket::encode, decode, IPacket::handle, Optional.of(NetworkDirection.PLAY_TO_SERVER));
    }

    public static void sendToClient(Object packet, ServerPlayer player) {
        CHANNEL.sendTo(packet, player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }

    public static void sendToServer(Object packet) {
        CHANNEL.sendToServer(packet);
    }

    public static void sendTo(Object packet, ServerPlayer player) {
        if (!(player instanceof FakePlayer)) {
            CHANNEL.sendTo(packet, player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
        }
    }
}
