package me.jameslloyd.sleepyenchants.enchants;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.checkerframework.checker.units.qual.A;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

import static org.bukkit.Bukkit.getServer;

public class CustomEnchants {

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
    private static final List<Material> SHOVELS = Arrays.asList(Material.WOODEN_SHOVEL, Material.STONE_SHOVEL, Material.IRON_SHOVEL, Material.GOLDEN_SHOVEL, Material.DIAMOND_SHOVEL, Material.NETHERITE_SHOVEL, Material.WOODEN_PICKAXE, Material.STONE_PICKAXE, Material.IRON_PICKAXE, Material.GOLDEN_PICKAXE, Material.DIAMOND_PICKAXE, Material.NETHERITE_PICKAXE);
    private static final List<Material> SHOVELPICK = Arrays.asList(Material.WOODEN_SHOVEL, Material.STONE_SHOVEL, Material.IRON_SHOVEL, Material.GOLDEN_SHOVEL, Material.DIAMOND_SHOVEL, Material.NETHERITE_SHOVEL);
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
            getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&aSuccessfully registered the " + enchantment.getName() + " enchantment"));
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
}
