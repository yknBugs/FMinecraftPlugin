package com.ykn.fplugin.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ServerData {

    public static int tick = 0;

    public static HashMap<UUID, PlayerData> playerdata = new HashMap<UUID, PlayerData>();

    public static Collection<UUID> killerEntity = new ArrayList<UUID>();

    public static PlayerData getPlayerData(UUID uuid) {
        return playerdata.get(uuid);
    }

    public static Player getPlayer(UUID uuid) {
        return Bukkit.getServer().getPlayer(uuid);
    }
    
}
