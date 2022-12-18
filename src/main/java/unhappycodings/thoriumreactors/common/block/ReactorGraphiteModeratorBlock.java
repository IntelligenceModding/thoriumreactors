package unhappycodings.thoriumreactors.common.block;

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

    public static final VoxelShape ALL = Shapes.or(Block.box(7, 0, 7, 9, 16, 9), Block.box(10, 0, 4, 12, 16, 6),
            Block.box(10, 0, 10, 12, 16, 12), Block.box(4, 0, 10, 6, 16, 12), Block.box(4, 0, 4, 6, 16, 6));

    public ReactorGraphiteModeratorBlock() {
        super(Properties.of(Material.STONE));
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return ALL;
    }
}
