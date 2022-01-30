package me.jameslloyd.sleepyenchants.events;

import me.jameslloyd.sleepyenchants.SleepyEnchants;
import me.jameslloyd.sleepyenchants.enchants.CustomEnchants;
import me.jameslloyd.sleepyenchants.particles.Effects;
import me.jameslloyd.sleepyenchants.particles.ParticleData;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
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
    ItemStack itemOnChest;
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
            if (itemInHand.containsEnchantment(CustomEnchants.BLADEBEAM)) bladeBeam();
            if (itemInHand.containsEnchantment(CustomEnchants.SPINATTACK)) spinAttack();
        }
        if ((action == Action.RIGHT_CLICK_AIR) || (action == Action.RIGHT_CLICK_BLOCK)) {
            if (itemInHand.containsEnchantment(CustomEnchants.SWORDSDANCE)) swordsDance();
            if (itemInHand.containsEnchantment(CustomEnchants.DASH)) dash();
            if (itemInHand.containsEnchantment(CustomEnchants.EXCALIBUR)) excalibur();
            if (itemInHand.containsEnchantment(CustomEnchants.DRAGONDANCE)) dragonDance();
            if (itemInHand.containsEnchantment(CustomEnchants.HASTYMINER)) hastyMiner();
            if (itemInHand.containsEnchantment(CustomEnchants.BELLYDRUM)) bellyDrum();
        }
    }

    Map<String, Long> hmCooldowns = new HashMap<String, Long>();
    public void hastyMiner() {
        String playerName = player.getName();
        if (hmCooldowns.containsKey(player.getName())) {
            if (hmCooldowns.get(playerName) > System.currentTimeMillis()) {
                long timeLeft = (hmCooldowns.get(playerName) - System.currentTimeMillis()) / 1000;
                sendMsg(player, "&6Ability will be ready in " + timeLeft + " second(s)");
                return;
            }
        }
        int coolDownTime = 200 - ((itemInHand.getEnchantmentLevel(CustomEnchants.HASTYMINER)-1) * 10);
        ddCooldowns.put(playerName, System.currentTimeMillis() + (coolDownTime * 1000L));

        // particles
        ParticleData particle = new ParticleData(player.getUniqueId());
        if (particle.hasID()) {
            particle.endTask();
            particle.removeID();
        }
        Effects effects = new Effects(player);
        effects.startTotem(Particle.WAX_ON);
        Bukkit.getServer().getScheduler().runTaskLater(SleepyEnchants.getPlugin(SleepyEnchants.class), () -> {
            particle.endTask();
            particle.removeID();
        }, 60*20);
        player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 60*20, 1, true, false));
        player.playSound(player.getLocation(), "minecraft:custom.hastyminer", SoundCategory.MASTER, 100, 1);
        sendMsg(player, "&aUsing the Hasty Miner enchant!");
    }

    Map<String, Long> ddCooldowns = new HashMap<String, Long>();
    public void dragonDance() {
        String playerName = player.getName();
        if (ddCooldowns.containsKey(player.getName())) {
            if (ddCooldowns.get(playerName) > System.currentTimeMillis()) {
                long timeLeft = (ddCooldowns.get(playerName) - System.currentTimeMillis()) / 1000;
                sendMsg(player, "&6Ability will be ready in " + timeLeft + " second(s)");
                return;
            }
        }
        int coolDownTime = 300 - ((itemInHand.getEnchantmentLevel(CustomEnchants.EXCALIBUR)-1) * 10);
        ddCooldowns.put(playerName, System.currentTimeMillis() + (coolDownTime * 1000L));

        // particles
        ParticleData particle = new ParticleData(player.getUniqueId());
        if (particle.hasID()) {
            particle.endTask();
            particle.removeID();
        }
        Effects effects = new Effects(player);
        effects.startTotem(Particle.DRAGON_BREATH);
        Bukkit.getServer().getScheduler().runTaskLater(SleepyEnchants.getPlugin(SleepyEnchants.class), () -> {
            particle.endTask();
            particle.removeID();
        }, 60*20);

        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 60*20, 0, true, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 60*20, 1, true, false));

        player.playSound(player.getLocation(), "minecraft:custom.dragondance", SoundCategory.MASTER, 100, 1);
        sendMsg(player, "&aUsing the Dragon Dance enchant!");
    }

    Map<String, Long> exCooldowns = new HashMap<String, Long>();
    public void excalibur() {
        String playerName = player.getName();
        if (exCooldowns.containsKey(player.getName())) {
            if (exCooldowns.get(playerName) > System.currentTimeMillis()) {
                long timeLeft = (exCooldowns.get(playerName) - System.currentTimeMillis()) / 1000;
                sendMsg(player, "&6Ability will be ready in " + timeLeft + " second(s)");
                return;
            }
        }
        int coolDownTime = 600 - ((itemInHand.getEnchantmentLevel(CustomEnchants.EXCALIBUR)-1) * 10);
        exCooldowns.put(playerName, System.currentTimeMillis() + (coolDownTime * 1000L));

        // particles
        ParticleData particle = new ParticleData(player.getUniqueId());
        if (particle.hasID()) {
            particle.endTask();
            particle.removeID();
        }
        Effects effects = new Effects(player);
        effects.startTotem(Particle.END_ROD);
        Bukkit.getServer().getScheduler().runTaskLater(SleepyEnchants.getPlugin(SleepyEnchants.class), () -> {
            particle.endTask();
            particle.removeID();
        }, 60*20);
        Map<String, Long> exCooldowns = new HashMap<String, Long>();
        //Instant Health II
        //Strength — 1 minute
        //Speed — 1 minute
        //Resistance — 1 minute
        //Regeneration — 10 seconds
        //Absorption — 2 minutes

        player.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 1*20, 1, true, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 60*20, 0, true, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 60*20, 0, true, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 60*20, 0, true, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 10*20, 0, true, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 120*20, 0, true, false));

        player.playSound(player.getLocation(), "minecraft:custom.excalibur", SoundCategory.MASTER, 100, 1);
        sendMsg(player, "&aUsing the Excalibur enchant!");
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
        int coolDownTime = 300 - ((itemInHand.getEnchantmentLevel(CustomEnchants.SWORDSDANCE)-1) * 10);
        sDCooldowns.put(playerName, System.currentTimeMillis() + (coolDownTime * 1000L));

        // particles
        ParticleData particle = new ParticleData(player.getUniqueId());
        if (particle.hasID()) {
            particle.endTask();
            particle.removeID();
        }
        Effects effects = new Effects(player);
        effects.startTotem(Particle.WAX_OFF);
        Bukkit.getServer().getScheduler().runTaskLater(SleepyEnchants.getPlugin(SleepyEnchants.class), () -> {
            particle.endTask();
            particle.removeID();
        }, 60*20);

        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 60*20, 1, true, false));
        player.playSound(player.getLocation(), "minecraft:custom.swordsdance", SoundCategory.MASTER, 100, 1);
        sendMsg(player, "&aUsing the Swords Dance enchant!");
    }

    Map<String, Long> bdCooldowns = new HashMap<String, Long>();
    public void bellyDrum() {
        if (player.getHealth() >= 10.5) { //half a heart above 50% HP
            String playerName = player.getName();
            if (bdCooldowns.containsKey(player.getName())) {
                if (bdCooldowns.get(playerName) > System.currentTimeMillis()) {
                    long timeLeft = (bdCooldowns.get(playerName) - System.currentTimeMillis()) / 1000;
                    sendMsg(player, "&6Ability will be ready in " + timeLeft + " second(s)");
                    return;
                }
            }

                int coolDownTime = 350 - ((itemInHand.getEnchantmentLevel(CustomEnchants.BELLYDRUM)-1) * 10);
                bdCooldowns.put(playerName, System.currentTimeMillis() + (coolDownTime * 1000L));

                // particles
                ParticleData particle = new ParticleData(player.getUniqueId());
                if (particle.hasID()) {
                    particle.endTask();
                    particle.removeID();
                }
                Effects effects = new Effects(player);
                effects.startTotem(Particle.DAMAGE_INDICATOR);
                Bukkit.getServer().getScheduler().runTaskLater(SleepyEnchants.getPlugin(SleepyEnchants.class), () -> {
                    particle.endTask();
                    particle.removeID();
                }, 60 * 20);
            player.setHealth(player.getHealth() - 10);
            player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 60 * 20, 5, true, false));
            player.playSound(player.getLocation(), "minecraft:custom.bellydrum", SoundCategory.MASTER, 100, 1);
            sendMsg(player, "&aUsing the Belly Drum enchant!");
        } else {
            sendMsg(player, "&cYou must be above half HP to use Belly Drum!");
        }
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
            final double attackDamage = player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue();
            final int enchantLevel = itemInHand.getEnchantmentLevel(CustomEnchants.SPINATTACK);

            @Override
            public void run() {
                location.setYaw(location.getYaw()+60);
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
                                if (!entity.equals(player)) {;
                                    ((LivingEntity) entity).damage(attackDamage + enchantLevel);
                                    hitEntities.add(entity);
                                }
                            }
                        }
                    }
                }

                if (sALoopCount == 5) {

                    Bukkit.getScheduler().cancelTask(sATaskID);
                }
                sALoopCount++;
            }
        }, 0, 1);

        PlayerToggleSneak.fullCharged.remove(player.getUniqueId());
        sendMsg(player, "&aYou used the Spin Attack ability!");

        // Urbosa's Fury code
        if (itemInHand == null) return;
        if (!itemInHand.hasItemMeta()) return;
        if (!itemInHand.getItemMeta().hasEnchant(CustomEnchants.URBOSASFURY)) return;

        Block stoodBlock = location.getBlock().getRelative(BlockFace.DOWN);

        // first set
        Location locNorth = stoodBlock.getRelative(BlockFace.NORTH).getLocation();
        Location locEast = stoodBlock.getRelative(BlockFace.EAST).getLocation();
        Location locSouth = stoodBlock.getRelative(BlockFace.SOUTH).getLocation();
        Location locWest = stoodBlock.getRelative(BlockFace.WEST).getLocation();

        // second set
        Location locNorthEast = stoodBlock.getRelative(BlockFace.NORTH_EAST).getLocation();
        Location locSouthEast = stoodBlock.getRelative(BlockFace.SOUTH_EAST).getLocation();
        Location locSouthWest = stoodBlock.getRelative(BlockFace.SOUTH_WEST).getLocation();
        Location locNorthWest = stoodBlock.getRelative(BlockFace.NORTH_WEST).getLocation();


        Bukkit.getServer().getScheduler().runTaskLater(SleepyEnchants.getPlugin(SleepyEnchants.class), () -> {
            location.getWorld().strikeLightning(locNorth);
            location.getWorld().strikeLightning(locEast);
            location.getWorld().strikeLightning(locSouth);
            location.getWorld().strikeLightning(locWest);
        }, 8);

        Bukkit.getServer().getScheduler().runTaskLater(SleepyEnchants.getPlugin(SleepyEnchants.class), () -> {
            location.getWorld().strikeLightning(locNorthEast);
            location.getWorld().strikeLightning(locSouthEast);
            location.getWorld().strikeLightning(locSouthWest);
            location.getWorld().strikeLightning(locNorthWest);
        }, 16);
    }

    Map<String, Long> dashCooldowns = new HashMap<String, Long>();
    public void dash() {
        String playerName = player.getName();
        if (dashCooldowns.containsKey(player.getName())) {
            if (dashCooldowns.get(playerName) > System.currentTimeMillis()) {
                long timeLeft = (dashCooldowns.get(playerName) - System.currentTimeMillis()) / 1000;
                sendMsg(player, "&6Ability will be ready in " + timeLeft + " second(s)");
                return;
            }
        }
        int coolDownTime = 7;
        dashCooldowns.put(playerName, System.currentTimeMillis() + (coolDownTime * 1000L));
        itemInHand = player.getInventory().getItemInMainHand();
        double dist = 1.25 * itemInHand.getEnchantmentLevel(CustomEnchants.DASH);
        player.playSound(player.getLocation(), "minecraft:custom.dash", SoundCategory.MASTER, 100, 1);
        player.setVelocity(player.getLocation().getDirection().multiply(dist));
        sendMsg(player, "&aYou used the Dash ability!");

    }
}
