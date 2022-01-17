package me.jameslloyd.sleepyenchants.events;

import me.jameslloyd.sleepyenchants.SleepyEnchants;
import me.jameslloyd.sleepyenchants.enchants.CustomEnchants;
import me.jameslloyd.sleepyenchants.particles.Effects;
import me.jameslloyd.sleepyenchants.particles.ParticleData;
import me.jameslloyd.sleepyenchants.utils.VectorUtils;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlayerInteract implements Listener {

    Map<String, Long> cooldowns = new HashMap<String, Long>();
    ItemStack itemInHand;

    private void sendMsg(CommandSender sender, String msg) {
        sender.sendMessage((ChatColor.translateAlternateColorCodes('&', msg)));
    }

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        Action action = e.getAction();
        itemInHand = player.getInventory().getItemInMainHand();

        if (itemInHand == null) return;
        if (!itemInHand.hasItemMeta()) return;

        if ((action == Action.LEFT_CLICK_AIR) || (action == Action.LEFT_CLICK_BLOCK)) {
            if (itemInHand.getItemMeta().hasEnchant(CustomEnchants.BLADEBEAM)) bladeBeam(player);
        } else if (!((action == Action.RIGHT_CLICK_AIR) || (action == Action.RIGHT_CLICK_BLOCK))) {
            if (itemInHand.getItemMeta().hasEnchant(CustomEnchants.SWORDSDANCE)) swordsDance(player);
        }
    }

    public void swordsDance(Player player) {
        String playerName = player.getName();
        if (cooldowns.containsKey(player.getName())) {
            if (cooldowns.get(playerName) > System.currentTimeMillis()) {
                long timeLeft = (cooldowns.get(playerName) - System.currentTimeMillis()) / 1000;
                sendMsg(player, "&6Ability will be ready in " + timeLeft + " second(s)");
                return;
            }
        }
        int coolDownTime = 300 - (itemInHand.getEnchantmentLevel(CustomEnchants.SWORDSDANCE) - 1) * 10;
        cooldowns.put(playerName, System.currentTimeMillis() + (coolDownTime * 1000L));

        // particles
        ParticleData particle = new ParticleData(player.getUniqueId());
        if (particle.hasID()) {
            particle.endTask();
            particle.removeID();
        }
        Effects effects = new Effects(player);
        effects.startTotem();
        Bukkit.getServer().getScheduler().runTaskLater(SleepyEnchants.getPlugin(SleepyEnchants.class), () -> {
            particle.endTask();
            particle.removeID();
        }, 60*20);

        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 60*20, 1, true, false));
        player.playSound(player.getLocation(), "minecraft:custom.swordsdance", SoundCategory.MASTER, 100, 1);
        sendMsg(player, "&aUsing the Swords Dance enchant!");
    }

    public void bladeBeam(Player player) {
        if (player.getHealth() >= 19.5) {  // full hp
            World world = player.getWorld();
            Location origin = player.getEyeLocation();
            Vector direction = origin.getDirection();

            Location firstPoint = origin.clone().add(direction);
            Location secondPoint = firstPoint.clone().add(direction);
            Location thirdPoint = secondPoint.clone().add(direction);

            ArrayList<Entity> hitEntities = new ArrayList<Entity>();
            world.spawnParticle(Particle.SWEEP_ATTACK, firstPoint, 0);
            for (Entity entity: world.getNearbyEntities(firstPoint, 2, 2, 2)) {
                if (entity instanceof LivingEntity) {
                    if (!entity.equals(player)) {
                        ((LivingEntity) entity).damage(2 * (3 + (itemInHand.getEnchantmentLevel(CustomEnchants.BLADEBEAM) - 1) * 1.5));
                        hitEntities.add(entity);
                    }
                }
            }

            Bukkit.getServer().getScheduler().runTaskLater(SleepyEnchants.getPlugin(SleepyEnchants.class), () -> {
                world.spawnParticle(Particle.SWEEP_ATTACK, secondPoint, 0);
                for (Entity entity: world.getNearbyEntities(secondPoint, 2, 2, 2)) {
                    if (entity instanceof LivingEntity && !hitEntities.contains(entity)) {
                        if (!entity.equals(player)) {
                            ((LivingEntity) entity).damage(2 * (3 + (itemInHand.getEnchantmentLevel(CustomEnchants.BLADEBEAM) - 1) * 1.5));
                            hitEntities.add(entity);
                        }
                    }
                }

                Bukkit.getServer().getScheduler().runTaskLater(SleepyEnchants.getPlugin(SleepyEnchants.class), () -> {
                    world.spawnParticle(Particle.SWEEP_ATTACK, thirdPoint, 0);
                    for (Entity entity: world.getNearbyEntities(thirdPoint, 2, 2, 2)) {
                        if (entity instanceof LivingEntity && !hitEntities.contains(entity)) {
                            if (!entity.equals(player)) {
                                ((LivingEntity) entity).damage(2 * (3 + (itemInHand.getEnchantmentLevel(CustomEnchants.BLADEBEAM) - 1) * 1.5));
                            }
                        }
                    }
                }, 3);
            }, 3);

            sendMsg(player, "&aYou used the Blade Beam ability!");

        } else {
            sendMsg(player, "&cYou must be full HP to use Blade Beam!");
        }
    }
}
