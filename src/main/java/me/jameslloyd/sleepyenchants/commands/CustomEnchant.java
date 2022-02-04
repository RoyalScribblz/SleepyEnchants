package me.jameslloyd.sleepyenchants.commands;

import me.jameslloyd.sleepyenchants.enchants.CustomEnchants;
import me.jameslloyd.sleepyenchants.enchants.EnchantmentWrapper;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

import org.bukkit.inventory.ItemStack;

public class CustomEnchant implements CommandExecutor {

    private void sendMsg(CommandSender sender, String msg) {
        sender.sendMessage((ChatColor.translateAlternateColorCodes('&', msg)));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;

        if (args.length < 2) return false;
        String enchantName = args[0];
        int level = Integer.parseInt(args[1]);

        ItemStack item = player.getInventory().getItemInMainHand();

        Enchantment enchant = CustomEnchants.getEnchant(enchantName);
        if (enchant == null) return false;
        //TODO Prevent duplicates of the same enchantment
        boolean success = CustomEnchants.applyEnchant(item, (EnchantmentWrapper) enchant, level);

        if (success) {
            sendMsg(player, "&aSuccessfully applied " + enchant.getName() + "!");
            return true;
        } else {
            sendMsg(player, "&cYou cannot apply this enchantment!");
            return false;
        }
    }
}
