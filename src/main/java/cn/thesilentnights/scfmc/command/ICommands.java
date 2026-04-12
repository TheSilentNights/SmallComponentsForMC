package cn.thesilentnights.scfmc.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public interface ICommands {
    void register(CommandDispatcher<CommandSourceStack> pDispatcher, LiteralArgumentBuilder<CommandSourceStack> pMainNode);

    static void registerAll(CommandDispatcher<CommandSourceStack> pDispatcher) {
        LiteralArgumentBuilder<CommandSourceStack> mainNode = Commands.literal("scfmc");
        new SetPwd().register(pDispatcher, mainNode);
    }

}
