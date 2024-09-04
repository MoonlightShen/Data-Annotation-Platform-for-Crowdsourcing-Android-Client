package com.echo.datatag3.mvvm.view.fragment;

import com.echo.datatag3.R;
import com.echo.datatag3.adapter.MessageWindowListAdapter;
import com.echo.datatag3.base.BaseFragment;
import com.echo.datatag3.bean.entity.Team;
import com.echo.datatag3.bean.logic.MessageWindow;
import com.echo.datatag3.databinding.FragmentTeamListBinding;
import com.echo.datatag3.interfaces.callback.team.QueryTeamsCallback;
import com.echo.datatag3.mvvm.model.TeamListModel;
import com.echo.datatag3.mvvm.viewmodel.TeamListViewModel;
import com.echo.datatag3.util.business.TeamUtil;
import com.echo.datatag3.util.system.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class TeamListFragment extends BaseFragment<TeamListViewModel, TeamListModel, FragmentTeamListBinding> {


    @Override
    protected int getContentViewId() {
        return R.layout.fragment_team_list;
    }

    @Override
    protected void init() {
        MessageWindowListAdapter adapter = new MessageWindowListAdapter();
//        adapter.setClickListener();
        getViewModel().getModel().setAdapter(adapter);
    }

    /**
     * 创建子线程处理耗时操作，重写此方法时必须重写loadSuccess()方法
     *
     * @return
     */
    @Override
    protected List<Runnable> loadWithChildThread() {
        List<Runnable> runnableList = new ArrayList<>();
        runnableList.add(() -> TeamUtil.queryTeams(new QueryTeamsCallback() {
            @Override
            public void onSuccess(List<Team> teams) {
                List<MessageWindow> messageWindows = new ArrayList<>();
                for (Team team:teams){
                    messageWindows.add(new MessageWindow(team));
                }
                runOnUiThread(() -> {
                    getViewModel().getModel().getAdapter().refreshItems(messageWindows);
                    for (int i=0;i<getViewModel().getModel().getAdapter().getData().size();i++){
                        MessageWindow messageWindow = getViewModel().getModel().getAdapter().getItem(i);
                        int finalI = i;
//                        ChatMessageUtil.queryMessage(new QueryMessageCallback() {
//                            @Override
//                            public void onSuccess(ChatMessage message) {
//                                if (message==null){
//                                    return;
//                                }
//                                if (message.getImageContent()==null) messageWindow.setLastMessage(message.getTextContent());
//                                else messageWindow.setLastMessage("[图片]");
//                                messageWindow.setLastMessageTime(message.getSendTime());
//                                runOnUiThread(() -> {
//                                    getViewModel().getModel().getAdapter().refreshItem(messageWindow, finalI);
//                                });
//                            }
//                            @Override
//                            public void onError(String message) {
//                                //TODO 打印日志
//                            }
//                        }, messageWindow.getMessageWindowId());
//                        ChatMessageUtil.queryMessages(new QueryMessagesCallback() {
//                            @Override
//                            public void onSuccess(List<ChatMessage> messages, boolean hasMore) {
//                                if (messages==null){
//                                    return;
//                                }
//                                int unreadNum = 0;
//                                for (ChatMessage chatMessage:messages){
//                                    if (!chatMessage.getRead())unreadNum++;
//                                }
//                                if (unreadNum>0){
//                                    messageWindow.setUnreadMessagesNum(unreadNum);
//                                    runOnUiThread(() -> {
//                                        getViewModel().getModel().getAdapter().refreshItem(messageWindow, finalI);
//                                    });
//                                }
//                            }
//                            @Override
//                            public void onError(Object message) {
//
//                            }
//                        }, messageWindow.getMessageWindowId(), TimeUtil.getCurrentTime());
                    }
                });
            }
            @Override
            public void onError() {
                ToastUtil.error("加载失败");
            }
        }));
        return runnableList;
    }

    /**
     * 所有子线程成功处理完成后，会调用此方法
     */
    @Override
    protected void loadSuccess() {
        super.loadSuccess();
    }
}