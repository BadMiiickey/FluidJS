package com.miiickey.fluidjs.kubejs.ingredient;

import dev.latvian.mods.kubejs.core.FluidStackKJS;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.crafting.FluidIngredient;

import javax.annotation.Nullable;
import java.util.List;

public interface FluidIngredientWrapper {
    FluidIngredient of(FluidIngredient fluidIngredient);
    FluidIngredient of(FluidStack fluidStack);
    FluidIngredient of(FluidStackKJS fluidStackJS);
    FluidIngredient of(TagKey<Fluid> fluidTagKey);
    FluidIngredient of(List<?> list);

    static FluidIngredient of(@Nullable Object o) {
        return FluidIngredientJS.of(o);
    }

    static FluidIngredient of() {
        return FluidIngredient.empty();
    }
}
