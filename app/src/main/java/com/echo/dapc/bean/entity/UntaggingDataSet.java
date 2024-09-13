package com.echo.dapc.bean.entity;

import com.echo.dapc.bean.converter.conmon.IntegerStringMapConverter;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;

import java.util.Map;

@Entity(nameInDb = "dt_untagging_data_set")
public class UntaggingDataSet {
    @Id(autoincrement = true)
    @Unique
    private Long setIndex;
    private Long setId;
    @Convert(converter = IntegerStringMapConverter.class, columnType = String.class)
    private Map<Integer, String> taggingOptionDict;
    private Integer lastTagging;

    @Generated(hash = 20353402)
    public UntaggingDataSet(Long setIndex, Long setId,
                            Map<Integer, String> taggingOptionDict, Integer lastTagging) {
        this.setIndex = setIndex;
        this.setId = setId;
        this.taggingOptionDict = taggingOptionDict;
        this.lastTagging = lastTagging;
    }

    @Generated(hash = 109567136)
    public UntaggingDataSet() {
    }


    public Long getSetIndex() {
        return this.setIndex;
    }

    public void setSetIndex(Long setIndex) {
        this.setIndex = setIndex;
    }

    public Long getSetId() {
        return this.setId;
    }

    public void setSetId(Long setId) {
        this.setId = setId;
    }

    public Map<Integer, String> getTaggingOptionDict() {
        return this.taggingOptionDict;
    }

    public void setTaggingOptionDict(Map<Integer, String> taggingOptionDict) {
        this.taggingOptionDict = taggingOptionDict;
    }

    public Integer getLastTagging() {
        return this.lastTagging;
    }

    public void setLastTagging(Integer lastTagging) {
        this.lastTagging = lastTagging;
    }
}
