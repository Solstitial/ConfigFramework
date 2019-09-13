package net.lightshard.configframework.serialization;

import net.lightshard.configframework.Configurable;
import net.lightshard.configframework.YamlFile;
import net.lightshard.configframework.util.StringUtil;

import java.util.List;

public class Serializers
{
    public static final TypeSerializer<String> STRING = new TypeSerializer<String>()
    {
        public boolean existsInFile(YamlFile loadedFile, Configurable configurable)
        {
            return loadedFile.getString(configurable.path()) != null;
        }

        public String getFromFile(YamlFile loadedFile, Configurable configurable)
        {
            String raw = loadedFile.getString(configurable.path());
            return StringUtil.colorize(raw, configurable.colorCharLocale());
        }

        public void setInFile(YamlFile loadedFile, Configurable configurable, String object)
        {
            String strToWrite = StringUtil.reverseColor(object, configurable.colorCharLocale());
            loadedFile.set(configurable.path(), strToWrite);
        }
    };
    public static final TypeSerializer<Boolean> BOOLEAN = new TypeSerializer<Boolean>()
    {
        public boolean existsInFile(YamlFile loadedFile, Configurable configurable)
        {
            String path = configurable.path();
            return loadedFile.getBoolean(path) != false || loadedFile.getString(path) != null;
        }

        public Boolean getFromFile(YamlFile loadedFile, Configurable configurable)
        {
            return loadedFile.getBoolean(configurable.path());
        }

        public void setInFile(YamlFile loadedFile, Configurable configurable, Boolean object)
        {
            loadedFile.set(configurable.path(), object);
        }
    };
    public static final TypeSerializer<Integer> INTEGER = new TypeSerializer<Integer>()
    {
        public boolean existsInFile(YamlFile loadedFile, Configurable configurable)
        {
            String path = configurable.path();
            return loadedFile.getInt(path) != 0 || loadedFile.getString(path) != null;
        }

        public Integer getFromFile(YamlFile loadedFile, Configurable configurable)
        {
            return loadedFile.getInt(configurable.path());
        }

        public void setInFile(YamlFile loadedFile, Configurable configurable, Integer object)
        {
            loadedFile.set(configurable.path(), object);
        }
    };
    public static final TypeSerializer<Short> SHORT = new TypeSerializer<Short>()
    {
        public boolean existsInFile(YamlFile loadedFile, Configurable configurable)
        {
            String path = configurable.path();
            return loadedFile.getShort(path) != 0 || loadedFile.getString(path) != null;
        }

        public Short getFromFile(YamlFile loadedFile, Configurable configurable)
        {
            return loadedFile.getShort(configurable.path());
        }

        public void setInFile(YamlFile loadedFile, Configurable configurable, Short object)
        {
            loadedFile.set(configurable.path(), object);
        }
    };
    public static final TypeSerializer<Double> DOUBLE = new TypeSerializer<Double>()
    {
        public boolean existsInFile(YamlFile loadedFile, Configurable configurable)
        {
            String path = configurable.path();
            return loadedFile.getDouble(path) != 0.0D || loadedFile.getString(path) != null;
        }

        public Double getFromFile(YamlFile loadedFile, Configurable configurable)
        {
            return loadedFile.getDouble(configurable.path());
        }

        public void setInFile(YamlFile loadedFile, Configurable configurable, Double object)
        {
            loadedFile.set(configurable.path(), object);
        }
    };
    public static final TypeSerializer<Float> FLOAT = new TypeSerializer<Float>()
    {
        public boolean existsInFile(YamlFile loadedFile, Configurable configurable)
        {
            String path = configurable.path();
            return loadedFile.getFloat(path) != 0.0F || loadedFile.getString(path) != null;
        }

        public Float getFromFile(YamlFile loadedFile, Configurable configurable)
        {
            return loadedFile.getFloat(configurable.path());
        }

        public void setInFile(YamlFile loadedFile, Configurable configurable, Float object)
        {
            loadedFile.set(configurable.path(), object);
        }
    };
    public static final TypeSerializer<Long> LONG = new TypeSerializer<Long>()
    {
        public boolean existsInFile(YamlFile loadedFile, Configurable configurable)
        {
            String path = configurable.path();
            return loadedFile.getLong(path) != 0L || loadedFile.getString(path) != null;
        }

        public Long getFromFile(YamlFile loadedFile, Configurable configurable)
        {
            return loadedFile.getLong(configurable.path());
        }

        public void setInFile(YamlFile loadedFile, Configurable configurable, Long object)
        {
            loadedFile.set(configurable.path(), object);
        }
    };
    public static final TypeSerializer<List<String>> LIST_STRING = new TypeSerializer<List<String>>()
    {
        public boolean existsInFile(YamlFile loadedFile, Configurable configurable)
        {
            String path = configurable.path();
            if(!loadedFile.getStringList(path).isEmpty())
            {
                return true;
            }
            return loadedFile.keyExists(path);
        }

        public List<String> getFromFile(YamlFile loadedFile, Configurable configurable)
        {
            List<String> raw = loadedFile.getStringList(configurable.path());
            return StringUtil.colorize(raw, configurable.colorCharLocale());
        }

        public void setInFile(YamlFile loadedFile, Configurable configurable, List<String> object)
        {
            List<String> raw = StringUtil.reverseColor(object, configurable.colorCharLocale());
            loadedFile.set(configurable.path(), raw);
        }
    };
    public static final ItemStackSerializer ITEMSTACK = new ItemStackSerializer();

    private Serializers() {}

}
