package net.greenjab.jabsfixedenchanting.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.greenjab.jabsfixedenchanting.JabsFixedEnchanting;
import net.greenjab.jabsfixedenchanting.client.render.ChiseledBookRenderer;
import net.greenjab.jabsfixedenchanting.client.screens.NewAnvilScreen;
import net.greenjab.jabsfixedenchanting.client.screens.NewEnchantmentScreen;
import net.greenjab.jabsfixedenchanting.registry.registries.MenuRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.network.chat.Component;

public class JabsFixedEnchantingClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		MenuScreens.register(MenuRegistry.NEW_ENCHANTMENT_SCREEN_HANDLER, NewEnchantmentScreen::new);
		MenuScreens.register(MenuRegistry.NEW_ANVIL_SCREEN_HANDLER, NewAnvilScreen::new);

		ChiseledBookRenderer CBR = new ChiseledBookRenderer();
		HudElementRegistry.addLast(JabsFixedEnchanting.id("chiseled_book"), CBR);

		FabricLoader.getInstance().getModContainer("jabsfixedenchanting").ifPresent(modContainer ->
			ResourceManagerHelper.registerBuiltinResourcePack(
            JabsFixedEnchanting.id( "re_covered"),
            modContainer,
            Component.translatable("jabsfixedenchanting.re_covered"),
            ResourcePackActivationType.NORMAL
        ));
	}

	public static boolean usingCustomContainers() {
		return (Minecraft.getInstance().getResourcePackRepository().getSelectedPacks().stream().anyMatch(pack -> pack.location().id().toLowerCase().contains("recolourful_containers")));
	}
}