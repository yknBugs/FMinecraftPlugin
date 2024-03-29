package com.ykn.fplugin.event;

import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.ykn.fplugin.config.Config;
import com.ykn.fplugin.data.PlayerData;
import com.ykn.fplugin.data.ServerData;
import com.ykn.fplugin.language.ActionbarLanguage;
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
            PlayerData playerData = Util.getPlayerData(player);
            this.sendAfkMessageToPlayer(player, playerData);
            this.sendBiomeMessageToPlayer(player, playerData);
            this.showActionbarMessageToPlayer(player, playerData);
        }

    }

    private void sendAfkMessageToPlayer(Player player, PlayerData playerData) {
        if (Config.isAfkActive() && !player.hasPermission("fplugin.afk.bypass")) {
            playerData.afk++;
        }

        if (Config.isAfkInformActive() && playerData.afk > Config.getAfkInformTick()) {
            String message = TextLanguage.getAfkInformMessage(player, playerData.afk);
            PlaceholderMessage placeholderMessage = new PlaceholderMessage("fplugin.afk.inform", message);
            PersistentMessage persistentMessage = new PersistentMessage(0, Config.getAfkInformDuration(), Config.getAfkInformPriority(), placeholderMessage);
            Util.replaceActionbarPersistentMessage(playerData, persistentMessage);
        }

        if (Config.isAfkBroadcastActive() && playerData.afk == Config.getAfkBroadcastTick()) {
            String message = TextLanguage.getAfkBroadcastMessage(player, playerData.afk);
            Language.log(3, message);
            message = Config.getPrefix() + message;
            Util.sendTextMessageToLimit(player, "fplugin.afk.selfafk", "fplugin.afk.allafk", message);
        }
    }

    private void sendBiomeMessageToPlayer(Player player, PlayerData playerData) {
        if (!Config.isBiomeMessageActive()) {
            return;
        }

        if (playerData.biomeMessageDelay == 0 && player.getLocation().getBlock().getBiome() != playerData.lastBiome) {
            playerData.lastBiome = player.getLocation().getBlock().getBiome();
            String selfMessage = ActionbarLanguage.getSelfBiomeMessage(player);
            PlaceholderMessage selfPhMessage = new PlaceholderMessage("fplugin.biomemessage.self", selfMessage);
            PersistentMessage selfPMessage = new PersistentMessage(0, Config.getBiomeMessageDuration(), Config.getBiomeMessagePriority(), selfPhMessage);
            String otherMessage = ActionbarLanguage.getOtherBiomeMessage(player);
            PlaceholderMessage otherPhMessage = new PlaceholderMessage("fplugin.biomemessage.other", otherMessage);
            PersistentMessage otherPMessage = new PersistentMessage(0, Config.getBiomeMessageDuration(), Config.getBiomeMessagePriority(), otherPhMessage);
            Util.sendUniqueActionbarMessage(player, selfPMessage, otherPMessage);
        }

        playerData.biomeMessageDelay--;
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