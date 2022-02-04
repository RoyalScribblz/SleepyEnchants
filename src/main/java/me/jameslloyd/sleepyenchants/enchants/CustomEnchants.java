package me.jameslloyd.sleepyenchants.enchants;

import de.tr7zw.nbtapi.NBTItem;
import me.jameslloyd.sleepyenchants.utils.NamingUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.checkerframework.checker.units.qual.A;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static org.bukkit.Bukkit.getServer;

public class CustomEnchants {

    private static String colourise(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    // rarities
    private static final ChatColor COMMON = ChatColor.GRAY;
    private static final ChatColor RARE = ChatColor.GREEN;
    private static final ChatColor EXOTIC = ChatColor.GOLD;
    private static final ChatColor LEGENDARY = ChatColor.LIGHT_PURPLE;

    // compatible material sets
    private static final List<Material> SWORDS = Arrays.asList(Material.WOODEN_SWORD, Material.STONE_SWORD, Material.IRON_SWORD, Material.GOLDEN_SWORD, Material.DIAMOND_SWORD, Material.NETHERITE_SWORD);
    private static final List<Material> AXES = Arrays.asList(Material.WOODEN_AXE, Material.STONE_AXE, Material.IRON_AXE, Material.GOLDEN_AXE, Material.DIAMOND_AXE, Material.NETHERITE_AXE);
    private static final List<Material> MELEE = Arrays.asList(Material.WOODEN_AXE, Material.STONE_AXE, Material.IRON_AXE, Material.GOLDEN_AXE, Material.DIAMOND_AXE, Material.NETHERITE_AXE, Material.WOODEN_SWORD, Material.STONE_SWORD, Material.IRON_SWORD, Material.GOLDEN_SWORD, Material.DIAMOND_SWORD, Material.NETHERITE_SWORD);
    private static final List<Material> PICKAXES = Arrays.asList(Material.WOODEN_PICKAXE, Material.STONE_PICKAXE, Material.IRON_PICKAXE, Material.GOLDEN_PICKAXE, Material.DIAMOND_PICKAXE, Material.NETHERITE_PICKAXE);
    private static final List<Material> SHOVELSPICKS = Arrays.asList(Material.WOODEN_SHOVEL, Material.STONE_SHOVEL, Material.IRON_SHOVEL, Material.GOLDEN_SHOVEL, Material.DIAMOND_SHOVEL, Material.NETHERITE_SHOVEL, Material.WOODEN_PICKAXE, Material.STONE_PICKAXE, Material.IRON_PICKAXE, Material.GOLDEN_PICKAXE, Material.DIAMOND_PICKAXE, Material.NETHERITE_PICKAXE);
    private static final List<Material> SHOVELS = Arrays.asList(Material.WOODEN_SHOVEL, Material.STONE_SHOVEL, Material.IRON_SHOVEL, Material.GOLDEN_SHOVEL, Material.DIAMOND_SHOVEL, Material.NETHERITE_SHOVEL);
    private static final List<Material> SHIELD = Collections.singletonList(Material.SHIELD);
    private static final List<Material> BOW = Collections.singletonList(Material.BOW);
    private static final List<Material> CROSSBOW = Collections.singletonList(Material.CROSSBOW);
    private static final List<Material> HELMETS = Arrays.asList(Material.LEATHER_HELMET, Material.CHAINMAIL_HELMET, Material.IRON_HELMET, Material.GOLDEN_HELMET, Material.DIAMOND_HELMET, Material.NETHERITE_HELMET);
    private static final List<Material> CHESTPLATES = Arrays.asList(Material.LEATHER_CHESTPLATE, Material.CHAINMAIL_CHESTPLATE, Material.IRON_CHESTPLATE, Material.GOLDEN_CHESTPLATE, Material.DIAMOND_CHESTPLATE, Material.NETHERITE_CHESTPLATE);
    private static final List<Material> LEGGINGS = Arrays.asList(Material.LEATHER_LEGGINGS, Material.CHAINMAIL_LEGGINGS, Material.IRON_LEGGINGS, Material.GOLDEN_LEGGINGS, Material.DIAMOND_LEGGINGS, Material.NETHERITE_LEGGINGS);
    private static final List<Material> BOOTS = Arrays.asList(Material.LEATHER_BOOTS, Material.CHAINMAIL_BOOTS, Material.IRON_BOOTS, Material.GOLDEN_BOOTS, Material.DIAMOND_BOOTS, Material.NETHERITE_BOOTS);
    private static final List<Material> ARMOUR = Arrays.asList(Material.LEATHER_HELMET, Material.CHAINMAIL_HELMET, Material.IRON_HELMET, Material.GOLDEN_HELMET, Material.DIAMOND_HELMET, Material.NETHERITE_HELMET,
                                              Material.LEATHER_CHESTPLATE, Material.CHAINMAIL_CHESTPLATE, Material.IRON_CHESTPLATE, Material.GOLDEN_CHESTPLATE, Material.DIAMOND_CHESTPLATE, Material.NETHERITE_CHESTPLATE,
                                              Material.LEATHER_LEGGINGS, Material.CHAINMAIL_LEGGINGS, Material.IRON_LEGGINGS, Material.GOLDEN_LEGGINGS, Material.DIAMOND_LEGGINGS, Material.NETHERITE_LEGGINGS,
                                              Material.LEATHER_BOOTS, Material.CHAINMAIL_BOOTS, Material.IRON_BOOTS, Material.GOLDEN_BOOTS, Material.DIAMOND_BOOTS, Material.NETHERITE_BOOTS);
    private static final List<Material> ELYTRA = Collections.singletonList(Material.ELYTRA);
    private static final List<Material> FISHINGROD = Collections.singletonList(Material.FISHING_ROD);
    private static final List<Material> HOES = Arrays.asList(Material.WOODEN_HOE, Material.STONE_HOE, Material.IRON_HOE, Material.GOLDEN_HOE, Material.DIAMOND_HOE, Material.NETHERITE_HOE);
    private static final List<Material> EVERYTHING = null;  // check for null

    // enchants
    public static final Enchantment BLADEBEAM = new EnchantmentWrapper("bladebeam", "Blade Beam", 2, SWORDS, LEGENDARY);
    public static final Enchantment SWORDSDANCE = new EnchantmentWrapper("swordsdance", "Swords Dance", 5, SWORDS, EXOTIC);
    public static final Enchantment SPINATTACK = new EnchantmentWrapper("spinattack", "Spin Attack", 3, SWORDS, EXOTIC);
    public static final Enchantment URBOSASFURY = new EnchantmentWrapper("urbosasfury", "Urbosa's Fury", 1, SWORDS, LEGENDARY);
    public static final Enchantment EXCALIBUR = new EnchantmentWrapper("excalibur", "Excalibur", 5, SWORDS, LEGENDARY);
    public static final Enchantment DASH = new EnchantmentWrapper("dash", "Dash", 3, SWORDS, EXOTIC);
    public static final Enchantment DEVILSSCYTHE = new EnchantmentWrapper("devilsscythe", "Devil's Scythe", 5, SWORDS, RARE);
    public static final Enchantment SWIFTBLADE = new EnchantmentWrapper("swiftblade", "Swift Blade", 5, SWORDS, RARE);
    public static final Enchantment ICEASPECT = new EnchantmentWrapper("iceaspect", "Ice Aspect", 2, SWORDS, RARE);
    public static final Enchantment BOMBERACE = new EnchantmentWrapper("bomberace", "Bomber Ace", 3, ELYTRA, RARE);
    public static final Enchantment DRAGONDANCE = new EnchantmentWrapper("dragondance", "Dragon Dance", 5, AXES, EXOTIC);
    public static final Enchantment WINGARDIUMLEVIOSA = new EnchantmentWrapper("wingardiumleviosa", "Wingardium Leviosa", 3, SWORDS, COMMON);
    public static final Enchantment SHEERCOLD = new EnchantmentWrapper("sheercold", "Sheer Cold", 2, BOW, EXOTIC);
    public static final Enchantment HASTYMINER = new EnchantmentWrapper("hastyminer", "Hasty Miner", 5, PICKAXES, RARE);
    public static final Enchantment BELLYDRUM = new EnchantmentWrapper("bellydrum", "Belly Drum", 5, AXES, EXOTIC);
    public static final Enchantment CUTCLEAN = new EnchantmentWrapper("cutclean", "Cut Clean", 1, PICKAXES, COMMON);
    public static final Enchantment POTLUCK = new EnchantmentWrapper("potluck", "Potluck", 1, SWORDS, COMMON);
    public static final Enchantment BOMBER = new EnchantmentWrapper("bomber", "Bomber", 1, BOW, COMMON);
    public static final Enchantment DRAGONSBREATH = new EnchantmentWrapper("dragonsbreath", "Dragon's Breath", 1, BOW, RARE);
    public static final Enchantment WITHERSSKULL = new EnchantmentWrapper("withersskull", "Wither's Skull", 1, BOW, RARE);
    public static final Enchantment GRAPPLINGHOOK = new EnchantmentWrapper("grapplinghook", "Grappling Hook", 3, FISHINGROD, RARE);
    public static final Enchantment AXETHROW = new EnchantmentWrapper("axethrow", "Axe Throw", 3, AXES, RARE);

    // map of namespaces and enchants
    private static final HashMap<String, Enchantment> ENCHANT_KEYS = new HashMap<String, Enchantment>();

    // each rarity
    private static final List<Enchantment> COMMON_ENCHANTS = new ArrayList<Enchantment>(Arrays.asList(WINGARDIUMLEVIOSA, CUTCLEAN, POTLUCK, BOMBER));
    private static final List<Enchantment> RARE_ENCHANTS = new ArrayList<Enchantment>(Arrays.asList(DEVILSSCYTHE, SWIFTBLADE, ICEASPECT, BOMBERACE, HASTYMINER, DRAGONSBREATH, WITHERSSKULL, GRAPPLINGHOOK, AXETHROW));
    private static final List<Enchantment> EXOTIC_ENCHANTS = new ArrayList<Enchantment>(Arrays.asList(SWORDSDANCE, SPINATTACK, DASH, DRAGONDANCE, SHEERCOLD, BELLYDRUM));
    private static final List<Enchantment> HEROIC_ENCHANTS = new ArrayList<Enchantment>(Arrays.asList());
    private static final List<Enchantment> LEGENDARY_ENCHANTS = new ArrayList<Enchantment>(Arrays.asList(BLADEBEAM, URBOSASFURY, EXCALIBUR));

    // set of all possible enchants
    private static final List<Enchantment> ALL = new ArrayList<>();

    public static void register() {

        // register all enchantments in ALL after adding each rarity to it
        ALL.addAll(COMMON_ENCHANTS);
        ALL.addAll(RARE_ENCHANTS);
        ALL.addAll(EXOTIC_ENCHANTS);
        ALL.addAll(HEROIC_ENCHANTS);
        ALL.addAll(LEGENDARY_ENCHANTS);

        for (Enchantment enchant: ALL) {
            boolean registered = Arrays.stream(Enchantment.values()).collect(Collectors.toList()).contains(enchant);
            if (!registered) registerEnchantment(enchant);
        }
    }

    public static void registerEnchantment(Enchantment enchantment) {
        boolean registered = true;
        try {
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);
            Enchantment.registerEnchantment(enchantment);
        } catch (Exception e) {
            registered = false;
            e.printStackTrace();
        }

        if (registered) {
            ENCHANT_KEYS.put(String.valueOf(enchantment.getKey()).substring(10), enchantment);
            getServer().getConsoleSender().sendMessage(colourise("&aSuccessfully registered the " + enchantment.getName() + " enchantment"));
        }
    }

    public static Enchantment getEnchant(String s) {
        return ENCHANT_KEYS.get(s);
    }

    public static Set<String> getPossibleKeys() {
        return ENCHANT_KEYS.keySet();
    }

    public static List<Enchantment> getAll() {return ALL;}

    public static List<Enchantment> getCommon() {return COMMON_ENCHANTS;}
    public static List<Enchantment> getRare() {return RARE_ENCHANTS;}
    public static List<Enchantment> getExotic() {return EXOTIC_ENCHANTS;}
    public static List<Enchantment> getHeroic() {return HEROIC_ENCHANTS;}
    public static List<Enchantment> getLegendary() {return LEGENDARY_ENCHANTS;}

    public static boolean applyEnchant(ItemStack item, EnchantmentWrapper enchant, int level) {

        // check compatible, if not, don't modify, size 0 means compatible with all
        List<Material> compatibleMaterials = enchant.getMaterials();
        if (!(compatibleMaterials.contains(item.getType()) || compatibleMaterials.size() == 0)) return false;

        // check level doesn't supersede max level
        if (level > enchant.getMaxLevel()) level = enchant.getMaxLevel();

        if (item.containsEnchantment(enchant)) {
            if (level != 0 && item.getEnchantmentLevel(enchant) >= level) {  // if zero we remove the enchantment
                return false;  // don't continue if already higher unless 0
            }
        }

        if (level == 0) {  // remove if 0
            item.removeEnchantment(enchant);
        } else {  //
            item.addUnsafeEnchantment(enchant, level);
        }
        String name = enchant.getName();
        ChatColor colour = enchant.getColour();

        // add enchant name to the lore
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();
        if (meta.hasLore()) lore = meta.getLore(); // get lore if preexisting

        lore.removeIf(l -> l.startsWith(colour + name));  // remove old lore of this enchant

        // insert new lore at the start to prevent going after non-enchant lore
        if (level != 0) {
            lore.add(0, colour + name + " " + NamingUtils.intToRomanNumeral(level));
        }

        meta.setLore(lore);
        item.setItemMeta(meta);

        // special cases
        if (enchant == CustomEnchants.SWIFTBLADE) {
            double additionalSpeed = item.getEnchantmentLevel(CustomEnchants.SWIFTBLADE) * 0.5;
            meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED,
                    new AttributeModifier(UUID.randomUUID(), "attackSpeed", additionalSpeed, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
            item.setItemMeta(meta);
        }

        return true;
    }

    public static void giveRandomBook(Player player, List<Enchantment> possibleEnchants) {
        // pick a random enchant from the set of possible enchants
        int randInt = ThreadLocalRandom.current().nextInt(possibleEnchants.size());
        Enchantment enchant = possibleEnchants.get(randInt);
        giveBook(player, (EnchantmentWrapper) enchant);
    }

    public static void giveBook(Player player, EnchantmentWrapper enchant) {
        // create book item and name it
        ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta bookMeta = book.getItemMeta();
        String enchantName = colourise("&" + enchant.getColour().getChar() + "&l" + enchant.getName());
        bookMeta.setDisplayName(colourise("&8-= " + enchantName + " &fI &8=-"));

        // lore
        ArrayList<String> bookLore = new ArrayList<String>();
        bookLore.add(colourise("&f&oApplication: &f&n" + getCompatibleName(enchant)));
        bookMeta.setLore(bookLore);
        book.setItemMeta(bookMeta);

        // give the book an NBT tag for the enchantment
        NBTItem nbti = new NBTItem(book);
        nbti.setString("CustomEnchant", String.valueOf(enchant.getKey()).substring(10));
        book = nbti.getItem();

        // give the player the book
        player.getInventory().addItem(book);
    }

    public static String getCompatibleName(EnchantmentWrapper enchant) {
        List<Material> mats = enchant.getMaterials();
        if (mats == SWORDS) {
            return "Swords";
        }
        if (mats == AXES) {
            return "Axes";
        }
        if (mats == MELEE) {
            return "Melee";
        }
        if (mats == PICKAXES) {
            return "Pickaxes";
        }
        if (mats == SHOVELSPICKS) {
            return "Shovels & Pickaxes";
        }
        if (mats == SHOVELS) {
            return "Shovels";
        }
        if (mats == SHIELD) {
            return "Shields";
        }
        if (mats == BOW) {
            return "Bows";
        }
        if (mats == CROSSBOW) {
            return "Crossbows";
        }
        if (mats == HELMETS) {
            return "Helmets";
        }
        if (mats == CHESTPLATES) {
            return "Chestplates";
        }
        if (mats == LEGGINGS) {
            return "Leggings";
        }
        if (mats == BOOTS) {
            return "Boots";
        }
        if (mats == ARMOUR) {
            return "All Armour";
        }
        if (mats == ELYTRA) {
            return "Elytra";
        }
        if (mats == FISHINGROD) {
            return "Fishing Rods";
        }
        if (mats == HOES) {
            return "Hoes";
        }
        if (mats == EVERYTHING) {
            return "All Items";
        }
        return "";
    }
}
