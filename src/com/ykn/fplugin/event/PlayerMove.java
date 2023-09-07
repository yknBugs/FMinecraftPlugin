package com.ykn.fplugin.event;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.ykn.fplugin.config.Config;
import com.ykn.fplugin.data.PlayerData;
import com.ykn.fplugin.data.ServerData;
import com.ykn.fplugin.language.ConsoleLanguage;
import com.ykn.fplugin.language.Language;
import com.ykn.fplugin.language.TextLanguage;
import com.ykn.fplugin.util.Util;

public class PlayerMove implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent playerMoveEvent) {

        if (Config.isAfkActive() && isChangeSightDirection(playerMoveEvent.getFrom(), playerMoveEvent.getTo())) {
            resetPlayerAfkTimer(playerMoveEvent.getPlayer());
        }

    }

    public void resetPlayerAfkTimer(Player player) {
        PlayerData playerData = ServerData.playerdata.get(player.getUniqueId());
        if (playerData == null) {
            ConsoleLanguage.sendMissingPlayerDataWarning(player);
            playerData = new PlayerData();
            playerData.uuid = player.getUniqueId();
            playerData.joinTick = ServerData.tick;
            ServerData.playerdata.put(player.getUniqueId(), playerData);
        }

        if (Config.isAfkBackActive() && playerData.afk >= Config.getAfkBroadcastTick()) {
            String message = TextLanguage.getAfkBackMessage(player, playerData.afk);
            Language.log(3, message);
            message = Config.getPrefix() + message;
            Util.sendTextMessageToLimit(player, "fplugin.afk.selfback", "fplugin.afk.allback", message);
        }

        playerData.afk = 0;
    }

    private boolean isChangeSightDirection(Location loc1, Location loc2) {
        if (loc1.getPitch() != loc2.getPitch()) {
            return true;
        }

        if (loc1.getYaw() != loc2.getYaw()) {
            return true;
        }

        return false;
    }
    
}
