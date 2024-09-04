package com.echo.datatag3.bean.converter;


import com.echo.datatag3.bean.enumeration.ChatType;

import org.greenrobot.greendao.converter.PropertyConverter;

public class ChatTypeConverter implements PropertyConverter<ChatType, Integer> {

    @Override
    public ChatType convertToEntityProperty(Integer databaseValue) {
        return ChatType.getByIndex(databaseValue);
    }

    @Override
    public Integer convertToDatabaseValue(ChatType entityProperty) {
        return entityProperty.getIndex();
    }
}
