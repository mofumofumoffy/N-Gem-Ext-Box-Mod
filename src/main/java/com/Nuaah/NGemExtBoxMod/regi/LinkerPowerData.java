package com.Nuaah.NGemExtBoxMod.regi;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.HashMap;
import java.util.Map;

public class LinkerPowerData extends SavedData {
    private int linkerPower = 0;
    private int nearLinkerCoreDistance = 0;

    public static final String ID = "linker_power_data";

    // 各Coreの位置と内容を保持
    private final Map<BlockPos, CompoundTag> coreData = new HashMap<>();

    public static LinkerPowerData get(Level level) {
        if (!(level instanceof ServerLevel serverLevel)) {
            throw new IllegalStateException("LinkerPowerData can only be accessed on the server side!");
        }

        ServerLevel overworld = level.getServer().overworld();

        return overworld.getDataStorage().computeIfAbsent(
                LinkerPowerData::load,
                LinkerPowerData::new,
                ID
        );
    }

    public LinkerPowerData() {}

    public void setCoreData(BlockPos pos, CompoundTag tag) {
        System.out.println("SAVED");
        coreData.put(pos, tag == null ? null : tag.copy());
        setDirty();
    }

    public CompoundTag getCoreData(BlockPos pos) {
        CompoundTag t = coreData.get(pos);
        return t == null ? null : t.copy();
    }

    public void removeCore(BlockPos pos) {
        System.out.println("REMOVED");
        coreData.remove(pos);
        setDirty();
    }

    public Map<BlockPos, CompoundTag> getAll() {
        return coreData;
    }

    public static LinkerPowerData load(CompoundTag nbt) {
        LinkerPowerData data = new LinkerPowerData();
        data.linkerPower = nbt.getInt("LinkerPower");
        data.nearLinkerCoreDistance = nbt.getInt("LinkerCoreDistance");

        ListTag list = nbt.getList("linker_gem_list", 10); //データ保存
        for (int i = 0; i < list.size(); i++) {
            CompoundTag tag = list.getCompound(i);
            BlockPos pos = BlockPos.of(tag.getLong("pos"));
            CompoundTag coreTag = tag.getCompound("data");
            data.coreData.put(pos, coreTag);
        }
        return data;
    }

    @Override
    public CompoundTag save(CompoundTag nbt) {
//        System.out.println("[LinkerPowerData.save] coreData.size = " + coreData.size());
        nbt.putInt("LinkerPower", linkerPower);
        nbt.putInt("LinkerCoreDistance", nearLinkerCoreDistance);

        ListTag list = new ListTag(); //コア情報保存
        for (Map.Entry<BlockPos, CompoundTag> entry : coreData.entrySet()) {
            System.out.println("[LinkerPowerData.save] adding pos=" + entry.getKey() + " tag=" + entry.getValue());
            CompoundTag tag = new CompoundTag();
            tag.putLong("pos", entry.getKey().asLong());
            tag.put("data", entry.getValue()); // dataの中にItemStack情報も含まれている
            list.add(tag);
        }
//        System.out.println("[LinkerPowerData.save] list.size = " + list.size());

        nbt.put("linker_gem_list", list);
//        System.out.println("[LinkerPowerData.save] final nbt = " + nbt);

        return nbt;
    }

    public int getLinkerPower() {
        return linkerPower;
    }

    public void setNearLinkerCoreDistance(int distance){
        nearLinkerCoreDistance = distance;
    }

    public int getNearLinkerCoreDistance(){
        return nearLinkerCoreDistance;
    }

    public void addPower(int amount) {
        linkerPower += amount;
        setDirty(); // ← 保存マーク
    }
}
