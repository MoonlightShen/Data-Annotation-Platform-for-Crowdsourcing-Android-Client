package com.echo.dapc.adapter;

import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import com.echo.dapc.R;
import com.echo.dapc.adapter.viewholder.message.ChatMessageViewHolder;
import com.echo.dapc.adapter.viewholder.message.TextMessageViewHolder;
import com.echo.dapc.base.ChatMessageListAdapter;
import com.echo.dapc.bean.entity.PrivateMessage;
import com.echo.dapc.bean.enumeration.Gender;
import com.echo.dapc.util.business.AvatarUtil;
import com.echo.dapc.util.business.InfoUtil;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class PrivateMessageListAdapter extends ChatMessageListAdapter<PrivateMessage> {

    public PrivateMessageListAdapter() {
        this(new ArrayList<>());
    }

    public PrivateMessageListAdapter(List<PrivateMessage> data) {
        super(data);
    }

    @Override
    protected boolean isMySend(int position) {
        return getItem(position).getSenderId().longValue() == InfoUtil.getUserId();
    }

    @Override
    protected boolean isMySend(PrivateMessage privateMessage) {
        return privateMessage.getSenderId().longValue() == InfoUtil.getUserId();
    }

    @NonNull
    @Override
    protected Comparator<? super PrivateMessage> createMessageComparator() {
        return (o1, o2) -> {
            if (o1.getSendTime() >o2.getSendTime()){
                return 1;
            } else if (o1.getSendTime().longValue() ==o2.getSendTime()) {
                return 0;
            }else {
                return -1;
            }
        };
    }

    @Override
    public int getItemViewType(int position) {
        return (isMySend(position) ? -1 : 1) * getItem(position).getContentType().getIndex();
    }

    @Override
    protected int getViewLayoutId(int viewType) {
        return ViewType.getByIndex(viewType).getLayoutId();
    }

    @NonNull
    @Override
    protected ChatMessageViewHolder createViewHolder(@NonNull View view, int viewType) {
        ChatMessageViewHolder viewHolder;
        try {
            Class<? extends ChatMessageViewHolder> cla = ViewType.getByIndex(viewType).getViewHolderClass();
            viewHolder = cla.getConstructor(View.class).newInstance(view);
        } catch (InvocationTargetException | IllegalAccessException | InstantiationException |
                 NoSuchMethodException e) {
            viewHolder = new ChatMessageViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    protected void bindViewHolder(@NonNull PrivateMessage privateMessage, @NonNull ChatMessageViewHolder viewHolder) {
        if (viewHolder instanceof TextMessageViewHolder) {
            AvatarUtil.loadUserAvatar(Gender.UNKNOWN, privateMessage.getSenderId(), ((TextMessageViewHolder) viewHolder).userAvatar, false);
            ((TextMessageViewHolder) viewHolder).messageText.setText(privateMessage.getContent());
        }
    }

    private enum ViewType {
        UNKNOWN(0, R.layout.message_text_receive, TextMessageViewHolder.class),
        TEXT_RECEIVE(1, R.layout.message_text_receive, TextMessageViewHolder.class),
        TEXT_SEND(-1, R.layout.message_text_send, TextMessageViewHolder.class),
        IMAGE_RECEIVE(2, R.layout.message_text_receive, TextMessageViewHolder.class),
        IMAGE_SEND(-2, R.layout.message_text_send, TextMessageViewHolder.class);

        final int index;
        final @LayoutRes int layoutId;
        final Class<? extends ChatMessageViewHolder> viewHolderClass;

        ViewType(int index, int layoutId, Class<? extends ChatMessageViewHolder> viewHolderClass) {
            this.index = index;
            this.layoutId = layoutId;
            this.viewHolderClass = viewHolderClass;
        }

        static @NonNull ViewType getByIndex(int index) {
            for (ViewType value : values()) {
                if (value.getIndex() == index) {
                    return value;
                }
            }
            return UNKNOWN;
        }

        public int getIndex() {
            return index;
        }

        public int getLayoutId() {
            return layoutId;
        }

        public Class<? extends ChatMessageViewHolder> getViewHolderClass() {
            return viewHolderClass;
        }
    }
}
