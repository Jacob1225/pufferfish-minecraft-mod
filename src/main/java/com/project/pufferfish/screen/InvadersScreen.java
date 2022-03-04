//package com.project.pufferfish.screen;
//
//import com.mojang.blaze3d.matrix.MatrixStack;
//import com.mojang.blaze3d.platform.GlStateManager;
//import com.project.pufferfish.Invaders;
//import net.minecraft.client.gui.GuiButton;
//import net.minecraft.client.gui.GuiScreen;
//import net.minecraft.client.gui.screen.Screen;
//import net.minecraft.client.renderer.GlStateManager;
//import net.minecraft.client.resources.I18n;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.tileentity.TileEntity;
//import net.minecraft.util.ResourceLocation;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.world.World;
//import org.lwjgl.input.Keyboard;
//
//import javax.annotation.Nullable;
//import java.awt.*;
//import java.io.IOException;
//
//public class InvadersScreen extends Screen {
//    // Tick Variables
//    public int tickCounter = 0;
//    private boolean useTick = true;
//
//    // Menu Variables
//    public boolean inMenu = true;
//    public int menuOption = 0;
//    public int menu = 0, startMenu = 0;
//    public boolean useInternalMenu = true;
//
//    // GUI Variables
//    public int textureWidth = 256, textureHeight = 266;
//    private int guiLeft, guiTop;
//    private int xSize = 0, ySize = 0;
//    private ResourceLocation gui;
//    private int buttonX = 0, buttonY = 0;
//    public int buttonWidth, buttonHeight = 20;
//    private int[] offset = {0, 0};
//    private float scale = 1;
//    public int xScaled, yScaled;
//    private static final int GUI_X = 234, GUI_Y = 284;
//
//    // Cost Variables
//    private int cost = 1;
//    private boolean enoughCoins = true;
//    public boolean canGetCoinBack = true;
//    public int score = 0;
//
//    // Constructor Variable
////    private InvadersTile tileEntity;
//    private PlayerEntity player;
//    private World world;
//    private BlockPos pos;
//
//    // Arrow Texture
//    private static final ResourceLocation arrows = new ResourceLocation(Invaders.MOD_ID, "/textures/gui/arrows_gui.png");
//    private static final ResourceLocation background = new ResourceLocation(Invaders.MOD_ID, "textures/gui/invaders_gui.png");
//
//    public InvadersScreen(World worldIn, TileEntity tileEntity, @Nullable BlockPos pos, PlayerEntity player) {
//        super(worldIn, tileEntity, pos, player);
//        this.pos = pos;
//        this.world = worldIn;
//        this.player = player;
//        menu = startMenu;
//        this.font = minecraft.font;
//        setGuiSize(GUI_X, GUI_Y, 0.8F);
//        setTexture(background, 512, 512);
//    }
//
//
//    /**
//     * Set the width and height of the GUI Texture
//     *
//     * @param width  Width of Texture
//     * @param height Height of Texture
//     */
//    public void setGuiSize(int width, int height) {
//        xSize = width;
//        ySize = height;
//    }
//
//    /**
//     * Set the width and height of the GUI Texture along with Scale Factor
//     *
//     * @param width  Width of Texture
//     * @param height Height of Texture
//     * @param scale  Scale Factor
//     */
//    public void setGuiSize(int width, int height, float scale) {
//        xSize = width;
//        ySize = height;
//        this.scale = scale;
//    }
//
//    /**
//     * Set Scale Factor
//     *
//     * @param scale Scale Factor
//     */
//    public void setGuiScale(float scale) {
//        this.scale = scale;
//    }
//
//    /**
//     * Gets GUI Scale Factor
//     *
//     * @return scale
//     */
//    public float getGuiScale() {
//        return scale;
//    }
//
//    /**
//     * Disable Insert Menu to allow custom ones
//     *
//     * @param disable True = off, False = on
//     */
//    public void disableInternalMenu(boolean disable) {
//        useInternalMenu = !disable;
//    }
//
//
//    /**
//     * Set GUI Texture
//     *
//     * @param texture GUI Texture Location
//     */
//    public void setTexture(ResourceLocation texture) {
//        gui = texture;
//    }
//
//    /**
//     * Set GUI Texture with custom width and height
//     *
//     * @param texture GUI Texture Location
//     * @param width   Custom Texture Width
//     * @param height  Custom Texture Height
//     */
//    public void setTexture(ResourceLocation texture, int width, int height) {
//        gui = texture;
//        textureWidth = width;
//        textureHeight = height;
//    }
//
//    /**
//     * Set a custom size to the 'Insert Coin' Button
//     *
//     * @param width  Width of Button
//     * @param height Height of Button
//     */
//    public void setButtonSize(int width, int height) {
//        buttonWidth = width;
//        buttonHeight = height;
//    }
//
//
//    /**
//     * Returns World passed by GUIHandler
//     *
//     * @return world
//     */
//    public World getWorld() {
//        return world;
//    }
//
//    /**
//     * Returns position of block by GUIHandler.
//     * Used officially to play sounds
//     *
//     * @return BlockPos
//     */
//    public BlockPos getPos() {
//        return pos;
//    }
//
//    /**
//     * Returns player entity passed by GUIHandler
//     * Used officially to play sounds
//     *
//     * @return EntityPlayer
//     */
//    public PlayerEntity getPlayer() {
//        return player;
//    }
//
//    @Override
//    public void updateScreen() {
//        if (useTick) tickCounter++;
//    }
//
//
//    /**
//     * Draws the Game Board with specific dimensions
//     *
//     * @param mouseX
//     * @param mouseY
//     * @param partialTicks
//     */
//    @Override
//    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
//        xScaled = Math.round((width / 2) / scale);
//        yScaled = Math.round((height / 2) / scale);
//        GlStateManager._blendColor(1.0F, 1.0F, 1.0F, 1.0F);
//        GlStateManager.scale(scale, scale, scale);
//        this.minecraft.getTextureManager().getTexture(gui);
//        this.drawModalRectWithCustomSizedTexture(xScaled - (xSize / 2), yScaled - (ySize / 2), 0, 0, xSize, ySize, textureWidth, textureHeight);
//        super.render(matrixStack, mouseX, mouseY, partialTicks);
//
//        displayScore();
//    }
//
//    /**
//     * Displays the score on the board
//     */
//    public void displayScore(){
//        //this.font.draw(String.format("Score: %d", this.score), 160, 40, Color.white.getRGB());
//        //this.font.draw(String.format("Press enter to score up"), 160, 60, Color.white.getRGB());
//        //this.font.drawString(String.format("Press r to reset"), 160, 70, Color.white.getRGB());
//    }
//
//    /**
//     * Increments score when a key is pressed;
//     * Resets score when another key is pressed
//     *
//     * @param typedChar
//     * @param keyCode
//     * @throws IOException
//     */
//    @Override
//    protected void keyTyped(char typedChar, int keyCode) throws IOException {
//        System.out.println("====keyCode:" + keyCode);
//        //42: spacebar,  28: enter,  19: 'r'
//        if (keyCode == 19) {
//            scoreReset();
//            this.fontRenderer.drawString(String.format("Score: %d", this.score), 160, 40, Color.white.getRGB());
//        }
//        if (keyCode == 28) { // Enter, change it to work only when in-game
//            scoreUp(10);
//            System.out.println("new score:" + this.score);
//            this.font.draw().drawString(String.format("Score: %d", this.score), 160, 40, Color.white.getRGB());
//        }
//        super.charTyped(typedChar, keyCode);
//    }
//
//    /**
//     * Resets the score
//     */
//    public void scoreReset() {
//        this.score = 0;
//    }
//
//    /**
//     * Increment the score
//     *
//     * @param upBy
//     */
//    public void scoreUp(int upBy) {
//        System.out.println("-- score up --");
//        this.score += upBy;
//    }
//
//    /**
//     * Draw left arrow
//     */
//    public void drawLeftArrow(int x, int y) {
//        drawLeftArrow(x, y, false);
//    }
//
//    /**
//     * Draw right arrow
//     */
//    public void drawRightArrow(int x, int y) {
//        drawRightArrow(x, y, false);
//    }
//
//    /**
//     * Draw up arrow
//     */
//    public void drawUpArrow(int x, int y) {
//        drawUpArrow(x, y, false);
//    }
//
//    /**
//     * Draw down arrow
//     */
//    public void drawDownArrow(int x, int y) {
//        drawDownArrow(x, y, false);
//    }
//
//    /**
//     * Draw left arrow
//     *
//     * @param bind call texture manager to bind texture
//     */
//    public void drawLeftArrow(int x, int y, boolean bind) {
//        if (bind) this.minecraft.getTextureManager().bind(arrows);
//        this.drawModalRectWithCustomSizedTexture(x, y, 7, 0, 7, 11, 128, 128);
//    }
//
//    /**
//     * Draw right arrow
//     *
//     * @param bind call texture manager to bind texture
//     */
//    public void drawRightArrow(int x, int y, boolean bind) {
//        if (bind) this.minecraft.getTextureManager().bind(arrows);
//        this.drawScreen(x, y, 0, 0, 7, 11, 128, 128);
//    }
//
//    /**
//     * Draw up arrow
//     *
//     * @param bind call texture manager to bind texture
//     */
//    public void drawUpArrow(int x, int y, boolean bind) {
//        if (bind) this.minecraft.getTextureManager().bindTexture(arrows);
//        this.drawModalRectWithCustomSizedTexture(x, y, 25, 0, 11, 7, 128, 128);
//    }
//
//    /**
//     * Draw down arrow
//     *
//     * @param bind call texture manager to bind texture
//     */
//    public void drawDownArrow(int x, int y, boolean bind) {
//        if (bind) this.minecraft.getTextureManager().bind(arrows);
//        this.drawModalRectWithCustomSizedTexture(x, y, 14, 0, 11, 7, 128, 128);
//    }
//
//    @Override
//    public boolean doesGuiPauseGame() {
//        return false;
//    }
//
//    @Override
//    public void init() {
//        super.init();
//
//        this.guiLeft = Math.round((width / 2) / scale) - (xSize / 2); //(this.width - this.xSize) / 2;
//        this.guiTop = Math.round((height / 2) / scale) - (ySize / 2); //(this.height - this.ySize) / 2;
//    }
//
//    // TODO: Use it stop game
//    @Override
//    public void onGuiClosed() {
//    }
//}
//
//
