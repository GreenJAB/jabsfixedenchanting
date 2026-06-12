package net.greenjab.jabsfixedenchanting;

import net.fabricmc.api.ModInitializer;

import net.greenjab.jabsfixedenchanting.registry.registries.*;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;

public class JabsFixedEnchanting implements ModInitializer {
	public static final String NAMESPACE = "jabsfixedenchanting";
	public static final String MOD_NAME = "Jabs Fixed Enchanting";
	public static final Logger LOGGER = LoggerFactory.getLogger(NAMESPACE);
	public static MinecraftServer SERVER = null;

	public static HashMap<Item, Integer> ItemCapacities = new HashMap<>();

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing " + MOD_NAME);

		ItemRegistry.registerItems();
		ItemGroupRegistry.registerItemGroups();
		BlockRegistry.registerBlocks();
		LootTableRegistry.registerLootTable();
		ParticleRegistry.registerParticles();
		MenuRegistry.registerMenus();
	}

	public static ArrayList<ItemStack> getArmor(LivingEntity entity) {
		ArrayList<ItemStack> armor = new ArrayList<>();
		armor.add(entity.getItemBySlot(EquipmentSlot.FEET));
		armor.add(entity.getItemBySlot(EquipmentSlot.LEGS));
		armor.add(entity.getItemBySlot(EquipmentSlot.CHEST));
		armor.add(entity.getItemBySlot(EquipmentSlot.HEAD));
		return armor;
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(NAMESPACE, path);
	}
}

//TODO archeaology/fishing enchanting