package com.echo.datatag3.bean.entity;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.echo.datatag3.BR;
import com.echo.datatag3.bean.converter.conmon.LongListConverter;
import com.echo.datatag3.bean.converter.conmon.StringListConverter;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;

import java.util.List;

@Entity(nameInDb = "dt_local_task")
public class LocalTask extends BaseObservable {
    @Id(autoincrement = true)
    @Unique
    private Long taskIndex;

    private String title;
    private String description;
    private Long startDelay;
    private Long duration;
    @Convert(converter = StringListConverter.class, columnType = String.class)
    private List<String> tags;

    @Convert(converter = LongListConverter.class, columnType = String.class)
    private List<Long> localDataFiles;
    @Convert(converter = LongListConverter.class, columnType = String.class)
    private List<Long> uploadedDataFiles;

    private Integer groupSize;
    private Integer visibleLevel;

    private Integer totalQuota;
    private Integer unitPoint;
    private Integer taggingSceneType;
    private String taggingSceneSettings;

    private Long lastEditTime;

    @Convert(converter = LongListConverter.class, columnType = String.class)
    private List<Long> inviteUsers;




    @Generated(hash = 1591667643)
    public LocalTask(Long taskIndex, String title, String description,
            Long startDelay, Long duration, List<String> tags,
            List<Long> localDataFiles, List<Long> uploadedDataFiles,
            Integer groupSize, Integer visibleLevel, Integer totalQuota,
            Integer unitPoint, Integer taggingSceneType,
            String taggingSceneSettings, Long lastEditTime,
            List<Long> inviteUsers) {
        this.taskIndex = taskIndex;
        this.title = title;
        this.description = description;
        this.startDelay = startDelay;
        this.duration = duration;
        this.tags = tags;
        this.localDataFiles = localDataFiles;
        this.uploadedDataFiles = uploadedDataFiles;
        this.groupSize = groupSize;
        this.visibleLevel = visibleLevel;
        this.totalQuota = totalQuota;
        this.unitPoint = unitPoint;
        this.taggingSceneType = taggingSceneType;
        this.taggingSceneSettings = taggingSceneSettings;
        this.lastEditTime = lastEditTime;
        this.inviteUsers = inviteUsers;
    }

    @Generated(hash = 1949502145)
    public LocalTask() {
    }




    public Long getTaskIndex() {
        return this.taskIndex;
    }

    public void setTaskIndex(Long taskIndex) {
        this.taskIndex = taskIndex;
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }

    @Bindable
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        notifyPropertyChanged(BR.description);
    }

    @Bindable
    public Long getStartDelay() {
        return startDelay;
    }

    public void setStartDelay(Long startDelay) {
        this.startDelay = startDelay;
        notifyPropertyChanged(BR.startDelay);
    }

    @Bindable
    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
        notifyPropertyChanged(BR.duration);
    }

    @Bindable
    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
        notifyPropertyChanged(BR.tags);
    }

    @Bindable
    public List<Long> getLocalDataFiles() {
        return localDataFiles;
    }

    public void setLocalDataFiles(List<Long> localDataFiles) {
        this.localDataFiles = localDataFiles;
        notifyPropertyChanged(BR.localDataFiles);
    }

    @Bindable
    public List<Long> getUploadedDataFiles() {
        return uploadedDataFiles;
    }

    public void setUploadedDataFiles(List<Long> uploadedDataFiles) {
        this.uploadedDataFiles = uploadedDataFiles;
        notifyPropertyChanged(BR.uploadedDataFiles);
    }

    @Bindable
    public Integer getGroupSize() {
        return groupSize;
    }

    public void setGroupSize(Integer groupSize) {
        this.groupSize = groupSize;
        notifyPropertyChanged(BR.groupSize);
    }

    @Bindable
    public Integer getVisibleLevel() {
        return visibleLevel;
    }

    public void setVisibleLevel(Integer visibleLevel) {
        this.visibleLevel = visibleLevel;
        notifyPropertyChanged(BR.visibleLevel);
    }

    @Bindable
    public Integer getTotalQuota() {
        return totalQuota;
    }

    public void setTotalQuota(Integer totalQuota) {
        this.totalQuota = totalQuota;
        notifyPropertyChanged(BR.totalQuota);
    }

    @Bindable
    public Integer getUnitPoint() {
        return unitPoint;
    }

    public void setUnitPoint(Integer unitPoint) {
        this.unitPoint = unitPoint;
        notifyPropertyChanged(BR.unitPoint);
    }

    @Bindable
    public Integer getTaggingSceneType() {
        return taggingSceneType;
    }

    public void setTaggingSceneType(Integer taggingSceneType) {
        this.taggingSceneType = taggingSceneType;
        notifyPropertyChanged(BR.taggingSceneType);
    }

    @Bindable
    public String getTaggingSceneSettings() {
        return taggingSceneSettings;
    }

    public void setTaggingSceneSettings(String taggingSceneSettings) {
        this.taggingSceneSettings = taggingSceneSettings;
        notifyPropertyChanged(BR.taggingSceneSettings);
    }

    @Bindable
    public Long getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(Long lastEditTime) {
        this.lastEditTime = lastEditTime;
        notifyPropertyChanged(BR.lastEditTime);
    }

    @Bindable
    public List<Long> getInviteUsers() {
        return inviteUsers;
    }

    public void setInviteUsers(List<Long> inviteUsers) {
        this.inviteUsers = inviteUsers;
        notifyPropertyChanged(BR.inviteUsers);
    }
}
