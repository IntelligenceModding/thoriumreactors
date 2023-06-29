package unhappycodings.thoriumreactors.common.blockentity.reactor;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import unhappycodings.thoriumreactors.common.block.reactor.ReactorControllerBlock;
import unhappycodings.thoriumreactors.common.block.reactor.ReactorCoreBlock;
import unhappycodings.thoriumreactors.common.block.reactor.ReactorValveBlock;
import unhappycodings.thoriumreactors.common.blockentity.ModFluidTank;
import unhappycodings.thoriumreactors.common.container.reactor.ReactorControllerContainer;
import unhappycodings.thoriumreactors.common.enums.ParticleTypeEnum;
import unhappycodings.thoriumreactors.common.enums.ReactorStateEnum;
import unhappycodings.thoriumreactors.common.enums.ValveTypeEnum;
import unhappycodings.thoriumreactors.common.network.PacketHandler;
import unhappycodings.thoriumreactors.common.network.toclient.reactor.ClientReactorParticleDataPacket;
import unhappycodings.thoriumreactors.common.network.toclient.reactor.ClientReactorRenderDataPacket;
import unhappycodings.thoriumreactors.common.registration.*;
import unhappycodings.thoriumreactors.common.util.CalculationUtil;
import unhappycodings.thoriumreactors.common.util.SoundUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ReactorControllerBlockEntity extends BlockEntity implements MenuProvider {
    public static final int MAX_HEAT = 971;
    public List<BlockPos> framePosList = new ArrayList<>();
    public BlockPos floorPosA, floorPosB, floorPosARight, floorPosBLeft;
    public BlockPos ceilingPosA, ceilingPosB, ceilingPosARight, ceilingPosBLeft;
    public int pillarAHeight, pillarARightHeight, pillarBHeight, pillarBLeftHeight;
    public int floorPosLength, floorPosClockwise, floorPosCounterClockwise;
    public int reactorXLenght, reactorYLenght;
    public BlockPos reactorCorePos;
    public List<BlockPos> moderatorPos;
    public List<BlockPos> valvePos;
    public BlockPos controlRodPos;
    public boolean canBeAssembled;
    public boolean assembled;
    public String warning = "";
    public String notification = "";
    public boolean isReactorActive;
    public boolean isExchangerActive;
    public boolean isTurbineActive;
    public int reactorCapacity;
    int controllers = 0;
    long soundTicks;

    // Reactor
    private short reactorCurrentTemperature; // 0-3000
    private short reactorTargetTemperature; // 0-3000
    private byte reactorTargetLoadSet; // 0-100%
    private byte reactorCurrentLoadSet; // 0-100%
    private long reactorRunningSince; // timestamp
    private byte reactorStatus = 100; // 0-100%
    private float reactorContainment; // 0-100%
    private float reactorRadiation; // uSv per hour
    private float reactorPressure = 29.98f; // in PSI
    private int fuelAdditions = 0;
    private boolean scrammed;
    private ReactorStateEnum reactorState = ReactorStateEnum.STOP; // STARTING - RUNNING - STOP
    // Rods
    public byte[] fuelRodStatus = new byte[81];
    public byte[] controlRodStatus = new byte[64];
    // Turbine
    private short turbineTargetSpeed; // RPM
    private short turbineCurrentSpeed; // RPM
    private byte turbineTargetOverflowSet; // 0-100%
    private byte turbineCurrentOverflowSet; // 0-100%
    private byte turbineTargetLoadSet; // 0-100%
    private byte turbineCurrentLoadSet; // 0-100%
    private boolean turbineCoilsEngaged; // true-false
    private short turbineCurrentFlow; // Buckets per second
    private long turbinePowerGeneration; // FE per tick

    private ModFluidTank FLUID_TANK_IN = new ModFluidTank(35000, true, true, 0, FluidStack.EMPTY) {
        @Override
        public int getCapacity() {
            return reactorCapacity / 2;
        }
        @Override
        public int getSpace() {
            return super.getSpace();
        }
    };
    private ModFluidTank FLUID_TANK_OUT = new ModFluidTank(35000, true, true, 0, FluidStack.EMPTY) {
        @Override
        public int getCapacity() {
            return reactorCapacity / 2;
        }
        @Override
        public int getSpace() {
            return getCapacity() - getFluidAmount();
        }
    };

    public ReactorControllerBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.REACTOR_CONTROLLER.get(), pPos, pBlockState);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        updateBlock();
    }

    public void tick() {
        BlockPos controllerPos = this.getBlockPos();

        if (getBlockState().getValue(ReactorControllerBlock.POWERED) != assembled)
            getLevel().setBlock(getBlockPos(), getBlockState().setValue(ReactorControllerBlock.POWERED, assembled), 3);
        if (isCasing(getState(controllerPos.below())) && level.getGameTime() % 10 == 0) {
            Direction controllerDirection = this.getBlockState().getValue(ReactorControllerBlock.FACING).getOpposite();

            // floor check if square, casings and in min and max sizes
            updateFloorPositions(controllerPos, controllerDirection);
            if (floorPosA == BlockPos.ZERO || floorPosB == BlockPos.ZERO) return;
            if (!canBeAssembled) { resetAssembled(); return;  }

            // Pillar check for all 4 corners
            updatePillarPositions(controllerDirection, floorPosA, floorPosB);
            if (!canBeAssembled) { resetAssembled(); return;  }

            // Ceiling check, if surrounded by casings with rod in mid
            updateCeilingPositions();
            if (!canBeAssembled) { resetAssembled(); return;  }

            // Inner check, if graphite moderators and core are present
            updateInnerPositions();
            if (!canBeAssembled) { resetAssembled(); return;  }

            // Inner check, if graphite moderators and core are present
            updateWallPositions();
            if (!canBeAssembled) { resetAssembled(); return;  }

            // Check if Core is 2 blocks relative to Controller (middle of floor)
            BlockPos p = getBlockPos().relative(getBlockState().getValue(ReactorControllerBlock.FACING).getOpposite(), 2);
            if (!level.getBlockState(p).is(ModBlocks.REACTOR_CORE.get())) { resetAssembled(); return;  }

            // If everything is assembled right, we continue here
            if (!assembled) {
                for (Player player : level.players()) {
                    PacketHandler.sendToClient(new ClientReactorParticleDataPacket(addParticleOffset(getBlockPos(), getBlockState().getValue(ReactorControllerBlock.FACING)), ParticleTypeEnum.REACTOR, reactorXLenght, getReactorHeight() + 1, reactorYLenght), (ServerPlayer) player);
                }
            }
            reactorCapacity = (3 * 3 * (getReactorHeight() - 1) * 1000) - 1000;
            assembled = true;
        }

        if (isAssembled()) {
            tryTankReactor(); // Molten Salt input from configured valves
            tryOutputTank(); // Output of Heated Molten Salt into configures valves
            tryFuelReactor(); // Input of Enriched Uranium Pellets from configures valves
            updateTemperature(); // Temperature Simulation
            if (new Random().nextFloat() < 0.01) setReactorPressure(getReactorPressure() == 29.98f ? 29.97f : 29.98f); // Randomly changes reactor pressure a bit
            if (getFluidAmountIn() > 0 || getFluidAmountOut() > 0) radiationPlayerCheck(); // Kills players inside the reactor if its fueled

            // If scrammed, do simulation
            if (isScrammed()) doScramSimulation();
            if (getReactorState() != ReactorStateEnum.STOP) doReactorSimulation();
            else {setReactorActive(false);setCoreHeating(false);}

            updateRenderData();
            updateBlock();
        }

        BlockPos corePos = getBlockPos().relative(getBlockState().getValue(ReactorControllerBlock.FACING).getOpposite(), 2);
        if (getReactorState() != ReactorStateEnum.STOP) {
            if (soundTicks == 0) {
                stopReactorSound(ModSounds.REACTOR_RUN.get());
                stopReactorSound(ModSounds.REACTOR_SHUTDOWN.get());
                level.playSound(null, corePos, ModSounds.REACTOR_STARTUP.get(), SoundSource.BLOCKS, 1f, 1f);
            }
            if (soundTicks % 92 == 0 && soundTicks != 0) {
                stopReactorSound(ModSounds.REACTOR_STARTUP.get());
                stopReactorSound(ModSounds.REACTOR_SHUTDOWN.get());
                level.playSound(null, corePos, ModSounds.REACTOR_RUN.get(), SoundSource.BLOCKS, 1f, 1f);
            }
            soundTicks++;
        } else {
            if (soundTicks > 0) {
                stopReactorSound(ModSounds.REACTOR_STARTUP.get());
                stopReactorSound(ModSounds.REACTOR_RUN.get());
                level.playSound(null, corePos, ModSounds.REACTOR_SHUTDOWN.get(), SoundSource.BLOCKS, 1f, 1f);
            }
            soundTicks = 0;
        }
        if (soundTicks == 9199) soundTicks = 92;
    }

    public void stopReactorSound(SoundEvent type) {
        SoundUtil.stopSound(type, SoundSource.BLOCKS, getBlockPos(), level);
    }

    public BlockPos addParticleOffset(BlockPos pos, Direction direction) {
        return switch (direction) {
            case WEST -> pos.offset(0, -1, -2);
            case EAST -> pos.offset(-4, -1, -2);
            case SOUTH -> pos.offset(-2, -1, -4);
            default -> pos.offset(-2, -1, 0);
        };
    }

    public void scram(String text) {
        setScrammed(true);
        setNotification(text);
        level.playSound(null, getBlockPos(), ModSounds.ALARM_2.get(), SoundSource.BLOCKS, 2f, 1f);
    }

    public void doScramSimulation() {
        setReactorRunningSince(-1);
        setReactorActive(false);
        setCoreHeating(false);
        for (byte i = 0; i < 64; i++)
            if ((byte) getControlRodStatus(i) < 100)
                setControlRodStatus(i, (byte) ((byte) getControlRodStatus(i) + 1));
        if (level.getGameTime() % 60 == 0) level.playSound(null, getBlockPos(), ModSounds.ALARM_1.get(), SoundSource.BLOCKS, 1f, 1f);
        if (level.getGameTime() % 40 == 0) level.playSound(null, getBlockPos(), ModSounds.ALARM_2.get(), SoundSource.BLOCKS, 2f, 1f);
        setReactorState(ReactorStateEnum.STOP);
        updateBlock();
    }

    public void doReactorSimulation() {
        setReactorRunningSince(getReactorRunningSince() + 1);
        if (getFluidAmountIn() < 8000 && !isScrammed()) {
            scram("Warning: Emergency Scram! Molten Salt level low");
            return;
        } else if (getNotification().equals("Warning: Emergency Scram! Molten Salt level low")) {
            setNotification("");
        }

        setCoreHeating(true);
        setReactorActive(true);

        if (getReactorCurrentTemperature() > 100 && getReactorState() == ReactorStateEnum.RUNNING) {
            int modifier = (int) Math.floor(getReactorCurrentTemperature() / 50f);
            int amount = getFluidSpaceOut() > modifier ? (getFluidAmountIn() >= modifier ? modifier : getFluidAmountIn()) : (getFluidSpaceOut() >= modifier ? modifier : getFluidSpaceOut());

            if (FLUID_TANK_OUT.getFluid().isEmpty()) {
                FLUID_TANK_OUT.setFluid(new FluidStack(ModFluids.SOURCE_HEATED_MOLTEN_SALT.get(), amount));
                getFluidIn().shrink(amount);
            } else if (!FLUID_TANK_IN.getFluid().isEmpty()) {
                getFluidIn().shrink(amount);
                getFluidOut().grow(amount);
            }
        }
    }

    public void updateTemperature() {
        int fuelValue = 0, controlValue = 0;
        for (int i = 0; i < getFuelRodStatus().length; i++) fuelValue += getFuelRodStatus()[i];
        for (int i = 0; i < getControlRodStatus().length; i++) controlValue += getControlRodStatus()[i];
        float fuelValuePercent = (fuelValue / 8100f * 100f), controlValuePercent = (1f - (controlValue / 6400f));
        short targetTemperature = (short) (((fuelValuePercent * controlValuePercent) / 100f) * MAX_HEAT);
        short normalTemp = (short) (level.getBiome(getBlockPos()).is(Tags.Biomes.IS_COLD) ? 4 : 22);

        setReactorTargetTemperature(targetTemperature < normalTemp || getReactorState() == ReactorStateEnum.STOP ? normalTemp : targetTemperature);

        // Default reactor heating
        if (getReactorCurrentTemperature() + calculateTemperature(false) < getReactorTargetTemperature())
            setReactorCurrentTemperature((short) (getReactorCurrentTemperature() + calculateTemperature(false)));

        // Small step heating until target temperature
        else if (getReactorCurrentTemperature() < getReactorTargetTemperature() || getReactorCurrentTemperature() > getReactorTargetTemperature())
            setReactorCurrentTemperature((short) (getReactorCurrentTemperature() + (getReactorCurrentTemperature() < getReactorTargetTemperature() ? 1 : -1)));

    }

    public int calculateTemperature(boolean cooling) {
        if (getReactorCurrentTemperature() < MAX_HEAT / 3) {
            return cooling ? -1 : 8;
        } else if (getReactorCurrentTemperature() < MAX_HEAT / 2) {
            return cooling ? -2 : 5;
        } else {
            return cooling ? -4 : 2;
        }
    }

    public void setCoreHeating(boolean state) {
        BlockPos p = getBlockPos().relative(getBlockState().getValue(ReactorControllerBlock.FACING).getOpposite(), 2);
        if (level.getBlockState(p).is(ModBlocks.REACTOR_CORE.get()))
            level.setBlockAndUpdate(p, level.getBlockState(p).setValue(ReactorCoreBlock.HEATING, state));
    }

    public void setCoreFueled(boolean state) {
        BlockPos p = getBlockPos().relative(getBlockState().getValue(ReactorControllerBlock.FACING).getOpposite(), 2);
        if (level.getBlockState(p).is(ModBlocks.REACTOR_CORE.get()))
            level.setBlockAndUpdate(p, level.getBlockState(p).setValue(ReactorCoreBlock.FUELED, state));
    }

    public void radiationPlayerCheck() {
        BlockPos p = getBlockPos().relative(getBlockState().getValue(ReactorControllerBlock.FACING).getOpposite(), 2);
        List<ServerPlayer> players = level.getEntitiesOfClass(ServerPlayer.class, new AABB(p.getX() + -2, p.getY() + -1, p.getZ() + -2, p.getX() + 3, p.getY() + getReactorHeight(), p.getZ() + 3));

        for (ServerPlayer player : players) {
            player.hurt(ModDamageSources.OVERDOSIS, Float.MAX_VALUE);
        }
    }

    public void updateRenderData() {
        BlockPos p = getBlockPos();
        List<ServerPlayer> players = level.getEntitiesOfClass(ServerPlayer.class, new AABB(p.getX() + -18, p.getY() + -18, p.getZ() + -18, p.getX() + 18, p.getY() + 18, p.getZ() + 18));

        for (ServerPlayer player : players) {
            PacketHandler.sendToClient(new ClientReactorRenderDataPacket(getBlockPos(), getReactorHeight(), getFluidIn(), getFluidOut()), player);
        }
    }

    public void tryOutputTank() {
        if (valvePos != null) {
            for (BlockPos blockPos : valvePos) {
                if (!level.getBlockState(blockPos).is(ModBlocks.REACTOR_VALVE.get())) return;
                if (level.getBlockState(blockPos).getValue(ReactorValveBlock.TYPE) == ValveTypeEnum.FLUID_OUTPUT) {
                    level.getBlockEntity(blockPos).getCapability(ForgeCapabilities.FLUID_HANDLER).ifPresent(storage -> {
                        if (getFluidOut().isEmpty()) return;

                        int free = storage.getTankCapacity(0) - storage.getFluidInTank(0).getAmount();
                        int amount = free >= getFluidAmountOut() ? getFluidAmountOut() : free;
                        if (storage.getFluidInTank(0).isEmpty()) {
                            storage.fill(new FluidStack(ModFluids.SOURCE_HEATED_MOLTEN_SALT.get(), amount), IFluidHandler.FluidAction.EXECUTE);
                            getFluidOut().shrink(amount);
                        } else {
                            storage.getFluidInTank(0).grow(amount);
                            getFluidOut().shrink(amount);
                        }
                    });
                }
            }
        }
    }

    public void tryTankReactor() {
        if (valvePos != null) {
            for (BlockPos blockPos : valvePos) {
                if (!level.getBlockState(blockPos).is(ModBlocks.REACTOR_VALVE.get())) return;
                if (level.getBlockState(blockPos).getValue(ReactorValveBlock.TYPE) == ValveTypeEnum.FLUID_INPUT) {
                    level.getBlockEntity(blockPos).getCapability(ForgeCapabilities.FLUID_HANDLER).ifPresent(storage -> {
                        FluidStack fluidExternal = storage.getFluidInTank(0);
                        int amount = fluidExternal.getAmount();
                        if (fluidExternal.isFluidEqual(ModFluids.SOURCE_MOLTEN_SALT.get().getBucket().getDefaultInstance()) && amount > 0) {

                            if (FLUID_TANK_IN.getFluid().isEmpty()) {
                                FLUID_TANK_IN.setFluid(new FluidStack(ModFluids.SOURCE_MOLTEN_SALT.get(), amount));
                                storage.getFluidInTank(0).shrink(amount);
                            } else if (getFluidAmountIn() + amount <= getFluidCapacityIn()){
                                storage.getFluidInTank(0).shrink(amount);
                                getFluidIn().grow(amount);
                            }
                        }
                    });
                }

            }

        }
    }

    public void tryFuelReactor() {
        if (valvePos != null) {
            int fuelValue = 0;
            for (int i = 0; i < getFuelRodStatus().length; i++) fuelValue += getFuelRodStatus()[i];
            int fuelPercentage = (int) (fuelValue / 8100f * 100f);

            setCoreFueled(fuelValue > 0);

            for (BlockPos blockPos : valvePos) {
                if (!level.getBlockState(blockPos).is(ModBlocks.REACTOR_VALVE.get())) return;
                if (level.getBlockState(blockPos).is(ModBlocks.REACTOR_VALVE.get())) {
                    ReactorValveBlockEntity entity = (ReactorValveBlockEntity) level.getBlockEntity(blockPos);
                    if (fuelAdditions == 0 && fuelValue < 8100 && entity.getItem(0).is(ModItems.ENRICHED_URANIUM.get()) && fuelPercentage < getReactorTargetLoadSet() && level.getBlockState(blockPos).getValue(ReactorValveBlock.TYPE) == ValveTypeEnum.ITEM_INPUT) {
                        entity.getItem(0).shrink(1);
                        fuelAdditions = 20;
                    } else if (fuelPercentage >= getReactorTargetLoadSet() && level.getBlockState(blockPos).getValue(ReactorValveBlock.TYPE) == ValveTypeEnum.ITEM_OUTPUT) {
                        if (fuelAdditions == 20) {
                            if (entity.getItem(0).isEmpty())
                                entity.setItem(0, new ItemStack(ModItems.ENRICHED_URANIUM.get(), 1));
                            else
                                entity.getItem(0).grow(1);
                            fuelAdditions = 0;
                        }
                    }
                }
            }

            int randomNumber = new Random().nextInt(81);
            if (fuelAdditions != 0) {
                if (getFuelRodStatus((byte) randomNumber) < getReactorTargetLoadSet()) {
                    setFuelRodStatus((byte) randomNumber, (byte) (getFuelRodStatus((byte) randomNumber) + 1)) ;
                    fuelAdditions--;
                }
            }
            if (fuelPercentage >= getReactorTargetLoadSet()) {
                if (getFuelRodStatus((byte) randomNumber) > getReactorTargetLoadSet() && fuelAdditions < 20) {
                    setFuelRodStatus((byte) randomNumber, (byte) (getFuelRodStatus((byte) randomNumber) - 1)) ;
                    fuelAdditions++;
                }
            }
        }
    }

    @Override
    public AABB getRenderBoundingBox() {
        return new AABB(getBlockPos().offset(-10, -10, -10), getBlockPos().offset(11, 11, 11));
    }

    public int getReactorCapacity() {
        return reactorCapacity;
    }

    public void setReactorCapacity(int reactorCapacity) {
        this.reactorCapacity = reactorCapacity;
    }

    public boolean isReactorActive() {
        return isReactorActive;
    }

    public void setReactorActive(boolean reactorActive) {
        isReactorActive = reactorActive;
    }

    public boolean isTurbineActive() {
        return isTurbineActive;
    }

    public void setTurbineActive(boolean turbineActive) {
        isTurbineActive = turbineActive;
    }

    public boolean isExchangerActive() {
        return isExchangerActive;
    }

    public void setExchangerActive(boolean exchangerActive) {
        isExchangerActive = exchangerActive;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public FluidStack getFluidIn() {
        return FLUID_TANK_IN.getFluid();
    }

    public int getReactorHeight() {
        return pillarAHeight;
    }

    public void setReactorHeight(int height) {
        pillarAHeight = height;
    }

    public void setFluidIn(FluidStack stack) {
        FLUID_TANK_IN.setFluid(stack);
    }

    public int getFluidCapacityIn() {
        return FLUID_TANK_IN.getCapacity();
    }

    public int getFluidSpaceIn() {
        return FLUID_TANK_IN.getSpace();
    }

    public int getFluidAmountIn() {
        return FLUID_TANK_IN.getFluidAmount();
    }

    public FluidStack getFluidOut() {
        return FLUID_TANK_OUT.getFluid();
    }

    public void setFluidOut(FluidStack stack) {
        FLUID_TANK_OUT.setFluid(stack);
    }

    public int getFluidCapacityOut() {
        return FLUID_TANK_OUT.getCapacity();
    }

    public int getFluidSpaceOut() {
        return FLUID_TANK_OUT.getSpace();
    }

    public int getFluidAmountOut() {
        return FLUID_TANK_OUT.getFluidAmount();
    }

    @NotNull
    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag nbt = new CompoundTag();
        nbt.putBoolean("Assembled", isAssembled());
        // Reactor
        nbt.putShort("ReactorTargetTemperature", getReactorTargetTemperature());
        nbt.putShort("ReactorCurrentTemperature", getReactorCurrentTemperature());
        nbt.putByte("ReactorTargetLoadSet", getReactorTargetLoadSet());
        nbt.putByte("ReactorCurrentLoadSet", getReactorCurrentLoadSet());
        nbt.putLong("ReactorRunningSince", getReactorRunningSince());
        nbt.putByte("ReactorStatus", getReactorStatus());
        nbt.putFloat("ReactorContainment", getReactorContainment());
        nbt.putFloat("ReactorRadiation", getReactorRadiation());
        nbt.putFloat("ReactorPressure", getReactorPressure());
        nbt.putInt("FuelAdditions", getFuelAdditions());
        nbt.putBoolean("Scrammed", isScrammed());
        nbt.putString("ReactorState", getReactorState().toString());
        nbt.put("FluidIn", FLUID_TANK_IN.writeToNBT(new CompoundTag()));
        nbt.put("FluidOut", FLUID_TANK_OUT.writeToNBT(new CompoundTag()));
        nbt.putInt("ReactorCapacity", reactorCapacity);
        nbt.putLong("SoundTicks", soundTicks);
        // Rod
        nbt.putByteArray("FuelRodStatus", getFuelRodStatus());
        nbt.putByteArray("ControlRodStatus", getControlRodStatus());
        // Turbine
        nbt.putShort("TurbineTargetSpeed", getTurbineTargetSpeed());
        nbt.putShort("TurbineCurrentSpeed", getTurbineCurrentSpeed());
        nbt.putByte("TurbineTargetOverflowSet", getTurbineTargetOverflowSet());
        nbt.putByte("TurbineCurrentOverflowSet", getTurbineCurrentOverflowSet());
        nbt.putByte("TurbineTargetLoadSet", getTurbineTargetLoadSet());
        nbt.putByte("TurbineCurrentLoadSet", getTurbineCurrentLoadSet());
        nbt.putBoolean("TurbineCoilsEngaged", isTurbineCoilsEngaged());
        nbt.putShort("TurbineCurrentFlow", getTurbineCurrentFlow());
        nbt.putLong("TurbinePowerGeneration", getTurbinePowerGeneration());
        return nbt;
    }

    @Override
    public void handleUpdateTag(final CompoundTag tag) {
        setAssembled(tag.getBoolean("Assembled"));
        // Reactor
        setReactorTargetTemperature(tag.getShort("ReactorTargetTemperature"));
        setReactorCurrentTemperature(tag.getShort("ReactorCurrentTemperature"));
        setReactorTargetLoadSet(tag.getByte("ReactorTargetLoadSet"));
        setReactorCurrentLoadSet(tag.getByte("ReactorCurrentLoadSet"));
        setReactorRunningSince(tag.getLong("ReactorRunningSince"));
        setReactorStatus(tag.getByte("ReactorStatus"));
        setReactorContainment(tag.getFloat("ReactorContainment"));
        setReactorRadiation(tag.getFloat("ReactorRadiation"));
        setReactorPressure(tag.getFloat("ReactorPressure"));
        setFuelAdditions(tag.getInt("FuelAdditions"));
        setScrammed(tag.getBoolean("Scrammed"));
        setReactorState(ReactorStateEnum.get(tag.getString("ReactorState")));
        FLUID_TANK_IN.readFromNBT(tag.getCompound("FluidIn"));
        FLUID_TANK_OUT.readFromNBT(tag.getCompound("FluidOut"));
        reactorCapacity = tag.getInt("ReactorCapacity");
        soundTicks = tag.getLong("SoundTicks");
        // Rod
        setFuelRodStatus(tag.getByteArray("FuelRodStatus"));
        setControlRodStatus(tag.getByteArray("ControlRodStatus"));
        // Turbine
        setTurbineTargetSpeed(tag.getShort("TurbineTargetSpeed"));
        setTurbineCurrentSpeed(tag.getShort("TurbineCurrentSpeed"));
        setTurbineTargetOverflowSet(tag.getByte("TurbineTargetOverflowSet"));
        setTurbineCurrentOverflowSet(tag.getByte("TurbineCurrentOverflowSet"));
        setTurbineTargetLoadSet(tag.getByte("TurbineTargetLoadSet"));
        setTurbineCurrentLoadSet(tag.getByte("TurbineCurrentLoadSet"));
        setTurbineCoilsEngaged(tag.getBoolean("TurbineCoilsEngaged"));
        setTurbineCurrentFlow(tag.getShort("TurbineCurrentFlow"));
        setTurbinePowerGeneration(tag.getLong("TurbinePowerGeneration"));
    }

    @Override
    public void saveAdditional(@NotNull CompoundTag nbt) {
        nbt.putBoolean("Assembled", isAssembled());
        // Reactor
        nbt.putShort("ReactorTargetTemperature", getReactorTargetTemperature());
        nbt.putShort("ReactorCurrentTemperature", getReactorCurrentTemperature());
        nbt.putByte("ReactorTargetLoadSet", getReactorTargetLoadSet());
        nbt.putByte("ReactorCurrentLoadSet", getReactorCurrentLoadSet());
        nbt.putLong("ReactorRunningSince", getReactorRunningSince());
        nbt.putByte("ReactorStatus", getReactorStatus());
        nbt.putFloat("ReactorContainment", getReactorContainment());
        nbt.putFloat("ReactorRadiation", getReactorRadiation());
        nbt.putFloat("ReactorPressure", getReactorPressure());
        nbt.putInt("FuelAdditions", getFuelAdditions());
        nbt.putBoolean("Scrammed", isScrammed());
        nbt.putString("ReactorState", getReactorState().toString());
        nbt.put("FluidIn", FLUID_TANK_IN.writeToNBT(new CompoundTag()));
        nbt.put("FluidOut", FLUID_TANK_OUT.writeToNBT(new CompoundTag()));
        nbt.putInt("ReactorCapacity", reactorCapacity);
        nbt.putLong("SoundTicks", soundTicks);
        // Rod
        nbt.putByteArray("FuelRodStatus", getFuelRodStatus());
        nbt.putByteArray("ControlRodStatus", getControlRodStatus());
        // Turbine
        nbt.putShort("TurbineTargetSpeed", getTurbineTargetSpeed());
        nbt.putShort("TurbineCurrentSpeed", getTurbineCurrentSpeed());
        nbt.putByte("TurbineTargetOverflowSet", getTurbineTargetOverflowSet());
        nbt.putByte("TurbineCurrentOverflowSet", getTurbineCurrentOverflowSet());
        nbt.putByte("TurbineTargetLoadSet", getTurbineTargetLoadSet());
        nbt.putByte("TurbineCurrentLoadSet", getTurbineCurrentLoadSet());
        nbt.putBoolean("TurbineCoilsEngaged", isTurbineCoilsEngaged());
        nbt.putShort("TurbineCurrentFlow", getTurbineCurrentFlow());
        nbt.putLong("TurbinePowerGeneration", getTurbinePowerGeneration());
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        setAssembled(nbt.getBoolean("Assembled"));
        // Reactor
        setReactorTargetTemperature(nbt.getShort("ReactorTargetTemperature"));
        setReactorCurrentTemperature(nbt.getShort("ReactorCurrentTemperature"));
        setReactorTargetLoadSet(nbt.getByte("ReactorTargetLoadSet"));
        setReactorCurrentLoadSet(nbt.getByte("ReactorCurrentLoadSet"));
        setReactorRunningSince(nbt.getLong("ReactorRunningSince"));
        setReactorStatus(nbt.getByte("ReactorStatus"));
        setReactorContainment(nbt.getFloat("ReactorContainment"));
        setReactorRadiation(nbt.getFloat("ReactorRadiation"));
        setReactorPressure(nbt.getFloat("ReactorPressure"));
        setFuelAdditions(nbt.getInt("FuelAdditions"));
        setScrammed(nbt.getBoolean("Scrammed"));
        setReactorState(ReactorStateEnum.get(nbt.getString("ReactorState")));
        FLUID_TANK_IN.readFromNBT(nbt.getCompound("FluidIn"));
        FLUID_TANK_OUT.readFromNBT(nbt.getCompound("FluidOut"));
        reactorCapacity = nbt.getInt("ReactorCapacity");
        soundTicks = nbt.getLong("SoundTicks");
        // Rod
        setFuelRodStatus(nbt.getByteArray("FuelRodStatus"));
        setControlRodStatus(nbt.getByteArray("ControlRodStatus"));
        // Turbine
        setTurbineTargetSpeed(nbt.getShort("TurbineTargetSpeed"));
        setTurbineCurrentSpeed(nbt.getShort("TurbineCurrentSpeed"));
        setTurbineTargetOverflowSet(nbt.getByte("TurbineTargetOverflowSet"));
        setTurbineCurrentOverflowSet(nbt.getByte("TurbineCurrentOverflowSet"));
        setTurbineTargetLoadSet(nbt.getByte("TurbineTargetLoadSet"));
        setTurbineCurrentLoadSet(nbt.getByte("TurbineCurrentLoadSet"));
        setTurbineCoilsEngaged(nbt.getBoolean("TurbineCoilsEngaged"));
        setTurbineCurrentFlow(nbt.getShort("TurbineCurrentFlow"));
        setTurbinePowerGeneration(nbt.getLong("TurbinePowerGeneration"));
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

    @NotNull
    @Override
    public Component getDisplayName() {
        return Component.translatable("block.thoriumreactors.reactor_controller");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, @NotNull Inventory pInventory, @NotNull Player pPlayer) {
        return new ReactorControllerContainer(pContainerId, pInventory, getBlockPos(), getLevel(), 0);
    }

    public void setFuelAdditions(int fuelAdditions) {
        this.fuelAdditions = fuelAdditions;
    }

    public int getFuelAdditions() {
        return fuelAdditions;
    }

    public boolean isScrammed() {
        return scrammed;
    }

    public void setScrammed(boolean scrammed) {
        this.scrammed = scrammed;
    }

    public void updateBlock() {
        if(level != null && !level.isClientSide) {
            BlockState state = level.getBlockState(worldPosition);
            level.sendBlockUpdated(worldPosition, state, state, 2);
            setChanged(level, getBlockPos(), state);
        }
    }

    public void updateWallPositions() {
        valvePos = new ArrayList<>();
        List<BlockPos> blockListBack = CalculationUtil.getBlockPositions(floorPosA, ceilingPosARight, level); // Back
        List<BlockPos> blockListLeft = CalculationUtil.getBlockPositions(floorPosBLeft, ceilingPosA, level); // Left
        List<BlockPos> blockListRight = CalculationUtil.getBlockPositions(floorPosB, ceilingPosARight, level); // Right
        List<BlockPos> blockListFront = CalculationUtil.getBlockPositions(floorPosBLeft, ceilingPosB, level); // Front

        checkWallArea(blockListBack);
        checkWallArea(blockListLeft);
        checkWallArea(blockListRight);
        checkWallArea(blockListFront);

        controllers = 0;
        if (valvePos.size() != 4) {
            resetAssembled("Reactor is in need of four valves. Currently " + valvePos.size());
        }
    }

    public void checkWallArea(List<BlockPos> blockPosList) {
        for (BlockPos pos : blockPosList) {
            if (level.getBlockState(pos).is(ModBlocks.REACTOR_CONTROLLER_BLOCK.get())) {
                controllers++;
                if (controllers > 1) resetAssembled("Reactor cannot have two controller blocks! Block at " + pos);
            }
            if (!(isCasing(getState(pos)) || isGlass(getState(pos)) || isValve(getState(pos)) || getState(pos).is(getState(getBlockPos()).getBlock()))) {
                resetAssembled("Missing reactor casing, glass or valve in wall at " + pos);
            }
            if (isValve(getState(pos))) {
                if (!(level.getBlockEntity(pos) instanceof ReactorValveBlockEntity entity)) return;
                valvePos.add(pos);
                entity.setReactorCorePosition(this.getBlockPos());
            }
        }
    }

    public void updateInnerPositions() {
        int moderatorCount = pillarAHeight - 2;
        reactorCorePos = null;
        moderatorPos = new ArrayList<>(moderatorCount);

        for (int i = 0; i < pillarAHeight; i++) {
            if (getState(controlRodPos.below(i)).is(ModBlocks.REACTOR_CORE.get())) {
                if (isCasing(getState(controlRodPos.below(i).below()))) {
                    reactorCorePos = controlRodPos.below(i);
                }
            } else if (getState(controlRodPos.below(i)).is(ModBlocks.REACTOR_GRAPHITE_MODERATOR.get())) {
                moderatorPos.add(controlRodPos.below(i));
            }
        }

        if (reactorCorePos == null || !isCasing(getState(reactorCorePos.below())) || reactorCorePos.below().getY() != floorPosA.getY()) {
            resetAssembled("Reactor core not found or placed on the reactor floor");
            return;
        }

        if (moderatorPos != null) {
            for (BlockPos pos : moderatorPos) {
                if (!getState(pos).is(ModBlocks.REACTOR_GRAPHITE_MODERATOR.get()) || reactorCorePos.getX() != pos.getX() || reactorCorePos.getZ() != pos.getZ() || moderatorPos.size() != moderatorCount) {
                    resetAssembled("Reactor moderators not valid. Must be placed between reactor core and rod controller");
                }
            }
        } else {
            resetAssembled("Reactor moderators not found. Must be placed between reactor core and rod controller");
        }

    }

    public void updateFloorPositions(BlockPos controllerPos, Direction controllerDirection) {
        reactorXLenght = 0;
        reactorYLenght = 0;
        canBeAssembled = true;
        floorPosLength = 0;
        floorPosCounterClockwise = 0;
        floorPosClockwise = 0;
        while (floorPosLength < 6)
            if (isCasing(getState(controllerPos.below().relative(controllerDirection, floorPosLength + 1)))) floorPosLength++;
            else break;
        while (floorPosClockwise < 6)
            if (isCasing(getState(controllerPos.below().relative(controllerDirection.getClockWise(), floorPosClockwise + 1)))) floorPosClockwise++;
            else break;
        while (floorPosCounterClockwise < 6)
            if (isCasing(getState(controllerPos.below().relative(controllerDirection.getCounterClockWise(), floorPosCounterClockwise + 1)))) floorPosCounterClockwise++;
            else break;

        floorPosA = switch (controllerDirection) {
            case NORTH -> controllerPos.relative(controllerDirection.getClockWise(), -floorPosCounterClockwise).relative(controllerDirection, floorPosLength).relative(Direction.DOWN, 1);
            case EAST -> controllerPos.relative(controllerDirection, floorPosLength).relative(controllerDirection.getClockWise(), -floorPosCounterClockwise).relative(Direction.DOWN, 1);
            case SOUTH -> controllerPos.relative(controllerDirection, floorPosLength).relative(controllerDirection.getClockWise(), -floorPosCounterClockwise).relative(Direction.DOWN, 1);
            case WEST -> controllerPos.relative(controllerDirection, floorPosLength).relative(controllerDirection.getCounterClockWise(), floorPosCounterClockwise).relative(Direction.DOWN, 1);
            default -> BlockPos.ZERO;
        };
        floorPosB = switch (controllerDirection) {
            case NORTH -> controllerPos.relative(controllerDirection.getClockWise(), floorPosClockwise).relative(Direction.DOWN, 1);
            case EAST -> controllerPos.relative(controllerDirection.getCounterClockWise(), -floorPosClockwise).relative(Direction.DOWN, 1);
            case SOUTH -> controllerPos.relative(controllerDirection.getCounterClockWise(), -floorPosClockwise).relative(Direction.DOWN, 1);
            case WEST -> controllerPos.relative(controllerDirection.getClockWise(), floorPosClockwise).relative(Direction.DOWN, 1);
            default -> BlockPos.ZERO;
        };

        List<BlockPos> blockPosList = CalculationUtil.getBlockPositions(floorPosA, floorPosB, level);
        for (BlockPos pos : blockPosList) {
            if (!isCasing(getState(pos))) {
                resetAssembled("Missing reactor casing in floor at position " + pos);
                return;
            }
        }

        if (controllerDirection == Direction.NORTH) {
            reactorXLenght = (int) Math.floor(floorPosB.getX() - floorPosA.getX() + 1);
            reactorYLenght = (int) Math.floor(floorPosB.getZ() - floorPosA.getZ() + 1);
        } else if (controllerDirection == Direction.EAST){
            reactorXLenght = (int) Math.floor(floorPosA.getX() - floorPosB.getX() + 1);
            reactorYLenght = (int) Math.floor(floorPosB.getZ() - floorPosA.getZ() + 1);
        } else if (controllerDirection == Direction.SOUTH){
            reactorXLenght = (int) Math.floor(floorPosA.getX() - floorPosB.getX() + 1);
            reactorYLenght = (int) Math.floor(floorPosA.getZ() - floorPosB.getZ() + 1);
        } else {
            reactorXLenght = (int) Math.floor(floorPosB.getX() - floorPosA.getX() + 1);
            reactorYLenght = (int) Math.floor(floorPosA.getZ() - floorPosB.getZ() + 1);
        }

        if (reactorXLenght != reactorYLenght || reactorXLenght < 5 || reactorYLenght < 5) {
            resetAssembled();
        }
    }

    public void updatePillarPositions(Direction controllerDirection, BlockPos floorPosA, BlockPos floorPosB) {
        pillarAHeight = 0;
        pillarARightHeight = 0;
        pillarBHeight = 0;
        pillarBLeftHeight = 0;
        floorPosARight = switch (controllerDirection.getOpposite()) {
            case NORTH -> new BlockPos(floorPosB.getX(), floorPosA.getY(), floorPosA.getZ());
            case EAST -> new BlockPos(floorPosA.getX(), floorPosA.getY(), floorPosB.getZ());
            case SOUTH -> new BlockPos(floorPosB.getX(), floorPosA.getY(), floorPosA.getZ());
            case WEST -> new BlockPos(floorPosA.getX(), floorPosA.getY(), floorPosB.getZ());
            default -> BlockPos.ZERO;
        };
        floorPosBLeft = switch (controllerDirection.getOpposite()) {
            case NORTH -> new BlockPos(floorPosA.getX(), floorPosA.getY(), floorPosB.getZ());
            case EAST -> new BlockPos(floorPosB.getX(), floorPosA.getY(), floorPosA.getZ());
            case SOUTH -> new BlockPos(floorPosA.getX(), floorPosA.getY(), floorPosB.getZ());
            case WEST -> new BlockPos(floorPosB.getX(), floorPosA.getY(), floorPosA.getZ());
            default -> BlockPos.ZERO;
        };

        while (pillarAHeight < 5) {
            if (isCasing(getState(floorPosA.relative(Direction.UP, pillarAHeight + 1)))) pillarAHeight++;
            else break;
        }
        while (pillarARightHeight < 5) {
            if (isCasing(getState(floorPosARight.relative(Direction.UP, pillarARightHeight + 1)))) pillarARightHeight++;
            else break;
        }
        while (pillarBHeight < 5) {
            if (isCasing(getState(floorPosB.relative(Direction.UP, pillarBHeight + 1)))) pillarBHeight++;
            else break;
        }
        while (pillarBLeftHeight < 5) {
            if (isCasing(getState(floorPosBLeft.relative(Direction.UP, pillarBLeftHeight + 1)))) pillarBLeftHeight++;
            else break;
        }

        if (pillarAHeight < 3 || pillarARightHeight < 3 || pillarBHeight < 3 || pillarBLeftHeight < 3 || pillarAHeight != pillarBHeight || pillarBHeight != pillarBLeftHeight || pillarBLeftHeight != pillarARightHeight) {
            resetAssembled("Missing reactor casing at pillars or invalid height of reactor!");
        }
    }

    public void updateCeilingPositions() {
        ceilingPosA = floorPosA.offset(0, pillarAHeight, 0);
        ceilingPosB = floorPosB.offset(0, pillarBHeight, 0);
        ceilingPosARight = floorPosARight.offset(0, pillarARightHeight, 0);
        ceilingPosBLeft = floorPosBLeft.offset(0, pillarBLeftHeight, 0);
        framePosList = new ArrayList<>();
        controlRodPos = null;

        // Checking ceiling frame for casings
        List<List<BlockPos>> positionList = List.of(List.of(ceilingPosA, ceilingPosARight), List.of(ceilingPosARight, ceilingPosB), List.of(ceilingPosB, ceilingPosBLeft), List.of(ceilingPosBLeft, ceilingPosA));
        for (List<BlockPos> allBlockList : positionList) {
            List<BlockPos> blockPosList = CalculationUtil.getBlockPositions(allBlockList.get(0), allBlockList.get(1), level);
            for (BlockPos pos : blockPosList) {
                if (!isCasing(getState(pos))) {
                    resetAssembled("Missing reactor casing in ceiling frame at position " + pos);
                    return;
                }
                if (!framePosList.contains(pos))
                    framePosList.add(pos);
            }
        }

        List<BlockPos> ceilingWall = CalculationUtil.getBlockPositions(ceilingPosA, ceilingPosB, level);
        for (BlockPos pos : framePosList)
            if (ceilingWall.contains(pos))
                ceilingWall.remove(pos);

        List<BlockState> glassStates = new ArrayList<>();
        List<BlockState> casingStates = new ArrayList<>();
        for (BlockPos pos : ceilingWall) {
            if (getState(pos).is(ModBlocks.REACTOR_ROD_CONTROLLER.get())) {
                controlRodPos = pos;
            } else if (isGlass(getState(pos))) {
                glassStates.add(getState(pos));
            } else if (isCasing(getState(pos))) {
                casingStates.add(getState(pos));
            }
        }

        if (controlRodPos == null || !hasFrameConnection(controlRodPos))
            resetAssembled("Missing reactor rod controller in ceiling or no connection to frame with casings");

        if (glassStates.size() + casingStates.size() + 1 != ceilingWall.size())
            resetAssembled("Missing reactor casings or glass in ceiling");

    }

    public boolean hasFrameConnection(BlockPos pos) {
        boolean exit = false;
        int runs = 0;
        BlockPos current = pos;
        while (!exit) {
            for (Direction value : Direction.values()) {
                if (getState(current.relative(value)).is(ModBlocks.REACTOR_CASING.get())) {
                    current = current.relative(value);
                    if (framePosList.contains(current.relative(value)))
                        exit = true;
                }
                if (runs > 64)
                    exit = true;
                runs++;
            }
        }
        return exit && runs <= 64;
    }

    public void resetAssembled() {
        canBeAssembled = false;
        assembled = false;

        if (getBlockState().getValue(ReactorControllerBlock.POWERED) != assembled)
            getLevel().setBlock(getBlockPos(), getBlockState().setValue(ReactorControllerBlock.POWERED, assembled), 3);
    }

    public void resetAssembled(String text) {
        if (warning != null)
            warning = text;
        resetAssembled();
    }

    public byte[] getFuelRodStatus() {
        return fuelRodStatus;
    }

    public byte getFuelRodStatus(byte index) {
        return getFuelRodStatus()[index];
    }

    public void setFuelRodStatus(byte[] fuelRodStatus) {
        this.fuelRodStatus = fuelRodStatus;
        setChanged();
    }

    public void setFuelRodStatus(byte index, byte value) {
        getFuelRodStatus()[index] = value;
        setChanged();
    }

    public byte[] getControlRodStatus() {
        return controlRodStatus;
    }

    public int getControlRodStatus(byte index) {
        return getControlRodStatus()[index];
    }

    public void setControlRodStatus(byte[] fuelRodStatus) {
        this.controlRodStatus = fuelRodStatus;
    }

    public int setControlRodStatus(byte index, byte value) {
        return getControlRodStatus()[index] = value;
    }

    public BlockState getState(BlockPos pos) {
        return this.level.getBlockState(pos);
    }

    public boolean isCasing(BlockState state) {
        return state.is(ModBlocks.REACTOR_CASING.get());
    }

    public boolean isGlass(BlockState state) {
        return state.is(ModBlocks.REACTOR_GLASS.get());
    }

    public boolean isValve(BlockState state) {
        return state.is(ModBlocks.REACTOR_VALVE.get());
    }

    public boolean isAssembled() {
        return assembled;
    }

    public void setAssembled(boolean canBeAssembled) {
        this.assembled = canBeAssembled;
    }

    public short getReactorCurrentTemperature() {
        return reactorCurrentTemperature;
    }

    public void setReactorCurrentTemperature(short reactorCurrentTemperature) {
        this.reactorCurrentTemperature = reactorCurrentTemperature;
    }

    public short getReactorTargetTemperature() {
        return reactorTargetTemperature;
    }

    public void setReactorTargetTemperature(short reactorTargetTemperature) {
        this.reactorTargetTemperature = reactorTargetTemperature;
    }

    public byte getReactorTargetLoadSet() {
        return reactorTargetLoadSet;
    }

    public void setReactorTargetLoadSet(byte reactorTargetLoadSet) {
        this.reactorTargetLoadSet = reactorTargetLoadSet;
    }

    public byte getReactorCurrentLoadSet() {
        return reactorCurrentLoadSet;
    }

    public void setReactorCurrentLoadSet(byte reactorCurrentLoadSet) {
        this.reactorCurrentLoadSet = reactorCurrentLoadSet;
    }

    public long getReactorRunningSince() {
        return reactorRunningSince;
    }

    public void setReactorRunningSince(long reactorRunningSince) {
        this.reactorRunningSince = reactorRunningSince;
    }

    public byte getReactorStatus() {
        return reactorStatus;
    }

    public void setReactorStatus(byte reactorStatus) {
        this.reactorStatus = reactorStatus;
    }

    public float getReactorContainment() {
        return reactorContainment;
    }

    public void setReactorContainment(float reactorContainment) {
        this.reactorContainment = reactorContainment;
    }

    public float getReactorRadiation() {
        return reactorRadiation;
    }

    public void setReactorRadiation(float reactorRadiation) {
        this.reactorRadiation = reactorRadiation;
    }

    public float getReactorPressure() {
        return reactorPressure;
    }

    public void setReactorPressure(float reactorPressure) {
        this.reactorPressure = reactorPressure;
    }

    public ReactorStateEnum getReactorState() {
        return reactorState;
    }

    public void setReactorState(ReactorStateEnum reactorState) {
        this.reactorState = reactorState;
    }

    public short getTurbineTargetSpeed() {
        return turbineTargetSpeed;
    }

    public void setTurbineTargetSpeed(short turbineTargetSpeed) {
        this.turbineTargetSpeed = turbineTargetSpeed;
    }

    public short getTurbineCurrentSpeed() {
        return turbineCurrentSpeed;
    }

    public void setTurbineCurrentSpeed(short turbineCurrentSpeed) {
        this.turbineCurrentSpeed = turbineCurrentSpeed;
    }

    public byte getTurbineTargetOverflowSet() {
        return turbineTargetOverflowSet;
    }

    public void setTurbineTargetOverflowSet(byte turbineTargetOverflowSet) {
        this.turbineTargetOverflowSet = turbineTargetOverflowSet;
    }

    public byte getTurbineCurrentOverflowSet() {
        return turbineCurrentOverflowSet;
    }

    public void setTurbineCurrentOverflowSet(byte turbineCurrentOverflowSet) {
        this.turbineCurrentOverflowSet = turbineCurrentOverflowSet;
    }

    public byte getTurbineTargetLoadSet() {
        return turbineTargetLoadSet;
    }

    public void setTurbineTargetLoadSet(byte turbineTargetLoadSet) {
        this.turbineTargetLoadSet = turbineTargetLoadSet;
    }

    public byte getTurbineCurrentLoadSet() {
        return turbineCurrentLoadSet;
    }

    public void setTurbineCurrentLoadSet(byte turbineCurrentLoadSet) {
        this.turbineCurrentLoadSet = turbineCurrentLoadSet;
    }

    public boolean isTurbineCoilsEngaged() {
        return turbineCoilsEngaged;
    }

    public void setTurbineCoilsEngaged(boolean turbineCoilsEngaged) {
        this.turbineCoilsEngaged = turbineCoilsEngaged;
    }

    public short getTurbineCurrentFlow() {
        return turbineCurrentFlow;
    }

    public void setTurbineCurrentFlow(short turbineCurrentFlow) {
        this.turbineCurrentFlow = turbineCurrentFlow;
    }

    public long getTurbinePowerGeneration() {
        return turbinePowerGeneration;
    }

    public void setTurbinePowerGeneration(long turbinePowerGeneration) {
        this.turbinePowerGeneration = turbinePowerGeneration;
    }
}
