package net.greenjab.jabsfixedenchanting.registry.registries;

import net.greenjab.jabsfixedenchanting.JabsFixedEnchanting;
import net.greenjab.jabsfixedenchanting.registry.menu.NewAnvilMenu;
import net.greenjab.jabsfixedenchanting.registry.menu.NewEnchantmentMenu;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;

public class MenuRegistry {

    public static final MenuType<NewEnchantmentMenu> NEW_ENCHANTMENT_SCREEN_HANDLER =
            Registry.register(
                    BuiltInRegistries.MENU,
                    JabsFixedEnchanting.id("new_enchantment"),
                    new MenuType<>(NewEnchantmentMenu::new, FeatureFlags.VANILLA_SET)
            );
    public static final MenuType<NewAnvilMenu> NEW_ANVIL_SCREEN_HANDLER =
            Registry.register(
                    BuiltInRegistries.MENU,
                    JabsFixedEnchanting.id("new_anvil"),
                    new MenuType<>(NewAnvilMenu::new, FeatureFlags.VANILLA_SET)
            );

    public static void registerMenus() {
        System.out.println("register Menus");
    }
}
