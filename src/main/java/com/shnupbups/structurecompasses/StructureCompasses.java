package com.shnupbups.structurecompasses;

import net.fabricmc.api.ModInitializer;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.structure.StructureFeatures;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.StructureFeature;

@SuppressWarnings("unused")
public class StructureCompasses implements ModInitializer {
	public static StructureCompassItem ABANDONED_MINESHAFT_COMPASS;
	public static StructureCompassItem BURIED_TREASURE_COMPASS;
	public static StructureCompassItem DESERT_PYRAMID_COMPASS;
	public static StructureCompassItem END_CITY_COMPASS;
	public static StructureCompassItem IGLOO_COMPASS;
	public static StructureCompassItem JUNGLE_TEMPLE_COMPASS;
	public static StructureCompassItem NETHER_FORTRESS_COMPASS;
	public static StructureCompassItem OCEAN_MONUMENT_COMPASS;
	public static StructureCompassItem OCEAN_RUIN_COMPASS;
	public static StructureCompassItem PILLAGER_OUTPOST_COMPASS;
	public static StructureCompassItem SHIPWRECK_COMPASS;
	public static StructureCompassItem STRONGHOLD_COMPASS;
	public static StructureCompassItem VILLAGE_COMPASS;
	public static StructureCompassItem WITCH_HUT_COMPASS;
	public static StructureCompassItem WOODLAND_MANSION_COMPASS;

	@Override
	public void onInitialize() {
		ABANDONED_MINESHAFT_COMPASS = register(StructureFeatures.MINESHAFT);
		BURIED_TREASURE_COMPASS = register(StructureFeatures.BURIED_TREASURE);
		DESERT_PYRAMID_COMPASS = register(StructureFeatures.DESERT_PYRAMID);
		END_CITY_COMPASS = register(StructureFeatures.END_CITY);
		IGLOO_COMPASS = register(StructureFeatures.IGLOO);
		JUNGLE_TEMPLE_COMPASS = register(StructureFeatures.JUNGLE_PYRAMID);
		NETHER_FORTRESS_COMPASS = register(StructureFeatures.FORTRESS);
		OCEAN_MONUMENT_COMPASS = register(StructureFeatures.MONUMENT);
		OCEAN_RUIN_COMPASS = register(StructureFeatures.OCEAN_RUIN);
		PILLAGER_OUTPOST_COMPASS = register(StructureFeatures.PILLAGER_OUTPOST);
		SHIPWRECK_COMPASS = register(StructureFeatures.SHIPWRECK);
		STRONGHOLD_COMPASS = register(StructureFeatures.STRONGHOLD);
		VILLAGE_COMPASS = register(StructureFeatures.VILLAGE);
		WITCH_HUT_COMPASS = register(StructureFeatures.SWAMP_HUT);
		WOODLAND_MANSION_COMPASS = register(StructureFeatures.MANSION);
	}

	public static StructureCompassItem register(StructureFeature structure) {
		StructureCompassItem compass = new StructureCompassItem(new Item.Settings().group(ItemGroup.TOOLS), structure);
		return Registry.register(Registry.ITEM, getId(Registry.STRUCTURE_FEATURE.getId(structure).getPath()+"_compass"), compass);
	}
	
	public static Identifier getId(String name) {
		return new Identifier("structurecompasses", name);
	}
}
