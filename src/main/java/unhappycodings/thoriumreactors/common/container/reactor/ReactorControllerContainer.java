package unhappycodings.thoriumreactors.common.container.reactor;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.common.blockentity.machine.MachineBlastFurnaceBlockEntity;
import unhappycodings.thoriumreactors.common.blockentity.reactor.ReactorControllerBlockEntity;
import unhappycodings.thoriumreactors.common.container.base.container.BaseContainer;
import unhappycodings.thoriumreactors.common.network.PacketHandler;
import unhappycodings.thoriumreactors.common.network.toclient.ClientBlastFurnaceDataPacket;
import unhappycodings.thoriumreactors.common.network.toclient.ClientReactorControllerDataPacket;
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
                entity.getReactorPressure(), entity.getReactorState(), entity.getTurbineTargetSpeed(), entity.getTurbineCurrentSpeed(),
                entity.getTurbineTargetOverflowSet(), entity.getTurbineCurrentOverflowSet(), entity.getTurbineTargetLoadSet(), entity.getTurbineCurrentLoadSet(),
                entity.isTurbineCoilsEngaged(), entity.getTurbineCurrentFlow(), entity.getTurbinePowerGeneration()), (ServerPlayer) inventory.player);

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
