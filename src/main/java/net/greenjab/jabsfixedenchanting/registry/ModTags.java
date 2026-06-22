package net.greenjab.jabsfixedenchanting.registry;

import net.greenjab.jabsfixedenchanting.JabsFixedEnchanting;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;

public class ModTags {

    public static final TagKey<Item> STRINGTAG = TagKey.create(Registries.ITEM, JabsFixedEnchanting.id("string"));

    public static final TagKey<Enchantment> ABANDONED_MINESHAFT_EBOOKS = enchant_of("chest/abandoned_mineshaft");
    public static final TagKey<Enchantment> ANCIENT_CITY_EBOOKS = enchant_of("chest/ancient_city");
    public static final TagKey<Enchantment> BASTION_TREASURE_EBOOKS = enchant_of("chest/bastion_treasure");
    public static final TagKey<Enchantment> BURIED_TREASURE_EBOOKS = enchant_of("chest/buried_treasure");
    public static final TagKey<Enchantment> DESERT_PYRAMID_EBOOKS = enchant_of("chest/desert_pyramid");
    public static final TagKey<Enchantment> END_CITY_TREASURE_EBOOKS = enchant_of("chest/end_city_treasure");
    public static final TagKey<Enchantment> IGLOO_CHEST_EBOOKS = enchant_of("chest/igloo_chest");
    public static final TagKey<Enchantment> JUNGLE_TEMPLE_EBOOKS = enchant_of("chest/jungle_temple");
    public static final TagKey<Enchantment> NETHER_BRIDGE_EBOOKS = enchant_of("chest/nether_bridge");
    public static final TagKey<Enchantment> PILLAGER_OUTPOST_EBOOKS = enchant_of("chest/pillager_outpost");
    public static final TagKey<Enchantment> RUINED_PORTAL_EBOOKS = enchant_of("chest/ruined_portal");
    public static final TagKey<Enchantment> SHIPWRECK_TREASURE_EBOOKS = enchant_of("chest/shipwreck_treasure");
    public static final TagKey<Enchantment> SIMPLE_DUNGEON_EBOOKS = enchant_of("chest/simple_dungeon");
    public static final TagKey<Enchantment> STRONGHOLD_LIBRARY_EBOOKS = enchant_of("chest/stronghold_library");
    public static final TagKey<Enchantment> SWAMP_HUT_EBOOKS = enchant_of("chest/swamp_hut");
    public static final TagKey<Enchantment> UNDERWATER_RUIN_BIG_EBOOKS = enchant_of("chest/underwater_ruin_big");
    public static final TagKey<Enchantment> WOODLAND_MANSION_EBOOKS = enchant_of("chest/woodland_mansion");

    public static final TagKey<Enchantment> TRIAL_CHAMBER_EBOOKS = enchant_of("other/trial_chamber");
    public static final TagKey<Enchantment> TRAIL_RUINS_EBOOKS = enchant_of("other/trail_ruins");
    public static final TagKey<Enchantment> FISHING_TREASURE_EBOOKS = enchant_of("other/fishing_treasure");

    private static TagKey<Enchantment> enchant_of(String id)  {
        return TagKey.create(Registries.ENCHANTMENT, JabsFixedEnchanting.id(id));
    }

}
