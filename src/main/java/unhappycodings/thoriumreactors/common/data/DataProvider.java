package unhappycodings.thoriumreactors.common.data;

import net.minecraft.Util;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.common.registration.Registration;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = ThoriumReactors.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataProvider {

    @SubscribeEvent
    public static void onDataGen(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> completablefuture = CompletableFuture.supplyAsync(VanillaRegistries::createLookup, Util.backgroundExecutor());

        generator.addProvider(true, new ModelAndBlockStatesProvider(generator, existingFileHelper));
        generator.addProvider(true, new ItemModelProvider(generator, existingFileHelper));
        generator.addProvider(true, new BlockTagsProvider(generator.getPackOutput(), Registration.BLOCKS.getRegistryKey(), completablefuture, existingFileHelper));
        generator.addProvider(true, new ItemTagsProvider(generator.getPackOutput(), Registration.ITEMS.getRegistryKey(), completablefuture, existingFileHelper));
        generator.addProvider(true, new RecipeProvider(generator.getPackOutput()));
        generator.addProvider(true, new AmericanLanguageProvider(generator, "en_us"));
        generator.addProvider(true, new GermanLanguageProvider(generator, "de_de"));
        generator.addProvider(true, new SoundProvider(generator, ThoriumReactors.MOD_ID, existingFileHelper));
        generator.addProvider(true, new LootTableProvider(generator.getPackOutput()));
    }
}
