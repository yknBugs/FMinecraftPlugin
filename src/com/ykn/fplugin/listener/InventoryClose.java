package com.ykn.fplugin.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import com.ykn.fplugin.config.GlobalConfig;
import com.ykn.fplugin.data.GlobalData;
import com.ykn.fplugin.data.PlayerData;

public class InventoryClose implements Listener {
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getPlayer() instanceof Player) {
            Player player = (Player) event.getPlayer();
            PlayerData playerData = GlobalData.getPlayerData(player);

            //关闭物品栏
            if (playerData == null) {
                GlobalConfig.getLogger().warning("Missing Player Data: " + player.getName());
            } else {
                //玩家数据修改
            }   

        }
    }    
}
