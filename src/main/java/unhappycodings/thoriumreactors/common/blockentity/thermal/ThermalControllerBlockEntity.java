package unhappycodings.thoriumreactors.common.blockentity.thermal;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import unhappycodings.thoriumreactors.common.block.thermal.ThermalValveBlock;
import unhappycodings.thoriumreactors.common.blockentity.ModFluidTank;
import unhappycodings.thoriumreactors.common.blockentity.thermal.base.ThermalFrameBlockEntity;
import unhappycodings.thoriumreactors.common.enums.ThermalValveTypeEnum;
import unhappycodings.thoriumreactors.common.registration.ModBlockEntities;
import unhappycodings.thoriumreactors.common.registration.ModBlocks;
import unhappycodings.thoriumreactors.common.registration.ModFluids;

import java.util.ArrayList;
import java.util.List;

public class ThermalControllerBlockEntity extends ThermalFrameBlockEntity {
    public List<BlockPos> valvePos = new ArrayList<>(4);
    public boolean assembled;
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
                            } else if (getFluidAmountIn() + amount <= getFluidCapacityIn()) {
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
                            int fillable = storage.fill(new FluidStack(ModFluids.SOURCE_DEPLETED_MOLTEN_SALT.get(), amount), IFluidHandler.FluidAction.SIMULATE);
                            if (fillable > 0 && conversions + fillable <= 1000) {
                                storage.fill(new FluidStack(ModFluids.SOURCE_DEPLETED_MOLTEN_SALT.get(), fillable), IFluidHandler.FluidAction.EXECUTE);
                                conversions += amount;
                                getFluidIn().shrink(fillable);
                            }
                        }
                    });
                }

                if (level.getBlockState(pos).getValue(ThermalValveBlock.TYPE) == ThermalValveTypeEnum.COOLANT_INPUT && conversions >= 10) {
                    entity.getCapability(ForgeCapabilities.FLUID_HANDLER).ifPresent(storageInput -> {
                        FluidStack coolantIn = storageInput.getFluidInTank(0);
                        int amount = Math.min(coolantIn.getAmount(), 5);
                        if (amount > 0 && coolantIn.getFluid().isSame(Fluids.WATER)) {
                            for (BlockPos blockPos : valvePos) {
                                if (!level.getBlockState(blockPos).is(ModBlocks.THERMAL_VALVE.get())) return;
                                if (level.getBlockState(blockPos).getValue(ThermalValveBlock.TYPE) == ThermalValveTypeEnum.COOLANT_OUTPUT) {
                                    ThermalValveBlockEntity valveBlock = (ThermalValveBlockEntity) level.getBlockEntity(blockPos);
                                    valveBlock.getCapability(ForgeCapabilities.FLUID_HANDLER).ifPresent(storageOutput -> {
                                        FluidStack coolantOut = storageOutput.getFluidInTank(0);
                                        if (coolantOut.getAmount() + amount * 1300 <= storageOutput.getTankCapacity(0)) {
                                            coolantIn.shrink(amount);
                                            storageOutput.fill(new FluidStack(ModFluids.SOURCE_STEAM.get(), amount * 1300), IFluidHandler.FluidAction.EXECUTE);
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

    public int getConversions() {
        return conversions;
    }

    public void setConversions(int conversions) {
        this.conversions = conversions;
    }

    @NotNull
    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag nbt = super.getUpdateTag();
        nbt.putBoolean("Assembled", isAssembled());
        nbt.putInt("Conversions", conversions);
        nbt.put("FluidIn", FLUID_TANK_IN.writeToNBT(new CompoundTag()));
        for (int i = 0; i < 4; i++)
            if (valvePos != null && valvePos.size() > i) nbt.put("ValvePos-" + (i + 1), parsePosToTag(valvePos.get(i)));
        return nbt;
    }

    @Override
    public void handleUpdateTag(final CompoundTag tag) {
        setAssembled(tag.getBoolean("Assembled"));
        setConversions(tag.getInt("Conversions"));
        FLUID_TANK_IN.readFromNBT(tag.getCompound("FluidIn"));
        valvePos = new ArrayList<>(4);
        for (int i = 0; i < 4; i++) valvePos.add(BlockEntity.getPosFromTag(tag.getCompound("ValvePos-" + (i + 1))));
        super.handleUpdateTag(tag);
    }

    @Override
    public void saveAdditional(@NotNull CompoundTag nbt) {
        nbt.putBoolean("Assembled", isAssembled());
        nbt.putInt("Conversions", conversions);
        nbt.put("FluidIn", FLUID_TANK_IN.writeToNBT(new CompoundTag()));
        for (int i = 0; i < 4; i++)
            if (valvePos != null && valvePos.size() > i) nbt.put("ValvePos-" + (i + 1), parsePosToTag(valvePos.get(i)));
        super.saveAdditional(nbt);
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        setAssembled(nbt.getBoolean("Assembled"));
        setConversions(nbt.getInt("Conversions"));
        FLUID_TANK_IN.readFromNBT(nbt.getCompound("FluidIn"));
        valvePos = new ArrayList<>(4);
        for (int i = 0; i < 4; i++) valvePos.add(BlockEntity.getPosFromTag(nbt.getCompound("ValvePos-" + (i + 1))));
        super.load(nbt);
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
