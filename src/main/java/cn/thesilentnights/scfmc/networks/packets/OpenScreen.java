package cn.thesilentnights.scfmc.networks.packets;

import cn.thesilentnights.scfmc.functions.apis.Lockable;
import cn.thesilentnights.scfmc.menu.CheckPassword;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class OpenScreen {
    private final ScreenType screenType;
    private final BlockPos pos;

    public OpenScreen(ScreenType screenType, BlockPos pos) {
        this.screenType = screenType;
        this.pos = pos;
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeEnum(screenType);
        buffer.writeBlockPos(pos);
    }
    public static OpenScreen decode(FriendlyByteBuf buffer) {
        return new OpenScreen(buffer.readEnum(ScreenType.class), buffer.readBlockPos());
    }

    public static void handle(OpenScreen openScreen, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if (ctx.get().getSender() == null){
                return;
            }

            switch (openScreen.screenType) {
                case LOCKABLE_CHEST_PASSWORD -> {
                    if (ctx.get().getSender().level().getBlockEntity(openScreen.pos) instanceof Lockable lockable)
                        Minecraft.getInstance().forceSetScreen(new CheckPassword(Component.literal("Lockable Chest"), lockable));
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }


    public enum ScreenType {
        LOCKABLE_CHEST_PASSWORD
    }
}
