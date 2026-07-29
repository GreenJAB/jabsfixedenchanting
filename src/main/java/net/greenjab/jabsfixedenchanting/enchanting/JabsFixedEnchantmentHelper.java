package net.greenjab.jabsfixedenchanting.enchanting;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.greenjab.jabsfixedenchanting.JabsFixedEnchanting;
import net.greenjab.jabsfixedenchanting.registry.registries.GameRuleRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EnchantingTableBlock;

import java.util.*;

public class JabsFixedEnchantmentHelper {

    public static HashMap<Item, Integer> ItemCapacities = new HashMap<>();
    private static int lastCapacity = 0;
    public static final int POWER_WHEN_MAX_LEVEL = 12;

    public static int getEnchantmentPower(Holder<Enchantment> enchantment, int level) {
        return Mth.ceil(enchantment.is(EnchantmentTags.CURSE) ?
                Math.min(-1, curseEnchantmentPowerFunction(enchantment.value(), level)) : Math.max(1, enchantmentPowerFunction(enchantment, level)));
    }

    private static double enchantmentPowerFunction(Holder<Enchantment> enchantment, int level) {
        return (POWER_WHEN_MAX_LEVEL+enchantment.value().getMaxLevel()-5) * Math.pow((double) level / enchantment.value().getMaxLevel(), 2);
    }

    private static double curseEnchantmentPowerFunction(Enchantment enchantment, int level) {
        return (-5) * Math.pow((double) level / enchantment.getMaxLevel(), 1.6);
    }

    public static int getEnchantmentCapacity(ItemStack itemStack) {
        Item item = itemStack.getItem();
        if (lastCapacity != JabsFixedEnchanting.SERVER.getGameRules().get(GameRuleRegistry.ENCHANT_CAPACITY_PERCENTAGE)) {
            lastCapacity = JabsFixedEnchanting.SERVER.getGameRules().get(GameRuleRegistry.ENCHANT_CAPACITY_PERCENTAGE);
            ItemCapacities = new HashMap<>(Map.of());
        }
        if (!ItemCapacities.containsKey(item))
            if (JabsFixedEnchanting.SERVER!=null) {
                HashMap<Item, Integer> map = new HashMap<>(Map.of());
                map.putAll(ItemCapacities);
                map.put(item, getNewEnchantmentCapacity(itemStack));
                ItemCapacities = map;
            }
        return ItemCapacities.getOrDefault(item, 0);
    }

    public static int getNewEnchantmentCapacity(ItemStack itemStack) {
        if (itemStack.is(Items.ENCHANTED_BOOK)||itemStack.is(Items.BOOK)) return 50;

        List<EnchantmentInstance> list = getPossibleEntries(itemStack);
        int power = 0;
        for (EnchantmentInstance enchantmentLevelEntry : list) {
            power += JabsFixedEnchantmentHelper.getEnchantmentPower(enchantmentLevelEntry.enchantment(), enchantmentLevelEntry.level());
        }
        boolean isGold = itemStack.is(ItemTags.PIGLIN_LOVED);
        return Math.min(Mth.ceil(power*(isGold?1f:JabsFixedEnchanting.SERVER.getGameRules().get(GameRuleRegistry.ENCHANT_CAPACITY_PERCENTAGE)/100f)), 50);
    }

    public static List<EnchantmentInstance> getPossibleEntries(ItemStack stack) {
        List<EnchantmentInstance> list = Lists.newArrayList();
        Level world =  Objects.requireNonNull(JabsFixedEnchanting.SERVER).overworld();
        Registry<Enchantment> optional = world.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
        Iterator<Enchantment> e = optional.stream().iterator();
        while (e.hasNext()) {
            Enchantment enchantment = e.next();
            Holder<Enchantment> ench = optional.wrapAsHolder(enchantment);
            if (!ench.is(EnchantmentTags.CURSE) && enchantment.canEnchant(stack))
                list.add(new EnchantmentInstance(optional.wrapAsHolder(enchantment), enchantment.getMaxLevel()));
        }
        return list;
    }

    public static int getOccupiedEnchantmentCapacity(ItemStack itemStack, boolean atLeast1) {
        int power = 0;
        ItemEnchantments enchantmentLevelsMap = EnchantmentHelper.getEnchantmentsForCrafting(itemStack);
        for (Holder<Enchantment> enchantment : enchantmentLevelsMap.keySet()) {
            int add = JabsFixedEnchantmentHelper.getEnchantmentPower(enchantment, enchantmentLevelsMap.getLevel(enchantment));
            power += add;
        }
        boolean isGold = itemStack.is(ItemTags.PIGLIN_LOVED);
        return Math.max(Mth.ceil(power*(isGold?0.5:1)),atLeast1?1:0);
    }

    /**
     * Counts nearby bookshelves; turned vanilla code snippet into dedicated method
     * @param world The world the enchanting table is in
     * @param enchantingTablePosition Its position as a net.minecraft.util.math.BlockPos
     * @return Bookshelf-count capped to 15
     */
    public static int countAccessibleBookshelves(Level world, BlockPos enchantingTablePosition) {
        int bookShelfCount = 0;
        for(BlockPos p : EnchantingTableBlock.BOOKSHELF_OFFSETS) {
            if (EnchantingTableBlock.isValidBookShelf(world, enchantingTablePosition, p)) {
                bookShelfCount++;
            }
        }
        bookShelfCount = Math.min(bookShelfCount, 15);
        return bookShelfCount;
    }

    /**
     * Similar to net.minecraft.block.EnchantingTableBlock.canAccessPowerProvider(World w, BlockPos tablePos, BlockPos providerOffset).
     * Checks whether the enchanting table at the given position can access the given block.
     * @param world The world the enchanting table is in
     * @param enchantingTablePosition It's position as a net.minecraft.util.math.BlockPos
     * @param providerOffset Given provider offset from the enchanting table's position
     * @param block net.minecraft.block.Block to look for
     * @return true if block matches and can be accessed, false otherwise
     */
    public static boolean canAccessBlock(Level world, BlockPos enchantingTablePosition, BlockPos providerOffset, Block block) {
        return world.getBlockState(enchantingTablePosition.offset(providerOffset)).is(block)
               && world.getBlockState(enchantingTablePosition.offset(providerOffset.getX() / 2, providerOffset.getY(), providerOffset.getZ() / 2))
                       .is(BlockTags.ENCHANTMENT_POWER_TRANSMITTER);
    }

    public static ItemStack applySuperEnchants(ItemStack IS, RandomSource random) {
        return applySuperEnchants(IS, random, false);
    }

    public static ItemStack applySuperEnchants(ItemStack IS, RandomSource random, boolean pale) {
        if (JabsFixedEnchanting.SERVER.getGameRules().get(GameRuleRegistry.SUPER_ENCHANT_CHANCE)==0) return IS;
        if (!IS.is(Items.ENCHANTED_BOOK)) {
            ItemStack IS2 = IS.getItem().getDefaultInstance();
            ItemEnchantments map = EnchantmentHelper.getEnchantmentsForCrafting(IS);

            boolean isSuper = false;
            ItemEnchantments.Mutable builder = new ItemEnchantments.Mutable(EnchantmentHelper.getEnchantmentsForCrafting(IS));
            for (Object2IntMap.Entry<Holder<Enchantment>> entry : map.entrySet()) {
                Holder<Enchantment> registryEntry = entry.getKey();
                Enchantment e = registryEntry.value();
                int i = entry.getIntValue();
                if (e.getMaxLevel() != 1) {
                    if (random.nextFloat() < JabsFixedEnchanting.SERVER.getGameRules().get(GameRuleRegistry.SUPER_ENCHANT_CHANCE)/100.0+(pale?0.10:0)) {
                        i = e.getMaxLevel() + 1;
                        isSuper = true;
                    }
                }
                builder.set(registryEntry, i);
            }

            if (isSuper) {
                IS2.set(DataComponents.REPAIR_COST, 1);
                if (!JabsFixedEnchanting.SERVER.getGameRules().get(GameRuleRegistry.MENDING_ON_OP_ITEMS)) {
                    builder.removeIf(e -> e.is(Enchantments.MENDING));
                }
            }
            EnchantmentHelper.setEnchantments(IS2, builder.toImmutable());
            return IS2;
        } else return IS;
    }

    public static int enchantLevel(ItemStack stack, String name) {
        int level = 0;
        ItemEnchantments itemEnchantmentsComponent = stack.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);
        for (Holder<Enchantment> e : stack.getEnchantments().keySet()) {
            if (e.getRegisteredName().toLowerCase().contains(name.toLowerCase())) {
                level += itemEnchantmentsComponent.getLevel(e);
            }
        }
        return level;
    }
}
