package com.Nuaah.NGemExtBoxMod.regi.net;

import com.Nuaah.NGemExtBoxMod.main.NGemExtBoxMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetworkHandler {
    public static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel CHANNEL =
            NetworkRegistry.newSimpleChannel(
                    new ResourceLocation(NGemExtBoxMod.MOD_ID, "main"),
                    () -> PROTOCOL_VERSION,
                    PROTOCOL_VERSION::equals,
                    PROTOCOL_VERSION::equals
            );

    private static int packetId = 0;

    public static int nextID() { return packetId++; }

    public static void register() {
        CHANNEL.registerMessage(
                nextID(),
                LinkerCoreSyncPacket.class,
                LinkerCoreSyncPacket::encode,
                LinkerCoreSyncPacket::decode,
                LinkerCoreSyncPacket::handle
        );
    }
}

