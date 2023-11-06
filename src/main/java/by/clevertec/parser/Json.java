package by.clevertec.parser;

public interface Json {

    String toJson(Object object);
    Object toObject(String json, Class<?> clazz);
}
