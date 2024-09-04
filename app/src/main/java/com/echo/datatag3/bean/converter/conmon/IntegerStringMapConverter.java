package com.echo.datatag3.bean.converter.conmon;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.HashMap;
import java.util.Map;

public class IntegerStringMapConverter implements PropertyConverter<Map<Integer, String>, String> {

    @Override
    public Map<Integer, String> convertToEntityProperty(String databaseValue) {
        if (databaseValue == null || "".equals(databaseValue)) {
            return new HashMap<>();
        } else {
            return new Gson().fromJson(databaseValue, new TypeToken<Map<Integer, String>>() {
            }.getType());
        }
    }

    @Override
    public String convertToDatabaseValue(Map<Integer, String> entityProperty) {
        if (entityProperty == null || entityProperty.isEmpty()) {
            return "";
        } else {
            return new Gson().toJson(entityProperty);
        }
    }

}
