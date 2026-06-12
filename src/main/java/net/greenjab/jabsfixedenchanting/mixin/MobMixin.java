package net.greenjab.jabsfixedenchanting.mixin;

import net.greenjab.jabsfixedenchanting.enchanting.JabsFixedEnchantmentHelper;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biomes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(Mob.class)
public abstract class MobMixin extends LivingEntity {

    protected MobMixin(EntityType<? extends LivingEntity> entityType, Level world) {
        super(entityType, world);
    }

    @ModifyVariable(
            method = "enchantSpawnedEquipment(Lnet/minecraft/world/level/ServerLevelAccessor;Lnet/minecraft/world/entity/EquipmentSlot;Lnet/minecraft/util/RandomSource;FLnet/minecraft/world/DifficultyInstance;)V",
            at = @At(value = "HEAD", ordinal = 0),
            argsOnly = true
    )
    private float applySuperEnchantArmor(
            float chance) {
        return chance * (this.level().getBiome(this.blockPosition()).is(Biomes.PALE_GARDEN)?1.5f:1);
    }

    @ModifyArg(method = "enchantSpawnedEquipment(Lnet/minecraft/world/level/ServerLevelAccessor;Lnet/minecraft/world/entity/EquipmentSlot;Lnet/minecraft/util/RandomSource;FLnet/minecraft/world/DifficultyInstance;)V", at = @At(value = "INVOKE",
                                                                                                                                                                                                                target = "Lnet/minecraft/world/entity/Mob;setItemSlot(Lnet/minecraft/world/entity/EquipmentSlot;Lnet/minecraft/world/item/ItemStack;)V"), index = 1)
    private ItemStack applySuperEnchantArmor(
            ItemStack stack) {
        return JabsFixedEnchantmentHelper.applySuperEnchants(stack, random, this.level().getBiome(this.blockPosition()).is(Biomes.PALE_GARDEN));
    }
}
