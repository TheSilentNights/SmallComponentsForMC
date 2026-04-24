package cn.thesilentnights.scfmc.items.blocks;

import cn.thesilentnights.scfmc.items.blockentity.AbstractLockableChestEntity;
import cn.thesilentnights.scfmc.items.blockentity.CommonLockableChestEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class CommonLockableChest extends AbstractLockableChest{
    public CommonLockableChest(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    protected AbstractLockableChestEntity gLockableChestEntity(BlockState pState, Level pLevel, BlockPos pPos) {
        return new CommonLockableChestEntity(pPos, pState);
    }

    public static Properties genProperties() {
        return BlockBehaviour.Properties.of().noParticlesOnBreak().strength(-1.0F, 12000F).noParticlesOnBreak();
    }
}
