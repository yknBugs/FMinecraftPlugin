package com.ykn.fplugin.command.message;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ykn.fplugin.config.GlobalConfig;
import com.ykn.fplugin.config.command.CmdMessageConfig;
import com.ykn.fplugin.data.GlobalData;
import com.ykn.fplugin.data.PlayerData;
import com.ykn.fplugin.function.BossbarMessage;
import com.ykn.fplugin.util.BroadcastInfo;
import com.ykn.fplugin.util.WorldInfo;

public class BossbarCmd extends MessageCommand {

    public boolean isAdd;
    public Player player;

    public BossbarCmd(CommandSender sender, Command command, String label, String[] args, int index) {
        super(sender, command, label, args, index);
        this.isAdd = true;
        this.player = null;
    }

    @Override
    public List<String> onTabComplete(String permissionNeed) {
        if (this.args.length == 0) {
            return null;
        } else if (!this.hasPermission(permissionNeed) || !this.isActive()) {
            return new LinkedList<String>();
        } else if (this.isAdd == false) {
            List<String> result = new LinkedList<String>();
            if (this.args.length == this.index + 2) {
                Collection<? extends Player> players = Bukkit.getOnlinePlayers();
                for (Player player : players) {
                    result.add(player.getName());
                }
            }
            return result;
        } else if (this.args.length == this.index + 2) {
            List<String> result = new LinkedList<String>();
            result.add("true");
            result.add("false");
            return result;
        } else if (this.args.length == this.index + 3) {
            List<String> result = new LinkedList<String>();
            result.add("white");
            result.add("blue");
            result.add("green");
            result.add("pink");
            result.add("purple");
            result.add("red");
            result.add("yellow");
            return result;
        } else if (this.args.length == this.index + 4) {
            List<String> result = new LinkedList<String>();
            result.add("solid");
            result.add("segmented6");
            result.add("segmented10");
            result.add("segmented12");
            result.add("segmented20");
            return result;
        } else if (this.args.length == this.index + 5) {
            List<String> result = new LinkedList<String>();
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
            result.add("permanent");
            return result;
        } else if (this.args.length == this.index + 6) {
            List<String> result = new LinkedList<String>();
            if (this.hasPermission("fplugin.f.message.override")) {
                result.add("true");
            }
            result.add("false");
            return result;
        } else {
            return new LinkedList<String>();
        }
    }

    public boolean executeRemove() {
        if (this.args.length == this.index + 1) {
            GlobalData.serverData.serverBossbar.remove();
            return true;
        }
        Player player = WorldInfo.getPlayer(this.args[this.index + 1]);
        if (player == null) {
            String[] holders = { this.args[this.index + 1] };
            BroadcastInfo.replyMessage(this.sender, true, CmdMessageConfig.wrongName(), holders);
            return false;
        }
        PlayerData playerData = GlobalData.getPlayerData(player);
        if (playerData == null) {
            GlobalConfig.getLogger().warning("Missing Player Data: " + player.getName());
            BroadcastInfo.replyMessage(this.sender, false, CmdMessageConfig.missingData(), null);
            return false;
        }
        playerData.playerBossbar.remove();
        return true;
    }

    @Override
    public boolean onLegalExecute(boolean isConsole) {
        if (!this.isActive()) {
            String[] holders = { this.args[1] };
            BroadcastInfo.replyMessage(this.sender, true, CmdMessageConfig.cmdWrongParam(), holders);
            return false;
        }

        if (!isAdd) {
            return this.executeRemove();
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
            BroadcastInfo.replyMessage(this.sender, false, CmdMessageConfig.missingColor(), null);
            return false;
        }

        BarColor barColor = BarColor.WHITE;
        if (this.args[this.index + 2].equalsIgnoreCase("white")) {
            barColor = BarColor.WHITE;
        } else if (this.args[this.index + 2].equalsIgnoreCase("blue")) {
            barColor = BarColor.BLUE;
        } else if (this.args[this.index + 2].equalsIgnoreCase("green")) {
            barColor = BarColor.GREEN;
        } else if (this.args[this.index + 2].equalsIgnoreCase("pink")) {
            barColor = BarColor.PINK;
        } else if (this.args[this.index + 2].equalsIgnoreCase("purple")) {
            barColor = BarColor.PURPLE;
        } else if (this.args[this.index + 2].equalsIgnoreCase("red")) {
            barColor = BarColor.RED;
        } else if (this.args[this.index + 2].equalsIgnoreCase("yellow")) {
            barColor = BarColor.YELLOW;
        } else {
            String[] holders = { this.args[this.index + 2] };
            BroadcastInfo.replyMessage(this.sender, true, CmdMessageConfig.wrongColor(), holders);
            return false;
        }

        if (this.args.length == this.index + 3) {
            BroadcastInfo.replyMessage(this.sender, false, CmdMessageConfig.missingStyle(), null);
            return false;
        }

        BarStyle barStyle = BarStyle.SOLID;
        if (this.args[this.index + 3].equalsIgnoreCase("solid") || this.args[this.index + 3].equalsIgnoreCase("0")) {
            barStyle = BarStyle.SOLID;
        } else if (this.args[this.index + 3].equalsIgnoreCase("segmented6") || this.args[this.index + 3].equalsIgnoreCase("6")) {
            barStyle = BarStyle.SEGMENTED_6;
        } else if (this.args[this.index + 3].equalsIgnoreCase("segmented10") || this.args[this.index + 3].equalsIgnoreCase("10")) {
            barStyle = BarStyle.SEGMENTED_10;
        } else if (this.args[this.index + 3].equalsIgnoreCase("segmented12") || this.args[this.index + 3].equalsIgnoreCase("12")) {
            barStyle = BarStyle.SEGMENTED_12;
        } else if (this.args[this.index + 3].equalsIgnoreCase("segmented20") || this.args[this.index + 3].equalsIgnoreCase("20")) {
            barStyle = BarStyle.SEGMENTED_20;
        } else {
            String[] holders = { this.args[this.index + 3] };
            BroadcastInfo.replyMessage(this.sender, true, CmdMessageConfig.wrongStyle(), holders);
            return false;
        }

        if (this.args.length == this.index + 4) {
            BroadcastInfo.replyMessage(this.sender, false, CmdMessageConfig.missingTime(), null);
            return false;
        }

        int time = 0;
        if (this.args[this.index + 4].matches("^[0-9]{1,8}$")) {
            time = Integer.valueOf(this.args[this.index + 4]);
        } else if (this.args[this.index + 4].equalsIgnoreCase("permanent")) {
            time = -1;
        } else if (this.args[this.index + 4].matches("^[0-9]{9,}$")) {
            String[] holders = { this.args[this.index + 4] };
            BroadcastInfo.replyMessage(this.sender, true, CmdMessageConfig.timeTooLarge(), holders);
            time = -1;
        } else if (this.args[this.index + 4].matches("^-[0-9]+$")) {
            String[] holders = { this.args[this.index + 4] };
            BroadcastInfo.replyMessage(this.sender, true, CmdMessageConfig.timeTooSmall(), holders);
            return false;
        } else {
            String[] holders = { this.args[this.index + 4] };
            BroadcastInfo.replyMessage(this.sender, true, CmdMessageConfig.wrongTime(), holders);
            return false;
        }

        if (this.args.length == this.index + 5) {
            BroadcastInfo.replyMessage(this.sender, false, CmdMessageConfig.missingOr(), null);
            return false;
        }

        boolean isOverride = false;
        if (this.args[this.index + 5].equalsIgnoreCase("false")) {
            isOverride = false;
        } else if (this.args[this.index + 5].equalsIgnoreCase("true")) {
            if (!this.hasPermission("fplugin.f.message.override")) {
                String[] holders = { this.args[this.index + 5] };
                BroadcastInfo.replyMessage(this.sender, false, CmdMessageConfig.noOrPermission(), holders);
                return false;
            }
            isOverride = true;
        } else if (this.args[this.index + 5].equalsIgnoreCase("0")) {
            isOverride = false;
        } else if (this.args[this.index + 5].equalsIgnoreCase("1")) {
            if (!this.hasPermission("fplugin.f.message.override")) {
                String[] holders = { this.args[this.index + 5] };
                BroadcastInfo.replyMessage(this.sender, false, CmdMessageConfig.noOrPermission(), holders);
                return false;
            }
            isOverride = true;
        } else {
            String[] holders = { this.args[this.index + 5] };
            BroadcastInfo.replyMessage(this.sender, false, CmdMessageConfig.wrongOr(), holders);
            return false;
        }

        if (this.args.length == this.index + 6) {
            BroadcastInfo.replyMessage(this.sender, false, CmdMessageConfig.missingText(), null);
            return false;
        }

        int i = this.index + 6;
        String msg = this.args[i];
        for (i = this.index + 7; i < this.args.length; i++) {
            msg = msg + " " + this.args[i];
        }

        if ((!this.hasPermission("fplugin.f.message.unsafe")) && (!BroadcastInfo.isTextSafe(msg))) {
            String[] holders = { msg };
            BroadcastInfo.replyMessage(this.sender, true, CmdMessageConfig.noSafePermission(), holders);
            return false;
        }

        BossbarMessage bossbarMessage = new BossbarMessage(barColor, barStyle, time, msg);
        bossbarMessage.override = isOverride;
        if (time == -1) {
            bossbarMessage.maxValue = 100;
            bossbarMessage.isPermanent = true;
        }
        if (hasPrefix) {
            bossbarMessage.title = GlobalConfig.getPrefix() + msg;
        } 
        if (this.player == null) {
            if (this.sender instanceof Player) {
                bossbarMessage.player = (Player) this.sender;
            }
            if (GlobalData.serverData.serverBossbar.isShow) {
                if (this.hasPermission("fplugin.f.message.remove")) {
                    GlobalData.serverData.serverBossbar.remove();
                    if (time == 0) {
                        GlobalData.serverData.serverBossbar = bossbarMessage;
                        GlobalData.serverData.serverBossbar.maxValue = 100;
                        GlobalData.serverData.serverBossbar.showTick = GlobalData.serverData.ticks;
                        return true;
                    }
                } else {
                    String[] holders = { this.args[this.index] };
                    BroadcastInfo.replyMessage(this.sender, true, CmdMessageConfig.duplicateError(), holders);
                    return false;
                }
            }
            GlobalData.showBossbarMessage(bossbarMessage);
        } else {
            PlayerData playerData = GlobalData.getPlayerData(player);
            if (playerData == null) {
                GlobalConfig.getLogger().warning("Missing Player Data: " + player.getName());
                BroadcastInfo.replyMessage(this.sender, false, CmdMessageConfig.missingData(), null);
                return false;
            }
            if (playerData.playerBossbar.isShow) {
                if (this.hasPermission("fplugin.f.message.remove")) {
                    playerData.playerBossbar.remove();
                    if (time == 0) {
                        playerData.playerBossbar = bossbarMessage;
                        playerData.playerBossbar.maxValue = 100;
                        playerData.playerBossbar.showTick = GlobalData.serverData.ticks;
                        return true;
                    }
                } else {
                    String[] holders = { this.args[this.index] };
                    BroadcastInfo.replyMessage(this.sender, true, CmdMessageConfig.duplicateError(), holders);
                    return false;
                }
            }
            GlobalData.showBossbarMessage(playerData, bossbarMessage);
        }

        return true;
    }
    
    
}
