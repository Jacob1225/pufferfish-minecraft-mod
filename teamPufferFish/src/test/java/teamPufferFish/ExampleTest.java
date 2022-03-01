package teamPufferFish;

import net.minecraft.block.Blocks;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ExampleTest
{
    @Test
    public void testStoneRegistryName()
    {
        assertEquals("minecraft:stone", Blocks.STONE.getRegistryName().toString());
    }
}

