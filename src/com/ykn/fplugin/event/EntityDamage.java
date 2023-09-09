package com.ykn.fplugin.event;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import com.ykn.fplugin.config.Config;
import com.ykn.fplugin.language.ActionbarLanguage;
import com.ykn.fplugin.language.Language;
import com.ykn.fplugin.message.PersistentMessage;
import com.ykn.fplugin.message.SiegeMessage;
import com.ykn.fplugin.util.Util;

public class EntityDamage implements Listener {

    @EventHandler
    public void onEntityDamage(EntityDamageEvent entityDamageEvent) {

        if (Config.isSiegeMessageActive() && entityDamageEvent.getEntity() instanceof Player) {
            this.showSiegeMessage((Player) entityDamageEvent.getEntity());
        }

    }

    private void showSiegeMessage(Player player) {
        List<Entity> entities = player.getNearbyEntities(Config.getSiegeMonsterDistanceX(), Config.getSiegeMonsterDistanceY(), Config.getSiegeMonsterDistanceZ());
        List<Monster> monsters = new ArrayList<Monster>();
        for (Entity entity : entities) {
            if (entity instanceof Monster) {
                monsters.add((Monster) entity);
            }
        }

        if (monsters.size() < Config.getSiegeMonsterCount()) {
            return;
        }

        SiegeMessage selfMessage = new SiegeMessage(player, "fplugin.siegemessage.self", ActionbarLanguage.getSelfSiegeMessage());
        SiegeMessage otherMessage = new SiegeMessage(player, "fplugin.siegemessage.other", ActionbarLanguage.getOtherSiegeMessage());
        PersistentMessage sMessage = new PersistentMessage(0, Config.getSiegeMessageDuration(), Config.getSiegeMessagePriority(), selfMessage);
        PersistentMessage oMessage = new PersistentMessage(0, Config.getSiegeMessageDuration(), Config.getSiegeMessagePriority(), otherMessage);
        Language.log(4, otherMessage.formatPlaceholders());
        Util.sendUniqueActionbarMessage(player, sMessage, oMessage);
    }
    
}
