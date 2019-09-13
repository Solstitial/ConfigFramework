package net.lightshard.configframework.util;

import org.bukkit.ChatColor;

import java.util.List;
import java.util.stream.Collectors;

public class StringUtil
{

    private StringUtil() {}

    public static String colorize(String string, char colorCharLocale)
    {
        if(string == null)
            return string;

        return ChatColor.translateAlternateColorCodes(colorCharLocale, string);
    }

    public static List<String> colorize(List<String> strings, final char colorCharLocale)
    {
        if(strings == null || strings.isEmpty())
            return strings;

        return strings
                .stream()
                .map(s -> {  return colorize(s, colorCharLocale); })
                .collect(Collectors.toList());
    }

    public static String reverseColor(String string, char colorCharLocale)
    {
        if(string == null)
            return string;

        return string.replace(ChatColor.COLOR_CHAR + "", colorCharLocale + "");
    }

    public static List<String> reverseColor(List<String> strings, final char colorCharLocale)
    {
        if(strings == null || strings.isEmpty())
            return strings;

        return strings
                .stream()
                .map(s -> {  return reverseColor(s, colorCharLocale); })
                .collect(Collectors.toList());
    }

}
