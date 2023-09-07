package com.ykn.fplugin.language;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.checkerframework.checker.units.qual.m;

import com.ykn.fplugin.config.LocationConfig;
import com.ykn.fplugin.util.Util;

public class TextLanguage extends Language {

    public static String getDeathMessage(LivingEntity entity, Entity killer, Player damager, DamageCause damageCause) {
        if (damageCause == null) {
            return languageYML.getConfig().getString("deathmessage.noreason", "[entity] 死了");
        }

        String damage = damageCause.name().toLowerCase().replace("_", "");
        String message = null;
        String biome = entity.getLocation().getBlock().getBiome().name().toLowerCase().replace('_', ' ');

        if (entity instanceof Player) {
            //玩家死亡
            message = languageYML.getConfig().getString("deathmessage.player", "[entity] 的死亡坐标为 [location] ([biome])");
            message = message.replace('&', '\u00a7');
            message = message.replace("[location]", LocationConfig.formatLocation(entity.getLocation()));
            message = message.replace("[biome]", biome);
            if (damager != null && killer != damager) {
                message = message.replace("[damager]", Util.getEntityName(damager));
            }
            if (killer != null) {
                message = message.replace("[killer]", Util.getEntityName(killer));
            }
        } else if (killer == null && damager == null) {
            //两者都无
            message = languageYML.getConfig().getString("deathmessage." + damage + ".nokiller");
            if (message == null) {
                ConsoleLanguage.sendNoDeathReasonWarning(entity, damageCause);
                message = languageYML.getConfig().getString("deathmessage.unknownreason.nokiller", "[entity] 被杀死了");
            }
            message = message.replace('&', '\u00a7');
            message = message.replace("[location]", LocationConfig.formatLocation(entity.getLocation()));
            message = message.replace("[biome]", biome);
        } else if (killer == null && damager != null) {
            //无攻击者，有在战斗中的玩家，为 #1 在与 #3 的战斗中xxx
            message = languageYML.getConfig().getString("deathmessage." + damage + ".onlydamager");
            if (message == null) {
                ConsoleLanguage.sendNoDeathReasonWarning(entity, damageCause);
                message = languageYML.getConfig().getString("deathmessage.unknownreason.onlydamager", "[entity] 在与 [damager] 的战斗中被杀");
            }
            message = message.replace('&', '\u00a7');
            message = message.replace("[location]", LocationConfig.formatLocation(entity.getLocation()));
            message = message.replace("[biome]", biome);
            message = message.replace("[damager]", Util.getEntityName(damager));
        } else if (killer != null && damager == null) {
            //有攻击者，无在战斗中的玩家，为 #1 被 #2 xxx
            message = languageYML.getConfig().getString("deathmessage." + damage + ".onlykiller");
            if (message == null) {
                ConsoleLanguage.sendNoDeathReasonWarning(entity, damageCause);
                message = languageYML.getConfig().getString("deathmessage.unknownreason.onlykiller", "[entity] 被 [killer] 杀死了");
            }
            message = message.replace('&', '\u00a7');
            message = message.replace("[location]", LocationConfig.formatLocation(entity.getLocation()));
            message = message.replace("[biome]", biome);
            message = message.replace("[killer]", Util.getEntityName(killer));
        } else if (killer != null && damager != null && killer != damager) {
            //两者都有但两者不一样，为 #1 在与 #3 的战斗中被 #2 xxx
            message = languageYML.getConfig().getString("deathmessage." + damage + ".both");
            if (message == null) {
                ConsoleLanguage.sendNoDeathReasonWarning(entity, damageCause);
                message = languageYML.getConfig().getString("deathmessage.unknownreason.both", "[entity] 在与 [damager] 的战斗中被 [killer] 杀死了");
            }
            message = message.replace('&', '\u00a7');
            message = message.replace("[location]", LocationConfig.formatLocation(entity.getLocation()));
            message = message.replace("[biome]", biome);
            message = message.replace("[damager]", Util.getEntityName(damager));
            message = message.replace("[killer]", Util.getEntityName(killer));
        } else {
            //攻击者为玩家，为 #1 被 #2 xxx
            message = languageYML.getConfig().getString("deathmessage." + damage + ".onlykiller");
            if (message == null) {
                ConsoleLanguage.sendNoDeathReasonWarning(entity, damageCause);
                message = languageYML.getConfig().getString("deathmessage.unknownreason.onlykiller", "[entity] 被 [killer] 杀死了");
            }
            message = message.replace('&', '\u00a7');
            message = message.replace("[location]", LocationConfig.formatLocation(entity.getLocation()));
            message = message.replace("[biome]", biome);
            message = message.replace("[killer]", Util.getEntityName(killer));
        }
        message = message.replace("[entity]", Util.getEntityName(entity));
        // 重命名攻击者实体可能导致通过占位符注入的bug [不严重]

        return message;
    }

    public static String getAfkInformMessage(Player player, int ticks) {
        return getAfkMessage(player, ticks, "afk.inform", "你已挂机 [seconds] 秒");
    }

    public static String getAfkBroadcastMessage(Player player, int ticks) {
        return getAfkMessage(player, ticks, "afk.broadcast", "[player] 暂时离开了");
    }

    public static String getAfkBackMessage(Player player, int ticks) {
        return getAfkMessage(player, ticks, "afk.back", "[player] 挂机 [seconds] 秒后回来了");
    }

    private static String getAfkMessage(Player player, int ticks, String path, String def) {
        String message = languageYML.getConfig().getString(path, def);
        message = message.replace('&', '\u00a7');
        message = message.replace("[ticks]", Integer.toString(ticks));
        message = message.replace("[seconds]", Integer.toString(ticks / 20));
        message = message.replace("[health]", LocationConfig.formatDistance(player.getHealth()));
        message = message.replace("[hunger]", Integer.toString(player.getFoodLevel()));
        message = message.replace("[location]", LocationConfig.formatLocation(player.getLocation()));
        message = message.replace("[biome]", player.getLocation().getBlock().getBiome().name().toLowerCase().replace('_', ' '));
        message = message.replace("[player]", Util.getEntityName(player));
        return message;
    }
    
}
