package com.ykn.fplugin.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.ykn.fplugin.config.GlobalConfig;
import com.ykn.fplugin.config.command.CmdGUIConfig;
import com.ykn.fplugin.data.GlobalData;
import com.ykn.fplugin.data.PlayerData;
import com.ykn.fplugin.gui.InvGUI;

public class InventoryClick implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            PlayerData playerData = GlobalData.getPlayerData(player);

            //试图点击gui界面
            if (playerData == null) {
                GlobalConfig.getLogger().warning("Missing Player Data: " + player.getName());
            } else {
                if (event.getView().getTitle().equalsIgnoreCase(CmdGUIConfig.title())) {
                    event.setCancelled(true);
                    if (event.getView().getTopInventory() == event.getClickedInventory()) {
                        if (playerData.lastOpenGUI != null && playerData.lastOpenGUI instanceof InvGUI) {
                            InvGUI invGUI = (InvGUI) playerData.lastOpenGUI;
                            invGUI.onClick(event, player, event.getSlot());
                        } else {
                            GlobalConfig.getLogger().warning("Unexpected Player Data [Excepted has Opened InvGUI but not]: " + player.getName());
                        }
                    }
                }
            }
        }
    }
}
