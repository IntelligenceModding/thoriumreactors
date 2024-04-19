package unhappycodings.thoriumreactors.common.registration;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.phys.Vec3;
import unhappycodings.thoriumreactors.common.capability.RadiationSavedData;
import unhappycodings.thoriumreactors.common.util.RadiationUtil;

import java.sql.Array;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ModCommands {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("thoriumreactors").requires((sourceStack) -> {
            return sourceStack.hasPermission(3);
        }).then(Commands.literal("radiation")
            .then(Commands.literal("add")
                .then(Commands.argument("radius", IntegerArgumentType.integer())
                    .then(Commands.argument("strength", FloatArgumentType.floatArg(0, 10000))
                        .executes(commandContext -> {
                            return RadiationUtil.setChunkData(commandContext.getSource(), IntegerArgumentType.getInteger(commandContext, "radius"), FloatArgumentType.getFloat(commandContext, "strength"));
                        }))))
            .then(Commands.literal("removeAll")
                .executes(commandContext -> {
                    return RadiationUtil.clearChunkData(commandContext.getSource());
                }
            ))
            .then(Commands.literal("clear")
                .then(Commands.argument("player", EntityArgument.player())
                    .executes(commandContext -> {
                        return RadiationUtil.clearPlayer(commandContext.getSource(), EntityArgument.getPlayer(commandContext, "player"));
                    })))
            .then(Commands.literal("clear")
                .executes(commandContext -> {
                    return RadiationUtil.clearPlayer(commandContext.getSource(), null);
                }))
        ));
    }

}
