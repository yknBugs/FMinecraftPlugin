package com.ykn.fplugin.util;

import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class Util {

    /**
     * 往有权限的玩家发送 Actionbar 消息
     * @param permission 权限
     * @param message 要发送的消息的内容
     */
    public static void sendActionbarMessageToLimit(String permission, String message) {
        Collection<? extends Player> players = Bukkit.getServer().getOnlinePlayers();
        for (Player player : players) {
            if (player.hasPermission(permission)) {
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
            }
        }
    }

    
    /**
     * 获取实体的名称，被重命名后的实体会返回重命名后的名称
     * @param entity 要获取名称的实体
     * @return 实体的名称
     */
    public static String getEntityName(Entity entity) {
        if (entity.getCustomName() == null) {
            return entity.getName();
        } else {
            return entity.getCustomName();
        }
    }

}
