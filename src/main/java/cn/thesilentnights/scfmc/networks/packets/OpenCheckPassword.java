package cn.thesilentnights.scfmc.networks.packets;

import cn.thesilentnights.scfmc.functions.apis.Lockable;
import cn.thesilentnights.scfmc.menu.CheckPassword;
import cn.thesilentnights.scfmc.utils.Logging;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class OpenCheckPassword implements Packet {
    private final BlockPos pos;
    private final int pwdLength;

    public OpenCheckPassword(FriendlyByteBuf buffer) {
        this(buffer.readBlockPos(), buffer.readInt());
    }

    public OpenCheckPassword( BlockPos pos, int pwdLength) {
        this.pos = pos;
        this.pwdLength = pwdLength;
    }

    @Override
    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(pos);
        buffer.writeInt(pwdLength);
    }


    @Override
    public void handle(Supplier<NetworkEvent.Context> ctx) {

        ctx.get().enqueueWork(() -> {

            Logging.getLogger().debug("OpenScreen: {}", this.toString());

            if (Minecraft.getInstance().level.getBlockEntity(this.pos) instanceof Lockable lockable) {
                Minecraft.getInstance()
                        .forceSetScreen(new CheckPassword(Component.literal("Lockable Chest"), lockable, this.pwdLength));
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
