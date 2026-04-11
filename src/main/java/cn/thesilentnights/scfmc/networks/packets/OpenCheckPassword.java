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

public class OpenCheckPassword {
    private final BlockPos pos;
    private final int pwdLength;

    public OpenCheckPassword( BlockPos pos, int pwdLength) {
        this.pos = pos;
        this.pwdLength = pwdLength;
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(pos);
        buffer.writeInt(pwdLength);
    }

    public static OpenCheckPassword decode(FriendlyByteBuf buffer) {
        return new OpenCheckPassword(buffer.readBlockPos(), buffer.readInt());
    }

    public static void handle(OpenCheckPassword openScreen, Supplier<NetworkEvent.Context> ctx) {

        ctx.get().enqueueWork(() -> {

            Logging.getLogger().debug("OpenScreen: {}", openScreen);

            if (Minecraft.getInstance().level.getBlockEntity(openScreen.pos) instanceof Lockable lockable) {
                Minecraft.getInstance()
                        .forceSetScreen(new CheckPassword(Component.literal("Lockable Chest"), lockable, openScreen.pwdLength));
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
