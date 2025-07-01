package com.fluidjs.kubejs.createSource;

import com.fluidjs.FluidJS;
import com.fluidjs.kubejs.FluidEvents;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = FluidJS.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CreateFluidSourceHandler {

    @SubscribeEvent(priority = EventPriority.HIGH)
    @HideFromJS
    public static void onCreateFluidSource(BlockEvent.CreateFluidSourceEvent event) {
        Level level = event.getLevel();
        BlockPos blockPos = event.getPos();
        BlockState blockState = event.getState();
        FluidState fluidState = blockState.getFluidState();

        if (fluidState.isEmpty()) return;

        ResourceLocation fluidId = ForgeRegistries.FLUIDS.getKey(fluidState.getType());
        Boolean rule = CreateFluidSourceEventJS.FLUID_SOURCE_RULES.get(fluidId);

        if (rule != null) {
            event.setResult(rule ? Event.Result.ALLOW : Event.Result.DENY);
        }

        CreateFluidSourceEventJS eventJS = new CreateFluidSourceEventJS(level, blockPos, blockState);
        FluidEvents.FLUID_CREATE_SOURCE_REGISTRY.post(eventJS);
    }
}
