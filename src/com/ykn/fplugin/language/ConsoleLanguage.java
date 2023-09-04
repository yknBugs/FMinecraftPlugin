package com.ykn.fplugin.language;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import com.ykn.fplugin.config.Config;
import com.ykn.fplugin.util.Util;

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

    public static void sendAddKillerTagMessage(Entity entity) {
        String message = languageYML.getConfig().getString("console.addkillertag", "给 [entity] 添加 [tag] 标签");
        message = message.replace("[entity]", Util.getEntityName(entity));
        message = message.replace("[tag]", Config.getEntityKillerTag());
        log(4, message);
    }

    public static void sendNoDeathReasonWarning(Entity entity, DamageCause damageCause) {
        String message = languageYML.getConfig().getString("console.nodeathreason", "[entity] 因为 [reason] 死亡，但并未在配置文件中找到对应的消息内容");
        message = message.replace("[entity]", Util.getEntityName(entity));
        message = message.replace("[reason]", damageCause.name().toLowerCase().replace("_", ""));
        log(2, message);
    }
    
}
