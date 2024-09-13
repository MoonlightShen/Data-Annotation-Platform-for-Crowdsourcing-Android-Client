package com.echo.dapc.bean.converter;


import com.echo.dapc.bean.enumeration.ChatType;

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
