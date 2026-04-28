package cn.thesilentnights.scfmc.networks;

import java.util.function.Function;

import cn.thesilentnights.scfmc.networks.packets.Packet;
import cn.thesilentnights.scfmc.networks.packets.client.OpenCheckPassword;
import cn.thesilentnights.scfmc.networks.packets.server.PasswordVerify;
import cn.thesilentnights.scfmc.repo.Statics;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetWork {
        private static final String PROTOCOL_VERSION = "0.0.1";
        public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
                        ResourceLocation.fromNamespaceAndPath(Statics.MOD_ID, "main"),
                        () -> PROTOCOL_VERSION,
                        PROTOCOL_VERSION::equals,
                        PROTOCOL_VERSION::equals);

        public static void init() {
                registerMessage(0, PasswordVerify.class,PasswordVerify::new);
                registerMessage(1, OpenCheckPassword.class,OpenCheckPassword::new);
        }

        private static <T extends Packet> void registerMessage(int id, Class<T> packetClass,Function<FriendlyByteBuf,T> decoder) {
                CHANNEL.messageBuilder(packetClass, id)
                                .encoder((msg, buf) -> {
                                        msg.encode(buf);
                                })
                                .decoder(decoder)
                                .consumerMainThread((msg, ctx) -> {
                                        msg.handle(ctx);
                                }).add();
        }
}
