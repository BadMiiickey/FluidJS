package com.fluidjs.kubejs.createSource;

import dev.latvian.mods.kubejs.fluid.FluidStackJS;
import dev.latvian.mods.kubejs.level.BlockContainerJS;
import dev.latvian.mods.kubejs.level.LevelEventJS;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;

public class CreateFluidSourceEventJS extends LevelEventJS {
    public static final Map<ResourceLocation, Boolean> FLUID_SOURCE_RULES = new HashMap<>();
    private final Level level;
    private final BlockPos blockPos;
    private final BlockState blockState;
    private final BlockContainerJS block;
    private final FluidState fluidState;

    public CreateFluidSourceEventJS(Level level, BlockPos blockPos, BlockState blockState) {
        this.level = level;
        this.blockPos = blockPos;
        this.blockState = blockState;
        this.block = new BlockContainerJS(level, blockPos);
        this.fluidState = blockState.getFluidState();
    }

    public void create(FluidStackJS fluid) {
        ResourceLocation fluidId = ForgeRegistries.FLUIDS.getKey(fluid.getFluid());

        if (fluidId != null) {
            FLUID_SOURCE_RULES.put(fluidId, true);
        }
    }

    public void remove(FluidStackJS fluid) {
        ResourceLocation fluidId = ForgeRegistries.FLUIDS.getKey(fluid.getFluid());

        if (fluidId != null) {
            FLUID_SOURCE_RULES.put(fluidId, false);
        }
    }

    @HideFromJS
    public Boolean getRule(ResourceLocation fluidId) {
        return FLUID_SOURCE_RULES.get(fluidId);
    }

    public Level getLevel() {
        return level;
    }

    public BlockPos getBlockPos() {
        return blockPos;
    }

    public BlockState getBlockState() {
        return blockState;
    }

    public BlockContainerJS getBlock() {
        return block;
    }

    public FluidState getFluidState() {
        return fluidState;
    }
}
