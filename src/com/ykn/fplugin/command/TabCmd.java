package com.ykn.fplugin.command;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import com.ykn.fplugin.command.gui.NoParamCmd;

public class TabCmd implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return (new NoParamCmd(sender, command, label, args, 0)).onSuggest("fplugin.f");
    }
}
