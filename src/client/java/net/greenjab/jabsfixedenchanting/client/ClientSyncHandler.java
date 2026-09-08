package net.greenjab.jabsfixedenchanting.client;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.greenjab.jabsfixedenchanting.JabsFixedEnchanting;
import net.greenjab.jabsfixedenchanting.network.GameRulePayload;

/** Credit: Nettakrim, Squeek502, Bawnorton */
public class ClientSyncHandler {
    public static void init() {
        ClientPlayNetworking.registerGlobalReceiver(GameRulePayload.PACKET_ID, ClientSyncHandler::gamerule);
    }

    private static void gamerule(GameRulePayload payload, ClientPlayNetworking.Context context) {
        context.client().execute(()-> JabsFixedEnchanting.gameRules = payload.rules());
    }
}
