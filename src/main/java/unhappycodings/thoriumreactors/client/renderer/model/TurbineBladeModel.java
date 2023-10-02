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
import org.jetbrains.annotations.NotNull;
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

        PartDefinition cube_r1 = bb_main.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(6, 6).addBox(-23.25F, -2.0875F, -0.6F, 0.25F, 4.175F, 1.2F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.0088F, 0.0088F, -0.7854F, 0.7854F, 0.0F));

        PartDefinition cube_r2 = bb_main.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(6, 6).addBox(-23.25F, -2.0875F, -0.6F, 0.25F, 4.175F, 1.2F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.0088F, 0.0088F, 2.3562F, -0.7854F, -3.1416F));

        PartDefinition cube_r3 = bb_main.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(6, 6).addBox(-23.25F, -2.0875F, -0.6F, 0.25F, 4.175F, 1.2F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.0088F, 0.0088F, 0.0F, 1.5708F, 0.7854F));

        PartDefinition cube_r4 = bb_main.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(6, 6).addBox(-23.25F, -2.0875F, -0.6F, 0.25F, 4.175F, 1.2F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.0088F, 0.0088F, 0.0F, -1.5708F, -0.7854F));

        PartDefinition cube_r5 = bb_main.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(6, 6).addBox(-23.25F, -2.0875F, -0.6F, 0.25F, 4.175F, 1.2F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.0088F, 0.0088F, 2.3562F, 0.7854F, -3.1416F));

        PartDefinition cube_r6 = bb_main.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(6, 6).addBox(-23.25F, -2.0875F, -0.6F, 0.25F, 4.175F, 1.2F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.0088F, 0.0088F, -0.7854F, -0.7854F, 0.0F));

        PartDefinition cube_r7 = bb_main.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(6, 6).addBox(-23.25F, -2.0875F, -0.6F, 0.25F, 4.175F, 1.2F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.0088F, 0.0088F, 2.3562F, 0.0F, -3.1416F));

        PartDefinition cube_r8 = bb_main.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(6, 6).addBox(-23.25F, -2.0875F, -0.6F, 0.25F, 4.175F, 1.2F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.0088F, 0.0088F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r9 = bb_main.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(24, 0).addBox(-0.5F, -2.0F, 4.0F, 1.0F, 4.0F, 19.0F, new CubeDeformation(0.0F))
                .texOffs(6, 4).addBox(-0.25F, -1.0F, 2.0F, 0.5F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.0F, 0.0F, -0.6155F, -0.5236F, 0.9553F));

        PartDefinition cube_r10 = bb_main.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(24, 0).addBox(-0.5F, -2.0F, -23.0F, 1.0F, 4.0F, 19.0F, new CubeDeformation(0.0F))
                .texOffs(6, 4).addBox(-0.25F, -1.0F, -4.0F, 0.5F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.0F, 0.0F, 0.6155F, -0.5236F, -0.9553F));

        PartDefinition cube_r11 = bb_main.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(24, 18).addBox(-23.0F, -2.0F, -0.5F, 19.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 4).addBox(-4.0F, -1.0F, -0.25F, 2.0F, 2.0F, 0.5F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.0F, 0.0F, -0.7854F, -0.7854F, 0.0F));

        PartDefinition cube_r12 = bb_main.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(6, 4).addBox(2.0F, -1.0F, -0.25F, 2.0F, 2.0F, 0.5F, new CubeDeformation(0.0F))
                .texOffs(24, 18).addBox(4.0F, -2.0F, -0.5F, 19.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.0F, 0.0F, 0.7854F, -0.7854F, 0.0F));

        PartDefinition cube_r13 = bb_main.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(24, 18).addBox(4.0F, -2.0F, -0.5F, 19.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 4).addBox(2.0F, -1.0F, -0.25F, 2.0F, 2.0F, 0.5F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r14 = bb_main.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(24, 0).addBox(-0.5F, -2.0F, -15.5F, 1.0F, 4.0F, 19.0F, new CubeDeformation(0.0F))
                .texOffs(6, 4).addBox(-0.25F, -1.0F, 3.5F, 0.5F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.0F, -7.5F, 0.0F, 0.0F, -0.7854F));

        PartDefinition cube_r15 = bb_main.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(24, 18).addBox(-23.0F, -2.0F, -0.5F, 19.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 4).addBox(-4.0F, -1.0F, -0.25F, 2.0F, 2.0F, 0.5F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r16 = bb_main.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(6, 4).addBox(-0.25F, -1.0F, 2.0F, 0.5F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(24, 0).addBox(-0.5F, -2.0F, 4.0F, 1.0F, 4.0F, 19.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(@NotNull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        bb_main.render(poseStack, vertexConsumer, packedLight, packedOverlay);
    }

}