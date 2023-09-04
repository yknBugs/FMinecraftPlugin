package com.ykn.fplugin.language;

import java.util.logging.Level;

import com.ykn.fplugin.config.Config;
import com.ykn.fplugin.config.ConfigAccessor;

public class Language {
    
    public static ConfigAccessor languageYML = null;

    /**
     * 在控制台显示并记录日志消息
     * @param level 日志等级。1 错误 2 警告 3 信息 4 调试。调试等级必须当配置文件中的 debug 设置为 true 才会显示
     * @param msg 记录的日志消息的内容
     */
    public static void log(int level, String msg) {
        // 消息等级 1 错误 2 警告 3 信息 4 调试
        if (level == 1) {
            Config.thisPlugin.getLogger().log(Level.SEVERE, msg);
        } else if (level == 2) {
            Config.thisPlugin.getLogger().log(Level.WARNING, msg);
        } else if (level == 3) {
            Config.thisPlugin.getLogger().log(Level.INFO, msg);
        } else if (level == 4 && Config.getIsDebug()) {
            Config.thisPlugin.getLogger().log(Level.INFO, msg);
        }
    }

    /**
     * 在控制台显示并记录日志信息
     * @param msg 记录的日志信息的内容
     */
    public static void log(String msg) {
        Config.thisPlugin.getLogger().log(Level.INFO, msg);
    }

}
