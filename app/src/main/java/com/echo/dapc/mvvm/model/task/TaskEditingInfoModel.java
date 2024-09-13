package com.echo.dapc.mvvm.model.task;

import android.content.Intent;

import androidx.databinding.Bindable;

import com.echo.dapc.BR;
import com.echo.dapc.adapter.FlexboxLayoutAdapter;
import com.echo.dapc.adapter.SimpleTagListAdapter;
import com.echo.dapc.base.BaseModel;
import com.echo.dapc.bean.entity.LocalTask;
import com.echo.dapc.bean.logic.DataFile;
import com.echo.dapc.bean.logic.taggingscene.LabelClassification;
import com.echo.dapc.mvvm.view.task.TaskEditingInfoActivity;
import com.echo.dapc.util.GlobalConstant;
import com.echo.dapc.widget.bottomdialog.ImportFilesDialog;

import java.util.ArrayList;
import java.util.List;

public class TaskEditingInfoModel extends BaseModel {
    private LocalTask task = new LocalTask();
    private SimpleTagListAdapter tagsAdapter;
    private FlexboxLayoutAdapter tagOptionsAdapter;
    private boolean groupSizeExpand;
    private boolean taskQuotaExpand;
    private boolean startDelayExpand;

    private LabelClassification labelClassification = new LabelClassification();

    private boolean hasEdited;

    private List<DataFile> dataFiles = new ArrayList<>();

    private String groupSize;
    private String unitPoint;
    private int totalData;

    public ImportFilesDialog dialog;

    public ImportFilesDialog getDialog() {
        return dialog;
    }

    public void setDialog(ImportFilesDialog dialog) {
        this.dialog = dialog;
    }

    @Bindable
    public LocalTask getTask() {
        return task;
    }

    public void setTask(LocalTask task) {
        this.task = task;
        notifyPropertyChanged(BR.task);
    }

    @Bindable
    public String getUnitPoint() {
        return unitPoint;
    }

    public void setUnitPoint(String unitPoint) {
        this.unitPoint = unitPoint;
        notifyPropertyChanged(BR.unitPoint);
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
    public boolean isGroupSizeExpand() {
        return groupSizeExpand;
    }

    public void expandGroupSize() {
        this.groupSizeExpand = !groupSizeExpand;
        notifyPropertyChanged(BR.groupSizeExpand);
    }

    @Bindable
    public String getGroupSize() {
        return groupSize;
    }

    public void setGroupSize(String groupSize) {
        this.groupSize = groupSize;
        notifyPropertyChanged(BR.groupSize);
    }

    @Bindable
    public boolean isTaskQuotaExpand() {
        return taskQuotaExpand;
    }

    public void expandTaskQuota() {
        this.taskQuotaExpand = !taskQuotaExpand;
        notifyPropertyChanged(BR.taskQuotaExpand);
    }

    @Bindable
    public boolean isStartDelayExpand() {
        return startDelayExpand;
    }

    public void expandStartDelay() {
        this.startDelayExpand = !startDelayExpand;
        notifyPropertyChanged(BR.startDelayExpand);
    }

    @Bindable
    public FlexboxLayoutAdapter getTagOptionsAdapter() {
        return tagOptionsAdapter;
    }

    public void setTagOptionsAdapter(FlexboxLayoutAdapter tagOptionsAdapter) {
        this.tagOptionsAdapter = tagOptionsAdapter;
        notifyPropertyChanged(BR.tagOptionsAdapter);
    }

    public LabelClassification getLabelClassification() {
        return labelClassification;
    }

    public void setLabelClassification(LabelClassification labelClassification) {
        this.labelClassification = labelClassification;
    }

    @Bindable
    public boolean isHasEdited() {
        return hasEdited;
    }

    public void setHasEdited(boolean hasEdited) {
        if (!this.hasEdited&&hasEdited){
            ((TaskEditingInfoActivity) getContext()).setResult(GlobalConstant.LIST_AUTO_REFRESH, new Intent());
        }
        this.hasEdited = hasEdited;
        notifyPropertyChanged(BR.hasEdited);
    }

    @Bindable
    public List<DataFile> getDataFiles() {
        return dataFiles;
    }

    public void setDataFiles(List<DataFile> dataFiles) {
        this.dataFiles = dataFiles;
        notifyPropertyChanged(BR.dataFiles);
    }

    public void addDataFile(DataFile dataFile){
        this.dataFiles.add(dataFile);
        notifyPropertyChanged(BR.dataFiles);
    }

    @Bindable
    public int getTotalData() {
        return totalData;
    }

    public void setTotalData(int totalData) {
        this.totalData = totalData;
        notifyPropertyChanged(BR.totalData);
    }

    public void addToTotalData(int add){
        totalData+=add;
        notifyPropertyChanged(BR.totalData);
    }
}
