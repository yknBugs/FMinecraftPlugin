package com.ykn.fplugin.message;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.ykn.fplugin.config.LocationConfig;
import com.ykn.fplugin.util.Util;

public class BossMessage extends PlaceholderMessage {

    public Player player;
    public LivingEntity entity;

    public BossMessage() {
        super();
        this.player = null;
    }

    public BossMessage(String permission, String message) {
        super(permission, message);
        this.player = null;
        this.entity = null;
    }

    public BossMessage(Player player, LivingEntity entity, String permission, String message) {
        super(permission, message);
        this.player = player;
        this.entity = entity;
    }

    @Override
    public String formatPlaceholders() {
        String outputMessage = this.message.replace('&', '\u00a7');
        if (this.player == null || !this.entity.isValid() || !this.player.isOnline()) {
            outputMessage = outputMessage.replace("[playerHealth]", "Invalid");
            outputMessage = outputMessage.replace("[playerHunger]", "Invalid");
            outputMessage = outputMessage.replace("[entityHealth]", "Invalid");
            outputMessage = outputMessage.replace("[entityMaxHeath]", "Invalid");
            outputMessage = outputMessage.replace("[distance]", "Invalid");
            outputMessage = outputMessage.replace("[location]", "Invalid");
            outputMessage = outputMessage.replace("[biome]", "Invalid");
            outputMessage = outputMessage.replace("[player]", "Invalid");
            outputMessage = outputMessage.replace("[entity]", "Invalid");
            this.isValid = false;
            return outputMessage;
        }

        outputMessage = outputMessage.replace("[playerHealth]", LocationConfig.formatDistance(this.player.getHealth()));
        outputMessage = outputMessage.replace("[playerHunger]", Integer.toString(player.getFoodLevel()));
        outputMessage = outputMessage.replace("[entityHealth]", LocationConfig.formatDistance(this.entity.getHealth()));
        outputMessage = outputMessage.replace("[entityMaxHealth]", LocationConfig.formatDistance(this.entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()));
        outputMessage = outputMessage.replace("[distance]", LocationConfig.formatDistance(this.player.getLocation(), this.entity.getLocation()));
        if (this.player.getWorld() != this.entity.getWorld()) {
            this.isValid = false;
        }
        outputMessage = outputMessage.replace("[location]", LocationConfig.formatLocation(this.entity.getLocation()));
        outputMessage = outputMessage.replace("[biome]", this.entity.getLocation().getBlock().getBiome().name().toLowerCase().replace('_', ' '));
        outputMessage = outputMessage.replace("[player]", Util.getEntityName(this.player));
        outputMessage = outputMessage.replace("[entity]", Util.getEntityName(this.entity));

        return outputMessage;
    }
    
}
