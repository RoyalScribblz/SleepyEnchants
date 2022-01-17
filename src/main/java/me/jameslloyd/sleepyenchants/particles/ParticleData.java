package me.jameslloyd.sleepyenchants.particles;

import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ParticleData {

    private static Map<UUID, Integer> TRAILS = new HashMap<UUID, Integer>();
    private final UUID uuid;

    public ParticleData(UUID uuid) {
        this.uuid = uuid;
    }

    public void setID(int id) {
        TRAILS.put(uuid, id);
    }

    public int getID() {
        return TRAILS.get(uuid);
    }

    public boolean hasID() {
        return TRAILS.containsKey(uuid);
    }

    public void removeID() {
        TRAILS.remove(uuid);
    }

    public void endTask() {
        Bukkit.getScheduler().cancelTask(getID());
    }
}
