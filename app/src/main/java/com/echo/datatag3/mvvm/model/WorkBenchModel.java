package com.echo.datatag3.mvvm.model;

import androidx.databinding.Bindable;

import com.echo.datatag3.BR;
import com.echo.datatag3.adapter.TaskListAdapter;
import com.echo.datatag3.base.BaseModel;

public class WorkBenchModel extends BaseModel {
    private TaskListAdapter taskAdapter = null;
    private int count = 10;

    @Bindable
    public TaskListAdapter getTaskAdapter() {
        return taskAdapter;
    }

    public void setTaskAdapter(TaskListAdapter taskAdapter) {
        this.taskAdapter = taskAdapter;
        notifyPropertyChanged(BR.taskAdapter);
    }

    public int getCount() {
        if (count<=30)count+=1;
        return count;
    }
}
