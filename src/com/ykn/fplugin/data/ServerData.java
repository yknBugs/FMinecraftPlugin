package com.ykn.fplugin.data;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;

import com.ykn.fplugin.command.DelayCmd;
import com.ykn.fplugin.config.GlobalConfig;
import com.ykn.fplugin.data.base.FData;
import com.ykn.fplugin.function.BossbarMessage;
import com.ykn.fplugin.function.ShootMessage;

public class ServerData implements FData {
    public int ticks;

    public List<ShootMessage> scheduledShootMessage;
    public List<DelayCmd> scheduledDelayCommand;
    public BossbarMessage serverBossbar;

    public ServerData() {
        this.ticks = 0;
        this.scheduledShootMessage = new LinkedList<ShootMessage>();
        this.scheduledDelayCommand = new LinkedList<DelayCmd>();
        this.serverBossbar = new BossbarMessage(BarColor.WHITE, BarStyle.SOLID, 100, "Init");
        GlobalConfig.debug("ServerData reset");
    }

    @Override
    public void reset() {
        GlobalConfig.debug("ServerData reset");
        this.ticks = 0;
        this.scheduledShootMessage.clear();
        this.scheduledDelayCommand.clear();
        this.serverBossbar = new BossbarMessage(BarColor.WHITE, BarStyle.SOLID, 100, "Init");
    }

    @Override
    public void tick() {
        this.ticks++;

        while (this.scheduledShootMessage.size() > 0) {
            if (this.scheduledShootMessage.get(0).planTick == this.ticks) {
                this.scheduledShootMessage.get(0).show();
                this.scheduledShootMessage.remove(0);
            } else {
                break;
            }
        }

        while (this.scheduledDelayCommand.size() > 0) {
            if (this.scheduledDelayCommand.get(0).planTick == this.ticks) {
                this.scheduledDelayCommand.get(0).performCommand();
                this.scheduledDelayCommand.remove(0);
            } else {
                break;
            }
        }

        this.serverBossbar.tick();
    }
}
