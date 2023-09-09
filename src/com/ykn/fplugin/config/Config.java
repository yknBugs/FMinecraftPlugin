package com.ykn.fplugin.config;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.ykn.fplugin.FPlugin;

public class Config {
    
    public static final String version = "0.1.0";
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

    public static boolean isShootMessageActive() {
        return thisPlugin.getConfig().getBoolean("shootmessage.active", true);
    }

    public static int getShootMessagePriority() {
        return thisPlugin.getConfig().getInt("shootmessage.priority", 0);
    }

    public static int getShootMessageDuration() {
        return thisPlugin.getConfig().getInt("shootmessage.duration", 100);
    }

    public static boolean isBeingHitMessageActive() {
        return thisPlugin.getConfig().getBoolean("shootmessage.beinghit", true);
    }

    public static boolean isHitOtherMessageActive() {
        return thisPlugin.getConfig().getBoolean("shootmessage.hitother", true);
    }

    public static boolean isAllShootMessageActive() {
        return thisPlugin.getConfig().getBoolean("shootmessage.all", true);
    }

    public static String formatTime(LocalDateTime time) {
        String result = thisPlugin.getConfig().getString("timeformat", "yyyy年M月d日 H:mm:ss");
        return time.format(DateTimeFormatter.ofPattern(result));
    }

    public static boolean isEntityDeathMessageActive() {
        return thisPlugin.getConfig().getBoolean("deathmessage.active", true);
    }

    public static int getEntityDeathMessagePriority() {
        return thisPlugin.getConfig().getInt("deathmessage.priority", 0);
    }

    public static int getEntityDeathMessageDuration() {
        return thisPlugin.getConfig().getInt("deathmessage.duration", 200);
    }

    public static int getEntityDeathMessageHealth() {
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

    public static boolean isBiomeMessageActive() {
        return thisPlugin.getConfig().getBoolean("biomemessage.active", true);
    }

    public static int getBiomeMessageDelay() {
        return thisPlugin.getConfig().getInt("biomemessage.delay", 100);
    }

    public static int getBiomeMessagePriority() {
        return thisPlugin.getConfig().getInt("biomemessage.priority", -500);
    }

    public static int getBiomeMessageDuration() {
        return thisPlugin.getConfig().getInt("biomemessage.duration", 60);
    }

    public static boolean isSiegeMessageActive() {
        return thisPlugin.getConfig().getBoolean("siegemessage.active", true);
    }

    public static int getSiegeMonsterCount() {
        return thisPlugin.getConfig().getInt("siegemessage.monster", 8);
    }

    public static int getSiegeMessagePriority() {
        return thisPlugin.getConfig().getInt("siegemessage.priority", 0);
    }

    public static int getSiegeMessageDuration() {
        return thisPlugin.getConfig().getInt("siegemessage.duration", 100);
    }

    public static int getSiegeMonsterDistanceX() {
        return thisPlugin.getConfig().getInt("siegemessage.distancex", 8);
    }

    public static int getSiegeMonsterDistanceY() {
        return thisPlugin.getConfig().getInt("siegemessage.distancey", 8);
    }

    public static int getSiegeMonsterDistanceZ() {
        return thisPlugin.getConfig().getInt("siegemessage.distancez", 8);
    }

    public static boolean isBossMessageActive() {
        return thisPlugin.getConfig().getBoolean("bossmessage.active", true);
    }

    public static int getBossMessageHealth() {
        return thisPlugin.getConfig().getInt("bossmessage.health", 150);
    }

    public static int getBossMessagePriority() {
        return thisPlugin.getConfig().getInt("bossmessage.priority", -750);
    }

    public static int getBossMessageDuration() {
        return thisPlugin.getConfig().getInt("bossmessage.duration", 600);
    }
}
