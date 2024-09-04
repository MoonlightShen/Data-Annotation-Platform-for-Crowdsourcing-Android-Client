package com.echo.datatag3.base;

import android.app.Application;
import android.content.Intent;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.AndroidViewModel;

public abstract class BaseViewModel<T extends BaseModel> extends AndroidViewModel {
    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    private T t;

    public final T getModel() {
        return t;
    }

    public final void setModel(T t) {
        this.t = t;
    }

    protected final void sendMsgToMainThread(int what, Object obj) throws RuntimeException {
        if (t!=null){
            Message msg = t.getHandler().obtainMessage();
            msg.what = what;
            msg.obj = obj;
            t.getHandler().sendMessage(msg);
        }else{
            throw new RuntimeException("没有正确初始化Model");
        }
    }

    protected final void createIntent(Class<? extends AppCompatActivity> cls){
        createIntent(cls, false);
    }

    protected final void createIntent(Class<? extends AppCompatActivity> cls, boolean finish){
        Intent intent = new Intent(getModel().getContext(), cls);
        launch(intent);
        if (finish)((AppCompatActivity)(getModel().getContext())).finish();
    }

    protected final void launch(Intent intent){
        getModel().getLauncher().launch(intent);
    }

    protected final void postDelayed(@NonNull Runnable runnable, long delayMillis){
        getModel().getHandler().postDelayed(runnable, delayMillis);
    }

}
