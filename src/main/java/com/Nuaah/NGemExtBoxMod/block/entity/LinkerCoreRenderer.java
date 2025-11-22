package com.Nuaah.NGemExtBoxMod.block.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class LinkerCoreRenderer implements BlockEntityRenderer<LinkerCoreEntity> {
    public LinkerCoreRenderer(BlockEntityRendererProvider.Context context) {
        // ここで特に初期化は不要
    }

    @Override
    public void render(LinkerCoreEntity entity, float particleTick, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        ItemStack item = entity.getDisplayItem();

        if (item.isEmpty()) return;

        poseStack.pushPose();

        // --- 位置 ---
        poseStack.translate(0.5, 2, 0.5);

        // --- 回転 ---
        if (entity.getLevel() != null) {
            // Minecraftのゲーム内時間に基づいて回転（毎tick更新）
            float rotation = (entity.getLevel().getGameTime() + particleTick) % 360;
            poseStack.mulPose(Axis.YP.rotationDegrees(rotation));
        }

        // --- サイズ ---
//        poseStack.scale(0.5f, 0.5f, 0.5f);

        // --- 描画 ---
        Minecraft.getInstance().getItemRenderer().renderStatic(
                item,
                ItemDisplayContext.GROUND,
                combinedLight,
                combinedOverlay,
                poseStack,
                buffer,
                entity.getLevel(),
                0
        );

        poseStack.popPose();
    }


}
