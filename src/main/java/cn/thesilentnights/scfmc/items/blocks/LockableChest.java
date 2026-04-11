package cn.thesilentnights.scfmc.items;

import cn.thesilentnights.scfmc.blockentity.LockableChestEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.function.Supplier;

public class LockableChest extends ChestBlock {

    public LockableChest(Properties pProperties, Supplier<BlockEntityType<? extends ChestBlockEntity>> pBlockEntityType) {
        super(pProperties, pBlockEntityType);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pLevel.isClientSide){
            return InteractionResult.SUCCESS;
        }

        LockableChestEntity lockableChestEntity = (LockableChestEntity) pLevel.getBlockEntity(pPos);


    }
}
