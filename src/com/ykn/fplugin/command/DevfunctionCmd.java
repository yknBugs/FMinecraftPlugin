package com.ykn.fplugin.command;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ykn.fplugin.config.GlobalConfig;
import com.ykn.fplugin.config.command.CmdDevfunctionConfig;
import com.ykn.fplugin.config.custom.LocationConfig;
import com.ykn.fplugin.data.GlobalData;
import com.ykn.fplugin.data.PlayerData;
import com.ykn.fplugin.util.BroadcastInfo;

public class DevfunctionCmd extends FCommand {

    public DevfunctionCmd(CommandSender sender, Command command, String label, String[] args, int index) {
        super(sender, command, label, args, index);
    }

    private void testFunction() {
        PlayerData playerData = GlobalData.getPlayerData((Player) this.sender);
        BroadcastInfo.broadcastMessage(playerData.player, "死亡坐标保存的相关信息:");
        int i = 0;
        for (Location location : playerData.deathLocations) {
            BroadcastInfo.broadcastMessage(
                playerData.player, 
                "- " + i + ". Pos: " + LocationConfig.formatLocation(location) + "  Safe Pos: "
                + LocationConfig.formatLocation(playerData.safeDeathLocations.get(i)) + "  Is Safe: "
                + playerData.isDeathLocationsSafe.get(i) + "  Time: " +
                LocationConfig.formatTime(playerData.deathTimes.get(i)) +  " Message: " +
                 playerData.deathMessages.get(i));
            i++;
        }
        BroadcastInfo.broadcastMessage(playerData.player, "找到 " + i + " 条记录。");
    }

    @Override
    public boolean isActive() {
        if (CmdDevfunctionConfig.isCmdActive("devfunction") && GlobalConfig.debug()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean onConsoleExecute() {
        if (!this.isActive()) {
            String[] holders = { "/f " + this.args[0] };
            BroadcastInfo.replyMessage(this.sender, true, CmdDevfunctionConfig.cmdWrongParam(), holders);
            return false;
        } 
        GlobalConfig.getLogger().info(CmdDevfunctionConfig.startMessage());
        try {
            this.testFunction();
        } catch (Exception e) {
            GlobalConfig.getLogger().warning(CmdDevfunctionConfig.errorMessage());
            if (CmdDevfunctionConfig.printStackTrace()) {
                e.printStackTrace();
            }
        }
        GlobalConfig.getLogger().info(CmdDevfunctionConfig.endMessage());
        return true;
    }

    @Override
    public boolean onPlayerLegalExecute() {
        if (!this.isActive()) {
            String[] holders = { "/f " + this.args[0] };
            BroadcastInfo.replyMessage(this.sender, true, CmdDevfunctionConfig.cmdWrongParam(), holders);
            return false;
        }
        BroadcastInfo.replyMessage(this.sender, false, CmdDevfunctionConfig.startMessage(), null);
        try {
            this.testFunction();
        } catch (Exception e) {
            GlobalConfig.getLogger().warning(CmdDevfunctionConfig.errorMessage());
            BroadcastInfo.replyMessage(this.sender, false, CmdDevfunctionConfig.errorMessage(), null);
            if (CmdDevfunctionConfig.printStackTrace()) {
                e.printStackTrace();
            }
            if (CmdDevfunctionConfig.ingameStackTrace()) {
                StringWriter stringWriter= new StringWriter();
                PrintWriter printWriter = new PrintWriter(stringWriter);
                e.printStackTrace(printWriter);
                String[] msg = stringWriter.toString().split("\\n");
                for (String str : msg) {
                    this.sender.sendMessage(GlobalConfig.getPrefix() + str);
                }
            }
        }
        BroadcastInfo.replyMessage(this.sender, false, CmdDevfunctionConfig.endMessage(), null);
        return true;
    }
}
