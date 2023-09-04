package com.ykn.fplugin.event;

import java.util.Collection;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.ykn.fplugin.config.Config;
import com.ykn.fplugin.data.PlayerData;
import com.ykn.fplugin.data.ServerData;
import com.ykn.fplugin.message.PersistentMessage;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class TickChange extends BukkitRunnable {

    @Override
    public void run() {
        ServerData.tick++;

        Collection<? extends Player> players = Bukkit.getServer().getOnlinePlayers();
        for (Player player : players) {
            PlayerData playerData = ServerData.playerdata.get(player.getUniqueId());
            this.showActionbarMessageToPlayer(player, playerData);
        }

    }

    private void showActionbarMessageToPlayer(Player player, PlayerData playerData) {
        Iterator<PersistentMessage> iterator = playerData.persistentMessages.iterator();
        String message = "";
        while (iterator.hasNext()) {
            PersistentMessage persistentMessage = iterator.next();
            String permission = persistentMessage.placeholderMessage.permission;
            if (persistentMessage.delay <= 0 && persistentMessage.tick <= 0) {
                iterator.remove();
                continue;
            }

            if (permission != null && !player.hasPermission(permission)) {
                continue;
            }

            message = message + Config.getPrefix() + persistentMessage.placeholderMessage.formatPlaceholders() + "\n";
            persistentMessage.tick();
        }

        if (!playerData.persistentMessages.isEmpty()) {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
        }
    }
    
}
