package com.project.pufferfish;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class Invaders {
        @Mod.Instance(Reference.MODID)
        public static Invaders instance;

        // Logger
        public static final Logger logger = LogManager.getLogger(Reference.MODID);


        @EventHandler
        public void preInit (FMLPreInitializationEvent event) {

        }

        @EventHandler
        public void init (FMLInitializationEvent event) {
        }

        @EventHandler
        public void postInit (FMLPostInitializationEvent event) {

        }


}
