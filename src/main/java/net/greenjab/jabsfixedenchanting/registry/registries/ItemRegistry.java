package net.greenjab.jabsfixedenchanting.registry.registries;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import java.util.function.BiFunction;
import java.util.function.Function;

public class ItemRegistry {

    public static final Item NETHERITE_ANVIL = register(BlockRegistry.NETHERITE_ANVIL, new Item.Properties().fireResistant());
    public static final Item CHIPPED_NETHERITE_ANVIL = register(BlockRegistry.CHIPPED_NETHERITE_ANVIL, new Item.Properties().fireResistant());
    public static final Item DAMAGED_NETHERITE_ANVIL = register(BlockRegistry.DAMAGED_NETHERITE_ANVIL, new Item.Properties().fireResistant());


    public static Item register(ResourceKey<Item> key, Function<Item.Properties, Item> factory, Item.Properties settings) {
        Item item = factory.apply(settings.setId(key));
        if (item instanceof BlockItem blockItem) {
            blockItem.registerBlocks(Item.BY_BLOCK, item);
        }

        return Registry.register(BuiltInRegistries.ITEM, key, item);
    }
    public static Item register(Block block, Item.Properties settings) {
        return register(block, BlockItem::new, settings);
    }

    public static Item register(Block block, BiFunction<Block, Item.Properties, Item> factory, Item.Properties settings) {
        return register(
                keyOf(block.builtInRegistryHolder().key()),
                itemSettings -> factory.apply(block, itemSettings),
                settings.useBlockDescriptionPrefix()
        );
    }
    private static ResourceKey<Item> keyOf(ResourceKey<Block> blockKey) {
        return ResourceKey.create(Registries.ITEM, blockKey.identifier());
    }

    public static void registerItems() {
        System.out.println("register Items");
    }
}
