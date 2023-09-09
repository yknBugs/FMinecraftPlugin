package com.ykn.fplugin.event;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import com.ykn.fplugin.command.FCommand;
import com.ykn.fplugin.config.Config;
import com.ykn.fplugin.language.CommandLanguage;

public class SendCommand implements TabExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //f
        if (args.length == 0) {
            if (FCommand.checkPermission(sender, "fplugin.command.f")) {
                sender.sendMessage(Config.getPrefix() + "FMinecraftPlugin v" + Config.version);
            }
            return true;
        }

        //f <inserting...>
        if (Config.getIsDebug() && "dev".equalsIgnoreCase(args[0])) {
            FCommand.runDevCommand(sender, args);
            return true;
        }
        
        if ("reload".equalsIgnoreCase(args[0])) {
            FCommand.runReloadCommand(sender, args);
            return true;
        }

        CommandLanguage.sendUnknownCommandMessage(sender, args[0]);
        return true;
    }

    private List<String> onSuggest(CommandSender sender, Command command, String label, String[] args) {
        List<String> result = new ArrayList<String>();
        //f
        if (args.length == 0) {
            return result;
        }

        //f <inserting...>
        if (args.length == 1) {
            if (Config.getIsDebug() && sender.hasPermission("fplugin.command.dev")) {
                result.add("dev");
            }

            if (sender.hasPermission("fplugin.command.reload")) {
                result.add("reload");
            }

            return result;
        }

        return result;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> tabList = this.onSuggest(sender, command, label, args);
        if (tabList == null) {
            return null;
        }
        List<String> result = new ArrayList<String>();
        String currentInsert = args[args.length - 1];
        if (currentInsert.length() == 0) {
            return tabList;
        }

        for (String tabChoice : tabList) {
            if (currentInsert.length() > tabChoice.length()) {
                continue;
            }
            if (tabChoice.substring(0, currentInsert.length()).equalsIgnoreCase(currentInsert)) {
                result.add(tabChoice);
            }
        }
        return result;
    }
    
}
