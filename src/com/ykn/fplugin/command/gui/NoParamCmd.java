package com.ykn.fplugin.command.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.ykn.fplugin.command.BackCmd;
import com.ykn.fplugin.command.DelayCmd;
import com.ykn.fplugin.command.DevfunctionCmd;
import com.ykn.fplugin.command.FCommand;
import com.ykn.fplugin.command.HelpCmd;
import com.ykn.fplugin.command.RunCmd;
import com.ykn.fplugin.command.message.MsgCmd;
import com.ykn.fplugin.config.GlobalConfig;
import com.ykn.fplugin.config.command.CmdConfig;
import com.ykn.fplugin.util.BroadcastInfo;

public class NoParamCmd extends FCommand {

    public NoParamCmd(CommandSender sender, Command command, String label, String[] args, int index) {
        super(sender, command, label, args, index);
    }

    @Override
    public boolean isActive() {
        return CmdConfig.active();
    }

    @Override
    public List<String> getNextParamList() {
        List<String> result = new ArrayList<String>();
        result.add("version");
        if (this.hasPermission("fplugin.f.gui") && CmdConfig.isCmdActive("gui")) {
            result.add("gui");
        }
        if (this.hasPermission("fplugin.f.devfunction") && CmdConfig.isCmdActive("devfunction") && GlobalConfig.debug()) {
            result.add("devfunction");
        }
        if (this.hasPermission("fplugin.f.help") && CmdConfig.isCmdActive("help")) {
            result.add("help");
        }
        if (this.hasPermission("fplugin.f.run") && CmdConfig.isCmdActive("run")) {
            result.add("run");
        }
        if (this.hasPermission("fplugin.f.delay") && CmdConfig.isCmdActive("delay")) {
            result.add("delay");
        }
        if (this.hasPermission("fplugin.f.message") && CmdConfig.isCmdActive("message")) {
            result.add("message");
        }
        if (this.hasPermission("fplugin.f.back") && CmdConfig.isCmdActive("back")) {
            result.add("back");
        }
        return result;
    }

    @Override
    public List<String> onTabComplete(String permissionNeed) {
        if (this.args.length == 0) {
            return null;
        } else if (!this.hasPermission(permissionNeed) || !this.isActive()) {
            return new ArrayList<String>();
        } else if (this.args.length == 1) {
            return this.getNextParamList();
        } else if (this.args[0].equalsIgnoreCase("gui")) {
            return (new GUICmd(sender, command, label, args, 0)).onTabComplete("fplugin.f.gui");
        } else if (this.args[0].equalsIgnoreCase("devfunction")) {
            return (new DevfunctionCmd(sender, command, label, args, 0)).onTabComplete("fplugin.f.devfunction");
        } else if (this.args[0].equalsIgnoreCase("help")) {
            return (new HelpCmd(sender, command, label, args, 0)).onTabComplete("fplugin.f.help");
        } else if (this.args[0].equalsIgnoreCase("run")) {
            return (new RunCmd(sender, command, label, args, 0)).onTabComplete("fplugin.f.run");
        } else if (this.args[0].equalsIgnoreCase("delay")) {
            return (new DelayCmd(sender, command, label, args, 0)).onTabComplete("fplugin.f.delay");
        } else if (this.args[0].equalsIgnoreCase("message")) {
            return (new MsgCmd(sender, command, label, args, 0)).onTabComplete("fplugin.f.message");
        } else if (this.args[0].equalsIgnoreCase("back")) {
            return (new BackCmd(sender, command, label, args, 0)).onTabComplete("fplugin.f.back");
        } else {
            return new ArrayList<String>();
        }
    }

    @Override
    public boolean onConsoleExecute() {
        if (this.args.length == 0) {
            this.sender.sendMessage(GlobalConfig.getPrefix() + CmdConfig.copyright);
            return true;
        } else if (!this.isActive()) {
            String[] holders = { "/f " + this.args[0] };
            BroadcastInfo.replyMessage(this.sender, true, CmdConfig.cmdWrongParam(), holders);
            return false;
        } else if (this.args[0].equalsIgnoreCase("version")) {
            this.sender.sendMessage(GlobalConfig.getPrefix() + CmdConfig.copyright);
            return true;
        } else if (this.args[0].equalsIgnoreCase("gui")) {
            return (new GUICmd(sender, command, label, args, 0)).onExecute("fplugin.f.gui");
        } else if (this.args[0].equalsIgnoreCase("devfunction")) {
            return (new DevfunctionCmd(sender, command, label, args, 0)).onExecute("fplugin.f.devfunction");
        } else if (this.args[0].equalsIgnoreCase("help")) {
            return (new HelpCmd(sender, command, label, args, 0)).onExecute("fplugin.f.help");
        } else if (this.args[0].equalsIgnoreCase("run")) {
            return (new RunCmd(sender, command, label, args, 0)).onExecute("fplugin.f.run");
        } else if (this.args[0].equalsIgnoreCase("delay")) {
            return (new DelayCmd(sender, command, label, args, 0)).onExecute("fplugin.f.delay");
        } else if (this.args[0].equalsIgnoreCase("message")) {
            return (new MsgCmd(sender, command, label, args, 0)).onExecute("fplugin.f.message");
        } else if (this.args[0].equalsIgnoreCase("back")) {
            return (new BackCmd(sender, command, label, args, 0)).onExecute("fplugin.f.back");
        } else {
            String[] holders = { "/f " + this.args[0] };
            BroadcastInfo.replyMessage(this.sender, true, CmdConfig.cmdWrongParam(), holders);
            return false;
        }
    }
    
    @Override
    public boolean onPlayerLegalExecute() {
        if (!this.isActive()) {
            this.sender.sendMessage(GlobalConfig.getPrefix() + CmdConfig.copyright);
            return true;
        } else if (this.args.length == 0) {
            if (!CmdConfig.isCmdActive("gui")) {
                this.sender.sendMessage(GlobalConfig.getPrefix() + CmdConfig.copyright);
                return true;
            } else if (this.hasPermission("fplugin.f.gui")) {
                GlobalConfig.debug("Command missing parameters. Auto changed command to /f gui");
                return (new GUICmd(sender, command, label, args, 0)).onExecute("fplugin.f.gui");
            } else {
                this.sender.sendMessage(GlobalConfig.getPrefix() + CmdConfig.copyright);
                return true;
            }
        } else if (this.args[0].equalsIgnoreCase("version")) {
            this.sender.sendMessage(GlobalConfig.getPrefix() + CmdConfig.copyright);
            return true;
        } else if (this.args[0].equalsIgnoreCase("gui")) {
            return (new GUICmd(sender, command, label, args, 0)).onExecute("fplugin.f.gui");
        } else if (this.args[0].equalsIgnoreCase("devfunction")) {
            return (new DevfunctionCmd(sender, command, label, args, 0)).onExecute("fplugin.f.devfunction");
        } else if (this.args[0].equalsIgnoreCase("help")) {
            return (new HelpCmd(sender, command, label, args, 0)).onExecute("fplugin.f.help");
        } else if (this.args[0].equalsIgnoreCase("run")) {
            return (new RunCmd(sender, command, label, args, 0)).onExecute("fplugin.f.run");
        } else if (this.args[0].equalsIgnoreCase("delay")) {
            return (new DelayCmd(sender, command, label, args, 0)).onExecute("fplugin.f.delay");
        } else if (this.args[0].equalsIgnoreCase("message")) {
            return (new MsgCmd(sender, command, label, args, 0)).onExecute("fplugin.f.message");
        } else if (this.args[0].equalsIgnoreCase("back")) {
            return (new BackCmd(sender, command, label, args, 0)).onExecute("fplugin.f.back");
        } else {
            String[] holders = { "/f " + this.args[0] };
            BroadcastInfo.replyMessage(this.sender, true, CmdConfig.cmdWrongParam(), holders);
            return false;
        }
    }
}
