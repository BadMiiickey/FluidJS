package com.fluidjs.kubejs;

import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;

public interface FluidEvents {
    EventGroup GROUP = EventGroup.of("FluidEvents");

    EventHandler FLUID_INTERACT_REGISTRY = GROUP.server("register", () -> FluidInteractRegistryEvent.class);
}
