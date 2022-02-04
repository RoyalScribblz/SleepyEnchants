package me.jameslloyd.sleepyenchants.commands;

import me.jameslloyd.sleepyenchants.utils.ItemManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

public class GiveItem implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) return true;

        Player player = (Player) sender;
        if (args.length < 1) return false;
        String itemName = args[0];

        if (itemName.equalsIgnoreCase("hotpickaxe")) {
            player.getInventory().addItem(ItemManager.hotPickaxe);
            sender.sendMessage("Giving you a hot pickaxe!");
        }
        if (itemName.equalsIgnoreCase("dagger")) {
            player.getInventory().addItem(ItemManager.dagger);
            sender.sendMessage("Giving you a dagger!");
        }
        return true;
    }
}
