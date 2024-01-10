package unhappycodings.thoriumreactors.common.blockentity.reactor;

import net.minecraft.core.BlockPos;
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
import unhappycodings.thoriumreactors.common.blockentity.reactor.base.ReactorFrameBlockEntity;
import unhappycodings.thoriumreactors.common.blockentity.thermal.ThermalControllerBlockEntity;
import unhappycodings.thoriumreactors.common.config.CommonConfig;
import unhappycodings.thoriumreactors.common.container.reactor.ReactorControllerContainer;
import unhappycodings.thoriumreactors.common.enums.ReactorStateEnum;
import unhappycodings.thoriumreactors.common.enums.ValveTypeEnum;
import unhappycodings.thoriumreactors.common.network.PacketHandler;
import unhappycodings.thoriumreactors.common.network.toclient.reactor.ClientReactorRenderDataPacket;
import unhappycodings.thoriumreactors.common.registration.*;
import unhappycodings.thoriumreactors.common.util.FormattingUtil;
import unhappycodings.thoriumreactors.common.util.SoundUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ReactorControllerBlockEntity extends ReactorFrameBlockEntity implements MenuProvider {
    public static final int MAX_HEAT = 1320;
    public List<BlockPos> turbinePos;
    public List<BlockPos> valvePos;
    public BlockPos thermalPos = BlockPos.ZERO;
    public boolean assembled;
    public String notification = "";
    public boolean isReactorActive;
    public boolean isExchangerActive;
    public boolean isTurbineActive;
    public int reactorCapacity;
    long soundTicks;

    // Reactor
    private float reactorCurrentTemperature; // 0-3000
    private float reactorTargetTemperature; // 0-3000
    private byte reactorTargetLoadSet; // 0-100%
    private byte reactorCurrentLoadSet; // 0-100%
    private long reactorRunningSince; // timestamp
    private float reactorStatus = 100; // 0-100%
    private float reactorContainment = 100; // 0-100%
    private float reactorRadiation; // uSv per hour
    private float reactorPressure = 29.98f; // in PSI
    private int reactorHeight = 0;
    private int fuelAdditions = 0;
    private boolean scrammed;
    private ReactorStateEnum reactorState = ReactorStateEnum.STOP; // STARTING - RUNNING - STOP
    // Rods
    public byte[] depletedFuelRodStatus = new byte[81];
    public byte[] fuelRodStatus = new byte[81];
    public byte[] controlRodStatus = new byte[64];
    public byte[] targetControlRodStatus = new byte[64];
    // Turbine
    private byte turbineSpeed; // 0-3000
    private byte turbineTargetFlow; // 0-100%
    private byte turbineCurrentFlow; // 0-100%
    private boolean turbineCoilsEngaged; // true-false
    private boolean turbineActivated; // true-false
    private long turbinePowerGeneration; // FE per tick

    private final ModFluidTank FLUID_TANK_IN = new ModFluidTank(35000, true, true, 0, FluidStack.EMPTY) {
        @Override
        public int getCapacity() {
            return reactorCapacity / 2;
        }

        @Override
        public int getSpace() {
            return super.getSpace();
        }
    };
    private final ModFluidTank FLUID_TANK_OUT = new ModFluidTank(35000, true, true, 0, FluidStack.EMPTY) {
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
        if (getBlockState().getValue(ReactorControllerBlock.POWERED) != assembled)
            getLevel().setBlock(getBlockPos(), getBlockState().setValue(ReactorControllerBlock.POWERED, assembled), 3);

        if (isAssembled()) {
            tryTankReactor(); // Molten Salt input from configured valves
            tryOutputTank(); // Output of Heated Molten Salt into configures valves
            tryFuelReactor(); // Input of Enriched Uranium Pellets from configures valves
            updateTemperature(); // Temperature Simulation
            if ((getFluidAmountIn() > 0 || getFluidAmountOut() > 0) && level.getGameTime() % 10 == 0)
                radiationPlayerCheck(); // Kills players inside the reactor if its fueled

            // If scrammed, do simulation
            if (isScrammed()) doScramSimulation();
            else updateControlRods();
            if (getReactorState() != ReactorStateEnum.STOP) {
                doReactorSimulation();
            } else {
                setReactorActive(false);
                setCoreHeating(false);
            }

            updateRenderData();
            updateBlock();
            float random = new Random().nextFloat();
            if (getReactorPressure() < 30.2f && random < 0.5f) {
                setReactorPressure(getReactorPressure() + 0.0001f);
            } else if (getReactorPressure() > 29.7f && random > 0.5f) {
                setReactorPressure(getReactorPressure() - 0.0001f);
            }
        }
        if (level.getGameTime() % 20 == 0) {
            int reactorLoad = (int) (((getReactorCurrentTemperature() - 22) / 949) * 100);
            if (reactorLoad >= 105) {
                if (level.getGameTime() % 60 == 0) {
                    level.playSound(null, getBlockPos(), ModSounds.ALARM_3.get(), SoundSource.BLOCKS, 2f, 1f);
                }
                setReactorStatus((getReactorStatus() - ((reactorLoad - 105f) / 100f)));
            }
        }

        BlockPos corePos = getBlockPos().relative(getBlockState().getValue(ReactorControllerBlock.FACING).getOpposite(), 2);
        if (getReactorState() != ReactorStateEnum.STOP) {
            if (soundTicks == 0) {
                stopReactorSound(ModSounds.REACTOR_RUN.get());
                stopReactorSound(ModSounds.REACTOR_SHUTDOWN.get());
                level.playSound(null, corePos, ModSounds.REACTOR_STARTUP.get(), SoundSource.BLOCKS, 1f, 1f);
            }
            if (soundTicks % 92 == 0 && soundTicks != 0) {
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

    public void scram(String text) {
        setScrammed(true);
        setNotification(text);
    }

    public void doScramSimulation() {
        setReactorRunningSince(-1);
        setReactorActive(false);
        setCoreHeating(false);
        if (!getBlockState().getValue(ReactorControllerBlock.SCRAMMED))
            level.setBlockAndUpdate(getBlockPos(), getBlockState().setValue(ReactorControllerBlock.SCRAMMED, true));
        for (byte i = 0; i < 64; i++) {
            setTargetControlRodStatus(i, (byte) getControlRodStatus(i));
            if ((byte) getControlRodStatus(i) < 100)
                setControlRodStatus(i, (byte) ((byte) getControlRodStatus(i) + 1));
        }
        if (level.getGameTime() % 60 == 0)
            level.playSound(null, getBlockPos(), ModSounds.ALARM_1.get(), SoundSource.BLOCKS, 2f, 1f);
        if (level.getGameTime() % 40 == 0)
            level.playSound(null, getBlockPos(), ModSounds.ALARM_2.get(), SoundSource.BLOCKS, 2f, 1f);
        setReactorState(ReactorStateEnum.STOP);
        updateBlock();
    }

    public void updateControlRods() {
        for (byte i = 0; i < getTargetControlRodStatus().length; i++) {
            if (getControlRodStatus(i) > getTargetControlRodStatus(i)) {
                setControlRodStatus(i, (byte) (getControlRodStatus(i) - 1));
            } else if (getControlRodStatus(i) < getTargetControlRodStatus(i)) {
                setControlRodStatus(i, (byte) (getControlRodStatus(i) + 1));
            }
        }
    }

    public void doReactorSimulation() {
        if (getBlockState().getValue(ReactorControllerBlock.SCRAMMED))
            level.setBlockAndUpdate(getBlockPos(), getBlockState().setValue(ReactorControllerBlock.SCRAMMED, false));

        setReactorRunningSince(getReactorRunningSince() + 1);
        if (getFluidAmountIn() < 8000 && !isScrammed()) {
            scram(Component.translatable(FormattingUtil.getTranslatable("reactor.text.molten_salt_low_warning")).getString());
            return;
        } else if (!getNotification().isEmpty()) {
            setNotification("");
        }

        setCoreHeating(true);
        setReactorActive(true);

        if (getReactorCurrentTemperature() > 100 && getReactorState() == ReactorStateEnum.RUNNING) {
            int modifier = (int) Math.floor(getReactorCurrentTemperature() / 50f);
            int amount = (int) (getFluidSpaceOut() > modifier ? (getFluidAmountIn() >= modifier ? modifier : getFluidAmountIn()) * CommonConfig.reactorSaltGenerationModifier.get() : (getFluidSpaceOut() >= modifier ? modifier : getFluidSpaceOut()) * CommonConfig.reactorSaltGenerationModifier.get());

            if (FLUID_TANK_OUT.getFluid().isEmpty()) {
                FLUID_TANK_OUT.setFluid(new FluidStack(ModFluids.SOURCE_HEATED_MOLTEN_SALT.get(), amount));
                getFluidIn().shrink(amount);
            } else if (!FLUID_TANK_IN.getFluid().isEmpty()) {
                getFluidIn().shrink(amount);
                getFluidOut().grow(amount);
            }
        }

        // Unfuel reactor by 1 enriched and output one depleted uranium every 12 runtime hours
        if (false && getReactorRunningSince() % (12 * 60 * 60 * 20) == 0) {
            int runs = 0;
            for (int i = 0; i < 810; i++) {
                int randomNumber = new Random().nextInt(81);
                if (getFuelRodStatus((byte) randomNumber) > 0) {
                    setFuelRodStatus((byte) randomNumber, (byte) (getFuelRodStatus((byte) randomNumber) - 1));
                    setDepletedFuelRodStatus((byte) randomNumber, (byte) (getDepletedFuelRodStatus((byte) randomNumber) + 1));
                    runs++;
                }
                if (runs == 10) break;
            }
        }

    }

    public void updateTemperature() {
        int fuelValue = 0, controlValue = 0;
        for (int i = 0; i < getFuelRodStatus().length; i++) fuelValue += getFuelRodStatus()[i];
        for (int i = 0; i < getControlRodStatus().length; i++) controlValue += getControlRodStatus()[i];

        float fuelValuePercent = (fuelValue / 8100f * 100f), controlValuePercent = (1f - (controlValue / 6400f));
        float targetTemperature = (((fuelValuePercent * controlValuePercent) / 100f) * MAX_HEAT) * (reactorStatus / 20f - 4);
        short normalTemp = (short) (level.getBiome(getBlockPos()).is(Tags.Biomes.IS_COLD) ? 4 : 22);

        setReactorTargetTemperature(targetTemperature < normalTemp || getReactorState() == ReactorStateEnum.STOP ? normalTemp : targetTemperature);

        // Default reactor heating
        if (getReactorCurrentTemperature() + calculateTemperature(false) < getReactorTargetTemperature())
            setReactorCurrentTemperature((getReactorCurrentTemperature() + calculateTemperature(false)));

        // Small step heating until target temperature
        else if (getReactorCurrentTemperature() < getReactorTargetTemperature() || getReactorCurrentTemperature() > getReactorTargetTemperature())
            setReactorCurrentTemperature((getReactorCurrentTemperature() + (getReactorCurrentTemperature() < getReactorTargetTemperature() ? 1 : -1)));

        if (isReactorActive() && getReactorCurrentTemperature() > 30) {
            double random = Math.random();
            if (random > 0.1 && getReactorCurrentTemperature() <= getReactorTargetTemperature())
                setReactorCurrentTemperature((float) (getReactorCurrentTemperature() - (random * 2)));
        }

    }

    public float calculateTemperature(boolean cooling) {
        if (getReactorCurrentTemperature() < MAX_HEAT / 3f) {
            return cooling ? -1.1f : 10f;
        } else if (getReactorCurrentTemperature() < MAX_HEAT / 2f) {
            return cooling ? -2.1f : 5f;
        } else {
            return cooling ? -4.1f : 2f;
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
        List<ServerPlayer> players = level.getEntitiesOfClass(ServerPlayer.class, new AABB(p.getX() -2, p.getY() -1, p.getZ() -2, p.getX() + 3, p.getY() + getReactorHeight(), p.getZ() + 3));

        for (ServerPlayer player : players) {
            player.hurt(ModDamageSources.OVERDOSIS, Float.MAX_VALUE);
        }
    }

    public void updateRenderData() {
        BlockPos p = getBlockPos();
        List<ServerPlayer> players = level.getEntitiesOfClass(ServerPlayer.class, new AABB(p.getX() -18, p.getY() -18, p.getZ() -18, p.getX() + 18, p.getY() + 18, p.getZ() + 18));

        for (ServerPlayer player : players) {
            PacketHandler.sendToClient(new ClientReactorRenderDataPacket(getBlockPos(), getReactorHeight(), getFluidIn(), getFluidOut()), player);
        }
    }

    public void tryOutputTank() {
        if (valvePos != null) {
            for (BlockPos blockPos : valvePos) {
                if (!level.getBlockState(blockPos).is(ModBlocks.REACTOR_VALVE.get())) return;
                if (level.getBlockState(blockPos).getValue(ReactorValveBlock.TYPE) == ValveTypeEnum.FLUID_OUTPUT) {
                    level.getBlockEntity(blockPos).getCapability(ForgeCapabilities.FLUID_HANDLER, level.getBlockState(blockPos).getValue(ReactorValveBlock.FACING)).ifPresent(storage -> {
                        if (getFluidOut().isEmpty()) return;

                        int free = storage.getTankCapacity(0) - storage.getFluidInTank(0).getAmount();
                        int amount = Math.min(free, getFluidAmountOut());
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
                    level.getBlockEntity(blockPos).getCapability(ForgeCapabilities.FLUID_HANDLER, level.getBlockState(blockPos).getValue(ReactorValveBlock.FACING)).ifPresent(storage -> {
                        FluidStack fluidExternal = storage.getFluidInTank(0);
                        int amount = Math.min(FLUID_TANK_IN.getCapacity() - FLUID_TANK_IN.getFluidAmount(), fluidExternal.getAmount());
                        if (fluidExternal.isFluidEqual(ModFluids.SOURCE_MOLTEN_SALT.get().getBucket().getDefaultInstance()) && amount > 0) {

                            if (FLUID_TANK_IN.getFluid().isEmpty()) {
                                FLUID_TANK_IN.setFluid(new FluidStack(ModFluids.SOURCE_MOLTEN_SALT.get(), amount));
                                storage.getFluidInTank(0).shrink(amount);
                            } else if (getFluidAmountIn() + amount <= getFluidCapacityIn()) {
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
            int fuelValue = 0, depletedFuelValue = 0;
            for (int i = 0; i < getFuelRodStatus().length; i++) fuelValue += getFuelRodStatus()[i];
            for (int i = 0; i < getDepletedFuelRodStatus().length; i++) depletedFuelValue += getDepletedFuelRodStatus()[i];
            int fuelPercentage = (int) (fuelValue / 8100f * 100f);

            setCoreFueled(fuelValue > 0);

            for (BlockPos blockPos : valvePos) {
                if (!level.getBlockState(blockPos).is(ModBlocks.REACTOR_VALVE.get())) return;

                ReactorValveBlockEntity entity = (ReactorValveBlockEntity) level.getBlockEntity(blockPos);

                if (fuelAdditions == 0 && fuelValue + depletedFuelValue < 8100 && entity.getItem(0).is(ModItems.ENRICHED_URANIUM.get()) && fuelPercentage < getReactorTargetLoadSet() && level.getBlockState(blockPos).getValue(ReactorValveBlock.TYPE) == ValveTypeEnum.ITEM_INPUT) {
                    entity.getItem(0).shrink(1);
                    fuelAdditions = 10;
                } else if (fuelPercentage >= getReactorTargetLoadSet() && level.getBlockState(blockPos).getValue(ReactorValveBlock.TYPE) == ValveTypeEnum.ITEM_OUTPUT) {
                    if (fuelAdditions == 10) {
                        if (entity.getItem(0).isEmpty())
                            entity.setItem(0, new ItemStack(ModItems.ENRICHED_URANIUM.get(), 1));
                        else
                            entity.getItem(0).grow(1);
                        fuelAdditions = 0;
                    }
                }

                if (level.getBlockState(blockPos).getValue(ReactorValveBlock.TYPE) != ValveTypeEnum.ITEM_OUTPUT || depletedFuelValue < 10) continue;
                if ((entity.getItem(0).is(ModItems.DEPLETED_URANIUM.get()) && entity.getItem(0).getCount() < entity.getItem(0).getMaxStackSize()) || entity.getItem(0).isEmpty()) {

                    int runs = 0;
                    for (int i = 0; i < 810; i++) {
                        int randomNumber = new Random().nextInt(81);
                        if (getDepletedFuelRodStatus((byte) randomNumber) > 0) {
                            setDepletedFuelRodStatus((byte) randomNumber, (byte) (getDepletedFuelRodStatus((byte) randomNumber) - 1));
                            runs++;
                        }
                        if (runs == 10) break;
                    }

                    if (entity.getItem(0).isEmpty())
                        entity.setItem(0, new ItemStack(ModItems.DEPLETED_URANIUM.get(), 1));
                    else
                        entity.getItem(0).grow(1);

                }

            }

            int randomNumber = new Random().nextInt(81);
            if (fuelAdditions != 0) {
                if (getFuelRodStatus((byte) randomNumber) < getReactorTargetLoadSet() && getDepletedFuelRodStatus((byte) randomNumber) + getFuelRodStatus((byte) randomNumber) < 100) {
                    setFuelRodStatus((byte) randomNumber, (byte) (getFuelRodStatus((byte) randomNumber) + 1));
                    fuelAdditions--;
                }
            }
            if (fuelPercentage >= getReactorTargetLoadSet()) {
                if (getFuelRodStatus((byte) randomNumber) > getReactorTargetLoadSet() && fuelAdditions < 20) {
                    setFuelRodStatus((byte) randomNumber, (byte) (getFuelRodStatus((byte) randomNumber) - 1));
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
        if (level.getBlockEntity(thermalPos) instanceof ThermalControllerBlockEntity entity) {
            return entity.getConversions() > 3;
        }
        return false;
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
        return reactorHeight;
    }

    public void setReactorHeight(int height) {
        reactorHeight = height;
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
        nbt.putFloat("ReactorTargetTemperature", getReactorTargetTemperature());
        nbt.putFloat("ReactorCurrentTemperature", getReactorCurrentTemperature());
        nbt.putByte("ReactorTargetLoadSet", getReactorTargetLoadSet());
        nbt.putByte("ReactorCurrentLoadSet", getReactorCurrentLoadSet());
        nbt.putLong("ReactorRunningSince", getReactorRunningSince());
        nbt.putFloat("ReactorStatus", getReactorStatus());
        nbt.putFloat("ReactorContainment", getReactorContainment());
        nbt.putFloat("ReactorRadiation", getReactorRadiation());
        nbt.putFloat("ReactorPressure", getReactorPressure());
        nbt.putInt("FuelAdditions", getFuelAdditions());
        nbt.putBoolean("Scrammed", isScrammed());
        nbt.putString("ReactorState", getReactorState().toString());
        nbt.put("FluidIn", FLUID_TANK_IN.writeToNBT(new CompoundTag()));
        nbt.put("FluidOut", FLUID_TANK_OUT.writeToNBT(new CompoundTag()));
        nbt.putInt("ReactorCapacity", reactorCapacity);
        nbt.putInt("ReactorHeight", reactorHeight);
        nbt.putLong("SoundTicks", soundTicks);
        // Rod
        nbt.putByteArray("DepletedFuelRodStatus", getDepletedFuelRodStatus());
        nbt.putByteArray("FuelRodStatus", getFuelRodStatus());
        nbt.putByteArray("ControlRodStatus", getControlRodStatus());
        nbt.putByteArray("TargetControlRodStatus", getTargetControlRodStatus());
        nbt.put("ThermalPos", parsePosToTag(thermalPos));
        for (int i = 0; i < 4; i++)
            if (valvePos != null && valvePos.size() - 1 >= i) nbt.put("ValvePos-" + i, parsePosToTag(valvePos.get(i)));
        for (int i = 0; i < 9; i++)
            if (turbinePos != null && turbinePos.size() - 1 >= i)
                nbt.put("TurbinePos-" + i, parsePosToTag(turbinePos.get(i)));

        return nbt;
    }

    @Override
    public void handleUpdateTag(final CompoundTag tag) {
        setAssembled(tag.getBoolean("Assembled"));
        // Reactor
        setReactorTargetTemperature(tag.getFloat("ReactorTargetTemperature"));
        setReactorCurrentTemperature(tag.getFloat("ReactorCurrentTemperature"));
        setReactorTargetLoadSet(tag.getByte("ReactorTargetLoadSet"));
        setReactorCurrentLoadSet(tag.getByte("ReactorCurrentLoadSet"));
        setReactorRunningSince(tag.getLong("ReactorRunningSince"));
        setReactorStatus(tag.getFloat("ReactorStatus"));
        setReactorContainment(tag.getFloat("ReactorContainment"));
        setReactorRadiation(tag.getFloat("ReactorRadiation"));
        setReactorPressure(tag.getFloat("ReactorPressure"));
        setFuelAdditions(tag.getInt("FuelAdditions"));
        setScrammed(tag.getBoolean("Scrammed"));
        setReactorState(ReactorStateEnum.get(tag.getString("ReactorState")));
        FLUID_TANK_IN.readFromNBT(tag.getCompound("FluidIn"));
        FLUID_TANK_OUT.readFromNBT(tag.getCompound("FluidOut"));
        reactorCapacity = tag.getInt("ReactorCapacity");
        reactorHeight = tag.getInt("ReactorHeight");
        soundTicks = tag.getLong("SoundTicks");
        // Rod
        setDepletedFuelRodStatus(tag.getByteArray("DepletedFuelRodStatus"));
        setFuelRodStatus(tag.getByteArray("FuelRodStatus"));
        setControlRodStatus(tag.getByteArray("ControlRodStatus"));
        setTargetControlRodStatus(tag.getByteArray("TargetControlRodStatus"));
        valvePos = new ArrayList<>(4);
        turbinePos = new ArrayList<>(9);
        thermalPos = BlockEntity.getPosFromTag(tag.getCompound("ThermalPos"));
        for (int i = 0; i < 4; i++)
            if (tag.contains("ValvePos-" + i))
                valvePos.add(BlockEntity.getPosFromTag(tag.getCompound("ValvePos-" + i)));
        for (int i = 0; i < 9; i++)
            if (tag.contains("TurbinePos-" + i))
                turbinePos.add(BlockEntity.getPosFromTag(tag.getCompound("TurbinePos-" + i)));
    }

    @Override
    public void saveAdditional(@NotNull CompoundTag nbt) {
        nbt.putBoolean("Assembled", isAssembled());
        // Reactor
        nbt.putFloat("ReactorTargetTemperature", getReactorTargetTemperature());
        nbt.putFloat("ReactorCurrentTemperature", getReactorCurrentTemperature());
        nbt.putByte("ReactorTargetLoadSet", getReactorTargetLoadSet());
        nbt.putByte("ReactorCurrentLoadSet", getReactorCurrentLoadSet());
        nbt.putLong("ReactorRunningSince", getReactorRunningSince());
        nbt.putFloat("ReactorStatus", getReactorStatus());
        nbt.putFloat("ReactorContainment", getReactorContainment());
        nbt.putFloat("ReactorRadiation", getReactorRadiation());
        nbt.putFloat("ReactorPressure", getReactorPressure());
        nbt.putInt("FuelAdditions", getFuelAdditions());
        nbt.putBoolean("Scrammed", isScrammed());
        nbt.putString("ReactorState", getReactorState().toString());
        nbt.put("FluidIn", FLUID_TANK_IN.writeToNBT(new CompoundTag()));
        nbt.put("FluidOut", FLUID_TANK_OUT.writeToNBT(new CompoundTag()));
        nbt.putInt("ReactorCapacity", reactorCapacity);
        nbt.putInt("ReactorHeight", reactorHeight);
        nbt.putLong("SoundTicks", soundTicks);
        // Rod
        nbt.putByteArray("DepletedFuelRodStatus", getDepletedFuelRodStatus());
        nbt.putByteArray("FuelRodStatus", getFuelRodStatus());
        nbt.putByteArray("ControlRodStatus", getControlRodStatus());
        nbt.putByteArray("TargetControlRodStatus", getTargetControlRodStatus());
        nbt.put("ThermalPos", parsePosToTag(thermalPos));
        for (int i = 0; i < 4; i++)
            if (valvePos != null && valvePos.size() - 1 >= i) nbt.put("ValvePos-" + i, parsePosToTag(valvePos.get(i)));
        for (int i = 0; i < 9; i++)
            if (turbinePos != null && turbinePos.size() - 1 >= i)
                nbt.put("TurbinePos-" + i, parsePosToTag(turbinePos.get(i)));

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
        setReactorStatus(nbt.getFloat("ReactorStatus"));
        setReactorContainment(nbt.getFloat("ReactorContainment"));
        setReactorRadiation(nbt.getFloat("ReactorRadiation"));
        setReactorPressure(nbt.getFloat("ReactorPressure"));
        setFuelAdditions(nbt.getInt("FuelAdditions"));
        setScrammed(nbt.getBoolean("Scrammed"));
        setReactorState(ReactorStateEnum.get(nbt.getString("ReactorState")));
        FLUID_TANK_IN.readFromNBT(nbt.getCompound("FluidIn"));
        FLUID_TANK_OUT.readFromNBT(nbt.getCompound("FluidOut"));
        reactorCapacity = nbt.getInt("ReactorCapacity");
        reactorHeight = nbt.getInt("ReactorHeight");
        soundTicks = nbt.getLong("SoundTicks");
        // Rod
        setDepletedFuelRodStatus(nbt.getByteArray("DepletedFuelRodStatus"));
        setFuelRodStatus(nbt.getByteArray("FuelRodStatus"));
        setControlRodStatus(nbt.getByteArray("ControlRodStatus"));
        setTargetControlRodStatus(nbt.getByteArray("TargetControlRodStatus"));
        valvePos = new ArrayList<>(4);
        turbinePos = new ArrayList<>(9);
        thermalPos = BlockEntity.getPosFromTag(nbt.getCompound("ThermalPos"));
        for (int i = 0; i < 4; i++)
            if (nbt.contains("ValvePos-" + i))
                valvePos.add(BlockEntity.getPosFromTag(nbt.getCompound("ValvePos-" + i)));
        for (int i = 0; i < 9; i++)
            if (nbt.contains("TurbinePos-" + i))
                turbinePos.add(BlockEntity.getPosFromTag(nbt.getCompound("TurbinePos-" + i)));
    }

    public void setThermalPos(BlockPos thermalPos) {
        this.thermalPos = thermalPos;
    }

    public BlockPos getThermalPos() {
        return thermalPos;
    }

    public void setTurbinePos(List<BlockPos> turbinePos) {
        this.turbinePos = turbinePos;
    }

    public List<BlockPos> getTurbinePos() {
        return turbinePos;
    }

    public void addTurbinePos(BlockPos pos) {
        if (turbinePos == null) turbinePos = new ArrayList<>(9);
        if (turbinePos.size() < 9) turbinePos.add(pos);
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
        if (level != null && !level.isClientSide) {
            BlockState state = level.getBlockState(worldPosition);
            level.sendBlockUpdated(worldPosition, state, state, 2);
            setChanged(level, getBlockPos(), state);
        }
    }

    public byte[] getDepletedFuelRodStatus() {
        return depletedFuelRodStatus;
    }

    public byte getDepletedFuelRodStatus(byte index) {
        if (getDepletedFuelRodStatus().length == 0) return 0;
        return getDepletedFuelRodStatus()[index];
    }

    public void setDepletedFuelRodStatus(byte[] depletedFuelRodStatus) {
        this.depletedFuelRodStatus = depletedFuelRodStatus;
    }

    public void setDepletedFuelRodStatus(byte index, byte value) {
        getDepletedFuelRodStatus()[index] = value;
        setChanged();
    }

    public byte[] getFuelRodStatus() {
        return fuelRodStatus;
    }

    public byte getFuelRodStatus(byte index) {
        if (getFuelRodStatus().length == 0) return 0;
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
        if (getControlRodStatus().length == 0) return 0;
        return getControlRodStatus()[index];
    }

    public void setControlRodStatus(byte[] controlRodStatus) {
        this.controlRodStatus = controlRodStatus;
    }

    public void setControlRodStatus(byte index, byte value) {
        getControlRodStatus()[index] = value;
    }

    public void setTargetControlRodStatus(byte index, byte value) {
        getTargetControlRodStatus()[index] = value;
    }

    public byte getTargetControlRodStatus(byte index) {
        return targetControlRodStatus[index];
    }

    public void setTargetControlRodStatus(byte[] targetControlRodStatus) {
        this.targetControlRodStatus = targetControlRodStatus;
    }

    public byte[] getTargetControlRodStatus() {
        return targetControlRodStatus;
    }

    public BlockState getState(BlockPos pos) {
        return this.level.getBlockState(pos);
    }

    public boolean isAssembled() {
        return assembled;
    }

    public void setAssembled(boolean canBeAssembled) {
        this.assembled = canBeAssembled;
    }

    public float getReactorCurrentTemperature() {
        return reactorCurrentTemperature;
    }

    public void setReactorCurrentTemperature(float reactorCurrentTemperature) {
        this.reactorCurrentTemperature = reactorCurrentTemperature;
    }

    public float getReactorTargetTemperature() {
        return reactorTargetTemperature;
    }

    public void setReactorTargetTemperature(float reactorTargetTemperature) {
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

    public float getReactorStatus() {
        return reactorStatus;
    }

    public void setReactorStatus(float reactorStatus) {
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

    public boolean isTurbineActivated() {
        return turbineActivated;
    }

    public void setTurbineActivated(boolean turbineActivated) {
        this.turbineActivated = turbineActivated;
    }

    public boolean isTurbineCoilsEngaged() {
        return turbineCoilsEngaged;
    }

    public void setTurbineCoilsEngaged(boolean turbineCoilsEngaged) {
        this.turbineCoilsEngaged = turbineCoilsEngaged;
    }

    public byte getTurbineTargetFlow() {
        return turbineTargetFlow;
    }

    public void setTurbineTargetFlow(byte turbineTargetFlow) {
        this.turbineTargetFlow = turbineTargetFlow;
    }

    public byte getTurbineSpeed() {
        return turbineSpeed;
    }

    public void setTurbineSpeed(byte turbineSpeed) {
        this.turbineSpeed = turbineSpeed;
    }

    public byte getTurbineCurrentFlow() {
        return turbineCurrentFlow;
    }

    public void setTurbineCurrentFlow(byte turbineCurrentFlow) {
        this.turbineCurrentFlow = turbineCurrentFlow;
    }

    public long getTurbinePowerGeneration() {
        return turbinePowerGeneration;
    }

    public void setTurbinePowerGeneration(long turbinePowerGeneration) {
        this.turbinePowerGeneration = turbinePowerGeneration;
    }
}
