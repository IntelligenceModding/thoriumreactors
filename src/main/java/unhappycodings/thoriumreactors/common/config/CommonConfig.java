package unhappycodings.thoriumreactors.common.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;

import java.io.File;

public class CommonConfig {

    public static ForgeConfigSpec commonConfig;

    //region General
    public static ForgeConfigSpec.ConfigValue<String> somewhat;
    //endregion

    static {
        ForgeConfigSpec.Builder commonBuilder = new ForgeConfigSpec.Builder();

        init(commonBuilder);
        commonConfig = commonBuilder.build();
    }

    private static void init(ForgeConfigSpec.Builder commonBuilder) {
        commonBuilder.push("General");
        //somewhat = commonBuilder.comment("What Color should the overlay at the first corner be [Format: #RRGGBB]").define("first_corner_overlay_color", "#116300");
        commonBuilder.pop();
    }

    public static void loadConfigFile(ForgeConfigSpec config, String path) {
        final CommentedFileConfig file = CommentedFileConfig.builder(new File(path)).sync().autosave().writingMode(WritingMode.REPLACE).build();
        file.load();
        config.setConfig(file);
    }

}
