package com.Nuaah.NGemExtBoxMod.block.entity;

import com.Nuaah.NGemExtBoxMod.main.NGemExtBoxMod;
import com.Nuaah.NGemExtBoxMod.regi.NGemExtBoxModBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class NGemExtBoxModBlockEntityTypes {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, NGemExtBoxMod.MOD_ID);

    public static final RegistryObject<BlockEntityType<GemstoneWorkbenchEntity>> GEMSTONE_WORKBENCH = BLOCK_ENTITY_TYPES.register("gemstone_workbench",() -> set(GemstoneWorkbenchEntity::new, NGemExtBoxModBlocks.Blocks.GEMSTONE_WORKBENCH.get()));
    public static final RegistryObject<BlockEntityType<LinkerCoreEntity>> LINKER_CORE = BLOCK_ENTITY_TYPES.register("linker_core",() -> set(LinkerCoreEntity::new, NGemExtBoxModBlocks.Blocks.LINKER_CORE.get()));
    public static final RegistryObject<BlockEntityType<RoseQuartzLanternEntity>> ROSE_QUARTZ_LANTERN =
            BLOCK_ENTITY_TYPES.register("rose_quartz_lantern", () ->
                    BlockEntityType.Builder.of(
                            RoseQuartzLanternEntity::new,
                            NGemExtBoxModBlocks.Blocks.ROSE_QUARTZ_LANTERN.get(),
                            NGemExtBoxModBlocks.Blocks.BOTTLED_ROSE_QUARTZ.get() // ← 追加！
                    ).build(null)
            );

    private static <T extends BlockEntity> BlockEntityType<T> set (BlockEntityType.BlockEntitySupplier<T> entity, Block block){
        return BlockEntityType.Builder.of(entity,block).build(null);
    }
}
