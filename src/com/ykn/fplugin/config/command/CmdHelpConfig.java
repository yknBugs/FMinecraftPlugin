package com.ykn.fplugin.config.command;

import java.util.List;

public class CmdHelpConfig extends CmdConfig {
    
    public static boolean showNoPermission() {
        return getBoolean("command.help.shownopermission");
    }

    public static int maxPerPage() {
        return getInt("command.help.maxperpage");
    }

    public static String firstLine() {
        return getString("command.help.firstline");
    }

    public static String wrongPage() {
        return getString("command.help.wrongpage");
    }

    public static String pageTooSmall() {
        return getString("command.help.pagetoosmall");
    }

    public static String pageTooLarge() {
        return getString("command.help.pagetoolarge");
    }

    public static List<String> commandList() {
        return getStringList("command.help.list");
    }
}
