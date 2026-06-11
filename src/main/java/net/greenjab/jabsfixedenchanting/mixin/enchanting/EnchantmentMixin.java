package net.greenjab.jabsfixedenchanting.mixin.enchanting;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.world.item.enchantment.Enchantment;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Enchantment.class)
public abstract class EnchantmentMixin {

    @ModifyExpressionValue(method = "getFullname", at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/ChatFormatting;GRAY:Lnet/minecraft/ChatFormatting;",
            opcode = Opcodes.GETSTATIC
    ))
    private static ChatFormatting greenSuperName(ChatFormatting original, @Local(argsOnly = true) Holder<Enchantment> enchantment,
                                                 @Local(argsOnly = true) int level) {
        if (level > enchantment.value().getMaxLevel()) {
            return ChatFormatting.GREEN;
        }
        return original;
    }
}
