package net.greenjab.jabsfixedenchanting.mixin.enchanting;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @Inject(method = "inventoryTick", at = @At("HEAD"))
    private void addGreenGlintUpdate(Level level, Entity owner, EquipmentSlot slot, CallbackInfo ci) {
        if (slot == EquipmentSlot.MAINHAND) {
            if (level.getGameTime() % 20 == 0) {
                ItemStack stack = (ItemStack)(Object)this;
                if (stack.isEnchanted()) {
                    ItemEnchantments itemEnchantmentsComponent = stack.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);
                    stack.set(DataComponents.REPAIR_COST, 0);
                    for (Holder<Enchantment> enchantment : stack.getEnchantments().keySet()) {
                        if (itemEnchantmentsComponent.getLevel(enchantment) > enchantment.value().getMaxLevel()) {
                            stack.set(DataComponents.REPAIR_COST, 1);
                        }
                    }
                }
            }
        }
    }

    @ModifyArg(method = "addToTooltip", at = @At(value = "INVOKE", target ="Lnet/minecraft/world/item/component/TooltipProvider;addToTooltip(Lnet/minecraft/world/item/Item$TooltipContext;Ljava/util/function/Consumer;Lnet/minecraft/world/item/TooltipFlag;Lnet/minecraft/core/component/DataComponentGetter;)V"), index = 2)
    private TooltipFlag addEnchantLocationIcon(TooltipFlag type) {
        ItemStack stack = (ItemStack)(Object)this;
        if (stack.is(Items.ENCHANTED_BOOK)) {
            return TooltipFlag.ADVANCED;
        }
        return TooltipFlag.NORMAL;
    }

}
