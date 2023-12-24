package unhappycodings.thoriumreactors.common.container.chest;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.common.blockentity.chest.SteelChestBlockEntity;
import unhappycodings.thoriumreactors.common.container.base.screen.BaseScreen;
import unhappycodings.thoriumreactors.common.util.ScreenUtil;

public class SteelChestScreen extends BaseScreen<SteelChestContainer> {
    SteelChestContainer container;

    public SteelChestScreen(SteelChestContainer screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn);
        this.container = screenContainer;
        ((SteelChestBlockEntity) this.getTile()).startOpen(inv.player);
    }

    @Override
    protected void renderLabels(@NotNull GuiGraphics graphics, int pMouseX, int pMouseY) {
        PoseStack pPoseStack = graphics.pose();
        pPoseStack.pushPose();
        pPoseStack.scale(0.7f, 0.7f, 0.7f);
        ScreenUtil.drawText(Component.translatable("block.thoriumreactors.steel_chest_block").withStyle(ScreenUtil::notoSans), graphics, 10, 2, 11184810);
        ScreenUtil.drawRightboundText(Component.literal(Minecraft.getInstance().player.getScoreboardName()).withStyle(ScreenUtil::notoSans), graphics, 293, 2, 11184810);
        pPoseStack.popPose();
        ScreenUtil.drawText(Component.translatable("key.categories.inventory").withStyle(ScreenUtil::notoSans), graphics, 26, 145, 11184810);
    }

    @Override
    public void onClose() {
        this.getMenu().getTile().setChanged();
        super.onClose();
    }

    @Override
    public void removed() {
        super.removed();
        container.getTile().stopOpen(this.minecraft.player);
    }

    @Override
    public int getSizeX() {
        return 212;
    }

    @Override
    public int getSizeY() {
        return 238;
    }

    @Override
    public ResourceLocation getTexture() {
        return new ResourceLocation(ThoriumReactors.MOD_ID, "textures/gui/steel_chest_gui.png");
    }

}
