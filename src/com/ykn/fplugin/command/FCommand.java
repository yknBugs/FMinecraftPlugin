package com.ykn.fplugin.command;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import com.ykn.fplugin.config.Config;
import com.ykn.fplugin.language.CommandLanguage;

public class FCommand {

    private static void devFunction(CommandSender sender) {
        sender.sendMessage(Config.getPrefix() + "当前没有需要测试执行的功能。");
    }

    //f dev [inserting...]
    public static void runDevCommand(CommandSender sender, String[] args) {
        if (!checkPermission(sender, "fplugin.command.dev")) {
            return;
        }

        if (args.length > 1) {
            CommandLanguage.sendUnknownCommandMessage(sender, args[1]);
            return;
        }

        try {
            devFunction(sender);
        } catch (Exception exception) {
            sender.sendMessage(Config.getPrefix() + exception.toString());
        }
    }

    //f reload [inserting...]
    public static void runReloadCommand(CommandSender sender, String[] args) {
        if (!checkPermission(sender, "fplugin.command.reload")) {
            return;
        }

        if (args.length > 1) {
            CommandLanguage.sendUnknownCommandMessage(sender, args[1]);
            return;
        }

        try {
            CommandLanguage.sendStartReloadPluginMessage(sender);
            Bukkit.getPluginManager().disablePlugin(Config.thisPlugin);
            Bukkit.getPluginManager().enablePlugin(Config.thisPlugin);
            CommandLanguage.sendFinishReloadPluginMessage(sender);
        } catch (Exception exception) {
            CommandLanguage.sendReloadPluginExceptionMessage(exception, sender);
        }
    }

    /**
     * 检查指令发送者是否有权限，如果没有权限则往指令发送者发送一条没有权限的消息
     * @param sender 指令发送者
     * @param permission 权限
     * @return 指令发送者是否有权限
     */
    public static boolean checkPermission(CommandSender sender, String permission) {
        if (sender.hasPermission(permission)) {
            return true;
        } 
        CommandLanguage.sendNoPermissionMessage(sender, permission);
        return false;

    }
    
}
