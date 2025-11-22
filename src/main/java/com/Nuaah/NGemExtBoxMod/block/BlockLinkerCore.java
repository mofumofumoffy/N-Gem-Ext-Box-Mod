package com.Nuaah.NGemExtBoxMod.block;

import com.Nuaah.NGemExtBoxMod.block.entity.GemstoneWorkbenchEntity;
import com.Nuaah.NGemExtBoxMod.block.entity.LinkerCoreEntity;
import com.Nuaah.NGemExtBoxMod.regi.LinkerCoreRegistry;
import com.Nuaah.NGemExtBoxMod.regi.LinkerPowerData;
import com.Nuaah.NGemExtBoxMod.regi.NGemExtBoxModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class BlockLinkerCore extends BaseEntityBlock {
    public static final BooleanProperty TOP = BooleanProperty.create("top");
    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    public static final VoxelShape SHAPE = Block.box(2,0,2,14,30,14);
    public static final VoxelShape UP_SHAPE = Block.box(2,-16,2,14,14,14);

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return (state.getValue(TOP)) ? UP_SHAPE : SHAPE;
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState p_60578_, BlockGetter p_60579_, BlockPos p_60580_) {
        return SHAPE;
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return (state.getValue(TOP)) ? UP_SHAPE : SHAPE;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return (state.getValue(TOP)) ? RenderShape.INVISIBLE : RenderShape.MODEL;
    }

    public BlockLinkerCore() {
        super(Properties.of()
            .strength(4.5F,3.0F)
            .requiresCorrectToolForDrops()
            .noOcclusion()
            .lightLevel((state) -> state.hasProperty(LIT) && state.getValue(LIT) ? 5 : 0)
        );
        this.registerDefaultState(this.stateDefinition.any().setValue(LIT, false).setValue(TOP, false)); // デフォルトでは光ってない
    }

    // 置かれたときに上側も設置（setPlacedBy）
    @Override
    public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(world, pos, state, placer, stack);
        // 上空に邪魔なブロックがないか確認してから
        if (world.isEmptyBlock(pos.above())) {
            world.setBlock(pos.above(), state.setValue(TOP, true), 3);
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (state.getValue(TOP)) {
            // 上半分がクリックされた -> 下へ委譲
            BlockPos lower = pos.below();
            BlockState lowerState = world.getBlockState(lower);
            return lowerState.getBlock().use(lowerState, world, lower, player, hand, hit);
        }

        if (!world.isClientSide()) {
            BlockEntity entity = world.getBlockEntity(pos);
            if(entity instanceof LinkerCoreEntity) {

                int distance = ((LinkerCoreEntity) entity).getLinkerCoreDistance();

                LinkerPowerData data = LinkerPowerData.get(world);

                data.setNearLinkerCoreDistance(distance); //距離保存

                BlockPos linkerCorePos = ((LinkerCoreEntity) entity).findNearestCore();
                ((LinkerCoreEntity) entity).SaveLinkerCoreGem();

                if(player.isShiftKeyDown()){ //しゃがみテレポート
                    if (linkerCorePos != null && data.getLinkerPower() >= distance){

                        int X = linkerCorePos.getX();
                        int Y = linkerCorePos.getY();
                        int Z = linkerCorePos.getZ();

                        Direction clickedFace = hit.getDirection();

                        if(clickedFace == Direction.NORTH){
                            player.teleportTo(X + 0.5,Y,Z - 0.5);
                        }
                        else if(clickedFace == Direction.SOUTH){
                            player.teleportTo(X + 0.5,Y,Z + 1.5);
                        }
                        else if(clickedFace == Direction.EAST){
                            player.teleportTo(X + 1.5,Y,Z + 0.5);
                        }
                        else if(clickedFace == Direction.WEST){
                            player.teleportTo(X - 0.5,Y,Z + 0.5);
                        } else {
                            player.teleportTo(X,Y,Z);
                        }

                        data.addPower(-distance);
                        world.playSound(null,linkerCorePos, SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS,1.0f,1.0f);

                        if (world instanceof ServerLevel serverLevel) {
                            teleportParicle(serverLevel, player);
                        }
                    }
                } else { //GUI表示
                    NetworkHooks.openScreen(((ServerPlayer)player), (LinkerCoreEntity)entity, pos);
                }

            } else {
                throw new IllegalStateException("Our Container provider is missing!");
            }
        }

        return InteractionResult.sidedSuccess(world.isClientSide());
    }

    private static void teleportParicle(ServerLevel level, Player player) {
        double px = player.getX();
        double py = player.getY() + player.getEyeHeight() / 2; // 頭の高さ付近
        double pz = player.getZ();

        double offsetX = (Math.random() - 0.5) * 2; // -1 ~ 1
        double offsetY = Math.random();             // 0 ~ 1
        double offsetZ = (Math.random() - 0.5) * 2;

        level.sendParticles(ParticleTypes.FIREWORK,
            px + offsetX, py + offsetY, pz + offsetZ,
            10,
            0, 0.1, 0, // dx, dy, dz: 動き
                0.1);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIT,TOP);
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean moved) {
        // まず super を呼ぶ前にサーバ判定
        if (!world.isClientSide) {
            // 状態が変わらない（同じブロックの別状態に変化した）場合は上下処理しない
            if (state.getBlock() != newState.getBlock()) {
                if (state.getValue(TOP)) {
                    // 上側が壊された（あるいは置換された）場合 -> 下を消す（存在すれば）
                    BlockPos lower = pos.below();
                    BlockState lowerState = world.getBlockState(lower);
                    if (lowerState.getBlock() == this) {
                        // 下側は実際に同じブロックであることを確認してから削除
                        world.setBlock(lower, Blocks.AIR.defaultBlockState(), 3);
                    }
                } else {
                    // 下側が壊された（あるいは置換された）場合 -> ドロップ処理・上を消す
                    // ドロップ処理（BEがあるのは下のみ）
                    BlockEntity be = world.getBlockEntity(pos);
                    if (be instanceof LinkerCoreEntity) {
                        ((LinkerCoreEntity) be).drops();
                    }

                    // 上側も消す（存在すれば）
                    BlockPos up = pos.above();
                    if (world.getBlockState(up).getBlock() == this) {
                        world.setBlock(up, Blocks.AIR.defaultBlockState(), 3);
                    }

                    // データ削除（下が消えるときのみ）
                    LinkerPowerData data = LinkerPowerData.get(world);
                    data.removeCore(pos);
                }
            }
        }

        super.onRemove(state, world, pos, newState, moved);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        if (state.getValue(TOP)) return null;
        return new LinkerCoreEntity(pos,state);
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof LinkerCoreEntity linkerCore) {
            boolean hasGem = !linkerCore.getDisplayItem().isEmpty(); // ← entityの中身をチェック
            boolean isLit = state.getValue(LIT);

            if (hasGem != isLit) {
                world.setBlock(pos, state.setValue(LIT, hasGem), 3);
            }
        }
    }

    @Override
    public BlockState updateShape(BlockState state, Direction p_57504_, BlockState p_57505_, LevelAccessor p_57506_, BlockPos p_57507_, BlockPos p_57508_) {
        return !state.getValue(TOP) && p_57504_ == Direction.DOWN && !this.canSurvive(state, p_57506_, p_57507_) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, p_57504_, p_57505_, p_57506_, p_57507_, p_57508_);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader render, BlockPos pos) {
        return !state.getValue(TOP) && canSupportCenter(render, pos.below(), Direction.UP);
    }
}
