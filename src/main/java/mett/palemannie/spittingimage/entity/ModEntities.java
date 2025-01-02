package mett.palemannie.spittingimage.entity;

import mett.palemannie.spittingimage.SpittingImage;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, SpittingImage.MODID);

    public static final Supplier<EntityType<SpitEntity>> SPIT_PROJECTILE =
            ENTITY_TYPES.register("spit_projectile", () -> EntityType.Builder.of(SpitEntity::new, MobCategory.MISC)
                    .sized(0.5f, 1.15f).build("spit_projectile"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
