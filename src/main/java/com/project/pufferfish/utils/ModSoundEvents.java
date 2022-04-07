package com.project.pufferfish.utils;

import com.project.pufferfish.Invaders;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraft.util.ResourceLocation;


public class ModSoundEvents {
	
	public static final DeferredRegister<SoundEvent> SOUND_EVENTS = 
			DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Invaders.MOD_ID);
	
	public static final RegistryObject<SoundEvent> shoot = 
			registerSoundEvent("shoot");
	
	public static final RegistryObject<SoundEvent> explosion = 
			registerSoundEvent("explosion");
	
	public static final RegistryObject<SoundEvent> invaderkilled =
			registerSoundEvent("invaderkilled");
	
	
	private static RegistryObject<SoundEvent> registerSoundEvent(String name){
		return SOUND_EVENTS.register(name,() -> new SoundEvent(new ResourceLocation(Invaders.MOD_ID, name)));
	}
	
	public static void register(IEventBus eventBus) {
		SOUND_EVENTS.register(eventBus);
	}
	
	
}
