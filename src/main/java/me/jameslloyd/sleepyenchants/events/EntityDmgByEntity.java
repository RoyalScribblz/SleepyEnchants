package me.jameslloyd.sleepyenchants.events;

import me.jameslloyd.sleepyenchants.enchants.CustomEnchants;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
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

import java.util.concurrent.ThreadLocalRandom;

public class EntityDmgByEntity implements Listener {

    private void sendMsg(CommandSender sender, String msg) {
        sender.sendMessage((ChatColor.translateAlternateColorCodes('&', msg)));
    }

    Player player;
    ItemStack itemInHand;
    LivingEntity entity;

    @EventHandler
    public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent e){
        Entity damager = e.getDamager();
        entity = (LivingEntity) e.getEntity();
        if (damager instanceof Player) {
            player = (Player) damager;
            // player who damaged the entity
            sendMsg(player, "attack event");
            itemInHand = player.getInventory().getItemInMainHand();
            if (itemInHand == null) return;
            if (!itemInHand.hasItemMeta()) return;
            if (itemInHand.getItemMeta().hasEnchant(CustomEnchants.DEVILSSCYTHE)) devilsScythe(e);
            if (itemInHand.getItemMeta().hasEnchant(CustomEnchants.ICEASPECT)) iceAspect(e);
            if (itemInHand.getItemMeta().hasEnchant(CustomEnchants.WINGARDIUMLEVIOSA)) wingardiumLeviosa(e);

        }
    }

    public void devilsScythe(EntityDamageByEntityEvent e) {
        double trigger = ThreadLocalRandom.current().nextDouble() * 100;
        double trigger_chance = itemInHand.getEnchantmentLevel(CustomEnchants.DEVILSSCYTHE) * 2;

        if(trigger <= trigger_chance) {
            // spawn 20 soul particles in random places around the hit entity
            Location entityLoc = entity.getLocation();
            for (int i = 0; i < 20; i++) {
                double xOffset = ThreadLocalRandom.current().nextDouble();
                double yOffset = ThreadLocalRandom.current().nextDouble();
                double zOffset = ThreadLocalRandom.current().nextDouble();
                Location particleLoc = entityLoc.clone().add(xOffset - 0.5, yOffset + 0.8, zOffset - 0.5);

                entity.getWorld().spawnParticle(Particle.SOUL, particleLoc, 1);
                ((LivingEntity) e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.HARM,
                        1 * 20, 1, true, false));
                player.playSound(player.getLocation(), "minecraft:custom.devilsscythe", SoundCategory.MASTER, 100, 1);
                sendMsg(player, "&aUsing the Devil's Scythe enchant!");
            }
        }
    }

    public void iceAspect(EntityDamageByEntityEvent e) {
        int duration = 4 * itemInHand.getEnchantmentLevel(CustomEnchants.ICEASPECT);
        ((LivingEntity) e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.SLOW,  // add slowness effect
                duration * 20, 1, true, false));
        player.playSound(player.getLocation(), "minecraft:custom.iceaspect", SoundCategory.MASTER, 100, 1);

        // spawn 20 ice particles in random places around the hit entity
        Location entityLoc = entity.getLocation();
        for (int i = 0; i < 20; i++) {
            double xOffset = ThreadLocalRandom.current().nextDouble();
            double yOffset = ThreadLocalRandom.current().nextDouble();
            double zOffset = ThreadLocalRandom.current().nextDouble();
            Location particleLoc = entityLoc.clone().add(xOffset-0.5, yOffset+0.8, zOffset-0.5);
            entity.getWorld().spawnParticle(Particle.SNOWFLAKE, particleLoc, 1);
        }

        sendMsg(player, "&aUsing the Ice Aspect enchant!");
    }
    public void wingardiumLeviosa(EntityDamageByEntityEvent e) {
        double trigger = ThreadLocalRandom.current().nextDouble() * 100;
        double trigger_chance = itemInHand.getEnchantmentLevel(CustomEnchants.WINGARDIUMLEVIOSA) * 5;

        if(trigger <= trigger_chance) {
            ((LivingEntity) e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION,
                        1 * 4, 0, true, true));
                player.playSound(player.getLocation(), "minecraft:custom.winggardiumleviosa", SoundCategory.MASTER, 100, 1);
                sendMsg(player, "&aUsing the Wingardium Leviosa enchant!");
            }
        }
    }