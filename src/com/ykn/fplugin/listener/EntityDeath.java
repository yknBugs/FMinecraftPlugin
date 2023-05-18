package com.ykn.fplugin.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.ykn.fplugin.config.function.DeathMsgConfig;
import com.ykn.fplugin.function.EntityDeathMessage;
import com.ykn.fplugin.function.PlayerRelatedInfo;

public class EntityDeath implements Listener {

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event instanceof PlayerDeathEvent) {
            PlayerRelatedInfo.onDeath((PlayerDeathEvent) event);
        }

        if (event.getEntity() instanceof Player) {
            EntityDeathMessage.matchKiller(event.getEntity().getLastDamageCause());
        } else if (DeathMsgConfig.active()) {
            (new EntityDeathMessage(event.getEntity())).show();
        }
    }
    
}
