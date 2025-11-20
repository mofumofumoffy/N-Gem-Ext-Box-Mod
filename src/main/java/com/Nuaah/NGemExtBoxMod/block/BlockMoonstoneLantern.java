package com.Nuaah.NGemExtBoxMod.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class BlockMoonstoneLantern extends Block {
    public static final BooleanProperty HANGING = BlockStateProperties.HANGING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public static final VoxelShape SHAPE = Block.box(5,0,5,11,9,11);
    public static final VoxelShape HANGING_SHAPE = Block.box(5,1,5,11,10,11);

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());

        for(Direction direction : context.getNearestLookingDirections()) {
            if (direction.getAxis() == Direction.Axis.Y) {
                BlockState blockstate = this.defaultBlockState().setValue(HANGING, Boolean.valueOf(direction == Direction.UP));
                if (blockstate.canSurvive(context.getLevel(), context.getClickedPos())) {
                    return blockstate.setValue(WATERLOGGED, Boolean.valueOf(fluidstate.getType() == Fluids.WATER));
                }
            }
        }

        return null;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HANGING, WATERLOGGED);
    }

    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        return state.getValue(HANGING) ? HANGING_SHAPE : SHAPE;
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    public BlockMoonstoneLantern() {
        super(Properties.of()
            .strength(3.5F,3.5F)
            .requiresCorrectToolForDrops()
            .noOcclusion()
            .lightLevel((state) -> 15)
        );

        this.registerDefaultState(this.stateDefinition.any().setValue(HANGING, Boolean.valueOf(false)).setValue(WATERLOGGED, Boolean.valueOf(false)));
    }

    public BlockState updateShape(BlockState state, Direction dir, BlockState p_153485_, LevelAccessor p_153486_, BlockPos p_153487_, BlockPos p_153488_) {
        if (state.getValue(WATERLOGGED)) {
            p_153486_.scheduleTick(p_153487_, Fluids.WATER, Fluids.WATER.getTickDelay(p_153486_));
        }

        return getConnectedDirection(state).getOpposite() == dir && !state.canSurvive(p_153486_, p_153487_) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, dir, p_153485_, p_153486_, p_153487_, p_153488_);
    }

    private static Direction getConnectedDirection(BlockState state) {
        return state.getValue(HANGING) ? Direction.DOWN : Direction.UP;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader render, BlockPos pos) {
        Direction direction = getConnectedDirection(state).getOpposite();
        return Block.canSupportCenter(render, pos.relative(direction), direction.getOpposite());
    }

    @Override
    public boolean isPathfindable(BlockState p_153469_, BlockGetter p_153470_, BlockPos p_153471_, PathComputationType p_153472_) {
        return false;
    }
}
