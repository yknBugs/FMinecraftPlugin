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
        if (Config.isShootMessageActive() && hitEntity != null && hitEntity instanceof LivingEntity 
        && shooter != null && shooter instanceof LivingEntity && shooter != hitEntity) {
            this.showShootMessage(projectile, (LivingEntity) hitEntity, (LivingEntity) shooter);
        }

    }

    private void showShootMessage(Projectile projectile, LivingEntity hitEntity, LivingEntity shooter) {
        if (!hitEntity.isValid() || !shooter.isValid() || hitEntity.getWorld() != shooter.getWorld()) {
            return;
        }
        Collection<? extends Player> players = Bukkit.getServer().getOnlinePlayers();
        for (Player player : players) {
            String message = null;
            ShootMessage shootMessage = null;
            if (Config.isHitOtherMessageActive() && player == shooter) {
                message = ActionbarLanguage.getShootMessage();
                shootMessage = new ShootMessage("fplugin.shootmessage.hitother", message, projectile, hitEntity, shooter);
            } else if (Config.isBeingHitMessageActive() && player == hitEntity) {
                message = ActionbarLanguage.getBeingShootMessage();
                shootMessage = new ShootMessage("fplugin.shootmessage.beinghit", message, projectile, hitEntity, shooter);
            } else if (Config.isAllShootMessageActive()) {
                message = ActionbarLanguage.getShootAllMessage();
                shootMessage = new ShootMessage("fplugin.shootmessage.all", message, projectile, hitEntity, shooter);
            }

            if (message == null || shootMessage == null) {
                continue;
            }
            PersistentMessage pMessage = new PersistentMessage(0, Config.getShootMessageDuration(), Config.getShootMessagePriority(), shootMessage);
            Util.replaceActionbarPersistentMessage(player, pMessage);
        }
    }

}
