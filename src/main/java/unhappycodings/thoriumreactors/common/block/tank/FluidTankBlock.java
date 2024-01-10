package unhappycodings.thoriumreactors.common.block.tank;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.LavaFluid;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.WaterFluid;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import unhappycodings.thoriumreactors.client.config.ClientConfig;
import unhappycodings.thoriumreactors.common.blockentity.tank.FluidTankBlockEntity;
import unhappycodings.thoriumreactors.common.registration.ModBlocks;
import unhappycodings.thoriumreactors.common.registration.ModKeyBindings;
import unhappycodings.thoriumreactors.common.util.FormattingUtil;
import unhappycodings.thoriumreactors.common.util.KeyBindingUtil;
import unhappycodings.thoriumreactors.common.util.LootUtil;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class FluidTankBlock extends BaseEntityBlock {
    public int capacity = 0;
    public final RegistryObject<BlockEntityType<FluidTankBlockEntity>> type;

    public FluidTankBlock(int capacity, RegistryObject<BlockEntityType<FluidTankBlockEntity>> type) {
        super(BlockBehaviour.Properties.of(Material.GLASS).noOcclusion().sound(SoundType.GLASS).strength(3f));
        this.capacity = capacity;
        this.type = type;
    }

    @SuppressWarnings("deprecation")
    @NotNull
    @Override
    public InteractionResult use(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull Player pPlayer, @NotNull InteractionHand pHand, @NotNull BlockHitResult pHit) {
        FluidTankBlockEntity entity = (FluidTankBlockEntity) pLevel.getBlockEntity(pPos);
        if (pHand == InteractionHand.OFF_HAND) return InteractionResult.FAIL;
        ItemStack playerHandStack = pPlayer.getItemInHand(InteractionHand.MAIN_HAND);

        if (!pPlayer.getItemInHand(InteractionHand.MAIN_HAND).isEmpty() && playerHandStack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).isPresent()) {
            playerHandStack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).ifPresent(storage -> {
                if (!storage.getFluidInTank(0).isEmpty()) {
                    FluidStack playerFluidStack = storage.getFluidInTank(0);
                    if (entity.getFluidIn().isEmpty() || entity.getFluidIn().isFluidEqual(playerFluidStack)) {
                        int amount = entity.getFluidTank().fill(playerFluidStack, IFluidHandler.FluidAction.SIMULATE);
                        FluidStack itemAmount = storage.drain(amount, IFluidHandler.FluidAction.SIMULATE);
                        if (itemAmount.getAmount() > 0) {
                            entity.getFluidTank().fill(itemAmount, IFluidHandler.FluidAction.EXECUTE);
                            if (!pPlayer.isCreative()) {
                                storage.drain(itemAmount, IFluidHandler.FluidAction.EXECUTE);
                                if (storage.getFluidInTank(0).isEmpty() && playerHandStack.hasCraftingRemainingItem()) {
                                    pPlayer.setItemInHand(InteractionHand.MAIN_HAND, playerHandStack.getCraftingRemainingItem());
                                }
                            }
                            pLevel.playSound(null, pPos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1, 1);
                        }

                    }
                } else {
                    int playerFluidStack = entity.getFluidAmountIn();
                    FluidStack amount = entity.getFluidTank().drain(playerFluidStack, IFluidHandler.FluidAction.SIMULATE);
                    int itemAmount = storage.fill(amount, IFluidHandler.FluidAction.SIMULATE);
                    if (itemAmount > 0) {
                        if (playerHandStack.is(Items.BUCKET) && itemAmount >= 1000) {
                            if (!pPlayer.isCreative())
                                pPlayer.setItemInHand(InteractionHand.MAIN_HAND, entity.getFluidIn().getFluid().getBucket().getDefaultInstance());
                            entity.getFluidTank().drain(1000, IFluidHandler.FluidAction.EXECUTE);
                        } else {
                            storage.fill(new FluidStack(entity.getFluidIn(), itemAmount), IFluidHandler.FluidAction.EXECUTE);
                            entity.getFluidTank().drain(itemAmount, IFluidHandler.FluidAction.EXECUTE);
                        }
                        pLevel.playSound(null, pPos, SoundEvents.BUCKET_FILL, SoundSource.BLOCKS, 1, 1);
                    }
                }
            });
            return InteractionResult.SUCCESS;
        }

        MenuProvider namedContainerProvider = this.getMenuProvider(pState, pLevel, pPos);
        if (namedContainerProvider != null) {
            if (pPlayer instanceof ServerPlayer serverPlayerEntity)
                NetworkHooks.openScreen(serverPlayerEntity, namedContainerProvider, pPos);
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.SUCCESS;
    }

    @SuppressWarnings("deprecation")
    @NotNull
    @Override
    public List<ItemStack> getDrops(@NotNull BlockState pState, LootContext.Builder pBuilder) {
        return Collections.singletonList(LootUtil.getLoot(pBuilder.getParameter(LootContextParams.BLOCK_ENTITY), this));
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        return LootUtil.getLoot(level.getBlockEntity(pos), this);
    }

    @Override
    public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> items) {
        super.fillItemCategory(tab, items);
        if (tab == CreativeModeTab.TAB_SEARCH && this.asItem().getDefaultInstance().is(ModBlocks.CREATIVE_FLUID_TANK.get().asItem())) {
            int index = items.size();

            for (Fluid fluid : getKnownFluids()) {
                if (fluid instanceof ForgeFlowingFluid.Source || fluid instanceof LavaFluid.Source || fluid instanceof WaterFluid.Source) {
                    ItemStack blockStack = ModBlocks.CREATIVE_FLUID_TANK.get().asItem().getDefaultInstance();

                    FluidStack stack = new FluidStack(fluid, Integer.MAX_VALUE);
                    blockStack.getOrCreateTag().put("BlockEntityTag", writeToNBT(stack));

                    if (!items.contains(blockStack)) {
                        items.add(index, blockStack);
                        index++;
                    }
                }
            }
        }
    }

    public CompoundTag writeToNBT(FluidStack fluidStack) {
        CompoundTag dataTag = new CompoundTag();
        CompoundTag fluidTag = new CompoundTag();
        dataTag.putString("FluidName", ForgeRegistries.FLUIDS.getKey(fluidStack.getFluid()).toString());
        dataTag.putInt("Amount", fluidStack.getAmount());

        fluidTag.put("Fluid", dataTag);
        return fluidTag;
    }

    @NotNull
    protected Iterable<Fluid> getKnownFluids() {
        return ForgeRegistries.FLUIDS.getEntries().stream().map(Map.Entry::getValue)::iterator;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable BlockGetter pLevel, @NotNull List<Component> pTooltip, @NotNull TooltipFlag pFlag) {
        CompoundTag tag = pStack.getOrCreateTag().getCompound("BlockEntityTag");

        if (this.defaultBlockState().is(ModBlocks.CREATIVE_FLUID_TANK.get())) {
            FluidStack fluidIn = FluidStack.loadFluidStackFromNBT(tag.getCompound("Fluid"));
            pTooltip.add(Component.literal(fluidIn.getFluid().getFluidType().getDescription().getString() + ": ").withStyle(FormattingUtil.hex(0x0ACECE)).append(Component.translatable(FormattingUtil.getTranslatable("machines.tooltip.infinite")).withStyle(ChatFormatting.GRAY)));
            pTooltip.add(Component.translatable(FormattingUtil.getTranslatable("machines.tooltip.capacity")).withStyle(FormattingUtil.hex(0x3BA3D3)).append(Component.translatable(FormattingUtil.getTranslatable("machines.tooltip.infinite")).withStyle(ChatFormatting.GRAY)));
            return;
        }

        if (tag.get("Fluid") == null) {
            pTooltip.add(Component.translatable(FormattingUtil.getTranslatable("machines.tooltip.not_placed")).withStyle(FormattingUtil.hex(0xCE1F0A)));
            return;
        }
        if (KeyBindingUtil.isKeyPressed(ModKeyBindings.SHOW_DETAILS) && ClientConfig.showBlockDetails.get()) {
            if (!tag.getCompound("Fluid").isEmpty()) {
                FluidStack fluidIn = FluidStack.loadFluidStackFromNBT(tag.getCompound("Fluid"));
                pTooltip.add(Component.translatable(FormattingUtil.getTranslatable("machines.tooltip.capacity")).withStyle(FormattingUtil.hex(0x3BA3D3)).append(Component.literal(getTankCapacity(pStack) + "").withStyle(ChatFormatting.GRAY)).append(Component.literal(" mB").withStyle(FormattingUtil.hex(0x3BA3D3))));
                pTooltip.add(fluidIn.isEmpty() ? Component.translatable(FormattingUtil.getTranslatable("machines.tooltip.empty")).withStyle(FormattingUtil.hex(0x0ACECE)) : Component.literal(fluidIn.getFluid().getFluidType().getDescription().getString() + ": ").withStyle(FormattingUtil.hex(0x0ACECE)).append(Component.literal(fluidIn.getAmount() + " ").withStyle(ChatFormatting.GRAY)).append(Component.literal("mB").withStyle(FormattingUtil.hex(0x0ACECE))));
            }
        } else if (KeyBindingUtil.isKeyPressed(ModKeyBindings.SHOW_DESCRIPTION) && ClientConfig.showBlockDescription.get()) {
            pTooltip.add(Component.translatable(asBlock().getDescriptionId() + "_description").withStyle(ChatFormatting.GRAY));
        } else {
            if (ClientConfig.showBlockDetails.get())
                pTooltip.add(Component.translatable(FormattingUtil.getTranslatable("machines.tooltip.hold")).withStyle(ChatFormatting.GRAY).append(Component.literal(ModKeyBindings.SHOW_DETAILS.getKey().getDisplayName().getString()).withStyle(FormattingUtil.hex(0x7ED355))).append(Component.translatable(FormattingUtil.getTranslatable("machines.tooltip.for_details")).withStyle(ChatFormatting.GRAY)));
            if (ClientConfig.showBlockDescription.get())
                pTooltip.add(Component.translatable(FormattingUtil.getTranslatable("machines.tooltip.hold")).withStyle(ChatFormatting.GRAY).append(Component.literal(ModKeyBindings.SHOW_DESCRIPTION.getKey().getDisplayName().getString()).withStyle(FormattingUtil.hex(0x55D38A))).append(Component.translatable(FormattingUtil.getTranslatable("machines.tooltip.for_description")).withStyle(ChatFormatting.GRAY)));
        }
    }

    public int getTankCapacity(ItemStack stack) {
        if (stack.is(ModBlocks.SIMPLE_FLUID_TANK.get().asItem())) return ModBlocks.SIMPLE_FLUID_TANK.get().capacity;
        if (stack.is(ModBlocks.GENERIC_FLUID_TANK.get().asItem())) return ModBlocks.GENERIC_FLUID_TANK.get().capacity;
        return ModBlocks.PROGRESSIVE_FLUID_TANK.get().capacity;
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
        return new FluidTankBlockEntity(pos, state, capacity, type.get());
    }

    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState blockState, @NotNull BlockEntityType<T> type) {
        return level.isClientSide ? null : (a, b, c, blockEntity) -> ((FluidTankBlockEntity) blockEntity).tick();
    }

}
