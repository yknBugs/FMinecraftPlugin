package com.ykn.fplugin.language;

import org.bukkit.command.CommandSender;

import com.ykn.fplugin.config.Config;

public class CommandLanguage extends Language {

    public static void sendNoPermissionMessage(CommandSender sender, String permission) {
        String message = languageYML.getConfig().getString("command.nopermission", "你没有使用这个指令的权限");
        message = message.replace('&', '\u00a7');
        message = message.replace("[permission]", permission);
        sender.sendMessage(Config.getPrefix() + message);
    }

    public static void sendUnknownCommandMessage(CommandSender sender, String args) {
        String message = languageYML.getConfig().getString("command.unknown", "未知的指令参数 [args]");
        message = message.replace('&', '\u00a7');
        message = message.replace("[args]", args);
        sender.sendMessage(Config.getPrefix() + message);
    }
    
}
