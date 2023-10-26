package unhappycodings.thoriumreactors.client.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;

import java.io.File;

public class ClientConfig {
    public static ForgeConfigSpec clientConfig;

    //region General
    public static ForgeConfigSpec.ConfigValue<Boolean> showFirstJoinWelcomeMessage;
    //endregion

    static {
        ForgeConfigSpec.Builder clientBuilder = new ForgeConfigSpec.Builder();
        init(clientBuilder);
        clientConfig = clientBuilder.build();
    }

    private static void init(ForgeConfigSpec.Builder clientBuilder) {
        clientBuilder.push("Reactor Control Screen");
        showFirstJoinWelcomeMessage = clientBuilder.comment("Should the player get a welcome message for thorium on first join?").define("showFirstJoinWelcomeMessage", false);
        clientBuilder.pop();
    }

    public static void loadConfigFile(ForgeConfigSpec config, String path) {
        final CommentedFileConfig file = CommentedFileConfig.builder(new File(path)).sync().autosave().writingMode(WritingMode.REPLACE).build();
        file.load();
        config.setConfig(file);
    }
}
