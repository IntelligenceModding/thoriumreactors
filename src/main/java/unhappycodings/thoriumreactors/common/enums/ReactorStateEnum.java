package unhappycodings.thoriumreactors.common.enums;

import net.minecraft.util.StringRepresentable;

public enum ReactorStateEnum implements StringRepresentable {
    STARTING("starting"),
    RUNNING("running"),
    STOP("stop");
    private static final ReactorStateEnum[] vals = values();
    private String name;

    ReactorStateEnum(String name) {
        this.name = name;
    }

    public ReactorStateEnum next() {
        return vals[(this.ordinal() + 1) % vals.length];
    }

    @Override
    public String getSerializedName() {
        return name;
    }

    public static ReactorStateEnum get(String name) {
        for (ReactorStateEnum val : vals) {
            if (name.toLowerCase().equals(val.name)) {
                return val;
            }
        }
        return ReactorStateEnum.STOP;
    }

}
