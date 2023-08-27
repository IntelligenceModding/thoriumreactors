package unhappycodings.thoriumreactors.common.enums;

import net.minecraft.util.StringRepresentable;

public enum ThermalValveTypeEnum implements StringRepresentable {
    COOLANT_INPUT("coolant_input"),
    COOLANT_OUTPUT("coolant_output"),
    HEATING_FLUID_INPUT("heating_fluid_input"),
    HEATING_FLUID_OUTPUT("heating_fluid_output");

    private static final ThermalValveTypeEnum[] vals = values();
    private String name;

    ThermalValveTypeEnum(String name) {
        this.name = name;
    }

    public ThermalValveTypeEnum next() {
        return vals[(this.ordinal() + 1) % vals.length];
    }

    @Override
    public String getSerializedName() {
        return name;
    }
}
