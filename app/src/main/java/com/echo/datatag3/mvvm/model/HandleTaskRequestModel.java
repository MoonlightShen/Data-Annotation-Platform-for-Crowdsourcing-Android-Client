package com.echo.datatag3.mvvm.model;

import androidx.databinding.Bindable;

import com.echo.datatag3.BR;
import com.echo.datatag3.base.BaseModel;
import com.echo.datatag3.bean.logic.User;

public class HandleTaskRequestModel extends BaseModel {
    private User requester;
    private int totalComplete;
    private int totalCompletionRate;
    private long requestTime;

    @Bindable
    public User getRequester() {
        return requester;
    }

    public void setRequester(User requester) {
        this.requester = requester;
        notifyPropertyChanged(BR.requester);
    }

    @Bindable
    public int getTotalComplete() {
        return totalComplete;
    }

    public void setTotalComplete(int totalComplete) {
        this.totalComplete = totalComplete;
        notifyPropertyChanged(BR.totalComplete);
    }

    @Bindable
    public int getTotalCompletionRate() {
        return totalCompletionRate;
    }

    public void setTotalCompletionRate(int totalCompletionRate) {
        this.totalCompletionRate = totalCompletionRate;
        notifyPropertyChanged(BR.totalCompletionRate);
    }

    @Bindable
    public long getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(long requestTime) {
        this.requestTime = requestTime;
        notifyPropertyChanged(BR.requestTime);
    }
}
