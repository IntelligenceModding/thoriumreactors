package unhappycodings.thoriumreactors.common.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.client.renderer.WaterSourceItemStackRenderer;
import unhappycodings.thoriumreactors.common.block.WaterSourceBlock;

import java.util.function.Consumer;

public class WaterSourceBlockItem extends BlockItem {

    public WaterSourceBlockItem(RegistryObject<WaterSourceBlock> block) {
        super(block.get(), new Properties().tab(ThoriumReactors.creativeTab));
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                BlockEntityRenderDispatcher dispatcher = Minecraft.getInstance().getBlockEntityRenderDispatcher();
                EntityModelSet entityModelSet = Minecraft.getInstance().getEntityModels();
                return new WaterSourceItemStackRenderer(dispatcher, entityModelSet);
            }
        });
    }

    @NotNull
    @Override
    public Rarity getRarity(@NotNull ItemStack pStack) {
        return Rarity.EPIC;
    }
}
