package unhappycodings.thoriumreactors.common.block.turbine;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import unhappycodings.thoriumreactors.common.block.turbine.base.TurbineFrameBlock;
import unhappycodings.thoriumreactors.common.blockentity.reactor.ReactorValveBlockEntity;
import unhappycodings.thoriumreactors.common.blockentity.turbine.TurbinePowerPortBlockEntity;

public class TurbinePowerPortBlock extends TurbineFrameBlock {
    public static final DirectionProperty FACING = DirectionalBlock.FACING;

    public TurbinePowerPortBlock() {
        super(Properties.of(Material.METAL).strength(5f));
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new TurbinePowerPortBlockEntity(pos, state);
    }
}
