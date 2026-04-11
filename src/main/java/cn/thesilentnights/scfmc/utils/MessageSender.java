package cn.thesilentnights.scfmc.utils;


import com.mojang.brigadier.context.CommandContext;

import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;

public class MessageSender {
    public static void sendMessage(CommandContext<CommandSourceStack> pContext, String message, MessageType type){
        if (pContext.getSource().getPlayer() != null) {
            sendMessage(pContext.getSource().getPlayer(), message, type);
        }else{
            pContext.getSource().sendSystemMessage(serizeMessage(message, type));
        }
    }

    public static void sendMessage(Player p, String message, MessageType type){
        MutableComponent component = serizeMessage(message, type);
        p.sendSystemMessage(component);
    }

    private static MutableComponent serizeMessage(String message, MessageType type){
        MutableComponent component = Component.translatable(message);
        MutableComponent head = Component.literal("[SCFMC]:").withStyle(ChatFormatting.LIGHT_PURPLE);
        switch (type){
            case INFO:
                head.append(component.withStyle(ChatFormatting.GRAY));
                break;
            case ERROR:
                head.append(component.withStyle(ChatFormatting.RED));
                break;
            case SUCCESS:
                head.append(component.withStyle(ChatFormatting.GREEN));
                break;
        }
        return head;
        
    }

    public enum MessageType{
        INFO,
        ERROR,
        SUCCESS,
    }
    
}
