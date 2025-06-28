package com.fluidjs.kubejs;

import dev.latvian.mods.kubejs.event.EventJS;
import dev.latvian.mods.kubejs.fluid.FluidStackJS;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fluids.FluidType;

import static com.fluidjs.kubejs.helper.InteractionInformationHelper.getForBlock;
import static net.minecraftforge.fluids.FluidInteractionRegistry.*;

public class FluidInteractRegistryEvent extends EventJS {

    public void create(FluidStackJS fluid, HasFluidInteraction hasInteraction, FluidInteraction FluidInteraction) {
        FluidType fluidType = FluidTypeWrapper.of(fluid);
        InteractionInformation interactionInformation = new InteractionInformation(hasInteraction, FluidInteraction);

        addInteraction(fluidType, interactionInformation);
    }

    public void createForBlock(FluidStackJS fluid, FluidStackJS interact, Block sourceTransforToBlock, Block flowingTransforToBlock) {
        FluidType fluidType = FluidTypeWrapper.of(fluid);
        FluidStackJS interactFluid = FluidStackJS.of(interact);
        addInteraction(fluidType, getForBlock(interactFluid, sourceTransforToBlock, flowingTransforToBlock));
    }
}
