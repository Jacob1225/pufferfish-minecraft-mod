package com.project.pufferfish.item;

import com.project.pufferfish.Invaders;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Invaders.MOD_ID);

    public static final RegistryObject<Item> GAME_TOKEN = ITEMS.register("game_token",
            () -> new Item(new Item.Properties().tab(ItemGroup.TAB_MISC)));

    public static final RegistryObject<Item> PRIZE_TICKET = ITEMS.register("prize_ticket",
            () -> new Item(new Item.Properties().tab(ItemGroup.TAB_MISC)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
