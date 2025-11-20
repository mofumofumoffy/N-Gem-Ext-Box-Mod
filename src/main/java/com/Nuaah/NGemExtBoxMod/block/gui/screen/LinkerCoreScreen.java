package com.Nuaah.NGemExtBoxMod.block.gui.screen;

import com.Nuaah.NGemExtBoxMod.block.gui.container.GemstoneWorkbenchMenu;
import com.Nuaah.NGemExtBoxMod.block.gui.container.LinkerCoreMenu;
import com.Nuaah.NGemExtBoxMod.main.NGemExtBoxMod;
import com.Nuaah.NGemExtBoxMod.regi.LinkerPowerData;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

@SuppressWarnings("removal")
public class LinkerCoreScreen extends AbstractContainerScreen<LinkerCoreMenu> {

    public static final ResourceLocation LINKER_CORE_TEXTURE = new ResourceLocation(NGemExtBoxMod.MOD_ID,"textures/gui/container/linker_core.png");

    public static final Component INVENTORY_TITLE = Component.translatable("container." + NGemExtBoxMod.MOD_ID + ".inventory");

    public LinkerCoreScreen(LinkerCoreMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
        this.titleLabelX = 8;
        this.titleLabelY = 4;
        this.inventoryLabelX = 8;
        this.inventoryLabelY = this.imageHeight - 92;

    }

    @Override
    public void render(GuiGraphics graphics, int w, int h, float f) {
        this.renderBackground(graphics);
        super.render(graphics, w, h, f);
        this.renderTooltip(graphics, w, h);
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float f, int w, int h) {
        int setW = (this.width - this.imageWidth) / 2;
        int setH = (this.height - this.imageHeight) / 2;
        graphics.blit(LINKER_CORE_TEXTURE,setW,setH,0,0,this.imageWidth,this.imageHeight);
    }

    @Override
    protected void renderLabels(GuiGraphics graphics, int w, int h) {
        graphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 0x8B8B8B, false);
        graphics.drawString(this.font, this.INVENTORY_TITLE ,this.inventoryLabelX, this.inventoryLabelY, 0x8B8B8B, false);

        int power = this.menu.getLinkerPower();

        graphics.drawString(this.font, "Linker Power", 108, 4, 0x8B8B8B);
        graphics.drawString(this.font, Integer.toString(power), 121, 12, 0x8B8B8B);

        int distance = -1;
        distance = this.menu.getLinkerCoreDistance();
        Component label;

        if(distance == -1){
            label = Component.literal( "NOT FOUND");
        } else {
            label = Component.literal( "▶ " + Integer.toString(distance));
        }

        int textWidth = this.font.width(label);
        int centerX = (this.imageWidth - textWidth) / 2;

        graphics.drawString(this.font,label, centerX, 55, 0x8B8B8B);
    }
}
