package unhappycodings.thoriumreactors.common.container.chest;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.common.blockentity.chest.SteelChestBlockEntity;
import unhappycodings.thoriumreactors.common.container.base.screen.BaseScreen;
import unhappycodings.thoriumreactors.common.util.RenderUtil;

public class SteelChestScreen extends BaseScreen<SteelChestContainer> {
    SteelChestContainer container;

    public SteelChestScreen(SteelChestContainer screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn);
        this.container = screenContainer;
        ((SteelChestBlockEntity) this.getTile()).startOpen(inv.player);
    }

    @Override
    protected void renderLabels(@NotNull PoseStack pPoseStack, int pMouseX, int pMouseY) {
        pPoseStack.pushPose();
        pPoseStack.scale(0.7f, 0.7f, 0.7f);
        RenderUtil.drawText(Component.literal("Steel Chest").withStyle(RenderUtil::notoSans), pPoseStack, 10, 2, 11184810);
        RenderUtil.drawRightboundText(Component.literal(Minecraft.getInstance().player.getScoreboardName()).withStyle(RenderUtil::notoSans), pPoseStack, 293, 2, 11184810);
        pPoseStack.popPose();
        RenderUtil.drawText(Component.literal("Inventory").withStyle(RenderUtil::notoSans), pPoseStack, 26, 145, 11184810);
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
