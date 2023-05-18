package com.ykn.fplugin.config.command;

import java.util.List;

public class CmdRunConfig extends CmdConfig {
    public static List<String> customCommandList() {
        return getStringList("command.run.list");
    }

    public static String help(String command) {
        return getString("customcommand." + command + ".help");
    }

    public static boolean isConsole(String command) {
        return getBoolean("customcommand." + command + ".isconsole");
    }

    public static boolean isSafe(String command) {
        return getBoolean("customcommand." + command + ".issafe");
    }

    public static String permission(String command) {
        return "fplugin.f.run." + command;
    }

    public static List<String> commandList(String command) {
        return getStringList("customcommand." + command + ".command");
    }

    public static List<String> paramList(String command) {
        return getStringList("customcommand." + command + ".params");
    }

    public static String missingName() {
        return getString("command.run.missingname");
    }

    public static String wrongName() {
        return getString("command.run.wrongname");
    }

    public static String missingParams() {
        return getString("command.run.missingparams");
    }

    public static String wrongParams() {
        return getString("command.run.wrongparams");
    }

    public static String noPermission() {
        return getString("command.run.nopermission");
    }

    public static String notSafe() {
        return getString("command.run.notsafe");
    }
}
