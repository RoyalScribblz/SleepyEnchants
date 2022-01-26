package me.jameslloyd.sleepyenchants.events;

import me.jameslloyd.sleepyenchants.enchants.CustomEnchants;
import org.bukkit.ChatColor;
import org.bukkit.SoundCategory;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EntityDmgByEntity implements Listener {

    private void sendMsg(CommandSender sender, String msg) {
        sender.sendMessage((ChatColor.translateAlternateColorCodes('&', msg)));
    }

    Player player;
    ItemStack itemInHand;

    @EventHandler
    public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent e){
        Entity damager = e.getDamager();
        if (damager instanceof Player) {
            player = (Player) damager;
            // player who damaged the entity
            sendMsg(player, "attack event");
            itemInHand = player.getInventory().getItemInMainHand();
            if (itemInHand == null) return;
            if (!itemInHand.hasItemMeta()) return;
            if (itemInHand.getItemMeta().hasEnchant(CustomEnchants.DEVILSSCYTHE)) devilsScythe(e);
        }
    }

    public void devilsScythe(EntityDamageByEntityEvent e) {
        double trigger = Math.random() * 100;
        itemInHand = player.getInventory().getItemInMainHand();
        double trigger_chance = (itemInHand.getEnchantmentLevel(CustomEnchants.DEVILSSCYTHE) +1)  * 2;

        if(trigger <= trigger_chance) {
            ((LivingEntity) e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.HARM,
                    1 * 20, 1, true, false));
            player.playSound(player.getLocation(), "minecraft:custom.devilsscythe", SoundCategory.MASTER, 100, 1);
            sendMsg(player, "&aUsing the Devil's Scythe enchant!");

        }
    }
}
