package unhappycodings.thoriumreactors.common.container.reactor;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.common.blockentity.reactor.ReactorControllerBlockEntity;
import unhappycodings.thoriumreactors.common.container.base.container.BaseContainer;
import unhappycodings.thoriumreactors.common.network.PacketHandler;
import unhappycodings.thoriumreactors.common.network.toclient.reactor.ClientReactorControllerDataPacket;
import unhappycodings.thoriumreactors.common.registration.ModContainerTypes;

public class ReactorControllerContainer extends BaseContainer {
    public final Inventory inventory;

    public ReactorControllerContainer(int id, Inventory inventory, BlockPos pos, Level level, int containerSize) {
        super(ModContainerTypes.REACTOR_CONTROLLER_CONTAINER.get(), id, inventory, pos, level, containerSize);
        this.inventory = inventory;

    }

    @Override
    public void broadcastChanges() {
        ReactorControllerBlockEntity entity = (ReactorControllerBlockEntity) this.tileEntity;

        PacketHandler.sendToClient(new ClientReactorControllerDataPacket(entity.getBlockPos(), entity.getReactorTargetTemperature(), entity.getReactorCurrentTemperature(), entity.getReactorTargetLoadSet(), entity.getReactorCurrentLoadSet(),
                entity.getReactorRunningSince(), entity.getReactorStatus(), entity.getReactorContainment(), entity.getReactorRadiation(),
                entity.getReactorPressure(), entity.getReactorHeight(), entity.getReactorState(), entity.getTurbineTargetSpeed(), entity.getTurbineCurrentSpeed(),
                entity.getTurbineTargetOverflowSet(), entity.getTurbineCurrentOverflowSet(), entity.getTurbineTargetLoadSet(), entity.getTurbineCurrentLoadSet(),
                entity.isTurbineCoilsEngaged(), entity.getTurbineCurrentFlow(), entity.getTurbinePowerGeneration(), entity.getFuelRodStatus(), entity.getControlRodStatus(),
                entity.getFluidIn(), entity.getFluidOut(), entity.getNotification(), entity.isReactorActive(), entity.isTurbineActive(), entity.isExchangerActive(), entity.getReactorCapacity()), (ServerPlayer) inventory.player);

        super.broadcastChanges();
    }

    public ReactorControllerBlockEntity getTile() {
        return (ReactorControllerBlockEntity) this.tileEntity;
    }

    @Override
    public boolean stillValid(@NotNull Player playerIn) {
        return true;
    }
}
