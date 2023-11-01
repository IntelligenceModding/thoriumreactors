package unhappycodings.thoriumreactors.common.registration;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import org.lwjgl.glfw.GLFW;
import unhappycodings.thoriumreactors.ThoriumReactors;

public class ModKeyBindings {

    public static final KeyMapping SHOW_DETAILS = new KeyMapping("keybind." + ThoriumReactors.MOD_ID + ".details", KeyConflictContext.UNIVERSAL, KeyModifier.NONE, InputConstants.Type.KEYSYM.getOrCreate(GLFW.GLFW_KEY_LEFT_SHIFT), "itemGroup." + ThoriumReactors.MOD_ID);
    public static final KeyMapping SHOW_DESCRIPTION = new KeyMapping("keybind." + ThoriumReactors.MOD_ID + ".description", KeyConflictContext.UNIVERSAL, KeyModifier.NONE, InputConstants.Type.KEYSYM.getOrCreate(GLFW.GLFW_KEY_LEFT_CONTROL), "itemGroup." + ThoriumReactors.MOD_ID);

}
