package com.Nuaah.NGemExtBoxMod.block.gui.container;

import com.Nuaah.NGemExtBoxMod.block.entity.GemstoneWorkbenchEntity;
import com.Nuaah.NGemExtBoxMod.block.entity.LinkerCoreEntity;
import com.Nuaah.NGemExtBoxMod.block.gui.slot.GemstoneWorkbenchCombinableSlot;
import com.Nuaah.NGemExtBoxMod.regi.LinkerPowerData;
import com.Nuaah.NGemExtBoxMod.regi.tag.NGemExtBoxModTags;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class LinkerCoreMenu extends AbstractContainerMenu {

    private final LinkerCoreEntity blockEntity;
    private final Level level;
    private final ContainerData data;
    private final Player player;
    private boolean firstOpen = true;

    // ★ クライアント側用
//    public LinkerCoreMenu(int id, Inventory playerInv, FriendlyByteBuf buf) {
//        this(id, playerInv, (LinkerCoreEntity) playerInv.player.level()
//                .getBlockEntity(buf.readBlockPos()),new SimpleContainerData(2));
//    }

    public LinkerCoreMenu(int id, Inventory playerInventory, LinkerCoreEntity entity,ContainerData data) {
        super(NGemExtBoxModContainerTypes.LINKER_CORE.get(), id);
        checkContainerSize(playerInventory,2);

        this.blockEntity = entity;
        this.level = playerInventory.player.level();
        this.data = data;
        this.player = playerInventory.player;
        firstOpen = true;

        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iItemHandler -> {
            this.addSlot(new SlotItemHandler(iItemHandler, 0, 80, 35){

                @Override
                public int getMaxStackSize(@NotNull ItemStack stack) {
                    return 1;
                }

                @Override
                public boolean mayPlace(@NotNull ItemStack stack) {
                    return stack.is(Tags.Items.GEMS);
                }

                @Override
                public void set(@NotNull ItemStack stack) {
                    super.set(stack);

                    if(firstOpen){
                        firstOpen = false;
                        return;
                    }

                    //効果音
                    if (!stack.isEmpty()) {
                        player.playSound(SoundEvents.END_PORTAL_FRAME_FILL, 1.0F, 1.0F);
                    }
                }
            });
            this.addSlot(new SlotItemHandler(iItemHandler, 1, 134, 35){
                @Override
                public boolean mayPlace(@NotNull ItemStack stack) {
                    return stack.is(Tags.Items.GEMS);
                }
            });
        });

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);

        // データ同期用
        this.addDataSlots(this.data);
    }

    // クライアント側で呼ばれる getter
    public int getLinkerPower() {
        return data.get(0);
    }

    public int getLinkerCoreDistance() {
        return data.get(1);
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int col = 0; col < 9; ++col) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 142));
        }
    }

    @Override
    public void broadcastChanges() {
        super.broadcastChanges();

        if (level instanceof ServerLevel serverLevel) {
            // SavedData から現在のパワーを取得
            LinkerPowerData data = LinkerPowerData.get(serverLevel);
            this.data.set(0, data.getLinkerPower()); // ← 同期
            this.data.set(1, data.getNearLinkerCoreDistance()); // ← 同期

            double distance = getLinkerCoreDistance();
            data.setNearLinkerCoreDistance((int)distance);
        }
    }

    // --- Shiftクリック処理 ---
    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        // --- 特定のスロットは無効化 ---
        System.out.println(index);
        if (index == 1) {
            return ItemStack.EMPTY; // shiftクリック無効
        }

        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack original = slot.getItem();
            newStack = original.copy();

            int containerSlots = blockEntity.getItemHandler().getSlots();
            if (index < containerSlots) {
                if (!this.moveItemStackTo(original, containerSlots, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(original, 0, containerSlots, false)) {
                return ItemStack.EMPTY;
            }

            if (original.isEmpty()) slot.setByPlayer(ItemStack.EMPTY);
            else slot.setChanged();
        }
        return newStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return blockEntity != null && player.distanceToSqr(
                blockEntity.getBlockPos().getX() + 0.5D,
                blockEntity.getBlockPos().getY() + 0.5D,
                blockEntity.getBlockPos().getZ() + 0.5D
        ) <= 64.0D;
    }

}
