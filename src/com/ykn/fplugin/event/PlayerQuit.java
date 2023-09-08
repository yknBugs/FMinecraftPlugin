package com.ykn.fplugin.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.ykn.fplugin.data.PlayerData;
import com.ykn.fplugin.data.ServerData;
import com.ykn.fplugin.util.Util;

public class PlayerQuit implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent playerQuitEvent) {
        setPlayerQuitTick(playerQuitEvent.getPlayer());
    }

    private void setPlayerQuitTick(Player player) {
        PlayerData playerData = Util.getPlayerData(player);
        playerData.leaveTick = ServerData.tick;
        playerData.afk = 0;
        // playerData.persistentMessages.clear();
        playerData.persistentMessage = null;
    }
    
}
