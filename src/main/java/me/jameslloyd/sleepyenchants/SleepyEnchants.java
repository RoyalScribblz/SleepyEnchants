package me.jameslloyd.sleepyenchants;

import me.jameslloyd.sleepyenchants.commands.GiveEnchanted;
import me.jameslloyd.sleepyenchants.enchants.CustomEnchants;
import me.jameslloyd.sleepyenchants.events.EntityDmgByEntity;
import me.jameslloyd.sleepyenchants.events.PlayerInteract;
import me.jameslloyd.sleepyenchants.events.PlayerToggleSneak;
import me.jameslloyd.sleepyenchants.events.Quit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class SleepyEnchants extends JavaPlugin {

    ConsoleCommandSender console = getServer().getConsoleSender();
    PluginManager pluginManager = getServer().getPluginManager();

    public void log(String message) {
        console.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    @Override
    public void onEnable() {
        CustomEnchants.register();

        getCommand("ge").setExecutor(new GiveEnchanted());

        pluginManager.registerEvents(new PlayerInteract(), this);
        pluginManager.registerEvents(new Quit(), this);
        pluginManager.registerEvents(new PlayerToggleSneak(), this);
        pluginManager.registerEvents(new EntityDmgByEntity(), this);

        log("&a[SleepyEnchants] has started up successfully!");
    }

    @Override
    public void onDisable() {
        log("&a[SleepyEnchants] has shut down successfully!");
    }
}
