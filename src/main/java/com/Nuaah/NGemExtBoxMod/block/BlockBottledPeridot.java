package com.Nuaah.NGemExtBoxMod.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Random;

public class BlockBottledPeridot extends Block {

    public static final VoxelShape SHAPE = Block.box(5,0,5,11,8,11);

    public BlockBottledPeridot() {
        super(Properties.of().strength(0.5f,0.5f)
                .lightLevel((state) -> 5));
    }

    @Override
    public BlockState updateShape(BlockState p_57503_, Direction p_57504_, BlockState p_57505_, LevelAccessor p_57506_, BlockPos p_57507_, BlockPos p_57508_) {
        return p_57504_ == Direction.DOWN && !this.canSurvive(p_57503_, p_57506_, p_57507_) ? Blocks.AIR.defaultBlockState() : super.updateShape(p_57503_, p_57504_, p_57505_, p_57506_, p_57507_, p_57508_);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader render, BlockPos pos) {
        return canSupportCenter(render, pos.below(), Direction.UP);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource source) {
        Random random = new Random();

        if (random.nextInt(0,20) != 0)return;

        double d0 = (double)pos.getX() + 0.5D + random.nextDouble(-0.5,0.5);
        double d1 = (double)pos.getY() + 0.7D + random.nextDouble(-0.3,0);
        double d2 = (double)pos.getZ() + 0.5D + random.nextDouble(-0.5,0.5);
        world.addParticle(ParticleTypes.END_ROD, d0, d1, d2, 0.0D, 0.0D, 0.0D);
    }
}
