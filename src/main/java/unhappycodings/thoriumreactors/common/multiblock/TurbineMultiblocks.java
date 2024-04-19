package unhappycodings.thoriumreactors.common.multiblock;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import unhappycodings.thoriumreactors.common.registration.ModBlocks;

import java.util.ArrayList;
import java.util.List;

public class TurbineMultiblocks {

    private static final List<Block> TURBINE_5X5X10_3VENTS = List.of(
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_ROTATION_MOUNT.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_ROTOR.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_VOID, ModBlocks.TURBINE_ROTOR.get(), Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.AIR, ModBlocks.TURBINE_ROTOR.get(), Blocks.AIR, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.AIR, ModBlocks.TURBINE_ROTOR.get(), Blocks.AIR, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.AIR, ModBlocks.TURBINE_ROTOR.get(), Blocks.AIR, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.AIR, ModBlocks.TURBINE_ROTOR.get(), Blocks.AIR, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.AIR, ModBlocks.TURBINE_ROTOR.get(), Blocks.AIR, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.BARRIER, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK, Blocks.BARRIER, Blocks.AIR, ModBlocks.TURBINE_ROTOR.get(), Blocks.AIR, Blocks.BARRIER, Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.BARRIER, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_ROTATION_MOUNT.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get());

    private static final List<Block> TURBINE_5X5X9_3VENTS = List.of(
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_ROTATION_MOUNT.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_ROTOR.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_VOID, ModBlocks.TURBINE_ROTOR.get(), Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.AIR, ModBlocks.TURBINE_ROTOR.get(), Blocks.AIR, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.AIR, ModBlocks.TURBINE_ROTOR.get(), Blocks.AIR, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.AIR, ModBlocks.TURBINE_ROTOR.get(), Blocks.AIR, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.AIR, ModBlocks.TURBINE_ROTOR.get(), Blocks.AIR, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.BARRIER, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK, Blocks.BARRIER, Blocks.AIR, ModBlocks.TURBINE_ROTOR.get(), Blocks.AIR, Blocks.BARRIER, Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.BARRIER, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_ROTATION_MOUNT.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get());

    private static final List<Block> TURBINE_5X5X8_3VENTS = List.of(
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_ROTATION_MOUNT.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_ROTOR.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_VOID, ModBlocks.TURBINE_ROTOR.get(), Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.AIR, ModBlocks.TURBINE_ROTOR.get(), Blocks.AIR, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.AIR, ModBlocks.TURBINE_ROTOR.get(), Blocks.AIR, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.AIR, ModBlocks.TURBINE_ROTOR.get(), Blocks.AIR, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.BARRIER, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK, Blocks.BARRIER, Blocks.AIR, ModBlocks.TURBINE_ROTOR.get(), Blocks.AIR, Blocks.BARRIER, Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.BARRIER, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_ROTATION_MOUNT.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get());

    private static final List<Block> TURBINE_5X5X7_3VENTS = List.of(
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_ROTATION_MOUNT.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_ROTOR.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_VOID, ModBlocks.TURBINE_ROTOR.get(), Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.AIR, ModBlocks.TURBINE_ROTOR.get(), Blocks.AIR, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.AIR, ModBlocks.TURBINE_ROTOR.get(), Blocks.AIR, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.BARRIER, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK, Blocks.BARRIER, Blocks.AIR, ModBlocks.TURBINE_ROTOR.get(), Blocks.AIR, Blocks.BARRIER, Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.BARRIER, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_ROTATION_MOUNT.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get());

    private static final List<Block> TURBINE_5X5X10_3VENTS_HORIZONTAL_EAST = List.of(
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),

            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_VENT.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_VENT.get(),
            Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),

            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_ROTATION_MOUNT.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_VENT.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_ROTOR.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_VENT.get(),
            Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_VOID, ModBlocks.TURBINE_ROTOR.get(), Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, ModBlocks.TURBINE_ROTOR.get(), Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, ModBlocks.TURBINE_ROTOR.get(), Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, ModBlocks.TURBINE_ROTOR.get(), Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, ModBlocks.TURBINE_ROTOR.get(), Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, ModBlocks.TURBINE_ROTOR.get(), Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.BARRIER, Blocks.AIR, ModBlocks.TURBINE_ROTOR.get(), Blocks.AIR, Blocks.BARRIER,
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_ROTATION_MOUNT.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),

            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_VENT.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_VENT.get(),
            Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), Blocks.BARRIER, ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),

            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get());

    private static final List<Block> TURBINE_5X5X9_3VENTS_HORIZONTAL_EAST = List.of(
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),

            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_VENT.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_VENT.get(),
            Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),

            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_ROTATION_MOUNT.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_VENT.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_ROTOR.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_VENT.get(),
            Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_VOID, ModBlocks.TURBINE_ROTOR.get(), Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, ModBlocks.TURBINE_ROTOR.get(), Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, ModBlocks.TURBINE_ROTOR.get(), Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, ModBlocks.TURBINE_ROTOR.get(), Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, ModBlocks.TURBINE_ROTOR.get(), Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.BARRIER, Blocks.AIR, ModBlocks.TURBINE_ROTOR.get(), Blocks.AIR, Blocks.BARRIER,
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_ROTATION_MOUNT.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),

            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_VENT.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_VENT.get(),
            Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), Blocks.BARRIER, ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),

            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get());

    private static final List<Block> TURBINE_5X5X8_3VENTS_HORIZONTAL_EAST = List.of(
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),

            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_VENT.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_VENT.get(),
            Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),

            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_ROTATION_MOUNT.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_VENT.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_ROTOR.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_VENT.get(),
            Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_VOID, ModBlocks.TURBINE_ROTOR.get(), Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, ModBlocks.TURBINE_ROTOR.get(), Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, ModBlocks.TURBINE_ROTOR.get(), Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, ModBlocks.TURBINE_ROTOR.get(), Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.BARRIER, Blocks.AIR, ModBlocks.TURBINE_ROTOR.get(), Blocks.AIR, Blocks.BARRIER,
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_ROTATION_MOUNT.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),

            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_VENT.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_VENT.get(),
            Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), Blocks.BARRIER, ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),

            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get());

    private static final List<Block> TURBINE_5X5X7_3VENTS_HORIZONTAL_EAST = List.of(
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),

            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_VENT.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_VENT.get(),
            Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),

            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_ROTATION_MOUNT.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_VENT.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_ROTOR.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_VENT.get(),
            Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_VOID, ModBlocks.TURBINE_ROTOR.get(), Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, ModBlocks.TURBINE_ROTOR.get(), Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, ModBlocks.TURBINE_ROTOR.get(), Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.BARRIER, Blocks.AIR, ModBlocks.TURBINE_ROTOR.get(), Blocks.AIR, Blocks.BARRIER,
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_ROTATION_MOUNT.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),

            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_VENT.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_VENT.get(),
            Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), Blocks.BARRIER, ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),

            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get());

    public static final List<Block> TURBINE_5X5X10_3VENTS_HORIZONTAL_WEST = List.of(
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),

            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_BLOCK,
            ModBlocks.TURBINE_VENT.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_VENT.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),

            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_ROTATION_MOUNT.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),
            Blocks.BARRIER, Blocks.AIR, ModBlocks.TURBINE_ROTOR.get(), Blocks.AIR, Blocks.BARRIER,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, ModBlocks.TURBINE_ROTOR.get(), Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, ModBlocks.TURBINE_ROTOR.get(), Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, ModBlocks.TURBINE_ROTOR.get(), Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, ModBlocks.TURBINE_ROTOR.get(), Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, ModBlocks.TURBINE_ROTOR.get(), Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_VOID, ModBlocks.TURBINE_ROTOR.get(), Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_BLOCK,
            ModBlocks.TURBINE_VENT.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_ROTOR.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_VENT.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_ROTATION_MOUNT.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),

            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), Blocks.BARRIER, ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_BLOCK,
            ModBlocks.TURBINE_VENT.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_VENT.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),

            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get());

    public static final List<Block> TURBINE_5X5X9_3VENTS_HORIZONTAL_WEST = List.of(
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),

            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_BLOCK,
            ModBlocks.TURBINE_VENT.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_VENT.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),

            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_ROTATION_MOUNT.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),
            Blocks.BARRIER, Blocks.AIR, ModBlocks.TURBINE_ROTOR.get(), Blocks.AIR, Blocks.BARRIER,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, ModBlocks.TURBINE_ROTOR.get(), Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, ModBlocks.TURBINE_ROTOR.get(), Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, ModBlocks.TURBINE_ROTOR.get(), Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, ModBlocks.TURBINE_ROTOR.get(), Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_VOID, ModBlocks.TURBINE_ROTOR.get(), Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_BLOCK,
            ModBlocks.TURBINE_VENT.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_ROTOR.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_VENT.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_ROTATION_MOUNT.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),

            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), Blocks.BARRIER, ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_BLOCK,
            ModBlocks.TURBINE_VENT.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_VENT.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),

            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get());

    public static final List<Block> TURBINE_5X5X8_3VENTS_HORIZONTAL_WEST = List.of(
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),

            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_BLOCK,
            ModBlocks.TURBINE_VENT.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_VENT.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),

            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_ROTATION_MOUNT.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),
            Blocks.BARRIER, Blocks.AIR, ModBlocks.TURBINE_ROTOR.get(), Blocks.AIR, Blocks.BARRIER,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, ModBlocks.TURBINE_ROTOR.get(), Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, ModBlocks.TURBINE_ROTOR.get(), Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, ModBlocks.TURBINE_ROTOR.get(), Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_VOID, ModBlocks.TURBINE_ROTOR.get(), Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_BLOCK,
            ModBlocks.TURBINE_VENT.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_ROTOR.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_VENT.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_ROTATION_MOUNT.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),

            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), Blocks.BARRIER, ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_BLOCK,
            ModBlocks.TURBINE_VENT.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_VENT.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),

            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get());

    public static final List<Block> TURBINE_5X5X7_3VENTS_HORIZONTAL_WEST = List.of(
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),

            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_BLOCK,
            ModBlocks.TURBINE_VENT.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_VENT.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),

            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_ROTATION_MOUNT.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),
            Blocks.BARRIER, Blocks.AIR, ModBlocks.TURBINE_ROTOR.get(), Blocks.AIR, Blocks.BARRIER,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, ModBlocks.TURBINE_ROTOR.get(), Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, ModBlocks.TURBINE_ROTOR.get(), Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_VOID, ModBlocks.TURBINE_ROTOR.get(), Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_BLOCK,
            ModBlocks.TURBINE_VENT.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_ROTOR.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_VENT.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_ROTATION_MOUNT.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),

            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), Blocks.BARRIER, ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_BLOCK,
            Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_VOID, Blocks.STRUCTURE_BLOCK,
            ModBlocks.TURBINE_VENT.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_VENT.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),

            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get());

    public static final List<Block> TURBINE_5X5X10_3VENTS_HORIZONTAL_SOUTH = List.of(
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),

            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), Blocks.STRUCTURE_VOID, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), Blocks.STRUCTURE_VOID, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), Blocks.STRUCTURE_VOID, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),

            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.BARRIER, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), Blocks.STRUCTURE_VOID, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_ROTATION_MOUNT.get(), ModBlocks.TURBINE_ROTOR.get(), ModBlocks.TURBINE_ROTOR.get(), ModBlocks.TURBINE_ROTOR.get(), ModBlocks.TURBINE_ROTOR.get(), ModBlocks.TURBINE_ROTOR.get(), ModBlocks.TURBINE_ROTOR.get(), ModBlocks.TURBINE_ROTOR.get(), ModBlocks.TURBINE_ROTOR.get(), ModBlocks.TURBINE_ROTATION_MOUNT.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), Blocks.STRUCTURE_VOID, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.BARRIER, ModBlocks.TURBINE_CASING.get(),

            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), Blocks.STRUCTURE_VOID, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), Blocks.STRUCTURE_VOID, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.BARRIER,
            ModBlocks.TURBINE_CASING.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), Blocks.STRUCTURE_VOID, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),

            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get());

    public static final List<Block> TURBINE_5X5X9_3VENTS_HORIZONTAL_SOUTH = List.of(
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),

            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), Blocks.STRUCTURE_VOID, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), Blocks.STRUCTURE_VOID, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), Blocks.STRUCTURE_VOID, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),

            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.BARRIER, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), Blocks.STRUCTURE_VOID, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_ROTATION_MOUNT.get(), ModBlocks.TURBINE_ROTOR.get(), ModBlocks.TURBINE_ROTOR.get(), ModBlocks.TURBINE_ROTOR.get(), ModBlocks.TURBINE_ROTOR.get(), ModBlocks.TURBINE_ROTOR.get(), ModBlocks.TURBINE_ROTOR.get(), ModBlocks.TURBINE_ROTOR.get(), ModBlocks.TURBINE_ROTATION_MOUNT.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), Blocks.STRUCTURE_VOID, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.BARRIER, ModBlocks.TURBINE_CASING.get(),

            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), Blocks.STRUCTURE_VOID, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), Blocks.STRUCTURE_VOID, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.BARRIER,
            ModBlocks.TURBINE_CASING.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), Blocks.STRUCTURE_VOID, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),

            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get());

    public static final List<Block> TURBINE_5X5X8_3VENTS_HORIZONTAL_SOUTH = List.of(
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),

            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), Blocks.STRUCTURE_VOID, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), Blocks.STRUCTURE_VOID, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), Blocks.STRUCTURE_VOID, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),

            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.BARRIER, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), Blocks.STRUCTURE_VOID, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_ROTATION_MOUNT.get(), ModBlocks.TURBINE_ROTOR.get(), ModBlocks.TURBINE_ROTOR.get(), ModBlocks.TURBINE_ROTOR.get(), ModBlocks.TURBINE_ROTOR.get(), ModBlocks.TURBINE_ROTOR.get(), ModBlocks.TURBINE_ROTOR.get(), ModBlocks.TURBINE_ROTATION_MOUNT.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), Blocks.STRUCTURE_VOID, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.BARRIER, ModBlocks.TURBINE_CASING.get(),

            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), Blocks.STRUCTURE_VOID, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), Blocks.STRUCTURE_VOID, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.BARRIER,
            ModBlocks.TURBINE_CASING.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), Blocks.STRUCTURE_VOID, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),

            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get());

    public static final List<Block> TURBINE_5X5X7_3VENTS_HORIZONTAL_SOUTH = List.of(
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),

            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), Blocks.STRUCTURE_VOID, Blocks.AIR, Blocks.AIR, Blocks.AIR, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), Blocks.STRUCTURE_VOID, Blocks.AIR, Blocks.AIR, Blocks.AIR, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), Blocks.STRUCTURE_VOID, Blocks.AIR, Blocks.AIR, Blocks.AIR, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),

            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.BARRIER, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), Blocks.STRUCTURE_VOID, Blocks.AIR, Blocks.AIR, Blocks.AIR, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_ROTATION_MOUNT.get(), ModBlocks.TURBINE_ROTOR.get(), ModBlocks.TURBINE_ROTOR.get(), ModBlocks.TURBINE_ROTOR.get(), ModBlocks.TURBINE_ROTOR.get(), ModBlocks.TURBINE_ROTOR.get(), ModBlocks.TURBINE_ROTATION_MOUNT.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), Blocks.STRUCTURE_VOID, Blocks.AIR, Blocks.AIR, Blocks.AIR, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.BARRIER, ModBlocks.TURBINE_CASING.get(),

            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), Blocks.STRUCTURE_VOID, Blocks.AIR, Blocks.AIR, Blocks.AIR, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), Blocks.STRUCTURE_VOID, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.BARRIER,
            ModBlocks.TURBINE_CASING.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), Blocks.STRUCTURE_VOID, Blocks.AIR, Blocks.AIR, Blocks.AIR, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),

            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get());

    public static final List<Block> TURBINE_5X5X10_3VENTS_HORIZONTAL_NORTH = List.of(
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), 
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(), 
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(), 
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(), 
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), 
            
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(), 
            ModBlocks.TURBINE_CASING.get(), Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_VOID, ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_CASING.get(), 
            ModBlocks.TURBINE_CASING.get(), Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_VOID, ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_CASING.get(), 
            ModBlocks.TURBINE_CASING.get(), Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_VOID, ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_CASING.get(), 
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(), 
            
            ModBlocks.TURBINE_CASING.get(), Blocks.BARRIER, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_VOID, ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_ROTATION_MOUNT.get(), ModBlocks.TURBINE_ROTOR.get(), ModBlocks.TURBINE_ROTOR.get(), ModBlocks.TURBINE_ROTOR.get(), ModBlocks.TURBINE_ROTOR.get(), ModBlocks.TURBINE_ROTOR.get(), ModBlocks.TURBINE_ROTOR.get(), ModBlocks.TURBINE_ROTOR.get(), ModBlocks.TURBINE_ROTOR.get(), ModBlocks.TURBINE_ROTATION_MOUNT.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_VOID, ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.BARRIER, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(),

            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_VOID, ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_CASING.get(),
            Blocks.BARRIER, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_VOID, ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_VOID, ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(),

            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get());

    public static final List<Block> TURBINE_5X5X9_3VENTS_HORIZONTAL_NORTH = List.of(
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),

            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_VOID, ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_VOID, ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_VOID, ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(),

            ModBlocks.TURBINE_CASING.get(), Blocks.BARRIER, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_VOID, ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_ROTATION_MOUNT.get(), ModBlocks.TURBINE_ROTOR.get(), ModBlocks.TURBINE_ROTOR.get(), ModBlocks.TURBINE_ROTOR.get(), ModBlocks.TURBINE_ROTOR.get(), ModBlocks.TURBINE_ROTOR.get(), ModBlocks.TURBINE_ROTOR.get(), ModBlocks.TURBINE_ROTOR.get(), ModBlocks.TURBINE_ROTATION_MOUNT.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_VOID, ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.BARRIER, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(),

            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_VOID, ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_CASING.get(),
            Blocks.BARRIER, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_VOID, ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_VOID, ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(),

            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get());

    public static final List<Block> TURBINE_5X5X8_3VENTS_HORIZONTAL_NORTH = List.of(
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),

            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_VOID, ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_VOID, ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_VOID, ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(),

            ModBlocks.TURBINE_CASING.get(), Blocks.BARRIER, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_VOID, ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_ROTATION_MOUNT.get(), ModBlocks.TURBINE_ROTOR.get(), ModBlocks.TURBINE_ROTOR.get(), ModBlocks.TURBINE_ROTOR.get(), ModBlocks.TURBINE_ROTOR.get(), ModBlocks.TURBINE_ROTOR.get(), ModBlocks.TURBINE_ROTOR.get(), ModBlocks.TURBINE_ROTATION_MOUNT.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_VOID, ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.BARRIER, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(),

            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_VOID, ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_CASING.get(),
            Blocks.BARRIER, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_VOID, ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_VOID, ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(),

            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get());

    public static final List<Block> TURBINE_5X5X7_3VENTS_HORIZONTAL_NORTH = List.of(
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),

            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_VOID, ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_VOID, ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_VOID, ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(),

            ModBlocks.TURBINE_CASING.get(), Blocks.BARRIER, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_VOID, ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_ROTATION_MOUNT.get(), ModBlocks.TURBINE_ROTOR.get(), ModBlocks.TURBINE_ROTOR.get(), ModBlocks.TURBINE_ROTOR.get(), ModBlocks.TURBINE_ROTOR.get(), ModBlocks.TURBINE_ROTOR.get(), ModBlocks.TURBINE_ROTATION_MOUNT.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_VOID, ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.BARRIER, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(),

            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_VOID, ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_CASING.get(),
            Blocks.BARRIER, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_VOID, ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.STRUCTURE_VOID, ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(),

            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.STRUCTURE_BLOCK, ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get());

    public static List<Block> getTurbineFromSize(int size) {
        return switch (size) {
            case 7 -> TURBINE_5X5X9_3VENTS;
            case 6 -> TURBINE_5X5X8_3VENTS;
            case 5 -> TURBINE_5X5X7_3VENTS;
            default -> TURBINE_5X5X10_3VENTS;
        };
    }

    public static List<Block> getHorizontalTurbineFromSize(int size, Direction facing) {
        return switch (facing) {
            case EAST -> switch (size) {
                case 7 -> TURBINE_5X5X9_3VENTS_HORIZONTAL_EAST;
                case 6 -> TURBINE_5X5X8_3VENTS_HORIZONTAL_EAST;
                case 5 -> TURBINE_5X5X7_3VENTS_HORIZONTAL_EAST;
                default -> TURBINE_5X5X10_3VENTS_HORIZONTAL_EAST;
            };
            case WEST -> switch (size) {
                case 7 -> TURBINE_5X5X9_3VENTS_HORIZONTAL_WEST;
                case 6 -> TURBINE_5X5X8_3VENTS_HORIZONTAL_WEST;
                case 5 -> TURBINE_5X5X7_3VENTS_HORIZONTAL_WEST;
                default -> TURBINE_5X5X10_3VENTS_HORIZONTAL_WEST;
            };
            case SOUTH -> switch (size) {
                case 7 -> TURBINE_5X5X9_3VENTS_HORIZONTAL_SOUTH;
                case 6 -> TURBINE_5X5X8_3VENTS_HORIZONTAL_SOUTH;
                case 5 -> TURBINE_5X5X7_3VENTS_HORIZONTAL_SOUTH;
                default -> TURBINE_5X5X10_3VENTS_HORIZONTAL_SOUTH;
            };
            default -> switch (size) {
                case 7 -> TURBINE_5X5X9_3VENTS_HORIZONTAL_NORTH;
                case 6 -> TURBINE_5X5X8_3VENTS_HORIZONTAL_NORTH;
                case 5 -> TURBINE_5X5X7_3VENTS_HORIZONTAL_NORTH;
                default -> TURBINE_5X5X10_3VENTS_HORIZONTAL_NORTH;
            };
        };
    }

    public static boolean isTurbine(List<Block> targetTurbine, List<Block> turbineBlocks) {
        int valveCount = 0, controllerCount = 0, powerPortCount = 0;
        List<Block> moderatorBlocks = new ArrayList<>(9);
        if (targetTurbine.size() != turbineBlocks.size()) return false;
        for (int i = 0; i < targetTurbine.size(); i++) {
            if (targetTurbine.get(i) == Blocks.STRUCTURE_VOID && isTurbineModeratorPart(turbineBlocks.get(i))) {
                moderatorBlocks.add(turbineBlocks.get(i));
                continue;
            }

            if (targetTurbine.get(i) == Blocks.BARRIER && isTurbineWallPart(turbineBlocks.get(i))) {
                if (turbineBlocks.get(i) == ModBlocks.TURBINE_VALVE.get()) valveCount++;
                if (turbineBlocks.get(i) == ModBlocks.TURBINE_CONTROLLER_BLOCK.get()) controllerCount++;
                if (turbineBlocks.get(i) == ModBlocks.TURBINE_POWER_PORT.get()) powerPortCount++;
                continue;
            }

            if (targetTurbine.get(i) == Blocks.STRUCTURE_BLOCK && (turbineBlocks.get(i) == ModBlocks.TURBINE_GLASS.get() || turbineBlocks.get(i) == ModBlocks.TURBINE_CASING.get()))
                continue;

            if ((targetTurbine.get(i) != turbineBlocks.get(i))) {
                return false;
            }
        }
        return valveCount == 1 && controllerCount == 1 && powerPortCount == 1;
    }

    public static List<Block> getTurbineModeratorBLocks(List<Block> targetTurbine, List<Block> turbineBlocks) {
        List<Block> moderatorBlocks = new ArrayList<>(9);
        for (int i = 0; i < targetTurbine.size(); i++) {
            if (targetTurbine.get(i) == Blocks.STRUCTURE_VOID && isTurbineModeratorPart(turbineBlocks.get(i))) {
                moderatorBlocks.add(turbineBlocks.get(i));
            }

        }
        return moderatorBlocks;
    }

    public static boolean isTurbineModeratorPart(Block block) {
        return block == ModBlocks.NICKEL_BLOCK.get() || block == ModBlocks.NIOB_BLOCK.get() || block == ModBlocks.MOLYBDENUM_BLOCK.get();
    }

    public static boolean isTurbineWallPart(Block block) {
        return block == ModBlocks.TURBINE_VALVE.get() || block == ModBlocks.TURBINE_CONTROLLER_BLOCK.get() || block == ModBlocks.TURBINE_POWER_PORT.get() || block == ModBlocks.TURBINE_CASING.get() || block == ModBlocks.TURBINE_GLASS.get();
    }

}
