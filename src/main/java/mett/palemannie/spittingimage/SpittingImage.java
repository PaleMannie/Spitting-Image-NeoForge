package mett.palemannie.spittingimage;

import com.mojang.logging.LogUtils;
import mett.palemannie.spittingimage.entity.ModEntities;
import mett.palemannie.spittingimage.entity.client.SpitRenderer;
import mett.palemannie.spittingimage.network.ModMessages;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;

@Mod(SpittingImage.MODID)
public class SpittingImage {

    public static final String MODID = "spittingimage";
    private static final Logger LOGGER = LogUtils.getLogger();


    public SpittingImage(IEventBus modEventBus, Dist dist, ModContainer modContainer)
    {

        modContainer.registerConfig(ModConfig.Type.COMMON, SpittingImageConfig.COMMON_SPEC);

        modEventBus.addListener(this::commonSetup);
        ModEntities.register(modEventBus);
        modEventBus.addListener(ModMessages::registerMessages);

        if (dist == Dist.CLIENT) {
            modEventBus.addListener(KeyBindings::registerBindings);
        }

    }

    private void commonSetup(final FMLCommonSetupEvent event) {}

    @EventBusSubscriber(modid = MODID, /*bus = EventBusSubscriber.Bus.MOD,*/ value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {

            KeyBindings.register();
            EntityRenderers.register(ModEntities.SPIT.get(), SpitRenderer::new);
        }
    }
}
