package unhappycodings.thoriumreactors.common.blockentity.renderer;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BrightnessCombiner;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.LidBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import unhappycodings.thoriumreactors.ThoriumReactors;

import java.util.Calendar;

@OnlyIn(Dist.CLIENT)
public class SteelChestRenderer<T extends BlockEntity & LidBlockEntity> implements BlockEntityRenderer<T> {
    private final ModelPart lid;
    private final ModelPart bottom;
    private final ModelPart lock;

    public SteelChestRenderer(BlockEntityRendererProvider.Context pContext) {
        ModelPart modelpart = pContext.bakeLayer(ModelLayers.CHEST);
        this.bottom = modelpart.getChild("bottom");
        this.lid = modelpart.getChild("lid");
        this.lock = modelpart.getChild("lock");
    }


    public void render(T pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        Level level = pBlockEntity.getLevel();
        boolean flag = level != null;
        BlockState blockstate = flag ? pBlockEntity.getBlockState() : Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.SOUTH);

        pPoseStack.pushPose();
        float f = blockstate.getValue(ChestBlock.FACING).toYRot();
        pPoseStack.translate(0.5D, 0.5D, 0.5D);
        pPoseStack.mulPose(Vector3f.YP.rotationDegrees(-f));
        pPoseStack.translate(-0.5D, -0.5D, -0.5D);
        DoubleBlockCombiner.NeighborCombineResult<? extends ChestBlockEntity> neighborcombineresult = DoubleBlockCombiner.Combiner::acceptNone;

        float f1 = neighborcombineresult.apply(ChestBlock.opennessCombiner(pBlockEntity)).get(pPartialTick);
        f1 = 1.0F - f1;
        f1 = 1.0F - f1 * f1 * f1;
        int i = neighborcombineresult.apply(new BrightnessCombiner<>()).applyAsInt(pPackedLight);
        Material material = this.getMaterial();
        VertexConsumer vertexconsumer = material.buffer(pBufferSource, RenderType::entityCutout);
        this.render(pPoseStack, vertexconsumer, this.lid, this.lock, this.bottom, f1, i, pPackedOverlay);

        pPoseStack.popPose();

    }

    private void render(PoseStack pPoseStack, VertexConsumer pConsumer, ModelPart pLidPart, ModelPart pLockPart, ModelPart pBottomPart, float pLidAngle, int pPackedLight, int pPackedOverlay) {
        pLidPart.xRot = -(pLidAngle * ((float) Math.PI / 2F));
        pLockPart.xRot = pLidPart.xRot;
        pLidPart.render(pPoseStack, pConsumer, pPackedLight, pPackedOverlay);
        pLockPart.render(pPoseStack, pConsumer, pPackedLight, pPackedOverlay);
        pBottomPart.render(pPoseStack, pConsumer, pPackedLight, pPackedOverlay);
    }

    protected Material getMaterial() {
        return new Material(TextureAtlas.LOCATION_BLOCKS, new ResourceLocation(ThoriumReactors.MOD_ID, "block/steel_chest"));
    }
}
