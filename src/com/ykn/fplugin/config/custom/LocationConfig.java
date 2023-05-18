package com.ykn.fplugin.config.custom;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.bukkit.Location;

import com.ykn.fplugin.config.FConfig;

public class LocationConfig extends FConfig {

    public static String formatLocation(Location location) {
        String result = getString("location.format");
        int accuracy = getInt("location.accuracy");

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
        
        result = result.replaceAll("x", x);
        result = result.replaceAll("y", y);
        result = result.replaceAll("z", z);
        result = result.replaceAll("p", p);
        result = result.replaceAll("q", q);
        result = result.replaceAll("w", w);
        return result;
    }
    
    public static String formatDistance(Location loc1, Location loc2) {
        int accuracy = getInt("location.accuracy");
        double distance = loc1.distance(loc2);
        if (accuracy > 0) {
            return String.format("%." + accuracy + "f", distance);
        } else {
            return Integer.toString((int) distance);
        }
    }

    public static String formatDistance(double distance) {
        int accuracy = getInt("location.accuracy");
        if (accuracy > 0) {
            return String.format("%." + accuracy + "f", distance);
        } else {
            return Integer.toString((int) distance);
        }
    }

    public static String formatTime(LocalDateTime time) {
        String result = getString("location.time");
        return time.format(DateTimeFormatter.ofPattern(result));
    }
}
