package com.fluidjs.kubejs;

import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.script.BindingsEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class FluidJSPlugin extends KubeJSPlugin {

    @Override
    @SuppressWarnings({ "deprecation", "removal" })
    public void registerEvents() {
        FluidEvents.GROUP.register();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onCommonSetup);
    }

    @Override
    public void registerBindings(BindingsEvent event) {
        event.add("FluidType", FluidTypeWrapper.class);
    }

    private void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            FluidEvents.FLUID_INTERACT_REGISTRY.post(new FluidInteractRegistryEvent());
        });
    }
}
