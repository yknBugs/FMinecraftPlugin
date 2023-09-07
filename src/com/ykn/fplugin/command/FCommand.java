package com.ykn.fplugin.command;

import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ykn.fplugin.config.Config;
import com.ykn.fplugin.data.PlayerData;
import com.ykn.fplugin.data.ServerData;
import com.ykn.fplugin.language.CommandLanguage;

public class FCommand {

    private static void devFunction(CommandSender sender) {
        Collection<? extends Player> players = Bukkit.getServer().getOnlinePlayers();
        for (Player player : players) {
            PlayerData playerData = ServerData.playerdata.get(player.getUniqueId());
            playerData.afk = 5600;
        }
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
            sender.sendMessage(Config.getPrefix() + exception.getMessage());
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
