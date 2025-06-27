package com.fluidjs.kubejs;

import dev.latvian.mods.kubejs.KubeJSPlugin;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class FluidJSPlugin extends KubeJSPlugin {

    @Override
    public void registerEvents() {
        FluidEvents.GROUP.register();
        MinecraftForge.EVENT_BUS.addListener(this::onCommonSetup);
    }

    private void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            FluidEvents.FLUID_INTERACT_REGISTRY.post(new FluidInteractRegistryEvent());
        });
    }
}
