package cn.thesilentnights.scfmc.items.blocks;

import cn.thesilentnights.scfmc.items.blockentity.LockableChestEntity;
import cn.thesilentnights.scfmc.networks.NetWork;
import cn.thesilentnights.scfmc.networks.packets.OpenScreen;
import cn.thesilentnights.scfmc.registry.BlockRegistry;
import cn.thesilentnights.scfmc.utils.Logging;
import net.minecraft.core.BlockPos;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.PacketDistributor;

public class LockableChest extends ChestBlock {

    public LockableChest(Properties pProperties) {
        super(pProperties, BlockRegistry.LOCKABLE_CHEST_ENTITY);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pLevel.isClientSide) {
            return InteractionResult.SUCCESS;
        }

        LockableChestEntity lockableChestEntity = (LockableChestEntity) pLevel.getBlockEntity(pPos);

        if (lockableChestEntity != null) {
            Logging.getLogger().debug("LockableChestEntity: {}", lockableChestEntity);
            
            NetWork.CHANNEL.send(
                    PacketDistributor.PLAYER.with(()->pLevel.getServer().getPlayerList().getPlayer(pPlayer.getUUID())),
                    new OpenScreen(OpenScreen.ScreenType.LOCKABLE_CHEST_PASSWORD, lockableChestEntity.getBlockPos())
            );
        }
        return InteractionResult.SUCCESS;
    }

    public void activate(BlockState state, Level level, BlockPos pos, Player player) {
        if (!level.isClientSide) {
            ChestBlock block = (ChestBlock) state.getBlock();
            MenuProvider menuProvider = block.getMenuProvider(state, level, pos);

            if (menuProvider != null) {
                player.openMenu(menuProvider);
                player.awardStat(Stats.CUSTOM.get(Stats.OPEN_CHEST));
            }
        }
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new LockableChestEntity(pPos, pState);
    }
}
