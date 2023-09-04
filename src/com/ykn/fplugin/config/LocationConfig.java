package com.ykn.fplugin.config;

import org.bukkit.Location;

public class LocationConfig extends Config {

    public static String formatLocation(Location location) {
        String result = thisPlugin.getConfig().getString("location.format", "(w) [x, y, z]").replace('&', '\u00a7');
        int accuracy = thisPlugin.getConfig().getInt("location.accuracy", 1);
        boolean hideNamespace = thisPlugin.getConfig().getBoolean("location.hidenamespace", true);

        String x;
        String y;
        String z;
        String p;
        String q;
        String w;

        if (accuracy <= 0) {
            x = Integer.toString((int) location.getX());
            y = Integer.toString((int) location.getY());
            z = Integer.toString((int) location.getZ());
            p = Integer.toString((int) location.getPitch());
            q = Integer.toString((int) location.getYaw());
        } else {
            x = String.format("%." + accuracy + "f", location.getX());
            y = String.format("%." + accuracy + "f", location.getY());
            z = String.format("%." + accuracy + "f", location.getZ());
            p = String.format("%." + accuracy + "f", location.getPitch());
            q = String.format("%." + accuracy + "f", location.getYaw());
        }
        w = location.getWorld().getName();
        if (hideNamespace) {
            w = w.substring(w.indexOf(':') + 1);
        }
        
        result = result.replace("x", x);
        result = result.replace("y", y);
        result = result.replace("z", z);
        result = result.replace("p", p);
        result = result.replace("q", q);
        result = result.replace("w", w);
        return result;
    }
    
    public static String formatDistance(Location loc1, Location loc2) {
        int accuracy = thisPlugin.getConfig().getInt("location.accuracy", 1);
        double distance = 0.0;
        try {
            distance = loc1.distance(loc2);
        } catch (IllegalArgumentException illegalArgumentException) {
            return "Cross Dimension";
        }
        if (accuracy > 0) {
            return String.format("%." + accuracy + "f", distance);
        } else {
            return Integer.toString((int) distance);
        }
    }

    public static String formatDistance(double distance) {
        int accuracy = thisPlugin.getConfig().getInt("location.accuracy", 1);
        if (accuracy > 0) {
            return String.format("%." + accuracy + "f", distance);
        } else {
            return Integer.toString((int) distance);
        }
    }
}