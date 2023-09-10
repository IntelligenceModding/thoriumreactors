package unhappycodings.thoriumreactors.client.integration.top;

import mcjty.theoneprobe.api.*;
import mcjty.theoneprobe.apiimpl.elements.ElementProgress;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.common.block.reactor.ReactorValveBlock;
import unhappycodings.thoriumreactors.common.block.thermal.ThermalValveBlock;
import unhappycodings.thoriumreactors.common.blockentity.base.MachineContainerBlockEntity;
import unhappycodings.thoriumreactors.common.blockentity.reactor.ReactorControllerBlockEntity;
import unhappycodings.thoriumreactors.common.blockentity.reactor.ReactorValveBlockEntity;
import unhappycodings.thoriumreactors.common.blockentity.reactor.base.ReactorFrameBlockEntity;
import unhappycodings.thoriumreactors.common.blockentity.tank.FluidTankBlockEntity;
import unhappycodings.thoriumreactors.common.blockentity.thermal.ThermalValveBlockEntity;
import unhappycodings.thoriumreactors.common.blockentity.thermal.base.ThermalFrameBlockEntity;
import unhappycodings.thoriumreactors.common.blockentity.turbine.TurbineControllerBlockEntity;
import unhappycodings.thoriumreactors.common.blockentity.turbine.TurbinePowerPortBlockEntity;
import unhappycodings.thoriumreactors.common.blockentity.turbine.TurbineValveBlockEntity;
import unhappycodings.thoriumreactors.common.blockentity.turbine.base.TurbineFrameBlockEntity;
import unhappycodings.thoriumreactors.common.enums.ValveTypeEnum;
import unhappycodings.thoriumreactors.common.util.FormattingUtil;

import java.text.SimpleDateFormat;
import java.util.function.Function;

public class TOPInfoPlugin implements IProbeInfoProvider, Function<ITheOneProbe, Void> {
    public static final int COLOR_A = Color.rgb(66, 150, 0, 255);
    public static final int COLOR_B = Color.rgb(67, 204, 0, 255);

    @Override
    public Void apply(ITheOneProbe iTheOneProbe) {
        iTheOneProbe.registerProvider(this);
        return null;
    }

    @Override
    public ResourceLocation getID() {
        return new ResourceLocation(ThoriumReactors.MOD_ID, "data");
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level, BlockState blockState, IProbeHitData iProbeHitData) {
        if (!player.isShiftKeyDown() || probeMode != ProbeMode.EXTENDED) return;

        if (level.getBlockEntity(iProbeHitData.getPos()) instanceof FluidTankBlockEntity) {

            for (Direction value : Direction.values()) {
                level.getBlockEntity(iProbeHitData.getPos()).getCapability(ForgeCapabilities.FLUID_HANDLER, value).ifPresent(storage -> {
                    boolean isCreative = storage.getFluidInTank(0).getAmount() == Integer.MAX_VALUE;
                    iProbeInfo.horizontal(iProbeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER)).mcText(storage.getFluidInTank(1).getDisplayName().copy().append(Component.literal(": "))).tankSimple(storage.getTankCapacity(1), storage.getFluidInTank(1), iProbeInfo.defaultProgressStyle().numberFormat(isCreative ? NumberFormat.NONE : NumberFormat.FULL).suffix(Component.literal(isCreative ? "Infinite " : "mb " + FormattingUtil.formatPercentNum(storage.getFluidInTank(1).getAmount(), storage.getTankCapacity(1), false))).borderColor(Color.rgb(65, 65, 65, 255)).backgroundColor(Color.rgb(0, 0, 0, 0)));

                });
                return;
            }
        } else if (level.getBlockEntity(iProbeHitData.getPos()) instanceof TurbineControllerBlockEntity entity) {

            if (!entity.isActivated()) {
                iProbeInfo.mcText(Component.translatable(FormattingUtil.getTranslatable("turbine.top_info.turbine")).append(" ").append(Component.translatable(FormattingUtil.getTranslatable("turbine.top_info.inactive"))).withStyle(FormattingUtil.hex(0x9F0006)));
                return;
            } else {
                iProbeInfo.mcText(Component.translatable(FormattingUtil.getTranslatable("turbine.top_info.turbine")).append(" ").append(Component.translatable(FormattingUtil.getTranslatable("turbine.top_info.active"))).withStyle(FormattingUtil.hex(0x00A90B)));
            }
            if (level.getBlockEntity(entity.getPowerPortPos()) instanceof TurbinePowerPortBlockEntity powerPortBlockEntity)
                iProbeInfo.horizontal(iProbeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER)).mcText(Component.translatable(FormattingUtil.getTranslatable("machines.top_info.energy"))).progress(powerPortBlockEntity.getEnergy(), powerPortBlockEntity.getEnergyCapacity(), iProbeInfo.defaultProgressStyle().filledColor(COLOR_A).alternateFilledColor(COLOR_B).backgroundColor(Color.rgb(255, 255, 255, 255)).borderColor(Color.rgb(65, 65, 65, 255)).numberFormat(NumberFormat.COMMAS).suffix(" FE").showText(true));
            if (level.getBlockEntity(entity.getValvePos()) instanceof TurbineValveBlockEntity valveBlockEntity)
                valveTankRender(iProbeInfo, valveBlockEntity.getFluidIn(), valveBlockEntity.getFluidCapacityIn(), valveBlockEntity.getFluidAmountIn());

            iProbeInfo.mcText(Component.translatable(FormattingUtil.getTranslatable("turbine.top_info.producing")).withStyle(FormattingUtil.hex(0x7ED355)).append(Component.literal(entity.isCoilsEngaged() ? FormattingUtil.formatEnergy(entity.getRpm() * (FormattingUtil.getTurbineGenerationModifier(entity.getRpm()) * entity.getEnergyModifier())) : "0 FE" + "/t").withStyle(ChatFormatting.WHITE)));
            iProbeInfo.mcText(Component.translatable(FormattingUtil.getTranslatable("turbine.top_info.speed")).withStyle(FormattingUtil.hex(0x55D38A)).append(Component.literal(Math.floor(entity.getRpm() * 100) / 100 + " ").append(Component.translatable(FormattingUtil.getTranslatable("turbine.top_info.rpm"))).withStyle(ChatFormatting.WHITE)));
            iProbeInfo.mcText(Component.translatable(FormattingUtil.getTranslatable("turbine.top_info.flowrate")).withStyle(FormattingUtil.hex(0x0ACECE)).append(Component.literal(entity.getCurrentFlowrate() + " mB/t").withStyle(ChatFormatting.WHITE)));
            iProbeInfo.mcText(Component.translatable(FormattingUtil.getTranslatable("turbine.top_info.best_performing_at")).withStyle(FormattingUtil.hex(0x55D38A)));
        } else if (level.getBlockEntity(iProbeHitData.getPos()) instanceof ReactorControllerBlockEntity entity) {

            if (!entity.isReactorActive()) {
                iProbeInfo.mcText(Component.translatable(FormattingUtil.getTranslatable("reactor.top_info.reactor")).append(" ").append(Component.translatable(FormattingUtil.getTranslatable("reactor.top_info.inactive"))).withStyle(FormattingUtil.hex(0x9F0006)));
                return;
            } else {
                iProbeInfo.mcText(Component.translatable(FormattingUtil.getTranslatable("reactor.top_info.reactor")).append(" ").append(Component.translatable(FormattingUtil.getTranslatable("reactor.top_info.active"))).withStyle(FormattingUtil.hex(0x00A90B)));
            }

            for (int i = 0; i < entity.valvePos.size(); i++) {
                if (level.getBlockEntity(entity.valvePos.get(i)) instanceof ReactorValveBlockEntity valveBlockEntity) {
                    if (valveBlockEntity.getBlockState().getValue(ReactorValveBlock.TYPE) == ValveTypeEnum.FLUID_INPUT || valveBlockEntity.getBlockState().getValue(ReactorValveBlock.TYPE) == ValveTypeEnum.FLUID_OUTPUT) {
                        valveTankRender(iProbeInfo, valveBlockEntity.getFluidIn(), valveBlockEntity.getFluidCapacityIn(), valveBlockEntity.getFluidAmountIn());
                    }
                }
            }

            int fuelValue = 0, controlValue = 0;
            for (int i = 0; i < entity.getFuelRodStatus().length; i++) fuelValue += entity.getFuelRodStatus()[i];
            for (int i = 0; i < entity.getControlRodStatus().length; i++)
                controlValue += entity.getControlRodStatus()[i];

            iProbeInfo.mcText(Component.translatable(FormattingUtil.getTranslatable("reactor.top_info.rod_insert")).withStyle(FormattingUtil.hex(0x7ED355)).append(Component.literal((int) ((controlValue / 6400f) * 100) + "%").withStyle(ChatFormatting.WHITE)));
            iProbeInfo.mcText(Component.translatable(FormattingUtil.getTranslatable("reactor.top_info.uranium_fuel")).withStyle(FormattingUtil.hex(0x55D38A)).append(Component.literal((int) ((fuelValue / 8100f) * 100) + "%").withStyle(ChatFormatting.WHITE)));
            iProbeInfo.mcText(Component.translatable(FormattingUtil.getTranslatable("reactor.top_info.reactor_load")).withStyle(FormattingUtil.hex(0x0ACECE)).append(Component.literal((int) (((entity.getReactorCurrentTemperature() - 22) / 949) * 100) + "%").withStyle(ChatFormatting.WHITE)));
            iProbeInfo.mcText(Component.translatable(FormattingUtil.getTranslatable("reactor.top_info.status")).withStyle(FormattingUtil.hex(0x0ACECE)).append(Component.literal(Math.floor(entity.getReactorStatus() * 10) / 10 + "%").withStyle(ChatFormatting.WHITE)));

        } else if (level.getBlockEntity(iProbeHitData.getPos()) instanceof MachineContainerBlockEntity || level.getBlockEntity(iProbeHitData.getPos()) instanceof TurbineFrameBlockEntity ||
                level.getBlockEntity(iProbeHitData.getPos()) instanceof ThermalFrameBlockEntity || level.getBlockEntity(iProbeHitData.getPos()) instanceof ReactorFrameBlockEntity) {
            iProbeInfo.getElements().removeIf(element -> element instanceof ElementProgress);

            for (Direction value : Direction.values()) {
                level.getBlockEntity(iProbeHitData.getPos()).getCapability(ForgeCapabilities.ENERGY, value).ifPresent(storage -> {
                    iProbeInfo.horizontal(iProbeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER)).mcText(Component.translatable(FormattingUtil.getTranslatable("machines.top_info.energy"))).progress(storage.getEnergyStored(), storage.getMaxEnergyStored(), iProbeInfo.defaultProgressStyle().filledColor(COLOR_A).alternateFilledColor(COLOR_B).backgroundColor(Color.rgb(255, 255, 255, 255)).borderColor(Color.rgb(65, 65, 65, 255)).numberFormat(NumberFormat.COMMAS).suffix(" FE").showText(true));
                });
            }

            for (Direction value : Direction.values()) {
                level.getBlockEntity(iProbeHitData.getPos()).getCapability(ForgeCapabilities.FLUID_HANDLER, value).ifPresent(storage -> {
                    iProbeInfo.horizontal(iProbeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER)).mcText(storage.getFluidInTank(1).getDisplayName().copy().append(Component.literal(": "))).tankSimple(storage.getTankCapacity(1), storage.getFluidInTank(1), iProbeInfo.defaultProgressStyle().suffix(Component.literal("mb " + FormattingUtil.formatPercentNum(storage.getFluidInTank(1).getAmount(), storage.getTankCapacity(1), false))).borderColor(Color.rgb(65, 65, 65, 255)).backgroundColor(Color.rgb(0, 0, 0, 0)));
                });
            }

            if (level.getBlockEntity(iProbeHitData.getPos()) instanceof MachineContainerBlockEntity containerBlockEntity) {
                CompoundTag tag = containerBlockEntity.getUpdateTag();
                if (tag.getInt("RecipeTime") != 0 && tag.getInt("MaxRecipeTime") != 0) {
                    iProbeInfo.mcText(Component.translatable(FormattingUtil.getTranslatable("machines.top_info.recipe")).withStyle(FormattingUtil.hex(0x7ED355)).append(Component.literal(FormattingUtil.formatPercentNum(tag.getInt("MaxRecipeTime") - tag.getInt("RecipeTime"), tag.getInt("MaxRecipeTime"), false)).withStyle(ChatFormatting.WHITE)));
                }
                if (tag.getInt("Production") != 0 && tag.getInt("Fuel") != 0) {
                    SimpleDateFormat format = new SimpleDateFormat("mm'm' ss's'");
                    float fuel = tag.getInt("Fuel") / 20f * 1000 + (tag.getInt("Fuel") > 0 ? 1000 : 0);
                    iProbeInfo.mcText(Component.translatable(FormattingUtil.getTranslatable("machines.top_info.producing")).withStyle(FormattingUtil.hex(0x7ED355)).append(Component.literal(FormattingUtil.formatEnergy(tag.getInt("Production")) + "/t").withStyle(ChatFormatting.WHITE)));
                    iProbeInfo.mcText(Component.translatable(FormattingUtil.getTranslatable("machines.top_info.fuel")).withStyle(FormattingUtil.hex(0x55D38A)).append(Component.literal(format.format(fuel)).withStyle(ChatFormatting.WHITE)));

                }
            }

            if (level.getBlockEntity(iProbeHitData.getPos()) instanceof ThermalValveBlockEntity entity) {
                iProbeInfo.mcText(Component.translatable(FormattingUtil.getTranslatable("reactor.top_info.type")).withStyle(FormattingUtil.hex(0x7ED355)).append(Component.translatable(entity.getBlockState().getValue(ThermalValveBlock.TYPE).getSerializedName()).withStyle(ChatFormatting.WHITE)));
            }

            if (level.getBlockEntity(iProbeHitData.getPos()) instanceof ReactorValveBlockEntity entity) {
                iProbeInfo.mcText(Component.translatable(FormattingUtil.getTranslatable("reactor.top_info.type")).withStyle(FormattingUtil.hex(0x7ED355)).append(Component.translatable(entity.getBlockState().getValue(ReactorValveBlock.TYPE).getSerializedName()).withStyle(ChatFormatting.WHITE)));
            }

        }
    }

    private void valveTankRender(IProbeInfo iProbeInfo, FluidStack fluidIn, int fluidCapacityIn, int fluidAmountIn) {
        iProbeInfo.horizontal(iProbeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER)).mcText(fluidIn.getDisplayName().copy().append(Component.literal(": "))).tankSimple(fluidCapacityIn, fluidIn, iProbeInfo.defaultProgressStyle().suffix(Component.literal("mb " + FormattingUtil.formatPercentNum(fluidAmountIn, fluidCapacityIn, false))).borderColor(Color.rgb(65, 65, 65, 255)).backgroundColor(Color.rgb(0, 0, 0, 0)));
    }

}
