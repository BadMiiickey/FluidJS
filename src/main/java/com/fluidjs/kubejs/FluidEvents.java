package com.fluidjs.kubejs;

import com.fluidjs.kubejs.createSource.CreateFluidSourceEventJS;
import com.fluidjs.kubejs.interaction.FluidInteractRegistryEvent;
import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;

public interface FluidEvents {
    EventGroup GROUP = EventGroup.of("FluidEvents");

    EventHandler FLUID_INTERACT_REGISTRY = GROUP.startup("interact", () -> FluidInteractRegistryEvent.class);
    EventHandler FLUID_CREATE_SOURCE_REGISTRY = GROUP.server("source", () -> CreateFluidSourceEventJS.class);
}
