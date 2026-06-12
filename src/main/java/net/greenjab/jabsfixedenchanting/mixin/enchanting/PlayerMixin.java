package net.greenjab.jabsfixedenchanting.mixin.enchanting;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.gamerules.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerMixin {

    @Inject(method = "getBaseExperienceReward", at = @At("HEAD"), cancellable = true)
    private void halfLevelsOnDeath(CallbackInfoReturnable<Integer> cir, @Local(argsOnly = true) ServerLevel level) {
        Player player = (Player) (Object)this;
        if (!level.getGameRules().get(GameRules.KEEP_INVENTORY) && !player.isSpectator()) {
            int i = 0;
            for (int eLevel = 0; eLevel < player.experienceLevel / 2; eLevel++) {
                i +=getNextLevelExperience(eLevel);
            }
            if (player.experienceLevel%2==1) i +=getNextLevelExperience(player.experienceLevel/2)/2;
            i+= (int) (player.experienceProgress/2);
            cir.setReturnValue(i);
        } else {
            cir.setReturnValue(0);
        }
    }

    @Unique
    public int getNextLevelExperience(int currentLevel) {
        if (currentLevel >= 30) {
            return 112 + (currentLevel - 30) * 9;
        } else {
            return currentLevel >= 15 ? 37 + (currentLevel - 15) * 5 : 7 + currentLevel * 2;
        }
    }
}
