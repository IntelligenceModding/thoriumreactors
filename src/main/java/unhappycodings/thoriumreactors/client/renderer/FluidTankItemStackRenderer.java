package unhappycodings.thoriumreactors.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.RenderTypeHelper;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.common.registration.ModBlocks;

public class FluidTankItemStackRenderer extends BlockEntityWithoutLevelRenderer {
    private static final ResourceLocation MODEL_LOCATION = new ResourceLocation(ThoriumReactors.MOD_ID, "block/fluid_tank");

    public FluidTankItemStackRenderer(BlockEntityRenderDispatcher dispatcher, EntityModelSet modelSet) {
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

            CompoundTag tag = stack.getOrCreateTag().getCompound("BlockEntityTag");
            FluidStack fluidStack = FluidStack.loadFluidStackFromNBT(tag.getCompound("Fluid"));
            VertexConsumer boxVertexConsumer = buffer.getBuffer(RenderType.entityTranslucent(TextureAtlas.LOCATION_BLOCKS));
            if (fluidStack.getAmount() > 0) {
                float height = ((float) fluidStack.getAmount() / (float) getCapacityForType(stack)) * 0.94f;
                FluidTankBlockEntityRenderer.drawBox(poseStack, boxVertexConsumer, fluidStack, 0, 0f, 0, 11, 0.01f, 11, 0, 0, 16, 16, height, 0.032f, packedLight);
            }
        }
    }

    private int getCapacityForType(ItemStack stack) {
        if (stack.is(ModBlocks.SIMPLE_FLUID_TANK.get().asItem())) return 32000;
        if (stack.is(ModBlocks.GENERIC_FLUID_TANK.get().asItem())) return 64000;
        if (stack.is(ModBlocks.PROGRESSIVE_FLUID_TANK.get().asItem())) return 128000;
        else return Integer.MAX_VALUE;
    }

    private static boolean isLeftHand(ItemDisplayContext type) {
        return type == ItemDisplayContext.FIRST_PERSON_LEFT_HAND|| type == ItemDisplayContext.THIRD_PERSON_LEFT_HAND;
    }
}