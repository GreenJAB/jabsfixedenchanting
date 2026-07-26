package net.greenjab.jabsfixedenchanting.registry.registries;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.serialization.Codec;
import net.greenjab.jabsfixedenchanting.JabsFixedEnchanting;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.gamerules.*;

import java.util.function.ToIntFunction;

public class GameRuleRegistry {
    public static final GameRuleCategory JABSFIXEDENCHANTING = GameRuleCategory.register(JabsFixedEnchanting.id("aaa_jabsfixedenchanting"));

    public static GameRule<Integer> ENCHANT_CAPACITY_PERCENTAGE;
    public static GameRule<Integer> SUPER_ENCHANT_CHANCE;
    public static GameRule<Boolean> COMBINE_ENCHANTED_ITEMS;
    public static GameRule<Boolean> MENDING_ON_OP_ITEMS;
    public static GameRule<Boolean> VILLAGERS_BIOME_ENCHANTED_BOOKS;
    public static GameRule<Boolean> GRINDSTONE_DAMAGES_ITEM;
    public static GameRule<Boolean> GOLD_GEAR_AUTO_REPAIRS;


    public static void registerGameRules() {
        System.out.println("register GameRules");
        ENCHANT_CAPACITY_PERCENTAGE = registerInteger("enchant_capacity_percentage", 54, 1, 100);
        SUPER_ENCHANT_CHANCE = registerInteger("super_enchant_chance", 5, 0, 100);
        COMBINE_ENCHANTED_ITEMS = registerBoolean("combine_enchanted_items", false);
        MENDING_ON_OP_ITEMS = registerBoolean("mending_on_op_items", false);
        VILLAGERS_BIOME_ENCHANTED_BOOKS = registerBoolean("villagers_biome_enchanted_books", true);

        GRINDSTONE_DAMAGES_ITEM = registerBoolean("grindstone_damages_item", true);
        GOLD_GEAR_AUTO_REPAIRS = registerBoolean("gold_gear_auto_repairs", true);
    }

    private static GameRule<Boolean> registerBoolean(String name, boolean defaultValue) {
        return register(name, GameRuleType.BOOL, BoolArgumentType.bool(), Codec.BOOL, defaultValue,
                FeatureFlagSet.of(), GameRuleTypeVisitor::visitBoolean,value -> value ? 1 : 0);
    }

    private static GameRule<Integer> registerInteger(
            final String id, final int defaultValue, final int min, final int max) {
        return register(id, GameRuleType.INT, IntegerArgumentType.integer(min, max), Codec.intRange(min, max),
                defaultValue, FeatureFlagSet.of(), GameRuleTypeVisitor::visitInteger, i -> i);
    }

    private static <T> GameRule<T> register(String name, GameRuleType type,
                                            ArgumentType<T> argumentType, Codec<T> codec, T defaultValue, FeatureFlagSet requiredFeatures,
                                            GameRules.VisitorCaller<T> acceptor, ToIntFunction<T> commandResultSupplier) {
        return Registry.register(
                BuiltInRegistries.GAME_RULE, JabsFixedEnchanting.id(name),
                new GameRule<>(GameRuleRegistry.JABSFIXEDENCHANTING, type, argumentType, acceptor, codec, commandResultSupplier, defaultValue, requiredFeatures));
    }
}
