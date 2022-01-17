package me.jameslloyd.sleepyenchants.commands;

import me.jameslloyd.sleepyenchants.enchants.CustomEnchants;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GiveEnchanted implements CommandExecutor {

    CommandSender s;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        s = sender;
        if (!(sender instanceof Player)) return true;
        if (args.length < 2) return false;
        String enchantName = args[0];
        int level = Integer.parseInt(args[1]);

        if (enchantName.toLowerCase().equals("swordsdance")) {
            giveEnchantedItem(Material.DIAMOND_SWORD, CustomEnchants.SWORDSDANCE, ChatColor.GOLD, level);
        }

        if (enchantName.toLowerCase().equals("bladebeam")) {
            giveEnchantedItem(Material.DIAMOND_SWORD, CustomEnchants.BLADEBEAM, ChatColor.AQUA, level);
        }

        return true;
    }

    public void giveEnchantedItem(Material material, Enchantment enchant, ChatColor colour, int level) {
        ItemStack item = new ItemStack(material);
        item.addUnsafeEnchantment(enchant, level);
        String name = enchant.getName();

        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<String>();
        switch (level) {
            case 1:
                lore.add(colour + name + " I");
                break;
            case 2:
                lore.add(colour + name + " II");
                break;
            case 3:
                lore.add(colour + name + " III");
                break;
            case 4:
                lore.add(colour + name + " IV");
                break;
            case 5:
                lore.add(colour + name + " V");
                break;
        }
        meta.setLore(lore);
        item.setItemMeta(meta);

        ((Player) s).getInventory().addItem(item);
    }
}
