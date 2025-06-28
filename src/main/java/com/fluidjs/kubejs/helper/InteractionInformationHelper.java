package com.fluidjs.kubejs.helper;

import dev.latvian.mods.kubejs.fluid.FluidStackJS;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import static net.minecraftforge.fluids.FluidInteractionRegistry.*;

public class InteractionInformationHelper {

    @HideFromJS
    public static @NotNull InteractionInformation getForBlock(FluidStackJS interactFluid, Block sourceTransforToBlock, Block flowingTransforToBlock) {
        HasFluidInteraction hasFluidInteraction = (level, currentPos, relativePos, currentState) -> {
            Block relativeBlock = level.getBlockState(relativePos).getBlock();
            ResourceLocation relativeBlockId = ForgeRegistries.BLOCKS.getKey(relativeBlock);

            if (relativeBlockId == null) return false;

            return relativeBlockId.toString().equals(interactFluid.getId());
        };
        FluidInteraction fluidInteraction = (level, currentPos, relativePos, currentState) -> {
            BlockState sourceBlockState = sourceTransforToBlock.defaultBlockState();
            BlockState flowingBlockState = flowingTransforToBlock.defaultBlockState();
            BlockState newState = currentState.isSource() ? sourceBlockState : flowingBlockState;

            level.setBlockAndUpdate(currentPos, newState);
            level.levelEvent(1501, currentPos, 0);
        };

        return new InteractionInformation(hasFluidInteraction, fluidInteraction);
    }
}
