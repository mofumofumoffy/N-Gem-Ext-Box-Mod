package com.Nuaah.NGemExtBoxMod.regi;

import com.Nuaah.NGemExtBoxMod.block.*;
import com.Nuaah.NGemExtBoxMod.main.NGemExtBoxMod;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class NGemExtBoxModBlocks {
    public static class Blocks{
        public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, NGemExtBoxMod.MOD_ID);
        public static final RegistryObject<Block> RUBY_ORE = BLOCKS.register("ruby_ore", BlockRubyOre::new);
        public static final RegistryObject<Block> AQUAMARINE_ORE = BLOCKS.register("aquamarine_ore", BlockAquamarineOre::new);
        public static final RegistryObject<Block> GARNET_ORE = BLOCKS.register("garnet_ore", BlockGarnetOre::new);
        public static final RegistryObject<Block> MOONSTONE_ORE  = BLOCKS.register("moonstone_ore", BlockMoonstoneOre::new);
        public static final RegistryObject<Block> PERIDOT_ORE = BLOCKS.register("peridot_ore", BlockPeridotOre::new);
        public static final RegistryObject<Block> SAPPHIRE_ORE = BLOCKS.register("sapphire_ore", BlockSapphireOre::new);
        public static final RegistryObject<Block> TOPAZ_ORE = BLOCKS.register("topaz_ore", BlockTopazOre::new);
        public static final RegistryObject<Block> TOURMALINE_ORE = BLOCKS.register("tourmaline_ore", BlockTourmalineOre::new);
        public static final RegistryObject<Block> NETHER_ROSE_QUARTZ_ORE = BLOCKS.register("nether_rose_quartz_ore", BlockTourmalineOre::new);

        public static final RegistryObject<Block> DEEPSLATE_RUBY_ORE = BLOCKS.register("deepslate_ruby_ore", BlockDeepslateRubyOre::new);
        public static final RegistryObject<Block> DEEPSLATE_AQUAMARINE_ORE = BLOCKS.register("deepslate_aquamarine_ore", BlockDeepslateRubyOre::new);
        public static final RegistryObject<Block> DEEPSLATE_GARNET_ORE = BLOCKS.register("deepslate_garnet_ore", BlockDeepslateRubyOre::new);
        public static final RegistryObject<Block> DEEPSLATE_MOONSTONE_ORE = BLOCKS.register("deepslate_moonstone_ore", BlockDeepslateRubyOre::new);
        public static final RegistryObject<Block> DEEPSLATE_PERIDOT_ORE = BLOCKS.register("deepslate_peridot_ore", BlockDeepslateRubyOre::new);
        public static final RegistryObject<Block> DEEPSLATE_SAPPHIRE_ORE = BLOCKS.register("deepslate_sapphire_ore", BlockDeepslateRubyOre::new);
        public static final RegistryObject<Block> DEEPSLATE_TOPAZ_ORE = BLOCKS.register("deepslate_topaz_ore", BlockDeepslateRubyOre::new);
        public static final RegistryObject<Block> DEEPSLATE_TOURMALINE_ORE = BLOCKS.register("deepslate_tourmaline_ore", BlockDeepslateRubyOre::new);

        public static final RegistryObject<Block> RUBY_LANTERN = BLOCKS.register("ruby_lantern", BlockRubyLantern::new);
        public static final RegistryObject<Block> AQUAMARINE_LANTERN = BLOCKS.register("aquamarine_lantern", BlockAquamarineLantern::new);
        public static final RegistryObject<Block> GARNET_LANTERN = BLOCKS.register("garnet_lantern", BlockGarnetLantern::new);
        public static final RegistryObject<Block> MOONSTONE_LANTERN = BLOCKS.register("moonstone_lantern", BlockMoonstoneLantern::new);
        public static final RegistryObject<Block> PERIDOT_LANTERN = BLOCKS.register("peridot_lantern", BlockPeridotLantern::new);
        public static final RegistryObject<Block> SAPPHIRE_LANTERN = BLOCKS.register("sapphire_lantern", BlockSapphireLantern::new);
        public static final RegistryObject<Block> TOPAZ_LANTERN = BLOCKS.register("topaz_lantern", BlockTopazLantern::new);
        public static final RegistryObject<Block> TOURMALINE_LANTERN = BLOCKS.register("tourmaline_lantern", BlockTourmalineLantern::new);
        public static final RegistryObject<Block> AMETHYST_LANTERN = BLOCKS.register("amethyst_lantern", BlockAmethystLantern::new);
        public static final RegistryObject<Block> ROSE_QUARTZ_LANTERN = BLOCKS.register("rose_quartz_lantern", BlockRoseQuartzLantern::new);

        public static final RegistryObject<Block> BOTTLED_RUBY = BLOCKS.register("bottled_ruby", BlockBottledRuby::new);
        public static final RegistryObject<Block> BOTTLED_AQUAMARINE = BLOCKS.register("bottled_aquamarine", BlockBottledAquamarine::new);
        public static final RegistryObject<Block> BOTTLED_GARNET = BLOCKS.register("bottled_garnet", BlockBottledGarnet::new);
        public static final RegistryObject<Block> BOTTLED_MOONSTONE = BLOCKS.register("bottled_moonstone", BlockBottledMoonstone::new);
        public static final RegistryObject<Block> BOTTLED_PERIDOT = BLOCKS.register("bottled_peridot", BlockBottledPeridot::new);
        public static final RegistryObject<Block> BOTTLED_SAPPHIRE = BLOCKS.register("bottled_sapphire", BlockBottledSapphire::new);
        public static final RegistryObject<Block> BOTTLED_TOPAZ = BLOCKS.register("bottled_topaz", BlockBottledTopaz::new);
        public static final RegistryObject<Block> BOTTLED_TOURMALINE = BLOCKS.register("bottled_tourmaline", BlockBottledTourmaline::new);
        public static final RegistryObject<Block> BOTTLED_AMETHYST = BLOCKS.register("bottled_amethyst", BlockBottledTourmaline::new);
        public static final RegistryObject<Block> BOTTLED_ROSE_QUARTZ = BLOCKS.register("bottled_rose_quartz", BlockBottledRoseQuartz::new);

        public static final RegistryObject<Block> GEMSTONE_WORKBENCH = BLOCKS.register("gemstone_workbench", BlockGemstoneWorkbench::new);
        public static final RegistryObject<Block> GEODE = BLOCKS.register("geode", BlockGeode::new);
        public static final RegistryObject<Block> DEEPSLATE_GEODE = BLOCKS.register("deepslate_geode", BlockDeepslateGeode::new);
        public static final RegistryObject<Block> LINKER_CORE = BLOCKS.register("linker_core", BlockLinkerCore::new);
    }

    public static class BlockItems{
        public static final DeferredRegister<Item> BLOCK_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, NGemExtBoxMod.MOD_ID);
        public static final RegistryObject<Item> RUBY_ORE = BLOCK_ITEMS.register("ruby_ore",
                () -> new BlockItem(Blocks.RUBY_ORE.get(),new Item.Properties()));
        public static final RegistryObject<Item> AQUAMARINE_ORE = BLOCK_ITEMS.register("aquamarine_ore",
                () -> new BlockItem(Blocks.AQUAMARINE_ORE.get(),new Item.Properties()));
        public static final RegistryObject<Item> GARNET_ORE = BLOCK_ITEMS.register("garnet_ore",
                () -> new BlockItem(Blocks.GARNET_ORE.get(),new Item.Properties()));
        public static final RegistryObject<Item> MOONSTONE_ORE = BLOCK_ITEMS.register("moonstone_ore",
                () -> new BlockItem(Blocks.MOONSTONE_ORE .get(),new Item.Properties()));
        public static final RegistryObject<Item> PERIDOT_ORE = BLOCK_ITEMS.register("peridot_ore",
                () -> new BlockItem(Blocks.PERIDOT_ORE.get(),new Item.Properties()));
        public static final RegistryObject<Item> SAPPHIRE_ORE = BLOCK_ITEMS.register("sapphire_ore",
                () -> new BlockItem(Blocks.SAPPHIRE_ORE.get(),new Item.Properties()));
        public static final RegistryObject<Item> TOPAZ_ORE = BLOCK_ITEMS.register("topaz_ore",
                () -> new BlockItem(Blocks.TOPAZ_ORE.get(),new Item.Properties()));
        public static final RegistryObject<Item> TOURMALINE_ORE = BLOCK_ITEMS.register("tourmaline_ore",
                () -> new BlockItem(Blocks.TOURMALINE_ORE.get(),new Item.Properties()));
        public static final RegistryObject<Item> NETHER_ROSE_QUARTZ_ORE = BLOCK_ITEMS.register("nether_rose_quartz_ore",
                () -> new BlockItem(Blocks.NETHER_ROSE_QUARTZ_ORE.get(),new Item.Properties()));

        public static final RegistryObject<Item> DEEPSLATE_RUBY_ORE = BLOCK_ITEMS.register("deepslate_ruby_ore",
                () -> new BlockItem(Blocks.DEEPSLATE_RUBY_ORE.get(),new Item.Properties()));
        public static final RegistryObject<Item> DEEPSLATE_AQUAMARINE_ORE = BLOCK_ITEMS.register("deepslate_aquamarine_ore",
                () -> new BlockItem(Blocks.DEEPSLATE_AQUAMARINE_ORE.get(),new Item.Properties()));
        public static final RegistryObject<Item> DEEPSLATE_GARNET_ORE = BLOCK_ITEMS.register("deepslate_garnet_ore",
                () -> new BlockItem(Blocks.DEEPSLATE_GARNET_ORE.get(),new Item.Properties()));
        public static final RegistryObject<Item> DEEPSLATE_MOONSTONE_ORE = BLOCK_ITEMS.register("deepslate_moonstone_ore",
                () -> new BlockItem(Blocks.DEEPSLATE_MOONSTONE_ORE.get(),new Item.Properties()));
        public static final RegistryObject<Item> DEEPSLATE_PERIDOT_ORE = BLOCK_ITEMS.register("deepslate_peridot_ore",
                () -> new BlockItem(Blocks.DEEPSLATE_PERIDOT_ORE.get(),new Item.Properties()));
        public static final RegistryObject<Item> DEEPSLATE_SAPPHIRE_ORE = BLOCK_ITEMS.register("deepslate_sapphire_ore",
                () -> new BlockItem(Blocks.DEEPSLATE_SAPPHIRE_ORE.get(),new Item.Properties()));
        public static final RegistryObject<Item> DEEPSLATE_TOPAZ_ORE = BLOCK_ITEMS.register("deepslate_topaz_ore",
                () -> new BlockItem(Blocks.DEEPSLATE_TOPAZ_ORE.get(),new Item.Properties()));
        public static final RegistryObject<Item> DEEPSLATE_TOURMALINE_ORE = BLOCK_ITEMS.register("deepslate_tourmaline_ore",
                () -> new BlockItem(Blocks.DEEPSLATE_TOURMALINE_ORE.get(),new Item.Properties()));


        public static final RegistryObject<Item> RUBY_LANTERN = BLOCK_ITEMS.register("ruby_lantern",
                () -> new BlockItem(Blocks.RUBY_LANTERN.get(),new Item.Properties()));
        public static final RegistryObject<Item> AQUAMARINE_LANTERN = BLOCK_ITEMS.register("aquamarine_lantern",
                () -> new BlockItem(Blocks.AQUAMARINE_LANTERN.get(),new Item.Properties()));
        public static final RegistryObject<Item> GARNET_LANTERN = BLOCK_ITEMS.register("garnet_lantern",
                () -> new BlockItem(Blocks.GARNET_LANTERN.get(),new Item.Properties()));
        public static final RegistryObject<Item> MOONSTONE_LANTERN = BLOCK_ITEMS.register("moonstone_lantern",
                () -> new BlockItem(Blocks.MOONSTONE_LANTERN.get(),new Item.Properties()));
        public static final RegistryObject<Item> PERIDOT_LANTERN = BLOCK_ITEMS.register("peridot_lantern",
                () -> new BlockItem(Blocks.PERIDOT_LANTERN.get(),new Item.Properties()));
        public static final RegistryObject<Item> SAPPHIRE_LANTERN = BLOCK_ITEMS.register("sapphire_lantern",
                () -> new BlockItem(Blocks.SAPPHIRE_LANTERN.get(),new Item.Properties()));
        public static final RegistryObject<Item> TOPAZ_LANTERN = BLOCK_ITEMS.register("topaz_lantern",
                () -> new BlockItem(Blocks.TOPAZ_LANTERN.get(),new Item.Properties()));
        public static final RegistryObject<Item> TOURMALINE_LANTERN = BLOCK_ITEMS.register("tourmaline_lantern",
                () -> new BlockItem(Blocks.TOURMALINE_LANTERN.get(),new Item.Properties()));
        public static final RegistryObject<Item> AMETHYST_LANTERN = BLOCK_ITEMS.register("amethyst_lantern",
                () -> new BlockItem(Blocks.AMETHYST_LANTERN.get(),new Item.Properties()));
        public static final RegistryObject<Item> ROSE_QUARTZ_LANTERN = BLOCK_ITEMS.register("rose_quartz_lantern",
                () -> new BlockItem(Blocks.ROSE_QUARTZ_LANTERN.get(),new Item.Properties()));

        public static final RegistryObject<Item> BOTTLED_RUBY = BLOCK_ITEMS.register("bottled_ruby",
                () -> new BlockItem(Blocks.BOTTLED_RUBY.get(),new Item.Properties()));
        public static final RegistryObject<Item> BOTTLED_AQUAMARINE = BLOCK_ITEMS.register("bottled_aquamarine",
                () -> new BlockItem(Blocks.BOTTLED_AQUAMARINE.get(),new Item.Properties()));
        public static final RegistryObject<Item> BOTTLED_GARNET = BLOCK_ITEMS.register("bottled_garnet",
                () -> new BlockItem(Blocks.BOTTLED_GARNET.get(),new Item.Properties()));
        public static final RegistryObject<Item> BOTTLED_MOONSTONE = BLOCK_ITEMS.register("bottled_moonstone",
                () -> new BlockItem(Blocks.BOTTLED_MOONSTONE.get(),new Item.Properties()));
        public static final RegistryObject<Item> BOTTLED_PERIDOT = BLOCK_ITEMS.register("bottled_peridot",
                () -> new BlockItem(Blocks.BOTTLED_PERIDOT.get(),new Item.Properties()));
        public static final RegistryObject<Item> BOTTLED_SAPPHIRE = BLOCK_ITEMS.register("bottled_sapphire",
                () -> new BlockItem(Blocks.BOTTLED_SAPPHIRE.get(),new Item.Properties()));
        public static final RegistryObject<Item> BOTTLED_TOPAZ = BLOCK_ITEMS.register("bottled_topaz",
                () -> new BlockItem(Blocks.BOTTLED_TOPAZ.get(),new Item.Properties()));
        public static final RegistryObject<Item> BOTTLED_TOURMALINE = BLOCK_ITEMS.register("bottled_tourmaline",
                () -> new BlockItem(Blocks.BOTTLED_TOURMALINE.get(),new Item.Properties()));
        public static final RegistryObject<Item> BOTTLED_AMETHYST = BLOCK_ITEMS.register("bottled_amethyst",
                () -> new BlockItem(Blocks.BOTTLED_AMETHYST.get(),new Item.Properties()));
        public static final RegistryObject<Item> BOTTLED_ROSE_QUARTZ = BLOCK_ITEMS.register("bottled_rose_quartz",
                () -> new BlockItem(Blocks.BOTTLED_ROSE_QUARTZ.get(),new Item.Properties()));

        public static final RegistryObject<Item> GEMSTONE_WORKBENCH = BLOCK_ITEMS.register("gemstone_workbench",
                () -> new BlockItem(Blocks.GEMSTONE_WORKBENCH.get(),new Item.Properties()));

        public static final RegistryObject<Item> GEODE = BLOCK_ITEMS.register("geode",
                () -> new BlockItem(Blocks.GEODE.get(),new Item.Properties()));
        public static final RegistryObject<Item> DEEPSLATE_GEODE = BLOCK_ITEMS.register("deepslate_geode",
                () -> new BlockItem(Blocks.DEEPSLATE_GEODE.get(),new Item.Properties()));
        public static final RegistryObject<Item> LINKER_CORE = BLOCK_ITEMS.register("linker_core",
                () -> new BlockItem(Blocks.LINKER_CORE.get(),new Item.Properties()));
    }
}
