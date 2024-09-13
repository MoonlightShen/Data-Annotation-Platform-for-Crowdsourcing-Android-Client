package com.echo.dapc.base.activity;

import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.echo.dapc.R;
import com.echo.dapc.base.ChatMessageListAdapter;
import com.echo.dapc.databinding.ActivityChatBinding;
import com.echo.dapc.mvvm.model.ChatModel;
import com.echo.dapc.mvvm.viewmodel.ChatViewModel;
import com.echo.dapc.util.system.ToastUtil;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

public abstract class BaseChatActivity<T, Adapter extends ChatMessageListAdapter<T>> extends BaseDataBindingActivity<ChatViewModel<T, Adapter>, ChatModel<T, Adapter>, ActivityChatBinding> {
    private SendTextMessageCallback sendTextMessageCallback;

    /**
     * 获取当前Activity绑定的布局文件的ID
     *
     * @return 如R.layout.activity_xxx.xml
     */
    @Override
    protected int getUILayoutId() {
        return R.layout.activity_chat;
    }

    @Override
    protected final ChatViewModel<T, Adapter> createViewModel() {
        return (ChatViewModel<T, Adapter>) new ViewModelProvider(this).get(ChatViewModel.class);
    }

    @Override
    protected final ChatModel<T, Adapter> createModel() {
        return new ChatModel<>();
    }

    protected abstract @NonNull String getChatObjectIdKey();

    protected abstract @NonNull String getTitleText();

    protected abstract @NonNull Adapter createAdapter();

    protected abstract @NonNull OnRefreshListener createRefreshListener();

    protected abstract @NonNull SendTextMessageCallback createTextSendMessageCallback();

    protected abstract void initPage();

    @Override
    protected void initActivity() {
        super.initActivity();
        getBinding().titleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(TitleBar titleBar) {
                finish();
            }
        });
        long chatObjectId = getLongExtra(getChatObjectIdKey(), 0);
        if (chatObjectId != 0) {
            getModel().setChatObjectId(chatObjectId);
        } else {
            getBinding().smartRefreshLayout.setEnableRefresh(false);
            ToastUtil.error("消息列表拉取失败");
            //TODO 打印日志
        }

        LinearLayoutManager manager = new LinearLayoutManager(this);
//        manager.setStackFromEnd(true);
        getBinding().recycler.setLayoutManager(manager);
        getBinding().titleBar.setTitle(getTitleText());

        Adapter adapter = createAdapter();
        OnRefreshListener listener = createRefreshListener();

        getViewModel().getModel().setAdapter(adapter);
        getBinding().smartRefreshLayout.setOnRefreshListener(listener);

        sendTextMessageCallback = createTextSendMessageCallback();

        getBinding().sendMsgBtn.setOnClickListener(v -> {
            if (getModel().isCanSend() && getBinding().msgInput.getText() != null) {
                String contentMessage = getBinding().msgInput.getText().toString();
                getBinding().msgInput.setText("");
                scrollToBottom();
                //TODO 发送不同类型消息的分发，这里仅考虑了文字消息
                sendTextMessageCallback.message(contentMessage);
            }
        });

        getBinding().msgInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                getModel().setCanSend(s != null && !s.toString().isEmpty());
            }
        });

        initPage();
    }

    /**
     * 在主线程的初始化操作，如为一些特殊组件添加监听器
     */
    @Override
    protected void initOnMainThread() {

    }

    /**
     * 在子线程的初始化操作，如从网络请求中加载数据
     */
    @Override
    protected void initInBackground() {

    }

    @Override
    protected MonitorView[] clearFocusOfViews() {
        return new MonitorView[]{new MonitorView(getBinding().msgInput, MonitorStrategy.ROW)};
    }

    protected final long getChatObjectId() {
        return getModel().getChatObjectId();
    }

    protected final Adapter getMessageAdapter() {
        return getModel().getAdapter();
    }

    protected final T getEarliestMessage() {
        if (getMessageAdapter().getItemCount() == 0) return null;
        else return getMessageAdapter().getItem(0);
    }

    protected final void addMessage(T t) {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            getMessageAdapter().addItem(t);
        } else {
            getModel().getHandler().post(() -> getMessageAdapter().addItem(t));
        }
    }

    protected final void scrollToBottom() {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            getBinding().recycler.scrollToPosition(getMessageAdapter().getItemCount() - 1);
        } else {
            getModel().getHandler().post(() ->
                    getBinding().recycler.scrollToPosition(getMessageAdapter().getItemCount() - 1));
        }
    }

    protected interface SendTextMessageCallback {
        void message(String messageContent);
    }

}
