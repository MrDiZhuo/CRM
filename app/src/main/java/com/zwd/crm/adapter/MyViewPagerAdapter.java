package com.zwd.crm.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import com.zwd.crm.base.BaseFragment;

import java.util.ArrayList;

/**
 * Created by asus-pc on 2016/10/27.
 */


public class MyViewPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<String> title;
    ArrayList<BaseFragment> fragments;

    public MyViewPagerAdapter(FragmentManager fm, ArrayList<String> title, ArrayList<BaseFragment> fragments) {
        super(fm);
        this.title = title;
        this.fragments = fragments;
    }

    public MyViewPagerAdapter(FragmentManager fm, ArrayList<BaseFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
    @Override
    public CharSequence getPageTitle(int position){
        return title.get(position);
    }
}
