package by.clevertec.serializable;

import by.clevertec.service.FromJsonService;
import by.clevertec.service.ToJsonService;

public class JsonSerializableImpl implements JsonSerializable{

    private final FromJsonService fromJsonService;
    private final ToJsonService toJsonService;

    public JsonSerializableImpl(FromJsonService fromJsonService, ToJsonService toJsonService) {
        this.fromJsonService = fromJsonService;
        this.toJsonService = toJsonService;
    }

    @Override
    public Object fromJson(String json, Class<?> clazz) {
        return fromJsonService.toObject(json, clazz);
    }

    @Override
    public String toJson(Object object) {
        return toJsonService.toJson(object);
    }
}
