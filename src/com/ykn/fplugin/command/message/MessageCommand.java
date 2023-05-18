package com.ykn.fplugin.command.message;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.ykn.fplugin.command.FCommand;
import com.ykn.fplugin.config.command.CmdConfig;

public class MessageCommand extends FCommand {

    public MessageCommand(CommandSender sender, Command command, String label, String[] args, int index) {
        super(sender, command, label, args, index);
    }

    @Override
    public boolean isActive() {
        return CmdConfig.isCmdActive("message");
    }
    
    public boolean onLegalExecute(boolean isConsole) {
        return true;
    }

    @Override
    public boolean onConsoleExecute() {
        return this.onLegalExecute(true);
    }

    @Override
    public boolean onPlayerLegalExecute() {
        return this.onLegalExecute(false);
    }
}
