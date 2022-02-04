package me.jameslloyd.sleepyenchants.commands;

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
            player.getInventory().addItem(hotPickaxe());
            sender.sendMessage("Giving you a hot pickaxe!");
        }
        if (itemName.equalsIgnoreCase("dagger")) {
            player.getInventory().addItem(dagger());
            sender.sendMessage("Giving you a dagger!");
        }
        return true;
    }

    private static ItemStack hotPickaxe(){
        ItemStack item = new ItemStack(Material.DIAMOND_PICKAXE, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(1);
        meta.setDisplayName("Hot Pickaxe");
        meta.addEnchant(Enchantment.DIG_SPEED, 1000, true);
        meta.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 3, true);
        meta.addEnchant(Enchantment.DURABILITY, 1000, true);
        item.setItemMeta(meta);
        return item;
    }

    private static ItemStack dagger(){
        ItemStack item = new ItemStack(Material.IRON_SWORD, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(1);
        meta.setDisplayName("Dagger");
        AttributeModifier modifierDamage = new AttributeModifier(UUID.randomUUID(),
                "generic.attackDamage", 4, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
        AttributeModifier modifierSpeed = new AttributeModifier(UUID.randomUUID(),
                "generic.attackSpeed", 2.6, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifierDamage);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, modifierSpeed);
        item.setItemMeta(meta);

        return item;
    }
}
