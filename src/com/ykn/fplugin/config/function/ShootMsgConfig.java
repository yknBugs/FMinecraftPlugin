package com.ykn.fplugin.config.function;

import com.ykn.fplugin.config.FConfig;

public class ShootMsgConfig extends FConfig {

    public static boolean active() {
        return getBoolean("shootmessage.active");
    }

    public static String beingShoot() {
        return getString("shootmessage.beingshoot");
    }

    public static String shootOther() {
        return getString("shootmessage.shootother");
    }

    public static String allShoot() {
        return getString("shootmessage.all");
    }

    public static String formatHealth(double health) {
        int accuracy = getInt("shootmessage.healthaccuracy");
        if (accuracy > 0) {
            return String.format("%." + accuracy + "f", health);
        } else {
            return Integer.toString((int) health);
        }
    }

    public static boolean ignoreDeath() {
        return getBoolean("shootmessage.ignoredeath");
    }
    
}
