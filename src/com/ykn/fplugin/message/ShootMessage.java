package com.ykn.fplugin.message;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;

import com.ykn.fplugin.config.LocationConfig;
import com.ykn.fplugin.util.Util;

public class ShootMessage extends PlaceholderMessage {

    public Projectile projectile;
    public LivingEntity hitEntity;
    public LivingEntity shooter;

    public ShootMessage() {
        super();
        this.projectile = null;
        this.hitEntity = null;
        this.shooter = null;
    }

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

        if (this.shooter != null && this.hitEntity != null && this.shooter.isValid() && this.hitEntity.isValid()) {
            outputMessage = outputMessage.replace("[distance]", LocationConfig.formatDistance(this.shooter.getLocation(), this.hitEntity.getLocation()));
            if (this.shooter.getWorld() != this.hitEntity.getWorld()) {
                this.isValid = false;
            }
        } else {
            outputMessage = outputMessage.replace("[distance]", "Invalid");
            this.isValid = false;
        }
        
        // 借用 formatDistance 功能来四舍五入
        if (this.shooter != null) {
            if (this.shooter.isValid()) {
                outputMessage = outputMessage.replace("[shooterLocation]", LocationConfig.formatLocation(this.shooter.getLocation()));
                outputMessage = outputMessage.replace("[shooterHealth]", LocationConfig.formatDistance(this.shooter.getHealth()));
            } else {
                outputMessage = outputMessage.replace("[shooterLocation]", "Invalid");
                outputMessage = outputMessage.replace("[shooterHealth]", "Invalid");
                this.isValid = false;
            }
        } else {
            outputMessage = outputMessage.replace("[shooter]", "Null");
            this.isValid = false;
        }

        if (this.hitEntity != null) {
            if (this.hitEntity.isValid()) {
                outputMessage = outputMessage.replace("[entityLocation]", LocationConfig.formatLocation(this.hitEntity.getLocation()));
                outputMessage = outputMessage.replace("[entityHealth]", LocationConfig.formatDistance(this.hitEntity.getHealth()));
            } else {
                outputMessage = outputMessage.replace("[entityLocation]", "Invalid");
                outputMessage = outputMessage.replace("[entityHealth]", "Invalid");
                this.isValid = false;
            }
        } else {
            outputMessage = outputMessage.replace("[hitEntity]", "Null");
            this.isValid = false;
        }

        // 不在上面就替换占位符是为了尽可能减少通过重命名实体以利用占位符注入获取信息的bug
        if (this.shooter != null) {
            outputMessage = outputMessage.replace("[shooter]", Util.getEntityName(this.shooter));
        }

        if (this.hitEntity != null) {
            outputMessage = outputMessage.replace("[hitEntity]", Util.getEntityName(this.hitEntity));
        }

        if (this.projectile != null) {
            outputMessage = outputMessage.replace("[projectile]", Util.getEntityName(this.projectile));
        } else {
            outputMessage = outputMessage.replace("[projectile]", "Null");
            this.isValid = false;
        }
        // 重命名攻击者实体可能导致通过占位符注入的bug [不严重]

        return outputMessage;
    }

}
