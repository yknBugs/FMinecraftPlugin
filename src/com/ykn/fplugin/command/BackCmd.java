package com.ykn.fplugin.command;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ykn.fplugin.config.command.CmdBackConfig;
import com.ykn.fplugin.config.custom.LocationConfig;
import com.ykn.fplugin.data.GlobalData;
import com.ykn.fplugin.data.PlayerData;
import com.ykn.fplugin.util.BroadcastInfo;

public class BackCmd extends FCommand {

    public BackCmd(CommandSender sender, Command command, String label, String[] args, int index) {
        super(sender, command, label, args, index);
    }

    @Override
    public boolean isActive() {
        return CmdBackConfig.isCmdActive("back");
    }

    @Override
    public boolean onConsoleExecute() {
        String[] holders = { "/f " + this.args[0] };
        if (!this.isActive()) {
            BroadcastInfo.replyMessage(this.sender, true, CmdBackConfig.cmdWrongParam(), holders);
            return false;
        }
        BroadcastInfo.replyMessage(this.sender, true, CmdBackConfig.cmdOnlyPlayerUse(), holders);
        return false;
    }

    @Override
    public boolean onPlayerLegalExecute() {
        if (!this.isActive()) {
            String[] holders = { "/f " + this.args[0] };
            BroadcastInfo.replyMessage(this.sender, true, CmdBackConfig.cmdWrongParam(), holders);
            return false;
        }
        Player player = (Player) this.sender;
        PlayerData playerData = GlobalData.getPlayerData(player);
        if (playerData == null) {
            CmdBackConfig.getLogger().warning("Missing Player Data: " + player.getName());
            BroadcastInfo.replyMessage(this.sender, false, CmdBackConfig.missingData(), null);
            return false;
        }
        Location location = playerData.backLocation;
        playerData.backLocation = player.getLocation();
        playerData.player.teleport(location);
        String[] holders = { Integer.toString((GlobalData.serverData.ticks - playerData.backTick) / 20),
            LocationConfig.formatLocation(location) };
        playerData.backTick = GlobalData.serverData.ticks;
        BroadcastInfo.replyMessage(this.sender, true, CmdBackConfig.message(), holders);
        return true;
    }
}
