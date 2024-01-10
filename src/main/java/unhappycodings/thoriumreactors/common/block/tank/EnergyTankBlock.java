package unhappycodings.thoriumreactors.common.block.tank;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import unhappycodings.thoriumreactors.client.config.ClientConfig;
import unhappycodings.thoriumreactors.common.blockentity.tank.EnergyTankBlockEntity;
import unhappycodings.thoriumreactors.common.registration.ModBlocks;
import unhappycodings.thoriumreactors.common.registration.ModKeyBindings;
import unhappycodings.thoriumreactors.common.util.FormattingUtil;
import unhappycodings.thoriumreactors.common.util.KeyBindingUtil;
import unhappycodings.thoriumreactors.common.util.LootUtil;

import java.util.Collections;
import java.util.List;

public class EnergyTankBlock extends BaseEntityBlock {
    public final RegistryObject<BlockEntityType<EnergyTankBlockEntity>> type;
    public int capacity = 0;

    public EnergyTankBlock(RegistryObject<BlockEntityType<EnergyTankBlockEntity>> type, int capacity) {
        super(Properties.copy(Blocks.GLASS).noOcclusion().sound(SoundType.GLASS).strength(3f));
        this.type = type;
        this.capacity = capacity;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable BlockGetter pLevel, @NotNull List<Component> pTooltip, @NotNull TooltipFlag pFlag) {
        CompoundTag tag = pStack.getOrCreateTag().getCompound("BlockEntityTag");

        if (this.defaultBlockState().is(ModBlocks.CREATIVE_ENERGY_TANK.get()) && tag.getInt("Energy") == Integer.MAX_VALUE) {
            pTooltip.add(Component.translatable(FormattingUtil.getTranslatable("machines.tooltip.energy")).withStyle(FormattingUtil.hex(0x3FD023)).append(Component.translatable(FormattingUtil.getTranslatable("machines.tooltip.infinite")).withStyle(ChatFormatting.GRAY)));
            pTooltip.add(Component.translatable(FormattingUtil.getTranslatable("machines.tooltip.capacity")).withStyle(FormattingUtil.hex(0x3BA3D3)).append(Component.translatable(FormattingUtil.getTranslatable("machines.tooltip.infinite")).withStyle(ChatFormatting.GRAY)));
            return;
        }

        if (tag.get("Energy") == null) {
            pTooltip.add(Component.translatable(FormattingUtil.getTranslatable("machines.tooltip.not_placed")).withStyle(FormattingUtil.hex(0xCE1F0A)));
            return;
        }
        if (KeyBindingUtil.isKeyPressed(ModKeyBindings.SHOW_DETAILS) && ClientConfig.showBlockDetails.get()) {
            int energy = tag.getInt("Energy");
            pTooltip.add(Component.translatable(FormattingUtil.getTranslatable("machines.tooltip.energy")).withStyle(FormattingUtil.hex(0x3FD023)).append(Component.literal(FormattingUtil.formatEnergy(energy)).withStyle(ChatFormatting.GRAY)));
            pTooltip.add(Component.translatable(FormattingUtil.getTranslatable("machines.tooltip.capacity")).withStyle(FormattingUtil.hex(0x3BA3D3)).append(Component.literal(FormattingUtil.formatEnergy(getCapacityForType(pStack))).withStyle(ChatFormatting.GRAY)));

        } else if (KeyBindingUtil.isKeyPressed(ModKeyBindings.SHOW_DESCRIPTION) && ClientConfig.showBlockDescription.get()) {
            pTooltip.add(Component.translatable(asBlock().getDescriptionId() + "_description").withStyle(ChatFormatting.GRAY));
        } else {
            if (ClientConfig.showBlockDetails.get())
                pTooltip.add(Component.translatable(FormattingUtil.getTranslatable("machines.tooltip.hold")).withStyle(ChatFormatting.GRAY).append(Component.literal(ModKeyBindings.SHOW_DETAILS.getKey().getDisplayName().getString()).withStyle(FormattingUtil.hex(0x7ED355))).append(Component.translatable(FormattingUtil.getTranslatable("machines.tooltip.for_details")).withStyle(ChatFormatting.GRAY)));
            if (ClientConfig.showBlockDescription.get())
                pTooltip.add(Component.translatable(FormattingUtil.getTranslatable("machines.tooltip.hold")).withStyle(ChatFormatting.GRAY).append(Component.literal(ModKeyBindings.SHOW_DESCRIPTION.getKey().getDisplayName().getString()).withStyle(FormattingUtil.hex(0x55D38A))).append(Component.translatable(FormattingUtil.getTranslatable("machines.tooltip.for_description")).withStyle(ChatFormatting.GRAY)));
        }
    }

    private int getCapacityForType(ItemStack stack) {
        if (stack.is(ModBlocks.SIMPLE_ENERGY_TANK.get().asItem())) return ModBlocks.SIMPLE_ENERGY_TANK.get().capacity;
        if (stack.is(ModBlocks.GENERIC_ENERGY_TANK.get().asItem())) return ModBlocks.GENERIC_ENERGY_TANK.get().capacity;
        if (stack.is(ModBlocks.PROGRESSIVE_ENERGY_TANK.get().asItem())) return ModBlocks.PROGRESSIVE_ENERGY_TANK.get().capacity;
        else return Integer.MAX_VALUE;
    }

    @SuppressWarnings("deprecation")
    @NotNull
    @Override
    public List<ItemStack> getDrops(@NotNull BlockState pState, LootParams.@NotNull Builder pParams) {
        return Collections.singletonList(LootUtil.getLoot(pParams.getParameter(LootContextParams.BLOCK_ENTITY), this));
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        return LootUtil.getLoot(level.getBlockEntity(pos), this);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return Block.box(2, 0, 2, 14, 16, 14);
    }

    @SuppressWarnings("deprecation")
    @NotNull
    @Override
    public RenderShape getRenderShape(@NotNull BlockState pState) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new EnergyTankBlockEntity(type.get(), pos, state, capacity);
    }

    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState blockState, @NotNull BlockEntityType<T> type) {
        return level.isClientSide ? null : (a, b, c, blockEntity) -> ((EnergyTankBlockEntity) blockEntity).tick();
    }

}
