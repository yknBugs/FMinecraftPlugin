package com.ykn.fplugin.command.message;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.ykn.fplugin.config.command.CmdMessageConfig;
import com.ykn.fplugin.util.BroadcastInfo;

public class RemoveCmd extends MessageCommand {

    public RemoveCmd(CommandSender sender, Command command, String label, String[] args, int index) {
        super(sender, command, label, args, index);
    }

    @Override
    public List<String> getNextParamList() {
        List<String> result = new ArrayList<String>();
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
        } else if (this.args.length == 3) {
            return this.getNextParamList();
        } else if (this.args[2].equalsIgnoreCase("bossbar")) {
            BossbarCmd bossbarCmd = new BossbarCmd(sender, command, label, args, 2);
            bossbarCmd.player = null;
            bossbarCmd.isAdd = false;
            return bossbarCmd.onTabComplete("fplugin.f.message.type.bossbar");
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
        if (this.args.length == 2) {
            BroadcastInfo.replyMessage(this.sender, false, CmdMessageConfig.missingType(), null);
            return false;
        }
        if (this.args[2].equalsIgnoreCase("bossbar")) {
            BossbarCmd bossbarCmd = new BossbarCmd(sender, command, label, args, 2);
            bossbarCmd.player = null;
            bossbarCmd.isAdd = false;
            return bossbarCmd.onExecute("fplugin.f.message.type.bossbar");
        }
        String[] holders = { this.args[2] };
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
