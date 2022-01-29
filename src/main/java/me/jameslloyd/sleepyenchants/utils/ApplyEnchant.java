package me.jameslloyd.sleepyenchants.utils;

import me.jameslloyd.sleepyenchants.enchants.CustomEnchants;
import me.jameslloyd.sleepyenchants.enchants.EnchantmentWrapper;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ApplyEnchant {

    public static boolean applyEnchant(ItemStack item, EnchantmentWrapper enchant, int level) {

        // check compatible, if not, don't modify
        List<Material> compatibleMaterials = enchant.getMaterials();
        if (!compatibleMaterials.contains(item.getType())) return false;

        // check level doesn't supersede max level
        if (level > enchant.getMaxLevel()) level = enchant.getMaxLevel();

        item.addUnsafeEnchantment(enchant, level);
        String name = enchant.getName();
        ChatColor colour = enchant.getColour();

        // add enchant name to the lore
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();
        if (meta.hasLore()) lore = meta.getLore(); // get lore if preexisting

        // insert new lore at the start to prevent going after non-enchant lore
        switch (level) {
            case 1:
                lore.add(0, colour + name + " I");
                break;
            case 2:
                lore.add(0, colour + name + " II");
                break;
            case 3:
                lore.add(0, colour + name + " III");
                break;
            case 4:
                lore.add(0, colour + name + " IV");
                break;
            case 5:
                lore.add(0, colour + name + " V");
                break;
            case 6:
                lore.add(0, colour + name + " VI");
                break;
            case 7:
                lore.add(0, colour + name + " VII");
                break;
            case 8:
                lore.add(0, colour + name + " VIII");
                break;
            case 9:
                lore.add(0, colour + name + " IX");
                break;
            case 10:
                lore.add(0, colour + name + " X");
                break;
        }
        meta.setLore(lore);
        item.setItemMeta(meta);

        // special cases
        if (enchant == CustomEnchants.SWIFTBLADE) {
            double additionalSpeed = item.getEnchantmentLevel(CustomEnchants.SWORDSDANCE) * 0.5;
            meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED,
                    new AttributeModifier(UUID.randomUUID(), "attackSpeed", additionalSpeed, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
            item.setItemMeta(meta);
        }

        return true;
    }

}