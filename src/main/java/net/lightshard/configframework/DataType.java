package net.lightshard.configframework;


import net.lightshard.configframework.serialization.Serializers;
import net.lightshard.configframework.serialization.TypeSerializer;

public enum DataType
{
    STRING(Serializers.STRING),
    BOOLEAN(Serializers.BOOLEAN),
    INTEGER(Serializers.INTEGER),
    SHORT(Serializers.SHORT),
    DOUBLE(Serializers.DOUBLE),
    FLOAT(Serializers.FLOAT),
    LONG(Serializers.LONG),
    LIST_STRING(Serializers.LIST_STRING),
    ITEMSTACK(Serializers.ITEMSTACK);

    private TypeSerializer<Object> serializer;

    DataType(TypeSerializer serializer)
    {
        this.serializer = serializer;
    }

    public TypeSerializer<Object> getSerializer()
    {
        return serializer;
    }

    public void setSerializer(TypeSerializer<Object> serializer)
    {
        this.serializer = serializer;
    }
}
