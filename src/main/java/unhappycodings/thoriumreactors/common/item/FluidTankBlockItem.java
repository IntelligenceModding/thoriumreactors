package unhappycodings.thoriumreactors.common.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.client.renderer.FluidTankItemStackRenderer;
import unhappycodings.thoriumreactors.common.block.tank.FluidTankBlock;
import unhappycodings.thoriumreactors.common.registration.ModBlocks;

import java.util.function.Consumer;

public class FluidTankBlockItem extends BlockItem {
    private final FluidTankBlock block;

    public FluidTankBlockItem(RegistryObject<FluidTankBlock> block) {
        super(block.get(), new Item.Properties());
        this.block = block.get();
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                BlockEntityRenderDispatcher dispatcher = Minecraft.getInstance().getBlockEntityRenderDispatcher();
                EntityModelSet entityModelSet = Minecraft.getInstance().getEntityModels();
                return new FluidTankItemStackRenderer(dispatcher, entityModelSet);
            }
        });
    }

    @NotNull
    @Override
    public Rarity getRarity(@NotNull ItemStack pStack) {
        return block.asItem().getDefaultInstance().is(ModBlocks.CREATIVE_FLUID_TANK.get().asItem()) ? Rarity.EPIC : Rarity.COMMON;
    }
}
