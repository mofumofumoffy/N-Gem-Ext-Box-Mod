package com.Nuaah.NGemExtBoxMod.regi;

import com.Nuaah.NGemExtBoxMod.block.entity.LinkerCoreEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LinkerCoreRegistry {
    private static final Set<LinkerCoreEntity> CORES = new HashSet<>();

    public static void register(LinkerCoreEntity core) {
        CORES.add(core);
    }

    public static void unregister(LinkerCoreEntity core) {
        CORES.remove(core);
    }

    //指定なし
    public static BlockPos getNearestCorePos(Level world, BlockPos currentPos) {
        if(world.isClientSide) return null;

        LinkerPowerData data = LinkerPowerData.get(world);
        double minDistance = Double.MAX_VALUE;
        BlockPos nearestPos = null;

        for (Map.Entry<BlockPos, CompoundTag> entry : data.getAll().entrySet()) {
            BlockPos pos = entry.getKey();

            // 自分自身をスキップ
            if (pos.equals(currentPos)) continue;
            CompoundTag coreTag = entry.getValue();
            ItemStack linkerGem = ItemStack.of(coreTag.getCompound("linker_gem"));

            if (linkerGem.isEmpty())continue;

            if (!coreTag.getString("linker_dimension").equals(world.dimension().location().toString())) continue;;

            // 距離を測る
            double distance = currentPos.distSqr(pos);
            if (distance < minDistance) {
                minDistance = distance;
                nearestPos = pos;
            }
        }

        return nearestPos; // nullの場合もある
    }

    //指定あり
    public static BlockPos getNearestCorePos(Level world, BlockPos currentPos, CompoundTag checkNBT) {
        LinkerPowerData data = LinkerPowerData.get(world);
        double minDistance = Double.MAX_VALUE;
        BlockPos nearestPos = null;

        System.out.println(data.getAll().entrySet() + "ALL LIST");

        for (Map.Entry<BlockPos, CompoundTag> entry : data.getAll().entrySet()) {
            BlockPos pos = entry.getKey();
            System.out.println(entry);
            System.out.println(pos + " POS");
            // 自分自身をスキップ
            if (pos.equals(currentPos)) continue;

            CompoundTag coreTag = entry.getValue();

            // 例えばItemStackが同じ宝石を持つかを確認（例）
            if (!isSameGem(coreTag, checkNBT)) continue;
            // 同じディメンションか？

            if (!coreTag.getString("linker_dimension").equals(world.dimension().location().toString())) continue;;

            // 距離を測る
            double distance = currentPos.distSqr(pos);
            if (distance < minDistance) {
                minDistance = distance;
                nearestPos = pos;
            }
        }

        return nearestPos; // nullの場合もある
    }

    // NBT内のアイテムデータが同じか判定（例）
    private static boolean isSameGem(CompoundTag a, CompoundTag b) {
        System.out.println(!a.contains("linker_gem") + " WHAT A");
        System.out.println(!b.contains("linker_gem") + " WHAT B");
        if(a == null || b == null) return false;

        // ItemStack化
        ItemStack itemA = ItemStack.of(a.getCompound("linker_gem"));
        ItemStack itemB = ItemStack.of(b.getCompound("linker_gem"));

        if(itemA == null || itemB == null) return false;

        System.out.println(itemA);
        System.out.println(itemB);

        // 種類のみ比較（NBTタグは無視）
        return ItemStack.isSameItem(itemA, itemB);
    }

}
