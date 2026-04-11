package cn.thesilentnights.scfmc;

import cn.thesilentnights.scfmc.networks.NetWork;
import cn.thesilentnights.scfmc.registry.BlockRegistry;
import cn.thesilentnights.scfmc.repo.Statics;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Statics.MOD_ID)
public class Scfmc {
    public Scfmc(FMLJavaModLoadingContext modLoadingContext) {
        BlockRegistry.init(modLoadingContext.getModEventBus());
        NetWork.init();
    }
}
