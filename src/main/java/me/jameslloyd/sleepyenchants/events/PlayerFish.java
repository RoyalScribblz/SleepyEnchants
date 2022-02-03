package me.jameslloyd.sleepyenchants.events;

import me.jameslloyd.sleepyenchants.enchants.CustomEnchants;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class PlayerFish implements Listener {
    private void sendMsg(CommandSender sender, String msg) {
        sender.sendMessage((ChatColor.translateAlternateColorCodes('&', msg)));
    }
    Player player;
    ItemStack itemInHand;

    @EventHandler
    public void onPlayerFish(PlayerFishEvent e){
        player = e.getPlayer();
        itemInHand = player.getItemInHand();
        if (!itemInHand.hasItemMeta()) return;
        if (itemInHand.containsEnchantment(CustomEnchants.GRAPPLINGHOOK)) grapplingHook(e);
    }

    Map<String, Long> gHCooldowns = new HashMap<String, Long>();
    public void grapplingHook(PlayerFishEvent e){
        if((e.getHook().getLocation().getBlock().getType()) == Material.WATER) return;
        if(!(e.getState().equals(PlayerFishEvent.State.REEL_IN))) return;
        String playerName = player.getName();
        if (gHCooldowns.containsKey(player.getName())) {
            if (gHCooldowns.get(playerName) > System.currentTimeMillis()) {
                long timeLeft = (gHCooldowns.get(playerName) - System.currentTimeMillis()) / 1000;
                sendMsg(player, "&6Ability will be ready in " + timeLeft + " second(s)");
                return;
            }
        }
        int coolDownTime = 3;
        gHCooldowns.put(playerName, System.currentTimeMillis() + (coolDownTime * 1000L));
        Location loc = player.getLocation();
        Location hookloc = e.getHook().getLocation();
        Location change = hookloc.subtract(loc);
        player.setVelocity(change.toVector().multiply(0.3 * itemInHand.getEnchantmentLevel(CustomEnchants.GRAPPLINGHOOK)));
    }
}
