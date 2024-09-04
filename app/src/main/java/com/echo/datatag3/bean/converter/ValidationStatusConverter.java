package com.echo.datatag3.bean.converter;

import com.echo.datatag3.bean.enumeration.ValidationStatus;

import org.greenrobot.greendao.converter.PropertyConverter;

public class ValidationStatusConverter implements PropertyConverter<ValidationStatus, Integer> {

    @Override
    public ValidationStatus convertToEntityProperty(Integer databaseValue) {
        return ValidationStatus.getByIndex(databaseValue);
    }

    @Override
    public Integer convertToDatabaseValue(ValidationStatus entityProperty) {
        return entityProperty.getIndex();
    }
}
