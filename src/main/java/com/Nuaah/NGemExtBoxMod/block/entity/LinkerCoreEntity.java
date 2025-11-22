package com.Nuaah.NGemExtBoxMod.block.entity;

import com.Nuaah.NGemExtBoxMod.block.BlockLinkerCore;
import com.Nuaah.NGemExtBoxMod.block.gui.container.LinkerCoreMenu;
import com.Nuaah.NGemExtBoxMod.main.NGemExtBoxMod;
import com.Nuaah.NGemExtBoxMod.regi.LinkerCoreRegistry;
import com.Nuaah.NGemExtBoxMod.regi.LinkerPowerData;
import com.Nuaah.NGemExtBoxMod.regi.NGemExtBoxModDataLoader;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class LinkerCoreEntity extends BlockEntity implements MenuProvider {

    private static final Logger log = LoggerFactory.getLogger(LinkerCoreEntity.class);
    private final ItemStackHandler itemHandler = new ItemStackHandler(2){
        @Override
        protected void onContentsChanged(int slotIndex) {
            super.onContentsChanged(slotIndex);

            ItemStack linkerGem = itemHandler.getStackInSlot(0);
            ItemStack linkerPowerSlot = itemHandler.getStackInSlot(1);

            if(slotIndex == 1){
                if(!linkerPowerSlot.isEmpty() && linkerPowerSlot.is(Tags.Items.GEMS)) {
                    Item gem = linkerPowerSlot.getItem();
                    int count = linkerPowerSlot.getCount();

                    if (!level.isClientSide()) {

//                        int distance = getLinkerCoreDistance();
//
//                        data.setNearLinkerCoreDistance(distance);

                        JsonObject json = NGemExtBoxModDataLoader.DATA.get(new ResourceLocation(NGemExtBoxMod.MOD_ID,"power"));

                        for (Map.Entry<String, JsonElement> entry : json.entrySet()){ //json読み取り
                            String key = entry.getKey();
                            Item jsonItem = BuiltInRegistries.ITEM.get(new ResourceLocation(key));
                            if (jsonItem == gem) {
                                int value = entry.getValue().getAsInt();
                                LinkerPowerData data = LinkerPowerData.get(level);
                                data.addPower(value * count);
                                itemHandler.setStackInSlot(1, ItemStack.EMPTY);
                                break;
                                }
                            }
                    }

                    setChanged();
                }
            } else {
                if (!level.isClientSide()) {
                    SaveLinkerCoreGem();

                    double distance = getLinkerCoreDistance();

                    LinkerPowerData data = LinkerPowerData.get(level);
                    data.setNearLinkerCoreDistance((int)distance);

                    // 光る処理
                    BlockState state = level.getBlockState(worldPosition);
                    if (!(state.getBlock() instanceof BlockLinkerCore)) return;

                    boolean hasGem = !getDisplayItem().isEmpty();
                    boolean isLit = state.getValue(BlockLinkerCore.LIT);

                    if (hasGem != isLit) {
                        level.setBlock(worldPosition, state.setValue(BlockLinkerCore.LIT, hasGem), 3);
                    }
                }
            }
            setChanged(); //保存
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    public void SaveLinkerCoreGem(){
        LinkerPowerData LinkerData = LinkerPowerData.get(level);
        ResourceKey<Level> dimension = level.dimension();

        // テータ保存
        System.out.println(getDisplayItem() + "DISPLAY");
        CompoundTag stackTag = getDisplayItem().save(new CompoundTag());
        CompoundTag coreTag = new CompoundTag();
        coreTag.put("linker_gem", stackTag);
        coreTag.putString("linker_dimension",dimension.location().toString());

        LinkerData.setCoreData(getBlockPos(), coreTag);
    }

    public LinkerCoreEntity(BlockPos pos, BlockState state) {
        super(NGemExtBoxModBlockEntityTypes.LINKER_CORE.get(), pos, state);
    }

    //最短距離取得 -1
    public int getLinkerCoreDistance(){
        LinkerPowerData data = LinkerPowerData.get(level);
        BlockPos nearLinkerCore = findNearestCore();
        double distance = -1;

        if (nearLinkerCore != null) {
            distance = Math.sqrt(this.getBlockPos().distSqr(nearLinkerCore));
        }

        return (int)distance;
    }

    public BlockPos findNearestCore() {
        if (level == null || level.isClientSide() || getDisplayItem().isEmpty()) return null;

        // 自分のCoreデータをNBT化（LinkerPowerDataに登録されている形式に合わせる）
        CompoundTag NBT = new CompoundTag();
        CompoundTag itemTag = new CompoundTag();
        getDisplayItem().save(itemTag);
        NBT.put("linker_gem", itemTag);

        // 一番近いCoreの座標を取得
        BlockPos nearestPos = LinkerCoreRegistry.getNearestCorePos(level, this.worldPosition, NBT);

        if (nearestPos != null) {
            System.out.println("最も近いCoreは " + nearestPos);
            return nearestPos;

        } else {
            System.out.println("近くに同じ宝石のCoreは見つかりませんでした");
            return null;
        }
    }

    //表示する宝石取得
    public ItemStack getDisplayItem(){
        return itemHandler.getStackInSlot(0);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);

        if (!level.isClientSide) { //リストに登録
            LinkerCoreRegistry.register(this);
            SaveLinkerCoreGem();

            // LinkerPowerData から自分の座標データを取得
//            if (level instanceof ServerLevel serverLevel) {
//                LinkerPowerData data = LinkerPowerData.get(serverLevel);
//                CompoundTag saved = data.getCoreData(getBlockPos());
//            }
        }
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        if (!level.isClientSide) {
            LinkerCoreRegistry.unregister(this);

        }
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    // アイテムNBT保存
    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);

        tag.put("linker_core", itemHandler.serializeNBT());
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        itemHandler.deserializeNBT(tag.getCompound("linker_core"));
        if (this.level != null && !this.level.isClientSide) {
            setChanged(); // 同期発行
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("container." + NGemExtBoxMod.MOD_ID + ".linker_core");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int a, Inventory inventory, Player player) {
        return new LinkerCoreMenu(a, inventory, this,new SimpleContainerData(2));
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        this.saveAdditional(tag);
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        // クライアント側で受け取ったデータを反映
        this.load(tag);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        // クライアントへ更新通知を送るパケット
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void setChanged() {
        super.setChanged();
        if (this.level != null && !this.level.isClientSide) {
            this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 3);
        }
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for(int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    // スロットアクセス用
    public ItemStackHandler getItemHandler() {
        return itemHandler;
    }
}
