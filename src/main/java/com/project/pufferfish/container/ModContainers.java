package com.project.pufferfish.container;

import com.project.pufferfish.Invaders;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModContainers {

    public static DeferredRegister<ContainerType<?>> CONTAINERS
            = DeferredRegister.create(ForgeRegistries.CONTAINERS, Invaders.MOD_ID);

    // registers the container
    public static final RegistryObject<ContainerType<ArcadeMachineContainer>> ARCADE_MACHINE_CONTAINER
            = CONTAINERS.register("arcade_machine_container",
            () -> IForgeContainerType.create(((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                World world = inv.player.getCommandSenderWorld();
                return new ArcadeMachineContainer(windowId, world, pos, inv, inv.player);
            })));

    public static void register (IEventBus eventBus) {
        CONTAINERS.register(eventBus);
    }

}
