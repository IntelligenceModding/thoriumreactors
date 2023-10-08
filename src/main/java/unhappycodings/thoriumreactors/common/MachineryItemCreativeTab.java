package unhappycodings.thoriumreactors.common;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.LavaFluid;
import net.minecraft.world.level.material.WaterFluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.common.registration.ModBlocks;
import unhappycodings.thoriumreactors.common.registration.ModItems;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class MachineryItemCreativeTab extends CreativeModeTab {

    public MachineryItemCreativeTab() {
        super(ThoriumReactors.MOD_ID + ".machinery");
    }

    @Override
    public void fillItemList(@NotNull NonNullList<ItemStack> items) {
        int index = 0;
        ArrayList<Item> itemList = new ArrayList<>();
        Collections.addAll(itemList, ModItems.CONFIGURATOR.get(), ModItems.MODULE_EMPTY.get(), ModItems.MODULE_IO.get(), ModItems.MODULE_ENERGY.get(),
                ModItems.MODULE_STORAGE.get(), ModItems.MODULE_TANK.get(), ModItems.MODULE_SENSOR.get(), ModItems.MODULE_PROCESSING.get(),
                ModItems.REDSTONE_PROCESSOR.get(), ModItems.GRAPHITE_TUBE.get(), ModItems.TURBINE_BLADE.get());

        ArrayList<Block> blockList = new ArrayList<>();
        Collections.addAll(blockList, ModBlocks.GENERATOR_BLOCK.get(), ModBlocks.FLUID_EVAPORATION_BLOCK.get(), ModBlocks.ELECTROLYTIC_SALT_SEPARATOR_BLOCK.get(),
                ModBlocks.SALT_MELTER_BLOCK.get(), ModBlocks.CONCENTRATOR_BLOCK.get(), ModBlocks.DECOMPOSER_BLOCK.get(),
                ModBlocks.URANIUM_OXIDIZER_BLOCK.get(), ModBlocks.FLUID_CENTRIFUGE_BLOCK.get(), ModBlocks.CRYSTALLIZER_BLOCK.get(),
                ModBlocks.BLAST_FURNACE_BLOCK.get(), ModBlocks.REACTOR_CASING.get(), ModBlocks.REACTOR_CONTROLLER_BLOCK.get(), ModBlocks.REACTOR_VALVE.get(), ModBlocks.REACTOR_ROD_CONTROLLER.get(),
                ModBlocks.REACTOR_CORE.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CONTROLLER_BLOCK.get(), ModBlocks.TURBINE_POWER_PORT.get(), ModBlocks.TURBINE_VALVE.get(),
                ModBlocks.TURBINE_ROTATION_MOUNT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(),
                ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_CONTROLLER.get(), ModBlocks.THERMAL_VALVE.get(), ModBlocks.THERMAL_HEAT_SINK.get(),
                ModBlocks.REACTOR_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.REACTOR_GRAPHITE_MODERATOR.get(), ModBlocks.TURBINE_ROTOR.get(), ModBlocks.THORIUM_CRAFTING_TABLE.get(),
                ModBlocks.BLASTED_IRON_CHEST_BLOCK.get(), ModBlocks.STEEL_CHEST_BLOCK.get(), ModBlocks.THORIUM_CHEST_BLOCK.get(),
                ModBlocks.SIMPLE_ENERGY_TANK.get(), ModBlocks.GENERIC_ENERGY_TANK.get(), ModBlocks.PROGRESSIVE_ENERGY_TANK.get(),
                ModBlocks.SIMPLE_FLUID_TANK.get(), ModBlocks.GENERIC_FLUID_TANK.get(), ModBlocks.PROGRESSIVE_FLUID_TANK.get());

        for (Block i : blockList) {
            items.add(index, new ItemStack(i));
            index++;
        }

        for (Fluid fluid : getKnownFluids()) {
            if (fluid instanceof ForgeFlowingFluid.Source || fluid instanceof LavaFluid.Source || fluid instanceof WaterFluid.Source) {
                ItemStack blockStack = ModBlocks.CREATIVE_FLUID_TANK.get().asItem().getDefaultInstance();

                FluidStack stack = new FluidStack(fluid, Integer.MAX_VALUE);
                blockStack.getOrCreateTag().put("BlockEntityTag", writeToNBT(stack));

                if (!items.contains(blockStack)) {
                    items.add(index, blockStack);
                    index++;
                }
            }
        }

        for (Item i : itemList) {
            items.add(index, new ItemStack(i));
            index++;
        }
    }

    @Override
    public @NotNull ItemStack makeIcon() {
        return new ItemStack(ModBlocks.GENERATOR_BLOCK.get());
    }


    public CompoundTag writeToNBT(FluidStack fluidStack) {
        CompoundTag dataTag = new CompoundTag();
        CompoundTag fluidTag = new CompoundTag();
        dataTag.putString("FluidName", ForgeRegistries.FLUIDS.getKey(fluidStack.getFluid()).toString());
        dataTag.putInt("Amount", fluidStack.getAmount());

        fluidTag.put("Fluid", dataTag);
        return fluidTag;
    }

    @NotNull
    protected Iterable<Fluid> getKnownFluids() {
        return ForgeRegistries.FLUIDS.getEntries().stream().map(Map.Entry::getValue)::iterator;
    }

}
