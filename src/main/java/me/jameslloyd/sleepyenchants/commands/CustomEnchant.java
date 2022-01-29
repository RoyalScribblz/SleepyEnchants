package me.jameslloyd.sleepyenchants.commands;

import me.jameslloyd.sleepyenchants.enchants.CustomEnchants;
import me.jameslloyd.sleepyenchants.enchants.EnchantmentWrapper;
import me.jameslloyd.sleepyenchants.utils.ApplyEnchant;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

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

        boolean success = false;
        switch (enchantName.toLowerCase()) {
            case "swordsdance":
                success = ApplyEnchant.applyEnchant(item, (EnchantmentWrapper) CustomEnchants.SWORDSDANCE, level);
                break;
            case "dragondance":
                success = ApplyEnchant.applyEnchant(item, (EnchantmentWrapper) CustomEnchants.DRAGONDANCE, level);
                break;
            case "bladebeam":
                success = ApplyEnchant.applyEnchant(item, (EnchantmentWrapper) CustomEnchants.BLADEBEAM, level);
                break;
            case "spinattack":
                success = ApplyEnchant.applyEnchant(item, (EnchantmentWrapper) CustomEnchants.SPINATTACK, level);
                break;
            case "excalibur":
                success = ApplyEnchant.applyEnchant(item, (EnchantmentWrapper) CustomEnchants.EXCALIBUR, level);
                break;
            case "devilsscythe":
                success = ApplyEnchant.applyEnchant(item, (EnchantmentWrapper) CustomEnchants.DEVILSSCYTHE, level);
                break;
            case "iceaspect":
                success = ApplyEnchant.applyEnchant(item, (EnchantmentWrapper) CustomEnchants.ICEASPECT, level);
                break;
            case "bomberace":
                success = ApplyEnchant.applyEnchant(item, (EnchantmentWrapper) CustomEnchants.BOMBERACE, level);
                break;
            case "wingardiumleviosa":
                success = ApplyEnchant.applyEnchant(item, (EnchantmentWrapper) CustomEnchants.WINGARDIUMLEVIOSA, level);
                break;
            case "dash":
                success = ApplyEnchant.applyEnchant(item, (EnchantmentWrapper) CustomEnchants.DASH, level);
                break;
            case "sheercold":
                success = ApplyEnchant.applyEnchant(item, (EnchantmentWrapper) CustomEnchants.SHEERCOLD, level);
                break;
            case "urbosasfury":
                success = ApplyEnchant.applyEnchant(item, (EnchantmentWrapper) CustomEnchants.URBOSASFURY, level);
                break;
            case "swiftblade":
                success = ApplyEnchant.applyEnchant(item, (EnchantmentWrapper) CustomEnchants.SWIFTBLADE, level);
                double additionalSpeed = item.getEnchantmentLevel(CustomEnchants.SWORDSDANCE) * 0.5;
                ItemMeta meta = item.getItemMeta();
                meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED,
                        new AttributeModifier(UUID.randomUUID(), "attackSpeed", additionalSpeed, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
                item.setItemMeta(meta);
                break;
        }

        if (success) {
            sendMsg(player, "&aSuccessfully applied" + enchantName);
            return true;
        } else {
            sendMsg(player, "&cSomething went wrong, please try again.");
            return false;
        }
    }
}
