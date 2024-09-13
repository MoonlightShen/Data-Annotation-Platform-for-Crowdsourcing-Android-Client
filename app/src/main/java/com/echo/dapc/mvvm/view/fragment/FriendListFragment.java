package com.echo.dapc.mvvm.view.fragment;

import android.database.sqlite.SQLiteException;

import com.echo.dapc.R;
import com.echo.dapc.adapter.MessageWindowListAdapter;
import com.echo.dapc.base.BaseFragment;
import com.echo.dapc.bean.entity.ChatInfo;
import com.echo.dapc.bean.entity.PrivateMessage;
import com.echo.dapc.bean.enumeration.MessageContentType;
import com.echo.dapc.bean.enumeration.MessageWindowType;
import com.echo.dapc.bean.logic.MessageWindow;
import com.echo.dapc.bean.entity.Friend;
import com.echo.dapc.databinding.FragmentFriendListBinding;
import com.echo.dapc.interfaces.callback.chatinfo.ChatInfoCallback;
import com.echo.dapc.interfaces.callback.chatinfo.ChatInfoListCallback;
import com.echo.dapc.interfaces.callback.common.CommonEntityListCallback;
import com.echo.dapc.interfaces.callback.message.PrivateMessageCallback;
import com.echo.dapc.mvvm.model.FriendListModel;
import com.echo.dapc.mvvm.view.PrivateChatActivity;
import com.echo.dapc.mvvm.viewmodel.FriendListViewModel;
import com.echo.dapc.util.business.ChatInfoUtil;
import com.echo.dapc.util.business.ChatMessageUtil;
import com.echo.dapc.util.business.InfoUtil;
import com.echo.dapc.util.business.UserUtil;
import com.echo.dapc.util.network.response.message.UnreadMessageInfoRes;

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
            createIntent(PrivateChatActivity.class, false, map);
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