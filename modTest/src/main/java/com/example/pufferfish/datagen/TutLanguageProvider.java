package com.example.pufferfish.datagen;

import com.example.pufferfish.PufferFish;
import com.example.pufferfish.setup.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

import static com.example.pufferfish.blocks.GeneratorBlock.MESSAGE_GENERATOR;
import static com.example.pufferfish.blocks.PowergenBlock.MESSAGE_POWERGEN;
import static com.example.pufferfish.blocks.PowergenBlock.SCREEN_TUTORIAL_POWERGEN;
import static com.example.pufferfish.setup.ModSetup.TAB_NAME;

public class TutLanguageProvider extends LanguageProvider {

    public TutLanguageProvider(DataGenerator gen, String locale) {
        super(gen, PufferFish.MODID, locale);
    }

    @Override
    protected void addTranslations() {
        add("itemGroup." + TAB_NAME, "Tutorial");
        add(MESSAGE_POWERGEN, "Power generator generating %s per tick!");
        add(MESSAGE_GENERATOR, "Generate ores from ingots!");
        add(SCREEN_TUTORIAL_POWERGEN, "Power generator");

        add(Registration.GENERATOR.get(), "Generator");
        add(Registration.POWERGEN.get(), "Power generator");
        add(Registration.PORTAL_BLOCK.get(), "Mysterious Portal");

        add(Registration.MYSTERIOUS_ORE_OVERWORLD.get(), "Mysterious ore");
        add(Registration.MYSTERIOUS_ORE_NETHER.get(), "Mysterious ore");
        add(Registration.MYSTERIOUS_ORE_END.get(), "Mysterious ore");
        add(Registration.MYSTERIOUS_ORE_DEEPSLATE.get(), "Mysterious ore");

        add(Registration.RAW_MYSTERIOUS_CHUNK.get(), "Mysterious Raw Chunk");
        add(Registration.MYSTERIOUS_INGOT.get(), "Mysterious Ingot");
        add(Registration.THIEF_EGG.get(), "Thief Egg");

        add(Registration.THIEF.get(), "Thief");
    }
}
