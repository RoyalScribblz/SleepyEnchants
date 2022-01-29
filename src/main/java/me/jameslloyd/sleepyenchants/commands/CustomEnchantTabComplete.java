package me.jameslloyd.sleepyenchants.commands;

import me.jameslloyd.sleepyenchants.enchants.CustomEnchants;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CustomEnchantTabComplete implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        List<String> completions = new ArrayList<String>();

        switch (args.length) {
            case 0:
                return completions;
            case 1:
                completions.addAll(CustomEnchants.getPossibleKeys());
                break;
            case 2:
                for (int i = 1; i <= CustomEnchants.getEnchant(args[0]).getMaxLevel(); i++) {
                    completions.add(Integer.toString(i));
                }
                break;
        }
        Collections.sort(completions);
        return StringUtil.copyPartialMatches(args[args.length - 1], completions, new ArrayList<>());
    }
}
