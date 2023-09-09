package com.ykn.fplugin.message;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;

import com.ykn.fplugin.config.Config;
import com.ykn.fplugin.config.LocationConfig;
import com.ykn.fplugin.util.Util;

public class SiegeMessage extends PlaceholderMessage {

    public Player player;

    public SiegeMessage() {
        super();
        this.player = null;
    }

    public SiegeMessage(String permission, String message) {
        super(permission, message);
        this.player = null;
    }

    public SiegeMessage(Player player, String permission, String message) {
        super(permission, message);
        this.player = player;
    }

    @Override
    public String formatPlaceholders() {
        String outputMessage = this.message.replace('&', '\u00a7');
        if (this.player == null || !this.player.isOnline()) {
            outputMessage = outputMessage.replace("[playerHealth]", "Invalid");
            outputMessage = outputMessage.replace("[playerHunger]", "Invalid");
            outputMessage = outputMessage.replace("[entityHealth]", "Invalid");
            outputMessage = outputMessage.replace("[count]", "Invalid");
            outputMessage = outputMessage.replace("[location]", "Invalid");
            outputMessage = outputMessage.replace("[biome]", "Invalid");
            outputMessage = outputMessage.replace("[player]", "Invalid");
            outputMessage = outputMessage.replace("[entity]", "Invalid");
            this.isValid = false;
            return outputMessage;
        }

        List<Entity> entities = this.player.getNearbyEntities(Config.getSiegeMonsterDistanceX(), Config.getSiegeMonsterDistanceY(), Config.getSiegeMonsterDistanceZ());
        List<Monster> monsters = new ArrayList<Monster>();
        for (Entity entity : entities) {
            if (entity instanceof Monster) {
                monsters.add((Monster) entity);
            }
        }

        if (monsters.size() < Config.getSiegeMonsterCount()) {
            this.isValid = false;
        }

        outputMessage = outputMessage.replace("[playerHealth]", LocationConfig.formatDistance(this.player.getHealth()));
        outputMessage = outputMessage.replace("[playerHunger]", Integer.toString(player.getFoodLevel()));

        if (monsters.size() == 0) {
            outputMessage = outputMessage.replace("[entityHealth]", "Null");
        } else {
            outputMessage = outputMessage.replace("[entityHealth]", LocationConfig.formatDistance(monsters.get(0).getHealth()));
        }

        outputMessage = outputMessage.replace("[count]", Integer.toString(monsters.size()));
        outputMessage = outputMessage.replace("[location]", LocationConfig.formatLocation(this.player.getLocation()));
        outputMessage = outputMessage.replace("[biome]", this.player.getLocation().getBlock().getBiome().name().toLowerCase().replace('_', ' '));
        outputMessage = outputMessage.replace("[player]", Util.getEntityName(this.player));

        if (monsters.size() == 0) {
            outputMessage = outputMessage.replace("[entity]", "Null");
        } else {
            outputMessage = outputMessage.replace("[entity]", Util.getEntityName(monsters.get(0)));
        }

        return outputMessage;
    }
    
}
