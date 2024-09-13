package com.echo.dapc.mvvm.view.fragment;

import com.echo.dapc.R;
import com.echo.dapc.base.BaseFragment;
import com.echo.dapc.databinding.FragmentCommunityBinding;
import com.echo.dapc.mvvm.model.CommunityModel;
import com.echo.dapc.mvvm.viewmodel.CommunityViewModel;

public class CommunityFragment extends BaseFragment<CommunityViewModel, CommunityModel, FragmentCommunityBinding> {

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_community;
    }

    @Override
    protected void init() {

    }

}