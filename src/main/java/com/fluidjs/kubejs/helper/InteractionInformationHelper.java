package com.fluidjs.kubejs.helper;

import dev.latvian.mods.kubejs.fluid.FluidStackJS;
import dev.latvian.mods.kubejs.level.BlockContainerJS;
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
import net.minecraftforge.fluids.FluidType;
import org.jetbrains.annotations.NotNull;

import static net.minecraftforge.fluids.FluidInteractionRegistry.*;

public class InteractionInformationHelper {

    @HideFromJS
    public static @NotNull InteractionInformation getForItem(FluidStackJS interactFluid, ItemStack item) {
        HasFluidInteraction hasFluidInteraction = (level, currentPos, relativePos, currentState) -> {
            FluidType interactFluidType = interactFluid.getFluid().getFluidType();
            FluidType relativeFluidType = level.getFluidState(relativePos).getFluidType();

            return relativeFluidType.equals(interactFluidType);
        };
        FluidInteraction fluidInteraction = (level, currentPos, relativePos, currentState) -> {
            BlockContainerJS blockContainer = new BlockContainerJS(level, currentPos);
            BlockPos sourcePos = FluidSourceTraceHelper.traceFluidSource(level, currentPos, 0);
            FluidState sourceFluidState = level.getFluidState(sourcePos);
            FluidState relativeFluidState = level.getFluidState(relativePos);

            if (sourceFluidState.isSource()) {
                blockContainer.popItem(item);
                level.setBlockAndUpdate(sourcePos, Blocks.AIR.defaultBlockState());
                level.levelEvent(1501, currentPos, 0);
            }

            if (relativeFluidState.getAmount() <= currentState.getAmount() && !sourceFluidState.isSource()) {
                level.setBlockAndUpdate(currentPos, Blocks.AIR.defaultBlockState());
            }
        };

        return new InteractionInformation(hasFluidInteraction, fluidInteraction);
    }

    @HideFromJS
    public static @NotNull InteractionInformation getForBlock(FluidStackJS interactFluid, Block sourceTransforToBlock, Block flowingTransforToBlock) {
        HasFluidInteraction hasFluidInteraction = (level, currentPos, relativePos, currentState) -> {
            FluidType interactFluidType = interactFluid.getFluid().getFluidType();
            FluidType relativeFluidType = level.getFluidState(relativePos).getFluidType();

            return relativeFluidType.equals(interactFluidType);
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

    @HideFromJS
    public static @NotNull InteractionInformation getForFluid(FluidStackJS interactFluid, FluidStackJS outputFluid) {
        HasFluidInteraction hasFluidInteraction = (level, currentPos, relativePos, currentState) -> {
            FluidType interactFluidType = interactFluid.getFluid().getFluidType();
            FluidType relativeFluidType = level.getFluidState(relativePos).getFluidType();

            return relativeFluidType.equals(interactFluidType);
        };
        FluidInteraction fluidInteraction = (level, currentPos, relativePos, currentState) -> {
            BlockPos sourcePos = FluidSourceTraceHelper.traceFluidSource(level, currentPos, 0);
            BlockPos interactSourcePos = FluidSourceTraceHelper.traceFluidSource(level, relativePos, 0);
            FluidState sourceFluidState = level.getFluidState(sourcePos);
            FluidState interactSourceFluidState = level.getFluidState(interactSourcePos);
            BlockState outputBlockState = outputFluid.getFluid().defaultFluidState().createLegacyBlock();
            FluidState relativeFluidState = level.getFluidState(relativePos);

            if (sourceFluidState.isSource() && interactSourceFluidState.isSource()) {
                level.setBlockAndUpdate(currentPos, outputBlockState);
                level.setBlockAndUpdate(sourcePos, Blocks.AIR.defaultBlockState());
                level.setBlockAndUpdate(interactSourcePos, Blocks.AIR.defaultBlockState());
                level.levelEvent(1501, currentPos, 0);
            }

            if (relativeFluidState.getAmount() <= currentState.getAmount() && !sourceFluidState.isSource()) {
                level.setBlockAndUpdate(currentPos, Blocks.AIR.defaultBlockState());
            }
        };

        return new InteractionInformation(hasFluidInteraction, fluidInteraction);
    }

    @HideFromJS
    public static @NotNull InteractionInformation getForExplosion(FluidStackJS interactFluid, Level.ExplosionInteraction explosion, float strength) {
        HasFluidInteraction hasFluidInteraction = (level, currentPos, relativePos, currentState) -> {
            FluidType interactFluidType = interactFluid.getFluid().getFluidType();
            FluidType relativeFluidType = level.getFluidState(relativePos).getFluidType();

            return relativeFluidType.equals(interactFluidType);
        };
        FluidInteraction fluidInteraction = (level, currentPos, relativePos, currentState) -> {
            FluidType interactFluidType = interactFluid.getFluid().getFluidType();
            FluidType relativeFluidType = level.getFluidState(relativePos).getFluidType();

            level.explode(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), strength, explosion);
            level.setBlockAndUpdate(currentPos, Blocks.AIR.defaultBlockState());

            if (interactFluidType == relativeFluidType) {
                level.setBlockAndUpdate(relativePos, Blocks.AIR.defaultBlockState());
            }

            level.levelEvent(1501, currentPos, 0);
        };

        return new InteractionInformation(hasFluidInteraction, fluidInteraction);
    }

    @HideFromJS
    public static @NotNull InteractionInformation getForEntity(FluidStackJS interactFluid, EntityType<? extends Entity> entityType) {
        HasFluidInteraction hasFluidInteraction = (level, currentPos, relativePos, currentState) -> {
            FluidType interactFluidType = interactFluid.getFluid().getFluidType();
            FluidType relativeFluidType = level.getFluidState(relativePos).getFluidType();

            return relativeFluidType.equals(interactFluidType);
        };
        FluidInteraction fluidInteraction = (level, currentPos, relativePos, currentState) -> {
            Entity summonEntity = entityType.create(level);
            BlockPos sourcePos = FluidSourceTraceHelper.traceFluidSource(level, currentPos, 0);
            FluidState sourceFluidState = level.getFluidState(sourcePos);
            FluidState relativeFluidState = level.getFluidState(relativePos);

            if (summonEntity != null && sourceFluidState.isSource()) {
                summonEntity.setPos(currentPos.getCenter());
                level.addFreshEntity(summonEntity);
                level.setBlockAndUpdate(sourcePos, Blocks.AIR.defaultBlockState());
                level.levelEvent(1501, currentPos, 0);
            }

            if (relativeFluidState.getAmount() <= currentState.getAmount() && !sourceFluidState.isSource()) {
                level.setBlockAndUpdate(currentPos, Blocks.AIR.defaultBlockState());
            }
        };

        return new InteractionInformation(hasFluidInteraction, fluidInteraction);
    }
}
