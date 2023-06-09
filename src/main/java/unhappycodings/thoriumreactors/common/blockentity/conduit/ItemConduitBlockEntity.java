package unhappycodings.thoriumreactors.common.blockentity.conduit;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.common.registration.ModBlockEntities;
import unhappycodings.thoriumreactors.common.util.FormattingUtil;

public class ItemConduitBlockEntity extends BlockEntity {
    protected boolean[] sidesExtract = new boolean[6];
    protected boolean[] sidesDisabled = new boolean[6];

    public ItemConduitBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.ITEM_CONDUIT.get(), pPos, pBlockState);
    }

    public void tick() {
        System.out.println("ticking");
    }

    @NotNull
    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag nbt = new CompoundTag();

        ListTag extractSides = new ListTag();
        for (boolean side : sidesExtract) extractSides.add(ByteTag.valueOf(side));
        nbt.put("Extract", extractSides);

        ListTag disabledSides = new ListTag();
        for (boolean side : sidesDisabled) disabledSides.add(ByteTag.valueOf(side));
        nbt.put("Disabled", disabledSides);
        return nbt;
    }

    @Override
    public void handleUpdateTag(final CompoundTag tag) {
        sidesExtract = new boolean[Direction.values().length];
        ListTag extractingList = tag.getList("Extract", Tag.TAG_BYTE);
        if (extractingList.size() >= sidesExtract.length) {
            for (int i = 0; i < sidesExtract.length; i++) {
                ByteTag b = (ByteTag) extractingList.get(i);
                sidesExtract[i] = b.getAsByte() != 0;
            }
        }

        sidesDisabled = new boolean[Direction.values().length];
        ListTag disabledList = tag.getList("Disabled", Tag.TAG_BYTE);
        if (disabledList.size() >= sidesDisabled.length) {
            for (int i = 0; i < sidesDisabled.length; i++) {
                ByteTag b = (ByteTag) disabledList.get(i);
                sidesDisabled[i] = b.getAsByte() != 0;
            }
        }
    }

    @Override
    public void saveAdditional(@NotNull CompoundTag nbt) {
        ListTag extractSides = new ListTag();
        for (boolean side : sidesExtract) extractSides.add(ByteTag.valueOf(side));
        nbt.put("Extract", extractSides);

        ListTag disabledSides = new ListTag();
        for (boolean side : sidesDisabled) disabledSides.add(ByteTag.valueOf(side));
        nbt.put("Disabled", disabledSides);
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        sidesExtract = new boolean[Direction.values().length];
        ListTag extractingList = tag.getList("Extract", Tag.TAG_BYTE);
        if (extractingList.size() >= sidesExtract.length) {
            for (int i = 0; i < sidesExtract.length; i++) {
                ByteTag b = (ByteTag) extractingList.get(i);
                sidesExtract[i] = b.getAsByte() != 0;
            }
        }

        sidesDisabled = new boolean[Direction.values().length];
        ListTag disabledList = tag.getList("Disabled", Tag.TAG_BYTE);
        if (disabledList.size() >= sidesDisabled.length) {
            for (int i = 0; i < sidesDisabled.length; i++) {
                ByteTag b = (ByteTag) disabledList.get(i);
                sidesDisabled[i] = b.getAsByte() != 0;
            }
        }
    }

    public void setSideExtracting(Direction direction, boolean state) {
        sidesExtract[FormattingUtil.getDirectionIndex(direction)] = state;
    }

    public void setSideDisabled(Direction direction, boolean state) {
        sidesDisabled[FormattingUtil.getDirectionIndex(direction)] = state;
    }

    public boolean getSideExtracting(Direction direction) {
        return sidesExtract[FormattingUtil.getDirectionIndex(direction)];
    }

    public boolean getSideDisabled(Direction direction) {
        return sidesDisabled[FormattingUtil.getDirectionIndex(direction)];
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public void syncData(ServerPlayer player) {
        player.connection.send(getUpdatePacket());
    }

    public void syncData() {
        if (level == null || level.isClientSide) {
            return;
        }
        LevelChunk chunk = level.getChunkAt(getBlockPos());
        ((ServerChunkCache) level.getChunkSource()).chunkMap.getPlayers(chunk.getPos(), false).forEach(e -> e.connection.send(getUpdatePacket()));
    }

}
