package unhappycodings.thoriumreactors.common.container;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.common.container.base.BaseScreen;

public class ThoriumCraftingTableScreen extends BaseScreen<ThoriumCraftingTableContainer> {
    ThoriumCraftingTableContainer container;

    public ThoriumCraftingTableScreen(ThoriumCraftingTableContainer screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn);
        this.container = screenContainer;
    }

    @Override
    protected void renderLabels(@NotNull PoseStack pPoseStack, int pMouseX, int pMouseY) {
        drawCenteredText(Component.literal("Thorium Crafting").getString(), pPoseStack, getSizeX() / 2, 4);
        drawText(Component.literal("Inventory").getString(), pPoseStack, 8, 117);
    }

    @Override
    public void onClose() {
        this.getMenu().getTile().setChanged();
        super.onClose();
    }

    @Override
    public int getSizeX() {
        return 176;
    }

    @Override
    public int getSizeY() {
        return 209;
    }

    @Override
    public ResourceLocation getTexture() {
        return new ResourceLocation(ThoriumReactors.MOD_ID, "textures/gui/workbench_gui.png");
    }

    public void drawCenteredText(String text, PoseStack stack, int x, int y) {
        Minecraft.getInstance().font.draw(stack, text, x - (Minecraft.getInstance().font.width(text) / 2f), y, 1315860);
    }

    public void drawCenteredText(String text, PoseStack stack, int x, int y, int color) {
        Minecraft.getInstance().font.draw(stack, text, x - (Minecraft.getInstance().font.width(text) / 2f), y, color);
    }

    public void drawText(String text, PoseStack stack, int x, int y) {
        Minecraft.getInstance().font.draw(stack, text, x, y, 1315860);
    }

}
