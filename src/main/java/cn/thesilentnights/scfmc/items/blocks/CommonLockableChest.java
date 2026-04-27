package cn.thesilentnights.scfmc.items.blocks;

import cn.thesilentnights.scfmc.items.blockentity.CommonLockableChestEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class CommonLockableChest extends AbstractLockableChest{
    public CommonLockableChest(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState state) {
        return new CommonLockableChestEntity(pPos, state);
    }

    public static Properties genProperties() {
        return BlockBehaviour.Properties.of().noParticlesOnBreak().strength(-1.0F, 12000F).noParticlesOnBreak();
    }
}
