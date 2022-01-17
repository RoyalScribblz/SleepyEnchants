package me.jameslloyd.sleepyenchants.events;

import me.jameslloyd.sleepyenchants.particles.ParticleData;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class Quit implements Listener {
    public void onQuit(PlayerQuitEvent e) {
        ParticleData p = new ParticleData(e.getPlayer().getUniqueId());
        if (p.hasID()) p.endTask();
    }
}
