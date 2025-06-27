package com.fluidjs.kubejs;

import dev.latvian.mods.kubejs.event.EventJS;
import dev.latvian.mods.rhino.util.HideFromJS;
import dev.latvian.mods.rhino.util.RemapForJS;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fluids.FluidInteractionRegistry;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.ForgeRegistries;

public class FluidInteractRegistryEvent extends EventJS {

    @HideFromJS
    public FluidInteractRegistryEvent() {

    }

    @RemapForJS("create")
    public void create(ResourceLocation fluid, FluidInteractionRegistry.HasFluidInteraction hasInteraction, FluidInteractionRegistry.FluidInteraction FluidInteraction) {
        FluidType fluidType = ForgeRegistries.FLUID_TYPES.get().getValue(fluid);
        FluidInteractionRegistry.InteractionInformation interactionInformation = new FluidInteractionRegistry.InteractionInformation(hasInteraction, FluidInteraction);

        if (fluidType == null) {
            throw new IllegalArgumentException("FluidType with resource location " + fluid + " does not exist.");
        }

        FluidInteractionRegistry.addInteraction(fluidType, interactionInformation);
    }
}
