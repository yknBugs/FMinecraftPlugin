package com.ykn.fplugin.config;

import java.util.List;
import java.util.logging.Logger;

import com.ykn.fplugin.FPlugin;

public class FConfig {
    public static FPlugin fPlugin = null;
    public static final String copyright = "FPlugin v0.0.3";

    public static String getString(String path) {
        String result = fPlugin.getConfig().getString(path);
        if (result == null) {
            return path;
        }
        return result;
    }

    public static String getColorString(String path) {
        return getString(path).replace('&', '\u00a7');
    }

    public static String toColorString(String msg) {
        return msg.replace('&', '\u00a7');
    }

    public static int getInt(String path) {
        return fPlugin.getConfig().getInt(path);
    }

    public static double getDouble(String path) {
        return fPlugin.getConfig().getDouble(path);
    }

    public static List<String> getStringList(String path) {
        return fPlugin.getConfig().getStringList(path);
    }

    public static Logger getLogger() {
        return fPlugin.getLogger();
    }

    public static boolean getBoolean(String path) {
        return fPlugin.getConfig().getBoolean(path);
    }
}
