package com.echo.datatag3.base;

import androidx.annotation.NonNull;

import com.echo.datatag3.adapter.viewholder.message.ChatMessageViewHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class ChatMessageListAdapter<T> extends BaseRecycleViewAdapter<T, ChatMessageViewHolder> {


    public ChatMessageListAdapter() {
        this(new ArrayList<>());
    }

    public ChatMessageListAdapter(List<T> data) {
        super(data);
    }

    protected abstract boolean isMySend(int position);
    protected abstract boolean isMySend(T t);

    protected abstract @NonNull Comparator<? super T> createMessageComparator();

    private final Comparator<? super T> messageComparator = createMessageComparator();

    @Override
    public void refreshItems(Collection<T> collection) {
        if (collection != null) {
            getData().clear();
            List<T> messages = (List<T>) collection;
            Collections.sort(messages, messageComparator);
            getData().addAll(collection);
            notifyDataSetChanged();
        }
    }

    @Override
    public void addItems(Collection<T> collection) {
        if (collection != null) {
            List<T> newMessages = (List<T>) collection;
            Collections.sort(newMessages, messageComparator);
            int minIndex = getItemCount();
            for (T newItem:newMessages){
                int index = Collections.binarySearch(getData(), newItem, messageComparator);
                if (index < 0) {
                    index = -index - 1;
                }
                minIndex = Math.min(minIndex, index);
                getData().add(index, newItem);
            }
            notifyItemRangeChanged(minIndex, getItemCount());
        }
    }

}
