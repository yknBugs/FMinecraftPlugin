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

    public static String getSelfSiegeMessage() {
        return languageYML.getConfig().getString("actionbar.siege.self", "你附近有 [entity] 等 [count] 个怪物"); 
    }

    public static String getOtherSiegeMessage() {
        return languageYML.getConfig().getString("actionbar.siege.other", "位于 [location] 的 [player] (生命值: [playerhealth]) 附近有 [entity] 等 [count] 个怪物"); 
    }

    public static String getSelfBossMessage() {
        return languageYML.getConfig().getString("actionbar.boss.self", "[entity] (生命值: [entityHealth] / [entityMaxHealth]) 距离你 [distance] 米"); 
    }

    public static String getOtherBossMessage() {
        return languageYML.getConfig().getString("actionbar.boss.other", "[player] (生命值: [playerHealth]) 正在与位于 [location] 的 [entity] (生命值: [entityHealth] / [entityMaxHealth]) 战斗"); 
    }
    
}
