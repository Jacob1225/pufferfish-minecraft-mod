package com.project.pufferfish.tileentity;

import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.texture.ITickable;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.SoundEvent;

public class InvadersTile extends TileEntity implements ITickable {
    private int game = 0;

    // Sound Variables
    private SoundEvent soundEvent;
    private boolean isPlaying, shouldStart, shouldStop, loop;

    public InvadersTile(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
        game = 0;
    }

    public InvadersTile() {
        this(ModTileEntities.INVADERS_TILE.get());
    }

    @Override
    public void tick() {
    }

    @Override
    public void load (BlockState state, CompoundNBT nbt) {
        game = serializeNBT().getInt("Game");
        super.load(state, nbt);
    }

    @Override
    public CompoundNBT save (CompoundNBT compound) {
        compound.putInt("Game", game);
        return super.save(compound);
    }

}

