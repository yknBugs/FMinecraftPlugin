package com.ykn.fplugin.gui.base;

import org.bukkit.entity.Player;

public interface GUI {
    public boolean isAvailable();

    public boolean show(Player player);

    public void onClose(Player player);
}
