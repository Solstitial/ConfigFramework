package net.lightshard.configframework;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class YamlFile
{
    private File folder;
    private File file;
    private YamlConfiguration config;
  
    public YamlFile(File folder, String fileName)
    {
        this.folder = folder;
        this.file = new File(folder, fileName + ".yml");
        this.config = new YamlConfiguration();
    }
  
    public YamlFile(File folder, File file)
    {
        this.folder = folder;
        this.file = file;
        this.config = new YamlConfiguration();
    }

    public YamlFile(File file)
    {
        this.folder = file.getParentFile();
        this.file = file;
        this.config = new YamlConfiguration();
    }
  
    public File getJavaFile()
    {
        return file;
    }
  
    public String getName()
    {
        return file.getName();
    }
  
    public boolean delete()
    {
        file.delete();
        return true;
    }
  
    public Set<String> getKeys()
    {
        return config.getKeys(false);
    }

    public boolean keyExists(String key)
    {
        String keyLower = key.toLowerCase();

        for (String elem : config.getKeys(true))
        {
            if (elem.toLowerCase().equals(keyLower))
                return true;
        }
        return false;
    }
  
    public boolean create()
    {
        if(!(folder.exists()))
        {
            folder.mkdirs();
        }
        if(!(file.exists()))
        {
            try
            {
                file.createNewFile();
                return true;
            }
            catch (IOException e)
            {
                e.printStackTrace();
                return false;
            }
        }
        else
        {
            return true;
        }
    }
  
    public boolean exists()
    {
        if(file.exists())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
  
    public boolean load()
    {
        try
        {
            config.load(file);
            return true;
        }
        catch(Exception exception)
        {
        	exception.printStackTrace();
            create();
            return false;
        }
    }
  
    public boolean save()
    {
        try
        {
            config.save(file);
            return true;
        }
        catch (IOException e)
        {
            System.out.println("Failed to save file: " + file.getName());
            return false;
        }
    }

    public FileConfiguration getFileConfiguration()
    {
        return config;
    }

    public ConfigurationSection getSection(String path)
    {
        return config.getConfigurationSection(path);
    }

    public void set(String path, Object value)
    {
        config.set(path, value);
    }
    
    public Object getObject(String path)
    {
    	return config.get(path);
    }
    
    public ItemStack getItemStack(String path)
    {
    	return config.getItemStack(path);
    }
  
    public int getInt(String path)
    {
        return config.getInt(path);
    }
  
    public String getString(String path)
    {
        return config.getString(path);
    }
  
    public long getLong(String path)
    {
        return config.getLong(path);
    }
  
    public boolean getBoolean(String path)
    {
        return config.getBoolean(path);
    }
  
    public List<String> getStringList(String path)
    {
        return config.getStringList(path);
    }
  
    public List<Integer> getIntList(String path)
    {
        return config.getIntegerList(path);
    }
  
    public float getFloat(String path)
    {
        return (float) getDouble(path);
    }
  
    public List<?> getList(String path)
    {
        return config.getList(path);
    }
  
    public double getDouble(String path)
    {
        return config.getDouble(path);
    }
  
    public short getShort(String path)
    {
        return (short) config.getInt(path);
    }
  
}