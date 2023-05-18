package com.ykn.fplugin;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import com.ykn.fplugin.command.Cmd;
import com.ykn.fplugin.command.TabCmd;
import com.ykn.fplugin.config.FConfig;
import com.ykn.fplugin.listener.EntityDeath;
import com.ykn.fplugin.listener.InventoryClick;
import com.ykn.fplugin.listener.InventoryClose;
import com.ykn.fplugin.listener.PlayerJoin;
import com.ykn.fplugin.listener.PlayerQuit;
import com.ykn.fplugin.listener.ProjectileHit;
import com.ykn.fplugin.listener.TickChange;

public class FPlugin extends JavaPlugin {
    BukkitTask tickTask;

    @Override
    public void onEnable() {
        this.tickTask = new TickChange().runTaskTimer(this, 0, 1);

        //检测依赖是否安装
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
            getLogger().warning("Could not find PlaceholderAPI. This plugin could not work without it.");
            getLogger().warning("Please download PlaceholderAPI and copy the jar file into your plugin directory.");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        if (Bukkit.getPluginManager().getPlugin("ProtocolLib") == null) {
            getLogger().warning("Could not find ProtocolLib. This plugin could not work without it.");
            getLogger().warning("Please download ProtocolLib and copy the jar file into your plugin directory.");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        //保存并读取config.yml
        this.getConfig().options().copyDefaults();
        this.saveDefaultConfig();

        //保存实例
        FConfig.fPlugin = this;

        //注册事件
        this.getServer().getPluginManager().registerEvents(new EntityDeath(), this);
        this.getServer().getPluginManager().registerEvents(new InventoryClick(), this);
        this.getServer().getPluginManager().registerEvents(new InventoryClose(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerQuit(), this);
        this.getServer().getPluginManager().registerEvents(new ProjectileHit(), this);

        //注册指令
        try {
            this.getCommand("f").setExecutor(new Cmd());
            this.getCommand("f").setTabCompleter(new TabCmd());
        } catch (NullPointerException e) {
            getLogger().warning("Error while registering commands: Command not in plugin.yml");
            e.printStackTrace();
        }
        
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll();
        this.tickTask.cancel();
    }
}
