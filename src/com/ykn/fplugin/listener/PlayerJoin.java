package com.ykn.fplugin.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.ykn.fplugin.data.GlobalData;
import com.ykn.fplugin.function.PlayerRelatedInfo;

public class PlayerJoin implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        //添加玩家数据
        Player player = event.getPlayer();
        GlobalData.addPlayer(player);
        PlayerRelatedInfo.onJoin(player);
    }
}
