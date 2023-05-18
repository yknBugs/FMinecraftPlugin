package com.ykn.fplugin.function;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.projectiles.ProjectileSource;

import com.ykn.fplugin.config.GlobalConfig;
import com.ykn.fplugin.config.custom.LocationConfig;
import com.ykn.fplugin.config.function.ShootMsgConfig;
import com.ykn.fplugin.data.GlobalData;
import com.ykn.fplugin.util.BroadcastInfo;
import com.ykn.fplugin.util.WorldInfo;

public class ShootMessage {
    public Projectile entity;
    public LivingEntity hitEntity;
    public LivingEntity hitter;
    public int planTick;

    private boolean isAvailable;

    private String a;
    private String v;
    private String p;
    private String l;
    private String w;
    private String x;

    public ShootMessage(ProjectileHitEvent event) {
        this.hitEntity = null;
        this.entity = event.getEntity();
        this.hitter = null;
        this.isAvailable = false;
        this.planTick = 0;

        //被攻击者必须存在
        if (event.getHitEntity() == null) {
            return;
        }

        //被攻击者必须是活实体
        if (!(event.getHitEntity() instanceof LivingEntity)) {
            return;
        }
        this.hitEntity = (LivingEntity) event.getHitEntity();

        //攻击者必须存在
        ProjectileSource projectileSource = this.entity.getShooter();
        if (projectileSource == null) {
            return;
        }

        //攻击者必须是活实体
        if (projectileSource instanceof LivingEntity) {
            this.hitter = (LivingEntity) projectileSource;
        } else {
            return;
        }

        //不能是自己射自己
        if (this.hitter == this.hitEntity) {
            return;
        }

        this.isAvailable = true;
        this.a = WorldInfo.getEntityName(this.hitter);
        this.v = WorldInfo.getEntityName(this.hitEntity);
        this.p = LocationConfig.formatLocation(this.hitter.getLocation());
        this.l = LocationConfig.formatLocation(this.hitEntity.getLocation());
        this.w = WorldInfo.getEntityName(this.entity);
        this.x = LocationConfig.formatDistance(this.hitter.getLocation(), this.hitEntity.getLocation());
    }
    
    public void preInit() {
        if (this.isAvailable) {
            this.planTick = GlobalData.serverData.ticks + 2;
            GlobalData.serverData.scheduledShootMessage.add(this);
        }
    }

    public void show() {
        if (!this.isAvailable) {
            return;
        }

        if (ShootMsgConfig.ignoreDeath() && this.hitEntity.getHealth() <= 0) {
            GlobalConfig.debug("Cancel shoot message because victim has been dead.");
            return;
        }

        List<Player> involvedPlayers = new LinkedList<Player>();
        if (this.hitter instanceof Player) {
            Player player = (Player) this.hitter;
            involvedPlayers.add(player);
            String message = setMessagePlaceholders(ShootMsgConfig.shootOther());
            if (message.charAt(0) == '/') {
                GlobalConfig.debug("Execute shoot message command: " + message);
                BroadcastInfo.executePlaceholderCommand(player, message.substring(1));
            } else if (player.hasPermission("fplugin.shootmessage.shootother")) {
                BroadcastInfo.sendActionbarText(player, BroadcastInfo.formatText(message));
            }
        }

        if (this.hitEntity instanceof Player) {
            Player player = (Player) this.hitEntity;
            involvedPlayers.add(player);
            String message = setMessagePlaceholders(ShootMsgConfig.beingShoot());
            if (message.charAt(0) == '/') {
                GlobalConfig.debug("Execute shoot message command: " + message);
                BroadcastInfo.executePlaceholderCommand(player, message.substring(1));
            } else if (player.hasPermission("fplugin.shootmessage.beingshoot")) {
                BroadcastInfo.sendActionbarText(player, BroadcastInfo.formatText(message));
            }
        }
        
        Collection<? extends Player> players = Bukkit.getOnlinePlayers();
        String message = setMessagePlaceholders(ShootMsgConfig.allShoot());
        if (message.charAt(0) == '/') {
            GlobalConfig.debug("Execute shoot message command: " + message);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), message.substring(1));
            return;
        }
        for (Player player : players) {
            if (player.hasPermission("fplugin.shootmessage.all") && !involvedPlayers.contains(player)) {
                BroadcastInfo.sendActionbarText(player, BroadcastInfo.formatText(message));
            }
        }
        GlobalConfig.debug("Shoot message triggered: " + message);
    }

    private String setMessagePlaceholders(String message) {
        String result = message.replaceAll("\\[a\\]", this.a);
        result = result.replaceAll("\\[v\\]", this.v);
        result = result.replaceAll("\\[p\\]", this.p);
        result = result.replaceAll("\\[l\\]", this.l);
        result = result.replaceAll("\\[w\\]", this.w);
        result = result.replaceAll("\\[x\\]", this.x);
        result = result.replaceAll("\\[h\\]", ShootMsgConfig.formatHealth(this.hitter.getHealth()));
        result = result.replaceAll("\\[d\\]", ShootMsgConfig.formatHealth(this.hitEntity.getHealth()));
        return result;
    }
}
