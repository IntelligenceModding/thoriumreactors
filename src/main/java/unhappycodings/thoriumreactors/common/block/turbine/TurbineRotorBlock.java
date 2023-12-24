package unhappycodings.thoriumreactors.common.block.turbine;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import unhappycodings.thoriumreactors.common.block.turbine.base.TurbineFrameBlock;
import unhappycodings.thoriumreactors.common.blockentity.turbine.TurbineRotorBlockEntity;
import unhappycodings.thoriumreactors.common.registration.ModItems;

public class TurbineRotorBlock extends TurbineFrameBlock {
    public VoxelShape SHAPE = Block.box(6, 0, 6, 10, 16, 10);
    public static final IntegerProperty BLADES = IntegerProperty.create("blades", 0, 8);
    public static final BooleanProperty RENDERING = BooleanProperty.create("rendering");

    public TurbineRotorBlock() {
        super(Properties.copy(Blocks.IRON_BLOCK).strength(5f));
        this.registerDefaultState(this.stateDefinition.any().setValue(BLADES, 0).setValue(RENDERING, false));
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
        return 10;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(BLADES, 0).setValue(RENDERING, false);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(BLADES, RENDERING);
    }

    @SuppressWarnings("deprecation")
    @NotNull
    @Override
    public RenderShape getRenderShape(@NotNull BlockState state) {
        return state.getValue(RENDERING) ? RenderShape.INVISIBLE : RenderShape.MODEL;
    }

    @SuppressWarnings("deprecation")
    @NotNull
    @Override
    public VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        return SHAPE;
    }

    @SuppressWarnings("deprecation")
    @NotNull
    @Override
    public InteractionResult use(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, Player pPlayer, @NotNull InteractionHand pHand, @NotNull BlockHitResult pHit) {
        if (pLevel.isClientSide) return InteractionResult.SUCCESS;
        if (pPlayer.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.TURBINE_BLADE.get())) {
            int bladesCount = pState.getValue(BLADES);
            if (bladesCount < 8) {
                pLevel.setBlockAndUpdate(pPos, pState.setValue(BLADES, bladesCount + 1));
                if (!pPlayer.isCreative())
                    pPlayer.getItemInHand(InteractionHand.MAIN_HAND).shrink(1);
            }
            return InteractionResult.SUCCESS;
        } else if (pPlayer.getItemInHand(InteractionHand.MAIN_HAND).isEmpty()) {
            int bladesCount = pState.getValue(BLADES);
            if (bladesCount > 0 && pPlayer.canTakeItem(ModItems.TURBINE_BLADE.get().getDefaultInstance())) {
                pLevel.setBlockAndUpdate(pPos, pState.setValue(BLADES, bladesCount - 1));
                if (!pPlayer.isCreative())
                    pPlayer.addItem(ModItems.TURBINE_BLADE.get().getDefaultInstance());
            }
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new TurbineRotorBlockEntity(pos, state);
    }

}
