package mett.palemannie.spittingimage.event;

import mett.palemannie.spittingimage.entity.client.SpitModel;
import mett.palemannie.spittingimage.SpittingImage;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = SpittingImage.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {

        event.registerLayerDefinition(SpitModel.LAYER_LOCATION, SpitModel::createBodyLayer);
    }
}