package com.miiickey.fluidjs.kubejs.properties;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashMap;
import java.util.Map;

public class FluidBuilderRandomTick {
    public static final Map<ResourceLocation, IFluidRandomTickCallback> CALLBACKS = new HashMap<>();

    public static void register(ResourceLocation id, IFluidRandomTickCallback callback) {
        CALLBACKS.put(id, callback);
    }

    public static class TickContext {
        public final BlockState state;
        public final ServerLevel level;
        public final BlockPos pos;
        public final RandomSource random;

        public TickContext(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
            this.state = state;
            this.level = level;
            this.pos = pos;
            this.random = random;
        }
    }
}
