package unhappycodings.thoriumreactors.common.blockentity.thermal;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import unhappycodings.thoriumreactors.common.block.reactor.ReactorControllerBlock;
import unhappycodings.thoriumreactors.common.block.thermal.ThermalControllerBlock;
import unhappycodings.thoriumreactors.common.block.thermal.ThermalValveBlock;
import unhappycodings.thoriumreactors.common.blockentity.ModFluidTank;
import unhappycodings.thoriumreactors.common.enums.ParticleTypeEnum;
import unhappycodings.thoriumreactors.common.enums.ThermalValveTypeEnum;
import unhappycodings.thoriumreactors.common.network.PacketHandler;
import unhappycodings.thoriumreactors.common.network.toclient.reactor.ClientReactorParticleDataPacket;
import unhappycodings.thoriumreactors.common.registration.ModBlockEntities;
import unhappycodings.thoriumreactors.common.registration.ModBlocks;
import unhappycodings.thoriumreactors.common.registration.ModFluids;
import unhappycodings.thoriumreactors.common.util.CalculationUtil;

import java.util.ArrayList;
import java.util.List;

public class ThermalControllerBlockEntity extends BlockEntity {
    public List<BlockPos> valvePos;
    public boolean canBeAssembled;
    public boolean assembled;
    public String warning = "";
    int conversions = 0;

    private final ModFluidTank FLUID_TANK_IN = new ModFluidTank(1000, true, true, 0, FluidStack.EMPTY);

    public ThermalControllerBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.THERMAL_CONTROLLER.get(), pPos, pBlockState);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        updateBlock();
    }

    public void tick() {
        if (level.getGameTime() % 10 == 0) {
            if (getBlockState().getValue(ReactorControllerBlock.POWERED) != assembled)
                getLevel().setBlock(getBlockPos(), getBlockState().setValue(ReactorControllerBlock.POWERED, assembled), 3);

            // Check if bottom layer is filled with 4 valves and conductors
            updateFloorPositions();
            if (!canBeAssembled) { resetAssembled(); return; }

            // If everything is assembled right, we continue here
            Direction direction = getBlockState().getValue(ThermalControllerBlock.FACING);
            long x = direction == Direction.WEST || direction == Direction.EAST ? 3 : 5, y = 3;
            if (x == 3) y = 5;
            if (!assembled) {
                for (Player player : level.players()) {
                    PacketHandler.sendToClient(new ClientReactorParticleDataPacket(addParticleOffset(getBlockPos(), getBlockState().getValue(ThermalControllerBlock.FACING)), ParticleTypeEnum.REACTOR, x, 2, y), (ServerPlayer) player);
                }
            }
            assembled = true;
        }
        if (isAssembled()) {
            if (valvePos == null) return;
            for (BlockPos pos : valvePos) {
                if (!(level.getBlockEntity(pos) instanceof ThermalValveBlockEntity entity)) return;
                if (level.getBlockState(pos).getValue(ThermalValveBlock.TYPE) == ThermalValveTypeEnum.HEATING_FLUID_INPUT) {
                    entity.getCapability(ForgeCapabilities.FLUID_HANDLER).ifPresent(storage -> {
                        FluidStack fluidExternal = storage.getFluidInTank(0);
                        int amount = fluidExternal.getAmount();
                        if (fluidExternal.getFluid().isSame(ModFluids.SOURCE_HEATED_MOLTEN_SALT.get()) && amount > 0) {
                            if (FLUID_TANK_IN.getFluid().isEmpty()) {
                                FLUID_TANK_IN.setFluid(new FluidStack(ModFluids.SOURCE_HEATED_MOLTEN_SALT.get(), amount));
                                storage.getFluidInTank(0).shrink(amount);
                            } else if (getFluidAmountIn() + amount <= getFluidCapacityIn()){
                                getFluidIn().grow(amount);
                                storage.getFluidInTank(0).shrink(amount);
                            }
                        }
                    });
                }

                if (level.getBlockState(pos).getValue(ThermalValveBlock.TYPE) == ThermalValveTypeEnum.HEATING_FLUID_OUTPUT) {
                    entity.getCapability(ForgeCapabilities.FLUID_HANDLER).ifPresent(storage -> {
                        int amount = Math.min(getFluidAmountIn(), 10);
                        if (getFluidIn().getFluid().isSame(ModFluids.SOURCE_HEATED_MOLTEN_SALT.get()) && amount > 0) {
                            int fillable = storage.fill(new FluidStack(ModFluids.SOURCE_MOLTEN_SALT.get(), amount), IFluidHandler.FluidAction.SIMULATE);
                            if (fillable > 0 && conversions + fillable <= 100) {
                                storage.fill(new FluidStack(ModFluids.SOURCE_MOLTEN_SALT.get(), fillable), IFluidHandler.FluidAction.EXECUTE);
                                conversions += amount;
                                getFluidIn().shrink(fillable);
                            }
                        }
                    });
                }

                if (level.getBlockState(pos).getValue(ThermalValveBlock.TYPE) == ThermalValveTypeEnum.COOLANT_INPUT && conversions >= 10) {
                    entity.getCapability(ForgeCapabilities.FLUID_HANDLER).ifPresent(storageInput -> {
                        FluidStack coolantIn = storageInput.getFluidInTank(0);
                        int amount = Math.min(coolantIn.getAmount(), 50);
                        if (amount > 0 && coolantIn.getFluid().isSame(Fluids.WATER)) {
                            for (BlockPos blockPos : valvePos) {
                                if (!level.getBlockState(blockPos).is(ModBlocks.THERMAL_VALVE.get())) return;
                                if (level.getBlockState(blockPos).getValue(ThermalValveBlock.TYPE) == ThermalValveTypeEnum.COOLANT_OUTPUT) {
                                    ThermalValveBlockEntity valveBlock = (ThermalValveBlockEntity) level.getBlockEntity(blockPos);
                                    valveBlock.getCapability(ForgeCapabilities.FLUID_HANDLER).ifPresent(storageOutput -> {
                                        FluidStack coolantOut = storageOutput.getFluidInTank(0);
                                        if (coolantOut.getAmount() + amount <= storageOutput.getTankCapacity(0)) {
                                            coolantIn.shrink(amount);
                                            storageOutput.fill(new FluidStack(ModFluids.SOURCE_STEAM.get(), amount * 2), IFluidHandler.FluidAction.EXECUTE);
                                            conversions -= 10;
                                        }
                                    });
                                }
                            }
                        }
                    });
                }
            }
        }
    }

    public FluidStack getFluidIn() {
        return FLUID_TANK_IN.getFluid();
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

    public BlockPos addParticleOffset(BlockPos pos, Direction direction) {
        return switch (direction) {
            case WEST -> pos.offset(0, 0, -2);
            case EAST -> pos.offset(-2, 0, -2);
            case SOUTH -> pos.offset(-2, 0, -2);
            default -> pos.offset(-2, 0, 0);
        };
    }

    public void updateFloorPositions() {
        Direction direction = getBlockState().getValue(ThermalControllerBlock.FACING);
        int lenghtRight = 0, lenghtLeft = 0, lenghtBack = 0;
        valvePos = new ArrayList<>();

        for (int i = 1; i < 4; i++) {
            BlockState leftState = level.getBlockState(getBlockPos().relative(direction.getClockWise(), i));
            if (leftState.is(ModBlocks.THERMAL_CONDUCTOR.get()) || leftState.is(ModBlocks.THERMAL_VALVE.get())) lenghtLeft = i;
            else { canBeAssembled = false; break; }
        }
        for (int i = 1; i < 4; i++) {
            BlockState rightState = level.getBlockState(getBlockPos().relative(direction.getCounterClockWise(), i));
            if (rightState.is(ModBlocks.THERMAL_CONDUCTOR.get()) || rightState.is(ModBlocks.THERMAL_VALVE.get())) lenghtRight = i;
            else { canBeAssembled = false; break; }
        }
        if (lenghtLeft > 0 && lenghtLeft == lenghtRight) {
            for (int i = 1; i < 3; i++) {
                BlockState backState = level.getBlockState(getBlockPos().relative(direction.getClockWise(), lenghtLeft).relative(direction.getClockWise().getClockWise(), i));
                if (backState.is(ModBlocks.THERMAL_CONDUCTOR.get()) || backState.is(ModBlocks.THERMAL_VALVE.get())) lenghtBack = i;
                else { canBeAssembled = false; break; }
            }
            if (lenghtBack < 2) resetAssembled("Invalid size of Heat Exchanger floor!");

            List<BlockPos> positions = CalculationUtil.getBlockPositions(getBlockPos().relative(direction.getCounterClockWise(), lenghtRight), getBlockPos().relative(direction.getClockWise(), lenghtLeft).relative(direction.getClockWise().getClockWise(), lenghtBack), level);
            for (BlockPos pos : positions) {
                if (!(level.getBlockState(pos).is(ModBlocks.THERMAL_CONDUCTOR.get()) || level.getBlockState(pos).is(ModBlocks.THERMAL_VALVE.get()) || isSameBlockPos(pos, getBlockPos()))) {
                    resetAssembled("Floor has to be filled with Thermal Conductors! Block at " + pos);
                    return;
                }
                if (level.getBlockState(pos).is(ModBlocks.THERMAL_VALVE.get())) valvePos.add(pos);
            }
            for (BlockPos pos : positions) {
                if (!level.getBlockState(pos.above()).is(ModBlocks.THERMAL_HEAT_SINK.get())) {
                    resetAssembled("Missing Heat Sink at upper structure! Block at " + pos);
                    return;
                }
            }
            if (valvePos.size() != 4) {
                resetAssembled("Heat Exchanger is in need of four valves. Currently " + valvePos.size());
            }
        } else {
            resetAssembled("Heat Exchanger lenght is not valid. Must be inbetween 3 and 7 blocks. Controller must be placed in the middle!");
        }

        canBeAssembled = (lenghtLeft > 0 && lenghtLeft == lenghtRight && valvePos.size() == 4);
    }

    public boolean isSameBlockPos(BlockPos pos1, BlockPos pos2) {
        return pos1.getX() == pos2.getX() && pos1.getY() == pos2.getY() && pos1.getZ() == pos2.getZ();
    }

    public void resetAssembled() {
        canBeAssembled = false;
        assembled = false;

        if (getBlockState().getValue(ThermalControllerBlock.POWERED) != assembled)
            getLevel().setBlock(getBlockPos(), getBlockState().setValue(ThermalControllerBlock.POWERED, assembled), 3);
    }

    public void resetAssembled(String text) {
        if (warning != null)
            warning = text;
        resetAssembled();
    }

    @NotNull
    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag nbt = new CompoundTag();
        nbt.putBoolean("Assembled", isAssembled());
        nbt.put("FluidIn", FLUID_TANK_IN.writeToNBT(new CompoundTag()));
        return nbt;
    }

    @Override
    public void handleUpdateTag(final CompoundTag tag) {
        setAssembled(tag.getBoolean("Assembled"));
        FLUID_TANK_IN.readFromNBT(tag.getCompound("FluidIn"));
    }

    @Override
    public void saveAdditional(@NotNull CompoundTag nbt) {
        nbt.putBoolean("Assembled", isAssembled());
        nbt.put("FluidIn", FLUID_TANK_IN.writeToNBT(new CompoundTag()));
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        setAssembled(nbt.getBoolean("Assembled"));
        FLUID_TANK_IN.readFromNBT(nbt.getCompound("FluidIn"));
    }

    public boolean isAssembled() {
        return assembled;
    }

    public void setAssembled(boolean canBeAssembled) {
        this.assembled = canBeAssembled;
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

    public void updateBlock() {
        if (level != null && !level.isClientSide) {
            BlockState state = level.getBlockState(worldPosition);
            level.sendBlockUpdated(worldPosition, state, state, 2);
            setChanged(level, getBlockPos(), state);
        }
    }

}
