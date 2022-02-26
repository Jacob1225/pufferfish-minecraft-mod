package com.project.pufferfish;
import com.project.pufferfish.utils.KeyPressHandler;
import com.project.pufferfish.utils.ReferenceData;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod(modid = ReferenceData.MODID, name = ReferenceData.NAME, version = ReferenceData.VERSION)
public class Invaders {
        @Mod.Instance(ReferenceData.MODID)
        public static Invaders instance;

        // Logger
        public static final Logger logger = LogManager.getLogger(ReferenceData.MODID);


        @EventHandler
        public void preInit (FMLPreInitializationEvent event) {

        }

        @EventHandler
        public void init (FMLInitializationEvent event) {
                MinecraftForge.EVENT_BUS.register(new KeyPressHandler());
        }

        @EventHandler
        public void postInit (FMLPostInitializationEvent event) {

        }


}
