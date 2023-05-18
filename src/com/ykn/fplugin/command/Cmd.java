package com.ykn.fplugin.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.ykn.fplugin.command.gui.NoParamCmd;
import com.ykn.fplugin.config.GlobalConfig;

public class Cmd implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        boolean success = (new NoParamCmd(sender, command, label, args, 0)).onExecute("fplugin.f");
        if (!success) {
            String cmd = "/f";
            for (String arg : args) {
                cmd = cmd + " " + arg;
            }
            GlobalConfig.debug("Failed execute Command:" + cmd);
        }
        return true;
    }
    
}
