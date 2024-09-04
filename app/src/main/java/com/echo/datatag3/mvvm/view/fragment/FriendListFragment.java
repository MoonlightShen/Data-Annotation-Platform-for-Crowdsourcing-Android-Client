package com.echo.datatag3.mvvm.view.fragment;

import android.database.sqlite.SQLiteException;

import com.echo.datatag3.R;
import com.echo.datatag3.adapter.MessageWindowListAdapter;
import com.echo.datatag3.base.BaseFragment;
import com.echo.datatag3.bean.entity.ChatInfo;
import com.echo.datatag3.bean.entity.PrivateMessage;
import com.echo.datatag3.bean.enumeration.MessageContentType;
import com.echo.datatag3.bean.enumeration.MessageWindowType;
import com.echo.datatag3.bean.logic.MessageWindow;
import com.echo.datatag3.bean.entity.Friend;
import com.echo.datatag3.databinding.FragmentFriendListBinding;
import com.echo.datatag3.interfaces.callback.chatinfo.ChatInfoCallback;
import com.echo.datatag3.interfaces.callback.chatinfo.ChatInfoListCallback;
import com.echo.datatag3.interfaces.callback.common.CommonEntityListCallback;
import com.echo.datatag3.interfaces.callback.message.PrivateMessageCallback;
import com.echo.datatag3.interfaces.callback.user.QueryFriendsCallback;
import com.echo.datatag3.mvvm.model.FriendListModel;
import com.echo.datatag3.mvvm.view.PrivateChatView;
import com.echo.datatag3.mvvm.viewmodel.FriendListViewModel;
import com.echo.datatag3.util.business.ChatInfoUtil;
import com.echo.datatag3.util.business.ChatMessageUtil;
import com.echo.datatag3.util.business.InfoUtil;
import com.echo.datatag3.util.business.UserUtil;
import com.echo.datatag3.util.network.response.message.UnreadMessageInfoRes;
import com.echo.datatag3.util.system.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FriendListFragment extends BaseFragment<FriendListViewModel, FriendListModel, FragmentFriendListBinding> {

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_friend_list;
    }

    @Override
    protected void init() {
        MessageWindowListAdapter messageWindowListAdapter = new MessageWindowListAdapter();
        messageWindowListAdapter.setClickListener((itemview, position) -> {
            MessageWindow messageWindow = getViewModel().getModel().getAdapter().getItem(position);
            Map<String, Object> map = new HashMap<>();
            map.put("friend_id", messageWindow.getMessageWindowId());
            map.put("friend_name", messageWindow.getName());
            createIntent(PrivateChatView.class, false, map);
            if (messageWindow.getUnreadMessagesNum()!=null&&messageWindow.getUnreadMessagesNum()>0){
                ChatInfoUtil.refreshPrivateMessageUnreadNum(messageWindow.getMessageWindowId(), 0);
            }
        });
        getModel().setAdapter(messageWindowListAdapter);
        getModel().setQueryUnreadMessageInfo(new Runnable() {
            @Override
            public void run() {
                ChatMessageUtil.queryUnreadInfo(new ChatInfoListCallback() {
                    @Override
                    public void onSuccess(UnreadMessageInfoRes.Data data) {
                        if (data.friendMessage!=null&&!data.friendMessage.isEmpty()){
                            for (Map.Entry<String, UnreadMessageInfoRes.Data.FriendMessageInfo> entry:data.friendMessage.entrySet()){
                                try {
                                    long id = Long.parseLong(entry.getKey());
                                    Integer position = getModel().getAdapter().getPosition(id);
                                    if (position!=null){
                                        MessageWindow messageWindow = getModel().getAdapter().getItem(position);
                                        messageWindow.setUnreadMessagesNum(entry.getValue().total);
                                        messageWindow.setContent(entry.getValue().content);
                                        messageWindow.setContentType(MessageContentType.getByIndex(entry.getValue().contentType));
                                        messageWindow.setLastSendTime(entry.getValue().lastSendTime);
                                        runOnUiThread(()->getModel().getAdapter().refreshItem(messageWindow, position));
                                    }
                                }catch (NumberFormatException | SQLiteException ignored){
                                    //TODO 标记日志
                                }
                            }
                        }
                    }

                    @Override
                    public void onSQLiteException() {

                    }

                    @Override
                    public void unknownError(String errorCode) {

                    }

                    @Override
                    public void onNoNetwork() {

                    }
                });
                getModel().getHandler().postDelayed(this, 5000);
            }
        });
    }

    @Override
    protected List<Runnable> loadWithChildThread() {
        List<Runnable> runnableList = new ArrayList<>();
        runnableList.add(() -> UserUtil.queryFriends(new CommonEntityListCallback<Friend>() {
            @Override
            public void onSuccess(List<Friend> friends) {
                List<MessageWindow> messageWindows = new ArrayList<>();
                for (Friend friend:friends){
                    messageWindows.add(new MessageWindow(friend.getUserId(), friend.getRemark(), null,
                            null, null, null, MessageWindowType.FRIEND));
                }
                runOnUiThread(() -> {
                    getViewModel().getModel().getAdapter().refreshItems(messageWindows);
                    for (int i=0;i<getViewModel().getModel().getAdapter().getData().size();i++){
                        MessageWindow messageWindow = getViewModel().getModel().getAdapter().getItem(i);
                        int finalI = i;
                        ChatMessageUtil.queryPrivateMessage(new PrivateMessageCallback() {
                            @Override
                            public void onSuccess(PrivateMessage message) {
                                if (message==null){
                                    return;
                                }
                                messageWindow.setContent(message.getContent());
                                messageWindow.setLastSendTime(message.getSendTime());
                                runOnUiThread(()-> ChatInfoUtil.queryPrivateMessageUnreadNum(new ChatInfoCallback() {
                                    @Override
                                    public void onSuccess(ChatInfo chatInfo) {
                                        if (chatInfo!=null&&chatInfo.getUnreadNum()!=null){
                                            messageWindow.setUnreadMessagesNum(chatInfo.getUnreadNum());
                                            messageWindow.setContent(chatInfo.getLastMessageContent());
                                            messageWindow.setContentType(chatInfo.getContentType());
                                            runOnUiThread(() -> getViewModel().getModel().getAdapter().refreshItem(messageWindow, finalI));
                                        }
                                    }

                                    @Override
                                    public void onSQLiteException() {

                                    }

                                    @Override
                                    public void onError(String errorCode) {

                                    }
                                }, messageWindow.getMessageWindowId()));
                            }

                            @Override
                            public void onSQLiteException() {

                            }

                            @Override
                            public void onError(String errorCode) {

                            }
                        }, messageWindow.getMessageWindowId(), InfoUtil.getUserId());
                    }
                });
            }
        }));
        return runnableList;
    }

    @Override
    protected void loadSuccess() {
        if (!getModel().isRunnableActivate()){
            getModel().setRunnableActivate(true);
            getModel().getHandler().post(getModel().getQueryUnreadMessageInfo());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!getModel().isRunnableActivate()){
            getModel().setRunnableActivate(true);
            getModel().getHandler().post(getModel().getQueryUnreadMessageInfo());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getModel().isRunnableActivate()){
            getModel().setRunnableActivate(false);
            getModel().getHandler().removeCallbacks(getModel().getQueryUnreadMessageInfo());
        }
    }
}