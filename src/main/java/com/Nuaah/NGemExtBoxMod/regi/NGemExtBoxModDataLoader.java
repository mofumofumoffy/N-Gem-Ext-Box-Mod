package com.Nuaah.NGemExtBoxMod.regi;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.HashMap;
import java.util.Map;

public class NGemExtBoxModDataLoader extends SimpleJsonResourceReloadListener {

    public static final Map<ResourceLocation, JsonObject> DATA = new HashMap<>();

    public NGemExtBoxModDataLoader() {
        super(new Gson(), "linker_core");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> list, ResourceManager manager, ProfilerFiller profiler) {
        DATA.clear();

        list.forEach((id, json) -> {
            DATA.put(id, json.getAsJsonObject());
        });

        System.out.println("Loaded script data: " + DATA.keySet());
    }
}
