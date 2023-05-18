package com.ykn.fplugin.config.command;

import java.util.List;

public class CmdGUIConfig extends CmdConfig {

    public static int slotCount() {
        return getInt("gui.cmdgui.slot");
    }

    public static String title() {
        return getString("gui.cmdgui.title");
    }

    public static List<String> itemList() {
        return getStringList("gui.cmdgui.list");
    }
    
}
