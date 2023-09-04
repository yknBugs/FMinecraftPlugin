package com.ykn.fplugin.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.ykn.fplugin.data.PlayerData;
import com.ykn.fplugin.data.ServerData;

public class PlayerJoin implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent playerJoinEvent) {
        initPlayerData(playerJoinEvent.getPlayer());
    }

    private void initPlayerData(Player player) {
        if (ServerData.playerdata.containsKey(player.getUniqueId())) {
            ServerData.playerdata.get(player.getUniqueId()).joinTick = ServerData.tick;
        } else {
            PlayerData playerData = new PlayerData();
            playerData.uuid = player.getUniqueId();
            playerData.joinTick = ServerData.tick;
            ServerData.playerdata.put(player.getUniqueId(), playerData);
        }
    }

}
