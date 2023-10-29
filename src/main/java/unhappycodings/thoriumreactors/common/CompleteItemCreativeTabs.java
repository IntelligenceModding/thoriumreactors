package unhappycodings.thoriumreactors.common;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
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
import unhappycodings.thoriumreactors.common.registration.ModBlocks;
import unhappycodings.thoriumreactors.common.registration.ModItems;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class CompleteItemCreativeTabs {

    public static void populateCreativeTabBuilder(CreativeModeTab.Builder builder) {
        builder.displayItems((features, output) -> {
            for (ItemStack item : getDisplayItems()) {
                output.accept(item);
            }
        });
        builder.icon(() -> ModItems.THORIUM.get().asItem().getDefaultInstance());
        builder.withTabsBefore(CreativeModeTabs.SPAWN_EGGS);
        builder.title(Component.translatable("itemGroup.thoriumreactors"));
    }

    public static Collection<ItemStack> getDisplayItems() {
        NonNullList<ItemStack> items = NonNullList.create();
        int index = 0;

        // Machines
        ArrayList<Block> machines = new ArrayList<>();
        Collections.addAll(machines, ModBlocks.GENERATOR_BLOCK.get(), ModBlocks.FLUID_EVAPORATION_BLOCK.get(), ModBlocks.ELECTROLYTIC_SALT_SEPARATOR_BLOCK.get(), ModBlocks.SALT_MELTER_BLOCK.get(), ModBlocks.CONCENTRATOR_BLOCK.get(), ModBlocks.DECOMPOSER_BLOCK.get(), ModBlocks.URANIUM_OXIDIZER_BLOCK.get(), ModBlocks.FLUID_CENTRIFUGE_BLOCK.get(), ModBlocks.CRYSTALLIZER_BLOCK.get(), ModBlocks.BLAST_FURNACE_BLOCK.get(), ModBlocks.FLUID_ENRICHER_BLOCK.get());
        for (Block i : machines) {
            items.add(index, new ItemStack(i));
            index++;
        }

        // Fluid Tanks
        ArrayList<Block> tanksOne = new ArrayList<>();
        Collections.addAll(tanksOne, ModBlocks.SIMPLE_ENERGY_TANK.get(), ModBlocks.GENERIC_ENERGY_TANK.get(), ModBlocks.PROGRESSIVE_ENERGY_TANK.get(), ModBlocks.CREATIVE_ENERGY_TANK.get());
        for (Block i : tanksOne) {
            items.add(index, new ItemStack(i));
            index++;
        }
        ItemStack blockStack = ModBlocks.CREATIVE_ENERGY_TANK.get().asItem().getDefaultInstance();
        blockStack.getOrCreateTag().put("BlockEntityTag", writeEnergyToNbt(Integer.MAX_VALUE));
        items.add(index, blockStack);
        index++;
        ArrayList<Block> tanksTwo = new ArrayList<>();
        Collections.addAll(tanksTwo, ModBlocks.SIMPLE_FLUID_TANK.get(), ModBlocks.GENERIC_FLUID_TANK.get(), ModBlocks.PROGRESSIVE_FLUID_TANK.get());
        for (Block i : tanksTwo) {
            items.add(index, new ItemStack(i));
            index++;
        }
        for (Fluid fluid : getKnownFluids()) {
            if (fluid instanceof ForgeFlowingFluid.Source || fluid instanceof LavaFluid.Source || fluid instanceof WaterFluid.Source) {
                blockStack = ModBlocks.CREATIVE_FLUID_TANK.get().asItem().getDefaultInstance();
                FluidStack stack = new FluidStack(fluid, Integer.MAX_VALUE);

                blockStack.getOrCreateTag().put("BlockEntityTag", writeFluidToNbt(stack));
                if (!items.contains(blockStack)) {
                    items.add(index, blockStack);
                    index++;
                }
            }
        }

        // Buckets
        ArrayList<Item> buckets = new ArrayList<>();
        Collections.addAll(buckets, ModItems.MOLTEN_SALT_BUCKET.get(), ModItems.DEPLETED_MOLTEN_SALT_BUCKET.get(), ModItems.HEATED_MOLTEN_SALT_BUCKET.get(), ModItems.URANIUM_HEXAFLUORITE_BUCKET.get(), ModItems.ENRICHED_URANIUM_HEXAFLUORITE_BUCKET.get(), ModItems.HYDROFLUORITE_BUCKET.get(), ModItems.STEAM_BUCKET.get());
        for (Item i : buckets) {
            items.add(index, new ItemStack(i));
            index++;
        }

        // Blocks
        ArrayList<Block> multistuctureBlocks = new ArrayList<>();
        Collections.addAll(multistuctureBlocks, ModBlocks.REACTOR_CASING.get(), ModBlocks.REACTOR_CONTROLLER_BLOCK.get(), ModBlocks.REACTOR_VALVE.get(), ModBlocks.REACTOR_ROD_CONTROLLER.get(), ModBlocks.REACTOR_CORE.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CONTROLLER_BLOCK.get(), ModBlocks.TURBINE_POWER_PORT.get(), ModBlocks.TURBINE_VALVE.get(), ModBlocks.TURBINE_ROTATION_MOUNT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_CONTROLLER.get(), ModBlocks.THERMAL_VALVE.get(), ModBlocks.THERMAL_HEAT_SINK.get(), ModBlocks.REACTOR_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.REACTOR_GRAPHITE_MODERATOR.get(), ModBlocks.TURBINE_ROTOR.get(), ModBlocks.THORIUM_CRAFTING_TABLE.get(), ModBlocks.BLASTED_IRON_CHEST_BLOCK.get(), ModBlocks.STEEL_CHEST_BLOCK.get(), ModBlocks.THORIUM_CHEST_BLOCK.get());
        for (Block i : multistuctureBlocks) {
            items.add(index, new ItemStack(i));
            index++;
        }

        // Items
        ArrayList<Item> allItems = new ArrayList<>();
        Collections.addAll(allItems, ModItems.CONFIGURATOR.get(), ModItems.MODULE_EMPTY.get(), ModItems.MODULE_IO.get(), ModItems.MODULE_ENERGY.get(), ModItems.MODULE_STORAGE.get(), ModItems.MODULE_TANK.get(), ModItems.MODULE_SENSOR.get(), ModItems.MODULE_PROCESSING.get(), ModItems.GRAPHITE_TUBE.get(), ModItems.TURBINE_BLADE.get(), ModItems.REDSTONE_PROCESSOR.get());
        for (Item i : allItems) {
            items.add(index, new ItemStack(i));
            index++;
        }

        // Resource Items
        ArrayList<Item> resourceItems = new ArrayList<>();
        Collections.addAll(resourceItems, ModItems.SODIUM.get(), ModItems.POTASSIUM.get(), ModItems.URAN_THREE_CHLORIDE.get(), ModItems.YELLOW_CAKE.get(), ModItems.THORIUM.get(), ModItems.RAW_URANIUM.get(), ModItems.ENRICHED_URANIUM.get(), ModItems.DEPLETED_URANIUM.get(), ModItems.FLUORITE.get(), ModItems.GRAPHITE_CRYSTAL.get(), ModItems.CHROMIUM_NUGGET.get(), ModItems.GRAPHITE_NUGGET.get(), ModItems.STEEL_NUGGET.get(), ModItems.BLASTED_IRON_NUGGET.get(), ModItems.TITANIUM_NUGGET.get(), ModItems.MOLYBDENUM_NUGGET.get(), ModItems.COBALT_NUGGET.get(), ModItems.FLUORITE_NUGGET.get(), ModItems.NICKEL_NUGGET.get(), ModItems.URANIUM_NUGGET.get(), ModItems.ALUMINUM_NUGGET.get(), ModItems.MANGANESE_NUGGET.get(), ModItems.NIOB_NUGGET.get(), ModItems.CHROMIUM_INGOT.get(), ModItems.GRAPHITE_INGOT.get(), ModItems.STEEL_INGOT.get(), ModItems.BLASTED_IRON_INGOT.get(), ModItems.TITANIUM_INGOT.get(), ModItems.MOLYBDENUM_INGOT.get(), ModItems.COBALT_INGOT.get(), ModItems.FLUORITE_INGOT.get(), ModItems.NICKEL_INGOT.get(), ModItems.URANIUM_INGOT.get(), ModItems.ALUMINUM_INGOT.get(), ModItems.MANGANESE_INGOT.get(), ModItems.NIOB_INGOT.get());
        for (Item i : resourceItems) {
            items.add(index, new ItemStack(i));
            index++;
        }

        // Resource Blocks
        ArrayList<Block> resourceBlocks = new ArrayList<>();
        Collections.addAll(resourceBlocks, ModBlocks.CHROMIUM_BLOCK.get(), ModBlocks.GRAPHITE_BLOCK.get(), ModBlocks.STEEL_BLOCK.get(), ModBlocks.BLASTED_IRON_BLOCK.get(), ModBlocks.TITANIUM_BLOCK.get(), ModBlocks.MOLYBDENUM_BLOCK.get(), ModBlocks.COBALT_BLOCK.get(), ModBlocks.FLUORITE_BLOCK.get(), ModBlocks.NICKEL_BLOCK.get(), ModBlocks.URANIUM_BLOCK.get(), ModBlocks.ALUMINUM_BLOCK.get(), ModBlocks.MANGANESE_BLOCK.get(), ModBlocks.NIOB_BLOCK.get(), ModBlocks.DEEPSLATE_TITANIC_IRON_ORE.get(), ModBlocks.TITANIC_IRON_ORE.get(), ModBlocks.DEEPSLATE_CHROMITE_ORE.get(), ModBlocks.CHROMITE_ORE.get(), ModBlocks.DEEPSLATE_GRAPHITE_ORE.get(), ModBlocks.GRAPHITE_ORE.get(), ModBlocks.DEEPSLATE_MOLYBDENUM_ORE.get(), ModBlocks.MOLYBDENUM_ORE.get(), ModBlocks.DEEPSLATE_FLUORITE_ORE.get(), ModBlocks.FLUORITE_ORE.get(), ModBlocks.DEEPSLATE_NICKEL_ORE.get(), ModBlocks.NICKEL_ORE.get(), ModBlocks.DEEPSLATE_URANIUM_ORE.get(), ModBlocks.URANIUM_ORE.get(), ModBlocks.DEEPSLATE_PYROCHLOR_ORE.get(), ModBlocks.PYROCHLOR_ORE.get(), ModBlocks.DEEPSLATE_MANGANESE_ORE.get(), ModBlocks.MANGANESE_ORE.get(), ModBlocks.DEEPSLATE_BAUXITE_ORE.get(), ModBlocks.BAUXITE_ORE.get(), ModBlocks.BLASTED_STONE.get());
        for (Block i : resourceBlocks) {
            items.add(index, new ItemStack(i));
            index++;
        }

        // Building Blocks
        ArrayList<Block> buildingBlocks = new ArrayList<>();
        Collections.addAll(buildingBlocks, ModBlocks.INDUSTRAL_BLOCK.get(), ModBlocks.INDUSTRAL_BLOCK_BIG_TILE.get(), ModBlocks.INDUSTRAL_BLOCK_BRICK.get(), ModBlocks.INDUSTRAL_BLOCK_PAVING.get(), ModBlocks.INDUSTRAL_BLOCK_SMOOTH.get(), ModBlocks.BLACK_INDUSTRAL_BLOCK.get(), ModBlocks.BLACK_INDUSTRAL_BLOCK_BIG_TILE.get(), ModBlocks.BLACK_INDUSTRAL_BLOCK_BRICK.get(), ModBlocks.BLACK_INDUSTRAL_BLOCK_PAVING.get(), ModBlocks.BLACK_INDUSTRAL_BLOCK_SMOOTH.get(), ModBlocks.WHITE_INDUSTRAL_BLOCK.get(), ModBlocks.WHITE_INDUSTRAL_BLOCK_BIG_TILE.get(), ModBlocks.WHITE_INDUSTRAL_BLOCK_BRICK.get(), ModBlocks.WHITE_INDUSTRAL_BLOCK_PAVING.get(), ModBlocks.WHITE_INDUSTRAL_BLOCK_SMOOTH.get(), ModBlocks.FACTORY_BLOCK.get(), ModBlocks.INVERTED_FACTORY_BLOCK.get(), ModBlocks.BLACK_FACTORY_BLOCK.get(), ModBlocks.BLACK_INVERTED_FACTORY_BLOCK.get(), ModBlocks.WARNING_BLOCK_LINED_BLACK_YELLOW_LEFT.get(), ModBlocks.WARNING_BLOCK_LINED_BLACK_YELLOW_RIGHT.get(), ModBlocks.WARNING_BLOCK_LINED_WHITE_ORANGE_LEFT.get(), ModBlocks.WARNING_BLOCK_LINED_WHITE_ORANGE_RIGHT.get(), ModBlocks.WARNING_BLOCK_LINED_WHITE_BLACK_LEFT.get(), ModBlocks.WARNING_BLOCK_LINED_WHITE_BLACK_RIGHT.get(), ModBlocks.INDUSTRAL_BLOCK_FLOOR.get(), ModBlocks.BLACK_INDUSTRAL_BLOCK_FLOOR.get(), ModBlocks.GRATE_FLOOR_BLOCK.get());
        for (Block i : buildingBlocks) {
            items.add(index, new ItemStack(i));
            index++;
        }
        return items;
    }

    public static CompoundTag writeFluidToNbt(FluidStack fluidStack) {
        CompoundTag dataTag = new CompoundTag();
        CompoundTag fluidTag = new CompoundTag();
        dataTag.putString("FluidName", ForgeRegistries.FLUIDS.getKey(fluidStack.getFluid()).toString());
        dataTag.putInt("Amount", fluidStack.getAmount());

        fluidTag.put("Fluid", dataTag);
        return fluidTag;
    }

    public static CompoundTag writeEnergyToNbt(int energy) {
        CompoundTag fluidTag = new CompoundTag();
        fluidTag.putInt("Energy", energy);
        return fluidTag;
    }

    @NotNull
    protected static Iterable<Fluid> getKnownFluids() {
        return ForgeRegistries.FLUIDS.getEntries().stream().map(Map.Entry::getValue)::iterator;
    }

}
