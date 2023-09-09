package com.ykn.fplugin;

import java.util.Collection;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import com.ykn.fplugin.config.Config;
import com.ykn.fplugin.config.ConfigAccessor;
import com.ykn.fplugin.data.PlayerData;
import com.ykn.fplugin.data.ServerData;
import com.ykn.fplugin.event.EntityDamage;
import com.ykn.fplugin.event.EntityDeath;
import com.ykn.fplugin.event.PlayerJoin;
import com.ykn.fplugin.event.PlayerMove;
import com.ykn.fplugin.event.PlayerQuit;
import com.ykn.fplugin.event.ProjectileHit;
import com.ykn.fplugin.event.SendCommand;
import com.ykn.fplugin.event.TickChange;
import com.ykn.fplugin.language.ConsoleLanguage;
import com.ykn.fplugin.language.Language;

public class FPlugin extends JavaPlugin {

    BukkitTask tickTask;

    @Override
    public void onEnable() {
        //保存并读取config.yml和language.yml
        // this.getConfig().options().copyDefaults(true);
        this.saveDefaultConfig();
        this.saveConfig();
        this.reloadConfig();

        ConfigAccessor languageConfig = new ConfigAccessor(this, "language.yml");
        // languageConfig.getConfig().options().copyDefaults(true);
        languageConfig.saveDefaultConfig();
        languageConfig.saveConfig();
        languageConfig.reloadConfig();


        //保存实例
        Config.thisPlugin = this;
        Language.languageYML = languageConfig;

        //注册事件
        this.tickTask = new TickChange().runTaskTimer(this, 0, 1);
        this.getServer().getPluginManager().registerEvents(new EntityDeath(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerMove(), this);
        this.getServer().getPluginManager().registerEvents(new EntityDamage(), this);
        // this.getServer().getPluginManager().registerEvents(new InventoryClick(), this);
        // this.getServer().getPluginManager().registerEvents(new InventoryClose(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerQuit(), this);
        this.getServer().getPluginManager().registerEvents(new ProjectileHit(), this);

        //注册指令
        PluginCommand fCommand = this.getCommand("f");
        if (fCommand == null) {
            getLogger().log(Level.SEVERE, "Failed to register command. Please check plugin.yml");
        } else {
            fCommand.setExecutor(new SendCommand());
            fCommand.setTabCompleter(new SendCommand());
        }

        //重新将所有玩家加入到数据列表
        Collection<? extends Player> players = Bukkit.getServer().getOnlinePlayers();
        for (Player player : players) {
            if (!ServerData.playerdata.containsKey(player.getUniqueId())) {
                PlayerData playerData = new PlayerData();
                playerData.uuid = player.getUniqueId();
                playerData.joinTick = ServerData.tick;
                ServerData.playerdata.put(player.getUniqueId(), playerData);
            }
        }

        ConsoleLanguage.sendPluginEnableMessage();
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll();
        this.tickTask.cancel();
        ConsoleLanguage.sendPluginDisableMessage();
    }

}
