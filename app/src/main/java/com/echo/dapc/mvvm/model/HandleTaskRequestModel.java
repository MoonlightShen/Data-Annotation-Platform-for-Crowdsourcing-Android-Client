package com.echo.dapc.mvvm.model;

import androidx.databinding.Bindable;

import com.echo.dapc.BR;
import com.echo.dapc.base.BaseModel;
import com.echo.dapc.bean.logic.User;

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
