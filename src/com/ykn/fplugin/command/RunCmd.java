package com.ykn.fplugin.command;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ykn.fplugin.config.GlobalConfig;
import com.ykn.fplugin.config.command.CmdRunConfig;
import com.ykn.fplugin.util.BroadcastInfo;

public class RunCmd extends FCommand {

    public RunCmd(CommandSender sender, Command command, String label, String[] args, int index) {
        super(sender, command, label, args, index);
    }

    private boolean runCustomCommand(String customCommand) {
        String permission = CmdRunConfig.permission(customCommand);
        boolean isConsole = CmdRunConfig.isConsole(customCommand);
        List<String> commands = CmdRunConfig.commandList(customCommand);
        String[] holders = { this.args[1] };

        if (!this.hasPermission(permission)) {
            BroadcastInfo.replyMessage(this.sender, true, CmdRunConfig.noPermission(), holders);
            return false;
        }

        //参数数量检查
        List<String> paramRegex = CmdRunConfig.paramList(customCommand);
        if (paramRegex.size() > this.args.length - 2) {
            String[] holders2 = { Integer.toString(paramRegex.size()), Integer.toString(this.args.length - 2),
                Integer.toString(paramRegex.size() - this.args.length + 2) };
            BroadcastInfo.replyMessage(this.sender, true, CmdRunConfig.missingParams(), holders2);
            return false;
        }

        //参数安全检查
        int i = 2;
        if (CmdRunConfig.isSafe(customCommand)) {
            for (i = 2; i < this.args.length; i++) {
                if (!BroadcastInfo.isTextSafe(this.args[i])) {
                    holders[0] = this.args[i];
                    BroadcastInfo.replyMessage(this.sender, true, CmdRunConfig.notSafe(), holders);
                    return false;
                }

                if (!this.args[i].matches(paramRegex.get(i - 2))) {
                    holders[0] = this.args[i];
                    BroadcastInfo.replyMessage(this.sender, true, CmdRunConfig.wrongParams(), holders);
                    return false;
                }
            }
        }

        for (String command : commands) {
            //填充自定义指令的参数
            String cmd = command;
            for (i = 2; i < this.args.length; i++) {
                cmd = cmd.replaceAll("\\[" + (i - 1) + "\\]", this.args[i]);
            }

            //执行指令
            if (this.sender instanceof Player) {
                Player player = (Player) this.sender;
                if (isConsole) {
                    BroadcastInfo.executePlaceholderCommand(player, cmd);
                } else {
                    BroadcastInfo.performCommand(player, cmd);
                }
            } else {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
            }

            GlobalConfig.debug("Run command in custom command: " + cmd);
        }
        return true;
    }

    private boolean runCustomCommand() {
        if (!this.isActive()) {
            String[] holders = { "/f " + this.args[0] };
            BroadcastInfo.replyMessage(this.sender, true, CmdRunConfig.cmdWrongParam(), holders);
            return false;
        }

        if (this.args.length == 1) {
            BroadcastInfo.replyMessage(this.sender, false, CmdRunConfig.missingName(), null);
            return false;
        }

        List<String> customCommandList = CmdRunConfig.customCommandList();
        boolean hasFind = false;
        for (String customCommand : customCommandList) {
            if (customCommand.equalsIgnoreCase(this.args[1])) {
                hasFind = true;
                return runCustomCommand(customCommand);
            }
        }
        if (!hasFind) {
            String[] holders = { this.args[1] };
            BroadcastInfo.replyMessage(this.sender, true, CmdRunConfig.wrongName(), holders);
            return false;
        }

        return true;
    }

    @Override
    public List<String> getNextParamList() {
        List<String> customCommandList = CmdRunConfig.customCommandList();
        List<String> result = new LinkedList<String>();
        for (String customCommand : customCommandList) {
            if (this.hasPermission(CmdRunConfig.permission(customCommand))) {
                result.add(customCommand);
            }
        }
        return result;
    }

    @Override
    public boolean isActive() {
        return CmdRunConfig.isCmdActive("run");
    }
    
    @Override
    public boolean onConsoleExecute() {
        return this.runCustomCommand();
    }

    @Override
    public boolean onPlayerLegalExecute() {
        return this.runCustomCommand();
    }
}
