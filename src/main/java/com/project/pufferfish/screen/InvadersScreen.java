package com.project.pufferfish.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.project.pufferfish.Invaders;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * This is the main class for the GUI screen to play the game. It also contains the "score" for the game
 */
public class InvadersScreen extends Screen {
    private int delayTicker;

    // Gui background (black image)
    private static final ResourceLocation background = new ResourceLocation(Invaders.MOD_ID, "textures/gui/invaders_gui.png");

    //Gui variables
    public int textureWidth = 256, textureHeight = 266;
    private int xSize = 0, ySize = 0;
    private float scale = 1;
    int relX = 0;
    int relY = 0;

    //Display score variables
    MatrixStack matrixStack;
    private int score;

    //Constructor variables
    private PlayerEntity player;
    private World world;
    private BlockPos pos;

    /**
     * Constructor class for Gui screen
     *
     * @param p_i51118_2_
     * @param world
     * @param tileEntity
     * @param pos
     * @param player
     */
    public InvadersScreen(boolean p_i51118_2_, World world, TileEntity tileEntity, @Nullable BlockPos pos, PlayerEntity player) {
        super(new TranslationTextComponent(p_i51118_2_ ? "deathScreen.title.hardcore" : "deathScreen.title"));
        this.pos = pos;
        this.world = world;
        this.player = player;
    }

    /**
     * initilizing screen
     */
    protected void init() {
        this.delayTicker = 0;
    }

    /**
     * Closes gui screen when esc key is pressed
     *
     * @return
     */
    public boolean shouldCloseOnEsc() {
        return true;
    }

    /**
     * Draw the gui screen and display score
     *
     * @param p_230430_1_
     * @param p_230430_2_
     * @param p_230430_3_
     * @param p_230430_4_
     */
    public void render(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
        this.matrixStack = p_230430_1_;
        relX = (this.width - textureWidth) / 2;
        relY = (this.height - textureHeight) / 2;
        assert this.minecraft != null;
        this.minecraft.getTextureManager().bind(background);
        this.blit(p_230430_1_, relX, relY, 0, 0, textureWidth, textureHeight);

        displayScore(this.matrixStack);

        super.render(this.matrixStack, p_230430_2_, p_230430_3_, p_230430_4_);
    }

    /**
     * Display the score on gui screen
     *
     * @param p_230430_1_
     */
    public void displayScore(MatrixStack p_230430_1_){
        int relX = (this.width - textureWidth) / 2;
        drawString(p_230430_1_, this.font, new TranslationTextComponent("Score: ").append((new StringTextComponent(Integer.toString(score)).withStyle(TextFormatting.WHITE))), relX, 10, 16777215);
        drawString(p_230430_1_, this.font, new TranslationTextComponent("Press t to score up").withStyle(TextFormatting.WHITE), relX, 25, 16777215);
        drawString(p_230430_1_, this.font, new TranslationTextComponent("Press r to reset").withStyle(TextFormatting.WHITE), relX, 40, 16777215);
    }

    /**
     * Register key inputs and adjust score as needed
     *
     * @param typedChar
     * @param keyCode
     * @return
     */
    @Override
    public boolean charTyped(char typedChar, int keyCode){
        if (typedChar == 'r') {
            scoreReset();
            drawCenteredString(this.matrixStack, this.font, new TranslationTextComponent("Score: ").append((new StringTextComponent(Integer.toString(score)).withStyle(TextFormatting.WHITE))), this.width / 2, 30, 16777215);
        }
        if (typedChar == 't') {
            scoreUp(10);
            drawCenteredString(this.matrixStack, this.font, new TranslationTextComponent("Score: ").append((new StringTextComponent(Integer.toString(score)).withStyle(TextFormatting.WHITE))), this.width / 2, 30, 16777215);
        }
        super.charTyped(typedChar, keyCode);
        return true;
    }

    /**
     * Resets the score
     */
    public void scoreReset() {
        this.score = 0;
    }

    /**
     * Increment the score
     *
     * @param upBy
     */
    public void scoreUp(int upBy) {
        this.score += upBy;
    }

    /**
     * pause screen
     *
     * @return
     */
    public boolean isPauseScreen() {
        return false;
    }

    public void tick() {
        super.tick();
        ++this.delayTicker;
        if (this.delayTicker == 20) {
            for (Widget widget : this.buttons) {
                widget.active = true;
            }
        }
    }
}


