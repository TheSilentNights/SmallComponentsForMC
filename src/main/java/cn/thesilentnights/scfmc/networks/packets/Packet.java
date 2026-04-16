package cn.thesilentnights.scfmc.networks.packets;

import java.util.function.Supplier;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

public interface Packet {

    public abstract void encode(FriendlyByteBuf buf);
    public abstract void handle(Supplier<NetworkEvent.Context> ctx);
} 
