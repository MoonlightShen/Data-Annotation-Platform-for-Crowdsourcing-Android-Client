package com.echo.dapc.base.activity;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.echo.dapc.util.system.JsonUtil;
import com.echo.dapc.util.system.KeyBoardUtil;
import com.echo.dapc.util.system.ThreadUtil;


public abstract class BaseActivity extends AppCompatActivity {

    protected Handler handler;
    protected ActivityResultLauncher<Intent> launcher;

    /**
     * 获取当前Activity绑定的布局文件的ID
     *
     * @return 如R.layout.activity_xxx.xml
     */
    protected abstract int getUILayoutId();

    /**
     * 根据不同的Activity二级类为当前Activity初始化，该方法应在二级Activity类中添加final关键字以闭合
     */
    protected abstract void initActivity();

    /**
     * 在主线程的初始化操作，如为一些特殊组件添加监听器
     */
    protected abstract void initOnMainThread();

    /**
     * 在子线程的初始化操作，如从网络请求中加载数据
     */
    protected abstract void initInBackground();

    protected void handleMsg(int what) {

    }

    protected void handleRes(int resultCode, Intent data) {

    }

    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getUILayoutId());
        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                handleMsg(msg.what);
            }
        };
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            handleRes(result.getResultCode(), result.getData());
        });

        initActivity();

        initOnMainThread();
        ThreadUtil.submitToCached(this::initInBackground);
    }

    protected final Context getContext() {
        return this;
    }

    protected final void createIntent(Class<? extends AppCompatActivity> cls) {
        createIntent(cls, false);
    }

    protected final void createIntent(Class<? extends AppCompatActivity> cls, boolean finish) {
        Intent intent = new Intent(this, cls);
        launcher.launch(intent);
        if (finish) finish();
    }

    protected final void createIntent(Class<? extends AppCompatActivity> cls, boolean finish, IntentData... intentData) {
        Intent intent = new Intent(this, cls);
        for (BaseDataBindingActivity.IntentData data : intentData) {
            if (data.valueClass == Integer.class) {
                intent.putExtra(data.key, (int) data.value);
            } else if (data.valueClass == String.class) {
                intent.putExtra(data.key, (String) data.value);
            } else if (data.valueClass == Long.class) {
                intent.putExtra(data.key, (Long) data.value);
            } else if (data.valueClass == Double.class) {
                intent.putExtra(data.key, (Double) data.value);
            } else if (data.valueClass == Character.class) {
                intent.putExtra(data.key, (Character) data.value);
            } else if (data.valueClass == Short.class) {
                intent.putExtra(data.key, (Short) data.value);
            } else if (data.valueClass == Boolean.class) {
                intent.putExtra(data.key, (Boolean) data.value);
            }
        }
        launcher.launch(intent);
        if (finish) finish();
    }

    @Override
    public final boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (clearFocusOfViews() != null) {
                for (MonitorView monitorView : clearFocusOfViews()) {
                    if (monitorView.view.hasFocus()) {
                        monitorView.view.clearFocus();
                        if (KeyBoardUtil.isSoftKeyBoardShow(this)) {
                            KeyBoardUtil.hideSoftInputFromWindow(this, monitorView.view);
                        }
                        break;
                    }
                }
            }
            if (clearFocusOfClasses() != null) {
                View view = getCurrentFocus();
                for (MonitorClass monitorClass : clearFocusOfClasses()) {
                    if (monitorClass.cls.isInstance(view)) {
                        if (switch (monitorClass.strategy) {
                            case ACCURATE -> KeyBoardUtil.touchInside(ev, view);
                            case ROW -> KeyBoardUtil.touchInRow(ev, view);
                            case COLUMN -> KeyBoardUtil.touchInColumn(ev, view);
                        }) {
                            return super.dispatchTouchEvent(ev);
                        }
                        KeyBoardUtil.hideSoftInputFromWindow(this, view);
                        break;
                    }
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 监控的组件列表，如果这些组件拥有焦点且用户产生了不满足对应监控策略的行为，则清除其焦点并隐藏键盘
     *
     * @return
     */
    protected MonitorView[] clearFocusOfViews() {
        return null;
    }

    /**
     * 监控的组件类型列表，如果这些组件拥有焦点且用户产生了不满足对应监控策略的行为，则清除其焦点并隐藏键盘
     *
     * @return
     */
    protected MonitorClass[] clearFocusOfClasses() {
        return null;
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

    protected final void postDelayed(@NonNull Runnable runnable, long delayMillis) {
        handler.postDelayed(runnable, delayMillis);
    }

    protected NotificationManager getNotificationManager() {
        return (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    protected enum MonitorStrategy {
        ACCURATE,
        ROW,
        COLUMN
    }

    protected static final class IntentData {
        private final String key;
        private final Object value;
        private final Class<?> valueClass;

        public IntentData(String key, Object value) {
            this.key = key;
            this.value = value;
            valueClass = value.getClass();
        }
    }

    protected static final class MonitorView {
        final View view;
        final MonitorStrategy strategy;

        public MonitorView(View view, MonitorStrategy strategy) {
            this.view = view;
            this.strategy = strategy;
        }
    }

    protected static final class MonitorClass {
        final Class<?> cls;
        final MonitorStrategy strategy;

        public MonitorClass(Class<?> cls, MonitorStrategy strategy) {
            this.cls = cls;
            this.strategy = strategy;
        }
    }
}
