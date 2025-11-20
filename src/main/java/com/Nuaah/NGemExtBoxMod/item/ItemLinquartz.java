package com.Nuaah.NGemExtBoxMod.item;

import com.Nuaah.NGemExtBoxMod.block.BlockLinkerCore;
import com.Nuaah.NGemExtBoxMod.regi.LinkerCoreRegistry;
import com.Nuaah.NGemExtBoxMod.regi.LinkerPowerData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.Tags;
import org.joml.Vector3f;

public class ItemLinquartz extends Item {
    private static BlockPos linkerCorePos;

    public ItemLinquartz() {
        super(new Properties()
                .stacksTo(1));
    }

    // 右クリックを押した時に「引き動作」を開始
    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        player.startUsingItem(hand); // ← 弓のように押しっぱなし開始

        linkerCorePos = LinkerCoreRegistry.getNearestCorePos(world, player.getOnPos());

        return InteractionResultHolder.consume(stack);
    }

    @Override
    public void onUseTick(Level world, LivingEntity entity, ItemStack stack, int remainingUseTicks) {
        super.onUseTick(world, entity, stack, remainingUseTicks);

        int usedTicks = stack.getUseDuration() - remainingUseTicks;

        if (world.isClientSide) {
            rotatingParticles(world,(Player) entity,usedTicks);

            if (usedTicks % 10 == 0) {
                towardsParticle(world, (Player) entity,linkerCorePos);
            }
        }
    }

    // 最大引き時間（弓と同じく 72000 tick = 60秒くらい）
    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    // 使用アニメーション（弓のように構える動き）
    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public void releaseUsing(ItemStack stack, Level world, LivingEntity entity, int timeLeft) {
        if (!(entity instanceof Player player)) return;
        int used = this.getUseDuration(stack) - timeLeft;

        if (used < 30)return;
        if (linkerCorePos == null)return;

        if (!world.isClientSide) {
            int distance = (int)Math.sqrt(player.getOnPos().distSqr(linkerCorePos));
            LinkerPowerData data = LinkerPowerData.get(world);
            System.out.println(data.getCoreData(linkerCorePos));

            if(data.getLinkerPower() >= distance){
                data.addPower(-distance);

                teleport(player,linkerCorePos);

                world.playSound(null,linkerCorePos, SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS,1.0f,1.0f);
                if (world instanceof ServerLevel serverLevel) {
                    teleportParicle(serverLevel, player);
                }
            }
        }
    }

    private static void teleport(Player player,BlockPos pos){
        if(pos == null) return;
        player.teleportTo(pos.getX(),pos.getY(),pos.getZ());
    }

    private static void teleportParicle(ServerLevel level, Player player) {
        double px = player.getX();
        double py = player.getY() + player.getEyeHeight() / 2; // 頭の高さ付近
        double pz = player.getZ();

        double offsetX = (Math.random() - 0.5) * 2; // -1 ~ 1
        double offsetY = Math.random();             // 0 ~ 1
        double offsetZ = (Math.random() - 0.5) * 2;

        level.sendParticles( ParticleTypes.ENCHANT,
            px + offsetX, py + offsetY, pz + offsetZ,
            40,
            1, -0.5, 1, // dx, dy, dz: 動き
            1);
    }

    public static void towardsParticle(Level level, Player player,BlockPos pos) {
        // パーティクル開始位置（プレイヤーの目の高さ）
        if(pos == null)return;

        Vec3 eyePos = player.getEyePosition();

        // ターゲット座標（ブロックの中心）
        Vec3 target = new Vec3(
                pos.getX() + 0.5,
                pos.getY() + 0.5,
                pos.getZ() + 0.5
        );

        // 方向ベクトル（正規化）
        Vec3 dir = target.subtract(eyePos).normalize();

        // パーティクル開始位置 → 目線位置 + 方向 * 少し距離（0.5 くらい）
        double offset = 1.5; // 好みで調整
        Vec3 start = eyePos.add(dir.scale(offset));

        // 飛ばす速度（好みで調整）
        double speed = 0.0;
        Vec3 velocity = dir.scale(speed);

        // パーティクル生成
        level.addParticle(
                ParticleTypes.END_ROD,
                start.x, start.y, start.z,
                velocity.x, velocity.y, velocity.z
        );
    }

    public static void rotatingParticles(Level level, Player player, int tick) {

        double radius = 2.0;         // プレイヤーからの距離
        double speed = 0.3;          // 回転速度
        double height = 0.5;         // パーティクルの高さ

        // 角度（ラジアン）
        double angle = tick * speed;

        // 計算
        double x = player.getX() + radius * Math.cos(angle);
        double y = player.getY() + height;
        double z = player.getZ() + radius * Math.sin(angle);

        // パーティクル生成（位置のみ）
        level.addParticle(
                new DustParticleOptions(
                    new Vector3f(0.1F, 0.0F, 0.1F),
                    1.0F // サイズ（好みで調整）
                ),
                x, y, z,
                0.0, 0.0, 0.0
        );
    }
}