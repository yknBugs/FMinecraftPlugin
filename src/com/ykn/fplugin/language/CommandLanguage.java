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

    public static void sendStartReloadPluginMessage(CommandSender sender) {
        String message = languageYML.getConfig().getString("command.reload.start", "开始重新加载插件");
        message = message.replace('&', '\u00a7');
        sender.sendMessage(Config.getPrefix() + message);
    }

    public static void sendFinishReloadPluginMessage(CommandSender sender) {
        String message = languageYML.getConfig().getString("command.reload.end", "重新加载插件完成");
        message = message.replace('&', '\u00a7');
        sender.sendMessage(Config.getPrefix() + message);
    }

    public static void sendReloadPluginExceptionMessage(Exception exception, CommandSender sender) {
        String message = languageYML.getConfig().getString("command.reload.except", "重载插件出错: [exception]");
        message = message.replace('&', '\u00a7');
        message = message.replace("[exception]", exception.toString());
        sender.sendMessage(Config.getPrefix() + message);
    }
}
