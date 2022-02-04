package me.jameslloyd.sleepyenchants.utils;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

public class ItemManager {

    public static ItemStack hotPickaxe;
    public static ItemStack dagger;

    public static void init(){
        createhotPickaxe();
        createDagger();

    }

    private static void createhotPickaxe(){
        ItemStack item = new ItemStack(Material.DIAMOND_PICKAXE, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(1);
        meta.setDisplayName("Hot Pickaxe");
        meta.addEnchant(Enchantment.DIG_SPEED, 1000, true);
        meta.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 3, true);
        meta.addEnchant(Enchantment.DURABILITY, 1000, true);
        item.setItemMeta(meta);

        hotPickaxe = item;
    }

    private static void createDagger(){
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

        dagger = item;
    }
}
