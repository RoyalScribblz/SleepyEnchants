package me.jameslloyd.sleepyenchants.enchants;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class EnchantmentWrapper extends Enchantment {

    private final String name;
    private final int maxLvl;
    private final List<Material> materials;
    private final ChatColor colour;

    public EnchantmentWrapper(String namespace, String name, int maxLvl, List<Material> materials, ChatColor colour) {
        super(NamespacedKey.minecraft(namespace));
        this.name = name;
        this.maxLvl = maxLvl;
        this.materials = materials;
        this.colour = colour;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getMaxLevel() {
        return maxLvl;
    }

    @Override
    public int getStartLevel() {
        return 0;
    }

    @Override
    public EnchantmentTarget getItemTarget() {
        return null;
    }

    @Override
    public boolean isTreasure() {
        return false;
    }

    @Override
    public boolean isCursed() {
        return false;
    }

    @Override
    public boolean conflictsWith(Enchantment other) {
        return false;
    }

    @Override
    public boolean canEnchantItem(ItemStack item) {
        return false;
    }

    public List<Material> getMaterials() {return materials;}

    public ChatColor getColour() {return colour;}
}
