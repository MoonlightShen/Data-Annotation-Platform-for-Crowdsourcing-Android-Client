package com.echo.datatag3.mvvm.view.fragment;

import com.echo.datatag3.R;
import com.echo.datatag3.base.BaseFragment;
import com.echo.datatag3.databinding.FragmentCommunityBinding;
import com.echo.datatag3.mvvm.model.CommunityModel;
import com.echo.datatag3.mvvm.viewmodel.CommunityViewModel;

public class CommunityFragment extends BaseFragment<CommunityViewModel, CommunityModel, FragmentCommunityBinding> {

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_community;
    }

    @Override
    protected void init() {

    }

}