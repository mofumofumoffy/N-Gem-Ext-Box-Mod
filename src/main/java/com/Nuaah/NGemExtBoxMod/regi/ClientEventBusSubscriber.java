package com.Nuaah.NGemExtBoxMod.regi;

import com.Nuaah.NGemExtBoxMod.block.entity.LinkerCoreRenderer;
import com.Nuaah.NGemExtBoxMod.block.entity.NGemExtBoxModBlockEntityTypes;
import com.Nuaah.NGemExtBoxMod.block.entity.NGemExtBoxModCapabilities;
import com.Nuaah.NGemExtBoxMod.block.gui.container.NGemExtBoxModContainerTypes;
import com.Nuaah.NGemExtBoxMod.block.gui.screen.GemstoneWorkbenchScreen;
import com.Nuaah.NGemExtBoxMod.block.gui.screen.LinkerCoreScreen;
import com.Nuaah.NGemExtBoxMod.main.NGemExtBoxMod;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = NGemExtBoxMod.MOD_ID,bus = Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)

@SuppressWarnings("removal")
public class ClientEventBusSubscriber {

    @SubscribeEvent
    public static void slientSetup(FMLClientSetupEvent event){
        blockScreenRegister();
    }

    private static void blockScreenRegister(){
        MenuScreens.register(NGemExtBoxModContainerTypes.GEMSTONE_WORKBENCH.get(), GemstoneWorkbenchScreen::new);
        MenuScreens.register(NGemExtBoxModContainerTypes.LINKER_CORE.get(), LinkerCoreScreen::new);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(NGemExtBoxModBlockEntityTypes.LINKER_CORE.get(), LinkerCoreRenderer::new);
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(NGemExtBoxModBlocks.Blocks.RUBY_LANTERN.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(NGemExtBoxModBlocks.Blocks.AQUAMARINE_LANTERN.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(NGemExtBoxModBlocks.Blocks.GARNET_LANTERN.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(NGemExtBoxModBlocks.Blocks.MOONSTONE_LANTERN.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(NGemExtBoxModBlocks.Blocks.PERIDOT_LANTERN.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(NGemExtBoxModBlocks.Blocks.SAPPHIRE_LANTERN.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(NGemExtBoxModBlocks.Blocks.TOPAZ_LANTERN.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(NGemExtBoxModBlocks.Blocks.TOURMALINE_LANTERN.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(NGemExtBoxModBlocks.Blocks.AMETHYST_LANTERN.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(NGemExtBoxModBlocks.Blocks.ROSE_QUARTZ_LANTERN.get(), RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(NGemExtBoxModBlocks.Blocks.BOTTLED_RUBY.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(NGemExtBoxModBlocks.Blocks.BOTTLED_AQUAMARINE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(NGemExtBoxModBlocks.Blocks.BOTTLED_GARNET.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(NGemExtBoxModBlocks.Blocks.BOTTLED_MOONSTONE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(NGemExtBoxModBlocks.Blocks.BOTTLED_PERIDOT.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(NGemExtBoxModBlocks.Blocks.BOTTLED_SAPPHIRE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(NGemExtBoxModBlocks.Blocks.BOTTLED_TOPAZ.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(NGemExtBoxModBlocks.Blocks.BOTTLED_TOURMALINE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(NGemExtBoxModBlocks.Blocks.BOTTLED_AMETHYST.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(NGemExtBoxModBlocks.Blocks.BOTTLED_ROSE_QUARTZ.get(), RenderType.cutout());
    }
}
