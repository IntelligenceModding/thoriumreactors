package unhappycodings.thoriumreactors.common.block.turbine;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import unhappycodings.thoriumreactors.common.block.turbine.base.TurbineFrameBlock;
import unhappycodings.thoriumreactors.common.blockentity.turbine.TurbineCasingBlockEntity;
import unhappycodings.thoriumreactors.common.blockentity.turbine.TurbineControllerBlockEntity;
import unhappycodings.thoriumreactors.common.blockentity.turbine.base.TurbineFrameBlockEntity;
import unhappycodings.thoriumreactors.common.registration.ModKeyBindings;
import unhappycodings.thoriumreactors.common.util.FormattingUtil;

import java.util.List;

public class TurbineCasingBlock extends TurbineFrameBlock {

    public TurbineCasingBlock() {
        super(Properties.of(Material.METAL).strength(5f));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new TurbineCasingBlockEntity(pos, state);
    }

}
