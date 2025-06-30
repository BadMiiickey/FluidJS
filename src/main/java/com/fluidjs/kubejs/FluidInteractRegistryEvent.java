package com.fluidjs.kubejs;

import dev.latvian.mods.kubejs.event.EventJS;
import dev.latvian.mods.kubejs.fluid.FluidStackJS;
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
        FluidType fluidType = fluid.getFluid().getFluidType();
        InteractionInformation interactionInformation = new InteractionInformation(hasInteraction, FluidInteraction);

        addInteraction(fluidType, interactionInformation);
    }

    public void createForItem(FluidStackJS fluid, FluidStackJS interact, ItemStack itemStack) {
        FluidType fluidType = fluid.getFluid().getFluidType();

        addInteraction(fluidType, getForItem(interact, itemStack));
    }

    public void createForBlock(FluidStackJS fluid, FluidStackJS interact, Block sourceTransforToBlock, Block flowingTransforToBlock) {
        FluidType fluidType = fluid.getFluid().getFluidType();

        addInteraction(fluidType, getForBlock(interact, sourceTransforToBlock, flowingTransforToBlock));
    }

    public void createForFluid(FluidStackJS fluid, FluidStackJS interact, FluidStackJS output) {
        FluidType fluidType = fluid.getFluid().getFluidType();

        addInteraction(fluidType, getForFluid(interact, output));
    }

    public void createForExplosion(FluidStackJS fluid, FluidStackJS interact, Level.ExplosionInteraction explosion, float strength) {
        FluidType fluidType = fluid.getFluid().getFluidType();

        addInteraction(fluidType, getForExplosion(interact, explosion, strength));
    }

    public void createForEntity(FluidStackJS fluid, FluidStackJS interact, EntityType<? extends Entity> entityType) {
        FluidType fluidType = fluid.getFluid().getFluidType();

        addInteraction(fluidType, getForEntity(interact, entityType));
    }
}
