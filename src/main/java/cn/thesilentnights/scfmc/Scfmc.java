package cn.thesilentnights.scfmc;

import cn.thesilentnights.scfmc.command.ICommands;
import cn.thesilentnights.scfmc.networks.NetWork;
import cn.thesilentnights.scfmc.registry.BlockRegistry;
import cn.thesilentnights.scfmc.registry.CommandRegistry;
import cn.thesilentnights.scfmc.registry.ItemRegistry;
import cn.thesilentnights.scfmc.registry.SoundRegistry;
import cn.thesilentnights.scfmc.repo.Statics;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Statics.MOD_ID)
public class Scfmc {
    public Scfmc(FMLJavaModLoadingContext modLoadingContext) {
        SoundRegistry.init(modLoadingContext.getModEventBus());
        BlockRegistry.init(modLoadingContext.getModEventBus());
        ItemRegistry.registerItems(modLoadingContext.getModEventBus());
        new CommandRegistry(MinecraftForge.EVENT_BUS);
        NetWork.init();
    }


}
