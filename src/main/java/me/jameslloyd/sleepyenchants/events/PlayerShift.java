package me.jameslloyd.sleepyenchants.events;

import me.jameslloyd.sleepyenchants.SleepyEnchants;
import me.jameslloyd.sleepyenchants.enchants.CustomEnchants;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Entity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class PlayerShift implements Listener {
    Player player;
    ItemStack Armor;
    TNTPrimed TNT;
    private void sendMsg(CommandSender sender, String msg) {
        sender.sendMessage((ChatColor.translateAlternateColorCodes('&', msg)));
    }

    @EventHandler
    public void onPlayerShift(PlayerToggleSneakEvent e){
        player = e.getPlayer();
        Armor = player.getInventory().getChestplate();
        if (Armor == null) return;
        if (!Armor.hasItemMeta()) return;
        if (Armor.containsEnchantment(CustomEnchants.BOMBERACE)) bomberAce();

    }

    Map<String, Long> aceCooldowns = new HashMap<String, Long>();
    public void bomberAce(){
        String playerName = player.getName();
        if(!player.isGliding()) return;
        if (aceCooldowns.containsKey(player.getName())) {
            if (aceCooldowns.get(playerName) > System.currentTimeMillis()) {
                long timeLeft = (aceCooldowns.get(playerName) - System.currentTimeMillis()) / 1000;
                sendMsg(player, "&6Ability will be ready in " + timeLeft + " second(s)");
                return;
            }
        }
        int coolDownTime = 30 - ((Armor.getEnchantmentLevel(CustomEnchants.BOMBERACE)-1) * 10);
        aceCooldowns.put(playerName, System.currentTimeMillis() + (coolDownTime * 1000L));

        TNT = (TNTPrimed)player.getWorld().spawnEntity(player.getLocation(), EntityType.PRIMED_TNT);
        TNT.setFuseTicks(50);
        sendMsg(player, "&aUsing the Bomber Ace enchant!");
    }
}
