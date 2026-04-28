package cn.thesilentnights.scfmc.items.blocks;

import cn.thesilentnights.scfmc.items.blockentity.OrdinaryLockableChestEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class OrdinaryLockableChest extends AbstractLockableChest{
    public OrdinaryLockableChest(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState state) {
        return new OrdinaryLockableChestEntity(pPos, state);
    }

    public static Properties genProperties() {
        return BlockBehaviour.Properties.of().noParticlesOnBreak().strength(-1.0F, 12000F).noParticlesOnBreak();
    }
}
