package com.ykn.fplugin.config.custom;

import com.ykn.fplugin.config.FConfig;

public class WaypointConfig extends FConfig {
    
    public static boolean isWaterSafe() {
        return getBoolean("waypoint.iswatersafe");
    }

    public static int saveSafeCD() {
        return getInt("waypoint.savesafecd");
    }

    public static int maxDeathPoint() {
        return getInt("waypoint.maxdeathpoint");
    }

}
