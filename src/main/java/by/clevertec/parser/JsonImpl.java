package by.clevertec.parser;

import by.clevertec.service.FromJsonService;
import by.clevertec.service.ToJsonService;

public class JsonImpl implements Json{

    private final ToJsonService toJsonService;
    private final FromJsonService fromJsonService;

    public JsonImpl(ToJsonService toJsonService, FromJsonService fromJsonService) {
        this.toJsonService = toJsonService;
        this.fromJsonService = fromJsonService;
    }


    @Override
    public String toJson(Object object) {
        return toJsonService.toJson(object);
    }

    @Override
    public Object toObject(String json, Class<?> clazz) {
        return fromJsonService.toObject(json, clazz);
    }
}
