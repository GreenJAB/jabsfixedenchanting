package net.greenjab.jabsfixedenchanting.mixin;

import net.greenjab.jabsfixedenchanting.JabsFixedEnchanting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Item.class)
public abstract class ItemMixin {

    @Inject(method = "inventoryTick", at = @At("HEAD"))
    private void repairGold(ItemStack itemStack, ServerLevel level, Entity owner, EquipmentSlot slot, CallbackInfo ci) {
        if (owner instanceof Player || owner instanceof ArmorStand) {
            if (itemStack.getComponents().has(DataComponents.DAMAGE)) {
                if (itemStack.is(ItemTags.PIGLIN_LOVED)) {
                    if (itemStack.getMaxDamage() != 0) {
                        if (level.getGameTime() % (24000 / itemStack.getMaxDamage()) == 0) {
                            if (owner instanceof Player player && JabsFixedEnchanting.getArmor(player).contains(itemStack)) return;
                            itemStack.setDamageValue(itemStack.getDamageValue() - 1);
                        }
                    }
                }
            }
        }
    }
}
