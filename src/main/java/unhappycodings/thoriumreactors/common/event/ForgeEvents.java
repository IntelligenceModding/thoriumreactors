package unhappycodings.thoriumreactors.common.event;

import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ModIdArgument;
import org.antlr.v4.runtime.misc.Triple;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.common.block.conduit.ItemConduitBlock;
import unhappycodings.thoriumreactors.common.blockentity.conduit.ItemConduitBlockEntity;
import unhappycodings.thoriumreactors.common.enums.DirectionConfiguration;
import unhappycodings.thoriumreactors.common.registration.ModItems;

@Mod.EventBusSubscriber(modid = ThoriumReactors.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEvents {

    @SubscribeEvent
    public static void onBlockClick(PlayerInteractEvent.RightClickBlock event) {
        InteractionHand hand = event.getHand();
        Player player = event.getEntity();
        Level level = event.getLevel();
        BlockState state = player.level.getBlockState(event.getPos());
        if (player.isShiftKeyDown()) player.swing(InteractionHand.MAIN_HAND);
        if (hand == InteractionHand.OFF_HAND || player.level.isClientSide || !(player.level.getBlockEntity(event.getPos()) instanceof ItemConduitBlockEntity blockEntity) || !player.isShiftKeyDown() || !player.isHolding(ModItems.CONFIGURATOR.get())) return;
        ItemConduitBlock block = (ItemConduitBlock) state.getBlock();
        Triple<VoxelShape, Direction, Double> values = block.getTargetShape(state, player.level, event.getPos(), player);
        if (values.b != null) {
            if (!blockEntity.getSideExtracting(values.b) && !blockEntity.getSideDisabled(values.b)) {
                player.level.setBlock(event.getPos(), state.setValue(ItemConduitBlock.TICKING, true), 3);
                blockEntity.setSideExtracting(values.b, true);
                level.setBlockAndUpdate(event.getPos(), state.setValue(ItemConduitBlock.getProperty(values.b.toString()), DirectionConfiguration.OUTPUT));
            } else if (!blockEntity.getSideDisabled(values.b)) {
                blockEntity.setSideExtracting(values.b, false);
                blockEntity.setSideDisabled(values.b, true);
                level.setBlockAndUpdate(event.getPos(), state.setValue(ItemConduitBlock.getProperty(values.b.toString()), DirectionConfiguration.DISABLED));
            } else {
                blockEntity.setSideDisabled(values.b, false);
                player.level.setBlock(event.getPos(), state.setValue(ItemConduitBlock.TICKING, false), 3);
                level.setBlockAndUpdate(event.getPos(), state.setValue(ItemConduitBlock.getProperty(values.b.toString()), DirectionConfiguration.STANDARD));
            }
        }
    }
}
