package com.ykn.fplugin.event;

import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.projectiles.ProjectileSource;

import com.ykn.fplugin.config.Config;
import com.ykn.fplugin.language.ActionbarLanguage;
import com.ykn.fplugin.message.PersistentMessage;
import com.ykn.fplugin.message.ShootMessage;
import com.ykn.fplugin.util.Util;

public class ProjectileHit implements Listener {
    
    @EventHandler
    public void onProjectileHit(ProjectileHitEvent projectileHitEvent) {

        Projectile projectile = projectileHitEvent.getEntity();
        Entity hitEntity = projectileHitEvent.getHitEntity();
        ProjectileSource shooter = projectile.getShooter();
        if (Config.isActiveShootMessage() && hitEntity != null && hitEntity instanceof LivingEntity 
        && shooter != null && shooter instanceof LivingEntity && shooter != hitEntity) {
            showShootMessage(projectile, (LivingEntity) hitEntity, (LivingEntity) shooter);
        }

    }

    private void showShootMessage(Projectile projectile, LivingEntity hitEntity, LivingEntity shooter) {
        Collection<? extends Player> players = Bukkit.getServer().getOnlinePlayers();
        for (Player player : players) {
            String message;
            ShootMessage shootMessage;
            if (player == shooter) {
                message = ActionbarLanguage.getShootMessage();
                shootMessage = new ShootMessage("fplugin.shootmessage.hitother", message, projectile, hitEntity, shooter);
            } else if (player == hitEntity) {
                message = ActionbarLanguage.getBeingShootMessage();
                shootMessage = new ShootMessage("fplugin.shootmessage.beinghit", message, projectile, hitEntity, shooter);
            } else {
                message = ActionbarLanguage.getShootAllMessage();
                shootMessage = new ShootMessage("fplugin.shootmessage.all", message, projectile, hitEntity, shooter);
            }

            Util.replaceActionbarPersistentMessage(player, new PersistentMessage(0, 100, Config.getShootMessagePriority(), shootMessage));
        }
    }

}
