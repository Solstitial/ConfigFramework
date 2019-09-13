package net.lightshard.configframework.serialization;

import net.lightshard.configframework.Configurable;
import net.lightshard.configframework.YamlFile;
import net.lightshard.configframework.util.StringUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemStackSerializer implements TypeSerializer<ItemStack>
{

    @Override
    public boolean existsInFile(YamlFile loadedFile, Configurable configurable)
    {
        if(loadedFile.getString(configurable.path() + ".type") == null)
            return false;

        return true;
    }

    @Override
    public ItemStack getFromFile(YamlFile loadedFile, Configurable configurable)
    {
        String path = configurable.path();
        char colorChar = configurable.colorCharLocale();

        Material material = Material.getMaterial(loadedFile.getString(path + ".type"));
        if(material == null)
            return null;

        ItemStack item = new ItemStack(material);
        item.setAmount(loadedFile.getInt(path + ".amount"));
        item.setDurability(loadedFile.getShort(path + ".durability"));
        if(loadedFile.getBoolean(path + ".meta"))
        {
            ItemMeta meta = item.getItemMeta();
            String name = loadedFile.getString(path + ".name");
            if(name != null)
            {
                meta.setDisplayName(StringUtil.colorize(name, colorChar));
            }

            List<String> lore = loadedFile.getStringList(path + ".lore");
            if(lore != null && lore.size() > 0)
            {
                meta.setLore(StringUtil.colorize(lore, colorChar));
            }
            item.setItemMeta(meta);
        }

        return item;
    }

    @Override
    public void setInFile(YamlFile loadedFile, Configurable configurable, ItemStack item)
    {
        String path = configurable.path();
        char colorChar = configurable.colorCharLocale();

        loadedFile.set(path + ".type", item.getType().toString());
        loadedFile.set(path + ".amount", item.getAmount());
        loadedFile.set(path + ".durability", item.getDurability());
        if(item.hasItemMeta())
        {
            loadedFile.set(path + ".meta", true);
            ItemMeta meta = item.getItemMeta();
            if(meta.hasDisplayName())
            {
                loadedFile.set(path + ".name", StringUtil.reverseColor(meta.getDisplayName(), colorChar));
            }
            if(meta.hasLore())
            {
                loadedFile.set(path + ".lore", StringUtil.reverseColor(meta.getLore(), colorChar));
            }
        }
    }
}
