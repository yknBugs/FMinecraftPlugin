package com.ykn.fplugin.language;

public class ActionbarLanguage extends Language {

    public static String getShootMessage() {
        return languageYML.getConfig().getString("actionbar.projectile.hitother", "你击中了 [distance] 米外的 [hitEntity] (生命值: [entityHealth])");
    }

    public static String getBeingShootMessage() {
        return languageYML.getConfig().getString("actionbar.projectile.beinghit", "你被 [shooter] 击中");
    }

    public static String getShootAllMessage() {
        return languageYML.getConfig().getString("actionbar.projectile.all", "位于 [entityLocation] 的 [hitEntity] (生命值: [entityHealth]) 被 [shooter] 击中"); 
    }
    
}
