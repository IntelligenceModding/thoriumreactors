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
        add("machine.click", definition().with(SoundDefinition.Sound.sound(new ResourceLocation(ThoriumReactors.MOD_ID, "machine_click"), SoundDefinition.SoundType.SOUND)));
        add("machine.generator", definition().with(SoundDefinition.Sound.sound(new ResourceLocation(ThoriumReactors.MOD_ID, "machine_generator"), SoundDefinition.SoundType.SOUND)));
        add("machine.fluid_evaporation", definition().with(SoundDefinition.Sound.sound(new ResourceLocation(ThoriumReactors.MOD_ID, "machine_fluid_evaporation"), SoundDefinition.SoundType.SOUND)));
        add("machine.electrolytic_salt_separator", definition().with(SoundDefinition.Sound.sound(new ResourceLocation(ThoriumReactors.MOD_ID, "machine_electrolytic_salt_separator"), SoundDefinition.SoundType.SOUND)));
        add("machine.salt_melter", definition().with(SoundDefinition.Sound.sound(new ResourceLocation(ThoriumReactors.MOD_ID, "machine_salt_melter"), SoundDefinition.SoundType.SOUND)));
        add("machine.uranium_oxidizer", definition().with(SoundDefinition.Sound.sound(new ResourceLocation(ThoriumReactors.MOD_ID, "machine_uranium_oxidizer"), SoundDefinition.SoundType.SOUND)));
        add("machine.fluid_centrifuge", definition().with(SoundDefinition.Sound.sound(new ResourceLocation(ThoriumReactors.MOD_ID, "machine_fluid_centrifuge"), SoundDefinition.SoundType.SOUND)));
        add("machine.crystalizer", definition().with(SoundDefinition.Sound.sound(new ResourceLocation(ThoriumReactors.MOD_ID, "machine_crystalizer"), SoundDefinition.SoundType.SOUND)));
        add("machine.blast_furnace", definition().with(SoundDefinition.Sound.sound(new ResourceLocation(ThoriumReactors.MOD_ID, "machine_blast_furnace"), SoundDefinition.SoundType.SOUND)));
        add("digitalbeep.0", definition().with(SoundDefinition.Sound.sound(new ResourceLocation(ThoriumReactors.MOD_ID, "beep_0"), SoundDefinition.SoundType.SOUND)));
        add("digitalbeep.1", definition().with(SoundDefinition.Sound.sound(new ResourceLocation(ThoriumReactors.MOD_ID, "beep_1"), SoundDefinition.SoundType.SOUND)));
        add("digitalbeep.2", definition().with(SoundDefinition.Sound.sound(new ResourceLocation(ThoriumReactors.MOD_ID, "beep_2"), SoundDefinition.SoundType.SOUND)));
        add("digitalbeep.3", definition().with(SoundDefinition.Sound.sound(new ResourceLocation(ThoriumReactors.MOD_ID, "beep_3"), SoundDefinition.SoundType.SOUND)));
        add("digitalbeep.4", definition().with(SoundDefinition.Sound.sound(new ResourceLocation(ThoriumReactors.MOD_ID, "beep_4"), SoundDefinition.SoundType.SOUND)));
        add("digitalbeep.5", definition().with(SoundDefinition.Sound.sound(new ResourceLocation(ThoriumReactors.MOD_ID, "beep_5"), SoundDefinition.SoundType.SOUND)));
        add("notification", definition().with(SoundDefinition.Sound.sound(new ResourceLocation(ThoriumReactors.MOD_ID, "notification"), SoundDefinition.SoundType.SOUND)));
        add("alarm.1", definition().with(SoundDefinition.Sound.sound(new ResourceLocation(ThoriumReactors.MOD_ID, "alarm_1"), SoundDefinition.SoundType.SOUND)));
        add("alarm.2", definition().with(SoundDefinition.Sound.sound(new ResourceLocation(ThoriumReactors.MOD_ID, "alarm_2"), SoundDefinition.SoundType.SOUND)));
        add("alarm.3", definition().with(SoundDefinition.Sound.sound(new ResourceLocation(ThoriumReactors.MOD_ID, "alarm_3"), SoundDefinition.SoundType.SOUND)));
        add("reactor.startup", definition().with(SoundDefinition.Sound.sound(new ResourceLocation(ThoriumReactors.MOD_ID, "reactor_startup"), SoundDefinition.SoundType.SOUND)));
        add("reactor.run", definition().with(SoundDefinition.Sound.sound(new ResourceLocation(ThoriumReactors.MOD_ID, "reactor_run"), SoundDefinition.SoundType.SOUND)));
        add("reactor.shutdown", definition().with(SoundDefinition.Sound.sound(new ResourceLocation(ThoriumReactors.MOD_ID, "reactor_shutdown"), SoundDefinition.SoundType.SOUND)));
    }
}
