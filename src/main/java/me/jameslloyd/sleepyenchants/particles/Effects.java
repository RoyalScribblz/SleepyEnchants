package me.jameslloyd.sleepyenchants.particles;

import me.jameslloyd.sleepyenchants.SleepyEnchants;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

public class Effects {
    private int taskID;
    private final Player player;

    public Effects(Player player) {
        this.player = player;
    }

    public void startTotem(Particle p) {
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(SleepyEnchants.getPlugin(SleepyEnchants.class), new Runnable() {

            double var = 0;
            Location loc, first, second;
            ParticleData particle = new ParticleData(player.getUniqueId());


            @Override
            public void run() {
                if (!particle.hasID()) {
                    particle.setID(taskID);
                }

                var += Math.PI / 16;
                loc = player.getLocation();
                first = loc.clone().add(Math.cos(var), Math.sin(var)+1, Math.sin(var));
                second = loc.clone().add(Math.cos(var + Math.PI), Math.sin(var)+1, Math.sin(var + Math.PI));

                player.getWorld().spawnParticle(p, first, 0);
                player.getWorld().spawnParticle(p, second, 0);
            }
        }, 0, 1);
    }
}
