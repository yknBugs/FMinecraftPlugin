package com.ykn.fplugin.data;

import java.util.UUID;

import org.bukkit.block.Biome;

import com.ykn.fplugin.message.PersistentMessage;

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
     * 玩家挂机时长
     */
    public int afk;

    /**
     * 玩家上次所在的生物群系
     */
    public Biome lastBiome;

    /**
     * 显示生物群系改变消息的延迟倒计时
     */
    public int biomeMessageDelay;

    /**
     * 正在显示在 ActionBar 上的消息集合
     */
    // Java 版本 ActionBar 不能换行
    // public List<PersistentMessage> persistentMessages;
    public PersistentMessage persistentMessage;

    public PlayerData() {
        this.uuid = null;
        this.joinTick = 0;
        this.leaveTick = 0;
        this.afk = 0;
        this.lastBiome = Biome.THE_VOID;
        this.biomeMessageDelay = 0;
        this.persistentMessage = null;
    }
}
