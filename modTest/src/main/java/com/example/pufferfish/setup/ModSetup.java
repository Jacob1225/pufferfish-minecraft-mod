package com.example.pufferfish.setup;

import com.example.pufferfish.entities.ThiefEntity;
import com.example.pufferfish.worldgen.dimensions.Dimensions;
import com.example.pufferfish.worldgen.ores.Ores;
import com.example.pufferfish.worldgen.structures.Structures;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class ModSetup {

    public static final String TAB_NAME = "assets";
    public static final CreativeModeTab ITEM_GROUP = new CreativeModeTab(TAB_NAME) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Items.DIAMOND);
        }
    };

    public static void setup() {
        IEventBus bus = MinecraftForge.EVENT_BUS;
        bus.addListener(Ores::onBiomeLoadingEvent);
//        bus.addListener(EventPriority.NORMAL, Structures::addDimensionalSpacing);
        bus.addListener(EventPriority.NORMAL, Structures::setupStructureSpawns);
    }

    public static void init(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            Ores.registerConfiguredFeatures();
            Structures.setupStructures();
            Structures.registerConfiguredStructures();
            Dimensions.register();
        });
    }

    @SubscribeEvent
    public static void onAttributeCreate(EntityAttributeCreationEvent event) {
        event.put(Registration.THIEF.get(), ThiefEntity.prepareAttributes().build());
    }
}
