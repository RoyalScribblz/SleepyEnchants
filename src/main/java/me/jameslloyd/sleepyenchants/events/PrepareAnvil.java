package me.jameslloyd.sleepyenchants.events;

import me.jameslloyd.sleepyenchants.enchants.CustomEnchants;
import me.jameslloyd.sleepyenchants.enchants.EnchantmentWrapper;
import me.jameslloyd.sleepyenchants.utils.ApplyEnchant;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PrepareAnvil implements Listener {

    @EventHandler
    public void onPrepareAnvil(PrepareAnvilEvent e) {
        AnvilInventory inventory = e.getInventory();
        ItemStack item1 = inventory.getItem(0);
        ItemStack item2 = inventory.getItem(1);
        ItemStack result = e.getResult();

        // check a combination is possible
        if (item1 == null || item2 == null) return;
        if (result == null) {
            if (item1.getType() == item2.getType()) result = new ItemStack(item1.getType());
        }

        if (result == null) return;

        // loop each enchant and add it to a map, if it's already there update the number to the larger one or level up
        Map<Enchantment, Integer> enchantsToApply = new HashMap<Enchantment, Integer>();
        Set<Enchantment> all = CustomEnchants.getAll();
        for (Enchantment enchant : all) {
            if (item1.containsEnchantment(enchant)) enchantsToApply.put(enchant, item1.getEnchantmentLevel(enchant));
            if (item2.containsEnchantment(enchant)) {
                if (!enchantsToApply.containsKey(enchant)) { // if it doesn't already contain add like normal
                    enchantsToApply.put(enchant, item1.getEnchantmentLevel(enchant));
                } else {
                    int level1 = enchantsToApply.get(enchant);
                    int level2 = item2.getEnchantmentLevel(enchant);

                    if (level2 > level1) {
                        enchantsToApply.replace(enchant, level2);
                    }

                    if (level2 == level1) {
                        enchantsToApply.replace(enchant, level2+1);
                    }
                }
            }
        }

        // no enchants to apply
        if (enchantsToApply.size() == 0) return;

        // apply each enchant
        for (Enchantment enchant : enchantsToApply.keySet()) {
            ApplyEnchant.applyEnchant(result, (EnchantmentWrapper) enchant, enchantsToApply.get(enchant));
        }

        e.setResult(result);
    }
}
