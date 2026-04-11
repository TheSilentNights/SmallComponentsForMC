package cn.thesilentnights.scfmc.registry;

import org.checkerframework.checker.units.qual.s;

import cn.thesilentnights.scfmc.items.items.LockableChestItem;
import cn.thesilentnights.scfmc.repo.Statics;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, Statics.MOD_ID);
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB,
            Statics.MOD_ID);

    public static final RegistryObject<Item> LOCKABLE_CHEST = ITEMS.register("lockable_chest",
            () -> new LockableChestItem(new Item.Properties().stacksTo(64)));

    public static final RegistryObject<CreativeModeTab> SMALL_COMPONENTS_TABS = TABS.register("lockable_chest_tab",
            () -> {
                return CreativeModeTab.builder()
                .icon(() -> LOCKABLE_CHEST.get().getDefaultInstance())
                .title(Component.translatable("Small_Components").withStyle(
                    ChatFormatting.LIGHT_PURPLE
                ))
                .displayItems((pParameters, pOutput) -> {
                    pOutput.accept(LOCKABLE_CHEST.get());
                })
                .build();
            });

    public static void registerItems(IEventBus eventBus) {
        ITEMS.register(eventBus);
        TABS.register(eventBus);
    }
}
