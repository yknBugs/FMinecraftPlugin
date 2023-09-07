package com.ykn.fplugin.config;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.ykn.fplugin.FPlugin;

public class Config {
    
    public static final String version = "0.0.5";
    public static FPlugin thisPlugin = null;

    public static boolean getIsDebug() {
        return thisPlugin.getConfig().getBoolean("debug", false);
    }

    public static String getPrefix() {
        return thisPlugin.getConfig().getString("prefix", "\u00a7f").replace('&', '\u00a7');
    }

    public static boolean isNoCustom() {
        return thisPlugin.getConfig().getBoolean("nocustom", false);
    }

    public static boolean isIgnoreEntityName() {
        return thisPlugin.getConfig().getBoolean("ignoreentityname", false);
    }

    public static boolean isActiveShootMessage() {
        return thisPlugin.getConfig().getBoolean("shootmessage.active", true);
    }

    public static int getShootMessagePriority() {
        return thisPlugin.getConfig().getInt("shootmessage.priority", 0);
    }

    public static int getShootMessageDuration() {
        return thisPlugin.getConfig().getInt("shootmessage.duration", 100);
    }

    public static String formatTime(LocalDateTime time) {
        String result = thisPlugin.getConfig().getString("timeformat", "yyyy年M月d日 H:mm:ss");
        return time.format(DateTimeFormatter.ofPattern(result));
    }

    public static boolean isActiveEntityDeathMessage() {
        return thisPlugin.getConfig().getBoolean("deathmessage.active", true);
    }

    public static int getEntityDeathMessagePriority() {
        return thisPlugin.getConfig().getInt("deathmessage.priority", 0);
    }

    public static int getEntityDeathMessageDuration() {
        return thisPlugin.getConfig().getInt("deathmessage.duration", 200);
    }

    public static int getHealthRegardedAsBoss() {
        return thisPlugin.getConfig().getInt("deathmessage.health", 150);
    }

    public static boolean isShowBossDeathMessage() {
        return thisPlugin.getConfig().getBoolean("deathmessage.boss", true);
    }

    public static boolean isShowRevengeDeathMessage() {
        return thisPlugin.getConfig().getBoolean("deathmessage.revenge", true);
    }

    public static boolean isShowAllDeathMessage() {
        return thisPlugin.getConfig().getBoolean("deathmessage.all", true);
    }

    public static boolean isShowPlayerDeathMessage() {
        return thisPlugin.getConfig().getBoolean("deathmessage.player", true);
    }

    public static boolean isShowRenamedDeathMessage() {
        return thisPlugin.getConfig().getBoolean("deathmessage.renamed", true);
    }

    public static boolean isAfkActive() {
        return thisPlugin.getConfig().getBoolean("afk.active", true);
    }

    public static boolean isAfkInformActive() {
        return thisPlugin.getConfig().getBoolean("afk.inform", true);
    }

    public static int getAfkInformTick() {
        return thisPlugin.getConfig().getInt("afk.informtick", 1200);
    }

    public static int getAfkInformPriority() {
        return thisPlugin.getConfig().getInt("afk.priority", -1000);
    }

    public static int getAfkInformDuration() {
        return thisPlugin.getConfig().getInt("afk.duration", 60);
    }

    public static boolean isAfkBroadcastActive() {
        return thisPlugin.getConfig().getBoolean("afk.broadcast", true);
    }

    public static int getAfkBroadcastTick() {
        return thisPlugin.getConfig().getInt("afk.broadcasttick", 6000);
    }

    public static boolean isAfkBackActive() {
        return thisPlugin.getConfig().getBoolean("afk.back", true);
    }
}
