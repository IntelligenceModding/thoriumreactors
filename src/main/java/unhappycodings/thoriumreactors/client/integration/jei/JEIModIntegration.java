package unhappycodings.thoriumreactors.client.integration.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.LavaFluid;
import net.minecraft.world.level.material.WaterFluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.client.config.ClientConfig;
import unhappycodings.thoriumreactors.common.container.ThoriumCraftingTableContainer;
import unhappycodings.thoriumreactors.common.container.ThoriumCraftingTableScreen;
import unhappycodings.thoriumreactors.common.container.machine.*;
import unhappycodings.thoriumreactors.common.container.reactor.ReactorControllerScreen;
import unhappycodings.thoriumreactors.common.recipe.*;
import unhappycodings.thoriumreactors.common.registration.ModBlocks;
import unhappycodings.thoriumreactors.common.registration.ModContainerTypes;
import unhappycodings.thoriumreactors.common.registration.ModRecipes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@JeiPlugin
public class JEIModIntegration implements IModPlugin {
    public static final mezz.jei.api.recipe.RecipeType<ThoriumCraftingRecipe> THORIUM_RECIPE_TYPE = new RecipeType<>(ThoriumCraftingRecipeCategory.UID, ThoriumCraftingRecipe.class);
    public static final mezz.jei.api.recipe.RecipeType<ConcentratingRecipe> CONCENTRATING_RECIPE_TYPE = new RecipeType<>(ConcentratingRecipeCategory.UID, ConcentratingRecipe.class);
    public static final mezz.jei.api.recipe.RecipeType<BlastingRecipe> BLASTING_RECIPE_TYPE = new RecipeType<>(BlastingRecipeCategory.UID, BlastingRecipe.class);
    public static final mezz.jei.api.recipe.RecipeType<DecomposingRecipe> DECOMPOSING_RECIPE_TYPE = new RecipeType<>(DecomposingRecipeCategory.UID, DecomposingRecipe.class);
    public static final mezz.jei.api.recipe.RecipeType<OxidizingRecipe> OXIDIZING_RECIPE_TYPE = new RecipeType<>(OxidizingRecipeCategory.UID, OxidizingRecipe.class);
    public static final mezz.jei.api.recipe.RecipeType<FluidEnrichingRecipe> FLUID_ENRICHING_RECIPE_TYPE = new RecipeType<>(FluidEnrichingRecipeCategory.UID, FluidEnrichingRecipe.class);
    public static final mezz.jei.api.recipe.RecipeType<CentrifugingRecipe> CENTRIFUGING_RECIPE_TYPE = new RecipeType<>(CentrifugingRecipeCategory.UID, CentrifugingRecipe.class);
    public static final mezz.jei.api.recipe.RecipeType<CrystallizingRecipe> CRYSTALLIZING_RECIPE_TYPE = new RecipeType<>(CrystallizingRecipeCategory.UID, CrystallizingRecipe.class);
    public static final mezz.jei.api.recipe.RecipeType<ElectrolysingRecipe> ELECTROLYSING_RECIPE_TYPE = new RecipeType<>(ElectrolysingRecipeCategory.UID, ElectrolysingRecipe.class);
    public static final mezz.jei.api.recipe.RecipeType<SaltSmeltingRecipe> SMELTING_RECIPE_TYPE = new RecipeType<>(SmeltingRecipeCategory.UID, SaltSmeltingRecipe.class);
    public static final mezz.jei.api.recipe.RecipeType<EvaporatingRecipe> EVAPORATING_RECIPE_TYPE = new RecipeType<>(EvaporatingRecipeCategory.UID, EvaporatingRecipe.class);

    public static final int SLOT_DEFAULT = 0;
    public static final int SLOT_DECOMPOSER = 1;
    public static final int PLAYER_INVENTORY_COUNT = 36;

    @NotNull
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(ThoriumReactors.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(ModBlocks.THORIUM_CRAFTING_TABLE.get().asItem().getDefaultInstance(), THORIUM_RECIPE_TYPE);
        registration.addRecipeCatalyst(ModBlocks.CONCENTRATOR_BLOCK.get().asItem().getDefaultInstance(), CONCENTRATING_RECIPE_TYPE);
        registration.addRecipeCatalyst(ModBlocks.BLAST_FURNACE_BLOCK.get().asItem().getDefaultInstance(), BLASTING_RECIPE_TYPE);
        registration.addRecipeCatalyst(ModBlocks.DECOMPOSER_BLOCK.get().asItem().getDefaultInstance(), DECOMPOSING_RECIPE_TYPE);
        registration.addRecipeCatalyst(ModBlocks.URANIUM_OXIDIZER_BLOCK.get().asItem().getDefaultInstance(), OXIDIZING_RECIPE_TYPE);
        registration.addRecipeCatalyst(ModBlocks.FLUID_ENRICHER_BLOCK.get().asItem().getDefaultInstance(), FLUID_ENRICHING_RECIPE_TYPE);
        registration.addRecipeCatalyst(ModBlocks.FLUID_CENTRIFUGE_BLOCK.get().asItem().getDefaultInstance(), CENTRIFUGING_RECIPE_TYPE);
        registration.addRecipeCatalyst(ModBlocks.CRYSTALLIZER_BLOCK.get().asItem().getDefaultInstance(), CRYSTALLIZING_RECIPE_TYPE);
        registration.addRecipeCatalyst(ModBlocks.ELECTROLYTIC_SALT_SEPARATOR_BLOCK.get().asItem().getDefaultInstance(), ELECTROLYSING_RECIPE_TYPE);
        registration.addRecipeCatalyst(ModBlocks.SALT_MELTER_BLOCK.get().asItem().getDefaultInstance(), SMELTING_RECIPE_TYPE);
        registration.addRecipeCatalyst(ModBlocks.FLUID_EVAPORATION_BLOCK.get().asItem().getDefaultInstance(), EVAPORATING_RECIPE_TYPE);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new ThoriumCraftingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new ConcentratingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new BlastingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new DecomposingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new OxidizingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new FluidEnrichingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new CentrifugingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new CrystallizingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new ElectrolysingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new SmeltingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new EvaporatingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerGuiHandlers(@NotNull IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(ThoriumCraftingTableScreen.class, 107, 54, 26, 19, THORIUM_RECIPE_TYPE);
        registration.addRecipeClickArea(MachineConcentratorScreen.class, 66, 41, 43, 12, CONCENTRATING_RECIPE_TYPE);
        registration.addRecipeClickArea(MachineBlastFurnaceScreen.class, 70, 47, 34, 10, BLASTING_RECIPE_TYPE);
        registration.addRecipeClickArea(MachineDecomposerScreen.class, 68, 50, 38, 8, DECOMPOSING_RECIPE_TYPE);
        registration.addRecipeClickArea(MachineUraniumOxidizerScreen.class, 70, 52, 34, 9, OXIDIZING_RECIPE_TYPE);
        registration.addRecipeClickArea(MachineFluidEnricherScreen.class, 70, 52, 34, 9, FLUID_ENRICHING_RECIPE_TYPE);
        registration.addRecipeClickArea(MachineFluidCentrifugeScreen.class, 70, 58, 34, 9, CENTRIFUGING_RECIPE_TYPE);
        registration.addRecipeClickArea(MachineCrystallizerScreen.class, 64, 47, 41, 10, CRYSTALLIZING_RECIPE_TYPE);
        registration.addRecipeClickArea(MachineElectrolyticSaltSeparatorScreen.class, 67, 33, 41, 31, ELECTROLYSING_RECIPE_TYPE);
        registration.addRecipeClickArea(MachineSaltMelterScreen.class, 74, 31, 24, 35, SMELTING_RECIPE_TYPE);
        registration.addRecipeClickArea(MachineFluidEvaporatorScreen.class, 71, 32, 32, 22, EVAPORATING_RECIPE_TYPE);

        registration.addGenericGuiContainerHandler(ReactorControllerScreen.class, new Handler<>());

    }

    static class Handler<T extends ReactorControllerScreen> implements IGuiContainerHandler<T> {

        @NotNull
        @Override
        public List<Rect2i> getGuiExtraAreas(@NotNull ReactorControllerScreen screen) {
            int xPos = screen.width - (screen.getMainSizeX() / 2);
            int yPos = screen.height - (screen.getMainSizeY() / 2);

            List<Rect2i> collection = new ArrayList<>();
            collection.add(new Rect2i(xPos / 2, yPos / 2, screen.getMainSizeX() / 2, screen.getMainSizeY() / 2)); //mid
            if (ClientConfig.showLeftReactorScreenArea.get())
                collection.add(new Rect2i((xPos - screen.getLeftSideX() + 1) / 2, yPos / 2, screen.getLeftSideX() / 2, screen.getLeftSideY() / 2)); //left
            if (ClientConfig.showRightReactorScreenArea.get())
                collection.add(new Rect2i((xPos + screen.getMainSizeX() + 1) / 2, yPos / 2, screen.getRightSideX() / 2, screen.getRightSideY() / 2)); //right

            return collection;
        }
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        if (ClientConfig.showCreativeFluidTanksInJEI.get()) {
            registration.registerSubtypeInterpreter(ModBlocks.CREATIVE_FLUID_TANK.get().asItem(), (stack, uidContext) -> {
                CompoundTag data = stack.getOrCreateTag().getCompound("BlockEntityTag");
                FluidStack fluidStack = FluidStack.loadFluidStackFromNBT(data.getCompound("Fluid"));

                return addInterpretation("fluid", fluidStack.getFluid().toString());
            });
        }

        if (ClientConfig.showCreativeEnergyTanksInJEI.get()) {
            registration.registerSubtypeInterpreter(ModBlocks.CREATIVE_ENERGY_TANK.get().asItem(), (stack, uidContext) -> {
                CompoundTag data = stack.getOrCreateTag().getCompound("BlockEntityTag");
                int energy = data.getInt("Energy");

                return addInterpretation("energy", energy + "");
            });
        }

    }

    private static String addInterpretation(String nbtRepresentation, String component) {
        return nbtRepresentation.isEmpty() ? component : nbtRepresentation + ":" + component;
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager rm = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();
        registration.addRecipes(THORIUM_RECIPE_TYPE, rm.getAllRecipesFor(ModRecipes.THORIUM_RECIPE_TYPE.get()));
        registration.addRecipes(CONCENTRATING_RECIPE_TYPE, rm.getAllRecipesFor(ModRecipes.CONCENTRATING_RECIPE_TYPE.get()));
        registration.addRecipes(BLASTING_RECIPE_TYPE, rm.getAllRecipesFor(ModRecipes.BLASTING_RECIPE_TYPE.get()));
        registration.addRecipes(DECOMPOSING_RECIPE_TYPE, rm.getAllRecipesFor(ModRecipes.DECOMPOSING_RECIPE_TYPE.get()));
        registration.addRecipes(OXIDIZING_RECIPE_TYPE, rm.getAllRecipesFor(ModRecipes.OXIDIZING_RECIPE_TYPE.get()));
        registration.addRecipes(FLUID_ENRICHING_RECIPE_TYPE, rm.getAllRecipesFor(ModRecipes.FLUID_ENRICHING_RECIPE_TYPE.get()));
        registration.addRecipes(CENTRIFUGING_RECIPE_TYPE, rm.getAllRecipesFor(ModRecipes.CENTRIFUGING_RECIPE_TYPE.get()));
        registration.addRecipes(CRYSTALLIZING_RECIPE_TYPE, rm.getAllRecipesFor(ModRecipes.CRYSTALLIZING_RECIPE_TYPE.get()));
        registration.addRecipes(ELECTROLYSING_RECIPE_TYPE, rm.getAllRecipesFor(ModRecipes.ELECTROLYSING_RECIPE_TYPE.get()));
        registration.addRecipes(SMELTING_RECIPE_TYPE, rm.getAllRecipesFor(ModRecipes.SALT_SMELTING_RECIPE_TYPE.get()));
        registration.addRecipes(EVAPORATING_RECIPE_TYPE, rm.getAllRecipesFor(ModRecipes.EVAPORATING_RECIPE_TYPE.get()));

        List<ItemStack> blockList = new ArrayList<>();
        if (ClientConfig.showCreativeFluidTanksInJEI.get()) {
            for (Fluid fluid : getKnownFluids()) {
                if (fluid instanceof ForgeFlowingFluid.Source || fluid instanceof LavaFluid.Source || fluid instanceof WaterFluid.Source) {
                    ItemStack blockStack = ModBlocks.CREATIVE_FLUID_TANK.get().asItem().getDefaultInstance();
                    FluidStack stack = new FluidStack(fluid, Integer.MAX_VALUE);
                    blockStack.getOrCreateTag().put("BlockEntityTag", writeToNBT(stack));

                    blockList.add(blockStack);
                }
            }
            registration.getIngredientManager().addIngredientsAtRuntime(VanillaTypes.ITEM_STACK, blockList);
        } else {
            blockList.add(new ItemStack(ModBlocks.CREATIVE_FLUID_TANK.get()));
            registration.getIngredientManager().removeIngredientsAtRuntime(VanillaTypes.ITEM_STACK, blockList);
        }

        blockList = new ArrayList<>();
        if (ClientConfig.showCreativeEnergyTanksInJEI.get()) {
            ItemStack blockStack = ModBlocks.CREATIVE_ENERGY_TANK.get().asItem().getDefaultInstance();
            blockStack.getOrCreateTag().put("BlockEntityTag", writeInt(Integer.MAX_VALUE));

            blockList.add(blockStack);
            registration.getIngredientManager().addIngredientsAtRuntime(VanillaTypes.ITEM_STACK, blockList);
        } else {
            blockList.add(new ItemStack(ModBlocks.CREATIVE_ENERGY_TANK.get()));
            registration.getIngredientManager().removeIngredientsAtRuntime(VanillaTypes.ITEM_STACK, blockList);
        }

    }

    public CompoundTag writeInt(int amount) {
        CompoundTag tag = new CompoundTag();
        tag.putInt("Energy", amount);
        return tag;
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

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(ThoriumCraftingTableContainer.class, ModContainerTypes.THORIUM_CRAFTING_TABLE_CONTAINER.get(), THORIUM_RECIPE_TYPE,
                SLOT_DEFAULT + PLAYER_INVENTORY_COUNT, ThoriumCraftingRecipe.MAX_WIDTH * ThoriumCraftingRecipe.MAX_HEIGHT, SLOT_DEFAULT, PLAYER_INVENTORY_COUNT);
        registration.addRecipeTransferHandler(MachineConcentratorContainer.class, ModContainerTypes.CONCENTRATOR_CONTAINER.get(), CONCENTRATING_RECIPE_TYPE,
                SLOT_DEFAULT + PLAYER_INVENTORY_COUNT, 1, SLOT_DEFAULT, PLAYER_INVENTORY_COUNT);
        registration.addRecipeTransferHandler(MachineBlastFurnaceContainer.class, ModContainerTypes.BLAST_FURNACE_CONTAINER.get(), BLASTING_RECIPE_TYPE,
                SLOT_DEFAULT + PLAYER_INVENTORY_COUNT, 3, SLOT_DEFAULT, PLAYER_INVENTORY_COUNT);
        registration.addRecipeTransferHandler(MachineDecomposerContainer.class, ModContainerTypes.DECOMPOSER_CONTAINER.get(), DECOMPOSING_RECIPE_TYPE,
                SLOT_DECOMPOSER + PLAYER_INVENTORY_COUNT, 2, SLOT_DECOMPOSER, PLAYER_INVENTORY_COUNT);
        registration.addRecipeTransferHandler(MachineUraniumOxidizerContainer.class, ModContainerTypes.URANIUM_OXIDIZER_CONTAINER.get(), OXIDIZING_RECIPE_TYPE,
                SLOT_DEFAULT + PLAYER_INVENTORY_COUNT, 2, SLOT_DEFAULT, PLAYER_INVENTORY_COUNT);
        registration.addRecipeTransferHandler(MachineFluidEnricherContainer.class, ModContainerTypes.FLUID_ENRICHER_CONTAINER.get(), FLUID_ENRICHING_RECIPE_TYPE,
                SLOT_DEFAULT + PLAYER_INVENTORY_COUNT, 2, SLOT_DEFAULT, PLAYER_INVENTORY_COUNT);
        // Skip: Centrifuging - Crystallizing - Electrolysing - Evaporating
        registration.addRecipeTransferHandler(MachineSaltMelterContainer.class, ModContainerTypes.SALT_MELTER_CONTAINER.get(), SMELTING_RECIPE_TYPE,
                SLOT_DEFAULT + PLAYER_INVENTORY_COUNT, 3, SLOT_DEFAULT, PLAYER_INVENTORY_COUNT);
    }

}
