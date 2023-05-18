package com.ykn.fplugin.config.custom;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.ykn.fplugin.config.FConfig;
import com.ykn.fplugin.config.GlobalConfig;
import com.ykn.fplugin.util.BroadcastInfo;
import com.ykn.fplugin.util.CustomItem;

public class ItemConfig extends FConfig {
    public static ItemStack getItem(String preset) {
        return CustomItem.createItem(getString("customitem." + preset + ".item"));
    }
    
    public static List<String> getCommands(String itemPreset) {
        return getStringList("customitem." + itemPreset + ".commands");
    }
    
    public static boolean needPermission(String itemPreset) {
        return getBoolean("customitem." + itemPreset + ".needpermission");
    }

    public static boolean closeGUI(String itemPreset) {
        return getBoolean("customitem." + itemPreset + ".closegui");
    }

    public static void executeCommands(Player player, String itemPreset) {
        boolean needpermission = needPermission(itemPreset);
        if (needpermission) {
            List<String> cmdList = getCommands(itemPreset);
            for (String cmd : cmdList) {
                GlobalConfig.debug("Player " + player.getName() + " execute command: " + cmd);
                BroadcastInfo.performCommand(player, cmd);
            }
        } else {
            List<String> cmdList = getCommands(itemPreset);
            for (String cmd : cmdList) {
                GlobalConfig.debug("Console " + player.getName() + " execute command: " + cmd);
                BroadcastInfo.executePlaceholderCommand(player, cmd);
            }
        }
    }
}
