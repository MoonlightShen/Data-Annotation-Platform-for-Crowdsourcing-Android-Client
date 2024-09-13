package com.echo.dapc.bean.converter;

import com.echo.dapc.bean.enumeration.MessageContentType;

import org.greenrobot.greendao.converter.PropertyConverter;

public class ContentTypeConverter implements PropertyConverter<MessageContentType, Integer> {

    @Override
    public MessageContentType convertToEntityProperty(Integer databaseValue) {
        return MessageContentType.getByIndex(databaseValue);
    }

    @Override
    public Integer convertToDatabaseValue(MessageContentType entityProperty) {
        return entityProperty.getIndex();
    }
}
