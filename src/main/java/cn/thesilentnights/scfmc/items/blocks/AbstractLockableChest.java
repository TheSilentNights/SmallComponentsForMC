package cn.thesilentnights.scfmc.items.blocks;

import javax.annotation.Nullable;

import org.checkerframework.checker.units.qual.s;

import cn.thesilentnights.scfmc.functions.apis.Lockable;
import cn.thesilentnights.scfmc.items.blockentity.AbstractLockableChestEntity;
import cn.thesilentnights.scfmc.networks.NetWork;
import cn.thesilentnights.scfmc.networks.packets.OpenCheckPassword;
import cn.thesilentnights.scfmc.registry.BlockRegistry;
import cn.thesilentnights.scfmc.registry.SoundRegistry;
import cn.thesilentnights.scfmc.utils.Logging;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.PacketDistributor;

public abstract class AbstractLockableChest extends ChestBlock {

    public AbstractLockableChest(Properties pProperties) {
        super(pProperties, BlockRegistry.LOCKABLE_CHEST_ENTITY);
    }


    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand,
            BlockHitResult pHit) {
        if (pLevel.isClientSide) {
            return InteractionResult.SUCCESS;
        }

        AbstractLockableChestEntity lockable = (AbstractLockableChestEntity) pLevel.getBlockEntity(pPos);

        if (lockable != null && pPlayer instanceof ServerPlayer) {
            ServerPlayer serverPlayer = (ServerPlayer) pPlayer;
            Logging.getLogger().debug("Lockable: {}", lockable.toString());

            NetWork.CHANNEL.send(
                    PacketDistributor.PLAYER
                            .with(() -> serverPlayer),
                    new OpenCheckPassword(lockable.getBlockPos(),lockable.getPassword().length()));
        }
        return InteractionResult.SUCCESS;
    }

    public void activate(BlockState state, Level level, BlockPos pos, Player player) {
        level.playSound(
                null,
                pos,
                SoundRegistry.CHEST_OPEN.get(),
                SoundSource.BLOCKS,
                0.5f,
                level.random.nextFloat() * 0.1f + 0.9f);

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
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState,
            @Nullable LivingEntity pPlacer, ItemStack pStack) {
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
        // Play a lock-click sound for everyone nearby when the chest is placed.
        // Run only on the server; the sound packet is broadcast to all nearby clients.
        if (!pLevel.isClientSide) {
            pLevel.playSound(
                    null, // null → play for ALL nearby players
                    pPos,
                    SoundRegistry.CHEST_PLACE.get(),
                    SoundSource.BLOCKS,
                    0.6f, 1.2f);
        }
    }


    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    
}
