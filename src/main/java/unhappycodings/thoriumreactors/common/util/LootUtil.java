package unhappycodings.thoriumreactors.common.util;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;

public class LootUtil {

    public static ItemStack getLoot(BlockEntity entity, Block block) {
        ItemStack machineStack = new ItemStack(block.asItem(), 1);
        entity.saveToItem(machineStack);
        if (entity instanceof BaseContainerBlockEntity blockEntity)
            if (blockEntity.hasCustomName()) machineStack.setHoverName(blockEntity.getCustomName());
        return machineStack;
    }

}
