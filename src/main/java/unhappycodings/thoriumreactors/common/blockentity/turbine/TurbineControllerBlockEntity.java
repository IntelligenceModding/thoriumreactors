package unhappycodings.thoriumreactors.common.blockentity.turbine;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import unhappycodings.thoriumreactors.common.block.turbine.TurbineControllerBlock;
import unhappycodings.thoriumreactors.common.block.turbine.TurbineRotorBlock;
import unhappycodings.thoriumreactors.common.blockentity.reactor.ReactorValveBlockEntity;
import unhappycodings.thoriumreactors.common.enums.ReactorParticleTypeEnum;
import unhappycodings.thoriumreactors.common.enums.ReactorStateEnum;
import unhappycodings.thoriumreactors.common.network.PacketHandler;
import unhappycodings.thoriumreactors.common.network.toclient.reactor.ClientReactorParticleDataPacket;
import unhappycodings.thoriumreactors.common.registration.*;
import unhappycodings.thoriumreactors.common.util.CalculationUtil;

import java.util.ArrayList;
import java.util.List;

public class TurbineControllerBlockEntity extends BlockEntity implements MenuProvider {
    public List<BlockPos> framePosList = new ArrayList<>();
    public BlockPos floorPosA, floorPosB, floorPosARight, floorPosBLeft;
    public BlockPos ceilingPosA, ceilingPosB, ceilingPosARight, ceilingPosBLeft;
    public int pillarAHeight, pillarARightHeight, pillarBHeight, pillarBLeftHeight;
    public int floorPosLength, floorPosClockwise, floorPosCounterClockwise;
    public int turbineXLenght, turbineYLenght;
    public List<BlockPos> rotorPos;
    public List<BlockPos> valvePos;
    public BlockPos rotationMountPosCeiling;
    public BlockPos rotationMountPosFloor;
    public boolean canBeAssembled;
    public boolean assembled;
    public String warning = "";
    public String notification = "";
    int controllers = 0;

    public TurbineControllerBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.TURBINE_CONTROLLER.get(), pPos, pBlockState);
    }

    public void tick() {
        BlockPos controllerPos = this.getBlockPos();

        if (getBlockState().getValue(TurbineControllerBlock.POWERED) != assembled)
            getLevel().setBlock(getBlockPos(), getBlockState().setValue(TurbineControllerBlock.POWERED, assembled), 3);
        if (isCasing(getState(controllerPos.below())) && level.getGameTime() % 10 == 0) {
            Direction controllerDirection = this.getBlockState().getValue(TurbineControllerBlock.FACING).getOpposite();

            // floor check if square, casings and in min and max sizes
            updateFloorPositions(controllerPos, controllerDirection);
            if (floorPosA == BlockPos.ZERO || floorPosB == BlockPos.ZERO) return;
            if (!canBeAssembled) { resetAssembled(); return;  }

            // Pillar check for all 4 corners
            updatePillarPositions(controllerDirection, floorPosA, floorPosB);
            if (!canBeAssembled) { resetAssembled(); return;  }

            // Ceiling check, if surrounded by casings with rod in mid
            updateCeilingPositions();
            if (!canBeAssembled) { resetAssembled(); return;  }

            // Inner check, if graphite moderators and core are present
            updateInnerPositions();
            if (!canBeAssembled) { resetAssembled(); return;  }

            // Inner check, if graphite moderators and core are present
            updateWallPositions();
            if (!canBeAssembled) { resetAssembled(); return;  }

            // If everything is assembled right, we continue here
            if (!assembled) {
                for (Player player : level.players()) {
                    PacketHandler.sendToClient(new ClientReactorParticleDataPacket(addParticleOffset(getBlockPos(), getBlockState().getValue(TurbineControllerBlock.FACING)), ReactorParticleTypeEnum.REACTOR, turbineXLenght, getTurbineHeight() + 1, turbineYLenght), (ServerPlayer) player);
                }
            }

            setTurbineRotorRendering(true);
            assembled = true;
        }

        if (isAssembled()) {

        }
    }

    public void setTurbineRotorRendering(boolean state) {
        if (rotorPos == null || rotorPos.isEmpty()) return;
        for (BlockPos blockPos : rotorPos) {
            if (level.getBlockState(blockPos).is(ModBlocks.TURBINE_ROTOR.get()) && level.getBlockState(blockPos).getValue(TurbineRotorBlock.RENDERING) != state)
                level.setBlockAndUpdate(blockPos, level.getBlockState(blockPos).setValue(TurbineRotorBlock.RENDERING, state));
        }
    }

    public void updateInnerPositions() {
        int rotorCount = pillarAHeight - 1;
        rotorPos = new ArrayList<>(rotorCount);

        for (int i = 0; i < getTurbineHeight() - 1; i++) {
            BlockPos pos = rotationMountPosFloor.relative(Direction.UP, i + 1);
            if (!getState(pos).is(ModBlocks.TURBINE_ROTOR.get())) { resetAssembled("Turbine rotation mounts have to be connected with turbine rotors. Block at " + pos); return; }
            if (getState(pos).getValue(TurbineRotorBlock.BLADES) != 8 && i < getTurbineHeight() - 4) { resetAssembled("Turbine rotors need to have 4 blades each, except the top three. Block at " + pos); return; }
            rotorPos.add(pos);
        }
    }

    public void updateWallPositions() {
        valvePos = new ArrayList<>();
        List<BlockPos> blockListBack = CalculationUtil.getBlockStates(floorPosA, ceilingPosARight, level); // Back
        List<BlockPos> blockListLeft = CalculationUtil.getBlockStates(floorPosBLeft, ceilingPosA, level); // Left
        List<BlockPos> blockListRight = CalculationUtil.getBlockStates(floorPosB, ceilingPosARight, level); // Right
        List<BlockPos> blockListFront = CalculationUtil.getBlockStates(floorPosBLeft, ceilingPosB, level); // Front

        checkWallArea(blockListBack);
        checkWallArea(blockListLeft);
        checkWallArea(blockListRight);
        checkWallArea(blockListFront);

        controllers = 0;
        if (valvePos.size() != 1) {
            resetAssembled("Turbine is in need of one valve. Currently " + valvePos.size());
        }
    }

    public void checkWallArea(List<BlockPos> blockPosList) {
        for (BlockPos pos : blockPosList) {
            if (level.getBlockState(pos).is(ModBlocks.TURBINE_CONTROLLER_BLOCK.get())) {
                controllers++;
                if (controllers > 1) resetAssembled("Turbine cannot have two controller blocks! Block at " + pos);
            }
            if (!(isCasing(getState(pos)) || isGlass(getState(pos)) || isValve(getState(pos)) || getState(pos).is(getState(getBlockPos()).getBlock()))) {
                resetAssembled("Missing turbine casing, glass or valve in wall at " + pos);
            }
            if (isValve(getState(pos))) {
                if (!(level.getBlockEntity(pos) instanceof TurbineValveBlockEntity entity)) return;
                valvePos.add(pos);
                entity.setTurbineCorePosition(this.getBlockPos());
            }
        }
    }

    public void updateCeilingPositions() {
        ceilingPosA = floorPosA.offset(0, pillarAHeight, 0);
        ceilingPosB = floorPosB.offset(0, pillarBHeight, 0);
        ceilingPosARight = floorPosARight.offset(0, pillarARightHeight, 0);
        ceilingPosBLeft = floorPosBLeft.offset(0, pillarBLeftHeight, 0);
        framePosList = new ArrayList<>();
        rotationMountPosCeiling = null;

        // Checking ceiling frame for casings
        List<List<BlockPos>> positionList = List.of(List.of(ceilingPosA, ceilingPosARight), List.of(ceilingPosARight, ceilingPosB), List.of(ceilingPosB, ceilingPosBLeft), List.of(ceilingPosBLeft, ceilingPosA));
        for (List<BlockPos> allBlockList : positionList) {
            List<BlockPos> blockPosList = CalculationUtil.getBlockStates(allBlockList.get(0), allBlockList.get(1), level);
            for (BlockPos pos : blockPosList) {
                if (!isCasing(getState(pos))) {
                    resetAssembled("Missing turbine casing in ceiling frame at position " + pos);
                    return;
                }
                if (!framePosList.contains(pos))
                    framePosList.add(pos);
            }
        }

        List<BlockPos> ceilingWall = CalculationUtil.getBlockStates(ceilingPosA, ceilingPosB, level);
        for (BlockPos pos : framePosList)
            if (ceilingWall.contains(pos))
                ceilingWall.remove(pos);

        List<BlockState> glassStates = new ArrayList<>();
        List<BlockState> casingStates = new ArrayList<>();
        for (BlockPos pos : ceilingWall) {
            if (isRotationMount(getState(pos))) {
                rotationMountPosCeiling = pos;
            } else if (isGlass(getState(pos))) {
                glassStates.add(getState(pos));
            } else if (isCasing(getState(pos))) {
                casingStates.add(getState(pos));
            }
        }

        if (rotationMountPosCeiling == null || !hasFrameConnection(rotationMountPosCeiling))
            resetAssembled("Missing turbine rotation mount in ceiling or no connection to frame with casings");

        if (glassStates.size() + casingStates.size() + 1 != ceilingWall.size())
            resetAssembled("Missing turbine casing or glass in ceiling!");

    }

    public boolean hasFrameConnection(BlockPos pos) {
        boolean exit = false;
        int runs = 0;
        BlockPos current = pos;
        while (!exit) {
            for (Direction value : Direction.values()) {
                if (getState(current.relative(value)).is(ModBlocks.TURBINE_CASING.get())) {
                    current = current.relative(value);
                    if (framePosList.contains(current.relative(value)))
                        exit = true;
                }
                if (runs > 64)
                    exit = true;
                runs++;
            }
        }
        return exit && runs <= 64;
    }

    public void updatePillarPositions(Direction controllerDirection, BlockPos floorPosA, BlockPos floorPosB) {
        pillarAHeight = 0;
        pillarARightHeight = 0;
        pillarBHeight = 0;
        pillarBLeftHeight = 0;
        floorPosARight = switch (controllerDirection.getOpposite()) {
            case NORTH -> new BlockPos(floorPosB.getX(), floorPosA.getY(), floorPosA.getZ());
            case EAST -> new BlockPos(floorPosA.getX(), floorPosA.getY(), floorPosB.getZ());
            case SOUTH -> new BlockPos(floorPosB.getX(), floorPosA.getY(), floorPosA.getZ());
            case WEST -> new BlockPos(floorPosA.getX(), floorPosA.getY(), floorPosB.getZ());
            default -> BlockPos.ZERO;
        };
        floorPosBLeft = switch (controllerDirection.getOpposite()) {
            case NORTH -> new BlockPos(floorPosA.getX(), floorPosA.getY(), floorPosB.getZ());
            case EAST -> new BlockPos(floorPosB.getX(), floorPosA.getY(), floorPosA.getZ());
            case SOUTH -> new BlockPos(floorPosA.getX(), floorPosA.getY(), floorPosB.getZ());
            case WEST -> new BlockPos(floorPosB.getX(), floorPosA.getY(), floorPosA.getZ());
            default -> BlockPos.ZERO;
        };

        while (pillarAHeight < 9) {
            if (isCasing(getState(floorPosA.relative(Direction.UP, pillarAHeight + 1)))) pillarAHeight++;
            else break;
        }
        while (pillarARightHeight < 9) {
            if (isCasing(getState(floorPosARight.relative(Direction.UP, pillarARightHeight + 1)))) pillarARightHeight++;
            else break;
        }
        while (pillarBHeight < 9) {
            if (isCasing(getState(floorPosB.relative(Direction.UP, pillarBHeight + 1)))) pillarBHeight++;
            else break;
        }
        while (pillarBLeftHeight < 9) {
            if (isCasing(getState(floorPosBLeft.relative(Direction.UP, pillarBLeftHeight + 1)))) pillarBLeftHeight++;
            else break;
        }

        if (pillarAHeight < 9 || pillarARightHeight < 9 || pillarBHeight < 9 || pillarBLeftHeight < 9 || pillarAHeight != pillarBHeight || pillarBHeight != pillarBLeftHeight || pillarBLeftHeight != pillarARightHeight) {
            resetAssembled("Missing turbine casing at pillars or invalid height of turbine!");
        }
    }

    public void updateFloorPositions(BlockPos controllerPos, Direction controllerDirection) {
        int rotationMountCount = 0;
        turbineXLenght = 0;
        turbineYLenght = 0;
        canBeAssembled = true;
        floorPosLength = 0;
        floorPosCounterClockwise = 0;
        floorPosClockwise = 0;
        rotationMountPosFloor = null;
        while (floorPosLength < 6) {
            if (isCasingOrRotationMount(getState(controllerPos.below().relative(controllerDirection, floorPosLength + 1)))) {
                if (isRotationMount(getState(controllerPos.below().relative(controllerDirection, floorPosLength + 1))))
                    rotationMountCount++;
                floorPosLength++;
            }
            else break;
        }
        while (floorPosClockwise < 6) {
            if (isCasingOrRotationMount(getState(controllerPos.below().relative(controllerDirection.getClockWise(), floorPosClockwise + 1)))) {
                if (isRotationMount(getState(controllerPos.below().relative(controllerDirection.getClockWise(), floorPosClockwise + 1))))
                    rotationMountCount++;
                floorPosClockwise++;
            }
            else break;
        }
        while (floorPosCounterClockwise < 6) {
            if (isCasingOrRotationMount(getState(controllerPos.below().relative(controllerDirection.getCounterClockWise(), floorPosCounterClockwise + 1)))) {
                if (isRotationMount(getState(controllerPos.below().relative(controllerDirection.getCounterClockWise(), floorPosCounterClockwise + 1))))
                    rotationMountCount++;
                floorPosCounterClockwise++;
            }
            else break;
        }

        if (rotationMountCount != 1 || !level.getBlockState(controllerPos.relative(controllerDirection, 2).relative(Direction.DOWN, 1)).is(ModBlocks.TURBINE_ROTATION_MOUNT.get())) {
            resetAssembled("No turbine rotation mount in floor or at wrong position. Needs to be in floor at the middle");
            return;
        }

        floorPosA = switch (controllerDirection) {
            case NORTH -> controllerPos.relative(controllerDirection.getClockWise(), -floorPosCounterClockwise).relative(controllerDirection, floorPosLength).relative(Direction.DOWN, 1);
            case EAST -> controllerPos.relative(controllerDirection, floorPosLength).relative(controllerDirection.getClockWise(), -floorPosCounterClockwise).relative(Direction.DOWN, 1);
            case SOUTH -> controllerPos.relative(controllerDirection, floorPosLength).relative(controllerDirection.getClockWise(), -floorPosCounterClockwise).relative(Direction.DOWN, 1);
            case WEST -> controllerPos.relative(controllerDirection, floorPosLength).relative(controllerDirection.getCounterClockWise(), floorPosCounterClockwise).relative(Direction.DOWN, 1);
            default -> BlockPos.ZERO;
        };
        floorPosB = switch (controllerDirection) {
            case NORTH -> controllerPos.relative(controllerDirection.getClockWise(), floorPosClockwise).relative(Direction.DOWN, 1);
            case EAST -> controllerPos.relative(controllerDirection.getCounterClockWise(), -floorPosClockwise).relative(Direction.DOWN, 1);
            case SOUTH -> controllerPos.relative(controllerDirection.getCounterClockWise(), -floorPosClockwise).relative(Direction.DOWN, 1);
            case WEST -> controllerPos.relative(controllerDirection.getClockWise(), floorPosClockwise).relative(Direction.DOWN, 1);
            default -> BlockPos.ZERO;
        };

        List<BlockPos> blockPosList = CalculationUtil.getBlockStates(floorPosA, floorPosB, level);
        for (BlockPos pos : blockPosList) {
            if (isRotationMount(getState(pos))){
                rotationMountPosFloor = pos;
            } else if (!isCasing(getState(pos))) {
                resetAssembled("Missing turbine casing in floor at position " + pos);
                return;
            }
        }

        if (controllerDirection == Direction.NORTH) {
            turbineXLenght = (int) Math.floor(floorPosB.getX() - floorPosA.getX() + 1);
            turbineYLenght = (int) Math.floor(floorPosB.getZ() - floorPosA.getZ() + 1);
        } else if (controllerDirection == Direction.EAST){
            turbineXLenght = (int) Math.floor(floorPosA.getX() - floorPosB.getX() + 1);
            turbineYLenght = (int) Math.floor(floorPosB.getZ() - floorPosA.getZ() + 1);
        } else if (controllerDirection == Direction.SOUTH){
            turbineXLenght = (int) Math.floor(floorPosA.getX() - floorPosB.getX() + 1);
            turbineYLenght = (int) Math.floor(floorPosA.getZ() - floorPosB.getZ() + 1);
        } else {
            turbineXLenght = (int) Math.floor(floorPosB.getX() - floorPosA.getX() + 1);
            turbineYLenght = (int) Math.floor(floorPosA.getZ() - floorPosB.getZ() + 1);
        }

        if (turbineXLenght != turbineYLenght || turbineXLenght < 5) {
            resetAssembled();
        }
    }

    public BlockPos addParticleOffset(BlockPos pos, Direction direction) {
        return switch (direction) {
            case WEST -> pos.offset(0, -1, -2);
            case EAST -> pos.offset(-4, -1, -2);
            case SOUTH -> pos.offset(-2, -1, -4);
            default -> pos.offset(-2, -1, 0);
        };
    }

    public int getTurbineHeight() {
        return pillarAHeight;
    }

    public void setTurbineHeight(int height) {
        pillarAHeight = height;
    }

    public BlockState getState(BlockPos pos) {
        return this.level.getBlockState(pos);
    }

    public boolean isRotationMount(BlockState state) {
        return state.is(ModBlocks.TURBINE_ROTATION_MOUNT.get());
    }

    public boolean isCasing(BlockState state) {
        return state.is(ModBlocks.TURBINE_CASING.get());
    }

    public boolean isCasingOrRotationMount(BlockState state) {
        return isCasing(state) || isRotationMount(state);
    }

    public boolean isGlass(BlockState state) {
        return state.is(ModBlocks.TURBINE_GLASS.get());
    }

    public boolean isValve(BlockState state) {
        return state.is(ModBlocks.TURBINE_VALVE.get());
    }

    public void resetAssembled() {
        setTurbineRotorRendering(false);
        canBeAssembled = false;
        assembled = false;

        if (getBlockState().getValue(TurbineControllerBlock.POWERED) != assembled)
            getLevel().setBlock(getBlockPos(), getBlockState().setValue(TurbineControllerBlock.POWERED, assembled), 3);
    }

    public void resetAssembled(String text) {
        if (warning != null)
            warning = text;
        resetAssembled();
    }

    public boolean isAssembled() {
        return assembled;
    }

    public void setAssembled(boolean assembled) {
        this.assembled = assembled;
    }

    public String getWarning() {
        return warning;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }

    @NotNull
    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag nbt = new CompoundTag();
        nbt.putBoolean("Assembled", isAssembled());
        nbt.putInt("Height", getTurbineHeight());
        return nbt;
    }

    @Override
    public void handleUpdateTag(final CompoundTag tag) {
        setAssembled(tag.getBoolean("Assembled"));
        setTurbineHeight(tag.getInt("Height"));
    }

    @Override
    public void saveAdditional(@NotNull CompoundTag nbt) {
        nbt.putBoolean("Assembled", isAssembled());
        nbt.putInt("Height", getTurbineHeight());
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        setAssembled(nbt.getBoolean("Assembled"));
        setTurbineHeight(nbt.getInt("Height"));
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        if (getLevel().isClientSide && net.getDirection() == PacketFlow.CLIENTBOUND) handleUpdateTag(pkt.getTag());
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public AABB getRenderBoundingBox() {
        return new AABB(getBlockPos().offset(-10, -10, -10), getBlockPos().offset(11, 11, 11));
    }

    @Override
    public Component getDisplayName() {
        return null;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return null;
    }
}
