package io.github.tivecs.theolib.util.string;

import org.bukkit.ChatColor;

import java.util.List;

public class StringColor {

    public static String colored(String text, char prefix){
        return ChatColor.translateAlternateColorCodes(prefix, text);
    }

    public static String colored(String text){
        return colored(text, '&');
    }

    public static List<String> colored(List<String> list, char prefix){
        list.replaceAll(text -> colored(text, prefix));
        return list;
    }

    public static List<String> colored(List<String> list){
        list.replaceAll(text -> colored(text, '&'));
        return list;
    }

}
