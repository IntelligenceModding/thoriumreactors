package unhappycodings.thoriumreactors.client.gui.widgets.base;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundEngine;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.client.gui.GuiUtil;
import unhappycodings.thoriumreactors.client.gui.widgets.ModButton;
import unhappycodings.thoriumreactors.common.registration.ModSounds;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class BaseWidget extends AbstractWidget {

    public final AbstractContainerScreen<?> screen;
    protected final List<BaseWidget> children = new ArrayList<>();
    protected BlockEntity tile;
    protected Minecraft minecraft = Minecraft.getInstance();
    protected int leftPos;
    protected int topPos;

    public BaseWidget(int x, int y, int width, int height, BlockEntity tile, AbstractContainerScreen<?> screen) {
        super(screen.getGuiLeft() + x, screen.getGuiTop() + y, width, height, Component.empty());
        this.tile = tile;
        this.screen = screen;
        this.leftPos = screen.getGuiLeft();
        this.topPos = screen.getGuiTop();
    }

    public BaseWidget(int x, int y, int width, int height, int imageWidth, int imageHeight) {
        super(((width - imageWidth) / 2) + x, ((height - imageHeight) / 2) + y, width, height, Component.empty());
        this.screen = null;
        this.leftPos = (width - imageWidth) / 2;
        this.topPos = (height - imageHeight) / 2;
    }

    @Override
    public void renderToolTip(@NotNull PoseStack matrixStack, int mouseX, int mouseY) {
        children.forEach(child -> child.renderToolTip(matrixStack, mouseX, mouseY));
    }

    @Override
    public void render(@NotNull PoseStack matrixStack, int x, int y, float partialTicks) {
        GuiUtil.reset();

        for (BaseWidget child : children) {
            child.render(matrixStack, x, y, minecraft.getDeltaFrameTime());
        }
    }

    @Override
    public void renderBg(@NotNull PoseStack matrixStack, @NotNull Minecraft minecraft, int pMouseX, int pMouseY) {
        GuiUtil.reset();

        renderToolTip(matrixStack, pMouseX, pMouseY);
    }

    @Override
    public void onClick(double pMouseX, double pMouseY) {
        for (BaseWidget child : children) {
            if (child instanceof ModButton) child.onClick(pMouseX, pMouseY);
        }
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        for (BaseWidget child : children) {
            if (child instanceof ModButton) child.mouseClicked(pMouseX, pMouseY, pButton);
        }
        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }

    @Override
    public void playDownSound(@NotNull SoundManager pHandler) {
        SoundEvent sound = this.x < (this.screen.width / 3) ? ModSounds.DIGITALBEEP_1.get() : this.x > ((this.screen.width / 3) * 2) ? ModSounds.DIGITALBEEP_1.get() : ModSounds.DIGITALBEEP_3.get();
        if (this instanceof ModButton) pHandler.play(SimpleSoundInstance.forUI(sound, 1.0F));
    }

    public <ELEMENT extends BaseWidget> ELEMENT addChild(ELEMENT child) {
        children.add(child);
        return child;
    }

    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        return super.isMouseOver(mouseX, mouseY) || children.stream().anyMatch(child -> child.isMouseOver(mouseX, mouseY));
    }

    public void drawText(String text, PoseStack stack, int x, int y) {
        Minecraft.getInstance().font.draw(stack, text, (leftPos - (width / 2) - x) - (Minecraft.getInstance().font.width(text) / 2), topPos + y, 4210752);
    }

    public void drawText(Component text, PoseStack stack, int x, int y) {
        Minecraft.getInstance().font.draw(stack, text, (leftPos - (width / 2) - x) - (Minecraft.getInstance().font.width(text) / 2), topPos + y, 4210752);
    }

    public void renderComponentTooltip(PoseStack poseStack, List<Component> components, int x, int y) {
        if (screen != null) screen.renderComponentTooltip(poseStack, components, x, y);
    }

    @Override
    public void updateNarration(@NotNull NarrationElementOutput narrationElementOutput) {
    }

}
