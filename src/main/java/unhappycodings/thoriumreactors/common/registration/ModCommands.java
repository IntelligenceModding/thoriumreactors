package unhappycodings.thoriumreactors.common.registration;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.moddiscovery.ModFileInfo;
import net.minecraftforge.fml.loading.moddiscovery.ModInfo;
import net.minecraftforge.forgespi.language.IModInfo;
import net.minecraftforge.network.ServerStatusPing;
import unhappycodings.thoriumreactors.ThoriumReactors;
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
        dispatcher.register(Commands.literal("thoriumreactors")
        .then(Commands.literal("radiation")
            .then(Commands.literal("add").requires((sourceStack) -> sourceStack.hasPermission(3))
                .then(Commands.literal("chunk")
                    .then(Commands.argument("radius", IntegerArgumentType.integer())
                        .then(Commands.argument("strength", FloatArgumentType.floatArg(0, 10000))
                            .executes(commandContext -> RadiationUtil.setChunkData(commandContext.getSource(), IntegerArgumentType.getInteger(commandContext, "radius"), FloatArgumentType.getFloat(commandContext, "strength"))))))
                .then(Commands.literal("player")
                    .then(Commands.argument("player", EntityArgument.player())
                        .then(Commands.argument("strength", FloatArgumentType.floatArg(0, 10000))
                            .executes(commandContext -> RadiationUtil.addPlayer(commandContext.getSource(), EntityArgument.getPlayer(commandContext, "player"), FloatArgumentType.getFloat(commandContext, "strength")))))))

            .then(Commands.literal("remove").requires((sourceStack) -> sourceStack.hasPermission(3))
                .then(Commands.literal("chunk")
                    .then(Commands.literal("all")
                        .executes(commandContext -> RadiationUtil.clearChunkData(commandContext.getSource()))))
                .then(Commands.literal("player")
                    .then(Commands.argument("player", EntityArgument.player())
                        .then(Commands.argument("strength", FloatArgumentType.floatArg(0, 10000))
                            .executes(commandContext -> RadiationUtil.removePlayer(commandContext.getSource(), EntityArgument.getPlayer(commandContext, "player"), FloatArgumentType.getFloat(commandContext, "strength")))))))


            .then(Commands.literal("set").requires((sourceStack) -> sourceStack.hasPermission(3))
                .then(Commands.literal("player")
                    .then(Commands.argument("player", EntityArgument.player())
                        .then(Commands.argument("strength", FloatArgumentType.floatArg(0, 10000))
                            .executes(commandContext -> RadiationUtil.setPlayer(commandContext.getSource(), EntityArgument.getPlayer(commandContext, "player"), FloatArgumentType.getFloat(commandContext, "strength")))))))

            .then(Commands.literal("clear").requires((sourceStack) -> sourceStack.hasPermission(3))
                .then(Commands.argument("player", EntityArgument.player())
                    .executes(commandContext -> RadiationUtil.clearPlayer(commandContext.getSource(), EntityArgument.getPlayer(commandContext, "player")))))
            .then(Commands.literal("clear").requires((sourceStack) -> sourceStack.hasPermission(3))
                .executes(commandContext -> RadiationUtil.clearPlayer(commandContext.getSource(), null)))
        ).then(Commands.literal("version")
            .executes(commandContext -> {
                List<IModInfo> mods = ModList.get().getMods();

                commandContext.getSource().sendSystemMessage(Component.literal("You are on thoriumreactors-" + SharedConstants.getCurrentVersion().getName() + "-" + mods.stream().filter((iModInfo -> iModInfo.getModId().equals(ThoriumReactors.MOD_ID))).findFirst().get().getVersion()));
                return 1;
            })));

    }

}
