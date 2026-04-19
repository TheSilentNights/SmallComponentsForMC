package cn.thesilentnights.scfmc.items.blockentity;


import cn.thesilentnights.scfmc.functions.apis.Lockable;
import cn.thesilentnights.scfmc.items.blocks.LockableChest;
import cn.thesilentnights.scfmc.registry.BlockRegistry;
import cn.thesilentnights.scfmc.registry.SoundRegistry;
import cn.thesilentnights.scfmc.utils.MessageSender.MessageType;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;

import org.jetbrains.annotations.NotNull;

public class LockableChestEntity extends ChestBlockEntity implements Lockable {
    private String password = "";
    private boolean wasOpen = false;


    public LockableChestEntity(@NotNull BlockPos pPos, @NotNull BlockState pBlockState) {
        super(BlockRegistry.LOCKABLE_CHEST_ENTITY.get(), pPos, pBlockState);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);

        pTag.putString("password", password);
    }

    @Override
    public void setPassword(@NotNull String pPassword){
        password = pPassword;
        setChanged();
    }

    @Override
    public void activate(Player pPlayer) {
        if (getLevel() != null && getBlockState().getBlock() instanceof LockableChest lockableChest) {
            this.wasOpen = true;
            lockableChest.activate(getBlockState(), getLevel(), getBlockPos(), pPlayer);
        }
    }

    @Override
    public boolean verify(String password) {
        return password.equals(this.password);
    }

    public String getPassword(){
        return password;
    }

    @Override
    public void load(@NotNull CompoundTag pTag) {
        super.load(pTag);

        this.password = pTag.getString("password");
    }

    // suppress vanilla sound event
    @Override
    public void startOpen(Player player) {

        // intentionally empty — vanilla sound suppressed
    }

    @Override
    public void stopOpen(Player player) {
        if (level != null && !level.isClientSide) {
            level.playSound(
                null,
                getBlockPos(),
                SoundRegistry.CHEST_CLOSE.get(),
                SoundSource.BLOCKS,
                1.0f, 1.0f
            );
        }
    }


}
