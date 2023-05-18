package com.ykn.fplugin.function;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.ykn.fplugin.config.GlobalConfig;
import com.ykn.fplugin.config.custom.LocationConfig;
import com.ykn.fplugin.config.function.PlayerDeathConfig;
import com.ykn.fplugin.data.GlobalData;
import com.ykn.fplugin.data.PlayerData;
import com.ykn.fplugin.util.BroadcastInfo;

public class PlayerRelatedInfo {

    public static void onDeath(PlayerDeathEvent event) {
        //保存死亡坐标
        Player player = event.getEntity();
        PlayerData playerData = GlobalData.getPlayerData(player);
        playerData.addDeathLocation(event.getDeathMessage());

        //执行死亡消息广播
        if (PlayerDeathConfig.active()) {
            List<String> messageList = PlayerDeathConfig.meesageList();
            Collection<? extends Player> players = Bukkit.getOnlinePlayers();
            for (String message : messageList) {
                message = message.replaceAll("\\[p\\]", player.getName());
                message = message.replaceAll("\\[l\\]", LocationConfig.formatLocation(player.getLocation()));
                message = message.replaceAll("\\[t\\]", LocationConfig.formatTime(LocalDateTime.now()));
                
                //执行指令
                if (message.charAt(0) == '/') {
                    BroadcastInfo.executePlaceholderCommand(player, message.substring(1));
                    continue;
                }

                //向有权限的玩家广播消息
                for (Player p : players) {
                    if (p == player) {
                        if (player.hasPermission("fplugin.playerdeath.self")) {
                            BroadcastInfo.sendMessage(player, message);

                        }
                    } else if (p.hasPermission("fplugin.playerdeath.others")) {
                        BroadcastInfo.sendMessage(p, player, message);
                    }
                }

                GlobalConfig.getLogger().info(message);
            }
        }
    }

    public static void onJoin(Player player) {
        PlayerData playerData = GlobalData.getPlayerData(player);
        if (playerData == null) {
            GlobalConfig.getLogger().warning("Missing Player Data: " + player.getName());
        } else {
            GlobalConfig.debug("Player login Location has been changed: " + player.getName());
            playerData.loginLocation = player.getLocation();
            playerData.loginTime = LocalDateTime.now();
        }
    }
    
}
