package com.echo.dapc.mvvm.model.task;

import androidx.databinding.Bindable;

import com.echo.dapc.BR;
import com.echo.dapc.adapter.FlexboxLayoutAdapter;
import com.echo.dapc.adapter.SimpleTagListAdapter;
import com.echo.dapc.base.BaseModel;
import com.echo.dapc.bean.enumeration.TaskState;
import com.echo.dapc.bean.enumeration.TaskTaggingSceneType;
import com.echo.dapc.bean.enumeration.TaskVisibleLevel;
import com.echo.dapc.bean.logic.taggingscene.LabelClassification;

public class TaskCommonInfoModel extends BaseModel {
    private Long taskId;
    private Long submissionTime;
    private TaskState state;

    private String title;
    private String description;
    //TODO 开始时间
    private Long endTime;
    private SimpleTagListAdapter tagsAdapter=new SimpleTagListAdapter();
    private Integer totalData;
    private Integer groupSize;
    private TaskVisibleLevel visibleLevel;
    private Integer unitPoint;
    private TaskTaggingSceneType taggingSceneType;
    private LabelClassification labelClassification;
    private FlexboxLayoutAdapter taggingOptionsAdapter=new FlexboxLayoutAdapter();

    private String abnormalContent;

    @Bindable
    public String getAbnormalContent() {
        return abnormalContent;
    }

    public void setAbnormalContent(String abnormalContent) {
        this.abnormalContent = abnormalContent;
        notifyPropertyChanged(BR.abnormalContent);
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    @Bindable
    public FlexboxLayoutAdapter getTaggingOptionsAdapter() {
        return taggingOptionsAdapter;
    }

    public void setTaggingOptionsAdapter(FlexboxLayoutAdapter taggingOptionsAdapter) {
        this.taggingOptionsAdapter = taggingOptionsAdapter;
        notifyPropertyChanged(BR.taggingOptionsAdapter);
    }

    @Bindable
    public LabelClassification getLabelClassification() {
        return labelClassification;
    }

    public void setLabelClassification(LabelClassification labelClassification) {
        this.labelClassification = labelClassification;
        notifyPropertyChanged(BR.labelClassification);
    }

    @Bindable
    public TaskTaggingSceneType getTaggingSceneType() {
        return taggingSceneType;
    }

    public void setTaggingSceneType(TaskTaggingSceneType taggingSceneType) {
        this.taggingSceneType = taggingSceneType;
        notifyPropertyChanged(BR.taggingSceneType);
    }

    @Bindable
    public Long getSubmissionTime() {
        return submissionTime;
    }

    public void setSubmissionTime(Long submissionTime) {
        this.submissionTime = submissionTime;
        notifyPropertyChanged(BR.submissionTime);
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
    public TaskState getState() {
        return state;
    }

    public void setState(TaskState state) {
        this.state = state;
        notifyPropertyChanged(BR.state);
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
    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
        notifyPropertyChanged(BR.endTime);
    }

    @Bindable
    public SimpleTagListAdapter getTagsAdapter() {
        return tagsAdapter;
    }

    public void setTagsAdapter(SimpleTagListAdapter tagsAdapter) {
        this.tagsAdapter = tagsAdapter;
        notifyPropertyChanged(BR.tagsAdapter);
    }

    @Bindable
    public Integer getTotalData() {
        return totalData;
    }

    public void setTotalData(Integer totalData) {
        this.totalData = totalData;
        notifyPropertyChanged(BR.totalData);
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
    public TaskVisibleLevel getVisibleLevel() {
        return visibleLevel;
    }

    public void setVisibleLevel(TaskVisibleLevel visibleLevel) {
        this.visibleLevel = visibleLevel;
        notifyPropertyChanged(BR.visibleLevel);
    }
}
