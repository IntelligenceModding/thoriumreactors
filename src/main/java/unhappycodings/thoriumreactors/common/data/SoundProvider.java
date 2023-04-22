package unhappycodings.thoriumreactors.common.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinition;
import net.minecraftforge.common.data.SoundDefinitionsProvider;
import unhappycodings.thoriumreactors.ThoriumReactors;

public class SoundProvider extends SoundDefinitionsProvider {


    /**
     * Creates a new instance of this data provider.
     *
     * @param generator The data generator instance provided by the event you are initializing this provider in.
     * @param modId     The mod ID of the current mod.
     * @param helper    The existing file helper provided by the event you are initializing this provider in.
     */
    protected SoundProvider(DataGenerator generator, String modId, ExistingFileHelper helper) {
        super(generator, modId, helper);
    }

    @Override
    public void registerSounds() {
        add("machine_click", definition().with(SoundDefinition.Sound.sound(new ResourceLocation(ThoriumReactors.MOD_ID, "machine_click"), SoundDefinition.SoundType.SOUND)));
        add("machine_generator", definition().with(SoundDefinition.Sound.sound(new ResourceLocation(ThoriumReactors.MOD_ID, "machine_generator"), SoundDefinition.SoundType.SOUND)));
        add("machine_fluid_evaporation", definition().with(SoundDefinition.Sound.sound(new ResourceLocation(ThoriumReactors.MOD_ID, "machine_fluid_evaporation"), SoundDefinition.SoundType.SOUND)));
        add("machine_electrolytic_salt_separator", definition().with(SoundDefinition.Sound.sound(new ResourceLocation(ThoriumReactors.MOD_ID, "machine_electrolytic_salt_separator"), SoundDefinition.SoundType.SOUND)));
        add("machine_salt_melter", definition().with(SoundDefinition.Sound.sound(new ResourceLocation(ThoriumReactors.MOD_ID, "machine_salt_melter"), SoundDefinition.SoundType.SOUND)));
        add("machine_uranium_oxidizer", definition().with(SoundDefinition.Sound.sound(new ResourceLocation(ThoriumReactors.MOD_ID, "machine_uranium_oxidizer"), SoundDefinition.SoundType.SOUND)));
        add("machine_fluid_centrifuge", definition().with(SoundDefinition.Sound.sound(new ResourceLocation(ThoriumReactors.MOD_ID, "machine_fluid_centrifuge"), SoundDefinition.SoundType.SOUND)));
        add("machine_crystalizer", definition().with(SoundDefinition.Sound.sound(new ResourceLocation(ThoriumReactors.MOD_ID, "machine_crystalizer"), SoundDefinition.SoundType.SOUND)));
        add("machine_blast_furnace", definition().with(SoundDefinition.Sound.sound(new ResourceLocation(ThoriumReactors.MOD_ID, "machine_blast_furnace"), SoundDefinition.SoundType.SOUND)));
    }
}
