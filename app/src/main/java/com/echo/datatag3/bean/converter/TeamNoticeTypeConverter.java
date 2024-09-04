package com.echo.datatag3.bean.converter;

import com.echo.datatag3.bean.enumeration.TeamNoticeType;

import org.greenrobot.greendao.converter.PropertyConverter;

public class TeamNoticeTypeConverter implements PropertyConverter<TeamNoticeType, Integer> {

    @Override
    public TeamNoticeType convertToEntityProperty(Integer databaseValue) {
        return TeamNoticeType.getByIndex(databaseValue);
    }

    @Override
    public Integer convertToDatabaseValue(TeamNoticeType entityProperty) {
        return entityProperty.getIndex();
    }
}
