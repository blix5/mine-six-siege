package com.blix.sixsiege.block;

import com.blix.sixsiege.SixSiege;
import com.blix.sixsiege.block.custom.BarricadeBlock;
import com.blix.sixsiege.sound.ModSounds;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {

    public static final Block BARRICADE = registerBlock("barricade",
            new BarricadeBlock(FabricBlockSettings.copyOf(Blocks.OAK_DOOR).strength(2.0f).sounds(ModSounds.BARRICADE_SOUNDS)));

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(SixSiege.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block) {
        Item item = Registry.register(Registries.ITEM, new Identifier(SixSiege.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
        return item;
    }

    public static void registerModBlocks() {
        SixSiege.LOGGER.info("Registering ModBlocks for " + SixSiege.MOD_ID);
    }

}
