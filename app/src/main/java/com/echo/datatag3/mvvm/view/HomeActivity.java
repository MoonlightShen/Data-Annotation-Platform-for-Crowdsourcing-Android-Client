package com.echo.datatag3.mvvm.view;

import android.annotation.SuppressLint;

import androidx.viewpager2.widget.MarginPageTransformer;

import com.echo.datatag3.R;
import com.echo.datatag3.adapter.FragmentStateViewPager2Adapter;
import com.echo.datatag3.base.BaseCustomActivity;
import com.echo.datatag3.databinding.ActivityHomeBinding;
import com.echo.datatag3.interfaces.callback.user.QueryUserNicknameCallback;
import com.echo.datatag3.mvvm.model.HomeModel;
import com.echo.datatag3.mvvm.view.fragment.CommunityFragment;
import com.echo.datatag3.mvvm.view.fragment.ContactsFragment;
import com.echo.datatag3.mvvm.view.fragment.WorkBenchPage;
import com.echo.datatag3.mvvm.viewmodel.HomeViewModel;
import com.echo.datatag3.util.business.AvatarUtil;
import com.echo.datatag3.util.business.InfoUtil;
import com.echo.datatag3.util.business.UserUtil;

import java.util.ArrayList;
import java.util.List;

import me.ibrahimsn.lib.OnItemSelectedListener;

public class HomeActivity extends BaseCustomActivity<HomeViewModel, HomeModel, ActivityHomeBinding> {

    @Override
    protected int getContentViewId() {
        return R.layout.activity_home;
    }

    @SuppressLint("RtlHardcoded")
    @Override
    protected void init() {
        getBinding().homeBmMenu.setOnItemSelectedListener((OnItemSelectedListener) i -> {
            getBinding().viewPager2.setCurrentItem(i);
            return true;
        });
        getBinding().homeRivUserAvatar.setOnClickListener(v -> {
            getBinding().leftSlideMenu.toggleLeftSlide();
        });

        FragmentStateViewPager2Adapter adapter = new FragmentStateViewPager2Adapter(this);
        adapter.addFragment(new WorkBenchPage());
        adapter.addFragment(new CommunityFragment());
        adapter.addFragment(new ContactsFragment());
        getBinding().viewPager2.setAdapter(adapter);
        getBinding().viewPager2.setOffscreenPageLimit(1);
        getBinding().viewPager2.setPageTransformer(new MarginPageTransformer(0));
        getBinding().viewPager2.setUserInputEnabled(false);

//        new BadgeView(getContext()).bindTarget(getBinding().systemNotices).setBadgeNumber(10).setOnDragStateChangedListener(new Badge.OnDragStateChangedListener() {
//            @Override
//            public void onDragStateChanged(int dragState, Badge badge, View targetView) {
//
//            }
//        });
        getViewModel().getModel().setUnReadNoticeNum(99);

        AvatarUtil.loadUserAvatar(InfoUtil.getGender(), InfoUtil.getUserId(), getBinding().homeRivUserAvatar, true);
        AvatarUtil.loadUserAvatar(InfoUtil.getGender(), InfoUtil.getUserId(), getBinding().userAvatar, true);
    }


    @Override
    protected List<Runnable> loadWithChildThread() {
        List<Runnable> runnableList = new ArrayList<>();
        runnableList.add(() -> {
            String name = InfoUtil.getUserName();
            if (name!=null&&!name.isEmpty()){
                getViewModel().getModel().setUserName(name);
            }else {
                UserUtil.queryUserNickname(new QueryUserNicknameCallback() {
                    @Override
                    public void onSuccess(String nickname) {
                        InfoUtil.refreshUserName(nickname);
                        getViewModel().getModel().setUserName(nickname);
                    }

                    @Override
                    public void onUserNotExist() {
                    }

                    @Override
                    public void onError(String error) {
                    }
                }, InfoUtil.getUserId());
            }
        });
        return runnableList;
    }

    @Override
    protected void loadSuccess() {
        super.loadSuccess();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getViewModel().getModel().setUserName(InfoUtil.getUserName());
        AvatarUtil.loadUserAvatar(InfoUtil.getGender(), InfoUtil.getUserId(), getBinding().homeRivUserAvatar, true);
        AvatarUtil.loadUserAvatar(InfoUtil.getGender(), InfoUtil.getUserId(), getBinding().userAvatar, true);
    }
}