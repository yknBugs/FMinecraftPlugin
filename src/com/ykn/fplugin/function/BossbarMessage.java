package com.ykn.fplugin.function;

import java.util.Collection;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import com.ykn.fplugin.config.GlobalConfig;
import com.ykn.fplugin.data.GlobalData;
import com.ykn.fplugin.util.BroadcastInfo;

public class BossbarMessage {

    public Player player;
    public String title;
    public BossBar bossBar;
    public boolean isShow;
    public int maxValue;
    public int value;
    public int showTick;
    public boolean isPermanent;
    public boolean override;

    public BossbarMessage(BarColor barColor, BarStyle barStyle, int time, String title) {
        String msg = title.replaceAll("\\[s\\]", Integer.toString(this.value / 20));
        msg = msg.replaceAll("\\[m\\]", Integer.toString(this.maxValue / 20));
        this.bossBar = Bukkit.createBossBar(GlobalConfig.toColorString(msg), barColor, barStyle);
        this.bossBar.setVisible(true);
        this.title = title;
        this.isShow = false;
        this.maxValue = time;
        this.value = 0;
        this.showTick = 0;
        this.isPermanent = false;
        this.override = false;
        this.player = null;
    }

    public void tick() {
        if (this.isPermanent == false && this.value > 0) {
            this.value--;
            if (this.value <= 0) {
                this.isShow = false;
                this.bossBar.removeAll();
            }
            String msg = title.replaceAll("\\[s\\]", Integer.toString(this.value / 20));
            msg = msg.replaceAll("\\[m\\]", Integer.toString(this.maxValue / 20));
            if (this.player != null) {
                msg = BroadcastInfo.setPapi(this.player, msg);
            }
            this.bossBar.setProgress((double) this.value / (double) this.maxValue);
            this.bossBar.setTitle(msg);
        }
    }

    public void show() {
        this.value = this.maxValue;
        this.isShow = true;
        this.showTick = GlobalData.serverData.ticks;
        this.bossBar.removeAll();
        Collection<? extends Player> players = Bukkit.getOnlinePlayers();
        for (Player player : players) {
            this.bossBar.addPlayer(player);
        }
    }

    public void show(Player player) {
        this.value = this.maxValue;
        this.isShow = true;
        this.showTick = GlobalData.serverData.ticks;
        this.bossBar.removeAll();
        this.bossBar.addPlayer(player);
    }
    
    public void show(List<Player> players) {
        this.value = this.maxValue;
        this.isShow = true;
        this.showTick = GlobalData.serverData.ticks;
        this.bossBar.removeAll();
        for (Player player : players) {
            this.bossBar.addPlayer(player);
        }
    }

    public void remove() {
        this.isShow = false;
        this.value = 0;
        this.bossBar.removeAll();
    }
}
