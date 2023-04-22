package unhappycodings.thoriumreactors.common.registration;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.RegistryObject;
import unhappycodings.thoriumreactors.ThoriumReactors;

public class ModSounds {

    public static final RegistryObject<SoundEvent> MACHINE_CLICK = registerSoundEvent("machine_click");
    public static final RegistryObject<SoundEvent> MACHINE_GENERATOR = registerSoundEvent("machine_generator");
    public static final RegistryObject<SoundEvent> MACHINE_ELECTROLYTIC_SALT_SEPARATOR = registerSoundEvent("machine_electrolytic_salt_separator");
    public static final RegistryObject<SoundEvent> MACHINE_SALT_MELTER = registerSoundEvent("machine_salt_melter");
    public static final RegistryObject<SoundEvent> MACHINE_FLUID_EVAPORATION = registerSoundEvent("machine_fluid_evaporation");
    public static final RegistryObject<SoundEvent> MACHINE_URANIUM_OXIDIZER = registerSoundEvent("machine_uranium_oxidizer");
    public static final RegistryObject<SoundEvent> MACHINE_FLUID_CENTRIFUGE = registerSoundEvent("machine_fluid_centrifuge");
    public static final RegistryObject<SoundEvent> MACHINE_CRYSTALIZER = registerSoundEvent("machine_crystalizer");
    public static final RegistryObject<SoundEvent> MACHINE_BLAST_FURNACE = registerSoundEvent("machine_blast_furnace");

    public static void register() {
    }

    private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        return Registration.SOUND_EVENTS.register(name, () -> new SoundEvent(new ResourceLocation(ThoriumReactors.MOD_ID, name)));
    }

}
