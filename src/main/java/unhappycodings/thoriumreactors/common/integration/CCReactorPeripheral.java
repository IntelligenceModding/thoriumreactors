package unhappycodings.thoriumreactors.common.integration;

import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.core.BlockPos;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import unhappycodings.thoriumreactors.common.blockentity.reactor.ReactorCasingBlockEntity;
import unhappycodings.thoriumreactors.common.blockentity.reactor.ReactorControllerBlockEntity;
import unhappycodings.thoriumreactors.common.blockentity.turbine.TurbineControllerBlockEntity;
import unhappycodings.thoriumreactors.common.enums.ReactorStateEnum;
import unhappycodings.thoriumreactors.common.util.LuaUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CCReactorPeripheral implements IPeripheral {
    private final List<IComputerAccess> connectedComputers = new ArrayList<>();
    private final ReactorCasingBlockEntity tileEntity;

    public CCReactorPeripheral(ReactorCasingBlockEntity tileEntity) {
        this.tileEntity = tileEntity;
    }

    @NotNull
    @Override
    public String getType() {
        return "thorium_reactor";
    }

    @Override
    public boolean equals(@Nullable IPeripheral iPeripheral) {
        return this == iPeripheral;
    }

    /**
     * Will be called when a computer disconnects from our block
     */
    @Override
    public void detach(@NotNull IComputerAccess computer) {
        connectedComputers.remove(computer);
    }

    /**
     * Will be called when a computer connects to our block
     */
    @Override
    public void attach(@NotNull IComputerAccess computer) {
        connectedComputers.add(computer);
    }

    public ReactorCasingBlockEntity getTileEntity() {
        return tileEntity;
    }

    /**
     * @return Whether reactor is assembled or not
     */
    @LuaFunction(mainThread = true)
    public final boolean isAssembled() {
        ReactorControllerBlockEntity entity = getReactorController();
        return entity != null && entity.isAssembled();
    }

    @LuaFunction(mainThread = true)
    public final String getReactorState() {
        ReactorControllerBlockEntity entity = getReactorController();
        return entity == null ? "undefined" : entity.getReactorState().getSerializedName();
    }

    @LuaFunction(mainThread = true)
    public final void setReactorState(String status) {
        ReactorControllerBlockEntity entity = getReactorController();
        if (entity == null) return;
        entity.setReactorState(ReactorStateEnum.get(status));
    }

    @LuaFunction(mainThread = true)
    public final Integer getReactorFluidAmountOut() {
        ReactorControllerBlockEntity entity = getReactorController();
        return entity == null ? -1 : entity.getFluidAmountOut();
    }

    @LuaFunction(mainThread = true)
    public final Integer getReactorFluidCapacityOut() {
        ReactorControllerBlockEntity entity = getReactorController();
        return entity == null ? -1 : entity.getFluidCapacityOut();
    }

    @LuaFunction(mainThread = true)
    public final Integer getReactorFluidAmountIn() {
        ReactorControllerBlockEntity entity = getReactorController();
        return entity == null ? -1 : entity.getFluidAmountIn();
    }

    @LuaFunction(mainThread = true)
    public final Integer getReactorFluidCapacityIn() {
        ReactorControllerBlockEntity entity = getReactorController();
        return entity == null ? -1 : entity.getFluidCapacityIn();
    }

    @LuaFunction(mainThread = true)
    public final Integer getReactorCapacity() {
        ReactorControllerBlockEntity entity = getReactorController();
        return entity == null ? -1 : entity.getReactorCapacity();
    }

    @LuaFunction(mainThread = true)
    public final Float getReactorCurrentLoadSet() { // Fuel
        ReactorControllerBlockEntity entity = getReactorController();
        if (entity == null) return -1f;
        int fuelValue = 0;
        if (entity.getFuelRodStatus().length != 0) {
            for (int i = 0; i < entity.getFuelRodStatus().length; i++)
                fuelValue += entity.getFuelRodStatus()[i];
        }
        return fuelValue / 8100f * 100f;
    }

    @LuaFunction(mainThread = true)
    public final Byte getReactorTargetLoadSet() { // Fuel
        ReactorControllerBlockEntity entity = getReactorController();
        return entity == null ? -1 : entity.getReactorTargetLoadSet();
    }

    @LuaFunction(mainThread = true)
    public final void setReactorTargetLoadSet(int rate) {// Fuel
        ReactorControllerBlockEntity entity = getReactorController();
        if (entity == null || rate < 0 || rate > 100) return;
        entity.setReactorTargetLoadSet((byte) rate);
    }

    @LuaFunction(mainThread = true)
    public final Float getReactorStatusPercent() {
        ReactorControllerBlockEntity entity = getReactorController();
        return entity == null ? -1 : entity.getReactorStatus();
    }

    @LuaFunction(mainThread = true)
    public final Float getReactorContainmentPercent() {
        ReactorControllerBlockEntity entity = getReactorController();
        return entity == null ? -1 : entity.getReactorContainment();
    }

    @LuaFunction(mainThread = true)
    public final Float getReactorRadiation() {
        ReactorControllerBlockEntity entity = getReactorController();
        return entity == null ? -1 : entity.getReactorRadiation();
    }

    @LuaFunction(mainThread = true)
    public final boolean isReactorScrammed() {
        ReactorControllerBlockEntity entity = getReactorController();
        return entity != null && entity.isScrammed();
    }

    @LuaFunction(mainThread = true)
    public final void setReactorScrammed(boolean state) {
        ReactorControllerBlockEntity entity = getReactorController();
        if (entity == null) return;
        entity.setScrammed(state);
    }

    @LuaFunction(mainThread = true)
    public final Long getReactorRunningSince() {
        ReactorControllerBlockEntity entity = getReactorController();
        return entity == null ? -1 : entity.getReactorRunningSince();
    }

    @LuaFunction(mainThread = true)
    public final Float getReactorCurrentTemperature() {
        ReactorControllerBlockEntity entity = getReactorController();
        return entity == null ? -1 : entity.getReactorCurrentTemperature();
    }

    @LuaFunction(mainThread = true)
    public final Float getReactorTargetTemperature() {
        ReactorControllerBlockEntity entity = getReactorController();
        return entity == null ? -1 : entity.getReactorTargetTemperature();
    }

    @LuaFunction(mainThread = true)
    public final String getReactorNotification() {
        ReactorControllerBlockEntity entity = getReactorController();
        return entity == null ? "undefined" : entity.getNotification();
    }

    @LuaFunction(mainThread = true)
    public final float getReactorLoad() {
        ReactorControllerBlockEntity entity = getReactorController();
        return entity == null ? -1 : ((entity.getReactorCurrentTemperature() - 22) / 949) * 100;
    }

    @LuaFunction(mainThread = true)
    public final float getFuelRodStatus(int index) {
        ReactorControllerBlockEntity entity = getReactorController();
        if (entity == null || index < 0 || index > 80) return -1;
        return entity.getFuelRodStatus((byte) index);
    }

    @LuaFunction(mainThread = true)
    public final Object getFuelRodStatusMap() {
        ReactorControllerBlockEntity entity = getReactorController();
        if (entity == null) return new ArrayList<>();
        Map<Integer, Integer> map = new HashMap<>(81);
        for (int i = 0; i < 81; i++) {
            map.put(i + 1, (int) entity.getFuelRodStatus((byte) i));
        }
        return map;
    }

    @LuaFunction(mainThread = true)
    public final float getDepletedFuelRodStatus(int index) {
        ReactorControllerBlockEntity entity = getReactorController();
        if (entity == null || index < 0 || index > 80) return -1;
        return entity.getDepletedFuelRodStatus((byte) index);
    }

    @LuaFunction(mainThread = true)
    public final Object getDepletedFuelRodStatusMap() {
        ReactorControllerBlockEntity entity = getReactorController();
        if (entity == null) return new ArrayList<>();
        Map<Integer, Integer> map = new HashMap<>(81);
        for (int i = 0; i < 81; i++) {
            map.put(i + 1, (int) entity.getDepletedFuelRodStatus((byte) i));
        }
        return map;
    }

    @LuaFunction(mainThread = true)
    public final float getCurrentControlRodStatus(int index) {
        ReactorControllerBlockEntity entity = getReactorController();
        if (entity == null || index < 0 || index > 63) return -1;
        return entity.getControlRodStatus((byte) index);
    }
    @LuaFunction(mainThread = true)
    public final Object getCurrentControlRodStatusMap() {
        ReactorControllerBlockEntity entity = getReactorController();
        if (entity == null) return new ArrayList<>();
        Map<Integer, Integer> map = new HashMap<>(64);
        for (int i = 0; i < 64; i++) {
            map.put(i + 1, entity.getControlRodStatus((byte) i));
        }
        return map;
    }

    @LuaFunction(mainThread = true)
    public final float getTargetControlRodStatus(int index) {
        ReactorControllerBlockEntity entity = getReactorController();
        if (entity == null || index < 0 || index > 63) return -1;
        return entity.getTargetControlRodStatus((byte) index);
    }

    @LuaFunction(mainThread = true)
    public final Object getTargetControlRodStatusMap() {
        ReactorControllerBlockEntity entity = getReactorController();
        if (entity == null) return new ArrayList<>();
        Map<Integer, Integer> map = new HashMap<>(64);
        for (int i = 0; i < 64; i++) {
            map.put(i + 1, (int) entity.getTargetControlRodStatus((byte) i));
        }
        return map;
    }

    @LuaFunction(mainThread = true)
    public final void setTargetControlRodStatus(int index, int value) {
        ReactorControllerBlockEntity entity = getReactorController();
        if (entity == null || index < 0 || index > 63 || value < 0 || value > 100) return;
        entity.setTargetControlRodStatus((byte) index, (byte) value);
    }

    // TURBINE
    @LuaFunction(mainThread = true)
    public final boolean isTurbineActive(int id) {
        ReactorControllerBlockEntity entity = getReactorController();
        if (entity == null) return false;
        if (entity.getLevel().getBlockEntity(entity.getTurbinePos().get(id)) instanceof TurbineControllerBlockEntity controllerBlockEntity) {
            return controllerBlockEntity.isActivated();
        }
        return false;
    }

    @LuaFunction(mainThread = true)
    public final void setTurbineActive(int id, boolean state) {
        ReactorControllerBlockEntity entity = getReactorController();
        if (entity == null) return;
        if (entity.getLevel().getBlockEntity(entity.getTurbinePos().get(id)) instanceof TurbineControllerBlockEntity controllerBlockEntity) {
            controllerBlockEntity.setActivated(state);
        }
    }

    @LuaFunction(mainThread = true)
    public final boolean isTurbineCoilsEngaged(int id) {
        ReactorControllerBlockEntity entity = getReactorController();
        if (entity == null) return false;
        if (entity.getLevel().getBlockEntity(entity.getTurbinePos().get(id)) instanceof TurbineControllerBlockEntity controllerBlockEntity) {
            return controllerBlockEntity.isCoilsEngaged();
        }
        return false;
    }

    @LuaFunction(mainThread = true)
    public final void setTurbineCoilsEngaged(int id, boolean state) {
        ReactorControllerBlockEntity entity = getReactorController();
        if (entity == null) return;
        if (entity.getLevel().getBlockEntity(entity.getTurbinePos().get(id)) instanceof TurbineControllerBlockEntity controllerBlockEntity) {
            controllerBlockEntity.setCoilsEngaged(state);
        }
    }

    @LuaFunction(mainThread = true)
    public final void copyConfigurationToAllTurbines(int id) {
        ReactorControllerBlockEntity entity = getReactorController();
        if (entity == null) return;
        if (entity.getLevel().getBlockEntity(entity.getTurbinePos().get(id)) instanceof TurbineControllerBlockEntity controllerBlockEntity) {
            float flowrate = controllerBlockEntity.getTargetFlowrate();
            boolean engaged = controllerBlockEntity.isCoilsEngaged();
            boolean activated = controllerBlockEntity.isActivated();
            for (BlockPos turbinePos : entity.getTurbinePos()) {
                if (controllerBlockEntity.getLevel().getBlockEntity(turbinePos) instanceof TurbineControllerBlockEntity targetEntity) {
                    targetEntity.setTargetFlowrate(flowrate);
                    targetEntity.setActivated(activated);
                    targetEntity.setCoilsEngaged(engaged);
                }
            }
        }
    }

    @LuaFunction(mainThread = true)
    public final boolean isTurbineAssembled(int id) {
        ReactorControllerBlockEntity entity = getReactorController();
        if (entity == null) return false;
        TurbineControllerBlockEntity turbineEntity = getTurbineController(id);
        if (turbineEntity != null) {
            return turbineEntity.isAssembled();
        }
        return false;
    }

    @LuaFunction(mainThread = true)
    public final Integer getTurbineHeight(int id) {
        ReactorControllerBlockEntity entity = getReactorController();
        if (entity == null) return -1;
        TurbineControllerBlockEntity turbineEntity = getTurbineController(id);
        if (turbineEntity != null) {
            return turbineEntity.getTurbineHeight() + 1;
        }
        return -1;
    }

    @LuaFunction(mainThread = true)
    public final Float getTurbineCurrentFlow(int id) {
        ReactorControllerBlockEntity entity = getReactorController();
        if (entity == null) return -1f;
        TurbineControllerBlockEntity turbineEntity = getTurbineController(id);
        if (turbineEntity != null) {
            return turbineEntity.getCurrentFlowrate();
        }
        return -1f;
    }

    @LuaFunction(mainThread = true)
    public final Float getTurbineTargetFlow(int id) {
        ReactorControllerBlockEntity entity = getReactorController();
        if (entity == null) return -1f;
        TurbineControllerBlockEntity turbineEntity = getTurbineController(id);
        if (turbineEntity != null) {
            return turbineEntity.getTargetFlowrate();
        }
        return -1f;
    }

    @LuaFunction(mainThread = true)
    public final void setTurbineTargetFlow(int id, int flowrate) {
        ReactorControllerBlockEntity entity = getReactorController();
        if (entity == null) return;
        TurbineControllerBlockEntity turbineEntity = getTurbineController(id);
        if (turbineEntity != null) {
            turbineEntity.setTargetFlowrate(flowrate);
        }
    }

    @LuaFunction(mainThread = true)
    public final Float getTurbineGeneration(int id) {
        ReactorControllerBlockEntity entity = getReactorController();
        if (entity == null) return -1f;
        TurbineControllerBlockEntity turbineEntity = getTurbineController(id);
        if (turbineEntity != null) {
            return turbineEntity.getTurbineGeneration();
        }
        return -1f;
    }

    @LuaFunction(mainThread = true)
    public final Float getTurbineSpeed(int id) {
        ReactorControllerBlockEntity entity = getReactorController();
        if (entity == null) return -1f;
        TurbineControllerBlockEntity turbineEntity = getTurbineController(id);
        if (turbineEntity != null) {
            return turbineEntity.getRpm();
        }
        return -1f;
    }

    @LuaFunction(mainThread = true)
    public final Float getTurbineEnergyModifier(int id) {
        ReactorControllerBlockEntity entity = getReactorController();
        if (entity == null) return -1f;
        TurbineControllerBlockEntity turbineEntity = getTurbineController(id);
        if (turbineEntity != null) {
            return turbineEntity.getEnergyModifier();
        }
        return -1f;
    }

    @LuaFunction(mainThread = true)
    public final Object getTurbinePosition(int id) {
        ReactorControllerBlockEntity entity = getReactorController();
        if (entity == null) return new ArrayList<>();
        TurbineControllerBlockEntity turbineEntity = getTurbineController(id);
        if (turbineEntity != null) {
            return LuaUtil.posToObject(turbineEntity.getBlockPos());
        }
        return null;
    }

    @LuaFunction(mainThread = true)
    public final String getTurbinePositionString(int id) {
        ReactorControllerBlockEntity entity = getReactorController();
        if (entity == null) return "undefined";
        TurbineControllerBlockEntity turbineEntity = getTurbineController(id);
        if (turbineEntity != null) {
            return turbineEntity.getBlockPos().toString().replace("BlockPos{", "").replace("}", "");
        }
        return "undefined";
    }

    @LuaFunction(mainThread = true)
    public final List<Object> getTurbinePositions() {
        ReactorControllerBlockEntity entity = getReactorController();
        if (entity == null) return new ArrayList<>();

        List<Object> turbines = new ArrayList<>();
        for (int index = 0; index <= entity.getTurbinePos().size() - 1; index++) {
            BlockPos turbinePos = entity.getTurbinePos().get(index);
            turbines.add(LuaUtil.turbineToObject(turbinePos, index));
        }
        return turbines;
    }

    @LuaFunction(mainThread = true)
    public final Integer getTurbineCount() {
        ReactorControllerBlockEntity entity = getReactorController();
        return entity == null ? -1 : entity.getTurbinePos().size();
    }

    private ReactorControllerBlockEntity getReactorController() {
        if (tileEntity.getLevel().getBlockEntity(tileEntity.getControllerPos()) instanceof ReactorControllerBlockEntity reactorTileEntity) {
            return reactorTileEntity;
        }
        return null;
    }

    private TurbineControllerBlockEntity getTurbineController(int id) {
        ReactorControllerBlockEntity entity = getReactorController();
        if (entity.getLevel().getBlockEntity(entity.getTurbinePos().get(id)) instanceof TurbineControllerBlockEntity controllerBlockEntity) {
            return controllerBlockEntity;
        }
        return null;
    }

}
