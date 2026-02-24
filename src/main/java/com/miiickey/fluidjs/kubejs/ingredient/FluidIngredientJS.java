package com.miiickey.fluidjs.kubejs.ingredient;

import dev.latvian.mods.kubejs.core.FluidStackKJS;
import dev.latvian.mods.rhino.Wrapper;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.crafting.FluidIngredient;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface FluidIngredientJS {
    static FluidIngredient of(@Nullable Object o) {
        while (o instanceof Wrapper) {
            o = ((Wrapper) o).unwrap();
        }

        if (o instanceof List<?> list) {
            List<FluidStack> allStacks = new ArrayList<>();

            for (Object item : list) {
                FluidIngredient result = of(item);

                if (result == null || result.isEmpty()) continue;

                allStacks.addAll(Arrays.asList(result.getStacks()));
            }

            return FluidIngredient.of(allStacks.toArray(FluidStack[]::new));
        } else if (o instanceof String s) {
            if (s.startsWith("#")) {
                ResourceLocation tagResourceLocation = ResourceLocation.tryParse(s.substring(1));

                if (tagResourceLocation == null) return FluidIngredient.empty();

                TagKey<Fluid> fluidTagKey = TagKey.create(BuiltInRegistries.FLUID.key(), tagResourceLocation);

                return FluidIngredient.tag(fluidTagKey);
            }

            ResourceLocation fluidResourceLocation = ResourceLocation.tryParse(s);

            if (fluidResourceLocation == null) return FluidIngredient.of();

            boolean hasKey = BuiltInRegistries.FLUID.containsKey(fluidResourceLocation);

            if (!hasKey) return FluidIngredient.empty();

            return FluidIngredient.of(BuiltInRegistries.FLUID.get(fluidResourceLocation));
        } else if (o instanceof FluidStack fluidStack) {
            return FluidIngredient.of(fluidStack);
        } else if (o instanceof FluidIngredient fluidIngredient) {
            return fluidIngredient;
        } else if (o instanceof FluidStackKJS fluidStackJS) {
            return FluidIngredient.of(fluidStackJS.kjs$getFluid());
        }

        return FluidIngredient.empty();
    }
}
