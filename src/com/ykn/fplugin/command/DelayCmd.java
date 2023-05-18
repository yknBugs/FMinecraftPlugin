package com.ykn.fplugin.command;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ykn.fplugin.config.GlobalConfig;
import com.ykn.fplugin.config.command.CmdDelayConfig;
import com.ykn.fplugin.data.GlobalData;
import com.ykn.fplugin.util.BroadcastInfo;

public class DelayCmd extends FCommand {

    public int planTick;
    public int planDelay;
    public String planCommand;
    public String planExecutor;

    public DelayCmd(CommandSender sender, Command command, String label, String[] args, int index) {
        super(sender, command, label, args, index);
        this.planTick = 0;
        this.planDelay = 0;
        this.planCommand = "help";
        this.planCommand = this.sender.getName();
    }

    private boolean delayExecuteCommand() {
        String[] holders = { "/f " + this.args[0] };
        if (!this.isActive()) {
            BroadcastInfo.replyMessage(this.sender, true, CmdDelayConfig.cmdWrongParam(), holders);
            return false;
        }

        if (this.args.length == 1) {
            BroadcastInfo.replyMessage(this.sender, false, CmdDelayConfig.missingTime(), null);
            return false;
        }

        //检查数字是否合法
        holders[0] = this.args[1];
        if (this.args[1].matches("^[0-9]{9,}$")) {
            BroadcastInfo.replyMessage(this.sender, true, CmdDelayConfig.timeTooLarge(), holders);
            return false;
        }
        if (this.args[1].matches("^-[0-9]+$")) {
            BroadcastInfo.replyMessage(this.sender, true, CmdDelayConfig.timeTooSmall(), holders);
            return false;
        }
        if (this.args[1].matches("^[0-9]{1,8}$")) {
            int delayTick = Integer.valueOf(this.args[1]);

            //检查执行者
            if (this.args.length == 2) {
                BroadcastInfo.replyMessage(this.sender, false, CmdDelayConfig.missingExecuter(), null);
                return false;
            }
            if ((!this.hasPermission("fplugin.f.delay.console")) && this.args[2].equalsIgnoreCase("console")) {
                BroadcastInfo.replyMessage(this.sender, false, CmdDelayConfig.consolePermission(), null);
                return false;
            }
            if ((!this.hasPermission("fplugin.f.delay.other")) && (!this.args[2].equalsIgnoreCase(this.sender.getName())) && (!this.args[2].equalsIgnoreCase("console"))) {
                BroadcastInfo.replyMessage(this.sender, false, CmdDelayConfig.otherPermission(), null);
                return false;
            }
            //未指定指令
            if (this.args.length == 3) {
                BroadcastInfo.replyMessage(this.sender, false, CmdDelayConfig.missingCommand(), null);
                return false;
            }
            //执行指令
            String cmd = this.args[3];
            int i = 4;
            for (i = 4; i < this.args.length; i++) {
                cmd = cmd + " " + this.args[i];
            }
            this.scheduleCommand(delayTick, this.args[2], cmd);
            return true;
        }
        if (this.args[1].equalsIgnoreCase("clear")) {
            if (!this.hasPermission("fplugin.f.delay.clear")) {
                BroadcastInfo.replyMessage(this.sender, false, CmdDelayConfig.clearPermission(), null);
                return false;
            }
            if (this.args.length == 2) {
                holders[0] = Integer.toString(GlobalData.serverData.scheduledDelayCommand.size());
                GlobalData.serverData.scheduledDelayCommand.clear();
                BroadcastInfo.replyMessage(this.sender, true, CmdDelayConfig.clearSuccess(), holders);
                return true;
            }
            String cmd = this.args[2];
            int i = 3;
            for (i = 3; i < this.args.length; i++) {
                cmd = cmd + " " + this.args[i];
            }
            List<DelayCmd> clearItem = new ArrayList<DelayCmd>();
            for (DelayCmd delayCmd : GlobalData.serverData.scheduledDelayCommand) {
                if (cmd.length() > delayCmd.planCommand.length()) {
                    continue;
                }
                if (delayCmd.planCommand.substring(0, cmd.length()).equalsIgnoreCase(cmd)) {
                    clearItem.add(delayCmd);
                }
            }
            holders[0] = Integer.toString(clearItem.size());
            for (DelayCmd delayCmd : clearItem) {
                GlobalData.serverData.scheduledDelayCommand.remove(delayCmd);
            }
            BroadcastInfo.replyMessage(this.sender, true, CmdDelayConfig.clearSuccess(), holders);
            return true;
        }
        BroadcastInfo.replyMessage(this.sender, true, CmdDelayConfig.wrongTime(), holders);
        return false;
    }

    /**
     * 延迟执行指令。如果仅仅是需要实现延迟执行指令的功能的话，初始化对象时CommandSender可以为null
     * 如果需要进行任何指令内容相关的操作必须在初始化对象时给参数正确赋值
     * @param delayTick 延迟执行的游戏刻
     * @param target 执行目标
     * @param cmd 指令内容
     */
    public void scheduleCommand(int delayTick, String target, String cmd) {
        this.planTick = GlobalData.serverData.ticks + delayTick;
        this.planDelay = delayTick;
        this.planCommand = cmd;
        this.planExecutor = target;

        if (delayTick <= 0) {
            this.performCommand();
            return;
        }

        int i = 0;
        for (DelayCmd delayCmd : GlobalData.serverData.scheduledDelayCommand) {
            if (delayCmd.planTick > this.planTick) {
                break;
            }
            i++;
        }
        GlobalData.serverData.scheduledDelayCommand.add(i, this);
        GlobalConfig.debug("Command /" + cmd + " will be executed by " + target + " in " + delayTick + " ticks");

        if (this.sender != null) {
            String[] holders = { "/" + cmd, Integer.toString(delayTick), target };
            BroadcastInfo.replyMessage(this.sender, true, CmdDelayConfig.delaySuccess(), holders);

            //寻找玩家
            if (target.equalsIgnoreCase("console")) {
                return;
            }
            Collection<? extends Player> players = Bukkit.getOnlinePlayers();
            for (Player player : players) {
                if (player.getName().equalsIgnoreCase(target)) {
                    return;
                }
            }
            BroadcastInfo.replyMessage(this.sender, true, CmdDelayConfig.warnExecuter(), holders);
        }
    }

    public void performCommand() {
        String[] holders = { "/" + this.planCommand, Integer.toString(this.planDelay), this.planExecutor };
        if (this.planExecutor.equalsIgnoreCase("console")) {
            if (this.sender != null) {
                BroadcastInfo.replyMessage(this.sender, true, CmdDelayConfig.executeSuccess(), holders);
            }
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), this.planCommand);
            GlobalConfig.debug("Console execute scheduled command: /" + this.planCommand);
            return;
        }
        Collection<? extends Player> players = Bukkit.getOnlinePlayers();
        for (Player player : players) {
            if (player.getName().equalsIgnoreCase(this.planExecutor)) {
                if (this.sender != null) {
                    BroadcastInfo.replyMessage(this.sender, true, CmdDelayConfig.executeSuccess(), holders);
                }
                player.performCommand(this.planCommand);
                GlobalConfig.debug(this.planExecutor + " execute scheduled command: /" + this.planCommand);
                return;
            }
        }

        GlobalConfig.debug("Failed execute scheduled command (Target " + this.planExecutor + " not found): /" + this.planCommand);
        if (this.sender != null) {
            BroadcastInfo.replyMessage(this.sender, true, CmdDelayConfig.noExecuter(), holders);
        }
    }

    @Override
    public boolean isActive() {
        return CmdDelayConfig.isCmdActive("delay");
    }
    
    @Override
    public boolean onConsoleExecute() {
        return this.delayExecuteCommand();
    }

    @Override
    public boolean onPlayerLegalExecute() {
        return this.delayExecuteCommand();
    }

    @Override
    public List<String> onTabComplete(String permissionNeed) {
        if (this.args.length == 0) {
            return null;
        } else if (!this.hasPermission(permissionNeed) || !this.isActive()) {
            return new ArrayList<String>();
        } else if (this.args.length == 2) {
            ArrayList<String> result = new ArrayList<String>();
            result.add("0");
            result.add("20");
            result.add("200");
            result.add("600");
            result.add("1200");
            result.add("2400");
            result.add("6000");
            result.add("18000");
            result.add("36000");
            result.add("72000");
            result.add("144000");
            result.add("216000");
            result.add("360000");
            result.add("1728000");
            result.add("12096000");
            if (this.hasPermission("fplugin.f.delay.clear")) {
                result.add("clear");
            }
            return result;
        } else if (this.args.length == 3 && this.args[1].matches("^[0-9]{1,8}$")) {
            ArrayList<String> result = new ArrayList<String>();
            if (this.hasPermission("fplugin.f.delay.console")) {
                result.add("console");
            }
            if (this.hasPermission("fplugin.f.delay.other")) {
                Collection<? extends Player> players = Bukkit.getOnlinePlayers();
                for (Player player : players) {
                    result.add(player.getName());
                }
            } else if (this.sender instanceof Player) {
                result.add(this.sender.getName());
            }
            return result;
        } else {
            return new ArrayList<String>();
        }
    }
}
