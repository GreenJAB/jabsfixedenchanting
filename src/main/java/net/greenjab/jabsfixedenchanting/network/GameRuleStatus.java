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

    public GameRuleStatus(){
    }

    public void updateRules(GameRules rules) {
        this.combine_items = rules.get(GameRuleRegistry.COMBINE_ENCHANTED_ITEMS);
        this.mending_on_op = rules.get(GameRuleRegistry.MENDING_ON_OP_ITEMS);
        this.grindstone_damage = rules.get(GameRuleRegistry.GRINDSTONE_DAMAGES_ITEM);
    }

    void toPacket(FriendlyByteBuf buf) {
        buf.writeBoolean(combine_items);
        buf.writeBoolean(mending_on_op);
        buf.writeBoolean(grindstone_damage);
    }

    static GameRuleStatus fromPacket(FriendlyByteBuf buf) {
        GameRuleStatus p = new GameRuleStatus();
        p.combine_items = buf.readBoolean();
        p.mending_on_op = buf.readBoolean();
        p.grindstone_damage = buf.readBoolean();
        return p;
    }

    public static void sendData(MinecraftServer server) {
        JabsFixedEnchanting.gameRules.updateRules(server.getGameRules());
        GameRulePayload payload = new GameRulePayload(JabsFixedEnchanting.gameRules);
        server.getPlayerList().getPlayers().forEach(player -> ServerPlayNetworking.send(player, payload));
    }
}
