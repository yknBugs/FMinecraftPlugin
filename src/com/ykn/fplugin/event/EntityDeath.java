package com.ykn.fplugin.event;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.projectiles.ProjectileSource;

import com.ykn.fplugin.config.Config;
import com.ykn.fplugin.language.ConsoleLanguage;
import com.ykn.fplugin.language.Language;
import com.ykn.fplugin.language.TextLanguage;
import com.ykn.fplugin.message.PersistentMessage;
import com.ykn.fplugin.message.PlaceholderMessage;
import com.ykn.fplugin.util.Util;

public class EntityDeath implements Listener {

    @EventHandler
    public void onEntityDeath(EntityDeathEvent entityDeathEvent) {
        if (entityDeathEvent.getEntity() instanceof Player) {
            this.tagKiller(entityDeathEvent.getEntity().getLastDamageCause());
        } 
        if (Config.isActiveEntityDeathMessage()) {
            this.showEntityDeathMessage(entityDeathEvent.getEntity());
        }
        
    }

    private void showEntityDeathMessage(LivingEntity entity) {
        // 获取具体死亡消息的内容
        EntityDamageEvent entityDamageEvent = entity.getLastDamageCause();
        Entity killer = this.getKiller(entityDamageEvent);
        DamageCause damageCause;
        if (entityDamageEvent == null) {
            damageCause = null;
        } else {
            damageCause = entityDamageEvent.getCause();
        }
        Player damager = entity.getKiller();
        String message = TextLanguage.getDeathMessage(entity, killer, damager, damageCause);

        // 根据情况确定死亡消息显示的位置
        // 玩家
        if (Config.isShowPlayerDeathMessage() && entity instanceof Player) {
            Language.log(3, message);
            message = Config.getPrefix() + message;
            Util.sendTextMessageToLimit((Player) entity, "fplugin.deathmessage.self", "fplugin.deathmessage.player", message);
            return;
        }

        // 重命名实体
        if (Config.isShowRenamedDeathMessage() && entity.getCustomName() != null) {
            Language.log(3, message);
            message = Config.getPrefix() + message;
            Util.sendTextMessageToLimit("fplugin.deathmessage.renamed", message);
            return;
        }

        // boss
        if (Config.isShowBossDeathMessage() && entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() > Config.getHealthRegardedAsBoss()) {
            Language.log(3, message);
            message = Config.getPrefix() + message;
            Util.sendTextMessageToLimit("fplugin.deathmessage.boss", message);
            return;
        }

        // 复仇
        if (Config.isShowRevengeDeathMessage() && entity.getScoreboardTags().contains(Config.getEntityKillerTag())) {
            Language.log(3, message);
            message = Config.getPrefix() + message;
            Util.sendTextMessageToLimit("fplugin.deathmessage.revenge", message);
            return;
        }

        // 生物
        if (Config.isShowAllDeathMessage()) {
            Language.log(4, message);
            PlaceholderMessage placeholderMessage = new PlaceholderMessage("fplugin.deathmessage.all", message);
            PersistentMessage persistentMessage = new PersistentMessage(0, 200, Config.getEntityDeathMessagePriority(), placeholderMessage);
            Util.sendPersistentMessageToLimit(persistentMessage);
            return;
        }
        
    }

    /**
     * 获取造成伤害的实体
     * @param entityDamageEvent 伤害事件
     */
    private Entity getKiller(EntityDamageEvent entityDamageEvent) {
        if (entityDamageEvent == null) {
            return null;
        }

        if (!(entityDamageEvent instanceof EntityDamageByEntityEvent)) {
            return null;
        }

        EntityDamageByEntityEvent entityDamageByEntityEvent = (EntityDamageByEntityEvent) entityDamageEvent;
        Entity killer = this.getTrueKiller(entityDamageByEntityEvent.getDamager());
        if (killer == null || !(killer instanceof LivingEntity)) {
            return null;
        }

        return killer;
    }


    /**
     * 将造成伤害的实体标记为杀手
     * @param entityDamageEvent 伤害事件
     */
    private void tagKiller(EntityDamageEvent entityDamageEvent) {
        if (entityDamageEvent == null) {
            return;
        }

        if (!(entityDamageEvent instanceof EntityDamageByEntityEvent)) {
            return;
        }

        EntityDamageByEntityEvent entityDamageByEntityEvent = (EntityDamageByEntityEvent) entityDamageEvent;
        Entity killer = this.getTrueKiller(entityDamageByEntityEvent.getDamager());
        if (killer == null || !(killer instanceof LivingEntity) || killer instanceof Player || !killer.isValid()) {
            return;
        }

        if (!killer.getScoreboardTags().contains(Config.getEntityKillerTag())) {
            ConsoleLanguage.sendAddKillerTagMessage(killer);
            killer.addScoreboardTag(Config.getEntityKillerTag());
        }
    }


    /**
     * 获取真正的伤害造成者
     * @return 如果直接造成伤害的实体是弹射物，则返回弹射物的发射者，否则返回攻击者本身
     */
    private Entity getTrueKiller(Entity killer) {
        if (killer == null) {
            return null;
        }

        if (!(killer instanceof Projectile)) {
            return killer;
        }

        Projectile projectile = (Projectile) killer;
        ProjectileSource projectileSource = projectile.getShooter();
        if (projectileSource == null) {
            return killer;
        }

        if (!(projectileSource instanceof Entity)) {
            return killer;
        }

        Entity trueKiller = (Entity) projectileSource;
        return trueKiller;
    }
    
}
