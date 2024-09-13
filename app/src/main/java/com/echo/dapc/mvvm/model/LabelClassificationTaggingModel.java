package com.echo.dapc.mvvm.model;

import androidx.databinding.Bindable;

import com.echo.dapc.BR;
import com.echo.dapc.base.BaseModel;
import com.echo.dapc.base.BaseSelectableListAdapter;
import com.echo.dapc.bean.entity.UntaggingData;
import com.google.android.flexbox.FlexboxLayoutManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LabelClassificationTaggingModel extends BaseModel {
    private long taggingDataSetIndex;
    private List<UntaggingData> taggingDataList = new ArrayList<>();
    private Integer currentDataIndex;
    private Map<Integer, List<Integer>> hasTagged = new HashMap<>();
    private Map<String, Integer> optionDict = new HashMap<>();

    private FlexboxLayoutManager flexboxLayoutManager;
    private BaseSelectableListAdapter optionAdapter;

    private Boolean customSelect;
    private String customOptionContent;
    private Boolean multipleSelect;

    private boolean hasEdited;

    @Bindable
    public boolean isHasEdited() {
        return hasEdited;
    }

    public long getTaggingDataSetIndex() {
        return taggingDataSetIndex;
    }

    public void setTaggingDataSetIndex(long taggingDataSetIndex) {
        this.taggingDataSetIndex = taggingDataSetIndex;
    }

    public void setHasEdited(boolean hasEdited) {
        this.hasEdited = hasEdited;
        notifyPropertyChanged(BR.hasEdited);
    }

    public List<UntaggingData> getTaggingDataList() {
        return taggingDataList;
    }

    public void setTaggingDataList(List<UntaggingData> taggingDataList) {
        this.taggingDataList = taggingDataList;
    }

    public Map<Integer, List<Integer>> getHasTagged() {
        return hasTagged;
    }

    public void setHasTagged(Map<Integer, List<Integer>> hasTagged) {
        this.hasTagged = hasTagged;
    }

    public Map<String, Integer> getOptionDict() {
        return optionDict;
    }

    public void setOptionDict(Map<String, Integer> optionDict) {
        this.optionDict = optionDict;
    }

    @Bindable
    public Integer getCurrentDataIndex() {
        return currentDataIndex;
    }

    public void setCurrentDataIndex(Integer currentDataIndex) {
        this.currentDataIndex = currentDataIndex;
        notifyPropertyChanged(BR.currentDataIndex);
    }

    public void addCurrentDataIndex(){
        this.currentDataIndex++;
        notifyPropertyChanged(BR.currentDataIndex);
    }

    public void reduceCurrentDataIndex(){
        this.currentDataIndex--;
        notifyPropertyChanged(BR.currentDataIndex);
    }

    @Bindable
    public String getCustomOptionContent() {
        return customOptionContent;
    }

    public void setCustomOptionContent(String customOptionContent) {
        this.customOptionContent = customOptionContent;
        notifyPropertyChanged(BR.customOptionContent);
    }

    @Bindable
    public FlexboxLayoutManager getFlexboxLayoutManager() {
        return flexboxLayoutManager;
    }

    public void setFlexboxLayoutManager(FlexboxLayoutManager flexboxLayoutManager) {
        this.flexboxLayoutManager = flexboxLayoutManager;
        notifyPropertyChanged(BR.flexboxLayoutManager);
    }

    @Bindable
    public BaseSelectableListAdapter getOptionAdapter() {
        return optionAdapter;
    }

    public void setOptionAdapter(BaseSelectableListAdapter optionAdapter) {
        this.optionAdapter = optionAdapter;
        notifyPropertyChanged(BR.optionAdapter);
    }

    @Bindable
    public Boolean getCustomSelect() {
        return customSelect;
    }

    public void setCustomSelect(Boolean customSelect) {
        this.customSelect = customSelect;
        notifyPropertyChanged(BR.customSelect);
    }

    @Bindable
    public Boolean getMultipleSelect() {
        return multipleSelect;
    }

    public void setMultipleSelect(Boolean multipleSelect) {
        this.multipleSelect = multipleSelect;
        notifyPropertyChanged(BR.multipleSelect);
    }
}
