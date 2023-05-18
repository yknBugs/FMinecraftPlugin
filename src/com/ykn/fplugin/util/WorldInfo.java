package com.ykn.fplugin.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.ykn.fplugin.config.custom.WaypointConfig;

public class WorldInfo {

    public static boolean isStableBlock(Block block) {
        if (block.getBoundingBox().getHeight() > 0.4 
        && block.getBoundingBox().getWidthX() > 0.9 && block.getBoundingBox().getWidthZ() > 0.9) {
            return true;
        }
        return false;
    }
    
    public static boolean isSafePlace(Location location) {
        Location loc = new Location(location.getWorld(), location.getX(), location.getY(), location.getZ());
        Block block0 = loc.add(0, 1.6, 0).getBlock();
        Block block1 = loc.add(0, -1, 0).getBlock();
        Block block2 = loc.add(0, -1, 0).getBlock();
        boolean isWaterSafe = WaypointConfig.isWaterSafe();
        boolean notSuffocation = false;

        if (block0.getType() == Material.AIR && block1.getType() == Material.AIR) {
            notSuffocation = true;
        }

        if (isWaterSafe) {
            if (block0.getType() == Material.WATER || block0.getType() == Material.AIR) {
                if (block1.getType() == Material.WATER || block1.getType() == Material.AIR) {
                    notSuffocation = true;
                }
            }
        }

        if (notSuffocation && isStableBlock(block2)) {
            return true;
        }

        if (notSuffocation && isWaterSafe && block2.getType() == Material.WATER) {
            return true;
        }

        return false;
    }

    public static Block getBottomStableBlock(Location location) {
        Location loc = new Location(location.getWorld(), location.getX(), location.getY(), location.getZ());
        loc.add(0, -0.4, 0);

        while (loc.getY() > loc.getWorld().getMinHeight()) {
            Block block = loc.getBlock();

            if (isStableBlock(block)) {
                return block;
            }

            if (WaypointConfig.isWaterSafe() && block.getType() == Material.WATER) {
                return block;
            }

            loc.add(0, -1, 0);
        }

        return location.getBlock();
    }

    public static List<Player> getOnlinePlayers() {
        Collection<? extends Player> p = Bukkit.getOnlinePlayers();
        List<Player> players = new ArrayList<Player>();
        for (Player player : p) {
            players.add(player);
        }
        return players;
    }

    public static Player getPlayer(String playerName) {
        Collection<? extends Player> players = Bukkit.getOnlinePlayers();
        for (Player player : players) {
            if (player.getName().equalsIgnoreCase(playerName)) {
                return player;
            }
        }
        return null;
    }

    public static String getEntityName(Entity entity) {
        if (entity.getCustomName() == null) {
            return entity.getName();
        } else {
            return entity.getCustomName();
        }
    }

}
