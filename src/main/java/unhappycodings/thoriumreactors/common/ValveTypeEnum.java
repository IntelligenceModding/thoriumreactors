package unhappycodings.thoriumreactors.common;

import net.minecraft.util.StringRepresentable;

public enum ValveTypeEnum implements StringRepresentable {
    ITEM_INPUT("item_input"),
    ITEM_OUTPUT("item_output"),
    FLUID_INPUT("fluid_input"),
    FLUID_OUTPUT("fluid_output");
    private static final ValveTypeEnum[] vals = values();
    private String name;

    ValveTypeEnum(String name) {
        this.name = name;
    }

    public ValveTypeEnum next() {
        return vals[(this.ordinal() + 1) % vals.length];
    }

    @Override
    public String getSerializedName() {
        return name;
    }
}
