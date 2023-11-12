package by.clevertec.service;

import by.clevertec.exception.CreateObjectException;
import by.clevertec.exception.NotFoundException;

import java.lang.reflect.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FromJsonService{
    public Object toObject(String json, Class<?> clazz) {

        Map<String, String> jsonMap = executeMapOfFieldsNameAndValue(json);
        Object object = getNewInstanceOfConstructor(clazz);

        return jsonMap.entrySet()
                .stream()
                .map(entry -> {
                    try {
                        Field declaredField = clazz.getDeclaredField(entry.getKey().replace("\"", ""));
                        declaredField.setAccessible(true);
                        Object parsedObject = getParsedObject(entry.getValue(), declaredField);
                        declaredField.set(object, parsedObject);
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        throw new NotFoundException("Поле не существует!");
                    }
                    return object;
                })
                .reduce((o, o2) -> o2)
                .orElseThrow(() -> new CreateObjectException("Произошла ошибка формирования объекта!"));
    }

    private Map<String, String> executeMapOfFieldsNameAndValue(String inJson) {

        String json = null;
        if (isNumber(inJson.charAt(0))) {
            json = inJson;
        }
        if (inJson.charAt(0) == '{') {
            json = inJson.substring(1, inJson.length() - 1);
        }
        Map<String, String> keyValueFields = new LinkedHashMap<>();
        String key;
        String value = null;
        while (!json.isEmpty()) {
            key = getKey(json);
            json = json.substring(key.length() + 1);
            if (isNull(json)) {
                value = "null";
            } else if (isBoolean(json.charAt(0))) {
                value = getBoolean(json);
            } else if (isObject(json.charAt(0))) {
                value = getObject(json);
            } else if (isNumber(json.charAt(0))) {
                value = getNumber(json);
            } else if (isString(json.charAt(0))) {
                value = getString(json);
            } else if (isArray(json.charAt(0))) {
                value = getArray(json);
            }
            int valueLength = value.length() + 1;
            if (json.length() <= valueLength) {
                json = "";
            } else {
                json = json.substring(value.length() + 1);
            }
            keyValueFields.put(createNameField(key), createValueField(value));
        }
        return keyValueFields;
    }

    private String createNameField(String line) {
        return line.replace("\"", "");
    }

    private String createValueField(String line) {

        if (line.startsWith("\"") && line.endsWith("\"") && !line.contains("{") && !line.contains("[")) {
            return line.replace("\"", "");
        } else {
            return line;
        }
    }

    private String getKey(String json) {

        StringBuilder builder = new StringBuilder();
        int counter = 0;
        for (char c : json.toCharArray()) {
            if (counter == 2) {
                break;
            }
            builder.append(c);
            if (c == '"') counter++;
        }
        return builder.toString();
    }

    private boolean isNull(String json) {
        return json.contains("null");
    }

    private String getBoolean(String json) {

        if (json.startsWith("true")) {
            return "true";
        } else if (json.startsWith("false")) {
            return "false";
        } else {
            throw new CreateObjectException("Должен быть true или false!");
        }
    }

    private String getNumber(String json) {

        if (json == null) throw new CreateObjectException("Не должен быть null!");
        int i = 0;
        while (i < json.length() && (Character.isDigit(json.charAt(i)) || json.charAt(i) == '.')) {
            i++;
        }
        return json.substring(0, i);
    }

    private String getObject(String json) {

        if ("null".equals(json)) return "null";
        if (json == null || !json.startsWith("{")) throw new CreateObjectException("Ваши данные не верны: " + json);
        final StringBuilder builder = new StringBuilder();
        int counter = 0;
        for (char c : json.toCharArray()) {
            if (c == '{') counter++;
            if (c == '}') counter--;
            builder.append(c);
            if (counter == 0) break;
        }
        return builder.toString();
    }

    private String getString(String json) {

        String result;
        try {
            result = getStringValue(json);
        } catch (CreateObjectException e) {
            result = getCharacterValue(json);
        }
        return result;
    }

    public String getCharacterValue(String json) {

        Pattern charPattern = Pattern.compile("([0-9]{1,5}|\"\\\\[uU][0-9a-fA-F]{4}\"|\"?null\"?|\"\\\\[bfnrt\"]\"|\".\")");
        return charPattern.matcher(json)
                .results()
                .map(MatchResult::group)
                .findFirst()
                .orElseThrow(() -> new CreateObjectException("Ошибка данных: " + json));
    }

    public String getStringValue(String json) {

        if (json == null) throw new CreateObjectException("Не должен быть null!");
        Pattern stringPattern = Pattern.compile("\"([^\"]*(\"{2})?[^\"]*)*\"");
        return stringPattern.matcher(json)
                .results()
                .map(MatchResult::group)
                .findFirst()
                .orElseThrow(() -> new CreateObjectException("Ошибка данных: " + json));
    }

    private static String getArray(String json) {
        int start = -1;
        int brackets = 0;
        for (int i = 0; i < json.length(); i++) {
            char c = json.charAt(i);
            if (c == '[') {
                if (brackets == 0) {
                    start = i;
                }
                brackets++;
            } else if (c == ']') {
                brackets--;
                if (brackets == 0) {
                    return json.substring(start, i + 1);
                }
            }
        }
        throw new CreateObjectException("Ошибка данных: " + json);
    }

    private boolean isBoolean(char ch) {
        return ch == 't' || ch == 'f';
    }

    private boolean isNumber(char ch) {
        return ch >= '0' && ch <= '9' || ch == '-';
    }

    private boolean isString(char ch) {
        return ch == '"';
    }

    private boolean isArray(char ch) {
        return ch == '[';
    }

    private boolean isObject(char ch) {
        return ch == '{';
    }

    private Object getNewInstanceOfConstructor(Class<?> clazz) {

        Object object;
        try {
            Constructor<?> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            object = constructor.newInstance();
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                 IllegalAccessException e) {
            throw new CreateObjectException("Ошибка при инициализации объекта!");
        }
        return object;
    }

    private Object getParsedObject(String value, Field declaredField) {

        Class<?> type = declaredField.getType();
        if (type.getSimpleName().startsWith("boolean") || type.getName().startsWith("java.lang.Boolean")) {
            return Boolean.valueOf(value);
        } else if (type.isPrimitive() || (type.getSuperclass() != null
                && type.getSuperclass().getName().endsWith("java.lang.Number"))) {
            return getParsedNumber(value, type);
        } else if (type.getName().startsWith("java.lang.String")) {
            return value;
        } else if (Arrays.toString(type.getInterfaces()).startsWith("[interface java.util.Collection]")) {
            return getParsedCollection(value, declaredField);
        } else if (type.getName().endsWith("java.util.Map")) {
            return getParsedMap(value, declaredField);
        } else if (type.isEnum()) {
            return getParsedEnum(value, declaredField);
        } else if (type.isArray()) {
            Class<?> arrayType = declaredField.getType().getComponentType();
            String[] jsonArray = value.substring(1, value.length() - 1).split(",(?=\\{)");
            Object array = Array.newInstance(arrayType, jsonArray.length);
            for (int i = 0; i < jsonArray.length; i++) {
                Array.set(array, i, toObject(jsonArray[i], arrayType));
            }
            return array;
        } else if (type.equals(UUID.class)) {
            return getParsedUUID(value);
        } else if (type.equals(LocalDate.class)) {
            return getParsedLocalDate(value);
        } else if (type.equals(OffsetDateTime.class) || type.getName().startsWith("java.time.OffsetDateTime")) {
            return getParsedOffsetDateTime(value);
        } else {
            return toObject(value, type);
        }
    }

    private Object getParsedNumber(String value, Class<?> type) {
        return switch (type.getSimpleName()) {
            case "Integer", "int" -> Integer.parseInt(value);
            case "Long", "long" -> Long.parseLong(value);
            case "BigDecimal" -> new BigDecimal(value);
            case "BigInteger" -> new BigInteger(value);
            case "Byte", "byte" -> Byte.parseByte(value);
            case "Short", "short" -> Short.parseShort(value);
            case "Float", "float" -> Float.parseFloat(value);
            default -> Double.parseDouble(value);
        };
    }

    private UUID getParsedUUID(String value) {
        return UUID.fromString(value);
    }

    private LocalDate getParsedLocalDate(String value) {
        return LocalDate.parse(value);
    }

    private OffsetDateTime getParsedOffsetDateTime(String value) {
        return OffsetDateTime.parse(value);
    }

    private LinkedHashMap<Object, String> getParsedMap(String value, Field declaredField) {
        if (value.matches(".*\\{.*\\{.*\\}.*\\}.*")) {
            return getParsedNestedMap(value, declaredField);
        } else {
            return getParsedSimpleMap(value, declaredField);
        }
    }

    private LinkedHashMap<Object, String> getParsedSimpleMap(String value, Field declaredField) {
        return value.lines()
                .map(s -> s.replace("{", ""))
                .map(s -> s.replace("}", ""))
                .map(s -> s.split(","))
                .flatMap(Arrays::stream)
                .map(s -> s.replace("\"", ""))
                .map(s -> s.split(":"))
                .collect(Collectors.toMap(
                        strings -> getParsedGeneric(declaredField, strings[0]),
                        strings -> strings[1],
                        (v1, v2) -> v2,
                        LinkedHashMap::new
                ));
    }

    private LinkedHashMap<Object, String> getParsedNestedMap(String value, Field declaredField) {
        return value.lines()
                .map(s -> s.substring(1, s.length() - 1))
                .map(s -> s.split(":", 2))
                .collect(Collectors.toMap(
                        strings -> getParsedGeneric(declaredField, strings[0].replace("\"", "")),
                        strings -> strings[1],
                        (v1, v2) -> v2,
                        LinkedHashMap::new
                ));
    }

    private Collection<Object> getParsedCollection(String value, Field declaredField) {
        Class<?> type = declaredField.getType();
        if (type.getName().startsWith("java.util.List")) {
            return getParsedCollectionStream(value, declaredField)
                    .toList();
        } else if (type.getName().startsWith("java.util.Set")) {
            return getParsedCollectionStream(value, declaredField)
                    .collect(Collectors.toSet());
        }
        return Collections.emptyList();
    }

    private Stream<Object> getParsedCollectionStream(String value, Field declaredField) {
        return value.lines()
                .map(s -> s.replace("[", ""))
                .map(s -> s.replace("]", ""))
                .map(s -> {
                    if (s.startsWith("{")) {
                        return s.split("(?<=\\}),(?=\\{)");
                    } else {
                        return s.split(",");
                    }
                })
                .flatMap(Arrays::stream)
                .map(s -> getParsedGeneric(declaredField, s));
    }

    private Object getParsedGeneric(Field declaredField, String s) {

        if (declaredField.getGenericType() instanceof ParameterizedType type) {
            Class<?> generic = (Class<?>) type.getActualTypeArguments()[0];
            if (Number.class.isAssignableFrom(generic)) {
                return getParsedNumber(s, generic);
            } else if (!generic.getName().startsWith("java")) {
                return toObject(s, generic);
            }
        }
        return s;
    }

    private Enum<?> getParsedEnum(String value, Field declaredField) {

        Class<?> enumClass = declaredField.getType();
        if (enumClass.isEnum()) {
            Object[] enumValues = enumClass.getEnumConstants();
            for (Object enumValue : enumValues) {
                if (((Enum<?>) enumValue).name().equals(value)) {
                    return (Enum<?>) enumValue;
                }
            }
        }
        return null;
    }
}