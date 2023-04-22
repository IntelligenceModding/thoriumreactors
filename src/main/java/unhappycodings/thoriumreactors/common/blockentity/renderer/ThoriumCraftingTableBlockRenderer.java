package unhappycodings.thoriumreactors.common.blockentity.renderer;

import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.common.blockentity.ThoriumCraftingTableBlockEntity;
import unhappycodings.thoriumreactors.common.registration.ModBlocks;
import unhappycodings.thoriumreactors.common.registration.ModItems;

import java.util.List;

public class ThoriumCraftingTableBlockRenderer implements BlockEntityRenderer<ThoriumCraftingTableBlockEntity> {
    public final BlockEntityRendererProvider.Context context;

    public ThoriumCraftingTableBlockRenderer(BlockEntityRendererProvider.Context context) {
        this.context = context;
    }

    @Override
    public void render(@NotNull ThoriumCraftingTableBlockEntity entity, float tick, @NotNull PoseStack stack, @NotNull MultiBufferSource source, int pPackedLight, int pPackedOverlay) {
        stack.pushPose();
        stack.scale(0.1f, 0.1f, 0.1f);
        stack.translate(1.85, 10, 1.85);
        List<ItemStack> stacks = NonNullList.withSize(entity.getContainerSize(), Items.AIR.getDefaultInstance());
        for (int i = 0; i < entity.getContainerSize(); i++) {
            if (!entity.getItem(i).isEmpty())
                stacks.set(i, entity.getItem(i));
        }

        int slot = 0;
        for (int row = 1; row < 6; row++) {
            for (int cell = 1; cell < 6; cell++) {
                if (!stacks.get(slot).is(Items.AIR)) {
                    stack.translate(cell + cell * 0.05, 0, row + row * 0.05);
                    stack.mulPose(Vector3f.XN.rotationDegrees(90));
                    context.getItemRenderer().render(Items.STICK.getDefaultInstance(), ItemTransforms.TransformType.FIXED, false, stack, source, pPackedLight,pPackedOverlay, context.getItemRenderer().getModel(stacks.get(slot), entity.getLevel(), null, 0));
                    stack.mulPose(Vector3f.XN.rotationDegrees(-90));
                    stack.translate(- (cell + cell * 0.05), 0, - (row + row * 0.05));
                }
                slot++;
            }
        }

        ItemStack output = stacks.get(stacks.size() - 1);
        if (!output.is(Items.AIR)) {
            stack.translate(6 + 6 * 0.05, 0, 3 + 3 * 0.05);
            stack.mulPose(Vector3f.XN.rotationDegrees(90));
            context.getItemRenderer().render(Items.STICK.getDefaultInstance(), ItemTransforms.TransformType.FIXED, false, stack, source, pPackedLight,pPackedOverlay, context.getItemRenderer().getModel(output, entity.getLevel(), null, 0));
            stack.mulPose(Vector3f.XN.rotationDegrees(-90));
            stack.translate(- (6 + 6 * 0.05), 0, - (3 + 3 * 0.05));
        }
        stack.popPose();


    }

}
