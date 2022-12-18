package unhappycodings.thoriumreactors.common.block;

import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.Nullable;

public class ReactorCoreBlock extends Block {
    public static final BooleanProperty HEATING = BooleanProperty.create("heating");
    public static final BooleanProperty FUELED = BooleanProperty.create("fueled");

    public ReactorCoreBlock() {
        super(Properties.of(Material.METAL));
        this.registerDefaultState(this.stateDefinition.any().setValue(HEATING, false).setValue(FUELED, false));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(HEATING, false).setValue(FUELED, false);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(HEATING, FUELED);
    }

}
