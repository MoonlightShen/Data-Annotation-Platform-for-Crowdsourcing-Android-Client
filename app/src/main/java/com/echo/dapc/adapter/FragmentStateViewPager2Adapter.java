package com.echo.dapc.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class FragmentStateViewPager2Adapter extends FragmentStateAdapter {

    private List<Fragment> mFragmentList = new ArrayList<>();

    private List<Long> mIds = new ArrayList<>();

    private AtomicLong mAtomicLong = new AtomicLong(0);

    public  FragmentStateViewPager2Adapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return mFragmentList.get(position);
    }

    public FragmentStateViewPager2Adapter addFragment(Fragment fragment) {
        if (fragment != null) {
            addFragment(mFragmentList.size(), fragment);
        }
        return this;
    }

    public FragmentStateViewPager2Adapter addFragment(int index, Fragment fragment) {
        if (fragment != null && index >= 0 && index <= mFragmentList.size()) {
            mFragmentList.add(index, fragment);
            mIds.add(index, getAtomicGeneratedId());
        }
        return this;
    }

    public FragmentStateViewPager2Adapter replaceFragment(int index, Fragment newFragment) {
        if (newFragment != null) {
            mFragmentList.set(index, newFragment);
            mIds.set(index, getAtomicGeneratedId());
        }
        return this;
    }

    public int replaceFragment(Fragment oldFragment, Fragment newFragment) {
        if (oldFragment != null && newFragment != null) {
            int index = mFragmentList.indexOf(oldFragment);
            if (index != -1) {
                mFragmentList.set(index, newFragment);
                mIds.set(index, getAtomicGeneratedId());
            }
            return index;
        }
        return -1;
    }

    public FragmentStateViewPager2Adapter removeFragment(int index) {
        if (index >= 0 && index < mFragmentList.size()) {
            mFragmentList.remove(index);
            mIds.remove(index);
        }
        return this;
    }

    private long getAtomicGeneratedId() {
        return mAtomicLong.incrementAndGet();
    }

    @Override
    public int getItemCount() {
        return mFragmentList.size();
    }

    public void clear() {
        mFragmentList.clear();
        mIds.clear();
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return mIds.get(position);
    }

    @Override
    public boolean containsItem(long itemId) {
        return mIds.contains(itemId);
    }
}
