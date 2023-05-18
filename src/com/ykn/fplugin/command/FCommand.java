package com.ykn.fplugin.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.block.CommandBlock;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ykn.fplugin.config.command.CmdConfig;
import com.ykn.fplugin.util.BroadcastInfo;

public class FCommand {
    public CommandSender sender;
    public Command command;
    public String label;
    public String[] args;
    public int index;

    /**
     * @param sender 指令的发送者
     * @param command 指令的名称
     * @param label 指令的别名
     * @param args 指令的参数
     * @param index 当前读取的索引位置
     */
    public FCommand(CommandSender sender, Command command, String label, String[] args, int index) {
        this.sender = sender;
        this.command = command;
        this.label = label;
        this.args = args;
        this.index = index;
    }

    public boolean hasPermission(String permission) {
        if (this.sender instanceof Player) {
            Player player = (Player) this.sender;
            return player.hasPermission(permission);
        } else {
            return true;
        }
    }

    public boolean isActive() {
        return true;
    }

    public List<String> getNextParamList() {
        return new ArrayList<String>();
    }

    public List<String> onTabComplete(String permissionNeed) {
        if (this.args.length == 0) {
            return null;
        } else if (!this.hasPermission(permissionNeed) || !this.isActive()) {
            return new ArrayList<String>();
        } else if (this.args.length == this.index + 2) {
            return this.getNextParamList();
        } else {
            return new ArrayList<String>();
        }
    }
    
    public List<String> onSuggest(String permissionNeed) {
        List<String> allParam = this.onTabComplete(permissionNeed);
        List<String> result = new ArrayList<String>();
        if (this.args.length == 0) {
            return result;
        }
        String currentInsert = this.args[this.args.length - 1];
        if (currentInsert.length() == 0) {
            return allParam;
        }

        for (String param : allParam) {
            if (currentInsert.length() > param.length()) {
                continue;
            }
            if (param.substring(0, currentInsert.length()).equalsIgnoreCase(currentInsert)) {
                result.add(param);
            }
        }
        return result;
    }

    public boolean onExecute(String permissionNeed) {
        if (this.sender instanceof Player) {
            return this.onPlayerExecute(permissionNeed);
        } else if (this.sender instanceof CommandBlock)
            return this.onCommandBlockExecute();
        else {
            return this.onConsoleExecute();
        }
    }

    public boolean onNoPermissionExecute() {
        String[] holders = { "/f" };
        if (this.args.length == 0) {
            BroadcastInfo.replyMessage(this.sender, true, CmdConfig.cmdNoPermission(), holders);
            return false;
        }
        holders[0] = "/f " + this.args[0];
        BroadcastInfo.replyMessage(this.sender, true, CmdConfig.cmdNoPermission(), holders);
        return false;
    }

    public boolean onConsoleExecute() {
        return true;
    }

    public boolean onCommandBlockExecute() {
        return true;
    }

    public boolean onPlayerLegalExecute() {
        return true;
    }

    public boolean onPlayerExecute(String permissionNeed) {
        Player player = (Player) this.sender;
        if (permissionNeed == null) {
            return this.onPlayerLegalExecute();
        } else if (player.hasPermission(permissionNeed)) {
            return this.onPlayerLegalExecute();
        } else {
            return this.onNoPermissionExecute();
        }
    }
}
