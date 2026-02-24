package com.miiickey.fluidjs.kubejs;

import com.miiickey.fluidjs.kubejs.interaction.FluidInteractRegistryEvent;
import com.miiickey.fluidjs.kubejs.source.CreateFluidSourceEventJS;
import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;

public interface FluidEvents {
    EventGroup GROUP = EventGroup.of("FluidEvents");

    EventHandler FLUID_INTERACT_REGISTRY = GROUP.startup("interact", () -> FluidInteractRegistryEvent.class);
    EventHandler FLUID_CREATE_SOURCE_REGISTRY = GROUP.server("source", () -> CreateFluidSourceEventJS.class);
}
