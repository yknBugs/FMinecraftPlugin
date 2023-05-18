package com.ykn.fplugin.config.command;

public class CmdDevfunctionConfig extends CmdConfig {

    public static String startMessage() {
        return getString("command.devfunction.startmessage");
    }

    public static String endMessage() {
        return getString("command.devfunction.endmessage");
    }

    public static String errorMessage() {
        return getString("command.devfunction.errormessage");
    }

    public static boolean printStackTrace() {
        return getBoolean("command.devfunction.printstacktrace");
    }

    public static boolean ingameStackTrace() {
        return getBoolean("command.devfunction.ingamestacktrace");
    }
    
}
