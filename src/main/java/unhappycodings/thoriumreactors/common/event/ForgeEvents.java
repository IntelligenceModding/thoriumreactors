package unhappycodings.thoriumreactors.common.event;

import de.maxhenkel.pipez.blocks.ItemPipeBlock;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.antlr.v4.runtime.misc.Triple;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.common.block.conduit.ItemConduitBlock;
import unhappycodings.thoriumreactors.common.registration.ModBlocks;

@Mod.EventBusSubscriber(modid = ThoriumReactors.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEvents {

    @SubscribeEvent
    public static void onBlockClick(PlayerInteractEvent.RightClickBlock event) {
        InteractionHand hand = event.getHand();
        Player player = event.getEntity();
        BlockState state = player.level.getBlockState(event.getPos());
        if (hand == InteractionHand.OFF_HAND || player.level.isClientSide || !(state.getBlock() instanceof ItemConduitBlock block) || !player.isShiftKeyDown()) return;

        Triple<VoxelShape, Direction, Double> values = block.getTargetShape(state, player.level, event.getPos(), player);
        if (values.b != null) {
            System.out.println(values.b);
            player.level.setBlock(event.getPos(), state.setValue(ItemConduitBlock.TICKING, !state.getValue(ItemConduitBlock.TICKING)), 3);
        }
    }

}
