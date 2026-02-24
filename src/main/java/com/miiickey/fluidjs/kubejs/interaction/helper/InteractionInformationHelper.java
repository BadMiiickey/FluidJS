package com.miiickey.fluidjs.kubejs.interaction.helper;

import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.neoforge.fluids.FluidInteractionRegistry;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidType;
import org.jetbrains.annotations.NotNull;

public class InteractionInformationHelper {

    @HideFromJS
    public static @NotNull FluidInteractionRegistry.InteractionInformation getForItem(FluidStack interactFluid, ItemStack item) {
        FluidInteractionRegistry.HasFluidInteraction hasFluidInteraction = (level, currentPos, relativePos, currentState) -> {
            FluidType interactFluidType = interactFluid.getFluidType();
            FluidType relativeFluidType = level.getFluidState(relativePos).getFluidType();

            return relativeFluidType.equals(interactFluidType);
        };
        FluidInteractionRegistry.FluidInteraction fluidInteraction = (level, currentPos, relativePos, currentState) -> {
            BlockPos sourcePos = FluidSourceTraceHelper.traceFluidSource(level, currentPos, 0);
            FluidState sourceFluidState = level.getFluidState(sourcePos);

            if (sourceFluidState.isSource()) {
                Block.popResource(level, currentPos, item);
                level.setBlockAndUpdate(sourcePos, Blocks.AIR.defaultBlockState());
                level.levelEvent(1501, currentPos, 0);
            }

            if (!sourceFluidState.isSource() && !currentState.isSource()) {
                level.setBlockAndUpdate(currentPos, Blocks.AIR.defaultBlockState());
            }
        };

        return new FluidInteractionRegistry.InteractionInformation(hasFluidInteraction, fluidInteraction);
    }

    @HideFromJS
    public static @NotNull FluidInteractionRegistry.InteractionInformation getForBlock(FluidStack interactFluid, Block sourceTransforToBlock, Block flowingTransforToBlock) {
        FluidInteractionRegistry.HasFluidInteraction hasFluidInteraction = (level, currentPos, relativePos, currentState) -> {
            FluidType interactFluidType = interactFluid.getFluidType();
            FluidType relativeFluidType = level.getFluidState(relativePos).getFluidType();

            return relativeFluidType.equals(interactFluidType);
        };
        FluidInteractionRegistry.FluidInteraction fluidInteraction = (level, currentPos, relativePos, currentState) -> {
            BlockState sourceBlockState = sourceTransforToBlock.defaultBlockState();
            BlockState flowingBlockState = flowingTransforToBlock.defaultBlockState();
            BlockState newState = currentState.isSource() ? sourceBlockState : flowingBlockState;

            level.setBlockAndUpdate(currentPos, newState);
            level.levelEvent(1501, currentPos, 0);
        };

        return new FluidInteractionRegistry.InteractionInformation(hasFluidInteraction, fluidInteraction);
    }

    @HideFromJS
    public static @NotNull FluidInteractionRegistry.InteractionInformation getForFluid(FluidStack interactFluid, FluidStack outputFluid) {
        FluidInteractionRegistry.HasFluidInteraction hasFluidInteraction = (level, currentPos, relativePos, currentState) -> {
            FluidType interactFluidType = interactFluid.getFluidType();
            FluidType relativeFluidType = level.getFluidState(relativePos).getFluidType();

            return relativeFluidType.equals(interactFluidType);
        };
        FluidInteractionRegistry.FluidInteraction fluidInteraction = (level, currentPos, relativePos, currentState) -> {
            BlockPos sourcePos = FluidSourceTraceHelper.traceFluidSource(level, currentPos, 0);
            BlockPos interactSourcePos = FluidSourceTraceHelper.traceFluidSource(level, relativePos, 0);
            FluidState sourceFluidState = level.getFluidState(sourcePos);
            FluidState interactSourceFluidState = level.getFluidState(interactSourcePos);
            BlockState outputBlockState = outputFluid.getFluid().defaultFluidState().createLegacyBlock();

            if (sourceFluidState.isSource() && interactSourceFluidState.isSource()) {
                level.setBlockAndUpdate(currentPos, outputBlockState);
                level.setBlockAndUpdate(interactSourcePos, Blocks.AIR.defaultBlockState());

                if (currentPos != sourcePos) {
                    level.setBlockAndUpdate(sourcePos, Blocks.AIR.defaultBlockState());
                }

                level.levelEvent(1501, currentPos, 0);
            }

            if (!sourceFluidState.isSource() && !currentState.isSource()) {
                level.setBlockAndUpdate(currentPos, Blocks.AIR.defaultBlockState());
            }
        };

        return new FluidInteractionRegistry.InteractionInformation(hasFluidInteraction, fluidInteraction);
    }

    @HideFromJS
    public static @NotNull FluidInteractionRegistry.InteractionInformation getForExplosion(FluidStack interactFluid, Level.ExplosionInteraction explosion, float strength) {
        FluidInteractionRegistry.HasFluidInteraction hasFluidInteraction = (level, currentPos, relativePos, currentState) -> {
            FluidType interactFluidType = interactFluid.getFluidType();
            FluidType relativeFluidType = level.getFluidState(relativePos).getFluidType();

            return relativeFluidType.equals(interactFluidType);
        };
        FluidInteractionRegistry.FluidInteraction fluidInteraction = (level, currentPos, relativePos, currentState) -> {
            FluidType interactFluidType = interactFluid.getFluidType();
            FluidType relativeFluidType = level.getFluidState(relativePos).getFluidType();

            level.explode(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), strength, explosion);
            level.setBlockAndUpdate(currentPos, Blocks.AIR.defaultBlockState());

            if (interactFluidType == relativeFluidType) {
                level.setBlockAndUpdate(relativePos, Blocks.AIR.defaultBlockState());
            }

            level.levelEvent(1501, currentPos, 0);
        };

        return new FluidInteractionRegistry.InteractionInformation(hasFluidInteraction, fluidInteraction);
    }

    @HideFromJS
    public static @NotNull FluidInteractionRegistry.InteractionInformation getForEntity(FluidStack interactFluid, EntityType<? extends Entity> entityType) {
        FluidInteractionRegistry.HasFluidInteraction hasFluidInteraction = (level, currentPos, relativePos, currentState) -> {
            FluidType interactFluidType = interactFluid.getFluidType();
            FluidType relativeFluidType = level.getFluidState(relativePos).getFluidType();

            return relativeFluidType.equals(interactFluidType);
        };
        FluidInteractionRegistry.FluidInteraction fluidInteraction = (level, currentPos, relativePos, currentState) -> {
            Entity summonEntity = entityType.create(level);
            BlockPos sourcePos = FluidSourceTraceHelper.traceFluidSource(level, currentPos, 0);
            FluidState sourceFluidState = level.getFluidState(sourcePos);

            if (summonEntity != null && sourceFluidState.isSource()) {
                summonEntity.setPos(currentPos.getCenter());
                level.addFreshEntity(summonEntity);
                level.setBlockAndUpdate(sourcePos, Blocks.AIR.defaultBlockState());
                level.levelEvent(1501, currentPos, 0);
            }

            if (!sourceFluidState.isSource() && !currentState.isSource()) {
                level.setBlockAndUpdate(currentPos, Blocks.AIR.defaultBlockState());
            }
        };

        return new FluidInteractionRegistry.InteractionInformation(hasFluidInteraction, fluidInteraction);
    }
}
