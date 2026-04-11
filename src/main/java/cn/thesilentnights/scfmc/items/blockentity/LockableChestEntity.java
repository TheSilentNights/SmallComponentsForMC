package cn.thesilentnights.scfmc.blockentity;


import org.jetbrains.annotations.NotNull;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class LockableChestEntity extends BlockEntity{
    private String password = "";


    protected LockableChestEntity(@NotNull BlockEntityType<?> pType, @NotNull BlockPos pPos, @NotNull BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);

        pTag.putString("password", password);
    }

    public void setPassword(@NotNull String pPassword){
        password = pPassword;
    }

    public String getPassword(){
        return password;
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        
        password = pTag.getString("password");
    }

    
}
