package com.ykn.fplugin.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

import com.ykn.fplugin.config.function.ShootMsgConfig;
import com.ykn.fplugin.function.ShootMessage;

public class ProjectileHit implements Listener {
    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (ShootMsgConfig.active()) {
            (new ShootMessage(event)).preInit();
        }
    }
}
