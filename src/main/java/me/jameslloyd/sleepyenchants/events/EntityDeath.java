package me.jameslloyd.sleepyenchants.events;

import me.jameslloyd.sleepyenchants.enchants.CustomEnchants;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.CookingRecipe;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.util.Iterator;

public class EntityDeath implements Listener {
    Player player;
    Entity entity;
    ItemStack itemInHand;
    ItemStack result;

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity() instanceof Player) {
            return;
        }

        EntityDamageEvent e = event.getEntity().getLastDamageCause();
        if (!(e instanceof EntityDamageByEntityEvent)) {
            return;
        }
        EntityDamageByEntityEvent nEvent = (EntityDamageByEntityEvent)e;

        if (!(nEvent.getDamager() instanceof Player)) {
            return;
        }
        player = (Player)nEvent.getDamager();
        itemInHand = player.getInventory().getItemInMainHand();
        if (itemInHand == null) return;
        if (!itemInHand.hasItemMeta()) return;
        if (itemInHand.containsEnchantment(CustomEnchants.POTLUCK)) potLuck(event);
    }

    public void potLuck(EntityDeathEvent e){
        for (ItemStack item : e.getDrops()){
            Iterator<Recipe> iter = Bukkit.recipeIterator();
            while (iter.hasNext()) {
                Recipe recipe = iter.next();
                if (!(recipe instanceof CookingRecipe)) continue;
                if (((CookingRecipe) recipe).getInput().getType() != item.getType()) continue;
                result = recipe.getResult();
                item.setType(result.getType());
                break;
            }
        }
    }
}
