package com.echo.dapc.bean.converter.conmon;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.ArrayList;
import java.util.List;

public class IntegerListConverter implements PropertyConverter<List<Integer>, String> {

    @Override
    public List<Integer> convertToEntityProperty(String databaseValue) {
        if (databaseValue == null || "".equals(databaseValue)) {
            return new ArrayList<>();
        }
        else {
            return new Gson().fromJson(databaseValue, new TypeToken<List<Integer>>() {}.getType());
        }
    }

    @Override
    public String convertToDatabaseValue(List<Integer> entityProperty) {
        if(entityProperty==null || entityProperty.isEmpty()){
            return "";
        }
        else{
            return new Gson().toJson(entityProperty);
        }
    }
}
