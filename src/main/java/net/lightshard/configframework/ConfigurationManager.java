package net.lightshard.configframework;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Ben O'Rourke
 */
public class ConfigurationManager
{

    private JavaPlugin plugin;

    public ConfigurationManager(JavaPlugin plugin)
    {
        this.plugin = plugin;
    }

    public void load(Class<?> configurationClazz)
    {
        HashMap<Field, Configurable> configurableValues = new HashMap<Field, Configurable>();

        if (!configurationClazz.isAnnotationPresent(Configuration.class))
        {
            log(Level.SEVERE, "Unable to load config " + configurationClazz.getSimpleName()
                    + ", the class doesn't have the Configuration annotation");
            return;
        }

        Configuration configuration = configurationClazz.getAnnotation(Configuration.class);
        for (Field field : configurationClazz.getDeclaredFields())
        {
            if (field.isAnnotationPresent(Configurable.class))
            {
                if (!Modifier.isStatic(field.getModifiers()))
                {
                    log(Level.WARNING, "Unable to use config field in " + configurationClazz.getSimpleName()
                            + " (" + field.getName() + ") - it's not static!");
                    continue;
                }
                Configurable configurable = field.getAnnotation(Configurable.class);

                for (Configurable value : configurableValues.values())
                {
                    if (configurable.path().equalsIgnoreCase(value.path()))
                    {
                        log(Level.SEVERE, "Duplicate path names in " + configurationClazz.getSimpleName()
                                + ": " + configurable.path() + " (" + value.path() + ")");
                    }
                }

                configurableValues.put(field, configurable);
            }
        }

        YamlFile file = getFile(configuration);
        if (!file.exists())
        {
            //Saving all the values from scratch
            file.create();
            file.load();
            file.set("VERSION", configuration.version());
            for (Entry<Field, Configurable> entry : configurableValues.entrySet())
            {
                setValue(file, entry.getKey(), entry.getValue());

                //Add color to relevant types
                Field field = entry.getKey();
                field.setAccessible(true);
                try
                {
                    field.set(null, field.get(null));
                }
                catch (IllegalArgumentException | IllegalAccessException ignored)
                {
                    ignored.printStackTrace();
                }
            }
            file.save();
        }
        else
        {
            //Load all the values
            file.load();
            for (Entry<Field, Configurable> entry : configurableValues.entrySet())
            {
                Field field = entry.getKey();
                Configurable configurable = entry.getValue();
                if (isPresent(file, configurable))
                {
                    //The value is present, so we'll load it
                    Object found = loadValue(file, configurable);
                    if (found == null)
                    {
                        log(Level.INFO, "Unable to find value from config for " + field.getName());
                        continue;
                    }

                    try
                    {
                        field.setAccessible(true);
                        field.set(null, found);
                    }
                    catch (IllegalArgumentException | IllegalAccessException e)
                    {
                        log(Level.INFO, "Unable to set field value of " + field.getName() + " as " + found);
                    }
                }
                else
                {
                    //The value wasn't present, so we'll patch it
                    setValue(file, field, configurable);
                    file.save();
                    //					Logger.log(Level.INFO, "Patched " + field.getName() + " into the config, since it was missing");

                    try
                    {
                        field.setAccessible(true);
                        field.set(null, field.get(null));
                    }
                    catch (Exception ignored)
                    {
                        log(Level.INFO, "Unable to set field value of " + field.getName());
                    }
                }
            }
        }
    }

    private boolean isPresent(YamlFile loadedFile, Configurable configurable)
    {
        return configurable.type().getSerializer().existsInFile(loadedFile, configurable);
    }

    private Object loadValue(YamlFile loadedFile, Configurable configurable)
    {
        return configurable.type().getSerializer().getFromFile(loadedFile, configurable);
    }

    public void saveValue(Class<?> configurationClazz, String fieldName)
    {
        if (!configurationClazz.isAnnotationPresent(Configuration.class))
        {
            log(Level.SEVERE, "Unable to load save value in config " + configurationClazz.getSimpleName()
                    + ", the class doesn't have the Configuration annotation");
            return;
        }
        Configuration configuration = configurationClazz.getAnnotation(Configuration.class);

        for (Field field : configurationClazz.getDeclaredFields())
        {
            if (field.isAnnotationPresent(Configurable.class))
            {
                if (field.getName().equalsIgnoreCase(fieldName))
                {
                    if (!Modifier.isStatic(field.getModifiers()))
                    {
                        log(Level.WARNING, "Unable to save config value in " + configurationClazz.getSimpleName()
                                + " - the field " + fieldName + " isn't static!");
                    }
                    else
                    {
                        YamlFile file = getFile(configuration);
                        if (file.exists())
                        {
                            file.load();
                            setValue(file, field, field.getAnnotation(Configurable.class));
                            file.save();
                        } else
                        {
                            //Load it, so all the values are saved
                            load(configurationClazz);
                        }
                    }
                    return;
                }
            }
        }
        throw new RuntimeException("Unable to save config value in " + configurationClazz.getSimpleName()
                + " - the field " + fieldName + " doesn't exist!");
    }

    private void setValue(YamlFile loadedFile, Field field, Configurable configurable)
    {
        try
        {
            configurable.type().getSerializer().setInFile(loadedFile, configurable, field.get(null));
        }
        catch (IllegalAccessException exception)
        {
            log(Level.SEVERE, "Exception while setting value in "
                    + loadedFile.getJavaFile().getName() + " ("
                    + field.getName() + ") " + exception.getMessage());
        }
    }

    private YamlFile getFile(Configuration configuration)
    {
        return new YamlFile(new File(plugin.getDataFolder() + "/" + configuration.path()));
    }

    private void log(Level level, String message)
    {
        plugin.getLogger().log(level, message);
    }

}
