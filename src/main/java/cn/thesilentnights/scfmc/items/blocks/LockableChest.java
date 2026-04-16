package cn.thesilentnights.scfmc.items.blocks;

import org.checkerframework.checker.units.qual.s;

import cn.thesilentnights.scfmc.items.blockentity.LockableChestEntity;
import cn.thesilentnights.scfmc.networks.NetWork;
import cn.thesilentnights.scfmc.networks.packets.OpenCheckPassword;
import cn.thesilentnights.scfmc.registry.BlockRegistry;
import cn.thesilentnights.scfmc.utils.Logging;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.PacketDistributor;

public class LockableChest extends ChestBlock {

    public LockableChest(Properties pProperties) {
        super(pProperties, BlockRegistry.LOCKABLE_CHEST_ENTITY);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand,
            BlockHitResult pHit) {
        if (pLevel.isClientSide) {
            return InteractionResult.SUCCESS;
        }

        LockableChestEntity lockableChestEntity = (LockableChestEntity) pLevel.getBlockEntity(pPos);

        if (lockableChestEntity != null && pPlayer instanceof ServerPlayer) {
            ServerPlayer serverPlayer = (ServerPlayer) pPlayer;
            Logging.getLogger().debug("LockableChestEntity: {}", lockableChestEntity.toString());

            NetWork.CHANNEL.send(
                    PacketDistributor.PLAYER
                            .with(() -> serverPlayer),
                    new OpenCheckPassword(lockableChestEntity.getBlockPos(),
                            lockableChestEntity.getPassword().length()));
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

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    public static Properties genProperties() {
        return BlockBehaviour.Properties.of().noParticlesOnBreak().strength(-1.0F, 12000F).noParticlesOnBreak();
    }
}
