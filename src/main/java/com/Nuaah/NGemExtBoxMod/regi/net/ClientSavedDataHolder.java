package com.Nuaah.NGemExtBoxMod.regi.net;

import net.minecraft.core.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ClientSavedDataHolder {
    public static int linkerPower = 0;
    public static double distance = 0;
    public static BlockPos linkerCorePos;
}