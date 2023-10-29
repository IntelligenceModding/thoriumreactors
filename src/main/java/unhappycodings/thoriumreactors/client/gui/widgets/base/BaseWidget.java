package unhappycodings.thoriumreactors.client.gui.widgets.base;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.client.gui.GuiUtil;
import unhappycodings.thoriumreactors.client.gui.widgets.ModButton;
import unhappycodings.thoriumreactors.common.container.base.screen.BaseScreen;

import java.util.ArrayList;
import java.util.List;

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
    public void render(@NotNull GuiGraphics graphics, int x, int y, float partialTicks) {
        GuiUtil.reset();

        for (BaseWidget child : children) {
            child.render(graphics, x, y, minecraft.getDeltaFrameTime());
        }
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
        if (this instanceof ModButton) super.playDownSound(pHandler);
    }

    public <ELEMENT extends BaseWidget> ELEMENT addChild(ELEMENT child) {
        children.add(child);
        return child;
    }

    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        return super.isMouseOver(mouseX, mouseY) || children.stream().anyMatch(child -> child.isMouseOver(mouseX, mouseY));
    }

}
