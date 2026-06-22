package net.greenjab.jabsfixedenchanting.registry.registries;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantRandomlyFunction;
import net.minecraft.world.level.storage.loot.functions.EnchantWithLevelsFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import static net.greenjab.jabsfixedenchanting.registry.ModTags.*;

public class LootTableAdditions {

    public static void registerLootTableAdds() {
        System.out.println("register LootTableAdds");

        LootTableEvents.MODIFY.register((key, tableBuilder, source, holder) -> {
            HolderLookup.RegistryLookup<Enchantment> enchantments = holder.lookupOrThrow(Registries.ENCHANTMENT);

            if (key==BuiltInLootTables.ABANDONED_MINESHAFT) {
                tableBuilder.pool(bookPool(enchantments, ABANDONED_MINESHAFT_EBOOKS, 1).build());
            } else if (key==BuiltInLootTables.ANCIENT_CITY) {
                tableBuilder.pool(bookPool(enchantments, ANCIENT_CITY_EBOOKS, 1).build());
            } else if (key==BuiltInLootTables.BASTION_TREASURE) {
                tableBuilder.pool(bookPoolPlus(enchantments, BASTION_TREASURE_EBOOKS, 1, 20, 1).build());
            } else if (key==BuiltInLootTables.BURIED_TREASURE) {
                tableBuilder.pool(bookPoolPlus(enchantments, BURIED_TREASURE_EBOOKS, 3, 20, 1).build());
            } else if (key==BuiltInLootTables.DESERT_PYRAMID) {
                tableBuilder.pool(bookPool(enchantments, DESERT_PYRAMID_EBOOKS, 1).build());
            } else if (key==BuiltInLootTables.END_CITY_TREASURE) {
                tableBuilder.pool(bookPoolPlus(enchantments, END_CITY_TREASURE_EBOOKS, 1, 30, 1).build());
            } else if (key==BuiltInLootTables.IGLOO_CHEST) {
                tableBuilder.pool(bookPoolPlus(enchantments, IGLOO_CHEST_EBOOKS, 3, 15, 1).build());
            } else if (key==BuiltInLootTables.JUNGLE_TEMPLE) {
                tableBuilder.pool(bookPool(enchantments, JUNGLE_TEMPLE_EBOOKS).build());
            } else if (key==BuiltInLootTables.NETHER_BRIDGE) {
                tableBuilder.pool(bookPoolPlus(enchantments, NETHER_BRIDGE_EBOOKS, 1, 15, 1).build());
            } else if (key==BuiltInLootTables.PILLAGER_OUTPOST) {
                tableBuilder.pool(bookPool(enchantments, PILLAGER_OUTPOST_EBOOKS).build());
            } else if (key==BuiltInLootTables.RUINED_PORTAL) {
                tableBuilder.pool(bookPoolPlus(enchantments, RUINED_PORTAL_EBOOKS, 10).build());
            } else if (key==BuiltInLootTables.SHIPWRECK_TREASURE) {
                tableBuilder.pool(bookPoolPlus(enchantments, SHIPWRECK_TREASURE_EBOOKS, 15).build());
            } else if (key==BuiltInLootTables.SIMPLE_DUNGEON) {
                tableBuilder.pool(bookPool(enchantments, SIMPLE_DUNGEON_EBOOKS).build());
            } else if (key==BuiltInLootTables.STRONGHOLD_LIBRARY) {
                tableBuilder.pool(bookPool(enchantments, STRONGHOLD_LIBRARY_EBOOKS, 1).build());
            } else if (key==BuiltInLootTables.UNDERWATER_RUIN_BIG) {
                tableBuilder.pool(bookPool(enchantments, UNDERWATER_RUIN_BIG_EBOOKS).build());
            } else if (key==BuiltInLootTables.WOODLAND_MANSION) {
                tableBuilder.pool(bookPool(enchantments, WOODLAND_MANSION_EBOOKS, 1).build());

            } else if (key==BuiltInLootTables.FISHING_TREASURE) {
                tableBuilder.modifyPools(builder -> builder
                        .add(LootItem.lootTableItem(Items.BOOK).setWeight(1)
                                .apply(new EnchantRandomlyFunction.Builder().withOneOf(enchantments.getOrThrow(FISHING_TREASURE_EBOOKS)))));
            } else if (key==BuiltInLootTables.TRAIL_RUINS_ARCHAEOLOGY_RARE) {
                tableBuilder.modifyPools(builder -> builder
                        .add(LootItem.lootTableItem(Items.BOOK).setWeight(4)
                                .apply(new EnchantRandomlyFunction.Builder().withOneOf(enchantments.getOrThrow(TRAIL_RUINS_EBOOKS))))
                        .add(LootItem.lootTableItem(Items.BOOK).setWeight(2)
                                .apply(new EnchantWithLevelsFunction.Builder(ConstantValue.exactly(20))
                                        .withOptions(enchantments.get(EnchantmentTags.ON_RANDOM_LOOT).map( named -> named)))));
            }

            //trial chamber vaults need to be overriden to remove the existing 'specific' ebooks
            /* else if (key==BuiltInLootTables.TRIAL_CHAMBERS) {
                tableBuilder.modifyPools(builder ->builder
                        .add(LootItem.lootTableItem(Items.BOOK).setWeight(4)
                                .apply(new EnchantRandomlyFunction.Builder().withOneOf(enchantments.getOrThrow(TRAIL_RUINS_EBOOKS))))
                        .add(LootItem.lootTableItem(Items.BOOK).setWeight(2)
                                .apply(new EnchantWithLevelsFunction.Builder(ConstantValue.exactly(20))
                                        .withOptions(enchantments.get(EnchantmentTags.ON_RANDOM_LOOT).map( named -> named)))));
            }*/

            //code for if I need to add to an existing table that has multiple pools
            /*if (key==BuiltInLootTables.BASTION_BRIDGE) {
                tableBuilder.modifyPools(builder -> {
                    ImmutableList<LootPoolEntryContainer> i = builder.entries.build();
                    for (LootPoolEntryContainer item : i) {
                        if (item instanceof LootItem lootItem){
                            if (lootItem.item.value() == Items.LODESTONE){
                                builder.add(
                                        LootItem.lootTableItem(Items.BOOK)
                                                .setWeight(1)
                                                .apply(new EnchantRandomlyFunction.Builder().withOneOf(enchantments.getOrThrow(TRAIL_RUINS_EBOOKS)))
                                );
                                break;
                            }
                        }

                    }
                });
            }*/
	  });
    }

    private static LootPool.Builder bookPoolPlus(HolderLookup.RegistryLookup<Enchantment> enchantments, TagKey<Enchantment> tag, int level){
        return bookPoolPlus(enchantments, tag, 2, 1, 1, level, 1);
    }

    private static LootPool.Builder bookPoolPlus(HolderLookup.RegistryLookup<Enchantment> enchantments, TagKey<Enchantment> tag, int rolls, int level, int plusWeight){
        return bookPoolPlus(enchantments, tag, rolls, 1, 1, level, plusWeight);
    }

    private static LootPool.Builder bookPoolPlus(HolderLookup.RegistryLookup<Enchantment> enchantments, TagKey<Enchantment> tag, int rolls, int weight, int blankWeight, int level, int plusWeight){
        return bookPool(enchantments, tag, rolls, weight, blankWeight).add(LootItem.lootTableItem(Items.BOOK).setWeight(plusWeight)
                .apply(new EnchantWithLevelsFunction.Builder(ConstantValue.exactly(level))
                        .withOptions(enchantments.get(EnchantmentTags.ON_RANDOM_LOOT).map( named -> named))));
    }

    private static LootPool.Builder bookPool(HolderLookup.RegistryLookup<Enchantment> enchantments, TagKey<Enchantment> tag, int rolls){
        return bookPool(enchantments, tag, rolls, 1, 1);
    }

    private static LootPool.Builder bookPool(HolderLookup.RegistryLookup<Enchantment> enchantments, TagKey<Enchantment> tag){
        return bookPool(enchantments, tag, 2, 1, 1);
    }

    private static LootPool.Builder bookPool(HolderLookup.RegistryLookup<Enchantment> enchantments, TagKey<Enchantment> tag, int rolls, int weight, int blankWeight){
        return LootPool.lootPool().setRolls(ConstantValue.exactly(rolls))
                .add(LootItem.lootTableItem(Items.BOOK)
                        .apply(new EnchantRandomlyFunction.Builder().withOneOf(enchantments.getOrThrow(tag))).setWeight(weight))
                .add(LootItem.lootTableItem(Items.AIR).setWeight(blankWeight));
    }
}
