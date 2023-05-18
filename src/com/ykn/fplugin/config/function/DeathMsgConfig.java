package com.ykn.fplugin.config.function;

import com.ykn.fplugin.config.FConfig;

public class DeathMsgConfig extends FConfig {

    public static boolean active() {
        return getBoolean("mobdeathmessage.active");
    }
    
    public static double maxHealth() {
        return getDouble("mobdeathmessage.maxhealth");
    }

    public static boolean revenge() {
        return getBoolean("mobdeathmessage.revenge");
    }

    public static boolean custom() {
        return getBoolean("mobdeathmessage.custom");
    }

    public static String revengeTag() {
        return getString("mobdeathmessage.revengetag");
    }

    public static String noReasonDeath() {
        return getString("mobdeathmessage.reason.noreason");
    }

    public static String noKillerDeath(String deathReason) {
        return getString("mobdeathmessage.reason." + deathReason + ".nokiller");
    }

    public static String onlyKillerDeath(String deathReason) {
        return getString("mobdeathmessage.reason." + deathReason + ".onlykiller");
    }

    public static String onlyDamagerDeath(String deathReason) {
        return getString("mobdeathmessage.reason." + deathReason + ".onlydamager");
    }

    public static String bothKillerDeath(String deathReason) {
        return getString("mobdeathmessage.reason." + deathReason + ".both");
    }
}
