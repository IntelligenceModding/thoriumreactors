package unhappycodings.thoriumreactors.common.blockentity.turbine;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import unhappycodings.thoriumreactors.common.block.reactor.ReactorControllerBlock;
import unhappycodings.thoriumreactors.common.block.turbine.TurbineControllerBlock;
import unhappycodings.thoriumreactors.common.network.PacketHandler;
import unhappycodings.thoriumreactors.common.network.toclient.turbine.ClientTurbineControllerDataPacket;
import unhappycodings.thoriumreactors.common.registration.ModBlockEntities;
import unhappycodings.thoriumreactors.common.registration.ModDamageSources;
import unhappycodings.thoriumreactors.common.registration.ModFluids;
import unhappycodings.thoriumreactors.common.registration.ModSounds;
import unhappycodings.thoriumreactors.common.util.EnergyUtil;
import unhappycodings.thoriumreactors.common.util.FormattingUtil;

import java.util.List;
import java.util.Random;

public class TurbineControllerBlockEntity extends BlockEntity {
    private BlockPos valvePos = BlockPos.ZERO, powerPortPos = BlockPos.ZERO;
    private String warning = "";
    private boolean assembled, coilsEngaged, activated;
    private float rotation = 0, rpm = 0, lastRpm = 0, ticks = 0, targetFlowrate = 0, currentFlowrate = 0;
    private int turbineHeight;
    private float energyModifier;
    private long turbinetime = 0;

    public TurbineControllerBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.TURBINE_CONTROLLER.get(), pPos, pBlockState);
    }

    public void tick() {
        if (level.getGameTime() % 20 == 0) turbinetime++;
        if (level.getGameTime() % 40 == 0) {
            level.playSound(null, getBlockPos().relative(getBlockState().getValue(TurbineControllerBlock.FACING).getOpposite(), 2).relative(Direction.UP, 1), ModSounds.TURBINE_RUN.get(), SoundSource.BLOCKS, getRpm() / 2000f, 1f);
        }

        if (valvePos != null && powerPortPos != null && level.getBlockEntity(valvePos) instanceof TurbineValveBlockEntity valveBlockEntity && level.getBlockEntity(powerPortPos) instanceof TurbinePowerPortBlockEntity powerPortBlockEntity) {

            if (coilsEngaged) {
                if (powerPortBlockEntity.getEnergy() + (int) (getRpm() * FormattingUtil.getTurbineGenerationModifier(getRpm()) * getEnergyModifier()) < powerPortBlockEntity.getCapacity()) {
                    powerPortBlockEntity.setEnergy(powerPortBlockEntity.getEnergy() + (int) (getRpm() * FormattingUtil.getTurbineGenerationModifier(getRpm()) * getEnergyModifier()));
                    EnergyUtil.trySendToNeighbors(level, getBlockPos(), powerPortBlockEntity.getEnergyStorage(), powerPortBlockEntity.getEnergy(), (int) powerPortBlockEntity.getMaxEnergyTransfer());
                } else if (powerPortBlockEntity.getEnergy() != powerPortBlockEntity.getCapacity()) {
                    powerPortBlockEntity.setEnergy(powerPortBlockEntity.getCapacity());
                    EnergyUtil.trySendToNeighbors(level, getBlockPos(), powerPortBlockEntity.getEnergyStorage(), powerPortBlockEntity.getEnergy(), (int) powerPortBlockEntity.getMaxEnergyTransfer());
                }
            }

            if (activated && valveBlockEntity.getFluidIn().isFluidEqual(new FluidStack(ModFluids.SOURCE_STEAM.get(), 1)) && valveBlockEntity.getFluidAmountIn() >= 1) {
                float amount = Math.min(valveBlockEntity.getFluidAmountIn(), getTargetFlowrate());
                setCurrentFlowrate(amount);

                if (amount > 1) {
                    valveBlockEntity.setFluidIn(new FluidStack(valveBlockEntity.getFluidIn(), (int) (valveBlockEntity.getFluidAmountIn() - amount)));
                    setRpm(getRpm() + ((Math.abs(amount / 10f) * getTurbineRotationModifier(true))));
                }

                if (getRpm() - (1f - (Math.abs(amount / 5f) / 1000f)) >= 0 && (getRpm() > 250 || getTargetFlowrate() == 0)) {
                    setRpm(getRpm() - (1f - (Math.abs(amount / 5) / 1000f)));
                }

            } else {
                if (getRpm() > 0)
                    setRpm(getRpm() - getTurbineRotationModifier(false));
                setCurrentFlowrate(0);
            }

        } else {
            setAssembled(false);
            level.setBlockAndUpdate(getBlockPos(), getBlockState().setValue(TurbineControllerBlock.POWERED, false));
        }

        if (turbinetime == 3 && getRpm() > 50 && level.getGameTime() % 20 == 0) {
            float random = new Random().nextFloat();
            getLevel().playSound(null, getBlockPos().relative(getBlockState().getValue(TurbineControllerBlock.FACING).getOpposite(), 2).relative(Direction.UP, 1),
                    random < 0.2f ? ModSounds.TURBINE_HISS_1.get() : random < 0.8f ? ModSounds.TURBINE_HISS_2.get() : ModSounds.TURBINE_HISS_3.get(), SoundSource.BLOCKS, 1f, 1f);
            turbinetime++;
        } else if (turbinetime >= 4) {
            turbinetime = 0;
        }

        for (Player player : level.players())
            PacketHandler.sendToClient(new ClientTurbineControllerDataPacket(getBlockPos(), coilsEngaged, activated, targetFlowrate, currentFlowrate, rpm, turbinetime), (ServerPlayer) player);

        if (level.getGameTime() % 10 == 0) {
            turbinePlayerCheck();
        }

    }

    public void turbinePlayerCheck() {
        BlockPos p = getBlockPos().relative(getBlockState().getValue(ReactorControllerBlock.FACING).getOpposite(), 2);
        List<ServerPlayer> players = level.getEntitiesOfClass(ServerPlayer.class, new AABB(p.getX() -2, p.getY() -1, p.getZ() -2, p.getX() + 3, p.getY() + getTurbineHeight(), p.getZ() + 3));

        for (ServerPlayer player : players) {
            player.hurt(ModDamageSources.GRIND, Float.MAX_VALUE);
        }
    }

    private float getTurbineRotationModifier(boolean upwards) {
        float rpm = getRpm();
        if (upwards) {
            return rpm <= 20 ? 0.01f : 5 / rpm;
        } else {
            if (rpm > 1999f) return 16.5f;
            else if (rpm > 1498f) return 11.5f;
            else if (rpm > 1448f) return 10.9f;
            else if (rpm > 1400f) return 9.9f;
            else if (rpm > 1250f) return 8.4f;
            else if (rpm > 1100f) return 7.4f;
            else if (rpm > 950f) return 6.5f;
            else if (rpm > 800f) return 4.6f;
            else if (rpm > 650f) return 2.5f;
            else if (rpm > 500f) return 1.4f;
            else if (rpm > 350f) return 1f;
            else if (rpm > 200f) return 0.8f;
            else if (rpm > 100f) return 0.7f;
            else if (rpm > 45f) return 0.4f;
            else if (rpm > 25f) return 0.2f;
            return rpm - 0.002f >= 0 ? 0.002f : rpm;
        }
    }

    public float getTurbineGeneration() {
        return isCoilsEngaged() ? (float) Math.floor((getRpm() * (FormattingUtil.getTurbineGenerationModifier(getRpm()) * getEnergyModifier())) * 100) / 100 : 0;
    }

    public int getTurbineHeight() {
        return turbineHeight;
    }

    public void setTurbineHeight(int height) {
        turbineHeight = height;
    }

    public BlockState getState(BlockPos pos) {
        return this.level.getBlockState(pos);
    }

    public boolean isCoilsEngaged() {
        return coilsEngaged;
    }

    public void setCoilsEngaged(boolean coilsEngaged) {
        this.coilsEngaged = coilsEngaged;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public boolean isAssembled() {
        return assembled;
    }

    public void setAssembled(boolean assembled) {
        this.assembled = assembled;
    }

    public String getWarning() {
        return warning;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public float getRpm() {
        return rpm;
    }

    public void setRpm(float rpm) {
        this.rpm = rpm;
    }

    public float getLastRpm() {
        return lastRpm;
    }

    public void setLastRpm(float lastRpm) {
        this.lastRpm = lastRpm;
    }

    public float getTicks() {
        return ticks;
    }

    public void setTicks(float ticks) {
        this.ticks = ticks;
    }

    public void setValvePos(BlockPos valvePos) {
        this.valvePos = valvePos;
    }

    public BlockPos getValvePos() {
        return valvePos;
    }

    public void setPowerPortPos(BlockPos powerPortPos) {
        this.powerPortPos = powerPortPos;
    }

    public BlockPos getPowerPortPos() {
        return powerPortPos;
    }

    public float getEnergyModifier() {
        return energyModifier;
    }

    public void setEnergyModifier(float energyModifier) {
        this.energyModifier = energyModifier;
    }

    public long getTurbinetime() {
        return turbinetime;
    }

    public void setTurbinetime(long turbinetime) {
        this.turbinetime = turbinetime;
    }

    public float getTargetFlowrate() {
        return targetFlowrate;
    }

    public void setTargetFlowrate(float targetFlowrate) {
        this.targetFlowrate = targetFlowrate;
    }

    public float getCurrentFlowrate() {
        return currentFlowrate;
    }

    public void setCurrentFlowrate(float currentFlowrate) {
        this.currentFlowrate = currentFlowrate;
    }

    @NotNull
    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag nbt = new CompoundTag();
        nbt.putBoolean("Assembled", isAssembled());
        nbt.putBoolean("CoilsEngaged", isCoilsEngaged());
        nbt.putBoolean("Activated", isActivated());
        nbt.putInt("Height", getTurbineHeight());
        nbt.putLong("TurbineTime", getTurbinetime());
        nbt.putFloat("RPM", getRpm());
        nbt.putFloat("EnergyModifier", getEnergyModifier());
        nbt.putFloat("LastRPM", getLastRpm());
        nbt.putFloat("Rotation", getRotation());
        nbt.putFloat("Ticks", getTicks());
        nbt.putFloat("Flowrate", getTargetFlowrate());
        nbt.putFloat("CurrentFlowrate", getCurrentFlowrate());
        nbt.put("ValvePos", parsePosToTag(getValvePos()));
        nbt.put("PowerPortPos", parsePosToTag(getPowerPortPos()));
        return nbt;
    }

    @Override
    public void handleUpdateTag(final CompoundTag tag) {
        setAssembled(tag.getBoolean("Assembled"));
        setCoilsEngaged(tag.getBoolean("CoilsEngaged"));
        setActivated(tag.getBoolean("Activated"));
        setTurbineHeight(tag.getInt("Height"));
        setTurbinetime(tag.getLong("TurbineTime"));
        setEnergyModifier(tag.getFloat("EnergyModifier"));
        setRpm(tag.getFloat("RPM"));
        setLastRpm(tag.getFloat("LastRPM"));
        setTargetFlowrate(tag.getFloat("Flowrate"));
        setCurrentFlowrate(tag.getFloat("CurrentFlowrate"));
        setRotation(tag.getFloat("Rotation"));
        setTicks(tag.getFloat("Ticks"));
        setValvePos(BlockEntity.getPosFromTag(tag.getCompound("ValvePos")));
        setPowerPortPos(BlockEntity.getPosFromTag(tag.getCompound("PowerPortPos")));
    }

    @Override
    public void saveAdditional(@NotNull CompoundTag nbt) {
        nbt.putBoolean("Assembled", isAssembled());
        nbt.putBoolean("CoilsEngaged", isCoilsEngaged());
        nbt.putBoolean("Activated", isActivated());
        nbt.putInt("Height", getTurbineHeight());
        nbt.putLong("TurbineTime", getTurbinetime());
        nbt.putFloat("EnergyModifier", getEnergyModifier());
        nbt.putFloat("RPM", getRpm());
        nbt.putFloat("LastRPM", getLastRpm());
        nbt.putFloat("Flowrate", getTargetFlowrate());
        nbt.putFloat("CurrentFlowrate", getCurrentFlowrate());
        nbt.put("ValvePos", parsePosToTag(getValvePos()));
        nbt.put("PowerPortPos", parsePosToTag(getPowerPortPos()));
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        setAssembled(nbt.getBoolean("Assembled"));
        setCoilsEngaged(nbt.getBoolean("CoilsEngaged"));
        setActivated(nbt.getBoolean("Activated"));
        setTurbineHeight(nbt.getInt("Height"));
        setTurbinetime(nbt.getLong("TurbineTime"));
        setEnergyModifier(nbt.getFloat("EnergyModifier"));
        setRpm(nbt.getFloat("RPM"));
        setLastRpm(nbt.getFloat("LastRPM"));
        setTargetFlowrate(nbt.getFloat("Flowrate"));
        setCurrentFlowrate(nbt.getFloat("CurrentFlowrate"));
        setValvePos(BlockEntity.getPosFromTag(nbt.getCompound("ValvePos")));
        setPowerPortPos(BlockEntity.getPosFromTag(nbt.getCompound("PowerPortPos")));
    }

    public CompoundTag parsePosToTag(BlockPos pos) {
        CompoundTag position = new CompoundTag();
        position.putInt("x", pos.getX());
        position.putInt("y", pos.getY());
        position.putInt("z", pos.getZ());
        return position;
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        if (getLevel().isClientSide && net.getDirection() == PacketFlow.CLIENTBOUND) handleUpdateTag(pkt.getTag());
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public AABB getRenderBoundingBox() {
        return new AABB(getBlockPos().offset(-10, -10, -10), getBlockPos().offset(11, 11, 11));
    }

}
