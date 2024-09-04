package com.echo.datatag3.bean.converter;

import com.echo.datatag3.bean.enumeration.Gender;

import org.greenrobot.greendao.converter.PropertyConverter;

public class GenderConverter implements PropertyConverter<Gender, Integer> {

    @Override
    public Gender convertToEntityProperty(Integer databaseValue) {
        return databaseValue==null?Gender.UNKNOWN:Gender.getByIndex(databaseValue);
    }

    @Override
    public Integer convertToDatabaseValue(Gender entityProperty) {
        return entityProperty.getIndex();
    }
}