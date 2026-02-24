package com.miiickey.fluidjs.kubejs.interaction;

import com.miiickey.fluidjs.FluidJS;
import com.miiickey.fluidjs.kubejs.FluidEvents;
import dev.latvian.mods.kubejs.event.KubeEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.fluids.FluidInteractionRegistry;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidType;
import org.jetbrains.annotations.NotNull;

import static com.miiickey.fluidjs.kubejs.interaction.helper.InteractionInformationHelper.*;
import static net.neoforged.neoforge.fluids.FluidInteractionRegistry.addInteraction;

@EventBusSubscriber(modid = FluidJS.MOD_ID)
public class FluidInteractRegistryEvent implements KubeEvent {

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            FluidInteractRegistryEvent interactEvent = new FluidInteractRegistryEvent();
            FluidEvents.FLUID_INTERACT_REGISTRY.post(interactEvent);
        });
    }

    public void create(@NotNull FluidStack fluid, FluidInteractionRegistry.HasFluidInteraction hasInteraction, FluidInteractionRegistry.FluidInteraction FluidInteraction) {
        FluidType fluidType = fluid.getFluidType();
        FluidInteractionRegistry.InteractionInformation interactionInformation = new FluidInteractionRegistry.InteractionInformation(hasInteraction, FluidInteraction);

        addInteraction(fluidType, interactionInformation);
    }

    public void createForItem(@NotNull FluidStack fluid, FluidStack interact, ItemStack itemStack) {
        FluidType fluidType = fluid.getFluidType();

        addInteraction(fluidType, getForItem(interact, itemStack));
    }

    public void createForBlock(@NotNull FluidStack fluid, FluidStack interact, Block sourceTransforToBlock, Block flowingTransforToBlock) {
        FluidType fluidType = fluid.getFluidType();

        addInteraction(fluidType, getForBlock(interact, sourceTransforToBlock, flowingTransforToBlock));
    }

    public void createForFluid(@NotNull FluidStack fluid, FluidStack interact, FluidStack output) {
        FluidType fluidType = fluid.getFluidType();

        addInteraction(fluidType, getForFluid(interact, output));
    }

    public void createForExplosion(@NotNull FluidStack fluid, FluidStack interact, Level.ExplosionInteraction explosion, float strength) {
        FluidType fluidType = fluid.getFluidType();

        addInteraction(fluidType, getForExplosion(interact, explosion, strength));
    }

    public void createForEntity(@NotNull FluidStack fluid, FluidStack interact, EntityType<? extends Entity> entityType) {
        FluidType fluidType = fluid.getFluidType();

        addInteraction(fluidType, getForEntity(interact, entityType));
    }
}
