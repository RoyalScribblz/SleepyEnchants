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
    public static final Enchantment EXCALIBUR = new EnchantmentWrapper("excalibur", "Excalibur", 5);
    public static final Enchantment DASH = new EnchantmentWrapper("dash", "Dash", 3);
    public static final Enchantment DEVILSSCYTHE = new EnchantmentWrapper("devilsscythe", "Devil's Scythe", 5);
    public static final Enchantment SWIFTBLADE = new EnchantmentWrapper("swiftblade", "Swift Blade", 5);
    public static final Enchantment ICEASPECT = new EnchantmentWrapper("iceaspect", "Ice Aspect", 2);
    public static final Enchantment BOMBERACE = new EnchantmentWrapper("bomberace", "Bomber Ace", 3);
    public static final Enchantment DRAGONDANCE = new EnchantmentWrapper("dragondance", "Dragon Dance", 5);
    public static final Enchantment WINGARDIUMLEVIOSA = new EnchantmentWrapper("wingardiumleviosa", "Wingardium Leviosa", 3);
    public static final Enchantment SHEERCOLD = new EnchantmentWrapper("sheercold", "Sheer Cold", 2);
    public static final Enchantment HASTYMINER = new EnchantmentWrapper("hastyminer", "Hasty Miner", 5);
    public static final Enchantment BELLYDRUM = new EnchantmentWrapper("bellydrum", "Belly Drum", 5);
    public static void register() {
        // register each enchant in this array
        for (Enchantment enchant: new Enchantment[]{BLADEBEAM, SWORDSDANCE, SPINATTACK, URBOSASFURY, EXCALIBUR, DASH,
                DEVILSSCYTHE, SWIFTBLADE, ICEASPECT, BOMBERACE, DRAGONDANCE, WINGARDIUMLEVIOSA, SHEERCOLD, HASTYMINER, BELLYDRUM}) {
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
