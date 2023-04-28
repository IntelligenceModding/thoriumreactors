package unhappycodings.thoriumreactors.common.network.toclient;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.network.NetworkEvent;
import unhappycodings.thoriumreactors.common.ReactorStateEnum;
import unhappycodings.thoriumreactors.common.blockentity.machine.MachineBlastFurnaceBlockEntity;
import unhappycodings.thoriumreactors.common.blockentity.reactor.ReactorControllerBlockEntity;
import unhappycodings.thoriumreactors.common.network.base.IPacket;

import java.util.Properties;

public class ClientReactorControllerDataPacket implements IPacket {
    private final BlockPos pos;
    /*
    short: -32 768 and 32 767
    byte: -128 and 127
     */
    // Reactor
    private final short reactorTargetTemperature; // 0-3000
    private final short reactorCurrentTemperature; // 0-3000
    private final byte reactorTargetLoadSet; // 0-100%
    private final byte reactorCurrentLoadSet; // 0-100%
    private final long reactorRunningSince; // timestamp
    private final byte reactorStatus; // 0-100%
    private final float reactorContainment; // 0-100%
    private final float reactorRadiation; // uSv per hour
    private final float reactorPressure; // in PSI
    private final ReactorStateEnum reactorState; // STARTING - RUNNING - STOP
    // Turbine
    private final short turbineTargetSpeed; // RPM
    private final short turbineCurrentSpeed; // RPM
    private final byte turbineTargetOverflowSet; // 0-100%
    private final byte turbineCurrentOverflowSet; // 0-100%
    private final byte turbineTargetLoadSet; // 0-100%
    private final byte turbineCurrentLoadSet; // 0-100%
    private final boolean turbineCoilsEngaged; // true-false
    private final short turbineCurrentFlow; // Buckets per second
    private final long turbinePowerGeneration; // FE per tick

    public ClientReactorControllerDataPacket(BlockPos pos, short reactorTargetTemperature, short reactorCurrentTemperature, byte reactorTargetLoadSet, byte reactorCurrentLoadSet,
                                             long reactorRunningSince, byte reactorStatus, float reactorContainment, float reactorRadiation,
                                             float reactorPressure, ReactorStateEnum reactorState, short turbineTargetSpeed, short turbineCurrentSpeed,
                                             byte turbineTargetOverflowSet, byte turbineCurrentOverflowSet, byte turbineTargetLoadSet, byte turbineCurrentLoadSet,
                                             boolean turbineCoilsEngaged, short turbineCurrentFlow, long turbinePowerGeneration) {
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
        this.reactorState = reactorState;
        this.turbineTargetSpeed = turbineTargetSpeed;
        this.turbineCurrentSpeed = turbineCurrentSpeed;
        this.turbineTargetOverflowSet = turbineTargetOverflowSet;
        this.turbineCurrentOverflowSet = turbineCurrentOverflowSet;
        this.turbineTargetLoadSet = turbineTargetLoadSet;
        this.turbineCurrentLoadSet = turbineCurrentLoadSet;
        this.turbineCoilsEngaged = turbineCoilsEngaged;
        this.turbineCurrentFlow = turbineCurrentFlow;
        this.turbinePowerGeneration = turbinePowerGeneration;
    }

    public static ClientReactorControllerDataPacket decode(FriendlyByteBuf buffer) {
        return new ClientReactorControllerDataPacket(buffer.readBlockPos(), buffer.readShort(), buffer.readShort(), buffer.readByte(), buffer.readByte(), buffer.readLong(),
                buffer.readByte(), buffer.readFloat(), buffer.readFloat(), buffer.readFloat(), buffer.readEnum(ReactorStateEnum.class),
                buffer.readShort(), buffer.readShort(), buffer.readByte(), buffer.readByte(), buffer.readByte(), buffer.readByte(), buffer.readBoolean(),
                buffer.readShort(), buffer.readLong());
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
        blockEntity.setReactorState(reactorState);
        // Turbine
        blockEntity.setTurbineTargetSpeed(turbineTargetSpeed);
        blockEntity.setTurbineCurrentSpeed(turbineCurrentSpeed);
        blockEntity.setTurbineTargetOverflowSet(turbineTargetOverflowSet);
        blockEntity.setTurbineCurrentOverflowSet(turbineCurrentOverflowSet);
        blockEntity.setTurbineTargetLoadSet(turbineTargetLoadSet);
        blockEntity.setTurbineCurrentLoadSet(turbineCurrentLoadSet);
        blockEntity.setTurbineCoilsEngaged(turbineCoilsEngaged);
        blockEntity.setTurbineCurrentFlow(turbineCurrentFlow);
        blockEntity.setTurbinePowerGeneration(turbinePowerGeneration);
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(pos);
        buffer.writeShort(reactorTargetTemperature);
        buffer.writeShort(reactorCurrentTemperature);
        buffer.writeByte(reactorTargetLoadSet);
        buffer.writeByte(reactorCurrentLoadSet);
        buffer.writeLong(reactorRunningSince);
        buffer.writeByte(reactorStatus);
        buffer.writeFloat(reactorContainment);
        buffer.writeFloat(reactorRadiation);
        buffer.writeFloat(reactorPressure);
        buffer.writeEnum(reactorState);
        // Turbine
        buffer.writeShort(turbineTargetSpeed);
        buffer.writeShort(turbineCurrentSpeed);
        buffer.writeByte(turbineTargetOverflowSet);
        buffer.writeByte(turbineCurrentOverflowSet);
        buffer.writeByte(turbineTargetLoadSet);
        buffer.writeByte(turbineCurrentLoadSet);
        buffer.writeBoolean(turbineCoilsEngaged);
        buffer.writeShort(turbineCurrentFlow);
        buffer.writeLong(turbinePowerGeneration);
    }
}
