package com.jiejunlv.theatre.view.activities;

import android.databinding.DataBindingUtil;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;

import com.jiejunlv.theatre.R;
import com.jiejunlv.theatre.databinding.ActivityMainBinding;

import com.jiejunlv.theatre.util.UiUtil;
import com.jiejunlv.theatre.view.adapter.MainTabAdapter;
import com.jiejunlv.theatre.view.views.PopupSearchWindow;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mBinding;


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
                mBinding.drawerLayoutMain.openDrawer(Gravity.START);
            }
        });

        // Setup the tabs
        mBinding.viewpagerMain.setAdapter(new MainTabAdapter(this, this.getSupportFragmentManager()));
        mBinding.tablayoutMain.setupWithViewPager(mBinding.viewpagerMain);

        // Pop up the search interface
        mBinding.logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // If the view is not showing completely, do not show the search window.
                if (UiUtil.isCover(view)){
                    return;
                }

                final int anchorLoc[] = new int[2];
                mBinding.toolbar.getLocationOnScreen(anchorLoc);
                PopupSearchWindow searchBar =  PopupSearchWindow
                        .from(MainActivity.this)
                        .setParentView(mBinding.toolbar)
                        .setWidthAndHeight(mBinding.toolbar.getWidth(), mBinding.toolbar.getHeight())
                        .setAnchorLocation(anchorLoc)
                        .setFocusable()
                        .build();

                searchBar.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        rotateHamburgerIcon();
                    }
                });
                searchBar.show();
            }
        });

    }

    /**
     * User dismisses the search popup window, rotate the hamburger icon for user experience somehow.
     */
    private void rotateHamburgerIcon(){
        UiUtil.showAnimation(this, mBinding.navHome, R.anim.icon_rotate_forward);
    }
}
