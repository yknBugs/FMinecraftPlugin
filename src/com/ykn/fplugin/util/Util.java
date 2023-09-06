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
     * 往有权限的玩家和指定的玩家发送聊天消息
     * @param target 指定的会收到消息的玩家，无论是否有权限
     * @param permission 拥有该权限就能收到消息的玩家
     * @param message 要发送的消息的内容
     */
    public static void sendTextMessageToLimit(Player target, String permission, String message) {
        Collection<? extends Player> players = Bukkit.getServer().getOnlinePlayers();
        for (Player player : players) {
            if (player == target || player.hasPermission(permission)) {
                player.sendMessage(message);
            }
        }
    }

    /**
     * 往有权限的玩家和指定的玩家发送聊天消息
     * @param target 指定的会收到消息的所有玩家，无论是否有权限
     * @param permission 拥有该权限就能收到消息的玩家
     * @param message 要发送的消息的内容
     */
    public static void sendTextMessageToLimit(Collection<Player> target, String permission, String message) {
        Collection<? extends Player> players = Bukkit.getServer().getOnlinePlayers();
        for (Player player : players) {
            if (player.hasPermission(permission) || target.contains(player)) {
                player.sendMessage(message);
            }
        }
    }

    /**
     * 往拥有指定权限的玩家发送聊天消息 <p>
     * 对于玩家 target，需要有 targetPermission 权限才能收到消息 <p>
     * 对于 target 以外的所有在线的玩家，需要有 otherPermission 权限才能收到消息
     * @param target 指定的会收到消息的玩家，需要有特殊权限
     * @param targetPermission 拥有该权限的指定玩家可以收到消息
     * @param otherPermission 拥有该权限的任何玩家都能收到消息
     * @param message 要发送的消息的内容
     */
    public static void sendTextMessageToLimit(Player target, String targetPermission, String otherPermission, String message) {
        Collection<? extends Player> players = Bukkit.getServer().getOnlinePlayers();
        for (Player player : players) {
            if (player.hasPermission(otherPermission) || (player == target && player.hasPermission(targetPermission))) {
                player.sendMessage(message);
            }
        }
    }

    /**
     * 往拥有指定权限的玩家发送聊天消息 <p>
     * 对于在列表 target 中的玩家，需要有 targetPermission 权限才能收到消息 <p>
     * 对于在列表 target 以外的所有在线的玩家，需要有 otherPermission 权限才能收到消息
     * @param target 指定的会收到消息的所有玩家，需要有特殊权限
     * @param targetPermission 拥有该权限的指定玩家可以收到消息
     * @param otherPermission 拥有该权限的任何玩家都能收到消息
     * @param message 要发送的消息的内容
     */
    public static void sendTextMessageToLimit(Collection<Player> target, String targetPermission, String otherPermission, String message) {
        Collection<? extends Player> players = Bukkit.getServer().getOnlinePlayers();
        for (Player player : players) {
            if (player.hasPermission(otherPermission) || (target.contains(player) && player.hasPermission(targetPermission))) {
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
