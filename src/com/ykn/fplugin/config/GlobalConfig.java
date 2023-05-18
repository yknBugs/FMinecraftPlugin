package com.ykn.fplugin.config;

public class GlobalConfig extends FConfig {
    public static boolean debug() {
        return getBoolean("debug");
    }

    public static void debug(String msg) {
        if (debug()) {
            getLogger().info(msg);
        }
    }

    public static String getPrefixConfig(String path) {
        return (getString("prefix") + getString(path)).replace('&', '\u00a7');
    }

    public static String getPrefix() {
        return getString("prefix").replace('&', '\u00a7');
    }
}
