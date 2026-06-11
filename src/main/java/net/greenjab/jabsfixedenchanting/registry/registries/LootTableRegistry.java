package net.greenjab.jabsfixedenchanting.registry.registries;

import net.greenjab.jabsfixedenchanting.JabsFixedEnchanting;
import net.greenjab.jabsfixedenchanting.registry.loot_function.LibrarianBookLootFunction;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;

public class LootTableRegistry {

    public static final ResourceKey<LootTable> SWAMP_HUT = registerLoot_Table("chests/swamp_hut");

    private static ResourceKey<LootTable> registerLoot_Table(String id) {
        return registerLootTable(ResourceKey.create(Registries.LOOT_TABLE, JabsFixedEnchanting.id(id)));
    }
    private static ResourceKey<LootTable> registerLootTable(ResourceKey<LootTable> key) {
        if (BuiltInLootTables.LOCATIONS.add(key)) {
            return key;
        } else {
            throw new IllegalArgumentException(key.identifier() + " is already a registered built-in loot table");
        }
    }

    public static void registerLootTable() {
        System.out.println("register LootTables");
        Registry.register(BuiltInRegistries.LOOT_FUNCTION_TYPE, JabsFixedEnchanting.id("librarian_book"), LibrarianBookLootFunction.CODEC);
    }
}
