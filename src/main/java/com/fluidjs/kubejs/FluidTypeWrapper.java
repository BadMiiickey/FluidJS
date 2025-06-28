package com.fluidjs.kubejs;

import dev.latvian.mods.kubejs.fluid.FluidStackJS;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.ForgeRegistries;

public interface FluidTypeWrapper {

    static FluidType of(FluidStackJS fluidString) {
        FluidStackJS fluid = FluidStackJS.of(fluidString);
        System.out.println("Fluid.of: " + fluid);
        if (fluid == null || fluid.getFluid() == null) {
            throw new IllegalArgumentException("FluidStackJS cannot be null or empty.");
        }

        FluidType fluidType = fluid.getFluid().getFluidType();

        if (fluidType.getClass().getName().contains("architectury")) {
            ResourceLocation fluidResourceLocation = ForgeRegistries.FLUIDS.getKey(fluid.getFluid());

            if (fluidResourceLocation == null) {
                throw new IllegalArgumentException("FluidStackJS does not have a valid fluid resource location.");
            }

            FluidType registeredType = ForgeRegistries.FLUID_TYPES.get().getValue(fluidResourceLocation);

            if (registeredType != null) {
                return registeredType;
            }
        }

        return fluidType;
    }

    @HideFromJS
    static FluidStackJS StackJSTransform(Object fluid) {
        return FluidStackJS.of(fluid);
    }
}
