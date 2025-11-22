package com.Nuaah.NGemExtBoxMod.regi;

import com.Nuaah.NGemExtBoxMod.main.NGemExtBoxMod;
import com.Nuaah.NGemExtBoxMod.regi.net.ClientSavedDataHolder;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = NGemExtBoxMod.MOD_ID,bus = Mod.EventBusSubscriber.Bus.FORGE,value = Dist.CLIENT)
public class ClientForgeEventBusSubscriber {

    @SubscribeEvent
    public static void onRenderOverlay(RenderGuiEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player == null) return;

        // 手に持ってるアイテムチェック
        ItemStack heldItem = player.getMainHandItem();
        if (heldItem.isEmpty()) return;
        if (!heldItem.is(NGemExtBoxModItems.LINQUARTZ.get())) return;

        int distance = (int)ClientSavedDataHolder.distance;
        int linkerPower = ClientSavedDataHolder.linkerPower;

        System.out.println(distance);

        // プレイヤー座標取得
        double x = player.getX();
        double y = player.getY();
        double z = player.getZ();

        // 表示用文字列
        Component distanceText;
        if (distance != -1){
            distanceText = Component.literal("▶ " + distance + "§7 linkerPower " + linkerPower);
        } else {
            distanceText = Component.literal("▶ NONE" + "§7 linkerPower " + linkerPower);
        }

        // 描画
        PoseStack poseStack = event.getGuiGraphics().pose();
        int width = mc.getWindow().getGuiScaledWidth();
        int height = mc.getWindow().getGuiScaledHeight();

        // 画面下に中央表示
        event.getGuiGraphics().drawCenteredString(
                mc.font,
                distanceText,
                width / 2,
                height - 55, // 下から20pxあたり
                0xFFFFFF
        );
    }
}
