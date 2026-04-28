package cn.thesilentnights.scfmc.registry;

import cn.thesilentnights.scfmc.command.ICommands;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * Command registry
 */
public class CommandRegistry {

    public CommandRegistry(IEventBus eventBus) {
        eventBus.register(this);
    }

    @SubscribeEvent
    public void registerCommands(RegisterCommandsEvent event) {
        ICommands.registerAll(event.getDispatcher());
    }

    
}
