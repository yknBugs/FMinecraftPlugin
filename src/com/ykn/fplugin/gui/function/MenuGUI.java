package com.ykn.fplugin.gui.function;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import com.ykn.fplugin.config.GlobalConfig;
import com.ykn.fplugin.config.command.CmdGUIConfig;
import com.ykn.fplugin.config.custom.ItemConfig;
import com.ykn.fplugin.data.PlayerData;
import com.ykn.fplugin.gui.InvGUI;
import com.ykn.fplugin.util.CustomItem;

public class MenuGUI extends InvGUI {

    public Player owner;

    public MenuGUI() {
        super();
        this.owner = null;
    }

    public MenuGUI(String title, int line, Player owner) {
        super(title, line);
        this.owner = owner;
    }

    @Override
    public void init(String title, int line) {
        super.init(title, line);
        this.owner = null;
    }

    public void init(String title, int line, Player owner) {
        super.init(title, line);
        this.owner = owner;
    }

    public boolean isOwnerAvailable(PlayerData playerData) {
        if (this.owner == null) {
            return false;
        } 
        if (!this.owner.isOnline()) {
            return false;
        }
        if (playerData == null) {
            return false;
        }
        return true;
    }

    /**
     * 获取预设好的物品
     * @param preset 预设参数
     * @return 物品。若无物品则返回null
     */
    public ItemStack getPresetItem(String preset) {
        //TODO: loginlocation and lastsaftlocation need support
        //Plan: Support Item Placeholders
        if (preset.equalsIgnoreCase("copyright")) {
            return CustomItem.createItem(Material.PAPER, 1, ChatColor.YELLOW + GlobalConfig.copyright);
        } else if (preset.equalsIgnoreCase("air")) {
            return null;
        } else {
            return ItemConfig.getItem(preset);
        }
    }

    /**
     * 通过预设的物品列表来设置物品栏gui内的物品
     * @param itemList 预设列表
     */
    public void setPresetItem(List<String> itemList) {
        int i = 0;
        for (String itemPreset : itemList) {
            ItemStack item = getPresetItem(itemPreset);
            this.gui.setItem(i, item);
            i++;
            if (i >= this.gui.getSize()) {
                break;
            }
        }        
    }

    @Override
    public void onClick(InventoryClickEvent event, Player player, int slot) {
        List<String> itemPreset = CmdGUIConfig.itemList();
        String item = "air";
        if (slot < itemPreset.size()) {
            item = itemPreset.get(slot);
        }

        if (item.equalsIgnoreCase("air")) {
            return;
        } else {
            GlobalConfig.debug("Player " + player.getName() + " click gui slot "+ slot + " item " + item);
        }
        
        
        if (item.equalsIgnoreCase("copyright")) {
            return;
        } else {
            if (ItemConfig.closeGUI(item)) {
                player.closeInventory();
            }

            ItemConfig.executeCommands(player, item);
        }

    }
}
