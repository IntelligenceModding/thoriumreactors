package unhappycodings.thoriumreactors.common.enums;

import net.minecraft.util.StringRepresentable;

public enum ReactorState implements StringRepresentable {
    STARTING("starting"),
    RUNNING("running"),
    STOP("stop");
    private static final ReactorState[] vals = values();
    private final String name;

    ReactorState(String name) {
        this.name = name;
    }

    public ReactorState next() {
        return vals[(this.ordinal() + 1) % vals.length];
    }

    @Override
    public String getSerializedName() {
        return name;
    }

    public static ReactorState get(String name) {
        for (ReactorState val : vals) {
            if (name.toLowerCase().equals(val.name)) {
                return val;
            }
        }
        return ReactorState.STOP;
    }

}
