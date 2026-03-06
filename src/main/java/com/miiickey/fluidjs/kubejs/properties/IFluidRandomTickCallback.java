package com.miiickey.fluidjs.kubejs.properties;

@FunctionalInterface
public interface IFluidRandomTickCallback {
    void accept(FluidBuilderRandomTick.TickContext context);
}
