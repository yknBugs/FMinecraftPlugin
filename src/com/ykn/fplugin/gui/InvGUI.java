package com.ykn.fplugin.gui;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.ykn.fplugin.config.GlobalConfig;
import com.ykn.fplugin.data.GlobalData;
import com.ykn.fplugin.data.PlayerData;
import com.ykn.fplugin.gui.base.GUI;

public class InvGUI implements GUI {
    public Inventory gui;

    public InvGUI() {
        this.gui = null;
    }

    public InvGUI(String title, int line) {
        if (line < 8) {
            this.gui = Bukkit.createInventory(null, line * 9, title);
        } else {
            this.gui = Bukkit.createInventory(null, line, title);
        }
    }

    /**
     * 创建物品栏GUI
     * @param title 物品栏GUI的名称
     * @param line 物品栏的格子数量，可输入行数或总格子数，总格子数必须为9的倍数
     */
    public void init(String title, int line) {
        if (line < 8) {
            this.gui = Bukkit.createInventory(null, line * 9, title);
        } else {
            this.gui = Bukkit.createInventory(null, line, title);
        }
    }

    @Override
    public boolean isAvailable() {
        if (this.gui == null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean show(Player player) {
        if (!isAvailable()) {
            return false;
        }

        //设置玩家数据为已经打开物品栏状态
        PlayerData playerData = GlobalData.getPlayerData(player);
        if (playerData == null || player.isOnline() == false) {
            return false;
        } else {
            GlobalConfig.debug("Open InvGUI: " + player.getName());
            playerData.lastOpenGUI = this;
            player.openInventory(gui);
        }

        return true;
    }

    @Override
    public void onClose(Player player) {
        
    }

    public void setItem(List<ItemStack> itemList) {
        int i = 0;
        for (ItemStack item : itemList) {
            this.gui.setItem(i, item);
            i++;
            if (i >= this.gui.getSize()) {
                break;
            }
        }
    }

    public void onClick(InventoryClickEvent event, Player player, int slot) {
        GlobalConfig.debug("Player " + player.getName() + " click gui slot "+ slot);
    }
}
