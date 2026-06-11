package net.greenjab.jabsfixedenchanting.client.mixin;


import com.llamalad7.mixinextras.sugar.Local;
import net.greenjab.jabsfixedenchanting.JabsFixedEnchanting;
import net.greenjab.jabsfixedenchanting.enchanting.JabsFixedEnchantmentHelper;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.fog.environment.LavaFogEnvironment;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(LavaFogEnvironment.class)
public abstract class LavaFogEnvironmentMixin {

    @ModifyConstant(method = "setupFog", constant = @Constant(floatValue = 5.0f))
    private float lessLavaFogFireRes(float constant) { return 9f;}

    @ModifyConstant(method = "setupFog", constant = @Constant(floatValue = 1.0f))
    private float lessLavaFog(float constant,
                              @Local(argsOnly = true) Camera camera) {
        int i = 0;
        if (camera.entity() instanceof Player entity) {
            for (ItemStack item : JabsFixedEnchanting.getArmor(entity)) {
                i += JabsFixedEnchantmentHelper.enchantLevel(item, "fire_protection");
            }
        }
        return 2.5f + 0.25f*Math.min(2*i,25);
    }

}
