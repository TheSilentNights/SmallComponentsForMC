package cn.thesilentnights.scfmc.networks;

import cn.thesilentnights.scfmc.networks.packets.OpenCheckPassword;
import cn.thesilentnights.scfmc.networks.packets.PasswordVerify;
import cn.thesilentnights.scfmc.repo.Statics;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetWork {
    private static final String PROTOCOL_VERSION = "0.0.1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            ResourceLocation.fromNamespaceAndPath(Statics.MOD_ID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void init() {
        CHANNEL.registerMessage(
                0,
                PasswordVerify.class,
                PasswordVerify::encode,
                PasswordVerify::decode,
                PasswordVerify::handle
        );
        CHANNEL.registerMessage(
                1,
                OpenCheckPassword.class,
                OpenCheckPassword::encode,
                OpenCheckPassword::decode,
                OpenCheckPassword::handle
        );

    }
}
