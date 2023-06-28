package unhappycodings.thoriumreactors.client.renderer.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import unhappycodings.thoriumreactors.ThoriumReactors;

public class TurbineBladeModel<T extends Entity> extends EntityModel<T> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(ThoriumReactors.MOD_ID, "turbine_blades"), "main");
    private final ModelPart bb_main;

    public TurbineBladeModel(ModelPart root) {
        this.bb_main = root.getChild("bb_main");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -16.0F, -2.0F, 4.0F, 16.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        bb_main.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(4, 4).addBox(-1.0F, -0.25F, 2.0F, 2.0F, 0.5F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(16, 0).addBox(-1.25F, -0.5F, 4.0F, 2.5F, 1.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.0F, 0.0F, -2.5251F, 0.4574F, -2.1129F));

        bb_main.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(16, 0).addBox(-1.25F, -0.5F, 4.0F, 2.5F, 1.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(4, 4).addBox(-1.0F, -0.25F, 2.0F, 2.0F, 0.5F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.0F, 0.0F, 0.6165F, -0.4574F, -1.0287F));

        bb_main.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(16, 0).addBox(-1.25F, -0.5F, 4.0F, 2.5F, 1.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(4, 4).addBox(-1.0F, -0.25F, 2.0F, 2.0F, 0.5F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.0F, 0.0F, -1.5708F, 0.6981F, -1.5708F));

        bb_main.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(16, 0).addBox(-1.25F, -0.5F, 4.0F, 2.5F, 1.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(4, 4).addBox(-1.0F, -0.25F, 2.0F, 2.0F, 0.5F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.0F, 0.0F, -0.6109F, 0.4363F, -1.0123F));

        bb_main.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(4, 4).addBox(-1.0F, -0.25F, 2.0F, 2.0F, 0.5F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(16, 0).addBox(-1.25F, -0.5F, 4.0F, 2.5F, 1.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.0F, 0.0F, 0.0F, 0.0F, -0.8727F));

        bb_main.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(16, 0).addBox(-1.25F, -0.5F, 4.0F, 2.5F, 1.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(4, 4).addBox(-1.0F, -0.25F, 2.0F, 2.0F, 0.5F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.0F, 0.0F, 2.5307F, -0.4363F, -2.1293F));

        bb_main.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(4, 4).addBox(-1.0F, -0.25F, 2.0F, 2.0F, 0.5F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(16, 0).addBox(-1.25F, -0.5F, 4.0F, 2.5F, 1.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.0F, 0.0F, -3.1416F, 0.0F, -2.2689F));

        bb_main.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(16, 0).addBox(-1.25F, -0.5F, 4.0F, 2.5F, 1.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(4, 4).addBox(-1.0F, -0.25F, 2.0F, 2.0F, 0.5F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.0F, 0.0F, 1.5708F, -0.6981F, -1.5708F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        bb_main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

}