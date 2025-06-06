package mett.palemannie.spittingimage.util;

import mett.palemannie.spittingimage.SpittingImage;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;

public class ModDamageTypes {

    public static ResourceKey<DamageType> register(String name){
        return ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(SpittingImage.MODID, name));
    }

    public static final ResourceKey<DamageType> SPIT_DAMAGE = register("spit_damage");
}
