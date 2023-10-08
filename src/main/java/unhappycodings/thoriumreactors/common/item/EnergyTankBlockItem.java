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
import unhappycodings.thoriumreactors.client.renderer.EnergyTankItemStackRenderer;
import unhappycodings.thoriumreactors.common.block.tank.EnergyTankBlock;

import java.util.function.Consumer;

public class EnergyTankBlockItem extends BlockItem {
    private final EnergyTankBlock block;

    public EnergyTankBlockItem(RegistryObject<EnergyTankBlock> block) {
        super(block.get(), new Properties().tab(ThoriumReactors.resourcesCreativeTab));
        this.block = block.get();
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                BlockEntityRenderDispatcher dispatcher = Minecraft.getInstance().getBlockEntityRenderDispatcher();
                EntityModelSet entityModelSet = Minecraft.getInstance().getEntityModels();
                return new EnergyTankItemStackRenderer(dispatcher, entityModelSet);
            }
        });
    }

    @NotNull
    @Override
    public Rarity getRarity(@NotNull ItemStack pStack) {
        return Rarity.COMMON;
    }
}
