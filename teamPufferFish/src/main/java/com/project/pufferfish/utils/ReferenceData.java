package com.project.pufferfish.utils;

import net.minecraft.util.ResourceLocation;

public class ReferenceData {
    public static final String MODID = "pufferfish";
    public static final String NAME = "Invaders Mod";
    public static final String VERSION = "0.0.1";
    public static final String CLIENT_PROXY = "";
    public static final String SERVER_PROXY = "y";
    public static final String DESCRIPTION = "Invader game within Minecraft";
    public static final String AUTHOR = "Team pufferfish";
    public static final String CREDIT = "Inspired and borrowed / modified many from SuperHB";
    public static final String URL = "";
    public static final String LOGO = "";
    public static final String UPDATE_URL = "";

    public static ResourceLocation createResource (String key) {
        return new ResourceLocation(MODID, key);
    }
}
