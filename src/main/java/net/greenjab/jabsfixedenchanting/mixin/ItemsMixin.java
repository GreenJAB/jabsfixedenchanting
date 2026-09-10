package net.greenjab.jabsfixedenchanting.mixin;

import net.greenjab.jabsfixedenchanting.registry.ModTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(Items.class)
public abstract class ItemsMixin {

    @ModifyArg(method="<clinit>", at = @At( value = "INVOKE", target = "Lnet/minecraft/world/item/Items;registerItem(Lnet/minecraft/resources/ResourceKey;Ljava/util/function/Function;Lnet/minecraft/world/item/Item$Properties;)Lnet/minecraft/world/item/Item;", ordinal = 0 ), slice = @Slice(from =
    @At(value = "FIELD", target = "Lnet/minecraft/references/ItemIds;TRIDENT:Lnet/minecraft/resources/ResourceKey;", opcode = Opcodes.GETSTATIC), to =
    @At(value = "FIELD",target = "Lnet/minecraft/world/item/Items;TRIDENT:Lnet/minecraft/world/item/Item;", opcode = Opcodes.PUTSTATIC)), index = 2)
    private static Item.Properties repairableTrident(Item.Properties properties) {
        return properties.repairable(Items.PRISMARINE_SHARD);}

    //As string is initilized after bow, need to pass itemtag of just string rather than string itself
    @ModifyArg(method="<clinit>", at = @At( value = "INVOKE", target = "Lnet/minecraft/world/item/Items;registerItem(Lnet/minecraft/resources/ResourceKey;Ljava/util/function/Function;Lnet/minecraft/world/item/Item$Properties;)Lnet/minecraft/world/item/Item;", ordinal = 0 ), slice = @Slice(from =
    @At(value = "FIELD", target = "Lnet/minecraft/references/ItemIds;BOW:Lnet/minecraft/resources/ResourceKey;", opcode = Opcodes.GETSTATIC), to =
    @At(value = "FIELD",target = "Lnet/minecraft/world/item/Items;BOW:Lnet/minecraft/world/item/Item;", opcode = Opcodes.PUTSTATIC)), index = 2)
    private static Item.Properties repairableBow(Item.Properties properties) {
        return properties.repairable(ModTags.STRING);}

    @ModifyArg(method="<clinit>", at = @At( value = "INVOKE", target = "Lnet/minecraft/world/item/Items;registerItem(Lnet/minecraft/resources/ResourceKey;Ljava/util/function/Function;Lnet/minecraft/world/item/Item$Properties;)Lnet/minecraft/world/item/Item;", ordinal = 0 ), slice = @Slice(from =
    @At(value = "FIELD", target = "Lnet/minecraft/references/ItemIds;CROSSBOW:Lnet/minecraft/resources/ResourceKey;", opcode = Opcodes.GETSTATIC), to =
    @At(value = "FIELD",target = "Lnet/minecraft/world/item/Items;CROSSBOW:Lnet/minecraft/world/item/Item;", opcode = Opcodes.PUTSTATIC)), index = 2)
    private static Item.Properties repairableCrossBow(Item.Properties properties) {
        return properties.repairable(ModTags.STRING);}

    @ModifyArg(method="<clinit>", at = @At( value = "INVOKE", target = "Lnet/minecraft/world/item/Items;registerItem(Lnet/minecraft/resources/ResourceKey;Ljava/util/function/Function;Lnet/minecraft/world/item/Item$Properties;)Lnet/minecraft/world/item/Item;", ordinal = 0 ), slice = @Slice(from =
    @At(value = "FIELD", target = "Lnet/minecraft/references/ItemIds;FISHING_ROD:Lnet/minecraft/resources/ResourceKey;", opcode = Opcodes.GETSTATIC), to =
    @At(value = "FIELD",target = "Lnet/minecraft/world/item/Items;FISHING_ROD:Lnet/minecraft/world/item/Item;", opcode = Opcodes.PUTSTATIC)), index = 2)
    private static Item.Properties repairableFishingRod(Item.Properties properties) {
        return properties.repairable(ModTags.STRING);}

}
