package me.jameslloyd.sleepyenchants.enchants;

import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.bukkit.Bukkit.getServer;

public class CustomEnchants {

    public static final Enchantment BLADEBEAM = new EnchantmentWrapper("bladebeam", "Blade Beam", 2);
    public static final Enchantment SWORDSDANCE = new EnchantmentWrapper("swordsdance", "Swords Dance", 5);
    public static final Enchantment SPINATTACK = new EnchantmentWrapper("spinattack", "Spin Attack", 3);
    public static final Enchantment URBOSASFURY = new EnchantmentWrapper("urbosasfury", "Urbosa's Fury", 1);

    public static void register() {
        for (Enchantment enchant: new Enchantment[]{BLADEBEAM, SWORDSDANCE, SPINATTACK, URBOSASFURY}) {  // register each enchant in this array
            boolean registered = Arrays.stream(Enchantment.values()).collect(Collectors.toList()).contains(enchant);
            if (!registered) registerEnchantment(enchant);
        }
    }

    public static void registerEnchantment(Enchantment enchantment) {
        boolean registered = true;
        try {
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);
            Enchantment.registerEnchantment(enchantment);
        } catch (Exception e) {
            registered = false;
            e.printStackTrace();
        }

        if (registered) {
            getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&aSuccessfully registered the " + enchantment.getName() + " enchantment"));
        }
    }
}
