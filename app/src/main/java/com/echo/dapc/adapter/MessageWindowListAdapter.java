package com.echo.dapc.adapter;

import android.view.View;

import androidx.annotation.NonNull;

import com.echo.dapc.R;
import com.echo.dapc.adapter.viewholder.MessageWindowViewHolder;
import com.echo.dapc.base.adapter.BaseRecycleListAdapter;
import com.echo.dapc.bean.enumeration.Gender;
import com.echo.dapc.bean.enumeration.MessageWindowType;
import com.echo.dapc.bean.logic.MessageWindow;
import com.echo.dapc.util.business.AvatarUtil;
import com.echo.dapc.util.system.TimeUtil;
import com.echo.dapc.widget.badgeview.BadgeView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageWindowListAdapter extends BaseRecycleListAdapter<MessageWindow, MessageWindowViewHolder> {
    public MessageWindowListAdapter() {
        this(new ArrayList<>());
    }

    private final Map<Long, Integer> map;

    @Override
    protected int getViewLayoutId(int viewType) {
        return R.layout.message_window;
    }

    @NonNull
    @Override
    protected MessageWindowViewHolder createViewHolder(@NonNull View view, int viewType) {
        return new MessageWindowViewHolder(view);
    }

    public MessageWindowListAdapter(List<MessageWindow> data) {
        super(data);
        map = new HashMap<>();
        for (int i = 0; i < data.size(); i++) {
            map.put(data.get(i).getMessageWindowId(), i);
        }
    }

    public Integer getPosition(Long id) {
        if (map != null && map.containsKey(id)) {
            return map.get(id);
        } else {
            return null;
        }
    }

    @Override
    protected void bindViewHolder(@NonNull MessageWindow messageWindow, @NonNull MessageWindowViewHolder viewHolder) {
        if (messageWindow.getMessageWindowId() != null) {
            if (messageWindow.getType() == MessageWindowType.FRIEND)
                AvatarUtil.loadUserAvatar(Gender.UNKNOWN, messageWindow.getMessageWindowId(), viewHolder.avatar, false);
            else
                AvatarUtil.loadTeamAvatar(messageWindow.getMessageWindowId(), viewHolder.avatar, false);
        }
        if (messageWindow.getName() != null) viewHolder.name.setText(messageWindow.getName());
        if (messageWindow.getLastSendTime() != null) {
            if (TimeUtil.countingDaysUntilNow(messageWindow.getLastSendTime()) > 7) {
                viewHolder.lastMessageTime.setText(TimeUtil.format(messageWindow.getLastSendTime(), TimeUtil.mmDD));
            } else if (TimeUtil.countingDaysUntilNow(messageWindow.getLastSendTime()) > 1) {
                viewHolder.lastMessageTime.setText(TimeUtil.format(messageWindow.getLastSendTime(), TimeUtil.eee));
            } else {
                viewHolder.lastMessageTime.setText(TimeUtil.format(messageWindow.getLastSendTime(), TimeUtil.hhMM));
            }
        }
        if (messageWindow.getContent() != null) {
            if (messageWindow.getContentType() != null) {
                switch (messageWindow.getContentType()) {
                    case TEXT -> viewHolder.lastMessage.setText(messageWindow.getContent());
                    case FILE -> viewHolder.lastMessage.setText("[文件]");
                    case IMAGE -> viewHolder.lastMessage.setText("[图片]");
                    case VIDEO -> viewHolder.lastMessage.setText("[视频]");
                    case VOICE -> viewHolder.lastMessage.setText("[语音]");
                    default -> viewHolder.lastMessage.setText("");
                }
            }
        }
        if (messageWindow.getUnreadMessagesNum() != null && messageWindow.getUnreadMessagesNum() != 0L) {
            new BadgeView(viewHolder.itemView.getContext()).bindTarget(viewHolder.unreadNotices).setBadgeNumber(messageWindow.getUnreadMessagesNum()).setOnDragStateChangedListener((dragState, badge, targetView) -> {
            });
        }
    }


}
