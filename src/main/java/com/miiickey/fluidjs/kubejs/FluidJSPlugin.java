package com.miiickey.fluidjs.kubejs;

import com.miiickey.fluidjs.kubejs.ingredient.FluidIngredientWrapper;
import dev.latvian.mods.kubejs.event.EventGroupRegistry;
import dev.latvian.mods.kubejs.plugin.KubeJSPlugin;
import dev.latvian.mods.kubejs.script.BindingRegistry;

public class FluidJSPlugin implements KubeJSPlugin {
    public void registerEvents(EventGroupRegistry event) {
        event.register(FluidEvents.GROUP);
    }

    public void registerBindings(BindingRegistry event) {
        event.add("FluidIngredient", FluidIngredientWrapper.class);
    }
}
