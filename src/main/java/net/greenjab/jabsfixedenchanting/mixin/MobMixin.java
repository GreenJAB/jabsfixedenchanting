package net.greenjab.jabsfixedenchanting.mixin;

import net.greenjab.jabsfixedenchanting.enchanting.JabsFixedEnchantmentHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biomes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

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

    @Inject(method = "finalizeSpawn", at=@At(value = "HEAD"))
    private void addNightTag(ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason spawnReason, SpawnGroupData groupData,
                             CallbackInfoReturnable<SpawnGroupData> cir){
        Mob LE = (Mob)(Object)this;
        if (LE instanceof Monster HE) {
            if (level.getBrightness(LightLayer.SKY, HE.blockPosition()) > 10 && level.getSkyDarken() >= 5) {
                HE.addTag("night");
            }
        }
    }
}
