package com.echo.dapc.mvvm.model;

import androidx.databinding.Bindable;

import com.echo.dapc.BR;
import com.echo.dapc.adapter.DataFileContentListAdapter;
import com.echo.dapc.base.BaseModel;
import com.echo.dapc.bean.logic.DataFile;

import java.io.BufferedReader;

public class DataFileTextContentModel extends BaseModel {
    private DataFile datafile;
    private BufferedReader reader;
    private DataFileContentListAdapter adapter;
    private String separator;

    private boolean localCached;

    public BufferedReader getReader() {
        return reader;
    }

    public void setReader(BufferedReader reader) {
        this.reader = reader;
    }

    @Bindable
    public DataFileContentListAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(DataFileContentListAdapter adapter) {
        this.adapter = adapter;
        notifyPropertyChanged(BR.adapter);
    }

    public DataFile getDatafile() {
        return datafile;
    }

    public void setDatafile(DataFile datafile) {
        this.datafile = datafile;
    }

    @Bindable
    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
        notifyPropertyChanged(BR.separator);
    }

    @Bindable
    public boolean isLocalCached() {
        return localCached;
    }

    public void setLocalCached(boolean localCached) {
        this.localCached = localCached;
        notifyPropertyChanged(BR.localCached);
    }
}
