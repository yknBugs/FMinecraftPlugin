package com.ykn.fplugin.data;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.entity.Player;

import com.ykn.fplugin.config.GlobalConfig;
import com.ykn.fplugin.data.base.FData;
import com.ykn.fplugin.function.BossbarMessage;

public class GlobalData implements FData {
    public static List<PlayerData> playerDataList = new LinkedList<PlayerData>();
    public static ServerData serverData = new ServerData();

    /**
     * 查找指定玩家的数据
     * @param player 玩家
     * @return 玩家的数据，如果玩家数据不存在则为null
     */
    public static PlayerData getPlayerData(Player player) {
        if (player == null) {
            return null;
        }
        for (PlayerData playerData : playerDataList) {
            if (playerData.player == player) {
                return playerData;
            }
        }
        return null;
    }

    /**
     * 判断是否存在指定玩家的数据
     * @param player 玩家
     * @return 存在返回true，不存在返回false
     */
    public static boolean hasPlayerData(Player player) {
        if (player == null) {
            return false;
        }
        for (PlayerData playerData : playerDataList) {
            if (playerData.player == player) {
                return true;
            }
        }
        return false;
    }

    /**
     * 添加新的玩家，用来保存该玩家的数据。如果玩家已经存在则什么都不做
     * @param player 玩家
     */
    public static void addPlayer(Player player) {
        if (player == null) {
            return;
        }
        if (!hasPlayerData(player)) {
            playerDataList.add(new PlayerData(player));
        }
    }

    @Override
    public void reset() {
        GlobalConfig.debug("GlobalConfig reset");
        playerDataList.clear();
    }

    @Override
    public void tick() {
        for (PlayerData playerData : playerDataList) {
            playerData.tick();

            if (playerData.playerBossbar.isShow == false && serverData.serverBossbar.isShow == true) {
                serverData.serverBossbar.bossBar.addPlayer(playerData.player);
            }
            if (playerData.playerBossbar.isShow == true && serverData.serverBossbar.isShow == false) {
                playerData.playerBossbar.bossBar.addPlayer(playerData.player);
            }
        }
        serverData.tick();
    }

    public static void showBossbarMessage(BossbarMessage bossbarMessage) {
        serverData.serverBossbar.remove();
        serverData.serverBossbar = bossbarMessage;
        if (bossbarMessage.override) {
            for (PlayerData playerData : playerDataList) {
                playerData.playerBossbar.bossBar.removeAll();
            }
            serverData.serverBossbar.show();
            return;
        }
        serverData.serverBossbar.show();
        serverData.serverBossbar.bossBar.removeAll();
        for (PlayerData playerData : playerDataList) {
            if (playerData.playerBossbar.isShow) {
                playerData.playerBossbar.bossBar.addPlayer(playerData.player);
            } else {
                serverData.serverBossbar.bossBar.addPlayer(playerData.player);
            }
        }
    }

    
    /**
     * 显示boss栏消息规则:
     * 两者Override都为true，优先显示后出现的消息。
     * 一者Override为false，另外一者为true，优先显示Override的。
     * 两者Override都为false，优先显示先出现的消息。
     * @param playerData 要显示的目标玩家对应的玩家数据
     * @param bossbarMessage 要显示的bossbar消息
     */
    public static void showBossbarMessage(PlayerData playerData, BossbarMessage bossbarMessage) {
        playerData.playerBossbar.remove();
        playerData.playerBossbar = bossbarMessage;
        if (bossbarMessage.override) {
            serverData.serverBossbar.bossBar.removePlayer(playerData.player);
            playerData.playerBossbar.show(playerData.player);
            return;
        }
        if (serverData.serverBossbar.isShow) {
            playerData.playerBossbar.show(playerData.player);
            playerData.playerBossbar.bossBar.removeAll();
            serverData.serverBossbar.bossBar.addPlayer(playerData.player);
            return;
        }
        playerData.playerBossbar.show(playerData.player);
    }
}
