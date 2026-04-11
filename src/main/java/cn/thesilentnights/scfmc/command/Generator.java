package cn.thesilentnights.scfmc.command;


import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import cn.thesilentnights.scfmc.items.blockentity.LockableChestEntity;
import cn.thesilentnights.scfmc.utils.RandomGenerator;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

public class Generator implements ICommands {
    @Override
    public void register(CommandDispatcher<CommandSourceStack> pDispatcher, LiteralArgumentBuilder<CommandSourceStack> pMainNode) {
        var node = pMainNode.then(
            Commands.literal("setPassword").then(
                Commands.argument("pos", BlockPosArgument.blockPos()).then(
                    Commands.literal("random").then(
                        Commands.argument("passwordLength", IntegerArgumentType.integer()).executes(
                            context->{
                                int passwordLength = IntegerArgumentType.getInteger(context, "passwordLength");
                                BlockPos pos = BlockPosArgument.getBlockPos(context, "pos");
                                
                                ServerLevel serverLevel = context.getSource().getLevel();

                                if (serverLevel.getBlockEntity(pos) instanceof LockableChestEntity entity) {
                                    entity.setPassword(new RandomGenerator().numeric(passwordLength));
                                }

                                return 1;
                            }
                        )
                    )
                ).then(
                    Commands.literal("certain").then(
                        Commands.argument("password", IntegerArgumentType.integer()).executes(
                            context->{
                                int password = IntegerArgumentType.getInteger(context, "password");
                                BlockPos pos = BlockPosArgument.getBlockPos(context, "pos");
                                
                                ServerLevel serverLevel = context.getSource().getLevel();

                                if (serverLevel.getBlockEntity(pos) instanceof LockableChestEntity entity) {
                                    entity.setPassword(String.valueOf(password));
                                }

                                return 1;
                            }
                        )
                    )
                )
            )
        );

        pDispatcher.register(node);
    }

}
