package com.ykn.fplugin.event;

import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.ykn.fplugin.config.Config;
import com.ykn.fplugin.data.PlayerData;
import com.ykn.fplugin.data.ServerData;
import com.ykn.fplugin.language.ConsoleLanguage;
import com.ykn.fplugin.language.Language;
import com.ykn.fplugin.language.TextLanguage;
import com.ykn.fplugin.message.PersistentMessage;
import com.ykn.fplugin.message.PlaceholderMessage;
import com.ykn.fplugin.util.Util;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class TickChange extends BukkitRunnable {

    @Override
    public void run() {
        ServerData.tick++;

        Collection<? extends Player> players = Bukkit.getServer().getOnlinePlayers();
        for (Player player : players) {
            PlayerData playerData = this.getPlayerData(player);
            this.sendAfkMessageToPlayer(player, playerData);
            this.showActionbarMessageToPlayer(player, playerData);
        }

    }

    private PlayerData getPlayerData(Player player) {
        PlayerData playerData = ServerData.playerdata.get(player.getUniqueId());
        if (playerData == null) {
            ConsoleLanguage.sendMissingPlayerDataWarning(player);
            playerData = new PlayerData();
            playerData.uuid = player.getUniqueId();
            playerData.joinTick = ServerData.tick;
            ServerData.playerdata.put(player.getUniqueId(), playerData);
        }
        return playerData;
    }

    private void sendAfkMessageToPlayer(Player player, PlayerData playerData) {
        if (Config.isAfkActive() && !player.hasPermission("fplugin.afk.bypass")) {
            playerData.afk++;
        }

        if (Config.isAfkInformActive() && playerData.afk > Config.getAfkInformTick()) {
            String message = TextLanguage.getAfkInformMessage(player, playerData.afk);
            PlaceholderMessage placeholderMessage = new PlaceholderMessage("fplugin.afk.inform", message);
            PersistentMessage persistentMessage = new PersistentMessage(0, 200, Config.getAfkInformPriority(), placeholderMessage);
            Util.replaceActionbarPersistentMessage(playerData, persistentMessage);
        }

        if (Config.isAfkBroadcastActive() && playerData.afk == Config.getAfkBroadcastTick()) {
            String message = TextLanguage.getAfkBroadcastMessage(player, playerData.afk);
            Language.log(3, message);
            message = Config.getPrefix() + message;
            Util.sendTextMessageToLimit(player, "fplugin.afk.selfafk", "fplugin.afk.allafk", message);
        }
    }

    private void showActionbarMessageToPlayer(Player player, PlayerData playerData) {
        if (playerData.persistentMessage == null) {
            return;
        }
        String permission = playerData.persistentMessage.placeholderMessage.permission;
        if (playerData.persistentMessage.delay <= 0 && playerData.persistentMessage.time <= 0) {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(" "));
            playerData.persistentMessage = null;
            return;
        }

        if (permission != null && !player.hasPermission(permission)) {
            playerData.persistentMessage = null;
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(" "));
            return;
        }

        playerData.persistentMessage.tick();
        String message = Config.getPrefix() + playerData.persistentMessage.placeholderMessage.formatPlaceholders();
        if (!playerData.persistentMessage.placeholderMessage.isValid) {
            playerData.persistentMessage = null;
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(" "));
            return;
        }
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
    }
    
}