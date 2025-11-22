package com.Nuaah.NGemExtBoxMod.regi;

import com.Nuaah.NGemExtBoxMod.block.entity.NGemExtBoxModCapabilities;
import com.Nuaah.NGemExtBoxMod.main.NGemExtBoxMod;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = NGemExtBoxMod.MOD_ID,bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ServerForgeEventBusSubscriber {

    @SubscribeEvent
    public static void onAddReloadListeners(AddReloadListenerEvent event) {
        event.addListener(new NGemExtBoxModDataLoader());
    }
}
