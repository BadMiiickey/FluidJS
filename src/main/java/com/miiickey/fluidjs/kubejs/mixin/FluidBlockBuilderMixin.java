package com.miiickey.fluidjs.kubejs.mixin;

import com.miiickey.fluidjs.kubejs.properties.FluidBuilderRandomTick;
import dev.latvian.mods.kubejs.fluid.FluidBlockBuilder;
import dev.latvian.mods.kubejs.registry.BuilderBase;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(FluidBlockBuilder.class)
public class FluidBlockBuilderMixin {

    @ModifyArg(
            method = "createObject*",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/LiquidBlock;<init>(Lnet/minecraft/world/level/material/FlowingFluid;Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)V"
            ),
            index = 1,
            remap = false
    )
    public BlockBehaviour.Properties injectRandomTick(BlockBehaviour.Properties properties) {
        ResourceLocation id = ((BuilderBase<?>) (Object) this).id;

        if (FluidBuilderRandomTick.CALLBACKS.containsKey(id)) {
            properties.randomTicks();
        }

        return properties;
    }
}
