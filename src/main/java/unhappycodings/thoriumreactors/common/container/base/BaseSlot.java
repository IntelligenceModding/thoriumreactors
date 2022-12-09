package unhappycodings.thoriumreactors.common.container.base;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.client.gui.GuiUtil;
import unhappycodings.thoriumreactors.common.util.RenderUtil;

import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

// CREDIT GOES TO: Sr_endi  | https://github.com/Seniorendi
public class BaseSlot extends SlotItemHandler {
    public static final ResourceLocation GHOST_OVERLAY = new ResourceLocation(ThoriumReactors.MOD_ID, "textures/gui/slot/ghost_overlay.png");

    private final Inventory inventory;
    private final Predicate<ItemStack> canPlace;
    public boolean isEnabled = true;
    int size;
    private Pair<Predicate<ItemStack>, ResourceLocation> overlay;
    private ItemStack[] ghostOverlays;
    private int nextGhostItemTick = 0;
    private ItemStack currentGhostItem;

    public BaseSlot(IItemHandler itemHandler, Inventory inventory, int index, int xPosition, int yPosition, ResourceLocation texture, Predicate<ItemStack> canPlace, ItemStack... ghostOverlays) {
        super(itemHandler, index, xPosition, yPosition);
        this.inventory = inventory;
        this.size = 18;
        this.canPlace = canPlace;
        this.ghostOverlays = ghostOverlays;
    }

    public BaseSlot addGhostOverlays(Item... ghostOverlays) {
        ItemStack[] items = new ItemStack[ghostOverlays.length];
        for (int i = 0; i < ghostOverlays.length; i++)
            items[i] = new ItemStack(ghostOverlays[i], 1);

        this.ghostOverlays = ArrayUtils.addAll(this.ghostOverlays, items);
        return this;
    }

    public BaseSlot addGhostListOverlays(List<Item> ghostOverlays) {
        ItemStack[] items = new ItemStack[ghostOverlays.size()];
        for (int i = 0; i < ghostOverlays.size(); i++) {
            items[i] = new ItemStack(ghostOverlays.get(i), 1);
        }
        this.ghostOverlays = ArrayUtils.addAll(this.ghostOverlays, items);
        return this;
    }

    public int getTexX() {
        return this.x - (getSize() - 16) / 2;
    }

    public int getTexY() {
        return this.y - (getSize() - 16) / 2;
    }

    public int getSize() {
        return size;
    }

    public BaseSlot setSize(int size) {
        this.size = size;
        return this;
    }

    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {
        this.setChanged();
        return canPlace.test(stack);
    }

    public void setEnabled(boolean enable) {
        this.isEnabled = enable;
    }

    @Override
    public boolean isActive() {
        return isEnabled;
    }

    @Override
    public void setChanged() {
        if (inventory != null) inventory.setChanged();
    }

    public ItemStack[] getGhostOverlayItem() {
        return ghostOverlays;
    }

    @OnlyIn(Dist.CLIENT)
    public void renderGhostOverlay(PoseStack stack, int x, int y) {
        if (getGhostOverlayItem() != null && getGhostOverlayItem().length > 0) {
            nextGhostItemTick++;

            if (!getItem().isEmpty()) return;
            if (this.currentGhostItem == null || this.currentGhostItem.isEmpty())
                this.currentGhostItem = getGhostOverlayItem()[new Random().nextInt(getGhostOverlayItem().length)];
            if (nextGhostItemTick % 500 == 0) {
                this.currentGhostItem = getGhostOverlayItem()[new Random().nextInt(getGhostOverlayItem().length)];
                nextGhostItemTick = 0;
            }

            stack.pushPose();
            RenderUtil.renderGuiItem(currentGhostItem, x + this.x, y + this.y);
            RenderSystem.setShaderTexture(0, GHOST_OVERLAY);
            RenderSystem.setShaderColor(1, 1, 1, 0.65f);
            RenderSystem.enableBlend();
            RenderSystem.disableDepthTest();
            //stack.translate(0,0,10);
            GuiComponent.blit(stack, x + this.x, y + this.y, 0, 0, 16, 16, 16, 16);
            RenderSystem.disableBlend();
            RenderSystem.enableDepthTest();
            GuiUtil.reset();
            stack.popPose();
        }
        if (overlay != null && overlay.getFirst().test(getItem())) {
            RenderSystem.setShaderColor(1, 1, 1, 0.4f);
            RenderSystem.enableBlend();
            RenderSystem.disableDepthTest();
            RenderSystem.setShaderTexture(0, overlay.getSecond());
            GuiComponent.blit(stack, x + this.x, y + this.y, 0, 0, 16, 16, 16, 16);
            RenderSystem.disableBlend();
            RenderSystem.enableDepthTest();

        }
    }
}
