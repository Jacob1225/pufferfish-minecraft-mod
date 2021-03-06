package com.project.pufferfish.item;

import com.project.pufferfish.Invaders;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SwordItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Invaders.MOD_ID);

    public static final RegistryObject<Item> GAME_TOKEN = ITEMS.register("game_token",
            () -> new Item(new Item.Properties().tab(ModItemGroup.ARCADE_GROUP)));

    public static final RegistryObject<Item> PRIZE_TICKET = ITEMS.register("prize_ticket",
            () -> new Item(new Item.Properties().tab(ModItemGroup.ARCADE_GROUP)));

    public static final RegistryObject<Item> SPACE_INVADERS_TROPHY = ITEMS.register("space_invaders_trophy",
            () -> new SwordItem(ModItemTier.TROPHY, 2, 3f,
                    new Item.Properties().tab(ModItemGroup.ARCADE_GROUP)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
