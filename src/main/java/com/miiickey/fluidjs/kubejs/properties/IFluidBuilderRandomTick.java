package com.miiickey.fluidjs.kubejs.properties;

import dev.latvian.mods.kubejs.fluid.FluidBuilder;

public interface IFluidBuilderRandomTick {
    FluidBuilder randomTick(IFluidRandomTickCallback callback);
}
