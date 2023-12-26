package unhappycodings.thoriumreactors.client.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;

import java.io.File;

public class ClientConfig {
    public static ForgeConfigSpec clientConfig;

    //region General
    public static ForgeConfigSpec.ConfigValue<Boolean> showLeftReactorScreenArea;
    public static ForgeConfigSpec.ConfigValue<Boolean> showRightReactorScreenArea;
    //endregion

    static {
        ForgeConfigSpec.Builder clientBuilder = new ForgeConfigSpec.Builder();
        init(clientBuilder);
        clientConfig = clientBuilder.build();
    }

    private static void init(ForgeConfigSpec.Builder clientBuilder) {
        clientBuilder.push("Reactor Control Screen");
        showLeftReactorScreenArea = clientBuilder.comment("Render left area of the reactor control screen").define("showLeftReactorScreenArea", true);
        showRightReactorScreenArea = clientBuilder.comment("Render right area of the reactor control screen").define("showRightReactorScreenArea", true);
        clientBuilder.pop();
    }

    public static void loadConfigFile(ForgeConfigSpec config, String path) {
        final CommentedFileConfig file = CommentedFileConfig.builder(new File(path)).sync().autosave().writingMode(WritingMode.REPLACE).build();
        file.load();
        config.setConfig(file);
    }
}
