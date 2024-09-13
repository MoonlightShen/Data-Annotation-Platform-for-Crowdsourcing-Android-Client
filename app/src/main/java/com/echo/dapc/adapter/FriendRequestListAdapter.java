package com.echo.dapc.adapter;

import android.os.AsyncTask;
import android.view.View;

import androidx.annotation.NonNull;

import com.echo.dapc.R;
import com.echo.dapc.adapter.viewholder.FriendApplicationViewHolder;
import com.echo.dapc.base.adapter.BaseRecycleListAdapter;
import com.echo.dapc.bean.entity.FriendRequest;
import com.echo.dapc.bean.enumeration.Gender;
import com.echo.dapc.bean.enumeration.StatusCode;
import com.echo.dapc.bean.enumeration.ValidationStatus;
import com.echo.dapc.util.business.AvatarUtil;
import com.echo.dapc.util.business.InfoUtil;
import com.echo.dapc.util.network.request.UserRequest;
import com.echo.dapc.util.network.response.user.UserNicknameRes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FriendRequestListAdapter extends BaseRecycleListAdapter<FriendRequest, FriendApplicationViewHolder> {

    public FriendRequestListAdapter() {
        this(new ArrayList<>());
    }

    public FriendRequestListAdapter(List<FriendRequest> data) {
        super(data);
    }

    @Override
    protected int getViewLayoutId(int viewType) {
        return R.layout.friend_request;
    }

    @NonNull
    @Override
    protected FriendApplicationViewHolder createViewHolder(@NonNull View view, int viewType) {
        return new FriendApplicationViewHolder(view);
    }

    @Override
    protected void bindViewHolder(@NonNull FriendRequest friendRequest, @NonNull FriendApplicationViewHolder viewHolder) {
        long id = InfoUtil.getUserId().longValue() == friendRequest.getRequesterId().longValue() ? friendRequest.getUserId() : friendRequest.getRequesterId();
        if (friendRequest.getRequesterId().longValue() == InfoUtil.getUserId() && friendRequest.getValidationStatus() == ValidationStatus.PENDING) {
            viewHolder.validationStatus.setText("等待对方处理");
        } else viewHolder.validationStatus.setText(friendRequest.getValidationStatus().getTag());
        AvatarUtil.loadUserAvatar(Gender.UNKNOWN, id, viewHolder.avatar, false);
        new UserNameQueryTask(viewHolder).execute(id);
        viewHolder.content.setText(friendRequest.getValidationContent());
    }

    private static class UserNameQueryTask extends AsyncTask<Long, Void, String> {
        private FriendApplicationViewHolder holder;

        public UserNameQueryTask(FriendApplicationViewHolder holder) {
            this.holder = holder;
        }

        @Override
        protected String doInBackground(Long... params) {
            long userId = params[0];

            String nickname = "";
            try {
                UserNicknameRes res = UserRequest.queryUserNickname(userId);
                if (StatusCode.SUCCESS.getCode().equals(res.code)) {
                    return res.data.nickname;
                }
            } catch (IOException ignored) {

            }

            return nickname;
        }

        @Override
        protected void onPostExecute(String result) {
            holder.title.setText(result);
        }
    }

}
