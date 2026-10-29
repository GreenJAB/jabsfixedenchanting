package net.greenjab.jabsfixedenchanting.network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.greenjab.jabsfixedenchanting.JabsFixedEnchanting;
import net.greenjab.jabsfixedenchanting.registry.registries.GameRuleRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.gamerules.GameRules;

public class GameRuleStatus {
    public boolean combine_items;
    public boolean mending_on_op;
    public boolean grindstone_damage;
    public boolean tomes_increase_level;
    public boolean one_to_one_lapis_cost;

    public GameRuleStatus(){
    }

    public void updateRules(GameRules rules) {
        this.combine_items = rules.get(GameRuleRegistry.COMBINE_ENCHANTED_ITEMS);
        this.mending_on_op = rules.get(GameRuleRegistry.MENDING_ON_OP_ITEMS);
        this.grindstone_damage = rules.get(GameRuleRegistry.GRINDSTONE_DAMAGES_ITEM);
        this.tomes_increase_level = rules.get(GameRuleRegistry.TOMES_INCREASE_LEVEL);
        this.one_to_one_lapis_cost = rules.get(GameRuleRegistry.ONE_TO_ONE_LAPIS_COST);
    }

    void toPacket(FriendlyByteBuf buf) {
        buf.writeBoolean(combine_items);
        buf.writeBoolean(mending_on_op);
        buf.writeBoolean(grindstone_damage);
        buf.writeBoolean(tomes_increase_level);
        buf.writeBoolean(one_to_one_lapis_cost);
    }

    static GameRuleStatus fromPacket(FriendlyByteBuf buf) {
        GameRuleStatus p = new GameRuleStatus();
        p.combine_items = buf.readBoolean();
        p.mending_on_op = buf.readBoolean();
        p.grindstone_damage = buf.readBoolean();
        p.tomes_increase_level = buf.readBoolean();
        p.one_to_one_lapis_cost = buf.readBoolean();
        return p;
    }

    public static void sendData(MinecraftServer server) {
        JabsFixedEnchanting.gameRules.updateRules(server.getGameRules());
        GameRulePayload payload = new GameRulePayload(JabsFixedEnchanting.gameRules);
        server.getPlayerList().getPlayers().forEach(player -> ServerPlayNetworking.send(player, payload));
    }
}
