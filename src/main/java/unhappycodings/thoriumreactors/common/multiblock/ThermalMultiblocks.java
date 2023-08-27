package unhappycodings.thoriumreactors.common.multiblock;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import unhappycodings.thoriumreactors.common.registration.ModBlocks;

import java.util.List;

public class ThermalMultiblocks {

    private static final List<Block> THERMAL_3X5_WEST = List.of(
            ModBlocks.THERMAL_HEAT_SINK.get(), ModBlocks.THERMAL_HEAT_SINK.get(), ModBlocks.THERMAL_HEAT_SINK.get(), ModBlocks.THERMAL_HEAT_SINK.get(), ModBlocks.THERMAL_HEAT_SINK.get(),
            ModBlocks.THERMAL_HEAT_SINK.get(), ModBlocks.THERMAL_HEAT_SINK.get(), ModBlocks.THERMAL_HEAT_SINK.get(), ModBlocks.THERMAL_HEAT_SINK.get(), ModBlocks.THERMAL_HEAT_SINK.get(),
            ModBlocks.THERMAL_HEAT_SINK.get(), ModBlocks.THERMAL_HEAT_SINK.get(), ModBlocks.THERMAL_HEAT_SINK.get(), ModBlocks.THERMAL_HEAT_SINK.get(), ModBlocks.THERMAL_HEAT_SINK.get(),
            ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_CONTROLLER.get(), ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_CONDUCTOR.get(),
            ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_CONDUCTOR.get(),
            ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_CONDUCTOR.get(),
            ModBlocks.THERMAL_VALVE.get(), ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_VALVE.get(),
            ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_CONDUCTOR.get(),
            ModBlocks.THERMAL_VALVE.get(), ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_VALVE.get());

    private static final List<Block> THERMAL_3X5_NORTH = List.of(
            ModBlocks.THERMAL_HEAT_SINK.get(), ModBlocks.THERMAL_HEAT_SINK.get(), ModBlocks.THERMAL_HEAT_SINK.get(), ModBlocks.THERMAL_HEAT_SINK.get(), ModBlocks.THERMAL_HEAT_SINK.get(),
            ModBlocks.THERMAL_HEAT_SINK.get(), ModBlocks.THERMAL_HEAT_SINK.get(), ModBlocks.THERMAL_HEAT_SINK.get(), ModBlocks.THERMAL_HEAT_SINK.get(), ModBlocks.THERMAL_HEAT_SINK.get(),
            ModBlocks.THERMAL_HEAT_SINK.get(), ModBlocks.THERMAL_HEAT_SINK.get(), ModBlocks.THERMAL_HEAT_SINK.get(), ModBlocks.THERMAL_HEAT_SINK.get(), ModBlocks.THERMAL_HEAT_SINK.get(),
            ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_CONDUCTOR.get(),
            ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_CONTROLLER.get(), ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_CONDUCTOR.get(),
            ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_CONDUCTOR.get(),
            ModBlocks.THERMAL_VALVE.get(), ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_VALVE.get(), ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_CONDUCTOR.get(),
            ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_CONDUCTOR.get(),
            ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_VALVE.get(), ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_VALVE.get());

    private static final List<Block> THERMAL_3X5_EAST = List.of(
            ModBlocks.THERMAL_HEAT_SINK.get(), ModBlocks.THERMAL_HEAT_SINK.get(), ModBlocks.THERMAL_HEAT_SINK.get(), ModBlocks.THERMAL_HEAT_SINK.get(), ModBlocks.THERMAL_HEAT_SINK.get(),
            ModBlocks.THERMAL_HEAT_SINK.get(), ModBlocks.THERMAL_HEAT_SINK.get(), ModBlocks.THERMAL_HEAT_SINK.get(), ModBlocks.THERMAL_HEAT_SINK.get(), ModBlocks.THERMAL_HEAT_SINK.get(),
            ModBlocks.THERMAL_HEAT_SINK.get(), ModBlocks.THERMAL_HEAT_SINK.get(), ModBlocks.THERMAL_HEAT_SINK.get(), ModBlocks.THERMAL_HEAT_SINK.get(), ModBlocks.THERMAL_HEAT_SINK.get(),
            ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_CONDUCTOR.get(),
            ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_CONDUCTOR.get(),
            ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_CONTROLLER.get(), ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_CONDUCTOR.get(),
            ModBlocks.THERMAL_VALVE.get(), ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_VALVE.get(),
            ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_CONDUCTOR.get(),
            ModBlocks.THERMAL_VALVE.get(), ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_VALVE.get());

    private static final List<Block> THERMAL_3X5_SOUTH = List.of(
            ModBlocks.THERMAL_HEAT_SINK.get(), ModBlocks.THERMAL_HEAT_SINK.get(), ModBlocks.THERMAL_HEAT_SINK.get(), ModBlocks.THERMAL_HEAT_SINK.get(), ModBlocks.THERMAL_HEAT_SINK.get(),
            ModBlocks.THERMAL_HEAT_SINK.get(), ModBlocks.THERMAL_HEAT_SINK.get(), ModBlocks.THERMAL_HEAT_SINK.get(), ModBlocks.THERMAL_HEAT_SINK.get(), ModBlocks.THERMAL_HEAT_SINK.get(),
            ModBlocks.THERMAL_HEAT_SINK.get(), ModBlocks.THERMAL_HEAT_SINK.get(), ModBlocks.THERMAL_HEAT_SINK.get(), ModBlocks.THERMAL_HEAT_SINK.get(), ModBlocks.THERMAL_HEAT_SINK.get(),
            ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_CONDUCTOR.get(),
            ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_CONTROLLER.get(), ModBlocks.THERMAL_CONDUCTOR.get(),
            ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_CONDUCTOR.get(),
            ModBlocks.THERMAL_VALVE.get(), ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_VALVE.get(), ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_CONDUCTOR.get(),
            ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_CONDUCTOR.get(),
            ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_VALVE.get(), ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_VALVE.get());

    public static List<Block> getThermalFromDirection(Direction direction) {
        return switch (direction) {
            case NORTH -> THERMAL_3X5_NORTH;
            case EAST -> THERMAL_3X5_EAST;
            case SOUTH -> THERMAL_3X5_SOUTH;
            default -> THERMAL_3X5_WEST;
        };
    }

    public static boolean isThermal(List<Block> targetThermal, List<Block> thermalBlocks) {
        if (targetThermal.size() != thermalBlocks.size()) return false;
        for (int i = 0; i < targetThermal.size(); i++) {
            if ((targetThermal.get(i) == Blocks.BARRIER))
                continue;
            if ((targetThermal.get(i) != thermalBlocks.get(i)))
                return false;
        }
        return true;
    }

}
