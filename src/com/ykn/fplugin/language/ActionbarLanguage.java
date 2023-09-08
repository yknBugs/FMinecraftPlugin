package com.ykn.fplugin.language;

import org.bukkit.entity.Player;

import com.ykn.fplugin.util.Util;

public class ActionbarLanguage extends Language {

    public static String getShootMessage() {
        return languageYML.getConfig().getString("actionbar.projectile.hitother", "你击中了 [distance] 米外的 [hitEntity] (生命值: [entityHealth])");
    }

    public static String getBeingShootMessage() {
        return languageYML.getConfig().getString("actionbar.projectile.beinghit", "你被 [shooter] 击中");
    }

    public static String getShootAllMessage() {
        return languageYML.getConfig().getString("actionbar.projectile.all", "位于 [entityLocation] 的 [hitEntity] (生命值: [entityHealth]) 被 [shooter] 击中"); 
    }

    public static String getSelfBiomeMessage(Player player) {
        String message = languageYML.getConfig().getString("actionbar.biome.self", "已来到 [biome]");
        message = message.replace('&', '\u00a7');
        message = message.replace("[biome]", player.getLocation().getBlock().getBiome().name().toLowerCase().replace('_', ' '));
        message = message.replace("[player]", Util.getEntityName(player));
        return message;
    }

    public static String getOtherBiomeMessage(Player player) {
        String message = languageYML.getConfig().getString("actionbar.biome.other", "[player] 到了 [biome]");
        message = message.replace('&', '\u00a7');
        message = message.replace("[biome]", player.getLocation().getBlock().getBiome().name().toLowerCase().replace('_', ' '));
        message = message.replace("[player]", Util.getEntityName(player));
        return message;
    }
    
}
