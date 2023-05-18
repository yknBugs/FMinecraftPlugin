package com.ykn.fplugin.data;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.Player;

import com.ykn.fplugin.config.GlobalConfig;
import com.ykn.fplugin.config.custom.WaypointConfig;
import com.ykn.fplugin.data.base.FData;
import com.ykn.fplugin.function.BossbarMessage;
import com.ykn.fplugin.gui.base.GUI;
import com.ykn.fplugin.util.WorldInfo;

public class PlayerData implements FData {
    public Player player;

    public BossbarMessage playerBossbar;

    public GUI lastOpenGUI;
    public Location loginLocation;
    public LocalDateTime loginTime;
    public Location lastSaftLocation;
    public Location currentSafeLocation;

    public Location backLocation;
    public int backTick;

    public List<Location> deathLocations;
    public List<Location> safeDeathLocations;
    public List<Boolean> isDeathLocationsSafe;
    public List<String> deathMessages;
    public List<LocalDateTime> deathTimes;

    public PlayerData(Player player) {
        this.player = player;
        this.playerBossbar = new BossbarMessage(BarColor.WHITE, BarStyle.SOLID, 100, "Init");
        this.lastOpenGUI = null;
        this.loginLocation = player.getLocation();
        this.loginTime = LocalDateTime.now();
        this.lastSaftLocation = this.loginLocation;
        this.currentSafeLocation = this.loginLocation;
        this.backLocation = this.loginLocation;
        this.backTick = GlobalData.serverData.ticks;
        this.deathLocations = new LinkedList<Location>();
        this.safeDeathLocations = new LinkedList<Location>();
        this.isDeathLocationsSafe = new LinkedList<Boolean>();
        this.deathMessages = new LinkedList<String>();
        this.deathTimes = new LinkedList<LocalDateTime>();
    }

    @Override
    public void reset() {
        GlobalConfig.debug("PlayerData reset: " + player.getName());
        this.playerBossbar = new BossbarMessage(BarColor.WHITE, BarStyle.SOLID, 100, "Init");
        this.lastSaftLocation = this.loginLocation;
        this.currentSafeLocation = this.loginLocation;
        this.backLocation = this.loginLocation;
        this.backTick = GlobalData.serverData.ticks;
        this.deathLocations.clear();
        this.safeDeathLocations.clear();
        this.isDeathLocationsSafe.clear();
        this.deathMessages.clear();
        this.deathTimes.clear();
    }

    @Override
    public void tick() {
        if (GlobalData.serverData.ticks % WaypointConfig.saveSafeCD() == 0) {
            if (this.player.getHealth() > 0 && WorldInfo.isSafePlace(this.player.getLocation())) {
                this.lastSaftLocation = this.currentSafeLocation;
                this.currentSafeLocation = this.player.getLocation();
            }
        }
        this.playerBossbar.player = this.player;
        this.playerBossbar.tick();
    }

    public void addDeathLocation(String deathMessage) {
        int maxDeathPoint = WaypointConfig.maxDeathPoint();
        this.deathLocations.add(this.player.getLocation());
        this.safeDeathLocations.add(this.lastSaftLocation);
        this.isDeathLocationsSafe.add(WorldInfo.isSafePlace(this.player.getLocation()));
        this.deathMessages.add(deathMessage);
        this.deathTimes.add(LocalDateTime.now());
        while (this.deathLocations.size() > maxDeathPoint) {
            this.deathLocations.remove(0);
            this.safeDeathLocations.remove(0);
            this.isDeathLocationsSafe.remove(0);
            this.deathMessages.remove(0);
            this.deathTimes.remove(0);
        }
        GlobalConfig.debug(this.player.getName() + "\'s death location has been saved.");
        this.backLocation = player.getLocation();
        this.backTick = GlobalData.serverData.ticks;
    }
}