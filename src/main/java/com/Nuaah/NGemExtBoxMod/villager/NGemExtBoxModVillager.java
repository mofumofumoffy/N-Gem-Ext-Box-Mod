package com.Nuaah.NGemExtBoxMod.villager;

import com.Nuaah.NGemExtBoxMod.main.NGemExtBoxMod;
import com.Nuaah.NGemExtBoxMod.regi.NGemExtBoxModBlocks;
import com.google.common.collect.ImmutableSet;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class NGemExtBoxModVillager {
    public static final DeferredRegister<PoiType> POI_TYPE =
            DeferredRegister.create(ForgeRegistries.POI_TYPES, NGemExtBoxMod.MOD_ID);
    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS =
            DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS,NGemExtBoxMod.MOD_ID);

    public static final RegistryObject<PoiType> GEMSTONE_POI = POI_TYPE.register("gemstone_pos"
            , () -> new PoiType(ImmutableSet.copyOf(NGemExtBoxModBlocks.Blocks.GEMSTONE_WORKBENCH.get().getStateDefinition().getPossibleStates())
            ,1,1));

    public static final RegistryObject<VillagerProfession> JEWELER =
            VILLAGER_PROFESSIONS.register("jeweler",() -> new VillagerProfession("jeweler"
            , holder -> holder.get() == GEMSTONE_POI.get(),holder -> holder.get() == GEMSTONE_POI.get()
            ,ImmutableSet.of(),ImmutableSet.of(), SoundEvents.VILLAGER_WORK_ARMORER));

    public static void register(IEventBus eventBus){
        POI_TYPE.register(eventBus);
        VILLAGER_PROFESSIONS.register(eventBus);
    }
}
