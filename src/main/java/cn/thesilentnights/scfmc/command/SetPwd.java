package cn.thesilentnights.scfmc.command;


import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import cn.thesilentnights.scfmc.functions.apis.Lockable;
import cn.thesilentnights.scfmc.items.blockentity.AbstractLockableChestEntity;
import cn.thesilentnights.scfmc.utils.MessageSender;
import cn.thesilentnights.scfmc.utils.RandomGenerator;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

public class SetPwd implements ICommands {
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

                                if (serverLevel.getBlockEntity(pos) instanceof Lockable lockable) {
                                    lockable.setPassword(new RandomGenerator().numeric(passwordLength));
                                    MessageSender.sendMessage(context, "Password set to " + lockable.getPassword(), MessageSender.MessageType.SUCCESS);
                                }else{
                                    MessageSender.sendMessage(context, "This block is not lockable!", MessageSender.MessageType.ERROR);
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

                                if (serverLevel.getBlockEntity(pos) instanceof Lockable lockable) {
                                    lockable.setPassword(String.valueOf(password));
                                    MessageSender.sendMessage(context, "Password set to " + password, MessageSender.MessageType.SUCCESS);
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
