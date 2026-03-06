package com.miiickey.fluidjs.kubejs.mixin;

import com.miiickey.fluidjs.kubejs.properties.FluidBuilderRandomTick;
import com.miiickey.fluidjs.kubejs.properties.IFluidBuilderRandomTick;
import com.miiickey.fluidjs.kubejs.properties.IFluidRandomTickCallback;
import dev.latvian.mods.kubejs.fluid.FluidBuilder;
import dev.latvian.mods.kubejs.registry.BuilderBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(FluidBuilder.class)
public class FluidBuilderMixin implements IFluidBuilderRandomTick {
    @Unique private boolean fluidjs$randomTick = false;

    @Override
    public FluidBuilder randomTick(IFluidRandomTickCallback callback) {
        this.fluidjs$randomTick = true;

        BuilderBase<?> object = (BuilderBase<?>) (Object) this;

        if (object.id != null) {
            FluidBuilderRandomTick.register(object.id, callback);
        }

        return (FluidBuilder) (Object) this;
    }
}
