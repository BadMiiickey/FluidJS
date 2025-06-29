package com.fluidjs.kubejs;

import dev.latvian.mods.kubejs.event.EventJS;
import dev.latvian.mods.kubejs.fluid.FluidStackJS;
import dev.latvian.mods.kubejs.item.ItemStackJS;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fluids.FluidType;

import static com.fluidjs.kubejs.helper.InteractionInformationHelper.*;
import static net.minecraftforge.fluids.FluidInteractionRegistry.*;

public class FluidInteractRegistryEvent extends EventJS {

    public void create(FluidStackJS fluid, HasFluidInteraction hasInteraction, FluidInteraction FluidInteraction) {
        FluidType fluidType = FluidTypeWrapper.of(fluid);
        InteractionInformation interactionInformation = new InteractionInformation(hasInteraction, FluidInteraction);

        addInteraction(fluidType, interactionInformation);
    }

    public void createForItem(FluidStackJS fluid, FluidStackJS interact, ItemStack itemStack) {
        FluidType fluidType = FluidTypeWrapper.of(fluid);
        FluidStackJS interactFluid = FluidStackJS.of(interact);
        ItemStack item = ItemStackJS.of(itemStack);

        addInteraction(fluidType, getForItem(interactFluid, item));
    }

    public void createForBlock(FluidStackJS fluid, FluidStackJS interact, Block sourceTransforToBlock, Block flowingTransforToBlock) {
        FluidType fluidType = FluidTypeWrapper.of(fluid);
        FluidStackJS interactFluid = FluidStackJS.of(interact);

        addInteraction(fluidType, getForBlock(interactFluid, sourceTransforToBlock, flowingTransforToBlock));
    }

    public void createForFluid(FluidStackJS fluid, FluidStackJS interact, FluidStackJS output) {
        FluidType fluidType = FluidTypeWrapper.of(fluid);
        FluidStackJS interactFluid = FluidStackJS.of(interact);
        FluidStackJS outputFluid = FluidStackJS.of(output);

        addInteraction(fluidType, getForFluid(interactFluid, outputFluid));
    }

    public void createForExplosion(FluidStackJS fluid, FluidStackJS interact, Level.ExplosionInteraction explosion) {
        FluidType fluidType = FluidTypeWrapper.of(fluid);
        FluidStackJS interactFluid = FluidStackJS.of(interact);

        addInteraction(fluidType, getForExplosion(interactFluid, explosion));
    }

    public void createForEntity(FluidStackJS fluid, FluidStackJS interact, EntityType<? extends Entity> entityType) {
        FluidType fluidType = FluidTypeWrapper.of(fluid);
        FluidStackJS interactFluid = FluidStackJS.of(interact);

        addInteraction(fluidType, getForEntity(interactFluid, entityType));
    }
}
