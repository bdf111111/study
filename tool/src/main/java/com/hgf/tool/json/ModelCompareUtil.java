package com.hgf.tool.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hgf.tool.common.model.constant.StringConstant;

import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;

public class ModelCompareUtil {

    private ModelCompareUtil() {}

    /**
     * 对比模型数据
     * @param newValue 新数据
     * @param oldValue 旧数据
     * @return 返回差别数据
     */
    public static String compareObject(Object newValue, Object oldValue) {
        String newValueJsonString = JsonUtil.toJsonString(newValue);
        String oldValueJsonString = JsonUtil.toJsonString(oldValue);

        JsonObject newJsonObject = JsonParser.parseString(newValueJsonString).getAsJsonObject();
        JsonObject oldJsonObject = JsonParser.parseString(oldValueJsonString).getAsJsonObject();

        boolean same = compareJsonObject(newJsonObject, oldJsonObject);

        return same ? StringConstant.EMPTY : JsonUtil.toJsonString(new ModelCompareDetail(newJsonObject, oldJsonObject));
    }

    /**
     * json对象比较
     * @param newJsonObject 新数据
     * @param oldJsonObject 旧数据
     * @return 返回比较结果，true为全部相同
     */
    private static boolean compareJsonObject(JsonObject newJsonObject, JsonObject oldJsonObject) {
        if (null == oldJsonObject) {
            return false;
        }

        Iterator<String> newJsonObjectKeyIterator = newJsonObject.keySet().iterator();

        while (newJsonObjectKeyIterator.hasNext()) {
            String key = newJsonObjectKeyIterator.next();

            JsonElement newJsonElement = newJsonObject.get(key);
            JsonElement oldJsonElement = oldJsonObject.get(key);

            checkObjectElement(oldJsonObject, newJsonObjectKeyIterator, key, newJsonElement, oldJsonElement);
        }

        return 0 == newJsonObject.size();
    }

    /**
     * 检查对象元素
     * @param oldJsonObject 旧json模型
     * @param newJsonObjectKeyIterator 新数据迭代器
     * @param key 键
     * @param newJsonElement 新json元素
     * @param oldJsonElement 旧json元素
     */
    private static void checkObjectElement(JsonObject oldJsonObject, Iterator<String> newJsonObjectKeyIterator, String key, JsonElement newJsonElement, JsonElement oldJsonElement) {
        if (newJsonElement.isJsonObject()) {
            if (compareJsonObject(newJsonElement.getAsJsonObject(), null == oldJsonElement ? null : oldJsonElement.getAsJsonObject())) {
                removeIterator(oldJsonObject, newJsonObjectKeyIterator, key);
            }
        } else if (newJsonElement.isJsonArray()) {
            if (compareJsonArray(newJsonElement.getAsJsonArray(), null == oldJsonElement ? new JsonArray() : oldJsonElement.getAsJsonArray())) {
                removeIterator(oldJsonObject, newJsonObjectKeyIterator, key);
            }
        } else {
            boolean equals = Objects.nonNull(oldJsonElement) && oldJsonElement.equals(newJsonElement);
            if (equals || (Objects.isNull(oldJsonElement) && StringConstant.EMPTY.equals(newJsonElement.getAsString()))) {
                removeIterator(oldJsonObject, newJsonObjectKeyIterator, key);
            }
        }
    }

    /**
     * json数组比较
     * @param newJsonArray 新数组
     * @param oldJsonArray 旧数组
     * @return 返回比较结果
     */
    private static boolean compareJsonArray(JsonArray newJsonArray, JsonArray oldJsonArray) {
        if (Objects.isNull(oldJsonArray)) {
            return false;
        }

        if (0 == newJsonArray.size() && 0 == oldJsonArray.size()) {
            return true;
        }

        if (newJsonArray.size() != oldJsonArray.size()) {
            return false;
        }

        return JsonUtil.toJsonString(newJsonArray).equals(JsonUtil.toJsonString(oldJsonArray));
    }

    /**
     * 删除迭代器元素
     * @param oldJsonObject 旧数据模型
     * @param newJsonObjectKeyIterator 新数据模型迭代器
     * @param key 键值
     */
    private static void removeIterator(JsonObject oldJsonObject, Iterator<String> newJsonObjectKeyIterator, String key) {
        newJsonObjectKeyIterator.remove();
        oldJsonObject.remove(key);
    }

    /**
     * 对比模型的ToString是否不相等
     * @param newValue 新数据
     * @param oldValue 旧数据
     * @return 是否不相等
     */
    public static boolean compareStrUnequals(Object newValue, Object oldValue) {
        return !Optional.ofNullable(newValue).map(Object::toString).orElse(StringConstant.EMPTY)
                .equals(Optional.ofNullable(oldValue).map(Object::toString).orElse(StringConstant.EMPTY));
    }

    /**
     * 模型对比详情
     */
    private static class ModelCompareDetail {

        private final JsonObject newValue;
        private final JsonObject oldValue;

        public ModelCompareDetail(JsonObject newValue, JsonObject oldValue) {
            this.newValue = newValue;
            this.oldValue = oldValue;
        }

        public JsonObject getNewValue() {
            return newValue;
        }

        public JsonObject getOldValue() {
            return oldValue;
        }
    }

}
