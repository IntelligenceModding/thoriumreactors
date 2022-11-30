package unhappycodings.thoriumreactors.common.util;

import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.HashSet;
import java.util.Set;

public class TagUtil {

    public static Set<Block> getValuesOfBlockTag(TagKey<Block> tag) {
        Set<Block> blocks = new HashSet<>();
        Registry.BLOCK.getTagOrEmpty(tag).forEach(element -> blocks.add(element.value()));
        return blocks;
    }

    public static Set<Item> getValuesOfItemTag(TagKey<Item> tag) {
        Set<Item> blocks = new HashSet<>();
        Registry.ITEM.getTagOrEmpty(tag).forEach(element -> blocks.add(element.value()));
        return blocks;
    }
}
