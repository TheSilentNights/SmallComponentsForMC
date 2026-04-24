package cn.thesilentnights.scfmc.items.blockentity;


import cn.thesilentnights.scfmc.functions.apis.IEntityRequiredParams;
import cn.thesilentnights.scfmc.functions.apis.Lockable;
import cn.thesilentnights.scfmc.items.blocks.AbstractLockableChest;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import org.jetbrains.annotations.NotNull;

public class AbstractLockableChestEntity extends ChestBlockEntity implements Lockable, IEntityRequiredParams {
    private String password = "";

    public AbstractLockableChestEntity(BlockEntityType<?> entityType, @NotNull BlockPos pPos, @NotNull BlockState pBlockState) {
        super(entityType, pPos, pBlockState);
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
        if (getLevel() != null && getBlockState().getBlock() instanceof AbstractLockableChest lockableChest) {
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

    /**
     * override it if you want to change the size of the container
     * @return the size of the container
         */
    @Override
    public int getContainerSize() {
        return super.getContainerSize();
    }

    // @Override
    // public void stopOpen(Player player) {
    //     if (level != null && !level.isClientSide) {
    //         level.playSound(
    //             null,
    //             getBlockPos(),
    //             SoundRegistry.CHEST_CLOSE.get(),
    //             SoundSource.BLOCKS,
    //             1.0f, 1.0f
    //         );
    //     }
    // }


}
