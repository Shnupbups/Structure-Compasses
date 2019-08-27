package com.shnupbups.structurecompasses;

import net.fabricmc.api.ModInitializer;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.structure.StructureFeatures;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.StructureFeature;

public class StructureCompasses implements ModInitializer {
	@Override
	public void onInitialize() {
		for(StructureFeature structure : Registry.STRUCTURE_FEATURE) {
			StructureCompassItem compass = new StructureCompassItem(new Item.Settings().group(ItemGroup.TOOLS), structure);
			Registry.register(Registry.ITEM, getId(structure.getName().toLowerCase()+"_compass"), compass);
		}
	}
	
	public static Identifier getId(String name) {
		return new Identifier("structurecompasses", name);
	}
}
