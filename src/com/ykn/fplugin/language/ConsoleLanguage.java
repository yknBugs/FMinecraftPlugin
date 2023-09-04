package com.ykn.fplugin.language;

import org.bukkit.entity.Player;

public class ConsoleLanguage extends Language {

    public static void sendPluginEnableMessage() {
        log(3, languageYML.getConfig().getString("console.pluginenable", "FPlugin 插件已启用"));
    }

    public static void sendPluginDisableMessage() {
        log(3, languageYML.getConfig().getString("console.plugindisable", "FPlugin 插件已停用"));
    }

    public static void sendMissingPlayerDataWarning(Player player) {
        String message = languageYML.getConfig().getString("console.missingplayerdata", "缺少玩家 [player] 的数据，已为其重新生成数据");
        message = message.replace("[player]", player.getName());
        log(2, message);
    }
    
}
