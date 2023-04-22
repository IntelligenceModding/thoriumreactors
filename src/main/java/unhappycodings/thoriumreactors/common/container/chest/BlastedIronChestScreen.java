package unhappycodings.thoriumreactors.common.container.chest;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.common.blockentity.chest.BlastedIronChestBlockEntity;
import unhappycodings.thoriumreactors.common.container.base.screen.BaseScreen;
import unhappycodings.thoriumreactors.common.util.RenderUtil;

public class BlastedIronChestScreen extends BaseScreen<BlastedIronChestContainer> {
    BlastedIronChestContainer container;

    public BlastedIronChestScreen(BlastedIronChestContainer screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn);
        this.container = screenContainer;
        ((BlastedIronChestBlockEntity) this.getTile()).startOpen(inv.player);
    }

    @Override
    protected void renderLabels(@NotNull PoseStack pPoseStack, int pMouseX, int pMouseY) {
        RenderUtil.drawCenteredText(Component.literal("Blasted Iron Chest").getString(), pPoseStack, getSizeX() / 2, 7);
        RenderUtil.drawText(Component.literal("Inventory").getString(), pPoseStack, 8, 127);
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
        return 176;
    }

    @Override
    public int getSizeY() {
        return 220;
    }

    @Override
    public ResourceLocation getTexture() {
        return new ResourceLocation(ThoriumReactors.MOD_ID, "textures/gui/blasted_iron_chest_gui.png");
    }

}
