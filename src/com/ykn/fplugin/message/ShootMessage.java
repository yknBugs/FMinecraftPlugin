package com.ykn.fplugin.message;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;

import com.ykn.fplugin.config.LocationConfig;
import com.ykn.fplugin.util.Util;

/**
 * 被弹射物击中的消息 <p>
 * 可用占位符: <p>
 * & 颜色字符 <p>
 * [projectile] 弹射物名称 <p>
 * [hitEntity] 被击中的实体名称 <p>
 * [entityLocation] 被击中的实体坐标 <p>
 * [entityHealth] 被击中的实体生命值 <p>
 * [shooter] 攻击者名称 <p>
 * [shooterLocation] 攻击者坐标 <p>
 * [shooterHealth] 攻击者生命值 <p>
 * [distance] 攻击者和被攻击者的距离 <p>
 */
public class ShootMessage extends PlaceholderMessage {

    public Projectile projectile;
    public LivingEntity hitEntity;
    public LivingEntity shooter;

    public ShootMessage(String permission, String message) {
        super(permission, message);
        this.projectile = null;
        this.hitEntity = null;
        this.shooter = null;
    }

    public ShootMessage(String permission, String message, Projectile projectile, LivingEntity hitEntity, LivingEntity shooter) {
        super(permission, message);
        this.projectile = projectile;
        this.hitEntity = hitEntity;
        this.shooter = shooter;
    }

    @Override
    public String formatPlaceholders() {
        String outputMessage = this.message.replace('&', '\u00a7');
        if (this.projectile != null) {
            outputMessage = outputMessage.replace("[projectile]", Util.getEntityName(this.projectile));
        } else {
            outputMessage = outputMessage.replace("[projectile]", "Null");
        }

        if (this.hitEntity != null) {
            outputMessage = outputMessage.replace("[hitEntity]", Util.getEntityName(this.hitEntity));
            if (this.hitEntity.isValid()) {
                outputMessage = outputMessage.replace("[entityLocation]", LocationConfig.formatLocation(this.hitEntity.getLocation()));
                outputMessage = outputMessage.replace("[entityHealth]", Double.toString(this.hitEntity.getHealth()));
            } else {
                outputMessage = outputMessage.replace("[entityLocation]", "Invalid");
                outputMessage = outputMessage.replace("[entityHealth]", "Invalid");
            }
        } else {
            outputMessage = outputMessage.replace("[hitEntity]", "Null");
        }
        
        if (this.shooter != null) {
            outputMessage = outputMessage.replace("[shooter]", Util.getEntityName(this.shooter));
            if (this.shooter.isValid()) {
                outputMessage = outputMessage.replace("[shooterLocation]", LocationConfig.formatLocation(this.shooter.getLocation()));
                outputMessage = outputMessage.replace("[shooterHealth]", Double.toString(this.shooter.getHealth()));
            } else {
                outputMessage = outputMessage.replace("[shooterLocation]", "Invalid");
                outputMessage = outputMessage.replace("[shooterHealth]", "Invalid");
            }
        } else {
            outputMessage = outputMessage.replace("[shooter]", "Null");
        }

        if (this.shooter != null && this.hitEntity != null && this.shooter.isValid() && this.hitEntity.isValid()) {
            outputMessage = outputMessage.replace("[distance]", LocationConfig.formatDistance(this.shooter.getLocation(), this.hitEntity.getLocation()));
        } else {
            outputMessage = outputMessage.replace("[distance]", "Invalid");
        }

        return outputMessage;
    }

}
