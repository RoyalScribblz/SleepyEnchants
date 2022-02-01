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
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.concurrent.ThreadLocalRandom;

public class BlockBreak implements Listener{
    private void sendMsg(CommandSender sender, String msg) {
        sender.sendMessage((ChatColor.translateAlternateColorCodes('&', msg)));
    }
    ItemStack itemInHand;
    ItemStack Drops;
    Player player;
    Block block;

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        player = e.getPlayer();
        itemInHand = player.getInventory().getItemInMainHand();
        if (itemInHand == null) return;
        if (!itemInHand.hasItemMeta()) return;
        if (itemInHand.containsEnchantment(CustomEnchants.CUTCLEAN)) cutClean(e);

    }

    public void cutClean(BlockBreakEvent e){
        block = e.getBlock();
        //int dropnumber = e.getBlock().getDrops(itemInHand).size();
        int dropnumber = 1;
        double trigger_chance;
        for (ItemStack item : block.getDrops()){
            if (item.getType() == Material.RAW_IRON){
                dropnumber += item.getAmount();
            }
        }
        if (block.getType() == Material.IRON_ORE || block.getType() == Material.DEEPSLATE_IRON_ORE) {
            e.setDropItems(false);
            block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.IRON_INGOT, dropnumber));
        }

        if (block.getType() == Material.GOLD_ORE || block.getType() == Material.DEEPSLATE_GOLD_ORE) {
            e.setDropItems(false);
            block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.GOLD_INGOT, dropnumber));
        }
        if (block.getType() == Material.COPPER_ORE || block.getType() == Material.DEEPSLATE_COPPER_ORE) {
            e.setDropItems(false);
            block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.COPPER_INGOT, dropnumber));
        }
    }
}