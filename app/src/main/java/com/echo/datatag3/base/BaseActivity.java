package com.echo.datatag3.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.library.baseAdapters.BR;

import com.echo.datatag3.util.system.JsonUtil;
import com.echo.datatag3.util.system.KeyBoardUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public abstract class BaseActivity<VM extends BaseViewModel<M>, M extends BaseModel, VDB extends ViewDataBinding> extends AppCompatActivity {
    protected static final int DISPATCH_ACCURATE = 1;
    protected static final int DISPATCH_ROW = 2;

    private VM viewModel;
    private VDB viewDataBinding;
    private Class<?>[] dispatchViewClasses;
    private int dispatchStrategy;

    protected final VM getViewModel() {
        return viewModel;
    }

    protected final VDB getBinding() {
        return viewDataBinding;
    }

    protected abstract int getContentViewId();

    protected abstract VM createViewModel();

    protected abstract M createModel() throws IllegalAccessException, InstantiationException;

    protected abstract void init();
//    CompletableFuture<Void> future = CompletableFuture.runAsync(new WorkerTask(i));
//    futures.add(future);

    protected void handleMsg(int what) {

    }

    protected void handleRes(int resultCode,Intent data) {

    }

    /**
     * 创建子线程处理耗时操作，重写此方法时必须重写loadSuccess()方法
     *
     * @return
     */
    protected List<Runnable> loadWithChildThread() {
        return null;
    }

    /**
     * 所有子线程成功处理完成后，会调用此方法
     */
    protected void loadSuccess() {

    }

    protected final void createIntent(Class<? extends AppCompatActivity> cls) {
        createIntent(cls, false);
    }

    protected final void createIntent(Class<? extends AppCompatActivity> cls, String msgKey, Object msgValue) {
        createIntent(cls, false, msgKey, msgValue);
    }

    protected final void createIntent(Class<? extends AppCompatActivity> cls, boolean finish) {
        createIntent(cls, finish, null, null);
    }

    protected final void createIntent(Class<? extends AppCompatActivity> cls, boolean finish, String msgKey, Object msgValue) {
        Intent intent = new Intent(this, cls);
        if (msgKey != null && msgValue != null) {
            if (msgValue instanceof Integer) intent.putExtra(msgKey, (int) msgValue);
            else if (msgValue instanceof String) intent.putExtra(msgKey, (String) msgValue);
            else if (msgValue instanceof Long) intent.putExtra(msgKey, (long) msgValue);
        }
        getViewModel().launch(intent);
        if (finish) finish();
    }

    protected final void createIntent(Class<? extends AppCompatActivity> cls, boolean finish, Map<String, Object> kv) {
        Intent intent = new Intent(this, cls);
        if (kv != null) {
            for (Map.Entry<String, Object> entry:kv.entrySet()){
                if (entry.getValue() instanceof Integer) intent.putExtra(entry.getKey(), (int) entry.getValue());
                else if (entry.getValue() instanceof String) intent.putExtra(entry.getKey(), (String) entry.getValue());
                else if (entry.getValue() instanceof Long) intent.putExtra(entry.getKey(), (long) entry.getValue());
            }
        }
        getViewModel().launch(intent);
        if (finish) finish();
    }

    protected final Context getContext(){
        return this;
    }

    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());

        //初始化binging
        viewDataBinding = DataBindingUtil.setContentView(this, getContentViewId());
        //给binding加上感知生命周期（该抽象类继承了AppcompatActivity）
        viewDataBinding.setLifecycleOwner(this);

        viewModel = createViewModel();

        M model;
        try {
            model = createModel();
        } catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException("初始化Model失败");
        }

        model.setContext(this);
        model.setFragmentManager(this.getSupportFragmentManager());
        model.setHandler(new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                handleMsg(msg.what);
            }
        });
        model.setLauncher(registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            handleRes(result.getResultCode(), result.getData());
        }));

        viewModel.setModel(model);

        viewDataBinding.setVariable(BR.viewModel, viewModel);

        init();

        List<CompletableFuture<Void>> futures = new ArrayList<>();
        List<Runnable> loaderTasks = loadWithChildThread();
        if (loaderTasks != null) {
            for (Runnable runnable : loaderTasks) {
                CompletableFuture<Void> future = CompletableFuture.runAsync(runnable);
                futures.add(future);
            }
        }
        if (!futures.isEmpty()) {
            CompletableFuture<Void> allOfFuture = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
            allOfFuture.thenRun(this::loadSuccess);
        }

    }

    protected final int getIntExtra(String key, int defaultValue) {
        return getIntent().getIntExtra(key, defaultValue);
    }

    protected final long getLongExtra(String key, long defaultValue) {
        return getIntent().getLongExtra(key, defaultValue);
    }

    protected final char getCharExtra(String key, char defaultValue) {
        return getIntent().getCharExtra(key, defaultValue);
    }

    protected final double getDoubleExtra(String key, double defaultValue) {
        return getIntent().getDoubleExtra(key, defaultValue);
    }

    protected final float getFloatExtra(String key, float defaultValue) {
        return getIntent().getFloatExtra(key, defaultValue);
    }

    protected final short getShortExtra(String key, short defaultValue) {
        return getIntent().getShortExtra(key, defaultValue);
    }

    protected final <T> T getJsonExtra(String key, Class<T> type) {
        return JsonUtil.fromJson(getStringExtra(key), type);
    }

    protected final String getStringExtra(String key) {
        return getIntent().getStringExtra(key);
    }

    protected final boolean getBooleanExtra(String key, boolean defaultValue) {
        return getIntent().getBooleanExtra(key, defaultValue);
    }

    @Override
    public final boolean dispatchTouchEvent(MotionEvent ev) {
        if (dispatchViewClasses != null && ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            for (Class<?> c : dispatchViewClasses) {
                if (c.isInstance(view)) {
                    if (KeyBoardUtil.isSoftInputShow(this)) {
                        switch (dispatchStrategy) {
                            case DISPATCH_ACCURATE:
                                if (KeyBoardUtil.touchInside(ev, view)) {
                                    return super.dispatchTouchEvent(ev);
                                }
                                break;
                            case DISPATCH_ROW:
                                if (KeyBoardUtil.touchInRow(ev, view)) {
                                    return super.dispatchTouchEvent(ev);
                                }
                                break;
                        }
                        KeyBoardUtil.hideSoftInputFromWindow(this, view);
                    }
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    protected final void dispatchFromClasses(Class<?>[] classes) {
        dispatchFromClasses(classes, DISPATCH_ACCURATE);
    }

    protected final void dispatchFromClasses(Class<?>[] classes, int dispatchStrategy) {
        this.dispatchViewClasses = classes;
        this.dispatchStrategy = dispatchStrategy;
    }

    protected final M getModel(){
        return getViewModel().getModel();
    }

    protected final void postDelayed(@NonNull Runnable runnable, long delayMillis){
        getModel().getHandler().postDelayed(runnable, delayMillis);
    }
}
