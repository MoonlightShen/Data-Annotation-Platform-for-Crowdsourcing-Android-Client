package com.echo.dapc.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.echo.dapc.BR;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public abstract class BaseFragment<VM extends BaseViewModel<M>, M extends BaseModel, VDB extends ViewDataBinding> extends Fragment {

    private VM viewModel;
    private VDB viewDataBinding;
    private View view;

    protected final VM getViewModel() {
        return viewModel;
    }

    protected final VDB getBinding() {
        return viewDataBinding;
    }

    protected abstract int getContentViewId();

    protected abstract void init();

    protected void handleMsg(int what) {

    }

    protected void handleRes(int resultCode) {

    }

    /**
     * 创建子线程处理耗时操作，重写此方法时必须重写loadSuccess()方法
     *
     * @return
     */
    protected List<Runnable> loadWithChildThread() {
        return null;
    }
//    CompletableFuture<Void> future = CompletableFuture.runAsync(new WorkerTask(i));
//    futures.add(future);

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
        Intent intent = new Intent(getContext(), cls);
        if (msgKey!=null&&msgValue!=null){
            if (msgValue instanceof Integer) intent.putExtra(msgKey, (int) msgValue);
            else if (msgValue instanceof String) intent.putExtra(msgKey, (String) msgValue);
            else if (msgValue instanceof Long) intent.putExtra(msgKey, (long) msgValue);
        }
        getViewModel().launch(intent);
        if (finish) requireActivity().finish();
    }

    protected final void createIntent(Class<? extends AppCompatActivity> cls, boolean finish, Map<String, Object> msg) {
        Intent intent = new Intent(getContext(), cls);
        if (msg!=null&&!msg.isEmpty()){
            for (Map.Entry<String, Object> entry:msg.entrySet()){
                if (entry.getValue() instanceof Integer) intent.putExtra(entry.getKey(), (int) entry.getValue());
                else if (entry.getValue() instanceof String) intent.putExtra(entry.getKey(), (String) entry.getValue());
                else if (entry.getValue() instanceof Long) intent.putExtra(entry.getKey(), (long) entry.getValue());
            }
        }
        getViewModel().launch(intent);
        if (finish) requireActivity().finish();
    }

    public final Context getContext() {
        return requireActivity();
    }

    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        // 避免多次从xml中加载布局文件
        if (view == null) {
            viewDataBinding = DataBindingUtil.inflate(inflater, getContentViewId(), null, false);
            view = viewDataBinding.getRoot();
            viewDataBinding.setLifecycleOwner(requireActivity());

            viewModel = (VM) new ViewModelProvider(this).get((Class<? extends BaseViewModel<? extends BaseModel>>) (Class<?>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);

            M model;
            try {
                model = (M) ((Class<? extends BaseModel>) (Class<?>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1]).newInstance();
            } catch (IllegalAccessException | java.lang.InstantiationException e) {
                throw new RuntimeException("初始化Model失败");
            }

            model.setContext(getContext());
            model.setHandler(new Handler(Looper.getMainLooper()) {
                @Override
                public void handleMessage(@NonNull Message msg) {
                    super.handleMessage(msg);
                    handleMsg(msg.what);
                }
            });
            model.setLauncher(registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                handleRes(result.getResultCode());
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
            } else {
                loadSuccess();
            }

        } else {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
        }
        return view;
    }

    protected final void runOnUiThread(Runnable runnable){
        requireActivity().runOnUiThread(runnable);
    }

    protected final int getIntExtra(String key, int defaultValue) {
        return getActivity().getIntent().getIntExtra(key, defaultValue);
    }

    protected final long getLongExtra(String key, long defaultValue) {
        return getActivity().getIntent().getLongExtra(key, defaultValue);
    }

    protected final char getCharExtra(String key, char defaultValue) {
        return getActivity().getIntent().getCharExtra(key, defaultValue);
    }

    protected final double getDoubleExtra(String key, double defaultValue) {
        return getActivity().getIntent().getDoubleExtra(key, defaultValue);
    }

    protected final float getFloatExtra(String key, float defaultValue) {
        return getActivity().getIntent().getFloatExtra(key, defaultValue);
    }

    protected final short getShortExtra(String key, short defaultValue) {
        return getActivity().getIntent().getShortExtra(key, defaultValue);
    }

    protected final String getStringExtra(String key) {
        return getActivity().getIntent().getStringExtra(key);
    }

    protected final M getModel(){
        return getViewModel().getModel();
    }

    protected final void postDelayed(@NonNull Runnable runnable, long delayMillis){
        getModel().getHandler().postDelayed(runnable, delayMillis);
    }

}
