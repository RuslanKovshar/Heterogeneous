package com.kovshar.ranking.utils;

import lombok.experimental.UtilityClass;
import org.json.JSONObject;

@UtilityClass
public class FieldsUtils {

    public static Object getDataFromField(JSONObject jsonObject, String fieldName) {
        String[] names = fieldName.split("\\.");
        Object data = null;
        for (int i = 0; i < names.length; i++) {
            String name = names[i];
            if (i == names.length - 1) {
                if (jsonObject.has(name)) {
                    data = jsonObject.get(name);
                    if (data instanceof JSONObject) {
                        data = null;
                    }
                }
            } else {
                if (jsonObject.has(name)) {
                    Object value = jsonObject.get(name);
                    if (value instanceof JSONObject) {
                        jsonObject = (JSONObject) value;
                    } else {
                        break;
                    }
                }
            }
        }
        return data;
    }
}
