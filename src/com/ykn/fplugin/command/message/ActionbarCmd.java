package com.ykn.fplugin.command.message;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ykn.fplugin.config.GlobalConfig;
import com.ykn.fplugin.config.command.CmdMessageConfig;
import com.ykn.fplugin.util.BroadcastInfo;

public class ActionbarCmd extends MessageCommand {

    public Player player;

    public ActionbarCmd(CommandSender sender, Command command, String label, String[] args, int index) {
        super(sender, command, label, args, index);
        this.player = null;
    }

    @Override
    public List<String> getNextParamList() {
        List<String> result = new ArrayList<String>();
        result.add("true");
        result.add("false");
        return result;
    }

    @Override
    public boolean onLegalExecute(boolean isConsole) {
        if (!this.isActive()) {
            String[] holders = { this.args[1] };
            BroadcastInfo.replyMessage(this.sender, true, CmdMessageConfig.cmdWrongParam(), holders);
            return false;
        }
        if (this.args.length == this.index + 1) {
            BroadcastInfo.replyMessage(this.sender, false, CmdMessageConfig.missingPrefix(), null);
            return false;
        }

        boolean hasPrefix = true;
        if (this.args[this.index + 1].equalsIgnoreCase("false")) {
            hasPrefix = false;
        } else if (this.args[this.index + 1].equalsIgnoreCase("true")) {
            hasPrefix = true;
        } else if (this.args[this.index + 1].equalsIgnoreCase("0")) {
            hasPrefix = false;
        } else if (this.args[this.index + 1].equalsIgnoreCase("1")) {
            hasPrefix = true;
        } else {
            String[] holders = { this.args[this.index + 1] };
            BroadcastInfo.replyMessage(this.sender, false, CmdMessageConfig.wrongPrefix(), holders);
            return false;
        }

        if (this.args.length == this.index + 2) {
            BroadcastInfo.replyMessage(this.sender, false, CmdMessageConfig.missingText(), null);
            return false;
        }

        int i = this.index + 2;
        String msg = this.args[i];
        for (i = this.index + 3; i < this.args.length; i++) {
            msg = msg + " " + this.args[i];
        }

        if ((!this.hasPermission("fplugin.f.message.unsafe")) && (!BroadcastInfo.isTextSafe(msg))) {
            String[] holders = { msg };
            BroadcastInfo.replyMessage(this.sender, true, CmdMessageConfig.noSafePermission(), holders);
            return false;
        }

        if (player == null) {
            if (hasPrefix) {
                BroadcastInfo.broadcastActionbarText(GlobalConfig.getPrefix() + msg);
            } else {
                BroadcastInfo.broadcastActionbarText(msg);
            }
        } else {
            if (hasPrefix) {
                BroadcastInfo.sendActionbarText(player, GlobalConfig.getPrefix() + msg);
            } else {
                BroadcastInfo.sendActionbarText(player, msg);
            }
        }

        return true;
    }
    
    @Override
    public boolean onNoPermissionExecute() {
        String[] holders = { this.args[this.index] };
        BroadcastInfo.replyMessage(this.sender, true, CmdMessageConfig.noTypePermission(), holders);
        return false;
    }
    
}
