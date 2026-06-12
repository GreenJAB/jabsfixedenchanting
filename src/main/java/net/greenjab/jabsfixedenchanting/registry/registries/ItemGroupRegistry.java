package net.greenjab.jabsfixedenchanting.registry.registries;

import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ItemGroupRegistry {

    public static final CreativeModeTab JABS_FIXED_ENCHANTING = FabricCreativeModeTab.builder().title(Component.translatable("itemgroup.jabsfixedenchanting"))
            .icon( () -> new ItemStack(ItemRegistry.NETHERITE_ANVIL))
            .displayItems(
                     (_, entries) -> {
                        entries.accept(ItemRegistry.NETHERITE_ANVIL);
                        entries.accept(ItemRegistry.CHIPPED_NETHERITE_ANVIL);
                        entries.accept(ItemRegistry.DAMAGED_NETHERITE_ANVIL);
                    }).build();


    public static void registerItemGroups() {
        System.out.println("register Item Groups");
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, "jabs_fixed_enchanting", JABS_FIXED_ENCHANTING);
    }
}
