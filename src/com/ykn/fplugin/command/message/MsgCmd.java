package com.ykn.fplugin.command.message;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.ykn.fplugin.config.command.CmdMessageConfig;
import com.ykn.fplugin.util.BroadcastInfo;

public class MsgCmd extends MessageCommand {

    public MsgCmd(CommandSender sender, Command command, String label, String[] args, int index) {
        super(sender, command, label, args, index);
    }

    @Override
    public List<String> getNextParamList() {
        List<String> result = new ArrayList<String>();
        if (this.hasPermission("fplugin.f.message.broadcast")) {
            result.add("broadcast");
        }
        if (this.hasPermission("fplugin.f.message.send")) {
            result.add("send");
        }
        if (this.hasPermission("fplugin.f.message.remove")) {
            result.add("remove");
        }
        return result;
    }

    @Override
    public List<String> onTabComplete(String permissionNeed) {
        if (this.args.length == 0) {
            return null;
        } else if (!this.hasPermission(permissionNeed) || !this.isActive()) {
            return new ArrayList<String>();
        } else if (this.args.length == 2) {
            return this.getNextParamList();
        } else if (this.args[1].equalsIgnoreCase("broadcast")) {
            return (new BroadcastCmd(sender, command, label, args, 1)).onTabComplete("fplugin.f.message.broadcast");
        } else if (this.args[1].equalsIgnoreCase("send")) {
           return (new SendCmd(sender, command, label, args, 1)).onTabComplete("fplugin.f.message.send");
        } else if (this.args[1].equalsIgnoreCase("remove")) {
            return (new RemoveCmd(sender, command, label, args, 1)).onTabComplete("fplugin.f.message.remove");
        } else {
            return new ArrayList<String>();
        }
    }

    @Override
    public boolean onLegalExecute(boolean isConsole) {
        if (!this.isActive()) {
            String[] holders = { "/f " + this.args[0] };
            BroadcastInfo.replyMessage(this.sender, true, CmdMessageConfig.cmdWrongParam(), holders);
            return false;
        } else if (this.args.length == 1) {
            BroadcastInfo.replyMessage(this.sender, false, CmdMessageConfig.missingOpr(), null);
            return false;
        } else if (this.args[1].equalsIgnoreCase("broadcast")) {
            BroadcastCmd broadcastCmd = new BroadcastCmd(sender, command, label, args, 1);
            broadcastCmd.targetPlayer = null;
            return broadcastCmd.onExecute("fplugin.f.message.broadcast");
        } else if (this.args[1].equalsIgnoreCase("send")) {
            return (new SendCmd(sender, command, label, args, 1)).onExecute("fplugin.f.message.send");
        } else if (this.args[1].equalsIgnoreCase("remove")) {
            return (new RemoveCmd(sender, command, label, args, 1)).onExecute("fplugin.f.message.remove");
        } else {
            String[] holders = { this.args[1] };
            BroadcastInfo.replyMessage(this.sender, true, CmdMessageConfig.wrongOpr(), holders);
            return false;
        }
    }
}
