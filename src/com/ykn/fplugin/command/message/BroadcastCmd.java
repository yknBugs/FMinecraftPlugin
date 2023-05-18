package com.ykn.fplugin.command.message;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ykn.fplugin.config.command.CmdMessageConfig;
import com.ykn.fplugin.util.BroadcastInfo;

public class BroadcastCmd extends MessageCommand {

    public Player targetPlayer;

    public BroadcastCmd(CommandSender sender, Command command, String label, String[] args, int index) {
        super(sender, command, label, args, index);
        this.targetPlayer = null;
    }

    @Override
    public List<String> getNextParamList() {
        List<String> result = new ArrayList<String>();
        if (this.hasPermission("fplugin.f.message.type.chat")) {
            result.add("chat");
        }
        if (this.hasPermission("fplugin.f.message.type.actionbar")) {
            result.add("actionbar");
        }
        if (this.hasPermission("fplugin.f.message.type.bossbar")) {
            result.add("bossbar");
        }
        return result;
    }

    @Override
    public List<String> onTabComplete(String permissionNeed) {
        if (this.args.length == 0) {
            return null;
        } else if (!this.hasPermission(permissionNeed) || !this.isActive()) {
            return new ArrayList<String>();
        } else if (this.args.length == this.index + 2) {
            return this.getNextParamList();
        } else if (this.args[this.index + 1].equalsIgnoreCase("chat")) {
            return (new ChatCmd(sender, command, label, args, this.index + 1)).onTabComplete("fplugin.f.message.type.chat");
        } else if (this.args[this.index + 1].equalsIgnoreCase("actionbar")) {
           return (new ActionbarCmd(sender, command, label, args, this.index + 1)).onTabComplete("fplugin.f.message.type.actionbar");
        } else if (this.args[this.index + 1].equalsIgnoreCase("bossbar")) {
            return (new BossbarCmd(sender, command, label, args, this.index + 1)).onTabComplete("fplugin.f.message.type.bossbar");
        } else {
            return new ArrayList<String>();
        }
    }
    
    @Override
    public boolean onLegalExecute(boolean isConsole) {
        if (!this.isActive()) {
            String[] holders = { this.args[1] };
            BroadcastInfo.replyMessage(this.sender, true, CmdMessageConfig.cmdWrongParam(), holders);
            return false;
        }
        if (this.args.length == this.index + 1) {
            BroadcastInfo.replyMessage(this.sender, false, CmdMessageConfig.missingType(), null);
            return false;
        }
        if (this.args[this.index + 1].equalsIgnoreCase("chat")) {
            ChatCmd chatCmd = new ChatCmd(sender, command, label, args, this.index + 1);
            chatCmd.player = this.targetPlayer;
            return chatCmd.onExecute("fplugin.f.message.type.chat");
        }
        if (this.args[this.index + 1].equalsIgnoreCase("actionbar")) {
            ActionbarCmd actionbarCmd = new ActionbarCmd(sender, command, label, args, this.index + 1);
            actionbarCmd.player = this.targetPlayer;
            return actionbarCmd.onExecute("fplugin.f.message.type.actionbar");
        }
        if (this.args[this.index + 1].equalsIgnoreCase("bossbar")) {
            BossbarCmd bossbarCmd = new BossbarCmd(sender, command, label, args, this.index + 1);
            bossbarCmd.player = this.targetPlayer;
            bossbarCmd.isAdd = true;
            return bossbarCmd.onExecute("fplugin.f.message.type.bossbar");
        }
        String[] holders = { this.args[this.index + 1] };
        BroadcastInfo.replyMessage(this.sender, true, CmdMessageConfig.wrongType(), holders);
        return false;
    }

    @Override
    public boolean onNoPermissionExecute() {
        String[] holders = { this.args[1] };
        BroadcastInfo.replyMessage(this.sender, true, CmdMessageConfig.noOprPermission(), holders);
        return false;
    }
}
