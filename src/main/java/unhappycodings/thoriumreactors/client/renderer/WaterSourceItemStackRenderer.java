package unhappycodings.thoriumreactors.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.client.RenderTypeHelper;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.common.registration.ModBlocks;

public class WaterSourceItemStackRenderer extends BlockEntityWithoutLevelRenderer {
    private static final ResourceLocation MODEL_LOCATION = new ResourceLocation(ThoriumReactors.MOD_ID, "block/water_source_block");

    public WaterSourceItemStackRenderer(BlockEntityRenderDispatcher dispatcher, EntityModelSet modelSet) {
        super(dispatcher, modelSet);
    }

    @Override
    public void renderByItem(ItemStack stack, @NotNull ItemDisplayContext displayContext, PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight, int packedOverlay) {
        ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();

        // Pop off the transformations applied by ItemRenderer before calling this
        poseStack.popPose();
        poseStack.pushPose();
        BakedModel mainModel = Minecraft.getInstance().getModelManager().getModel(MODEL_LOCATION);
        mainModel = mainModel.applyTransform(displayContext, poseStack, isLeftHand(displayContext));
        poseStack.translate(-0.5, -0.5, -0.5); // Replicate ItemRenderer's translation

        boolean glint = stack.hasFoil();
        for (RenderType type : mainModel.getRenderTypes(stack, true)) {
            type = RenderTypeHelper.getEntityRenderType(type, true);
            VertexConsumer consumer = ItemRenderer.getFoilBuffer(buffer, type, true, glint);
            renderer.renderModelLists(mainModel, stack, packedLight, packedOverlay, poseStack, consumer);

            VertexConsumer boxVertexConsumer = buffer.getBuffer(RenderType.entityTranslucent(TextureAtlas.LOCATION_BLOCKS));

            WaterSourceBlockEntityRenderer.drawBox(poseStack, boxVertexConsumer, new FluidStack(Fluids.WATER, 1), 0, 0f, 0, 11, 0.01f, 11, 0, 0, 16, 16, 0.975f, 0.032f, packedLight);

        }
    }

    private static boolean isLeftHand(ItemDisplayContext type) {
        return type == ItemDisplayContext.FIRST_PERSON_LEFT_HAND|| type == ItemDisplayContext.THIRD_PERSON_LEFT_HAND;
    }
}