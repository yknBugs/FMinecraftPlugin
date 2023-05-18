package com.ykn.fplugin.listener;

import org.bukkit.scheduler.BukkitRunnable;

import com.ykn.fplugin.data.GlobalData;
import com.ykn.fplugin.data.base.FData;

public class TickChange extends BukkitRunnable {
    @Override
    public void run() {
        
        //帧事件运行
        FData fData = new GlobalData();
        fData.tick();
    }
}
