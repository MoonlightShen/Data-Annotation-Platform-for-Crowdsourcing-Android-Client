package com.echo.datatag3.bean.entity;

import com.echo.datatag3.bean.converter.ContentTypeConverter;
import com.echo.datatag3.bean.converter.conmon.IntegerListConverter;
import com.echo.datatag3.bean.enumeration.MessageContentType;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;

import java.util.List;

@Entity(nameInDb = "dt_untagging_data")
public class UntaggingData {
    @Id(autoincrement = true)
    @Unique
    private Long dataIndex;
    private Long untaggingDataSetId;
    private Long dataId;
    @Convert(converter = ContentTypeConverter.class, columnType = Integer.class)
    private MessageContentType dataType;
    private String dataContent;
    @Convert(converter = IntegerListConverter.class, columnType = String.class)
    private List<Integer> taggingOption;


    @Generated(hash = 481979416)
    public UntaggingData(Long dataIndex, Long untaggingDataSetId, Long dataId,
                         MessageContentType dataType, String dataContent,
                         List<Integer> taggingOption) {
        this.dataIndex = dataIndex;
        this.untaggingDataSetId = untaggingDataSetId;
        this.dataId = dataId;
        this.dataType = dataType;
        this.dataContent = dataContent;
        this.taggingOption = taggingOption;
    }

    @Generated(hash = 465285596)
    public UntaggingData() {
    }


    public Long getDataId() {
        return this.dataId;
    }

    public void setDataId(Long dataId) {
        this.dataId = dataId;
    }
    public MessageContentType getDataType() {
        return this.dataType;
    }

    public void setDataType(MessageContentType dataType) {
        this.dataType = dataType;
    }

    public String getDataContent() {
        return this.dataContent;
    }

    public void setDataContent(String dataContent) {
        this.dataContent = dataContent;
    }

    public Long getDataIndex() {
        return this.dataIndex;
    }

    public void setDataIndex(Long dataIndex) {
        this.dataIndex = dataIndex;
    }

    public Long getUntaggingDataSetId() {
        return this.untaggingDataSetId;
    }

    public void setUntaggingDataSetId(Long untaggingDataSetId) {
        this.untaggingDataSetId = untaggingDataSetId;
    }

    public List<Integer> getTaggingOption() {
        return this.taggingOption;
    }

    public void setTaggingOption(List<Integer> taggingOption) {
        this.taggingOption = taggingOption;
    }
}
