package unhappycodings.thoriumreactors.client.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;

import java.io.File;

public class ClientConfig {
    public static ForgeConfigSpec clientConfig;

    //region General
    public static ForgeConfigSpec.ConfigValue<Boolean> showBlockDetails;
    public static ForgeConfigSpec.ConfigValue<Boolean> showBlockDescription;
    public static ForgeConfigSpec.ConfigValue<Boolean> showLeftReactorScreenArea;
    public static ForgeConfigSpec.ConfigValue<Boolean> showRightReactorScreenArea;
    public static ForgeConfigSpec.ConfigValue<Boolean> showCreativeEnergyTanksInJEI;
    public static ForgeConfigSpec.ConfigValue<Boolean> showCreativeEnergyTanksInCreativeTAB;
    public static ForgeConfigSpec.ConfigValue<Boolean> showCreativeFluidTanksInJEI;
    public static ForgeConfigSpec.ConfigValue<Boolean> showCreativeFluidTanksInCreativeTAB;
    //endregion

    static {
        ForgeConfigSpec.Builder clientBuilder = new ForgeConfigSpec.Builder();
        init(clientBuilder);
        clientConfig = clientBuilder.build();
    }

    private static void init(ForgeConfigSpec.Builder clientBuilder) {
        clientBuilder.push("General");
        showBlockDetails = clientBuilder.comment("Show by hotkey activated details hover text").define("showBlockDetails", true);
        showBlockDescription = clientBuilder.comment("Show by hotkey activated description hover text").define("showBlockDescription", true);
        clientBuilder.pop();

        clientBuilder.push("Reactor Control Screen");
        showLeftReactorScreenArea = clientBuilder.comment("Render left area of the reactor control screen").define("showLeftReactorScreenArea", true);
        showRightReactorScreenArea = clientBuilder.comment("Render right area of the reactor control screen").define("showRightReactorScreenArea", true);
        clientBuilder.pop();

        clientBuilder.push("Creative TAB and JEI");
        showCreativeEnergyTanksInJEI = clientBuilder.comment("Show fluid creative tanks in Just Enough Items (JEI)").define("showCreativeEnergyTanksInJEI", true);
        showCreativeEnergyTanksInCreativeTAB = clientBuilder.comment("Show fluid creative tanks in creative tab").define("showCreativeEnergyTanksInCreativeTAB", true);
        showCreativeFluidTanksInJEI = clientBuilder.comment("Show fluid creative tanks in Just Enough Items (JEI)").define("showCreativeFluidTanksInJEI", true);
        showCreativeFluidTanksInCreativeTAB = clientBuilder.comment("Show fluid creative tanks in creative tab").define("showCreativeFluidTanksInCreativeTAB", true);
        clientBuilder.pop();
    }

    public static void loadConfigFile(ForgeConfigSpec config, String path) {
        final CommentedFileConfig file = CommentedFileConfig.builder(new File(path)).sync().autosave().writingMode(WritingMode.REPLACE).build();
        file.load();
        config.setConfig(file);
    }
}
