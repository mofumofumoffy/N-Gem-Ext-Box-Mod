package com.Nuaah.NGemExtBoxMod.regi;

import com.Nuaah.NGemExtBoxMod.block.entity.NGemExtBoxModCapabilities;
import com.Nuaah.NGemExtBoxMod.main.NGemExtBoxMod;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = NGemExtBoxMod.MOD_ID,bus = Mod.EventBusSubscriber.Bus.MOD)
public class ServerEventBusSubscriber {
    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        NGemExtBoxModCapabilities.register(event);
    }
}
