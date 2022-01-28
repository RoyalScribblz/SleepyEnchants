package me.jameslloyd.sleepyenchants.commands;

import me.jameslloyd.sleepyenchants.enchants.CustomEnchants;
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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GiveEnchanted implements CommandExecutor {

    CommandSender s;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        s = sender;
        if (!(sender instanceof Player)) return true;
        if (args.length < 2) return false;
        String enchantName = args[0];
        int level = Integer.parseInt(args[1]);

        if (enchantName.equalsIgnoreCase("swordsdance")) {
            giveEnchantedItem(Material.DIAMOND_SWORD, CustomEnchants.SWORDSDANCE, ChatColor.GOLD, level, true);
        }
        if (enchantName.equalsIgnoreCase("dragondance")) {
            giveEnchantedItem(Material.DIAMOND_AXE, CustomEnchants.DRAGONDANCE, ChatColor.GOLD, level, true);
        }
        if (enchantName.equalsIgnoreCase("bladebeam")) {
            giveEnchantedItem(Material.DIAMOND_SWORD, CustomEnchants.BLADEBEAM, ChatColor.LIGHT_PURPLE, level, true);
        }
        if (enchantName.equalsIgnoreCase("spinattack")) {
            giveEnchantedItem(Material.DIAMOND_SWORD, CustomEnchants.SPINATTACK, ChatColor.GOLD, level, true);
        }
        if (enchantName.equalsIgnoreCase("excalibur")) {
            giveEnchantedItem(Material.DIAMOND_SWORD, CustomEnchants.EXCALIBUR, ChatColor.LIGHT_PURPLE, level, true);
        }
        if (enchantName.equalsIgnoreCase("devilsscythe")) {
            giveEnchantedItem(Material.DIAMOND_SWORD, CustomEnchants.DEVILSSCYTHE, ChatColor.GREEN, level, true);
        }
        if (enchantName.equalsIgnoreCase("iceaspect")) {
            giveEnchantedItem(Material.DIAMOND_SWORD, CustomEnchants.ICEASPECT, ChatColor.GREEN, level, true);
        }
        if (enchantName.equalsIgnoreCase("bomberace")) {
            giveEnchantedItem(Material.ELYTRA, CustomEnchants.BOMBERACE, ChatColor.GOLD, level, true);
        }
        if (enchantName.equalsIgnoreCase("wingardiumleviosa")) {
            giveEnchantedItem(Material.DIAMOND_SWORD, CustomEnchants.WINGARDIUMLEVIOSA, ChatColor.GREEN, level, true);
        }
        if (enchantName.equalsIgnoreCase("urbosasfury")) {  // TODO don't be lazy and optimise this
            ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
            item.addUnsafeEnchantment(CustomEnchants.SPINATTACK, level);
            item.addUnsafeEnchantment(CustomEnchants.URBOSASFURY, level);
            ItemMeta meta = item.getItemMeta();
            List<String> lore = new ArrayList<String>();
            lore.add(ChatColor.GOLD + "Spin Attack II");
            lore.add(ChatColor.LIGHT_PURPLE + "Urbosa's Fury");
            meta.setLore(lore);
            item.setItemMeta(meta);

            ((Player) s).getInventory().addItem(item);
        }
        if (enchantName.equalsIgnoreCase("dash")) {
            giveEnchantedItem(Material.DIAMOND_SWORD, CustomEnchants.DASH, ChatColor.GOLD, level, true);
        }
        if (enchantName.equalsIgnoreCase("swiftblade")) {  // TODO value from item disappears but functionally does increase speed
            ItemStack item = giveEnchantedItem(Material.DIAMOND_SWORD, CustomEnchants.SWIFTBLADE, ChatColor.GREEN, level, false);
            double additionalSpeed = item.getEnchantmentLevel(CustomEnchants.SWORDSDANCE) * 0.5;
            ItemMeta meta = item.getItemMeta();
            meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED,
                    new AttributeModifier(UUID.randomUUID(), "attackSpeed", additionalSpeed, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
            item.setItemMeta(meta);
            ((Player) s).getInventory().addItem(item);
        }
        if (enchantName.equalsIgnoreCase("sheercold")) {
            giveEnchantedItem(Material.BOW, CustomEnchants.SHEERCOLD, ChatColor.DARK_GRAY, level, true);
        }
        return true;
    }

    public ItemStack giveEnchantedItem(Material material, Enchantment enchant, ChatColor colour, int level, boolean giveItem) {
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
            case 6:
                lore.add(colour + name + " VI");
                break;
            case 7:
                lore.add(colour + name + " VII");
                break;
            case 8:
                lore.add(colour + name + " VIII");
                break;
            case 9:
                lore.add(colour + name + " IX");
                break;
            case 10:
                lore.add(colour + name + " X");
                break;
        }
        meta.setLore(lore);
        item.setItemMeta(meta);

        if (giveItem) {
            ((Player) s).getInventory().addItem(item);
        }
        return item;
    }
}
