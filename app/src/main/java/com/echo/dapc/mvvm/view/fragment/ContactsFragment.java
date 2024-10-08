package com.echo.dapc.mvvm.view.fragment;

import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.echo.dapc.R;
import com.echo.dapc.adapter.FragmentStateViewPager2Adapter;
import com.echo.dapc.base.BaseFragment;
import com.echo.dapc.databinding.FragmentContactsBinding;
import com.echo.dapc.mvvm.model.ContactsModel;
import com.echo.dapc.mvvm.viewmodel.ContactsViewModel;
import com.google.android.material.tabs.TabLayout;

public class ContactsFragment extends BaseFragment<ContactsViewModel, ContactsModel, FragmentContactsBinding> {


    @Override
    protected int getContentViewId() {
        return R.layout.fragment_contacts;
    }

    @Override
    protected void init() {
        FragmentStateViewPager2Adapter adapter = new FragmentStateViewPager2Adapter(requireActivity());
        adapter.addFragment(new FriendListFragment());
        adapter.addFragment(new TeamListFragment());
        getBinding().contactsList.setAdapter(adapter);
        getBinding().contactsList.setOffscreenPageLimit(2);
        getBinding().contactsList.setPageTransformer(new MarginPageTransformer(0));
        getBinding().contactsList.setUserInputEnabled(true);
        getBinding().contactsTypeTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                getBinding().contactsList.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        getBinding().contactsList.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                getBinding().contactsTypeTab.selectTab(getBinding().contactsTypeTab.getTabAt(position));
            }
        });
    }

}