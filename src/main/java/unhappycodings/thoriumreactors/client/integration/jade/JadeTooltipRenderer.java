package unhappycodings.thoriumreactors.client.integration.jade;

import mcjty.theoneprobe.api.Color;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import snownee.jade.api.*;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.fluid.JadeFluidObject;
import snownee.jade.api.ui.BoxStyle;
import snownee.jade.api.ui.IElement;
import snownee.jade.api.ui.IElementHelper;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.common.block.machine.MachineGeneratorBlock;
import unhappycodings.thoriumreactors.common.block.reactor.ReactorValveBlock;
import unhappycodings.thoriumreactors.common.block.tank.FluidTankBlock;
import unhappycodings.thoriumreactors.common.block.thermal.ThermalValveBlock;
import unhappycodings.thoriumreactors.common.blockentity.ModFluidTank;
import unhappycodings.thoriumreactors.common.blockentity.WaterSourceBlockEntity;
import unhappycodings.thoriumreactors.common.blockentity.base.MachineContainerBlockEntity;
import unhappycodings.thoriumreactors.common.blockentity.machine.MachineBlastFurnaceBlockEntity;
import unhappycodings.thoriumreactors.common.blockentity.reactor.ReactorControllerBlockEntity;
import unhappycodings.thoriumreactors.common.blockentity.reactor.ReactorValveBlockEntity;
import unhappycodings.thoriumreactors.common.blockentity.tank.EnergyTankBlockEntity;
import unhappycodings.thoriumreactors.common.blockentity.tank.FluidTankBlockEntity;
import unhappycodings.thoriumreactors.common.blockentity.thermal.ThermalValveBlockEntity;
import unhappycodings.thoriumreactors.common.blockentity.turbine.TurbineControllerBlockEntity;
import unhappycodings.thoriumreactors.common.blockentity.turbine.TurbinePowerPortBlockEntity;
import unhappycodings.thoriumreactors.common.blockentity.turbine.TurbineValveBlockEntity;
import unhappycodings.thoriumreactors.common.enums.ValveTypeEnum;
import unhappycodings.thoriumreactors.common.registration.ModBlocks;
import unhappycodings.thoriumreactors.common.registration.ModFluids;
import unhappycodings.thoriumreactors.common.util.FormattingUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public enum JadeTooltipRenderer implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
    INSTANCE;
    public static final int COLOR_A = Color.rgb(66, 150, 0, 255);
    public static final int COLOR_B = Color.rgb(67, 204, 0, 255);

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        BlockEntity blockEntity = accessor.getBlockEntity();
        if (blockEntity != null && accessor.getBlockState().getBlock().getDescriptionId().contains(ThoriumReactors.MOD_ID)) {
            CompoundTag serverData = accessor.getServerData();
            IElementHelper elements = tooltip.getElementHelper();

            if (serverData.contains("Energy"))
                tooltip.add(energyBar(elements, serverData.getLong("Energy"), serverData.getLong("EnergyCapacity"), Component.translatable(FormattingUtil.getTranslatable("machines.top_info.energy")).withStyle(ChatFormatting.WHITE)));

            String[] fluids = {"FluidIn", "FluidOut"};
            for (String fluidName : fluids) {
                if (serverData.contains(fluidName)) {
                    FluidStack fluid = FluidStack.loadFluidStackFromNBT(serverData.getCompound(fluidName));
                    tooltip.add(fluidBar(elements, fluid, fluid.getAmount(), serverData.getInt(fluidName + "Capacity")));
                }
            }

            if (blockEntity instanceof TurbineControllerBlockEntity entity) {
                if (!serverData.getBoolean("TurbineActivated")) {
                    tooltip.add(Component.translatable(FormattingUtil.getTranslatable("turbine.top_info.turbine")).append(" ").append(Component.translatable(FormattingUtil.getTranslatable("reactor.top_info.inactive"))).withStyle(FormattingUtil.hex(0x9F0006)));
                    return;
                } else {
                    tooltip.add(Component.translatable(FormattingUtil.getTranslatable("turbine.top_info.turbine")).append(" ").append(Component.translatable(FormattingUtil.getTranslatable("reactor.top_info.active"))).withStyle(FormattingUtil.hex(0x00A90B)));
                }

                tooltip.add(energyBar(elements, serverData.getLong("TurbineEnergy"), serverData.getLong("TurbineEnergyCapacity"), Component.translatable(FormattingUtil.getTranslatable("machines.top_info.energy")).withStyle(ChatFormatting.WHITE)));

                FluidStack fluid = FluidStack.loadFluidStackFromNBT(serverData.getCompound("TurbineFluid"));
                tooltip.add(fluidBar(elements, fluid, fluid.getAmount(), 10000));

                tooltip.add(Component.translatable(FormattingUtil.getTranslatable("turbine.top_info.producing")).withStyle(FormattingUtil.hex(0x7ED355)).append(Component.literal(entity.isCoilsEngaged() ? FormattingUtil.formatEnergy(entity.getTurbineGeneration()) : "0 FE" + "/t").withStyle(ChatFormatting.WHITE)));
                tooltip.add(Component.translatable(FormattingUtil.getTranslatable("turbine.top_info.speed")).withStyle(FormattingUtil.hex(0x55D38A)).append(Component.literal(Math.floor(entity.getRpm() * 100) / 100 + " ").append(Component.translatable(FormattingUtil.getTranslatable("turbine.top_info.rpm"))).withStyle(ChatFormatting.WHITE)));
                tooltip.add(Component.translatable(FormattingUtil.getTranslatable("turbine.top_info.flowrate")).withStyle(FormattingUtil.hex(0x0ACECE)).append(Component.literal(entity.getCurrentFlowrate() + " mB/t").withStyle(ChatFormatting.WHITE)));
                tooltip.add(Component.translatable(FormattingUtil.getTranslatable("turbine.top_info.best_performing_at")).withStyle(FormattingUtil.hex(0x55D38A)));
            }

            if (blockEntity instanceof ReactorControllerBlockEntity entity) {
                if (!serverData.getBoolean("ReactorActivated")) {
                    tooltip.add(Component.translatable(FormattingUtil.getTranslatable("reactor.top_info.reactor")).append(" ").append(Component.translatable(FormattingUtil.getTranslatable("reactor.top_info.inactive"))).withStyle(FormattingUtil.hex(0x9F0006)));
                    return;
                } else {
                    tooltip.add(Component.translatable(FormattingUtil.getTranslatable("reactor.top_info.reactor")).append(" ").append(Component.translatable(FormattingUtil.getTranslatable("reactor.top_info.active"))).withStyle(FormattingUtil.hex(0x00A90B)));
                }
                for (int i = 0; i < 4; i++) {
                    if (accessor.getLevel().getBlockEntity(BlockEntity.getPosFromTag(serverData.getCompound("ValvePos-" + i))) instanceof ReactorValveBlockEntity valveBlockEntity) {
                        if (valveBlockEntity.getBlockState().getValue(ReactorValveBlock.TYPE) == ValveTypeEnum.FLUID_INPUT || valveBlockEntity.getBlockState().getValue(ReactorValveBlock.TYPE) == ValveTypeEnum.FLUID_OUTPUT) {
                            FluidStack fluid = FluidStack.loadFluidStackFromNBT(serverData.getCompound("ValveFluid-" + i));
                            tooltip.add(fluidBar(elements, fluid, fluid.getAmount(), 1000));
                        }
                    }
                }
                int fuelValue = 0, controlValue = 0;
                for (int i = 0; i < entity.getFuelRodStatus().length; i++) fuelValue += entity.getFuelRodStatus()[i];
                for (int i = 0; i < entity.getControlRodStatus().length; i++)
                    controlValue += entity.getControlRodStatus()[i];

                tooltip.add(Component.translatable(FormattingUtil.getTranslatable("reactor.top_info.rod_insert")).withStyle(FormattingUtil.hex(0x7ED355)).append(Component.literal((int) ((controlValue / 6400f) * 100) + "%").withStyle(ChatFormatting.WHITE)));
                tooltip.add(Component.translatable(FormattingUtil.getTranslatable("reactor.top_info.uranium_fuel")).withStyle(FormattingUtil.hex(0x55D38A)).append(Component.literal((int) ((fuelValue / 8100f) * 100) + "%").withStyle(ChatFormatting.WHITE)));
                tooltip.add(Component.translatable(FormattingUtil.getTranslatable("reactor.top_info.reactor_load")).withStyle(FormattingUtil.hex(0x0ACECE)).append(Component.literal((int) (((entity.getReactorCurrentTemperature() - 22) / 949) * 100) + "%").withStyle(ChatFormatting.WHITE)));
                tooltip.add(Component.translatable(FormattingUtil.getTranslatable("reactor.top_info.status")).withStyle(FormattingUtil.hex(0x0ACECE)).append(Component.literal(Math.floor(entity.getReactorStatus() * 10) / 10 + "%").withStyle(ChatFormatting.WHITE)));
            }

            if (blockEntity instanceof ReactorValveBlockEntity entity)
                tooltip.add(Component.translatable(FormattingUtil.getTranslatable("reactor.top_info.type")).withStyle(FormattingUtil.hex(0x7ED355)).append(Component.translatable(entity.getBlockState().getValue(ReactorValveBlock.TYPE).getSerializedName()).withStyle(ChatFormatting.WHITE)));
            if (blockEntity instanceof ThermalValveBlockEntity entity)
                tooltip.add(Component.translatable(FormattingUtil.getTranslatable("reactor.top_info.type")).withStyle(FormattingUtil.hex(0x7ED355)).append(Component.translatable(entity.getBlockState().getValue(ThermalValveBlock.TYPE).getSerializedName()).withStyle(ChatFormatting.WHITE)));

            if (serverData.contains("Production")) {
                tooltip.add(Component.translatable(FormattingUtil.getTranslatable("machines.top_info.producing")).withStyle(FormattingUtil.hex(0x7ED355)).append(Component.literal(FormattingUtil.formatEnergy(serverData.getInt("Production")) + "/t").withStyle(ChatFormatting.WHITE)));
            }

            if (serverData.contains("Fuel") && blockEntity instanceof MachineBlastFurnaceBlockEntity) {
                SimpleDateFormat format = new SimpleDateFormat("mm'm' ss's'");
                float fuel = serverData.getInt("Fuel") / 20f * 1000 + (serverData.getInt("Fuel") > 0 ? 1000 : 0);
                tooltip.add(Component.translatable(FormattingUtil.getTranslatable("machines.top_info.fuel")).withStyle(FormattingUtil.hex(0x55D38A)).append(Component.literal(format.format(fuel)).withStyle(ChatFormatting.WHITE)));
            }

            if (serverData.contains("RecipeTime"))
                tooltip.add(Component.translatable(FormattingUtil.getTranslatable("machines.top_info.recipe")).withStyle(FormattingUtil.hex(0x7ED355)).append(Component.literal(FormattingUtil.formatPercentNum(serverData.getInt("MaxRecipeTime") - serverData.getInt("RecipeTime"), serverData.getInt("MaxRecipeTime"), false)).withStyle(ChatFormatting.WHITE)));

        }
    }

    private IElement energyBar(IElementHelper helper, long min, long max, Component prefix) {
        BoxStyle style = new BoxStyle();
        style.borderColor = Color.rgb(65, 65, 65, 255);
        style.borderWidth = 0.75f;
        return helper.progress((float) min / (float) max, prefix.copy().append(Component.literal(min == Integer.MAX_VALUE ? "Infinite" : FormattingUtil.formatNum(min))), helper.progressStyle().color(COLOR_A, COLOR_B).textColor(0xFFFFFF), style, true);
    }

    private IElement fluidBar(IElementHelper helper, FluidStack fluid, int amount, int capacity) {
        BoxStyle style = new BoxStyle();
        style.borderColor = Color.rgb(65, 65, 65, 255);
        style.borderWidth = 0.75f;
        return helper.progress((float) amount / (float) capacity, fluid.getDisplayName().copy().append(Component.literal(": " + (amount == Integer.MAX_VALUE ? "Infinite" : (amount + " mB")))), helper.progressStyle().overlay(helper.fluid(JadeFluidObject.of(fluid.getFluid()))), style, true);
    }

    @Override
    public void appendServerData(CompoundTag data, BlockAccessor accessor) {
        BlockEntity blockEntity = accessor.getBlockEntity();
        if (blockEntity instanceof MachineContainerBlockEntity entity) {
            if (entity.getUpdateTag().contains("Energy")) {
                data.putLong("Energy", entity.getEnergy());
                data.putLong("EnergyCapacity", entity.getCapacity());
            }
        }

        if (blockEntity instanceof TurbinePowerPortBlockEntity entity) {
            if (entity.getUpdateTag().contains("Energy")) {
                data.putLong("Energy", entity.getEnergy());
                data.putLong("EnergyCapacity", entity.getCapacity());
            }
        }

        if (blockEntity instanceof EnergyTankBlockEntity entity) {
            if (entity.getUpdateTag().contains("Energy")) {
                data.putLong("Energy", entity.getEnergy());
                data.putLong("EnergyCapacity", entity.getCapacity());
            }
        }

        for (Direction value : Direction.values()) {
            blockEntity.getCapability(ForgeCapabilities.FLUID_HANDLER, value).ifPresent(storage -> {
                String[] fluids = {"FluidIn", "FluidOut"};
                for (String fluid : fluids) {
                    if (blockEntity.getUpdateTag().contains(fluid)) {
                        data.put(fluid, blockEntity.getUpdateTag().get(fluid));
                        data.putInt(fluid + "Capacity", storage.getTankCapacity(0));
                    }
                }
            });
        }

        if (blockEntity instanceof ReactorControllerBlockEntity entity) {
            data.putBoolean("ReactorActivated", entity.isReactorActive());
            for (int i = 0; i < entity.valvePos.size(); i++)
                if (accessor.getLevel().getBlockEntity(entity.valvePos.get(i)) instanceof ReactorValveBlockEntity valveBlockEntity) {
                    if (valveBlockEntity.getBlockState().getValue(ReactorValveBlock.TYPE) == ValveTypeEnum.FLUID_INPUT || valveBlockEntity.getBlockState().getValue(ReactorValveBlock.TYPE) == ValveTypeEnum.FLUID_OUTPUT) {
                        data.put("ValveFluid-" + i, valveBlockEntity.getUpdateTag().get("FluidIn"));
                        data.put("ValvePos-" + i, parsePosToTag(valveBlockEntity.getBlockPos()));
                    }
                }
        }

        if (blockEntity instanceof TurbineControllerBlockEntity entity) {
            data.putBoolean("TurbineActivated", entity.isActivated());
            if (entity.getLevel().getBlockEntity(entity.getValvePos()) instanceof TurbineValveBlockEntity valveBlockEntity) {
                data.put("TurbineFluid", valveBlockEntity.getUpdateTag().get("FluidIn"));
            }
            if (entity.getLevel().getBlockEntity(entity.getPowerPortPos()) instanceof TurbinePowerPortBlockEntity powerPortBlockEntity) {
                data.putInt("TurbineEnergy", powerPortBlockEntity.getEnergy());
                data.putInt("TurbineEnergyCapacity", powerPortBlockEntity.getCapacity());
            }
        }

        CompoundTag tag = blockEntity.getUpdateTag();
        if (tag.contains("RecipeTime")) {
            data.putInt("RecipeTime", tag.getInt("RecipeTime"));
            data.putInt("MaxRecipeTime", tag.getInt("MaxRecipeTime"));
        }

        if (tag.contains("Production")) {
            data.putInt("Production", tag.getInt("Production"));
        }

        if (tag.contains("Fuel") && blockEntity.getBlockState().is(ModBlocks.GENERATOR_BLOCK.get())) {
            data.putInt("Fuel", tag.getInt("Fuel"));
            data.putInt("MaxFuel", tag.getInt("MaxFuel"));
        }
    }

    public CompoundTag parsePosToTag(BlockPos pos) {
        CompoundTag position = new CompoundTag();
        position.putInt("x", pos.getX());
        position.putInt("y", pos.getY());
        position.putInt("z", pos.getZ());
        return position;
    }

    @Override
    public ResourceLocation getUid() {
        return new ResourceLocation(ThoriumReactors.MOD_ID, "machines");
    }

    @Override
    public boolean enabledByDefault() {
        return true;
    }

}