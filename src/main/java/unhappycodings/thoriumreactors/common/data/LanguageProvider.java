package unhappycodings.thoriumreactors.common.data;

import net.minecraft.data.DataGenerator;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.common.block.ModBlocks;

public class LanguageProvider extends net.minecraftforge.common.data.LanguageProvider {

    public LanguageProvider(DataGenerator gen, String locale) {
        super(gen, ThoriumReactors.MOD_ID, locale);
    }

    @Override
    protected void addTranslations() {
        add(ModBlocks.THORIUM_CRAFTING_TABLE.get(), "Thorium Crafting Table");

        add("itemGroup.quarry.items", "Thorium Reactors");
    }
}
