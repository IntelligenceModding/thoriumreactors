package unhappycodings.thoriumreactors.common.container.chest;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.common.blockentity.chest.ThoriumChestBlockEntity;
import unhappycodings.thoriumreactors.common.container.base.screen.BaseScreen;
import unhappycodings.thoriumreactors.common.util.RenderUtil;

public class ThoriumChestScreen extends BaseScreen<ThoriumChestContainer> {
    ThoriumChestContainer container;

    public ThoriumChestScreen(ThoriumChestContainer screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn);
        this.container = screenContainer;
        ((ThoriumChestBlockEntity) this.getTile()).startOpen(inv.player);
    }

    @Override
    protected void renderLabels(@NotNull PoseStack pPoseStack, int pMouseX, int pMouseY) {
        RenderUtil.drawCenteredText(Component.literal("Thorium Chest").getString(), pPoseStack, getSizeX() / 2, 7);
        RenderUtil.drawText(Component.literal("Inventory").getString(), pPoseStack, 48, 163);
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
        return 248;
    }

    @Override
    public int getSizeY() {
        return 256;
    }

    @Override
    public ResourceLocation getTexture() {
        return new ResourceLocation(ThoriumReactors.MOD_ID, "textures/gui/thorium_chest_gui.png");
    }

}
