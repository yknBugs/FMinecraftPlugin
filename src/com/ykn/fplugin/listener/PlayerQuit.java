package com.ykn.fplugin.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.ykn.fplugin.config.GlobalConfig;
import com.ykn.fplugin.data.GlobalData;
import com.ykn.fplugin.data.PlayerData;

public class PlayerQuit implements Listener {
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        PlayerData playerData = GlobalData.getPlayerData(player);

        //关闭物品栏
        if (playerData == null) {
            GlobalConfig.getLogger().warning("Missing Player Data: " + player.getName());
        } else {
            //玩家数据修改
        }  
    }
}
