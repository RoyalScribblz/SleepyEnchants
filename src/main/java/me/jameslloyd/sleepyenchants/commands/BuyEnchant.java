package me.jameslloyd.sleepyenchants.commands;

import me.jameslloyd.sleepyenchants.events.InventoryClick;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BuyEnchant implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;

        InventoryClick.openEnchantMenu(player);

        return true;
    }
}
