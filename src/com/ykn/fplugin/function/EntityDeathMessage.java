package com.ykn.fplugin.function;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.projectiles.ProjectileSource;

import com.ykn.fplugin.config.GlobalConfig;
import com.ykn.fplugin.config.custom.LocationConfig;
import com.ykn.fplugin.config.function.DeathMsgConfig;
import com.ykn.fplugin.util.BroadcastInfo;
import com.ykn.fplugin.util.WorldInfo;

public class EntityDeathMessage {
    public LivingEntity entity;
    public EntityDamageEvent entityDamageEvent;
    public Entity killer;
    public Player damager;
    public String deathMessage;
    public double entityMaxHealth;

    public EntityDeathMessage(LivingEntity entity) {
        this.entity = entity;
        this.entityDamageEvent = entity.getLastDamageCause();
        this.killer = null;
        this.damager = entity.getKiller();
        this.deathMessage = DeathMsgConfig.noReasonDeath();
        this.entityMaxHealth = entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();

        //获取被杀实体的最后一次受击情况
        if (this.entityDamageEvent == null) {
            formatDeathMessage();
            return;
        }

        //获取攻击者
        if (this.entityDamageEvent instanceof EntityDamageByEntityEvent) {
            this.killer = ((EntityDamageByEntityEvent) entityDamageEvent).getDamager();
            this.killer = translateKiller(this.killer);
        }

        formatDeathMessage();
    }

    private void formatDeathMessage() {
        if (this.entityDamageEvent == null) {
            //没有伤害事件
            this.deathMessage = DeathMsgConfig.noReasonDeath();
        } else if (this.killer == null && this.damager == null) {
            //两者都无
            this.deathMessage = DeathMsgConfig.noKillerDeath(getDeathReason(this.entityDamageEvent.getCause()));
        } else if (killer == null && damager != null) {
            //无攻击者，有在战斗中的玩家，为 #1 在与 #3 的战斗中xxx
            this.deathMessage = DeathMsgConfig.onlyDamagerDeath(getDeathReason(this.entityDamageEvent.getCause()));
            this.deathMessage = this.deathMessage.replaceAll("\\[3\\]", WorldInfo.getEntityName(this.damager));
        } else if (killer != null && damager == null) {
            //有攻击者，无在战斗中的玩家，为 #1 被 #2 xxx
            this.deathMessage = DeathMsgConfig.onlyKillerDeath(getDeathReason(this.entityDamageEvent.getCause()));
            this.deathMessage = this.deathMessage.replaceAll("\\[2\\]", WorldInfo.getEntityName(this.killer));
        } else if (killer != null && damager != null && (!(killer instanceof Player))) {
            //两者都有但两者不一样，为 #1 在与 #3 的战斗中被 #2 xxx
            this.deathMessage = DeathMsgConfig.bothKillerDeath(getDeathReason(this.entityDamageEvent.getCause()));
            this.deathMessage = this.deathMessage.replaceAll("\\[2\\]", WorldInfo.getEntityName(this.killer));
            this.deathMessage = this.deathMessage.replaceAll("\\[3\\]", WorldInfo.getEntityName(this.damager));
        } else if (killer != null && damager != null && (killer instanceof Player)) {
            //攻击者为玩家，为 #1 被 #2 xxx
            this.deathMessage = DeathMsgConfig.onlyKillerDeath(getDeathReason(this.entityDamageEvent.getCause()));
            this.deathMessage = this.deathMessage.replaceAll("\\[2\\]", WorldInfo.getEntityName(this.damager));
        }
        this.deathMessage = this.deathMessage.replaceAll("\\[1\\]", WorldInfo.getEntityName(this.entity));
        this.deathMessage = this.deathMessage.replaceAll("\\[4\\]", LocationConfig.formatLocation(this.entity.getLocation()));
    }

    /**
     * 获取真实的攻击者。如果直接攻击者是弹射物就返回弹射物发射者，否则返回直接攻击者本身
     * @param killer 直接攻击者
     * @return 除非参数传入null，否则不可能返回null
     */
    public static Entity translateKiller(Entity killer) {
        if (killer == null) {
            return null;
        }

        if (killer instanceof Projectile) {
            Projectile projectile = (Projectile) killer;
            ProjectileSource projectileSource = projectile.getShooter();
            if (projectileSource == null) {
                return killer;
            }

            if (projectileSource instanceof Entity) {
                Entity sourceKiller = (Entity) projectileSource;
                return sourceKiller;
            }

            return killer;
        } 
        return killer;
    }

    /**
     * 标记攻击者。被弹射物攻击会标记发射弹射物者。不会标记玩家。
     * @param entityDamageEvent 伤害事件
     */
    public static void matchKiller(EntityDamageEvent entityDamageEvent) {
        if (entityDamageEvent == null) {
            return;
        }

        if (entityDamageEvent instanceof EntityDamageByEntityEvent) {
            Entity killer = ((EntityDamageByEntityEvent) entityDamageEvent).getDamager();
            killer = translateKiller(killer);
            if (killer instanceof LivingEntity && !(killer instanceof Player)) {
                if (!killer.getScoreboardTags().contains(DeathMsgConfig.revengeTag())) {
                    GlobalConfig.debug("Add revenge tag to entity: " + killer.getName());
                    killer.addScoreboardTag(DeathMsgConfig.revengeTag());
                }
            }
        }
    }

    public static String getDeathReason(DamageCause cause) {
        if (cause == DamageCause.BLOCK_EXPLOSION) {
            return "blockexplosion";
        } else if (cause == DamageCause.CONTACT) {
            return "contact";
        } else if (cause == DamageCause.CRAMMING) {
            return "cramming";
        } else if (cause == DamageCause.CUSTOM) {
            return "custom";
        } else if (cause == DamageCause.DRAGON_BREATH) {
            return "dragonbreath";
        } else if (cause == DamageCause.DROWNING) {
            return "drowing";
        } else if (cause == DamageCause.DRYOUT) {
            return "dryout";
        } else if (cause == DamageCause.ENTITY_ATTACK) {
            return "entityattack";
        } else if (cause == DamageCause.ENTITY_EXPLOSION) {
            return "entityexplosion";
        } else if (cause == DamageCause.ENTITY_SWEEP_ATTACK) {
            return "entitysweepattack";
        } else if (cause == DamageCause.FALL) {
            return "fall";
        } else if (cause == DamageCause.FALLING_BLOCK) {
            return "fallingblock";
        } else if (cause == DamageCause.FIRE) {
            return "fire";
        } else if (cause == DamageCause.FIRE_TICK) {
            return "firetick";
        } else if (cause == DamageCause.FLY_INTO_WALL) {
            return "flyintowall";
        } else if (cause == DamageCause.FREEZE) {
            return "freeze";
        } else if (cause == DamageCause.HOT_FLOOR) {
            return "hotfloor";
        } else if (cause == DamageCause.LAVA) {
            return "lava";
        } else if (cause == DamageCause.LIGHTNING) {
            return "lightning";
        } else if (cause == DamageCause.MAGIC) {
            return "magic";
        } else if (cause == DamageCause.MELTING) {
            return "melting";
        } else if (cause == DamageCause.POISON) {
            return "poison";
        } else if (cause == DamageCause.PROJECTILE) {
            return "projectile";
        } else if (cause == DamageCause.SONIC_BOOM) {
            return "sonicboom";
        } else if (cause == DamageCause.STARVATION) {
            return "starvation";
        } else if (cause == DamageCause.SUFFOCATION) {
            return "suffocation";
        } else if (cause == DamageCause.SUICIDE) {
            return "suicide";
        } else if (cause == DamageCause.THORNS) {
            return "thorns";
        } else if (cause == DamageCause.VOID) {
            return "void";
        } else if (cause == DamageCause.WITHER) {
            return "wither";
        } else {
            return "unknownreason";
        }        
    }

    public void show() {
        //执行指令
        if (this.deathMessage.charAt(0) == '/') {
            GlobalConfig.debug("Execute command for entity death: " + this.deathMessage);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), this.deathMessage.substring(1));
            return;
        }

        //重命名
        if (this.entity.getCustomName() != null) {
            GlobalConfig.getLogger().info(this.deathMessage);
            BroadcastInfo.broadcastMessage("fplugin.mobdeathmessage.custom", this.deathMessage);
            return;
        }

        //boss
        if (this.entityMaxHealth > DeathMsgConfig.maxHealth()) {
            GlobalConfig.getLogger().info(this.deathMessage);
            BroadcastInfo.broadcastMessage("fplugin.mobdeathmessage.boss", this.deathMessage);
            return;
        }

        //复仇
        if (this.entity.getScoreboardTags().contains(DeathMsgConfig.revengeTag())) {
            GlobalConfig.getLogger().info(this.deathMessage);
            BroadcastInfo.broadcastMessage("fplugin.mobdeathmessage.revenge", this.deathMessage);
            return;
        }

        //普通消息显示
        GlobalConfig.debug("Show entity death message: " + this.deathMessage);
        BroadcastInfo.broadcastActionbarText("fplugin.mobdeathmessage.actionbar", BroadcastInfo.formatText(this.deathMessage));
    }
    
}