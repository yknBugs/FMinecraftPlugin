package com.ykn.fplugin.config.command;

public class CmdDelayConfig extends CmdConfig {
    
    public static String missingTime() {
        return getString("command.delay.missingtime");
    }

    public static String wrongTime() {
        return getString("command.delay.wrongtime");
    }

    public static String timeTooSmall() {
        return getString("command.delay.timetoosmall");
    }

    public static String timeTooLarge() {
        return getString("command.delay.timetoolarge");
    }

    public static String missingExecuter() {
        return getString("command.delay.missingexecuter");
    }

    public static String missingCommand() {
        return getString("command.delay.missingcommand");
    }

    public static String noExecuter() {
        return getString("command.delay.noexecuter");
    }

    public static String consolePermission() {
        return getString("command.delay.consolepermission");
    }

    public static String otherPermission() {
        return getString("command.delay.otherpermission");
    }

    public static String clearPermission() {
        return getString("command.delay.clearpermission");
    }

    public static String warnExecuter() {
        return getString("command.delay.warnexecuter");
    }

    public static String delaySuccess() {
        return getString("command.delay.delaysuccess");
    }

    public static String executeSuccess() {
        return getString("command.delay.executesuccess");
    }

    public static String clearSuccess() {
        return getString("command.delay.clearsuccess");
    }
}
