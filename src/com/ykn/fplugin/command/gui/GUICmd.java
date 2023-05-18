package com.ykn.fplugin.command.gui;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ykn.fplugin.command.FCommand;
import com.ykn.fplugin.config.GlobalConfig;
import com.ykn.fplugin.config.command.CmdGUIConfig;
import com.ykn.fplugin.gui.function.MenuGUI;
import com.ykn.fplugin.util.BroadcastInfo;

public class GUICmd extends FCommand {

    public GUICmd(CommandSender sender, Command command, String label, String[] args, int index) {
        super(sender, command, label, args, index);
    }

    @Override
    public boolean isActive() {
        return CmdGUIConfig.isCmdActive("gui");
    }

    @Override
    public boolean onConsoleExecute() {
        String[] holders = { "/f " + this.args[0] };
        if (!this.isActive()) {
            BroadcastInfo.replyMessage(this.sender, true, CmdGUIConfig.cmdWrongParam(), holders);
        } else {
            BroadcastInfo.replyMessage(this.sender, true, CmdGUIConfig.cmdOnlyPlayerUse(), holders);
        }
        return false;
    }
    
    @Override
    public boolean onPlayerLegalExecute() {
        if (!this.isActive()) {
            String[] holders = { "/f " + this.args[0] };
            BroadcastInfo.replyMessage(this.sender, true, CmdGUIConfig.cmdWrongParam(), holders);
            return false;
        }
        Player player = (Player) this.sender;
        GlobalConfig.debug("Showing gui to player: " + player.getName());
        MenuGUI menuGUI = new MenuGUI(CmdGUIConfig.title(), CmdGUIConfig.slotCount(), player);
        menuGUI.setPresetItem(CmdGUIConfig.itemList());
        menuGUI.show(player);
        return true;
    }
}
