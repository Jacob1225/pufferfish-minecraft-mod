package com.project.pufferfish.gui;

import com.project.pufferfish.utils.Reference;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;

import javax.annotation.Nullable;
import java.awt.*;
import java.io.IOException;

public class GameBoard extends GuiScreen {
    // Tick Variables
    public int tickCounter = 0;
    private boolean useTick = true;

    // Menu Variables
    public boolean inMenu = true;
    public int menuOption = 0;
    public int menu = 0, startMenu = 0;
    public boolean useInternalMenu = true;
    public boolean editVolume;
    private float volume = 1.0f;
    private int iVol = 100;

    // GUI Variables
    public int textureWidth = 256, textureHeight = 266;
    private int guiLeft, guiTop;
    private int xSize = 0, ySize = 0;
    private ResourceLocation gui;
    private GuiButton insertCoin;
    private int buttonX = 0, buttonY = 0;
    public int buttonWidth, buttonHeight = 20;
    private int[] offset = { 0, 0 };
    private float scale = 1;
    public int xScaled, yScaled;
    private static final int GUI_X = 234, GUI_Y = 284;

    // Cost Variables
    private int cost = 1;
    private boolean enoughCoins = true;
    public boolean canGetCoinBack = true;

    // Constructor Variable
//    private TileEntityArcade tileEntity;
    private EntityPlayer player;
    private World world;
    private BlockPos pos;

    // Arrow Texture
    private static final ResourceLocation arrows = new ResourceLocation(Reference.MODID + "/textures/gui/gui_arrows.png");
    private static final ResourceLocation generic = new ResourceLocation(Reference.MODID,"textures/gui/generic_gui.png");

    public GameBoard(World world, TileEntity tileEntity, @Nullable BlockPos pos, EntityPlayer player) {
        this.pos = pos;
        this.world = world;
        this.player = player;
        menu = startMenu;
        this.fontRenderer = mc.getMinecraft().fontRenderer;
        buttonWidth = this.fontRenderer.getStringWidth(I18n.format("button.pufferfish:insert.locale")) + 6;
        setGuiSize(GUI_X, GUI_Y, 0.8F);
        setTexture(generic, 512, 512);
    }


    /**
     * Set the width and height of the GUI Texture
     *
     * @param width  Width of Texture
     * @param height Height of Texture
     */
    public void setGuiSize (int width, int height) {
        xSize = width;
        ySize = height;
    }

    /**
     * Set the width and height of the GUI Texture along with Scale Factor
     *
     * @param width  Width of Texture
     * @param height Height of Texture
     * @param scale  Scale Factor
     */
    public void setGuiSize (int width, int height, float scale) {
        xSize = width;
        ySize = height;
        this.scale = scale;
    }

    /**
     * Set Scale Factor
     *
     * @param scale Scale Factor
     */
    public void setGuiScale (float scale) {
        this.scale = scale;
    }

    /**
     * Gets GUI Scale Factor
     *
     * @return scale
     */
    public float getGuiScale () {
        return scale;
    }

    /**
     * Disable Insert Menu to allow custom ones
     *
     * @param disable True = off, False = on
     */
    public void disableInternalMenu (boolean disable) {
        useInternalMenu = !disable;
    }

    /**
     * Set the 'Insert Coin' Button Position.
     * (0,0) is the top left corner of GUI
     *
     * @param x X Position
     * @param y Y Position
     */
    public void setButtonPos (int x, int y) {
        buttonX = x;
        buttonY = y;
    }

    /**
     * Offsets the text of the Insert Coin Menu.
     * This will do nothing if useBasicMenu is false
     *
     * @param x X Offset
     * @param y Y Offset
     */
    public void setOffset (int x, int y) {
        offset[0] = x;
        offset[1] = y;
    }

    /**
     * Sets ID of which menu is the Main Menu
     *
     * @param startMenu Main Menu ID
     */
    public void setStartMenu (int startMenu) {
        this.startMenu = startMenu;
    }

    /**
     * Returns Main Menu ID
     *
     * @return Main Menu ID
     */
    public int getStartMenu () {
        return startMenu;
    }

    /**
     * Set GUI Texture
     *
     * @param texture GUI Texture Location
     */
    public void setTexture (ResourceLocation texture) {
        gui = texture;
    }

    /**
     * Set GUI Texture with custom width and height
     *
     * @param texture GUI Texture Location
     * @param width   Custom Texture Width
     * @param height  Custom Texture Height
     */
    public void setTexture (ResourceLocation texture, int width, int height) {
        gui = texture;
        textureWidth = width;
        textureHeight = height;
    }

    /**
     * Set a custom size to the 'Insert Coin' Button
     *
     * @param width  Width of Button
     * @param height Height of Button
     */
    public void setButtonSize (int width, int height) {
        buttonWidth = width;
        buttonHeight = height;
    }


    /**
     * Returns World passed by GUIHandler
     *
     * @return world
     */
    public World getWorld () {
        return world;
    }

    /**
     * Returns position of block by GUIHandler.
     * Used officially to play sounds
     *
     * @return BlockPos
     */
    public BlockPos getPos () {
        return pos;
    }

    /**
     * Returns player entity passed by GUIHandler
     * Used officially to play sounds
     *
     * @return EntityPlayer
     */
    public EntityPlayer getPlayer () {
        return player;
    }

    @Override
    public void updateScreen () {
        if (useTick) tickCounter++;
    }

    @Override
    public void drawScreen (int mouseX, int mouseY, float partialTicks) {
        xScaled = Math.round((width / 2) / scale);
        yScaled = Math.round((height / 2) / scale);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.scale(scale, scale, scale);
        this.mc.getTextureManager().bindTexture(gui);
        this.drawModalRectWithCustomSizedTexture(xScaled - (xSize / 2), yScaled - (ySize / 2), 0, 0, xSize, ySize, textureWidth, textureHeight);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void drawHoveringText(java.util.List<java.lang.String> textLines, int x, int y) {

    }

    /**
     * Draw left arrow
     */
    public void drawLeftArrow (int x, int y) {
        drawLeftArrow(x, y, false);
    }

    /**
     * Draw right arrow
     */
    public void drawRightArrow (int x, int y) {
        drawRightArrow(x, y, false);
    }

    /**
     * Draw up arrow
     */
    public void drawUpArrow (int x, int y) {
        drawUpArrow(x, y, false);
    }

    /**
     * Draw down arrow
     */
    public void drawDownArrow (int x, int y) {
        drawDownArrow(x, y, false);
    }

    /**
     * Draw left arrow
     *
     * @param bind call texture manager to bind texture
     */
    public void drawLeftArrow (int x, int y, boolean bind) {
        if (bind) this.mc.getTextureManager().bindTexture(arrows);
        this.drawModalRectWithCustomSizedTexture(x, y, 7, 0, 7, 11, 128, 128);
    }

    /**
     * Draw right arrow
     *
     * @param bind call texture manager to bind texture
     */
    public void drawRightArrow (int x, int y, boolean bind) {
        if (bind) this.mc.getTextureManager().bindTexture(arrows);
        this.drawModalRectWithCustomSizedTexture(x, y, 0, 0, 7, 11, 128, 128);
    }

    /**
     * Draw up arrow
     *
     * @param bind call texture manager to bind texture
     */
    public void drawUpArrow (int x, int y, boolean bind) {
        if (bind) this.mc.getTextureManager().bindTexture(arrows);
        this.drawModalRectWithCustomSizedTexture(x, y, 25, 0, 11, 7, 128, 128);
    }

    /**
     * Draw down arrow
     *
     * @param bind call texture manager to bind texture
     */
    public void drawDownArrow (int x, int y, boolean bind) {
        if (bind) this.mc.getTextureManager().bindTexture(arrows);
        this.drawModalRectWithCustomSizedTexture(x, y, 14, 0, 11, 7, 128, 128);
    }

    @Override
    public boolean doesGuiPauseGame () {
        return false;
    }

    @Override
    public void initGui () {
        super.initGui();

        this.guiLeft = Math.round((width / 2) / scale) - (xSize / 2); //(this.width - this.xSize) / 2;
        this.guiTop = Math.round((height / 2) / scale) - (ySize / 2); //(this.height - this.ySize) / 2;
    }

    // TODO: Use it stop all sounds
    @Override
    public void onGuiClosed () {
    }


    /**
     * Get separated RGB values of Color
     *
     * @return separated RGB values in float array
     */
    public float[] colorToFloat (Color color) {
        float red = Math.round((color.getRed() / 255.0F) * 100.0F) / 100.0F;
        float green = Math.round((color.getGreen() / 255.0F) * 100.0F) / 100.0F;
        float blue = Math.round((color.getBlue() / 255.0F) * 100.0F) / 100.0F;

        return new float[] { red, green, blue };
    }

    /**
     * Set color of GUI modal with Color rather than RGB values
     */
    public void glColor (Color color) {
        float red = Math.round((color.getRed() / 255.0F) * 100.0F) / 100.0F;
        float green = Math.round((color.getGreen() / 255.0F) * 100.0F) / 100.0F;
        float blue = Math.round((color.getBlue() / 255.0F) * 100.0F) / 100.0F;

        GlStateManager.color(red, green, blue);
    }

    /**
     * Re-map a number from one range to another
     */
    @Deprecated
    public long map (long in, long inMin, long inMax, long outMin, long outMax) {
        return (in - inMin) * (outMax - outMin) / (inMax - inMin) + outMin;
    }

    public double rand (int min, int max) {
        return (Math.random() * (max - min)) + min;
    }


    /**
     * Checks if key is pressed.
     * Use isKeyDown(keyCode), if you want to check if key is held down
     *
     * @param typedChar Key Character
     * @param keyCode Key Code
     * @throws IOException
     */
    @Override
    protected void keyTyped (char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
    }

    /**
     * Checks if key is held down
     *
     * @param keyCode Key Code
     * @return true if pressed
     */
    public boolean isKeyDown (int keyCode) {
        return Keyboard.isKeyDown(keyCode);
    }


}
