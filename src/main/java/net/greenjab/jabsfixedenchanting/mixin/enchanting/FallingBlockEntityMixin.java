package net.greenjab.jabsfixedenchanting.mixin.enchanting;

import net.greenjab.jabsfixedenchanting.registry.registries.BlockRegistry;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(FallingBlockEntity.class)
public abstract class FallingBlockEntityMixin {

    @Shadow
    private BlockState blockState;

    @ModifyVariable(method = "causeFallDamage", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/item/FallingBlockEntity;level()Lnet/minecraft/world/level/Level;"
    ), ordinal = 1)
    private float a (float damage) {
        if (this.blockState.is(BlockRegistry.NETHERITE_ANVIL)||
                this.blockState.is(BlockRegistry.CHIPPED_NETHERITE_ANVIL)||
                this.blockState.is(BlockRegistry.DAMAGED_NETHERITE_ANVIL)) {
            return damage*2;
        }
        return damage;
    }
}
