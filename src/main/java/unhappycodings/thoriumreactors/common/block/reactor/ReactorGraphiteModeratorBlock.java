package unhappycodings.thoriumreactors.common.block.reactor;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GlassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ReactorGraphiteModeratorBlock extends GlassBlock {

    public static final VoxelShape ALL = Block.box(4, 0, 4, 11.5, 16, 11.5);

    public ReactorGraphiteModeratorBlock() {
        super(Properties.of(Material.STONE));
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return ALL;
    }
}
