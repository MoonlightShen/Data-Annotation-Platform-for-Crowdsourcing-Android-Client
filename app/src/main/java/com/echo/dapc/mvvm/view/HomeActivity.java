package com.echo.dapc.mvvm.view;

import androidx.viewpager2.widget.MarginPageTransformer;

import com.echo.dapc.R;
import com.echo.dapc.adapter.FragmentStateViewPager2Adapter;
import com.echo.dapc.base.activity.BaseDataBindingActivity;
import com.echo.dapc.databinding.ActivityHomeBinding;
import com.echo.dapc.interfaces.callback.user.QueryUserNicknameCallback;
import com.echo.dapc.mvvm.model.HomeModel;
import com.echo.dapc.mvvm.view.fragment.CommunityFragment;
import com.echo.dapc.mvvm.view.fragment.ContactsFragment;
import com.echo.dapc.mvvm.view.fragment.WorkBenchFragment;
import com.echo.dapc.mvvm.viewmodel.HomeViewModel;
import com.echo.dapc.util.business.AvatarUtil;
import com.echo.dapc.util.business.InfoUtil;
import com.echo.dapc.util.business.UserUtil;

import me.ibrahimsn.lib.OnItemSelectedListener;

public class HomeActivity extends BaseDataBindingActivity<HomeViewModel, HomeModel, ActivityHomeBinding> {

    @Override
    protected void onRestart() {
        super.onRestart();
        getViewModel().getModel().setUserName(InfoUtil.getUserName());
        AvatarUtil.loadUserAvatar(InfoUtil.getGender(), InfoUtil.getUserId(), getBinding().homeRivUserAvatar, true);
        AvatarUtil.loadUserAvatar(InfoUtil.getGender(), InfoUtil.getUserId(), getBinding().userAvatar, true);
    }

    /**
     * 获取当前Activity绑定的布局文件的ID
     *
     * @return 如R.layout.activity_xxx.xml
     */
    @Override
    protected int getUILayoutId() {
        return R.layout.activity_home;
    }

    /**
     * 在主线程的初始化操作，如为一些特殊组件添加监听器
     */
    @Override
    protected void initOnMainThread() {
        getBinding().homeBmMenu.setOnItemSelectedListener((OnItemSelectedListener) i -> {
            getBinding().viewPager2.setCurrentItem(i);
            return true;
        });
        getBinding().homeRivUserAvatar.setOnClickListener(v -> {
            getBinding().leftSlideMenu.toggleLeftSlide();
        });

        FragmentStateViewPager2Adapter adapter = new FragmentStateViewPager2Adapter(this);
        adapter.addFragment(new WorkBenchFragment());
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

    /**
     * 在子线程的初始化操作，如从网络请求中加载数据
     */
    @Override
    protected void initInBackground() {
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
    }
}