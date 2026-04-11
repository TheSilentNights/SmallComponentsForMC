package cn.thesilentnights.scfmc.registry;

import cn.thesilentnights.scfmc.items.blockentity.LockableChestEntity;
import cn.thesilentnights.scfmc.items.blocks.LockableChest;
import cn.thesilentnights.scfmc.repo.Statics;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class BlockRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, Statics.MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Statics.MOD_ID);

    public static final RegistryObject<Block> LOCKABLE_CHEST = BLOCKS.register("lockable_chest", () -> new LockableChest(LockableChest.genProperties()));
    
    public static final RegistryObject<BlockEntityType<? extends ChestBlockEntity>> LOCKABLE_CHEST_ENTITY = BLOCK_ENTITIES.register(
            "lockable_chest_entity", () -> BlockEntityType.Builder.of(
                    LockableChestEntity::new,
                    LOCKABLE_CHEST.get()
            ).build(null)
    );


    public static void init(IEventBus eventBus) {
        BLOCKS.register(eventBus); // Register BLOCKS with the event bus
        BLOCK_ENTITIES.register(eventBus); // Register BLOCK_ENTITIES with the event bus
    }
}
