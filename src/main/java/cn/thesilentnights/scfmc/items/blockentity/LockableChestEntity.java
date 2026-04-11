package cn.thesilentnights.scfmc.items.blockentity;


import cn.thesilentnights.scfmc.functions.apis.Lockable;
import cn.thesilentnights.scfmc.items.blocks.LockableChest;
import cn.thesilentnights.scfmc.registry.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class LockableChestEntity extends ChestBlockEntity implements Lockable {
    private String password = "";


    public LockableChestEntity(@NotNull BlockPos pPos, @NotNull BlockState pBlockState) {
        super(BlockRegistry.LOCKABLE_CHEST_ENTITY.get(), pPos, pBlockState);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);

        pTag.putString("password", password);
    }

    public void setPassword(@NotNull String pPassword){
        password = pPassword;
    }

    @Override
    public void activate(Player pPlayer) {
        if (getLevel() != null && getBlockState().getBlock() instanceof LockableChest lockableChest) {
            // Your code here with pPlayer
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
        
        password = pTag.getString("password");
    }

    
}
