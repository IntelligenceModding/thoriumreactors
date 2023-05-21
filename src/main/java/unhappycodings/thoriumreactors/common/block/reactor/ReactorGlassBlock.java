package unhappycodings.thoriumreactors.common.block.reactor;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.GlassBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

public class ReactorGlassBlock extends GlassBlock {

    public ReactorGlassBlock() {
        super(BlockBehaviour.Properties.of(Material.GLASS).noOcclusion().sound(SoundType.GLASS).strength(3f));
    }

    public boolean propagatesSkylightDown(BlockState p_154824_, BlockGetter p_154825_, BlockPos p_154826_) {
        return true;
    }

}
