package unhappycodings.thoriumreactors.common.util;

import net.minecraft.core.BlockPos;

import java.util.HashMap;
import java.util.Map;

public class LuaUtil {

    public static Object turbineToObject(BlockPos pos, int turbineId) {
        if (pos == null) return null;

        Map<String, Integer> map = new HashMap<>(4);
        map.put("x", pos.getX());
        map.put("y", pos.getY());
        map.put("z", pos.getZ());
        map.put("turbineId", turbineId);
        return map;
    }

    public static Object posToObject(BlockPos pos) {
        if (pos == null) return null;

        Map<String, Integer> map = new HashMap<>(4);
        map.put("x", pos.getX());
        map.put("y", pos.getY());
        map.put("z", pos.getZ());
        return map;
    }

}
