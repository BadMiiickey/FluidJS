package com.fluidjs.kubejs;

import com.fluidjs.kubejs.createSource.CreateFluidSourceEventJS;
import com.fluidjs.kubejs.createSource.CreateFluidSourceHandler;
import com.fluidjs.kubejs.interaction.FluidInteractRegistryEvent;
import dev.latvian.mods.kubejs.KubeJSPlugin;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class FluidJSPlugin extends KubeJSPlugin {

    @Override
    @SuppressWarnings({ "removal" })
    public void registerEvents() {
        FluidEvents.GROUP.register();
        MinecraftForge.EVENT_BUS.register(CreateFluidSourceHandler.class);
        MinecraftForge.EVENT_BUS.register(FluidJSPlugin.class);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onCommonSetup);
    }

    private void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            FluidEvents.FLUID_INTERACT_REGISTRY.post(new FluidInteractRegistryEvent());
        });
    }

    @SubscribeEvent
    public static void onServerStarted(ServerStartedEvent event) {
        CreateFluidSourceEventJS.FLUID_SOURCE_RULES.clear();
    }
}
