package com.ykn.fplugin.util;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import com.ykn.fplugin.config.GlobalConfig;

public class CustomItem {
    public static ItemStack createItem(Material type, int amount) {
        return new ItemStack(type, amount);
    }

    public static ItemStack createItem(Material type, int amount, String displayName) {
        ItemStack itemStack = new ItemStack(type, amount);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(displayName);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack createItem(Material type, int amount, String displayName, String lore) {
        List<String> lores = new LinkedList<String>();
        String[] loreList = lore.split("\n");
        for (String lString : loreList) {
            lores.add(lString);
        }

        ItemStack itemStack = new ItemStack(type, amount);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(lores);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    /**
     * 根据物品的描述创建物品。描述应当遵守以下格式:
     * <p>物品名称 物品数量 name:物品名称 lore:物品注释 damage:损坏值
     * <p>前两项必须在相应位置 后面的参数可以交换位置 
     * <p>参数中，空格用下划线[_]代替，换行用竖线[|]代替，支持[&]颜色符号
     * @param description 物品描述
     * @return 物品，没有对应的物品则返回null
     */
    public static ItemStack createItem(String description) {
        String[] descriptionList = description.split(" ");
        Material material = Material.getMaterial(descriptionList[0].toUpperCase());
        if (material == null) {
            GlobalConfig.debug("Translate item description: " + description);
            GlobalConfig.getLogger().warning("No material named " + descriptionList[0]);
            return null;
        }
        ItemStack itemStack = new ItemStack(material, Integer.valueOf(descriptionList[1]));
        for (int i = 2; i < descriptionList.length; i++) {
            String[] label = descriptionList[i].split(":", 2);
            ItemMeta itemMeta = itemStack.getItemMeta();

            if (label[0].equalsIgnoreCase("name")) {
                itemMeta.setDisplayName(label[1].replace('_', ' ').replace('&', '\u00a7'));
            } else if (label[0].equalsIgnoreCase("lore")) {
                List<String> lores = new LinkedList<String>();
                String[] loreList = label[1].split("\\|");
                for (String lString : loreList) {
                    lores.add(lString.replace('_', ' ').replace('&', '\u00a7'));
                }
                itemMeta.setLore(lores);
            } else if (label[0].equalsIgnoreCase("damage")) {
                if (itemMeta instanceof Damageable) {
                    ((Damageable) itemMeta).setDamage(Integer.valueOf(label[1]));
                }
            }

            itemStack.setItemMeta(itemMeta);
        }

        return itemStack;
    }
    
}
