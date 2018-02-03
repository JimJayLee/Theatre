package com.jiejunlv.theatre.view.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jiejunlv.theatre.view.fragments.BaseFragment;

/**
 * Tab fragment adapter.
 * Created by jiejunlv on 1/2/2018.
 */

public class MainTabAdapter extends FragmentPagerAdapter {

    private static final int PAGE_COUNT = 2;

    private Context mContext;


    public MainTabAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        return BaseFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Movies";
            case 1:
                return "TV Shows";
            default:
                return "Error";
        }
    }
}
