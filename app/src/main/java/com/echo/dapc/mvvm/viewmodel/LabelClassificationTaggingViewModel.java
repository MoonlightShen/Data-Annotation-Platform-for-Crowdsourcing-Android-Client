package com.echo.dapc.mvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.echo.dapc.base.BaseViewModel;
import com.echo.dapc.bean.entity.UntaggingData;
import com.echo.dapc.interfaces.callback.untaggingdata.SaveTaggingProgressCallback;
import com.echo.dapc.mvvm.model.LabelClassificationTaggingModel;
import com.echo.dapc.util.business.TaggingDataUtil;
import com.echo.dapc.util.system.ToastUtil;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LabelClassificationTaggingViewModel extends BaseViewModel<LabelClassificationTaggingModel> {
    public LabelClassificationTaggingViewModel(@NonNull Application application) {
        super(application);
    }

    public void lastData() {
        if (getModel().getOptionAdapter().getSelectIndex()!=null){
            getModel().getHasTagged().put(getModel().getCurrentDataIndex(), getModel().getOptionAdapter().getSelectIndex());
        }else getModel().getHasTagged().remove(getModel().getCurrentDataIndex());
        if (getModel().getCurrentDataIndex()>=0&&getModel().getCurrentDataIndex()<getModel().getTaggingDataList().size()){
            getModel().reduceCurrentDataIndex();
        }else {
            ToastUtil.normal("已经是第一条数据了哦");
        }

    }

    public void nextData() {
        if (getModel().getOptionAdapter().getSelectIndex()!=null){
            getModel().getHasTagged().put(getModel().getCurrentDataIndex(), getModel().getOptionAdapter().getSelectIndex());
        }else getModel().getHasTagged().remove(getModel().getCurrentDataIndex());
        if (getModel().getCurrentDataIndex()>=0&&getModel().getCurrentDataIndex()<getModel().getTaggingDataList().size()){
            getModel().addCurrentDataIndex();
        }else {
            ToastUtil.normal("已经是最后一条数据了哦");
        }
    }

    public void saveProgress() {
        Set<Integer> positions = new HashSet<>();
        List<UntaggingData> untaggingDataList = getModel().getTaggingDataList();
        for (Map.Entry<Integer, List<Integer>> entry : getModel().getHasTagged().entrySet()) {
            positions.add(entry.getKey());
            untaggingDataList.get(entry.getKey()).setTaggingOption(entry.getValue());
        }
        Map<Integer, String> optionDict = new HashMap<>();
        for (Map.Entry<String, Integer> entry : getModel().getOptionDict().entrySet()) {
            optionDict.put(entry.getValue(), entry.getKey());
        }
        TaggingDataUtil.saveTaggingProgress(new SaveTaggingProgressCallback() {
            @Override
            public void onSuccess() {
                getModel().setHasEdited(true);
            }

            @Override
            public void setNotExist() {
                ToastUtil.error("保存失败");
            }

            @Override
            public void currentDataIndexOutOfRange() {
                ToastUtil.error("保存失败");
            }
        }, untaggingDataList, positions, getModel().getTaggingDataSetIndex(), getModel().getCurrentDataIndex(), optionDict);
    }

    public void reportData() {
        //TODO 举报数据
    }

}
