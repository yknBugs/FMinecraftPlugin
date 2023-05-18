package com.ykn.fplugin.config.function;

import java.util.List;

import com.ykn.fplugin.config.FConfig;

public class PlayerDeathConfig extends FConfig {
    public static boolean active() {
        return getBoolean("playerdeath.active");
    }

    public static List<String> meesageList() {
        return getStringList("playerdeath.message");
    }
}
