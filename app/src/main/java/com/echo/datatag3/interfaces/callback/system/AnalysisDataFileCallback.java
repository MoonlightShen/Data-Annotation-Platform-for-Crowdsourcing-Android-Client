package com.echo.datatag3.interfaces.callback.system;

public interface AnalysisDataFileCallback {
    void onAnalysisSuccess(double fileSize, int dataNum);
    void onFileNotExist();
}
