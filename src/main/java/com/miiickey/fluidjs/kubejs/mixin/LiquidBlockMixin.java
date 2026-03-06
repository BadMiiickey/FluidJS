package com.miiickey.fluidjs.kubejs.mixin;

import com.miiickey.fluidjs.kubejs.properties.FluidBuilderRandomTick;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(LiquidBlock.class)
public class LiquidBlockMixin extends Block {
    public LiquidBlockMixin(Properties properties) {
        super(properties);
    }

    /**
     * @author Miiickey
     * @reason 修复 LiquidBlock 默认忽略 Properties 中 randomTick 设置的问题
     */
    @Overwrite
    public boolean isRandomlyTicking(@NotNull BlockState state) {
        if (super.isRandomlyTicking(state)) return true;
        if (state.getFluidState().isRandomlyTicking()) return true;

        try {
            ResourceLocation id = BuiltInRegistries.FLUID.getKey(state.getFluidState().getType());

            if (!id.getPath().equals("empty") && FluidBuilderRandomTick.CALLBACKS.containsKey(id)) {
                return true;
            }
        } catch (Exception ignored) {
        }

        return false;
    }

    @Override
    public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        super.randomTick(state, level, pos, random);

        if (!state.isRandomlyTicking()) return;

        ResourceLocation id = BuiltInRegistries.FLUID.getKey(state.getFluidState().getType());
        var callback = FluidBuilderRandomTick.CALLBACKS.get(id);

        if (callback == null) return;

        FluidBuilderRandomTick.TickContext context = new FluidBuilderRandomTick.TickContext(state, level, pos, random);

        callback.accept(context);
    }
}
