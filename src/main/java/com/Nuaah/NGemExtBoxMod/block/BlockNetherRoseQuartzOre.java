package com.Nuaah.NGemExtBoxMod.block;

import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import org.jetbrains.annotations.Nullable;

public class BlockNetherRoseQuartzOre extends DropExperienceBlock {
    public BlockNetherRoseQuartzOre() {
        super(Properties.of()
                .strength(3.0F,3.0F)
                .requiresCorrectToolForDrops()
                , UniformInt.of(3,7)
        );
    }
}
