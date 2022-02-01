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

import java.util.Set;

public class PrepareAnvil implements Listener {

    @EventHandler
    public void onPrepareAnvil(PrepareAnvilEvent e) {
        AnvilInventory inventory = e.getInventory();
        ItemStack item1 = inventory.getItem(0);
        ItemStack item2 = inventory.getItem(1);
        ItemStack result = e.getResult();

        if (item1 == null || item2 == null) return;
        if (result == null) {
            if (item1.getType() == item2.getType()) result = new ItemStack(item1.getType());
        }

        if (result == null) return;

        boolean hasCustomEnchant = false;
        Set<Enchantment> all = CustomEnchants.getAll();
        for (Enchantment enchant : all) {
            if (item1.containsEnchantment(enchant)) {ApplyEnchant.applyEnchant(result, (EnchantmentWrapper) enchant, item1.getEnchantmentLevel(enchant)); hasCustomEnchant=true;}
            if (item2.containsEnchantment(enchant)) {ApplyEnchant.applyEnchant(result, (EnchantmentWrapper) enchant, item2.getEnchantmentLevel(enchant)); hasCustomEnchant=true;}
        }

        if (!hasCustomEnchant) return;

        e.setResult(result);
    }
}
