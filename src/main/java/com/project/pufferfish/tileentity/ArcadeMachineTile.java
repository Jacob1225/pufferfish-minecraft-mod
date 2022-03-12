package com.project.pufferfish.tileentity;

import com.project.pufferfish.item.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ArcadeMachineTile extends TileEntity {

    private final ItemStackHandler itemHandler = createHandler();
    private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);
    private static boolean hasToken;

    public ArcadeMachineTile(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public ArcadeMachineTile() {
        this(ModTileEntities.ARCADE_MACHINE_TILE.get());
    }

    // reads the data that was changed by the tileEntity
    @Override
    public void load (BlockState state, CompoundNBT nbt) {
        itemHandler.deserializeNBT(serializeNBT().getCompound("inv"));
        super.load(state, nbt);
    }

    // saves the data that was changed by the tileEntity
    @Override
    public CompoundNBT save (CompoundNBT compound) {
        compound.put("inv", itemHandler.serializeNBT());
        return super.save(compound);
    }

    private ItemStackHandler createHandler() {
        return new ItemStackHandler(1) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }

            // checks if the items placed in the arcade machine slot are the correct items
            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                switch (slot) {
                    case 0: return stack.getItem() == ModItems.GAME_TOKEN.get() ||
                            stack.getItem() == ModItems.PRIZE_TICKET.get();
                    default:
                        return false;
                }
            }

            // limits each slot to one item
            @Override
            public int getSlotLimit (int slot) {
                return 1;
            }

            // returns empty slot if an incorrect item is placed in the slot
            @Nonnull
            @Override
            public ItemStack insertItem (int slot, @Nonnull ItemStack stack, boolean simulate) {
                if (!isItemValid(slot, stack)) {
                    return stack;
                }

                return super.insertItem(slot, stack, simulate);
            }
        };
    }

    // restrict user to be able to interact from only one side
    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability (@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return handler.cast();
        }

        return super.getCapability(cap, side);
    }

    // checks if the player has inserted a token into the arcade machine slot
    public void tokenCheck() {
        boolean hasFocusInFirstSlot = this.itemHandler.getStackInSlot(0).getCount() > 0
                && this.itemHandler.getStackInSlot(0).getItem() == ModItems.GAME_TOKEN.get();

        if (hasFocusInFirstSlot) {
            hasToken = true;
        }
        else {
            hasToken = false;
        }
    }

    // helper method of tokenCheck() to return hasToken
    public static boolean getTokenCheck() {
        return hasToken;
    }

    // method for consumption of token and post-game reward when user plays game
    public void prizeCheck() {
        this.itemHandler.getStackInSlot(0).shrink(1);

        // TODO: change to boolean isArcadeMachineWinner() when implemented in ArcadeMachineContainer;
        boolean hasHighScoreForPrize = true;

        // leave prize in the slot if player played and got a high score
        if (hasHighScoreForPrize) {
            this.itemHandler.insertItem(0, new ItemStack(ModItems.PRIZE_TICKET.get()), false);
        }
        hasToken = false;
    }

}
