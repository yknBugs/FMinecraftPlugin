package com.ykn.fplugin.util;

import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.ykn.fplugin.config.Config;
import com.ykn.fplugin.data.PlayerData;
import com.ykn.fplugin.data.ServerData;
import com.ykn.fplugin.language.ConsoleLanguage;
import com.ykn.fplugin.message.PersistentMessage;

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
     * 往有权限的玩家发送 PersistentActionbar 消息
     * @param message 要发送的消息的内容
     */
    public static void sendPersistentMessageToLimit(PersistentMessage message) {
        Collection<? extends Player> players = Bukkit.getServer().getOnlinePlayers();
        for (Player player : players) {
            replaceActionbarPersistentMessage(player, message);
        }
    }

    /**
     * 往有权限的玩家发送聊天消息
     * @param permission 权限
     * @param message 要发送的消息的内容
     */
    public static void sendTextMessageToLimit(String permission, String message) {
        Collection<? extends Player> players = Bukkit.getServer().getOnlinePlayers();
        for (Player player : players) {
            if (player.hasPermission(permission)) {
                player.sendMessage(message);
            }
        }
    }

    /**
     * 获取实体的名称，被重命名后的实体会返回重命名后的名称
     * @param entity 要获取名称的实体
     * @return 实体的名称
     */
    public static String getEntityName(Entity entity) {
        if (entity.getCustomName() == null || Config.isIgnoreEntityName()) {
            return entity.getName();
        } 
        String entityName = entity.getCustomName();
        if (Config.isNoCustom()) {
            entityName = entityName.replace('[', '(').replace(']', ')');
        }
        return entityName;
    }

    /**
     * 尝试替换掉现在正在显示在 Actionbar 上的持续性消息
     * @param player 玩家
     * @param persistentMessage 替换后的消息
     */
    public static void replaceActionbarPersistentMessage(Player player, PersistentMessage persistentMessage) {
        if (!player.hasPermission(persistentMessage.placeholderMessage.permission)) {
            return;
        }
        PlayerData playerData = ServerData.playerdata.get(player.getUniqueId());
        if (playerData == null) {
            ConsoleLanguage.sendMissingPlayerDataWarning(player);
            playerData = new PlayerData();
            playerData.uuid = player.getUniqueId();
            playerData.joinTick = ServerData.tick;
            ServerData.playerdata.put(player.getUniqueId(), playerData);
        }
        if (playerData.persistentMessage == null || playerData.persistentMessage.priority <= persistentMessage.priority) {
            playerData.persistentMessage = persistentMessage;
        }   
    }

}
