package com.echo.dapc.interfaces.callback.system;

public interface AnalysisDataFileCallback {
    void onAnalysisSuccess(double fileSize, int dataNum);
    void onFileNotExist();
}
