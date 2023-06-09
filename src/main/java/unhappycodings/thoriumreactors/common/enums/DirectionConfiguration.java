package unhappycodings.thoriumreactors.common.enums;

import net.minecraft.util.StringRepresentable;

public enum DirectionConfiguration implements StringRepresentable {
    STANDARD("standard"),
    ENABLED("enabled"),
    OUTPUT("output"),
    DISABLED("disabled");
    private static final DirectionConfiguration[] vals = values();
    private final String name;

    DirectionConfiguration(String name) {
        this.name = name;
    }

    public DirectionConfiguration next() {
        return vals[(this.ordinal() + 1) % vals.length];
    }

    @Override
    public String getSerializedName() {
        return name;
    }

    public static DirectionConfiguration get(String name) {
        for (DirectionConfiguration val : vals) {
            if (name.toLowerCase().equals(val.name)) {
                return val;
            }
        }
        return DirectionConfiguration.ENABLED;
    }
}