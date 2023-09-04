package com.ykn.fplugin.data;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.ykn.fplugin.message.PersistentMessage;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class PlayerData {

    /**
     * 玩家的 UUID
     */
    public UUID uuid;

    /**
     * 玩家加入服务器的时间
     */
    public int joinTick;

    /**
     * 玩家上次离开服务器的时间
     */
    public int leaveTick;

    /**
     * 正在显示在 ActionBar 上的消息集合
     */
    public List<PersistentMessage> persistentMessages;

    public PlayerData() {
        this.uuid = null;
        this.joinTick = 0;
        this.leaveTick = 0;
        this.persistentMessages = new LinkedList<PersistentMessage>();
    }

    /**
     * 帧事件
     */
    public void tick() {
        // 显示 ActionBar 消息
        for (PersistentMessage persistentMessage : this.persistentMessages) {
            if (this.uuid == null) {
                return;
            }

            Player player = Bukkit.getPlayer(this.uuid);
            if (!player.isOnline()) {
                return;
            }
            
            String permission = persistentMessage.placeholderMessage.permission;
            if (permission != null && !player.hasPermission(permission)) {
                return;
            }

            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(persistentMessage.placeholderMessage.formatPlaceholders()));

            persistentMessage.tick();

            if (persistentMessage.delay <= 0 && persistentMessage.tick <= 0) {
                this.persistentMessages.remove(persistentMessage);
                break;
            }
        }


    }
    
}
