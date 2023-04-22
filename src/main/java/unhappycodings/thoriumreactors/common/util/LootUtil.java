package unhappycodings.thoriumreactors.common.util;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;

public class LootUtil {

    public static ItemStack getLoot(BaseContainerBlockEntity entity, Block block) {
        ItemStack machineStack = new ItemStack(block.asItem(), 1);
        entity.saveToItem(machineStack);
        if (entity.hasCustomName()) machineStack.setHoverName(entity.getCustomName());
        return machineStack;
    }

}
