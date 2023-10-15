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
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.RenderTypeHelper;
import net.minecraftforge.fluids.FluidStack;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.common.registration.ModBlocks;

public class EnergyTankItemStackRenderer extends BlockEntityWithoutLevelRenderer {
    private static final ResourceLocation MODEL_LOCATION = new ResourceLocation(ThoriumReactors.MOD_ID, "block/energy_tank");

    public EnergyTankItemStackRenderer(BlockEntityRenderDispatcher dispatcher, EntityModelSet modelSet) {
        super(dispatcher, modelSet);
    }

    @Override
    public void renderByItem(ItemStack stack, ItemTransforms.TransformType transformType, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();

        // Pop off the transformations applied by ItemRenderer before calling this
        poseStack.popPose();
        poseStack.pushPose();
        BakedModel mainModel = Minecraft.getInstance().getModelManager().getModel(MODEL_LOCATION);
        mainModel = mainModel.applyTransform(transformType, poseStack, isLeftHand(transformType));
        poseStack.translate(-0.5, -0.5, -0.5); // Replicate ItemRenderer's translation

        boolean glint = stack.hasFoil();
        for (RenderType type : mainModel.getRenderTypes(stack, true)) {
            type = RenderTypeHelper.getEntityRenderType(type, true);
            VertexConsumer consumer = ItemRenderer.getFoilBuffer(buffer, type, true, glint);
            renderer.renderModelLists(mainModel, stack, packedLight, packedOverlay, poseStack, consumer);

            CompoundTag tag = stack.getOrCreateTag().getCompound("BlockEntityTag");
            int amount = tag.getInt("Energy");
            VertexConsumer boxVertexConsumer = buffer.getBuffer(RenderType.entityTranslucent(TextureAtlas.LOCATION_BLOCKS));
            if (amount > 0) {
                float height = ((float) amount / (float) getCapacityForType(stack)) * 0.94f;
                EnergyTankBlockEntityRenderer.drawBox(poseStack, boxVertexConsumer, 0, 0f, 0, 11, 0.01f, 11, 0, 0, 16, 16, height, 0.032f, packedLight);
            }
        }
    }

    private int getCapacityForType(ItemStack stack) {
        if (stack.is(ModBlocks.SIMPLE_ENERGY_TANK.get().asItem())) return ModBlocks.SIMPLE_ENERGY_TANK.get().capacity;
        if (stack.is(ModBlocks.GENERIC_ENERGY_TANK.get().asItem())) return ModBlocks.GENERIC_ENERGY_TANK.get().capacity;
        if (stack.is(ModBlocks.PROGRESSIVE_ENERGY_TANK.get().asItem())) return ModBlocks.PROGRESSIVE_ENERGY_TANK.get().capacity;
        else return Integer.MAX_VALUE;
    }

    private static boolean isLeftHand(ItemTransforms.TransformType type) {
        return type == ItemTransforms.TransformType.FIRST_PERSON_LEFT_HAND || type == ItemTransforms.TransformType.THIRD_PERSON_LEFT_HAND;
    }
}