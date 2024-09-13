package com.echo.dapc.bean.converter.conmon;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.ArrayList;
import java.util.List;

public class StringListConverter implements PropertyConverter<List<String>, String> {

    @Override
    public List<String> convertToEntityProperty(String databaseValue) {
        if (databaseValue == null || "".equals(databaseValue)) {
            return new ArrayList<>();
        }
        else {
            return new Gson().fromJson(databaseValue, new TypeToken<List<String>>() {}.getType());
        }
    }

    @Override
    public String convertToDatabaseValue(List<String> entityProperty) {
        if(entityProperty==null){
            return "";
        }
        else{
            return new Gson().toJson(entityProperty);
        }
    }
}
