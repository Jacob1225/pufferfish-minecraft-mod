package com.project.pufferfish.utils;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class TileEntityArcade extends TileEntity implements ITickable {
    private int game = 0;

    // Sound Variables
    private SoundEvent soundEvent;
    private boolean isPlaying, shouldStart, shouldStop, loop;
    private float volume = 1f;

    // Energy (Default 10 RF/tick)
    private EnergyStorage storage;

    // Multiplayer
    private ArrayList<String> playerList;

    public TileEntityArcade () {
        game = 0;
        storage = new EnergyStorage(5000, 1000, 0);
        playerList = new ArrayList<>();
    }

//    public TileEntityArcade (int game) {
//        this.game = game;
//        storage = new EnergyStorage(5000, 1000, 0);
//        playerList = new ArrayList<>();
//    }

//    public int getGameID () {
//        return game;
//    }
//
//    public void setGameID (int id) {
//        game = id;
//    }


    @Override
    public void update () {
    }

    private SoundEvent setSound (ResourceLocation resource) {
        return SoundEvent.REGISTRY.getObject(resource);
    }

    public void playSound (ResourceLocation resource, float volume, boolean looping) {
        loop = looping;
        shouldStart = true;
        setVolume(volume);
        this.soundEvent = setSound(resource);
    }

    public void setVolume (float volume) {
        this.volume = volume;
    }

    @Override
    public NBTTagCompound writeToNBT (NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("Game", game);
        compound.setInteger("Energy", storage.getEnergyStored());
        return compound;
    }

    @Override
    public void readFromNBT (NBTTagCompound compound) {
        super.readFromNBT(compound);
        game = compound.getInteger("Game");
        storage.receiveEnergy(compound.getInteger("Energy"), false);
    }

    @Override
    public NBTTagCompound getUpdateTag () {
        NBTTagCompound tag = new NBTTagCompound();
        writeToNBT(tag);
        return tag;
    }

    @Override
    public void handleUpdateTag (NBTTagCompound tag) {
        readFromNBT(tag);
    }

    @Override
    public void onDataPacket (NetworkManager net, SPacketUpdateTileEntity packet) {
        NBTTagCompound tag = packet.getNbtCompound();
        handleUpdateTag(tag);
    }

    @Override
    @Nullable
    public SPacketUpdateTileEntity getUpdatePacket () {
        NBTTagCompound tag = getUpdateTag();
        final int meta = 0;

        return new SPacketUpdateTileEntity(pos, meta, tag);
    }

    @Override
    public boolean shouldRefresh (World world, BlockPos pos, IBlockState old, IBlockState newState) {
        if (world.isAirBlock(pos)) return true;
        return false;
    }

    // Capability System
    @Override
    public <T> T getCapability (Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY) return (T)storage;
        return super.getCapability(capability, facing);
    }

    @Override
    public boolean hasCapability (Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY) return true;
        return super.hasCapability(capability, facing);
    }
}

