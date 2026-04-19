package cn.thesilentnights.scfmc.registry;

import cn.thesilentnights.scfmc.repo.Statics;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class SoundRegistry {

    public static final DeferredRegister<SoundEvent> SOUNDS =
            DeferredRegister.create(Registries.SOUND_EVENT, Statics.MOD_ID);

    public static final RegistryObject<SoundEvent> CHEST_PLACE = register("chest_place");
    public static final RegistryObject<SoundEvent> CHEST_OPEN   = register("chest_open");
    public static final RegistryObject<SoundEvent> CHEST_CLOSE  = register("chest_close");
    public static final RegistryObject<SoundEvent> LOCKPICK     = register("lockpick");

    private static RegistryObject<SoundEvent> register(String name) {
        ResourceLocation loc = ResourceLocation.fromNamespaceAndPath(Statics.MOD_ID, name);
        return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(loc));
    }

    public static void init(IEventBus eventBus) {
        SOUNDS.register(eventBus);
    }
}
