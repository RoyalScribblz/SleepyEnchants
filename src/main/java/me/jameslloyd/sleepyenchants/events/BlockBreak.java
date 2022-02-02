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
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.bukkit.inventory.Recipe;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

public class BlockBreak implements Listener{
    private void sendMsg(CommandSender sender, String msg) {
        sender.sendMessage((ChatColor.translateAlternateColorCodes('&', msg)));
    }
    ItemStack itemInHand;
    ItemStack Drops;
    Player player;
    Block block;
    ItemStack result = null;

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
        int dropnumber = 0;
        for (ItemStack item : block.getDrops(itemInHand)){
            Iterator<Recipe> iter = Bukkit.recipeIterator();
            while (iter.hasNext()) {
                Recipe recipe = iter.next();
                if (!(recipe instanceof FurnaceRecipe)) continue;
                if (((FurnaceRecipe) recipe).getInput().getType() != item.getType()) continue;
                result = recipe.getResult();
                dropnumber += item.getAmount();
                break;
            }
        }
        //TODO Optimize this
        if(result == null) return;
        if (result.getType() == Material.IRON_INGOT || result.getType() == Material.GOLD_INGOT || result.getType() == Material.NETHERITE_SCRAP) {
            result.setAmount(dropnumber);
            block.setType(Material.AIR);
            block.getState().update();
            ((ExperienceOrb) block.getWorld().spawn(block.getLocation(), ExperienceOrb.class)).setExperience(1);
            block.getWorld().dropItemNaturally(block.getLocation(), result);
        }
    }
}