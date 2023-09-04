package com.ykn.fplugin.config;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.ykn.fplugin.FPlugin;

public class Config {
    
    public static FPlugin thisPlugin = null;

    public static boolean getIsDebug() {
        return thisPlugin.getConfig().getBoolean("debug", false);
    }

    public static String getPrefix() {
        return thisPlugin.getConfig().getString("prefix", "\u00a7f").replace('&', '\u00a7');
    }

    public static boolean isActiveShootMessage() {
        return thisPlugin.getConfig().getBoolean("shootmessage", true);
    }

    public static String formatTime(LocalDateTime time) {
        String result = thisPlugin.getConfig().getString("timeformat", "yyyy年M月d日 H:mm:ss");
        return time.format(DateTimeFormatter.ofPattern(result));
    }

    public static boolean isActiveEntityDeathMessage() {
        return thisPlugin.getConfig().getBoolean("deathmessage.active", true);
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

    public static String getEntityKillerTag() {
        return thisPlugin.getConfig().getString("deathmessage.revengetag", "killer");
    }

    public static boolean isShowRenamedDeathMessage() {
        return thisPlugin.getConfig().getBoolean("deathmessage.renamed", true);
    }
}
