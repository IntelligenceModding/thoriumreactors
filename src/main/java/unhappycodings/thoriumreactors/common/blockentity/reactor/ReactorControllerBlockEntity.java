package unhappycodings.thoriumreactors.common.blockentity.reactor;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.Tags;
import net.minecraftforge.server.command.ModIdArgument;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import unhappycodings.thoriumreactors.common.ReactorStateEnum;
import unhappycodings.thoriumreactors.common.ValveTypeEnum;
import unhappycodings.thoriumreactors.common.block.reactor.ReactorControllerBlock;
import unhappycodings.thoriumreactors.common.block.reactor.ReactorValveBlock;
import unhappycodings.thoriumreactors.common.container.reactor.ReactorControllerContainer;
import unhappycodings.thoriumreactors.common.registration.ModBlockEntities;
import unhappycodings.thoriumreactors.common.registration.ModBlocks;
import unhappycodings.thoriumreactors.common.registration.ModItems;
import unhappycodings.thoriumreactors.common.util.CalculationUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ReactorControllerBlockEntity extends BlockEntity implements MenuProvider {
    public List<BlockPos> framePosList = new ArrayList<>();
    public BlockPos floorPosA, floorPosB, floorPosARight, floorPosBLeft;
    public BlockPos ceilingPosA, ceilingPosB, ceilingPosARight, ceilingPosBLeft;
    public int pillarAHeight, pillarARightHeight, pillarBHeight, pillarBLeftHeight;
    public int floorPosLength, floorPosClockwise, floorPosCounterClockwise;
    public int reactorXLenght, reactorYLenght;
    public BlockPos reactorCorePos;
    public List<BlockPos> moderatorPos;
    public List<BlockPos> valvePos;
    public BlockPos controlRodPos;
    public boolean canBeAssembled;
    public boolean assembled;
    public String warning = "";
    public String notification = "";

    // Reactor
    private short reactorCurrentTemperature; // 0-3000
    private short reactorTargetTemperature; // 0-3000
    private byte reactorTargetLoadSet; // 0-100%
    private byte reactorCurrentLoadSet; // 0-100%
    private long reactorRunningSince; // timestamp
    private byte reactorStatus = 100; // 0-100%
    private float reactorContainment; // 0-100%
    private float reactorRadiation; // uSv per hour
    private float reactorPressure; // in PSI
    private int fuelAdditions = 0;
    private boolean scrammed;
    private ReactorStateEnum reactorState = ReactorStateEnum.STOP; // STARTING - RUNNING - STOP
    // Rods
    public byte[] fuelRodStatus = new byte[81];
    public byte[] controlRodStatus = new byte[64];
    // Turbine
    private short turbineTargetSpeed; // RPM
    private short turbineCurrentSpeed; // RPM
    private byte turbineTargetOverflowSet; // 0-100%
    private byte turbineCurrentOverflowSet; // 0-100%
    private byte turbineTargetLoadSet; // 0-100%
    private byte turbineCurrentLoadSet; // 0-100%
    private boolean turbineCoilsEngaged; // true-false
    private short turbineCurrentFlow; // Buckets per second
    private long turbinePowerGeneration; // FE per tick

    public ReactorControllerBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.REACTOR_CONTROLLER.get(), pPos, pBlockState);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        updateBlock(); // TESTING
    }

    public void tick() {
        BlockPos controllerPos = this.getBlockPos();

        if (getBlockState().getValue(ReactorControllerBlock.POWERED) != assembled)
            getLevel().setBlock(getBlockPos(), getBlockState().setValue(ReactorControllerBlock.POWERED, assembled), 3);
        if (isCasing(getState(controllerPos.below())) && level.getGameTime() % 20 == 0) {
            Direction controllerDirection = this.getBlockState().getValue(ReactorControllerBlock.FACING).getOpposite();

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
            assembled = true;

        }
        if (isAssembled()) {

            if (valvePos != null) {
                int fuelValue = 0;
                for (int i = 0; i < getFuelRodStatus().length; i++) fuelValue += getFuelRodStatus()[i];
                int fuelPercentage = (int) (fuelValue / 8100f * 100f);

                for (BlockPos blockPos : valvePos) {
                    if (!level.getBlockState(blockPos).is(ModBlocks.REACTOR_VALVE.get())) return;
                    ReactorValveBlockEntity entity = (ReactorValveBlockEntity) level.getBlockEntity(blockPos);
                    if (fuelAdditions == 0 && fuelValue < 8100 && entity.getItem(0).is(ModItems.ENRICHED_URANIUM.get()) && fuelPercentage < getReactorTargetLoadSet() && level.getBlockState(blockPos).getValue(ReactorValveBlock.TYPE) == ValveTypeEnum.ITEM_INPUT) {
                        entity.getItem(0).shrink(1);
                        fuelAdditions = 10;
                    } else if (fuelPercentage >= getReactorTargetLoadSet() && level.getBlockState(blockPos).getValue(ReactorValveBlock.TYPE) == ValveTypeEnum.ITEM_OUTPUT) {
                        if (fuelAdditions == 10) {
                            System.out.println(fuelAdditions);
                            if (entity.getItem(0).isEmpty())
                                entity.setItem(0, new ItemStack(ModItems.ENRICHED_URANIUM.get(), 1));
                            else
                                entity.getItem(0).grow(1);
                            fuelAdditions = 0;
                        }
                    }

                }

                int randomNumber = new Random().nextInt(81);
                if (fuelAdditions != 0) {
                    if (getFuelRodStatus((byte) randomNumber) < getReactorTargetLoadSet()) {
                        setFuelRodStatus((byte) randomNumber, (byte) (getFuelRodStatus((byte) randomNumber) + 1)) ;
                        fuelAdditions--;
                    }
                }
                if (fuelPercentage >= getReactorTargetLoadSet()) {
                    if (getFuelRodStatus((byte) randomNumber) > getReactorTargetLoadSet() && fuelAdditions < 10) {
                        setFuelRodStatus((byte) randomNumber, (byte) (getFuelRodStatus((byte) randomNumber) - 1)) ;
                        fuelAdditions++;
                    }
                }
            }



        }
    }

    @NotNull
    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag nbt = new CompoundTag();
        nbt.putBoolean("Assembled", isAssembled());
        // Reactor
        nbt.putShort("ReactorTargetTemperature", getReactorTargetTemperature());
        nbt.putShort("ReactorCurrentTemperature", getReactorCurrentTemperature());
        nbt.putByte("ReactorTargetLoadSet", getReactorTargetLoadSet());
        nbt.putByte("ReactorCurrentLoadSet", getReactorCurrentLoadSet());
        nbt.putLong("ReactorRunningSince", getReactorRunningSince());
        nbt.putByte("ReactorStatus", getReactorStatus());
        nbt.putFloat("ReactorContainment", getReactorContainment());
        nbt.putFloat("ReactorRadiation", getReactorRadiation());
        nbt.putFloat("ReactorPressure", getReactorPressure());
        nbt.putInt("FuelAdditions", getFuelAdditions());
        nbt.putBoolean("Scrammed", isScrammed());
        nbt.putString("ReactorState", getReactorState().toString());
        // Rod
        nbt.putByteArray("FuelRodStatus", getFuelRodStatus());
        nbt.putByteArray("ControlRodStatus", getControlRodStatus());
        // Turbine
        nbt.putShort("TurbineTargetSpeed", getTurbineTargetSpeed());
        nbt.putShort("TurbineCurrentSpeed", getTurbineCurrentSpeed());
        nbt.putByte("TurbineTargetOverflowSet", getTurbineTargetOverflowSet());
        nbt.putByte("TurbineCurrentOverflowSet", getTurbineCurrentOverflowSet());
        nbt.putByte("TurbineTargetLoadSet", getTurbineTargetLoadSet());
        nbt.putByte("TurbineCurrentLoadSet", getTurbineCurrentLoadSet());
        nbt.putBoolean("TurbineCoilsEngaged", isTurbineCoilsEngaged());
        nbt.putShort("TurbineCurrentFlow", getTurbineCurrentFlow());
        nbt.putLong("TurbinePowerGeneration", getTurbinePowerGeneration());
        return nbt;
    }

    @Override
    public void handleUpdateTag(final CompoundTag tag) {
        setAssembled(tag.getBoolean("Assembled"));
        // Reactor
        setReactorTargetTemperature(tag.getShort("ReactorTargetTemperature"));
        setReactorCurrentTemperature(tag.getShort("ReactorCurrentTemperature"));
        setReactorTargetLoadSet(tag.getByte("ReactorTargetLoadSet"));
        setReactorCurrentLoadSet(tag.getByte("ReactorCurrentLoadSet"));
        setReactorRunningSince(tag.getLong("ReactorRunningSince"));
        setReactorStatus(tag.getByte("ReactorStatus"));
        setReactorContainment(tag.getFloat("ReactorContainment"));
        setReactorRadiation(tag.getFloat("ReactorRadiation"));
        setReactorPressure(tag.getFloat("ReactorPressure"));
        setFuelAdditions(tag.getInt("FuelAdditions"));
        setScrammed(tag.getBoolean("Scrammed"));
        setReactorState(ReactorStateEnum.valueOf(tag.getString("ReactorState")));
        // Rod
        setFuelRodStatus(tag.getByteArray("FuelRodStatus"));
        setControlRodStatus(tag.getByteArray("ControlRodStatus"));
        // Turbine
        setTurbineTargetSpeed(tag.getShort("TurbineTargetSpeed"));
        setTurbineCurrentSpeed(tag.getShort("TurbineCurrentSpeed"));
        setTurbineTargetOverflowSet(tag.getByte("TurbineTargetOverflowSet"));
        setTurbineCurrentOverflowSet(tag.getByte("TurbineCurrentOverflowSet"));
        setTurbineTargetLoadSet(tag.getByte("TurbineTargetLoadSet"));
        setTurbineCurrentLoadSet(tag.getByte("TurbineCurrentLoadSet"));
        setTurbineCoilsEngaged(tag.getBoolean("TurbineCoilsEngaged"));
        setTurbineCurrentFlow(tag.getShort("TurbineCurrentFlow"));
        setTurbinePowerGeneration(tag.getLong("TurbinePowerGeneration"));
    }

    @Override
    public void saveAdditional(@NotNull CompoundTag nbt) {
        nbt.putBoolean("Assembled", isAssembled());
        // Reactor
        nbt.putShort("ReactorTargetTemperature", getReactorTargetTemperature());
        nbt.putShort("ReactorCurrentTemperature", getReactorCurrentTemperature());
        nbt.putByte("ReactorTargetLoadSet", getReactorTargetLoadSet());
        nbt.putByte("ReactorCurrentLoadSet", getReactorCurrentLoadSet());
        nbt.putLong("ReactorRunningSince", getReactorRunningSince());
        nbt.putByte("ReactorStatus", getReactorStatus());
        nbt.putFloat("ReactorContainment", getReactorContainment());
        nbt.putFloat("ReactorRadiation", getReactorRadiation());
        nbt.putFloat("ReactorPressure", getReactorPressure());
        nbt.putInt("FuelAdditions", getFuelAdditions());
        nbt.putBoolean("Scrammed", isScrammed());
        nbt.putString("ReactorState", getReactorState().toString());
        // Rod
        nbt.putByteArray("FuelRodStatus", getFuelRodStatus());
        nbt.putByteArray("ControlRodStatus", getControlRodStatus());
        // Turbine
        nbt.putShort("TurbineTargetSpeed", getTurbineTargetSpeed());
        nbt.putShort("TurbineCurrentSpeed", getTurbineCurrentSpeed());
        nbt.putByte("TurbineTargetOverflowSet", getTurbineTargetOverflowSet());
        nbt.putByte("TurbineCurrentOverflowSet", getTurbineCurrentOverflowSet());
        nbt.putByte("TurbineTargetLoadSet", getTurbineTargetLoadSet());
        nbt.putByte("TurbineCurrentLoadSet", getTurbineCurrentLoadSet());
        nbt.putBoolean("TurbineCoilsEngaged", isTurbineCoilsEngaged());
        nbt.putShort("TurbineCurrentFlow", getTurbineCurrentFlow());
        nbt.putLong("TurbinePowerGeneration", getTurbinePowerGeneration());
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        setAssembled(nbt.getBoolean("Assembled"));
        // Reactor
        setReactorTargetTemperature(nbt.getShort("ReactorTargetTemperature"));
        setReactorCurrentTemperature(nbt.getShort("ReactorCurrentTemperature"));
        setReactorTargetLoadSet(nbt.getByte("ReactorTargetLoadSet"));
        setReactorCurrentLoadSet(nbt.getByte("ReactorCurrentLoadSet"));
        setReactorRunningSince(nbt.getLong("ReactorRunningSince"));
        setReactorStatus(nbt.getByte("ReactorStatus"));
        setReactorContainment(nbt.getFloat("ReactorContainment"));
        setReactorRadiation(nbt.getFloat("ReactorRadiation"));
        setReactorPressure(nbt.getFloat("ReactorPressure"));
        setFuelAdditions(nbt.getInt("FuelAdditions"));
        setScrammed(nbt.getBoolean("Scrammed"));
        setReactorState(ReactorStateEnum.valueOf(nbt.getString("ReactorState")));
        // Rod
        setFuelRodStatus(nbt.getByteArray("FuelRodStatus"));
        setControlRodStatus(nbt.getByteArray("ControlRodStatus"));
        // Turbine
        setTurbineTargetSpeed(nbt.getShort("TurbineTargetSpeed"));
        setTurbineCurrentSpeed(nbt.getShort("TurbineCurrentSpeed"));
        setTurbineTargetOverflowSet(nbt.getByte("TurbineTargetOverflowSet"));
        setTurbineCurrentOverflowSet(nbt.getByte("TurbineCurrentOverflowSet"));
        setTurbineTargetLoadSet(nbt.getByte("TurbineTargetLoadSet"));
        setTurbineCurrentLoadSet(nbt.getByte("TurbineCurrentLoadSet"));
        setTurbineCoilsEngaged(nbt.getBoolean("TurbineCoilsEngaged"));
        setTurbineCurrentFlow(nbt.getShort("TurbineCurrentFlow"));
        setTurbinePowerGeneration(nbt.getLong("TurbinePowerGeneration"));
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

    @NotNull
    @Override
    public Component getDisplayName() {
        return Component.translatable("block.thoriumreactors.reactor_controller");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, @NotNull Inventory pInventory, @NotNull Player pPlayer) {
        return new ReactorControllerContainer(pContainerId, pInventory, getBlockPos(), getLevel(), 0);
    }

    public void setFuelAdditions(int fuelAdditions) {
        this.fuelAdditions = fuelAdditions;
    }

    public int getFuelAdditions() {
        return fuelAdditions;
    }

    public boolean isScrammed() {
        return scrammed;
    }

    public void setScrammed(boolean scrammed) {
        this.scrammed = scrammed;
    }

    public void updateBlock() {
        if(level != null && !level.isClientSide) {
            BlockState state = level.getBlockState(worldPosition);
            level.sendBlockUpdated(worldPosition, state, state, 2);
            setChanged(level, getBlockPos(), state);
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

        if (valvePos.size() != 4) {
            resetAssembled("Reactor is in need of three valves. Currently " + valvePos.size());
        }
    }

    public void checkWallArea(List<BlockPos> blockPosList) {
        for (BlockPos pos : blockPosList) {
            if (!(isCasing(getState(pos)) || isGlass(getState(pos)) || isValve(getState(pos)) || getState(pos).is(getState(getBlockPos()).getBlock()))) {
                resetAssembled("Missing reactor casing, glass or valve in wall at " + pos);
            }
            if (isValve(getState(pos))) {
                valvePos.add(pos);
                ReactorValveBlockEntity entity = (ReactorValveBlockEntity) level.getBlockEntity(pos);
                entity.setReactorCorePosition(this.getBlockPos());
            }
        }
    };

    public void updateInnerPositions() {
        int moderatorCount = pillarAHeight - 2;
        reactorCorePos = null;
        moderatorPos = new ArrayList<>(moderatorCount);

        for (int i = 0; i < pillarAHeight; i++) {
            if (getState(controlRodPos.below(i)).is(ModBlocks.REACTOR_CORE.get())) {
                if (isCasing(getState(controlRodPos.below(i).below()))) {
                    reactorCorePos = controlRodPos.below(i);
                }
            } else if (getState(controlRodPos.below(i)).is(ModBlocks.REACTOR_GRAPHITE_MODERATOR.get())) {
                moderatorPos.add(controlRodPos.below(i));
            }
        }

        if (reactorCorePos == null || !isCasing(getState(reactorCorePos.below())) || reactorCorePos.below().getY() != floorPosA.getY()) {
            resetAssembled("Reactor core not found or placed on the reactor floor");
            return;
        }

        if (moderatorPos != null) {
            for (BlockPos pos : moderatorPos) {
                if (!getState(pos).is(ModBlocks.REACTOR_GRAPHITE_MODERATOR.get()) || reactorCorePos.getX() != pos.getX() || reactorCorePos.getZ() != pos.getZ() || moderatorPos.size() != moderatorCount) {
                    resetAssembled("Reactor moderators not valid. Must be placed between reactor core and rod controller");
                }
            }
        } else {
            resetAssembled("Reactor moderators not found. Must be placed between reactor core and rod controller");
        }

    }

    public void updateFloorPositions(BlockPos controllerPos, Direction controllerDirection) {
        reactorXLenght = 0;
        reactorYLenght = 0;
        canBeAssembled = true;
        floorPosLength = 0;
        floorPosCounterClockwise = 0;
        floorPosClockwise = 0;
        while (floorPosLength < 6)
            if (isCasing(getState(controllerPos.below().relative(controllerDirection, floorPosLength + 1)))) floorPosLength++;
            else break;
        while (floorPosClockwise < 6)
            if (isCasing(getState(controllerPos.below().relative(controllerDirection.getClockWise(), floorPosClockwise + 1)))) floorPosClockwise++;
            else break;
        while (floorPosCounterClockwise < 6)
            if (isCasing(getState(controllerPos.below().relative(controllerDirection.getCounterClockWise(), floorPosCounterClockwise + 1)))) floorPosCounterClockwise++;
            else break;

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
            if (!isCasing(getState(pos))) {
                resetAssembled("Missing reactor casing in floor at position " + pos);
                return;
            }
        }

        if (controllerDirection == Direction.NORTH) {
            reactorXLenght = (int) Math.floor(floorPosB.getX() - floorPosA.getX() + 1);
            reactorYLenght = (int) Math.floor(floorPosB.getZ() - floorPosA.getZ() + 1);
        } else if (controllerDirection == Direction.EAST){
            reactorXLenght = (int) Math.floor(floorPosA.getX() - floorPosB.getX() + 1);
            reactorYLenght = (int) Math.floor(floorPosB.getZ() - floorPosA.getZ() + 1);
        } else if (controllerDirection == Direction.SOUTH){
            reactorXLenght = (int) Math.floor(floorPosA.getX() - floorPosB.getX() + 1);
            reactorYLenght = (int) Math.floor(floorPosA.getZ() - floorPosB.getZ() + 1);
        } else {
            reactorXLenght = (int) Math.floor(floorPosB.getX() - floorPosA.getX() + 1);
            reactorYLenght = (int) Math.floor(floorPosA.getZ() - floorPosB.getZ() + 1);
        }

        if (reactorXLenght != reactorYLenght || reactorXLenght < 5 || reactorYLenght < 5) {
            resetAssembled();
        }
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

        while (pillarAHeight < 5) {
            if (isCasing(getState(floorPosA.relative(Direction.UP, pillarAHeight + 1)))) pillarAHeight++;
            else break;
        }
        while (pillarARightHeight < 5) {
            if (isCasing(getState(floorPosARight.relative(Direction.UP, pillarARightHeight + 1)))) pillarARightHeight++;
            else break;
        }
        while (pillarBHeight < 5) {
            if (isCasing(getState(floorPosB.relative(Direction.UP, pillarBHeight + 1)))) pillarBHeight++;
            else break;
        }
        while (pillarBLeftHeight < 5) {
            if (isCasing(getState(floorPosBLeft.relative(Direction.UP, pillarBLeftHeight + 1)))) pillarBLeftHeight++;
            else break;
        }

        if (pillarAHeight < 3 || pillarARightHeight < 3 || pillarBHeight < 3 || pillarBLeftHeight < 3 || pillarAHeight != pillarBHeight || pillarBHeight != pillarBLeftHeight || pillarBLeftHeight != pillarARightHeight) {
            resetAssembled("Missing reactor casing at pillars or invalid height of reactor!");
        }
    }

    public void updateCeilingPositions() {
        ceilingPosA = floorPosA.offset(0, pillarAHeight, 0);
        ceilingPosB = floorPosB.offset(0, pillarBHeight, 0);
        ceilingPosARight = floorPosARight.offset(0, pillarARightHeight, 0);
        ceilingPosBLeft = floorPosBLeft.offset(0, pillarBLeftHeight, 0);
        framePosList = new ArrayList<>();
        controlRodPos = null;

        // Checking ceiling frame for casings
        List<List<BlockPos>> positionList = List.of(List.of(ceilingPosA, ceilingPosARight), List.of(ceilingPosARight, ceilingPosB), List.of(ceilingPosB, ceilingPosBLeft), List.of(ceilingPosBLeft, ceilingPosA));
        for (List<BlockPos> allBlockList : positionList) {
            List<BlockPos> blockPosList = CalculationUtil.getBlockStates(allBlockList.get(0), allBlockList.get(1), level);
            for (BlockPos pos : blockPosList) {
                if (!isCasing(getState(pos))) {
                    resetAssembled("Missing reactor casing in ceiling frame at position " + pos);
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
            if (getState(pos).is(ModBlocks.REACTOR_ROD_CONTROLLER.get())) {
                controlRodPos = pos;
            } else if (isGlass(getState(pos))) {
                glassStates.add(getState(pos));
            } else if (isCasing(getState(pos))) {
                casingStates.add(getState(pos));
            }
        }

        if (controlRodPos == null || !hasFrameConnection(controlRodPos))
            resetAssembled("Missing reactor rod controller in ceiling or no connection to frame with casings");

        if (glassStates.size() + casingStates.size() + 1 != ceilingWall.size())
            resetAssembled("Missing reactor casings or glass in ceiling");

    }

    public boolean hasFrameConnection(BlockPos pos) {
        boolean exit = false;
        int runs = 0;
        BlockPos current = pos;
        while (!exit) {
            for (Direction value : Direction.values()) {
                if (getState(current.relative(value)).is(ModBlocks.REACTOR_CASING.get())) {
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

    public void resetAssembled() {
        canBeAssembled = false;
        assembled = false;

        if (getBlockState().getValue(ReactorControllerBlock.POWERED) != assembled)
            getLevel().setBlock(getBlockPos(), getBlockState().setValue(ReactorControllerBlock.POWERED, assembled), 3);
    }

    public void resetAssembled(String text) {
        if (warning != null)
            warning = text;
        resetAssembled();
    }

    public byte[] getFuelRodStatus() {
        return fuelRodStatus;
    }

    public byte getFuelRodStatus(byte index) {
        return getFuelRodStatus()[index];
    }

    public void setFuelRodStatus(byte[] fuelRodStatus) {
        this.fuelRodStatus = fuelRodStatus;
        setChanged();
    }

    public void setFuelRodStatus(byte index, byte value) {
        getFuelRodStatus()[index] = value;
        setChanged();
    }

    public byte[] getControlRodStatus() {
        return controlRodStatus;
    }

    public int getControlRodStatus(byte index) {
        return getControlRodStatus()[index];
    }

    public void setControlRodStatus(byte[] fuelRodStatus) {
        this.controlRodStatus = fuelRodStatus;
    }

    public int setControlRodStatus(byte index, byte value) {
        return getControlRodStatus()[index] = value;
    }

    public BlockState getState(BlockPos pos) {
        return this.level.getBlockState(pos);
    }

    public boolean isCasing(BlockState state) {
        return state.is(ModBlocks.REACTOR_CASING.get());
    }

    public boolean isGlass(BlockState state) {
        return state.is(ModBlocks.REACTOR_GLASS.get());
    }

    public boolean isValve(BlockState state) {
        return state.is(ModBlocks.REACTOR_VALVE.get());
    }

    public boolean isAssembled() {
        return assembled;
    }

    public void setAssembled(boolean canBeAssembled) {
        this.assembled = canBeAssembled;
    }

    public short getReactorCurrentTemperature() {
        return reactorCurrentTemperature;
    }

    public void setReactorCurrentTemperature(short reactorCurrentTemperature) {
        this.reactorCurrentTemperature = reactorCurrentTemperature;
    }

    public short getReactorTargetTemperature() {
        return reactorTargetTemperature;
    }

    public void setReactorTargetTemperature(short reactorTargetTemperature) {
        this.reactorTargetTemperature = reactorTargetTemperature;
    }

    public byte getReactorTargetLoadSet() {
        return reactorTargetLoadSet;
    }

    public void setReactorTargetLoadSet(byte reactorTargetLoadSet) {
        this.reactorTargetLoadSet = reactorTargetLoadSet;
    }

    public byte getReactorCurrentLoadSet() {
        return reactorCurrentLoadSet;
    }

    public void setReactorCurrentLoadSet(byte reactorCurrentLoadSet) {
        this.reactorCurrentLoadSet = reactorCurrentLoadSet;
    }

    public long getReactorRunningSince() {
        return reactorRunningSince;
    }

    public void setReactorRunningSince(long reactorRunningSince) {
        this.reactorRunningSince = reactorRunningSince;
    }

    public byte getReactorStatus() {
        return reactorStatus;
    }

    public void setReactorStatus(byte reactorStatus) {
        this.reactorStatus = reactorStatus;
    }

    public float getReactorContainment() {
        return reactorContainment;
    }

    public void setReactorContainment(float reactorContainment) {
        this.reactorContainment = reactorContainment;
    }

    public float getReactorRadiation() {
        return reactorRadiation;
    }

    public void setReactorRadiation(float reactorRadiation) {
        this.reactorRadiation = reactorRadiation;
    }

    public float getReactorPressure() {
        return reactorPressure;
    }

    public void setReactorPressure(float reactorPressure) {
        this.reactorPressure = reactorPressure;
    }

    public ReactorStateEnum getReactorState() {
        return reactorState;
    }

    public void setReactorState(ReactorStateEnum reactorState) {
        this.reactorState = reactorState;
    }

    public short getTurbineTargetSpeed() {
        return turbineTargetSpeed;
    }

    public void setTurbineTargetSpeed(short turbineTargetSpeed) {
        this.turbineTargetSpeed = turbineTargetSpeed;
    }

    public short getTurbineCurrentSpeed() {
        return turbineCurrentSpeed;
    }

    public void setTurbineCurrentSpeed(short turbineCurrentSpeed) {
        this.turbineCurrentSpeed = turbineCurrentSpeed;
    }

    public byte getTurbineTargetOverflowSet() {
        return turbineTargetOverflowSet;
    }

    public void setTurbineTargetOverflowSet(byte turbineTargetOverflowSet) {
        this.turbineTargetOverflowSet = turbineTargetOverflowSet;
    }

    public byte getTurbineCurrentOverflowSet() {
        return turbineCurrentOverflowSet;
    }

    public void setTurbineCurrentOverflowSet(byte turbineCurrentOverflowSet) {
        this.turbineCurrentOverflowSet = turbineCurrentOverflowSet;
    }

    public byte getTurbineTargetLoadSet() {
        return turbineTargetLoadSet;
    }

    public void setTurbineTargetLoadSet(byte turbineTargetLoadSet) {
        this.turbineTargetLoadSet = turbineTargetLoadSet;
    }

    public byte getTurbineCurrentLoadSet() {
        return turbineCurrentLoadSet;
    }

    public void setTurbineCurrentLoadSet(byte turbineCurrentLoadSet) {
        this.turbineCurrentLoadSet = turbineCurrentLoadSet;
    }

    public boolean isTurbineCoilsEngaged() {
        return turbineCoilsEngaged;
    }

    public void setTurbineCoilsEngaged(boolean turbineCoilsEngaged) {
        this.turbineCoilsEngaged = turbineCoilsEngaged;
    }

    public short getTurbineCurrentFlow() {
        return turbineCurrentFlow;
    }

    public void setTurbineCurrentFlow(short turbineCurrentFlow) {
        this.turbineCurrentFlow = turbineCurrentFlow;
    }

    public long getTurbinePowerGeneration() {
        return turbinePowerGeneration;
    }

    public void setTurbinePowerGeneration(long turbinePowerGeneration) {
        this.turbinePowerGeneration = turbinePowerGeneration;
    }
}
