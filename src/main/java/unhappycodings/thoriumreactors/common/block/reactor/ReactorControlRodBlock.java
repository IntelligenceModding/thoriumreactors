package unhappycodings.thoriumreactors.common.block.reactor;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Material;

public class ReactorControlRodBlock extends Block {

    public ReactorControlRodBlock() {
        super(Properties.of(Material.METAL).strength(5f));
    }

}
