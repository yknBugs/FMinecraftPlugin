package com.ykn.fplugin.config.command;

import com.ykn.fplugin.config.FConfig;

public class CmdConfig extends FConfig {
    public static boolean active() {
        return getBoolean("command.active");
    }

    public static String cmdNoPermission() {
        return getString("command.nopermission");
    }

    public static String cmdWrongParam() {
        return getString("command.wrongparam");
    }
    
    public static String cmdOnlyPlayerUse() {
        return getString("command.onlyplayeruse");
    }

    public static String missingData() {
        return getString("command.missingdata");
    }

    public static String cmdHelpText(String cmd) {
        String command = cmd.toLowerCase();
        return getString("command." + command + ".help");
    }

    public static boolean isCmdActive(String cmd) {
        String command = cmd.toLowerCase();
        return getBoolean("command." + command + ".active");
    }
}
