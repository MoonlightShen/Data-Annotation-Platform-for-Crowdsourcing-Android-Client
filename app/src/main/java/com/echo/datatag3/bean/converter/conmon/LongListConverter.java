package com.echo.datatag3.bean.converter.conmon;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.ArrayList;
import java.util.List;

public class LongListConverter implements PropertyConverter<List<Long>, String> {

    @Override
    public List<Long> convertToEntityProperty(String databaseValue) {
        if (databaseValue == null || "".equals(databaseValue)) {
            return new ArrayList<>();
        }
        else {
            return new Gson().fromJson(databaseValue, new TypeToken<List<Long>>() {}.getType());
        }
    }

    @Override
    public String convertToDatabaseValue(List<Long> entityProperty) {
        if(entityProperty==null || entityProperty.isEmpty()){
            return "";
        }
        else{
            return new Gson().toJson(entityProperty);
        }
    }
}
