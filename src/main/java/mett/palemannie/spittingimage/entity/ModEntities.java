package mett.palemannie.spittingimage.entity;

import mett.palemannie.spittingimage.SpittingImage;
import mett.palemannie.spittingimage.entity.custom.SpitEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, SpittingImage.MODID);

    public static final Supplier<EntityType<SpitEntity>> SPIT =
            ENTITY_TYPES.register("spit", () -> EntityType.Builder.<SpitEntity>of(SpitEntity::new, MobCategory.MISC)
                    .sized(0.2f, 0.2f).build(ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(SpittingImage.MODID, "spit"))));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
