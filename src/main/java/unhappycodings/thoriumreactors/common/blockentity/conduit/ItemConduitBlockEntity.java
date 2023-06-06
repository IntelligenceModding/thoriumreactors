package unhappycodings.thoriumreactors.common.blockentity.conduit;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import unhappycodings.thoriumreactors.common.registration.ModBlockEntities;

public class ItemConduitBlockEntity extends BlockEntity {

    public ItemConduitBlockEntity( BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.ITEM_CONDUIT.get(), pPos, pBlockState);
    }

    public void tick() {
        System.out.println("sss");
    }

}
