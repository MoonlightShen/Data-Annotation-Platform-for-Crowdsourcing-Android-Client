package com.echo.datatag3.base;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import androidx.activity.result.ActivityResultLauncher;
import androidx.databinding.BaseObservable;
import androidx.fragment.app.FragmentManager;

public abstract class BaseModel extends BaseObservable {
    private Context context;
    private Handler handler;
    private ActivityResultLauncher<Intent> launcher;
    private FragmentManager fragmentManager;

    public Context getContext() {
        return context;
    }

    public Handler getHandler() {
        return handler;
    }

    public ActivityResultLauncher<Intent> getLauncher() {
        return launcher;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public void setLauncher(ActivityResultLauncher<Intent> launcher) {
        this.launcher = launcher;
    }

    public FragmentManager getFragmentManager() {
        return fragmentManager;
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }
}
