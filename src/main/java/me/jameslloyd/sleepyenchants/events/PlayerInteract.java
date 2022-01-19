package me.jameslloyd.sleepyenchants.events;

import me.jameslloyd.sleepyenchants.SleepyEnchants;
import me.jameslloyd.sleepyenchants.enchants.CustomEnchants;
import me.jameslloyd.sleepyenchants.particles.Effects;
import me.jameslloyd.sleepyenchants.particles.ParticleData;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
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
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlayerInteract implements Listener {

    ItemStack itemInHand;
    Player player;

    private void sendMsg(CommandSender sender, String msg) {
        sender.sendMessage((ChatColor.translateAlternateColorCodes('&', msg)));
    }

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent e) {
        player = e.getPlayer();
        Action action = e.getAction();
        itemInHand = player.getInventory().getItemInMainHand();

        if (itemInHand == null) return;
        if (!itemInHand.hasItemMeta()) return;

        if ((action == Action.LEFT_CLICK_AIR) || (action == Action.LEFT_CLICK_BLOCK)) {
            if (itemInHand.getItemMeta().hasEnchant(CustomEnchants.BLADEBEAM)) bladeBeam();
            if (itemInHand.getItemMeta().hasEnchant(CustomEnchants.SPINATTACK)) spinAttack();
        } else if (!((action == Action.RIGHT_CLICK_AIR) || (action == Action.RIGHT_CLICK_BLOCK))) {
            if (itemInHand.getItemMeta().hasEnchant(CustomEnchants.SWORDSDANCE)) swordsDance();
        }
    }

    Map<String, Long> sDCooldowns = new HashMap<String, Long>();
    public void swordsDance() {
        String playerName = player.getName();
        if (sDCooldowns.containsKey(player.getName())) {
            if (sDCooldowns.get(playerName) > System.currentTimeMillis()) {
                long timeLeft = (sDCooldowns.get(playerName) - System.currentTimeMillis()) / 1000;
                sendMsg(player, "&6Ability will be ready in " + timeLeft + " second(s)");
                return;
            }
        }
        int coolDownTime = 300 - (itemInHand.getEnchantmentLevel(CustomEnchants.SWORDSDANCE) - 1) * 10;
        sDCooldowns.put(playerName, System.currentTimeMillis() + (coolDownTime * 1000L));

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

    public void bladeBeam() {
        if (player.getHealth() >= 19.5) {  // full hp
            World world = player.getWorld();
            Location origin = player.getEyeLocation();
            Vector direction = origin.getDirection();

            ArrayList<Entity> hitEntities = new ArrayList<Entity>();
            Location[] points = {origin.clone().add(direction), origin.clone().add(direction).add(direction), origin.clone().add(direction).add(direction).add(direction)};
            int[] timings = {0, 3, 6};
            for (int i = 0; i < 3; i++) {
                int finalI = i;
                Bukkit.getServer().getScheduler().runTaskLater(SleepyEnchants.getPlugin(SleepyEnchants.class), () -> {
                    world.spawnParticle(Particle.SWEEP_ATTACK, points[finalI], 0);
                    for (Entity entity: world.getNearbyEntities(points[finalI], 2, 2, 2)) {
                        if (entity instanceof LivingEntity && !hitEntities.contains(entity)) {
                            if (!entity.equals(player)) {
                                ((LivingEntity) entity).damage(2 * (3 + (itemInHand.getEnchantmentLevel(CustomEnchants.BLADEBEAM) - 1) * 1.5));
                                hitEntities.add(entity);
                            }
                        }
                    }
                }, timings[i]);
            }

            sendMsg(player, "&aYou used the Blade Beam ability!");

        } else {
            sendMsg(player, "&cYou must be full HP to use Blade Beam!");
        }
    }

    private int sALoopCount = 0;
    private int sATaskID;
    public void spinAttack() {
        if (!PlayerToggleSneak.fullCharged.contains(player.getUniqueId())) return;

        Location location = player.getLocation();

        sALoopCount = 0;
        sATaskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(SleepyEnchants.getPlugin(SleepyEnchants.class), new Runnable() {

            ArrayList<Entity> hitEntities = new ArrayList<Entity>();

            @Override
            public void run() {
                location.setYaw(location.getYaw()+20);
                player.teleport(location);

                Location origin = player.getEyeLocation();
                Vector direction = origin.getDirection();
                World world = origin.getWorld();
                if (world != null) {
                    Location loc1 = origin.clone().add(direction);
                    Location loc2 = loc1.clone().add(direction);
                    Location loc3 = loc2.clone().add(direction);
                    Location loc4 = loc3.clone().add(direction);

                    Location[] locations = {loc1, loc2, loc3, loc4};
                    for (Location loc : locations) {
                        world.spawnParticle(Particle.CLOUD, loc, 0);
                        for (Entity entity : world.getNearbyEntities(loc, 2, 2, 2)) {
                            if (entity instanceof LivingEntity && !hitEntities.contains(entity)) {
                                if (!entity.equals(player)) {
                                    ((LivingEntity) entity).damage(6);
                                    hitEntities.add(entity);
                                }
                            }
                        }
                    }
                }

                if (sALoopCount == 17) {
                    Bukkit.getScheduler().cancelTask(sATaskID);
                }
                sALoopCount++;
            }
        }, 0, 1);

        PlayerToggleSneak.fullCharged.remove(player.getUniqueId());
        sendMsg(player, "&aYou used the Spin Attack ability!");
    }
}
