package com.jiejunlv.theatre.view.activities;


import android.databinding.DataBindingUtil;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.Gravity;
import android.view.View;

import com.jiejunlv.theatre.R;
import com.jiejunlv.theatre.databinding.ActivityMainBinding;

import com.jiejunlv.theatre.util.UiUtil;
import com.jiejunlv.theatre.view.PopupSearchWindow;
import com.jiejunlv.theatre.view.adapter.MainTabAdapter;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mBinding;
    private PopupSearchWindow mSearchBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setupViews();

    }

    private void setupViews(){

        // Setup a new home icon
        mBinding.navHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBinding.drawerLayoutMain.openDrawer(Gravity.END);
            }
        });

        // Setup the tabs
        mBinding.viewpagerMain.setAdapter(new MainTabAdapter(this, this.getSupportFragmentManager()));
        mBinding.tablayoutMain.setupWithViewPager(mBinding.viewpagerMain);

        // Pop up the search interface
        mBinding.logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mSearchBar == null){
                    final int anchorLoc[] = new int[2];
                    mBinding.toolbar.getLocationOnScreen(anchorLoc);
                    mSearchBar =  PopupSearchWindow
                            .from(MainActivity.this)
                            .setParentView(view)
                            .setWidthAndHeight(mBinding.toolbar.getWidth(), mBinding.toolbar.getHeight())
                            .setAnchorLocation(anchorLoc)
                            .setFocusable(true)
                            .build();
                }
                mSearchBar.show();
            }
        });

    }

    /**
     * User dismisses the search popup window, rotate the hamburger icon for user experience somehow.
     */
    public void rotateHamburgerIcon(){
        UiUtil.showAnimation(this, mBinding.navHome, R.anim.icon_rotate_forward);
    }

    /**
     * Destroy the search bar instance.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSearchBar = null;
    }

}