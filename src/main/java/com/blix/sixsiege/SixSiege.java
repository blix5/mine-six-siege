package com.blix.sixsiege;

import com.blix.sixsiege.block.ModBlocks;
import com.blix.sixsiege.item.ModItemGroup;
import com.blix.sixsiege.item.ModItems;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SixSiege implements ModInitializer {

	public static final String MOD_ID = "sixsiege";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItemGroup.registerItemGroups();
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
	}
}