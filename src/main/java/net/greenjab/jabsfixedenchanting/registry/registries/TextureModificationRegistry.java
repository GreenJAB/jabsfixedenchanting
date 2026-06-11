package net.greenjab.jabsfixedenchanting.registry.registries;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import net.greenjab.jabsfixedenchanting.JabsFixedEnchanting;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.equipment.ArmorType;
import net.ramixin.mixson.Mixson;
import net.ramixin.mixson.enums.ErrorPolicy;
import net.ramixin.mixson.enums.Lifetime;
import net.ramixin.mixson.util.Index;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class TextureModificationRegistry {

    public static void registerTextureModifications() {
        System.out.println("register TextureModifications");
        Arrays.stream(ArmorType.values()).filter(type -> type != ArmorType.BODY)
                .forEach(type -> registerItemModelDamageModification(type.getName(), Identifier.withDefaultNamespace("netherite")));

    }

    private static void registerItemModelDamageModification(String armorPieceType, Identifier armorMaterial) {
        Mixson.registerEvent(
                2000,
                Lifetime.PERSISTENT,
                ErrorPolicy.LOG,
                JabsFixedEnchanting.id("add_damage_variant_to_" + armorMaterial.getPath() + "_" + armorPieceType).toString(),
                index -> index.idEquals(new Index(Identifier.fromNamespaceAndPath(armorMaterial.getNamespace(), "items/" + armorMaterial.getPath() + "_" + armorPieceType))),
                context -> {
                    JsonObject root = context.getFile().getAsJsonObject();
                    if (root == null || !root.has("model")) return;

                    JsonObject model = root.getAsJsonObject("model");
                    if (model == null) return;

                    if (!model.has("type") || !model.get("type").getAsString().equals("minecraft:select")) return;

                    JsonArray cases = model.getAsJsonArray("cases");
                    if (cases == null || cases.isEmpty()) return;

                    List<Pair<Float, String>> damages = List.of(Pair.of(0f, ""),Pair.of(0.997f, "_broken"));

                    for (JsonElement e : cases.asList()) {
                        JsonObject e2 = e.getAsJsonObject();
                        for (Map.Entry<String, JsonElement> entry : e2.entrySet()) {
                            if (entry.getValue().isJsonObject()) {
                                JsonObject A = entry.getValue().getAsJsonObject();
                                if (A != null && A.has("type") || A.get("type").getAsString().equals("minecraft:model")) {
                                    String B = A.get("model").getAsString();
                                    A.remove("type");
                                    A.remove("model");
                                    A.addProperty("type", "minecraft:range_dispatch");
                                    A.addProperty("property", "minecraft:damage");
                                    JsonArray array = new JsonArray();
                                    for (Pair<Float, String> damage : damages) {
                                        JsonObject threshold = new JsonObject();
                                        threshold.addProperty("threshold", damage.getFirst());
                                        JsonObject damageState = new JsonObject();
                                        damageState.addProperty("type", "minecraft:model");
                                        damageState.addProperty("model", B + damage.getSecond());
                                        threshold.add("model", damageState);
                                        array.add(threshold);
                                    }
                                    A.add("entries", array);
                                }
                            }
                        }
                    }

                    JsonObject fallback = model.getAsJsonObject("fallback");
                    if (fallback != null && !fallback.isEmpty()) {
                        if (fallback.has("type") || fallback.get("type").getAsString().equals("minecraft:model")) {
                            String B = fallback.get("model").getAsString();
                            fallback.remove("type");
                            fallback.remove("model");
                            fallback.addProperty("type", "minecraft:range_dispatch");
                            fallback.addProperty("property", "minecraft:damage");
                            JsonArray array = new JsonArray();
                            for (Pair<Float, String> damage : damages) {
                                JsonObject threshold = new JsonObject();
                                threshold.addProperty("threshold", damage.getFirst());
                                JsonObject damageState = new JsonObject();
                                damageState.addProperty("type", "minecraft:model");
                                damageState.addProperty("model", B + damage.getSecond());
                                threshold.add("model", damageState);
                                array.add(threshold);
                            }
                            fallback.add("entries", array);
                        }
                    }
                }
        );
    }

}
