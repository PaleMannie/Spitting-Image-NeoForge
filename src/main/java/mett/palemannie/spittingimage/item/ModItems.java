package mett.palemannie.spittingimage.item;

import mett.palemannie.spittingimage.SpittingImage;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(SpittingImage.MODID);


    public static final DeferredItem<Item> SPIT = ITEMS.register("spit",
            () -> new Item(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(SpittingImage.MODID, "spit")))));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
