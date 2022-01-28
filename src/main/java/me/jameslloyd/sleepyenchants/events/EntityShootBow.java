package me.jameslloyd.sleepyenchants.events;

import me.jameslloyd.sleepyenchants.SleepyEnchants;
import me.jameslloyd.sleepyenchants.enchants.CustomEnchants;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.Set;

public class EntityShootBow implements Listener {

    private void sendMsg(CommandSender sender, String msg) {
        sender.sendMessage((ChatColor.translateAlternateColorCodes('&', msg)));
    }

    Entity projectile;
    ItemStack bow;

    @EventHandler
    public void onShootBow(EntityShootBowEvent e) {
        projectile = e.getProjectile();
        bow = e.getBow();

        if (!bow.hasItemMeta()) return;

        if (bow.getItemMeta().hasEnchant(CustomEnchants.SHEERCOLD)) sheerCold(e);
    }

    int sheerColdTaskID;
    public void sheerCold(EntityShootBowEvent e) {
        sheerColdTaskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(SleepyEnchants.getPlugin(SleepyEnchants.class), new Runnable() {

            final int RADIUS = 5;
            @Override
            public void run() {
                // find the highest water block
                Block block = projectile.getLocation().getBlock();
                while (block.getRelative(BlockFace.UP).getType() == Material.WATER) {
                    block = block.getRelative(BlockFace.UP);
                }

                // when the arrow touches water
                if (block.getType() == Material.WATER) {

                    // get all blocks in 5 radius
                    HashSet<Block> blocksInRadius = new HashSet<Block>();
                    blocksInRadius.add(block);
                    for(int x = -RADIUS; x <= RADIUS; x++) {
                        for(int z = -RADIUS; z <= RADIUS; z++) {
                            Block b = block.getRelative(x, 0, z);
                            if(block.getLocation().distance(b.getLocation()) <= RADIUS) {
                                if (b.getType() == Material.WATER) {  // only add water blocks
                                    blocksInRadius.add(b);
                                }
                            }
                        }
                    }

                    // set them all to ice
                    for (Block b: blocksInRadius) {
                        b.setType(Material.ICE);
                    }

                    // put the ice back to water after certain amount of seconds
                    Bukkit.getServer().getScheduler().runTaskLater(SleepyEnchants.getPlugin(SleepyEnchants.class), () -> {
                        for (Block b: blocksInRadius) {
                            b.setType(Material.WATER);
                        }
                    }, 20*20);

                    Bukkit.getScheduler().cancelTask(sheerColdTaskID);
                }

                // stopped moving without hitting water so end task
                if (projectile.isOnGround()) {
                    Bukkit.getScheduler().cancelTask(sheerColdTaskID);
                }
            }
        }, 0, 1);  // repeat every tick
    }
}
