package com.miiickey.fluidjs.kubejs.source;

import dev.latvian.mods.kubejs.level.KubeLevelEvent;
import dev.latvian.mods.kubejs.level.LevelBlock;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.neoforge.event.level.block.CreateFluidSourceEvent;

public class CreateFluidSourceEventJS implements KubeLevelEvent {
    @HideFromJS private final CreateFluidSourceEvent event;
    private final Level level;
    private final BlockPos blockPos;
    private final BlockState blockState;
    private final FluidState fluidState;
    private final LevelBlock block;

    public CreateFluidSourceEventJS(CreateFluidSourceEvent event) {
        this.event = event;
        this.level = event.getLevel();
        this.blockPos = event.getPos();
        this.blockState = event.getState();
        this.fluidState = blockState.getFluidState();
        this.block = new LevelBlock() {
            @Override
            public Level getLevel() {
                return level;
            }

            @Override
            public BlockPos getPos() {
                return blockPos;
            }
        };
    }

    public void allow() {
        event.setCanConvert(true);
    }

    public void deny() {
        event.setCanConvert(false);
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

    public FluidState getFluidState() {
        return fluidState;
    }

    public LevelBlock getBlock() {
        return block;
    }
}
