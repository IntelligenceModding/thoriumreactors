package unhappycodings.thoriumreactors.common.network.toclient.reactor;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.network.NetworkEvent;
import unhappycodings.thoriumreactors.common.blockentity.reactor.ReactorControllerBlockEntity;
import unhappycodings.thoriumreactors.common.enums.ReactorStateEnum;
import unhappycodings.thoriumreactors.common.network.base.IPacket;

import java.util.ArrayList;
import java.util.List;

public class ClientReactorControllerDataPacket implements IPacket {
    private final BlockPos pos;
    // Reactor
    private final float reactorTargetTemperature; // 0-3000
    private final float reactorCurrentTemperature; // 0-3000
    private final byte reactorTargetLoadSet; // 0-100%
    private final byte reactorCurrentLoadSet; // 0-100%
    private final long reactorRunningSince; // timestamp
    private final float reactorStatus; // 0-100%
    private final float reactorContainment; // 0-100%
    private final float reactorRadiation; // uSv per hour
    private final float reactorPressure; // in PSI
    private final int reactorHeight; // in PSI
    private final ReactorStateEnum reactorState; // STARTING - RUNNING - STOP
    private final FluidStack fluidIn;
    private final FluidStack fluidOut;
    private final String notification;
    public boolean isReactorActive;
    public boolean isExchangerActive;
    public boolean isTurbineActive;
    public int reactorCapacity;
    // Rods
    public final byte[] fuelRodStatus;
    public final byte[] depletedfuelRodStatus;
    public final byte[] controlRodStatus;
    // Turbine
    private final List<BlockPos> turbinePositions;
    private final byte turbineSpeed; // 0-3000
    private final byte turbineTargetFlow; // 0-100%
    private final byte turbineCurrentFlow; // 0-100%
    private final boolean turbineCoilsEngaged; // true-false
    private final boolean turbineActivated; // true-false
    private final long turbinePowerGeneration; // FE per tick

    public ClientReactorControllerDataPacket(BlockPos pos, float reactorTargetTemperature, float reactorCurrentTemperature, byte reactorTargetLoadSet, byte reactorCurrentLoadSet,
                                             long reactorRunningSince, float reactorStatus, float reactorContainment, float reactorRadiation,
                                             float reactorPressure, int reactorHeight, ReactorStateEnum reactorState, boolean turbineActivated, boolean turbineCoilsEngaged,
                                             byte turbineTargetFlow, byte turbineCurrentFlow, long turbinePowerGeneration, byte turbineSpeed, byte[] depletedfuelRodStatus, byte[] fuelRodStatus, byte[] controlRodStatus,
                                             FluidStack fluidIn, FluidStack fluidOut, String notification, boolean isReactorActive, boolean isTurbineActive, boolean isExchangerActive, int reactorCapacity, List<BlockPos> turbinePositions) {
        this.pos = pos;
        this.reactorTargetTemperature = reactorTargetTemperature;
        this.reactorCurrentTemperature = reactorCurrentTemperature;
        this.reactorTargetLoadSet = reactorTargetLoadSet;
        this.reactorCurrentLoadSet = reactorCurrentLoadSet;
        this.reactorRunningSince = reactorRunningSince;
        this.reactorStatus = reactorStatus;
        this.reactorContainment = reactorContainment;
        this.reactorRadiation = reactorRadiation;
        this.reactorPressure = reactorPressure;
        this.reactorHeight = reactorHeight;
        this.reactorState = reactorState;
        this.turbinePositions = turbinePositions;
        this.turbineSpeed = turbineSpeed;
        this.turbineActivated = turbineActivated;
        this.turbineCoilsEngaged = turbineCoilsEngaged;
        this.turbineCurrentFlow = turbineCurrentFlow;
        this.turbineTargetFlow = turbineTargetFlow;
        this.turbinePowerGeneration = turbinePowerGeneration;
        this.depletedfuelRodStatus = depletedfuelRodStatus;
        this.fuelRodStatus = fuelRodStatus;
        this.controlRodStatus = controlRodStatus;
        this.fluidIn = fluidIn;
        this.fluidOut = fluidOut;
        this.notification = notification;
        this.isReactorActive = isReactorActive;
        this.isTurbineActive = isTurbineActive;
        this.isExchangerActive = isExchangerActive;
        this.reactorCapacity = reactorCapacity;

    }

    public static ClientReactorControllerDataPacket decode(FriendlyByteBuf buffer) {
        return new ClientReactorControllerDataPacket(buffer.readBlockPos(), buffer.readFloat(), buffer.readFloat(), buffer.readByte(), buffer.readByte(), buffer.readLong(),
                buffer.readFloat(), buffer.readFloat(), buffer.readFloat(), buffer.readFloat(), buffer.readInt(), buffer.readEnum(ReactorStateEnum.class),
                buffer.readBoolean(), buffer.readBoolean(), buffer.readByte(), buffer.readByte(), buffer.readLong(), buffer.readByte(), buffer.readByteArray(), buffer.readByteArray(),
                buffer.readByteArray(), buffer.readFluidStack(), buffer.readFluidStack(), buffer.readUtf(),
                buffer.readBoolean(), buffer.readBoolean(), buffer.readBoolean(), buffer.readInt(), buffer.readList(FriendlyByteBuf::readBlockPos));
    }

    @SuppressWarnings("ConstantConditions")
    public void handle(NetworkEvent.Context context) {
        LocalPlayer player = Minecraft.getInstance().player;
        BlockEntity machine = player.level.getBlockEntity(pos);
        if (!(machine instanceof ReactorControllerBlockEntity blockEntity)) return;
        blockEntity.setReactorTargetTemperature(reactorTargetTemperature);
        blockEntity.setReactorCurrentTemperature(reactorCurrentTemperature);
        blockEntity.setReactorTargetLoadSet(reactorTargetLoadSet);
        blockEntity.setReactorCurrentLoadSet(reactorCurrentLoadSet);
        blockEntity.setReactorRunningSince(reactorRunningSince);
        blockEntity.setReactorStatus(reactorStatus);
        blockEntity.setReactorContainment(reactorContainment);
        blockEntity.setReactorRadiation(reactorRadiation);
        blockEntity.setReactorPressure(reactorPressure);
        blockEntity.setReactorHeight(reactorHeight);
        blockEntity.setReactorState(reactorState);
        // Rod
        blockEntity.setDepletedFuelRodStatus(depletedfuelRodStatus);
        blockEntity.setFuelRodStatus(fuelRodStatus);
        blockEntity.setControlRodStatus(controlRodStatus);
        // Turbine
        blockEntity.setTurbineActivated(turbineActivated);
        blockEntity.setTurbineCoilsEngaged(turbineCoilsEngaged);
        blockEntity.setTurbineTargetFlow(turbineTargetFlow);
        blockEntity.setTurbineCurrentFlow(turbineCurrentFlow);
        blockEntity.setTurbinePowerGeneration(turbinePowerGeneration);
        blockEntity.setTurbineSpeed(turbineSpeed);

        blockEntity.setFluidIn(fluidIn);
        blockEntity.setFluidOut(fluidOut);
        blockEntity.setNotification(notification);
        blockEntity.setReactorActive(isReactorActive);
        blockEntity.setTurbineActive(isTurbineActive);
        blockEntity.setExchangerActive(isExchangerActive);
        blockEntity.setReactorCapacity(reactorCapacity);
        // Turbine
        blockEntity.setTurbinePos(turbinePositions);
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(pos);
        buffer.writeFloat(reactorTargetTemperature);
        buffer.writeFloat(reactorCurrentTemperature);
        buffer.writeByte(reactorTargetLoadSet);
        buffer.writeByte(reactorCurrentLoadSet);
        buffer.writeLong(reactorRunningSince);
        buffer.writeFloat(reactorStatus);
        buffer.writeFloat(reactorContainment);
        buffer.writeFloat(reactorRadiation);
        buffer.writeFloat(reactorPressure);
        buffer.writeInt(reactorHeight);
        buffer.writeEnum(reactorState);
        // Turbine
        buffer.writeBoolean(turbineActivated);
        buffer.writeBoolean(turbineCoilsEngaged);
        buffer.writeByte(turbineTargetFlow);
        buffer.writeByte(turbineCurrentFlow);
        buffer.writeLong(turbinePowerGeneration);
        buffer.writeByte(turbineSpeed);
        // Rod
        buffer.writeByteArray(depletedfuelRodStatus);
        buffer.writeByteArray(fuelRodStatus);
        buffer.writeByteArray(controlRodStatus);

        buffer.writeFluidStack(fluidIn);
        buffer.writeFluidStack(fluidOut);
        buffer.writeUtf(notification);
        buffer.writeBoolean(isReactorActive);
        buffer.writeBoolean(isTurbineActive);
        buffer.writeBoolean(isExchangerActive);
        buffer.writeInt(reactorCapacity);
        // Turbine
        buffer.writeCollection(turbinePositions != null ? turbinePositions : new ArrayList<>(), FriendlyByteBuf::writeBlockPos);
    }
}
