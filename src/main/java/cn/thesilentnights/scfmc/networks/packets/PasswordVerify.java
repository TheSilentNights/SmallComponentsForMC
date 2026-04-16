package cn.thesilentnights.scfmc.networks.packets;

import cn.thesilentnights.scfmc.functions.apis.Lockable;
import cn.thesilentnights.scfmc.utils.MessageSender;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.Optional;
import java.util.function.Supplier;

public class PasswordVerify implements Packet {
    private final String password;
    private final BlockPos pos;

    public PasswordVerify(FriendlyByteBuf buffer) {
        this(buffer.readUtf(), buffer.readBlockPos());
    }

    public PasswordVerify(String password, BlockPos pos) {
        this.password = password;
        this.pos = pos;
    }

    @Override
    public void encode(FriendlyByteBuf buffer) {
        buffer.writeUtf(password);
        buffer.writeBlockPos(pos);
    }



    @Override
    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer sender = ctx.get().getSender();
            //verify the send direction(client -> server)
            if (sender == null){
                return;
            }

            Optional<Lockable> lockable = this.getLockable(sender.level());

            if (lockable.isPresent()){
                if (lockable.get().verify(this.password)){
                    lockable.get().activate(sender);
                    MessageSender.sendMessage(sender, "Password is correct", MessageSender.MessageType.SUCCESS);
                }else{
                    MessageSender.sendMessage(sender, "Password is incorrect", MessageSender.MessageType.ERROR);
                    return;
                }
            }
        });
    }

    private Optional<Lockable> getLockable(Level level) {
        if (level.getBlockEntity(this.pos) instanceof Lockable lockable) {
            return Optional.of(lockable);
        } else {
            return Optional.empty();
        }
    }



}
