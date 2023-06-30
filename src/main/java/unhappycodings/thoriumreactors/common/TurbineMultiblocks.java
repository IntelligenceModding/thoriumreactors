package unhappycodings.thoriumreactors.common;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import unhappycodings.thoriumreactors.common.registration.ModBlocks;

import java.util.List;

public class TurbineMultiblocks {

    private static final List<Block> TURBINE_5X5X10_3VENTS = List.of(
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_ROTATION_MOUNT.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_ROTOR.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.NICKEL_BLOCK.get(), ModBlocks.NICKEL_BLOCK.get(), ModBlocks.NICKEL_BLOCK.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.NICKEL_BLOCK.get(), ModBlocks.TURBINE_ROTOR.get(), ModBlocks.NICKEL_BLOCK.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.NICKEL_BLOCK.get(), ModBlocks.NICKEL_BLOCK.get(), ModBlocks.NICKEL_BLOCK.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_GLASS.get(), Blocks.AIR, Blocks.AIR, Blocks.AIR, ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), Blocks.AIR, ModBlocks.TURBINE_ROTOR.get(), Blocks.AIR, ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), Blocks.AIR, Blocks.AIR, Blocks.AIR, ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_GLASS.get(), Blocks.AIR, Blocks.AIR, Blocks.AIR, ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), Blocks.AIR, ModBlocks.TURBINE_ROTOR.get(), Blocks.AIR, ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), Blocks.AIR, Blocks.AIR, Blocks.AIR, ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_GLASS.get(), Blocks.AIR, Blocks.AIR, Blocks.AIR, ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), Blocks.AIR, ModBlocks.TURBINE_ROTOR.get(), Blocks.AIR, ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), Blocks.AIR, Blocks.AIR, Blocks.AIR, ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_GLASS.get(), Blocks.AIR, Blocks.AIR, Blocks.AIR, ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), Blocks.AIR, ModBlocks.TURBINE_ROTOR.get(), Blocks.AIR, ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), Blocks.AIR, Blocks.AIR, Blocks.AIR, ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_GLASS.get(), Blocks.AIR, Blocks.AIR, Blocks.AIR, ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), Blocks.AIR, ModBlocks.TURBINE_ROTOR.get(), Blocks.AIR, ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), Blocks.AIR, Blocks.AIR, Blocks.AIR, ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_GLASS.get(), Blocks.BARRIER, ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_GLASS.get(), Blocks.AIR, Blocks.AIR, Blocks.AIR, ModBlocks.TURBINE_GLASS.get(), Blocks.BARRIER, Blocks.AIR, ModBlocks.TURBINE_ROTOR.get(), Blocks.AIR, Blocks.BARRIER, ModBlocks.TURBINE_GLASS.get(), Blocks.AIR, Blocks.AIR, Blocks.AIR, ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_GLASS.get(), Blocks.BARRIER, ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_ROTATION_MOUNT.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get());

    private static final List<Block> TURBINE_5X5X9_3VENTS = List.of(
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_ROTATION_MOUNT.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_ROTOR.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.NICKEL_BLOCK.get(), ModBlocks.NICKEL_BLOCK.get(), ModBlocks.NICKEL_BLOCK.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.NICKEL_BLOCK.get(), ModBlocks.TURBINE_ROTOR.get(), ModBlocks.NICKEL_BLOCK.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.NICKEL_BLOCK.get(), ModBlocks.NICKEL_BLOCK.get(), ModBlocks.NICKEL_BLOCK.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_GLASS.get(), Blocks.AIR, Blocks.AIR, Blocks.AIR, ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), Blocks.AIR, ModBlocks.TURBINE_ROTOR.get(), Blocks.AIR, ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), Blocks.AIR, Blocks.AIR, Blocks.AIR, ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_GLASS.get(), Blocks.AIR, Blocks.AIR, Blocks.AIR, ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), Blocks.AIR, ModBlocks.TURBINE_ROTOR.get(), Blocks.AIR, ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), Blocks.AIR, Blocks.AIR, Blocks.AIR, ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_GLASS.get(), Blocks.AIR, Blocks.AIR, Blocks.AIR, ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), Blocks.AIR, ModBlocks.TURBINE_ROTOR.get(), Blocks.AIR, ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), Blocks.AIR, Blocks.AIR, Blocks.AIR, ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_GLASS.get(), Blocks.AIR, Blocks.AIR, Blocks.AIR, ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), Blocks.AIR, ModBlocks.TURBINE_ROTOR.get(), Blocks.AIR, ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), Blocks.AIR, Blocks.AIR, Blocks.AIR, ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_GLASS.get(), Blocks.BARRIER, ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_GLASS.get(), Blocks.AIR, Blocks.AIR, Blocks.AIR, ModBlocks.TURBINE_GLASS.get(), Blocks.BARRIER, Blocks.AIR, ModBlocks.TURBINE_ROTOR.get(), Blocks.AIR, Blocks.BARRIER, ModBlocks.TURBINE_GLASS.get(), Blocks.AIR, Blocks.AIR, Blocks.AIR, ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_GLASS.get(), Blocks.BARRIER, ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_ROTATION_MOUNT.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get());

    private static final List<Block> TURBINE_5X5X8_3VENTS = List.of(
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_ROTATION_MOUNT.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_ROTOR.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.NICKEL_BLOCK.get(), ModBlocks.NICKEL_BLOCK.get(), ModBlocks.NICKEL_BLOCK.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.NICKEL_BLOCK.get(), ModBlocks.TURBINE_ROTOR.get(), ModBlocks.NICKEL_BLOCK.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.NICKEL_BLOCK.get(), ModBlocks.NICKEL_BLOCK.get(), ModBlocks.NICKEL_BLOCK.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_GLASS.get(), Blocks.AIR, Blocks.AIR, Blocks.AIR, ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), Blocks.AIR, ModBlocks.TURBINE_ROTOR.get(), Blocks.AIR, ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), Blocks.AIR, Blocks.AIR, Blocks.AIR, ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_GLASS.get(), Blocks.AIR, Blocks.AIR, Blocks.AIR, ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), Blocks.AIR, ModBlocks.TURBINE_ROTOR.get(), Blocks.AIR, ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), Blocks.AIR, Blocks.AIR, Blocks.AIR, ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_GLASS.get(), Blocks.AIR, Blocks.AIR, Blocks.AIR, ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), Blocks.AIR, ModBlocks.TURBINE_ROTOR.get(), Blocks.AIR, ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), Blocks.AIR, Blocks.AIR, Blocks.AIR, ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_GLASS.get(), Blocks.BARRIER, ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_GLASS.get(), Blocks.AIR, Blocks.AIR, Blocks.AIR, ModBlocks.TURBINE_GLASS.get(), Blocks.BARRIER, Blocks.AIR, ModBlocks.TURBINE_ROTOR.get(), Blocks.AIR, Blocks.BARRIER, ModBlocks.TURBINE_GLASS.get(), Blocks.AIR, Blocks.AIR, Blocks.AIR, ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_GLASS.get(), Blocks.BARRIER, ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_ROTATION_MOUNT.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get());

    private static final List<Block> TURBINE_5X5X7_3VENTS = List.of(
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_ROTATION_MOUNT.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_ROTOR.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.ELECTROMAGNETIC_COIL.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_VENT.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.NICKEL_BLOCK.get(), ModBlocks.NICKEL_BLOCK.get(), ModBlocks.NICKEL_BLOCK.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.NICKEL_BLOCK.get(), ModBlocks.TURBINE_ROTOR.get(), ModBlocks.NICKEL_BLOCK.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.NICKEL_BLOCK.get(), ModBlocks.NICKEL_BLOCK.get(), ModBlocks.NICKEL_BLOCK.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_GLASS.get(), Blocks.AIR, Blocks.AIR, Blocks.AIR, ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), Blocks.AIR, ModBlocks.TURBINE_ROTOR.get(), Blocks.AIR, ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), Blocks.AIR, Blocks.AIR, Blocks.AIR, ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_GLASS.get(), Blocks.AIR, Blocks.AIR, Blocks.AIR, ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), Blocks.AIR, ModBlocks.TURBINE_ROTOR.get(), Blocks.AIR, ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), Blocks.AIR, Blocks.AIR, Blocks.AIR, ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_GLASS.get(), Blocks.BARRIER, ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_GLASS.get(), Blocks.AIR, Blocks.AIR, Blocks.AIR, ModBlocks.TURBINE_GLASS.get(), Blocks.BARRIER, Blocks.AIR, ModBlocks.TURBINE_ROTOR.get(), Blocks.AIR, Blocks.BARRIER, ModBlocks.TURBINE_GLASS.get(), Blocks.AIR, Blocks.AIR, Blocks.AIR, ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_GLASS.get(), Blocks.BARRIER, ModBlocks.TURBINE_GLASS.get(), ModBlocks.TURBINE_CASING.get(),
            ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_ROTATION_MOUNT.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get(), ModBlocks.TURBINE_CASING.get());

    public static boolean isTurbine5x5x10_3Vents(List<Block> blocks) {
        return isTurbine(TURBINE_5X5X10_3VENTS, blocks);
    }

    public static List<Block> getTurbineFromSize(int size) {
        return switch (size) {
            case 7 -> TURBINE_5X5X9_3VENTS;
            case 6 -> TURBINE_5X5X8_3VENTS;
            case 5 -> TURBINE_5X5X7_3VENTS;
            default -> TURBINE_5X5X10_3VENTS;
        };
    }

    public static boolean isTurbine5x5x9_3Vents(List<Block> blocks) {
        return isTurbine(TURBINE_5X5X9_3VENTS, blocks);
    }

    public static boolean isTurbine(List<Block> targetTurbine, List<Block> turbineBlocks) {
        if (targetTurbine.size() != turbineBlocks.size()) return false;
        for (int i = 0; i < targetTurbine.size(); i++) {
            if ((targetTurbine.get(i) == Blocks.BARRIER && isTurbineWallPart(turbineBlocks.get(i))))
                continue;
            if ((targetTurbine.get(i) != turbineBlocks.get(i)))
                return false;
        }
        return true;
    }

    public static boolean isTurbineWallPart(Block block) {
        return block == ModBlocks.TURBINE_VALVE.get() || block == ModBlocks.TURBINE_CONTROLLER_BLOCK.get() || block == ModBlocks.TURBINE_POWER_PORT.get() || block == ModBlocks.TURBINE_CASING.get() || block == ModBlocks.TURBINE_GLASS.get();
    }

}
