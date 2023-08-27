package unhappycodings.thoriumreactors.common.registration;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.RegistryObject;
import unhappycodings.thoriumreactors.ThoriumReactors;

public class ModSounds {
    public static final RegistryObject<SoundEvent> MACHINE_CLICK = registerSoundEvent("machine.click");
    public static final RegistryObject<SoundEvent> MACHINE_GENERATOR = registerSoundEvent("machine.generator");
    public static final RegistryObject<SoundEvent> MACHINE_ELECTROLYTIC_SALT_SEPARATOR = registerSoundEvent("machine.electrolytic_salt_separator");
    public static final RegistryObject<SoundEvent> MACHINE_SALT_MELTER = registerSoundEvent("machine.salt_melter");
    public static final RegistryObject<SoundEvent> MACHINE_FLUID_EVAPORATION = registerSoundEvent("machine.fluid_evaporation");
    public static final RegistryObject<SoundEvent> MACHINE_URANIUM_OXIDIZER = registerSoundEvent("machine.uranium_oxidizer");
    public static final RegistryObject<SoundEvent> MACHINE_FLUID_CENTRIFUGE = registerSoundEvent("machine.fluid_centrifuge");
    public static final RegistryObject<SoundEvent> MACHINE_CRYSTALIZER = registerSoundEvent("machine.crystalizer");
    public static final RegistryObject<SoundEvent> MACHINE_BLAST_FURNACE = registerSoundEvent("machine.blast_furnace");
    public static final RegistryObject<SoundEvent> DIGITALBEEP_0 = registerSoundEvent("digitalbeep.0");
    public static final RegistryObject<SoundEvent> DIGITALBEEP_1 = registerSoundEvent("digitalbeep.1");
    public static final RegistryObject<SoundEvent> DIGITALBEEP_2 = registerSoundEvent("digitalbeep.2");
    public static final RegistryObject<SoundEvent> DIGITALBEEP_3 = registerSoundEvent("digitalbeep.3");
    public static final RegistryObject<SoundEvent> DIGITALBEEP_4 = registerSoundEvent("digitalbeep.4");
    public static final RegistryObject<SoundEvent> DIGITALBEEP_5 = registerSoundEvent("digitalbeep.5");
    public static final RegistryObject<SoundEvent> NOTIFICATION = registerSoundEvent("notification");
    public static final RegistryObject<SoundEvent> ALARM_1 = registerSoundEvent("alarm.1");
    public static final RegistryObject<SoundEvent> ALARM_2 = registerSoundEvent("alarm.2");
    public static final RegistryObject<SoundEvent> ALARM_3 = registerSoundEvent("alarm.3");
    public static final RegistryObject<SoundEvent> REACTOR_STARTUP = registerSoundEvent("reactor.startup");
    public static final RegistryObject<SoundEvent> REACTOR_RUN = registerSoundEvent("reactor.run");
    public static final RegistryObject<SoundEvent> REACTOR_SHUTDOWN = registerSoundEvent("reactor.shutdown");
    public static final RegistryObject<SoundEvent> TURBINE_HISS_1 = registerSoundEvent("turbine.hiss.1");
    public static final RegistryObject<SoundEvent> TURBINE_HISS_2 = registerSoundEvent("turbine.hiss.2");
    public static final RegistryObject<SoundEvent> TURBINE_HISS_3 = registerSoundEvent("turbine.hiss.3");
    public static final RegistryObject<SoundEvent> TURBINE_RUN = registerSoundEvent("turbine.run");

    public static void register() {
    }

    private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        return Registration.SOUND_EVENTS.register(name, () -> new SoundEvent(new ResourceLocation(ThoriumReactors.MOD_ID, name)));
    }

}
