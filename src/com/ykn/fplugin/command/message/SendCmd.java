package com.ykn.fplugin.command.message;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ykn.fplugin.config.command.CmdMessageConfig;
import com.ykn.fplugin.util.BroadcastInfo;
import com.ykn.fplugin.util.WorldInfo;

public class SendCmd extends MessageCommand {

    public SendCmd(CommandSender sender, Command command, String label, String[] args, int index) {
        super(sender, command, label, args, index);
    }

    @Override
    public List<String> getNextParamList() {
        List<String> result = new LinkedList<String>();
        Collection<? extends Player> players = Bukkit.getOnlinePlayers();
        for (Player player : players) {
            result.add(player.getName());
        }
        return result;
    }

    @Override
    public List<String> onTabComplete(String permissionNeed) {
        if (this.args.length == 0) {
            return null;
        } else if (!this.hasPermission(permissionNeed) || !this.isActive()) {
            return new LinkedList<String>();
        } else if (this.args.length == 3) {
            return this.getNextParamList();
        } else {
            return (new BroadcastCmd(sender, command, label, args, 2)).onTabComplete("fplugin.f.message.send");
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
            BroadcastInfo.replyMessage(this.sender, false, CmdMessageConfig.missingName(), null);
            return false;
        }
        Player player = WorldInfo.getPlayer(this.args[2]);
        if (player == null) {
            String[] holders = { this.args[2] };
            BroadcastInfo.replyMessage(this.sender, true, CmdMessageConfig.wrongName(), holders);
            return false;
        }
        BroadcastCmd broadcastCmd = new BroadcastCmd(sender, command, label, args, 2);
        broadcastCmd.targetPlayer = player;
        return broadcastCmd.onExecute("fplugin.f.message.send");
    }

    @Override
    public boolean onNoPermissionExecute() {
        String[] holders = { this.args[1] };
        BroadcastInfo.replyMessage(this.sender, true, CmdMessageConfig.noOprPermission(), holders);
        return false;
    }
    
}
