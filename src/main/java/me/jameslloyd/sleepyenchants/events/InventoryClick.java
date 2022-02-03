package me.jameslloyd.sleepyenchants.events;

import me.jameslloyd.sleepyenchants.enchants.CustomEnchants;
import me.jameslloyd.sleepyenchants.utils.Experience;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class InventoryClick implements Listener {

    private static Inventory enchantMenu;

    private void sendMsg(CommandSender sender, String msg) {
        sender.sendMessage((colourise(msg)));
    }
    
    private static String colourise(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static void openEnchantMenu(Player player) {
        enchantMenu = Bukkit.createInventory(null, 45);

        // border
        ItemStack border = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta borderMeta = border.getItemMeta();
        borderMeta.setDisplayName(" ");
        border.setItemMeta(borderMeta);
        for (int i : new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 37, 38, 39, 40, 41, 42, 43}) {
            enchantMenu.setItem(i, border);
        }

        // rarities
        int[] positions = {20, 21, 22, 23, 24};
        Material[] materials = {Material.WHITE_STAINED_GLASS_PANE, Material.LIME_STAINED_GLASS_PANE, Material.YELLOW_STAINED_GLASS_PANE, Material.LIGHT_BLUE_STAINED_GLASS_PANE, Material.MAGENTA_STAINED_GLASS_PANE};
        String[] displayNames = {"&7&oCommon &f&lEnchant", "&a&oRare &f&lEnchant", "&6&oExotic &f&lEnchant", "&b&oHeroic &f&lEnchant", "&d&oLegendary &f&lEnchant"};
        int[] costs = {500, 1000, 1500, 2000, 2500};
        for (int i = 0; i < 5; i++) {
            ItemStack rarity = new ItemStack(materials[i]);
            ItemMeta rarityMeta = rarity.getItemMeta();
            rarityMeta.setDisplayName(colourise(displayNames[i]));
            ArrayList<String> rarityLore = new ArrayList<String>();
            rarityLore.add(colourise("&fXP Cost: &a" + costs[i]));
            rarityMeta.setLore(rarityLore);
            rarity.setItemMeta(rarityMeta);
            enchantMenu.setItem(positions[i], rarity);
        }

        // enchant book decor
        ItemStack enchantedBook = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta enchantedBookMeta = enchantedBook.getItemMeta();
        enchantedBookMeta.setDisplayName(" ");
        enchantedBook.setItemMeta(enchantedBookMeta);
        for (int i : new int[]{13, 31}) {
            enchantMenu.setItem(i, enchantedBook);
        }

        // exit button
        ItemStack exit = new ItemStack(Material.BARRIER);
        ItemMeta exitMeta = exit.getItemMeta();
        exitMeta.setDisplayName(colourise("&f&oExit <-"));
        exit.setItemMeta(exitMeta);
        enchantMenu.setItem(36, exit);

        // get xp button
        ItemStack getXp = new ItemStack(Material.NETHER_STAR);
        ItemMeta getXpMeta = getXp.getItemMeta();
        getXpMeta.setDisplayName(colourise("&fYou have &a" + Experience.getExp(player) + " &fxp."));
        getXp.setItemMeta(getXpMeta);
        enchantMenu.setItem(44, getXp);

        player.openInventory(enchantMenu);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (!e.getInventory().equals(enchantMenu)) return;
        e.setCancelled(true);

        Player player = (Player) e.getWhoClicked();
        int xp = Experience.getExp(player);

        switch (e.getSlot()) {
            case 36: // exit
                player.closeInventory();
                break;

            case 20: // common
                if (xp <= 500) break;
                Experience.changeExp(player, -500);
                giveBook(player, CustomEnchants.getCommon());
                sendMsg(player, "&fYou &asuccessfully &fbought a &7&oCommon &f&lEnchant");
                break;

            case 21: // rare
                if (xp <= 1000) break;
                Experience.changeExp(player, -1000);
                giveBook(player, CustomEnchants.getRare());
                sendMsg(player, "&fYou &asuccessfully &fbought a &a&oRare &f&lEnchant");
                break;

            case 22: // exotic
                if (xp <= 1500) break;
                Experience.changeExp(player, -1500);
                giveBook(player, CustomEnchants.getExotic());
                sendMsg(player, "&fYou &asuccessfully &fbought a &6&oExotic &f&lEnchant");
                break;

            case 23: // heroic
                if (xp <= 2000) break;
                Experience.changeExp(player, -2000);
                giveBook(player, CustomEnchants.getHeroic());
                sendMsg(player, "&fYou &asuccessfully &fbought a &b&oHeroic &f&lEnchant");
                break;

            case 24: // legendary
                if (xp <= 2500) break;
                Experience.changeExp(player, -2500);
                giveBook(player, CustomEnchants.getLegendary());
                sendMsg(player, "&fYou &asuccessfully &fbought a &d&oLegendary &f&lEnchant");
                break;
        }
    }

    void giveBook(Player player, List<Enchantment> possibleEnchants) {

        // pick a random enchant from the set of possible enchants
        int randInt = ThreadLocalRandom.current().nextInt(possibleEnchants.size());
        Enchantment enchant = possibleEnchants.get(randInt);

        ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta bookMeta = book.getItemMeta();
        bookMeta.setDisplayName(colourise("&f&n" + enchant.getKey()));
        book.setItemMeta(bookMeta);
        player.getInventory().addItem(book);
    }
}
