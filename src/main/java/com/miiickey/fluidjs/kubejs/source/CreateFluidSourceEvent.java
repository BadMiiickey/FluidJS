package com.miiickey.fluidjs.kubejs.source;

import com.miiickey.fluidjs.FluidJS;
import com.miiickey.fluidjs.kubejs.FluidEvents;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = FluidJS.MOD_ID)
public class CreateFluidSourceEvent {

    @SubscribeEvent
    public static void onCreateFluidSource(net.neoforged.neoforge.event.level.block.CreateFluidSourceEvent event) {
        CreateFluidSourceEventJS eventJS = new CreateFluidSourceEventJS(event);
        FluidEvents.FLUID_CREATE_SOURCE_REGISTRY.post(eventJS);
    }
}
