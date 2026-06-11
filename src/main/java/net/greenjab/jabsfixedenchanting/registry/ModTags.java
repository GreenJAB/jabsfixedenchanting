package net.greenjab.jabsfixedenchanting.registry;

import net.greenjab.jabsfixedenchanting.JabsFixedEnchanting;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;

public class ModTags {

    public static final TagKey<Item> STRINGTAG = TagKey.create(Registries.ITEM, JabsFixedEnchanting.id("string"));
    public static final TagKey<Item> UNBREAKABLE = TagKey.create(Registries.ITEM, JabsFixedEnchanting.id("unbreakable"));

}
