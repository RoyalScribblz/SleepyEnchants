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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerToggleSneak implements Listener {

    private void sendMsg(CommandSender sender, String msg) {
        sender.sendMessage((ChatColor.translateAlternateColorCodes('&', msg)));
    }
    Player player;
    ItemStack itemInHand;
    private int taskID;
    public static ArrayList<UUID> fullCharged = new ArrayList<UUID>();

    @EventHandler
    public void onToggleSneak(PlayerToggleSneakEvent e) {
        player = e.getPlayer();
        itemInHand = player.getInventory().getItemInMainHand();
        if (itemInHand == null) return;
        if (!itemInHand.hasItemMeta()) return;
        if (!itemInHand.containsEnchantment(CustomEnchants.SPINATTACK)) return;

        if (e.isSneaking()) {
            taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(SleepyEnchants.getPlugin(SleepyEnchants.class), new Runnable() {

                int completion = 0;

                public void run() {
                    if (completion < 60) {
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.translateAlternateColorCodes('&', "&f[&r" + "|".repeat(completion) + "&f]")));
                        completion++;
                        return;
                    }

                    if (completion == 60) {
                        fullCharged.add(player.getUniqueId());
                        sendMsg(player, "&aThe spin attack is fully charged!");
                        completion++;
                    }
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.translateAlternateColorCodes('&', "&f[&a" + "|".repeat(completion) + "&f]")));
                }
            }, 0, 1);
            return;
        }

        // un-sneaking
        if (fullCharged.contains(player.getUniqueId())) {
            fullCharged.remove(player.getUniqueId());
            Bukkit.getScheduler().cancelTask(taskID);
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(""));
        }
    }
}
