package com.Nuaah.NGemExtBoxMod.block.gui.container;

import com.Nuaah.NGemExtBoxMod.block.entity.GemstoneWorkbenchEntity;
import com.Nuaah.NGemExtBoxMod.block.entity.LinkerCoreEntity;
import com.Nuaah.NGemExtBoxMod.main.NGemExtBoxMod;
import net.minecraft.core.BlockPos;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class NGemExtBoxModContainerTypes {

    public static final DeferredRegister<MenuType<?>> MENU_TYPES =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, NGemExtBoxMod.MOD_ID);

    public static final RegistryObject<MenuType<GemstoneWorkbenchMenu>> GEMSTONE_WORKBENCH =
            MENU_TYPES.register("gemstone_workbench",
                    () -> IForgeMenuType.create((windowId, inv, data) -> {
                        BlockPos pos = data.readBlockPos();
                        Level level = inv.player.level();
                        BlockEntity be = level.getBlockEntity(pos);
                        if (be instanceof GemstoneWorkbenchEntity entity) {
                            return new GemstoneWorkbenchMenu(windowId, inv, entity);
                        }
                        return null;
                    })
            );

    public static final RegistryObject<MenuType<LinkerCoreMenu>> LINKER_CORE =
            MENU_TYPES.register("linker_core",() -> IForgeMenuType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                Level level = inv.player.level();
                BlockEntity be = level.getBlockEntity(pos);
                if (be instanceof LinkerCoreEntity entity) {
                    return new LinkerCoreMenu(windowId, inv, entity,new SimpleContainerData(2));
                }
                return null;
            }));
}