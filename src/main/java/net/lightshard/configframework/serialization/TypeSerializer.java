package net.lightshard.configframework.serialization;

import net.lightshard.configframework.Configurable;
import net.lightshard.configframework.Configuration;
import net.lightshard.configframework.YamlFile;

public interface TypeSerializer<T>
{

    boolean existsInFile(YamlFile loadedFile, Configurable configurable);

    T getFromFile(YamlFile loadedFile, Configurable configurable);

    void setInFile(YamlFile loadedFile, Configurable configurable, T object);

}
