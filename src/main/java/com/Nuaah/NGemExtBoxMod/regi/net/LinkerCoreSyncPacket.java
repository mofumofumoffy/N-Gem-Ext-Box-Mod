package com.Nuaah.NGemExtBoxMod.regi.net;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record LinkerCoreSyncPacket(int linkerPower, double distance, BlockPos linkerCorePos) {
    public static void encode(LinkerCoreSyncPacket pkt, FriendlyByteBuf buf) {
        buf.writeInt(pkt.linkerPower);
        buf.writeDouble(pkt.distance);
        buf.writeBlockPos(pkt.linkerCorePos);
    }

    // パケット読み込み
    public static LinkerCoreSyncPacket decode(FriendlyByteBuf buf) {
        return new LinkerCoreSyncPacket(buf.readInt(), buf.readDouble(),buf.readBlockPos());
    }

    // パケット受信時処理（クライアント側）
    public static void handle(LinkerCoreSyncPacket pkt, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            // クライアント側に保存
            ClientSavedDataHolder.linkerPower = pkt.linkerPower();
            ClientSavedDataHolder.distance = pkt.distance();
            ClientSavedDataHolder.linkerCorePos = pkt.linkerCorePos();
        });
        ctx.get().setPacketHandled(true);
    }
}
