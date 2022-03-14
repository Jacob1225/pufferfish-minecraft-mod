package com.project.pufferfish.block.custom;

import com.project.pufferfish.container.ArcadeMachineContainer;
import com.project.pufferfish.screen.InvadersScreen;
import com.project.pufferfish.tileentity.ArcadeMachineTile;
import com.project.pufferfish.tileentity.ModTileEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.Random;

import java.util.Random;

import static com.project.pufferfish.tileentity.ArcadeMachineTile.getTokenCheck;

public class ArcadeMachineBlock extends Block {

    // blockstate property
    public static final BooleanProperty PLAYED = BooleanProperty.create("played");
    public static final DirectionProperty FACING = DirectionProperty.create("facing", Direction.Plane.HORIZONTAL);

    // constructor
    public ArcadeMachineBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(PLAYED, false));
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH));
    }

    // create blockstate
    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
        builder.add(PLAYED, FACING);
        super.createBlockStateDefinition(builder);

    }

    // set blockstates when user places arcade machine down
    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return defaultBlockState().setValue(PLAYED, false)
                .setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    // right click interaction
    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos,
                                             PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        // check if we are on the server
        if(!worldIn.isClientSide()) {
            TileEntity tileEntity = worldIn.getBlockEntity(pos);

            if (tileEntity instanceof ArcadeMachineTile) {
                // if token was placed in arcade machine then open invaders GUI on right click
                if (getTokenCheck()) {
                	
                    // opens Invaders screen
                    openGui(player, worldIn, pos);


                    // after playing game, check for prize and set hasToken back to false
                    ((ArcadeMachineTile) tileEntity).prizeCheck();
                    worldIn.setBlock(pos, state.setValue(PLAYED, false), 3);

                }
                // else, display arcade machine inventory page
                else {
                    INamedContainerProvider containerProvider = createContainerProvider(worldIn, pos);
                    NetworkHooks.openGui(((ServerPlayerEntity)player), containerProvider, tileEntity.getBlockPos());
                }
            } else {
                throw new IllegalStateException("Our Container provider is missing!");
            }

        }
        return ActionResultType.SUCCESS;
    }

    // regularly check to update blockstate to play state if player inserted token
    @OnlyIn(Dist.CLIENT)
    public void animateTick (BlockState state, World worldIn, BlockPos pos, Random rand) {
        // check if a game token was placed into the arcade machine slot
        if (!state.getValue(PLAYED)) {
            TileEntity tileEntity = worldIn.getBlockEntity(pos);
            ((ArcadeMachineTile) tileEntity).tokenCheck();
            // if token was placed then change arcade machine blockstate into a playable state
            if (getTokenCheck()) {
                worldIn.setBlock(pos, state.setValue(PLAYED, true), 3);
            }
        }
    }


    // open Invaders GUI on client-side
    @OnlyIn(Dist.CLIENT)
    protected void openGui(PlayerEntity player, World worldIn, BlockPos pos) {
        final TileEntity tileEntity = worldIn.getBlockEntity(pos);
        if (tileEntity instanceof ArcadeMachineTile) {
            Minecraft.getInstance().setScreen(new InvadersScreen(false, worldIn, tileEntity, pos, player));
        }
    }

    private INamedContainerProvider createContainerProvider(World worldIn, BlockPos pos) {
        return new INamedContainerProvider() {
            @Override
            public ITextComponent getDisplayName() {
                return new TranslationTextComponent("screen.pufferfish.arcade_machine");
            }

            @Nullable
            @Override
            public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
                return new ArcadeMachineContainer(i, worldIn, pos, playerInventory, playerEntity);
            }
        };
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return ModTileEntities.ARCADE_MACHINE_TILE.get().create();
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

}
