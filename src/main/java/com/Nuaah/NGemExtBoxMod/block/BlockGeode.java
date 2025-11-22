package com.Nuaah.NGemExtBoxMod.block;

import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.DropExperienceBlock;

public class BlockGeode extends DropExperienceBlock {
    public BlockGeode() {
        super(Properties.of()
                .strength(5.5F,4.0F)
                .requiresCorrectToolForDrops()
                , UniformInt.of(3,7)
        );
    }
}
