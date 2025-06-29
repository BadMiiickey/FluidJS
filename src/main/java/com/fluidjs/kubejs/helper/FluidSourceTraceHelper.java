package com.fluidjs.kubejs.helper;

import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;

public class FluidSourceTraceHelper {
    private static final int MAX_TRACE_DEPTH = 128;

    public static BlockPos traceFluidSource(Level level, BlockPos currentPos, int depth) {
        if (depth > MAX_TRACE_DEPTH) return currentPos;

        FluidState currentState = level.getFluidState(currentPos);

        if (currentState.isEmpty()) return currentPos;
        if (currentState.isSource()) return currentPos;
        if (currentState.getValue(BlockStateProperties.FALLING)) {
            BlockPos upperPos = verticalTrace(level, currentPos, depth);

            if (upperPos != null && !upperPos.equals(currentPos)) return traceFluidSource(level, upperPos, depth + 1);
        } else {
            BlockPos horizontalPos = horizontalTrace(level, currentPos, depth);

            if (horizontalPos != null && !horizontalPos.equals(currentPos)) {
                return traceFluidSource(level, horizontalPos, depth + 1);
            }
        }

        return currentPos;
    }

    @HideFromJS
    private static BlockPos horizontalTrace(Level level, BlockPos currentPos, int depth) {
        FluidState currentState = level.getFluidState(currentPos);
        BlockPos[] directions = {
            currentPos.north(), currentPos.south(),
            currentPos.east(), currentPos.west()
        };

        for (BlockPos relativePos : directions) {
            FluidState relativeState = level.getFluidState(relativePos);

            if (relativeState.getFluidType() != currentState.getFluidType()) continue;
            if (relativeState.isEmpty()) continue;
            if (relativeState.isSource()) return relativePos;
            if (relativeState.getValue(BlockStateProperties.FALLING)) return verticalTrace(level, relativePos, depth + 1);
            if (relativeState.getAmount() > currentState.getAmount()) return horizontalTrace(level, relativePos, depth + 1);
        }

        return currentPos;
    }

    @HideFromJS
    private static BlockPos verticalTrace(Level level, BlockPos currentPos, int depth) {
        FluidState currentState = level.getFluidState(currentPos);
        FluidState relativeState = level.getFluidState(currentPos.above());

        if (relativeState.getFluidType() != currentState.getFluidType()) return currentPos;
        if (relativeState.isEmpty()) return currentPos;
        if (relativeState.isSource()) return currentPos.above();
        if (relativeState.getValue(BlockStateProperties.FALLING)) return verticalTrace(level, currentPos.above(), depth + 1);
        if (relativeState.getAmount() < currentState.getAmount()) return horizontalTrace(level, currentPos.above(), depth + 1);

        return currentPos;
    }
}
