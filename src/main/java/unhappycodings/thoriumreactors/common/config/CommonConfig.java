package unhappycodings.thoriumreactors.common.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;

import java.io.File;

public class CommonConfig {

    public static ForgeConfigSpec commonConfig;

    public static ForgeConfigSpec.DoubleValue turbineEnergyGenerationModifier;

    static {
        ForgeConfigSpec.Builder commonBuilder = new ForgeConfigSpec.Builder();

        init(commonBuilder);
        commonConfig = commonBuilder.build();
    }

    private static void init(ForgeConfigSpec.Builder commonBuilder) {
        commonBuilder.push("General");
        turbineEnergyGenerationModifier = commonBuilder.comment("Modifier of turbine energy generation. 1.0 is default and means normal generation 2.0 means doubled, 0.5 half!").defineInRange("turbine_energy_generation_modifier", 1.0f, 0.0f, 100.0f);
        commonBuilder.pop();
    }

    public static void loadConfigFile(ForgeConfigSpec config, String path) {
        final CommentedFileConfig file = CommentedFileConfig.builder(new File(path)).sync().autosave().writingMode(WritingMode.REPLACE).build();
        file.load();
        config.setConfig(file);
    }

}
