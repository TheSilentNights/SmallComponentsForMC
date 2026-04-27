package cn.thesilentnights.scfmc.items.blockentity;

import org.jetbrains.annotations.NotNull;

import cn.thesilentnights.scfmc.registry.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class CommonLockableChestEntity extends AbstractLockableChestEntity{

    public CommonLockableChestEntity(@NotNull BlockPos pPos, @NotNull BlockState pBlockState) {
        super(BlockRegistry.LOCKABLE_CHEST_ENTITY.get(), pPos, pBlockState);
    }


}
