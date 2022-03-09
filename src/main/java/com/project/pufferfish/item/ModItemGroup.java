package com.project.pufferfish.item;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ModItemGroup {

    public static final ItemGroup ARCADE_GROUP = new ItemGroup("arcadeMachineTab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.GAME_TOKEN.get());
        }
    };
}
