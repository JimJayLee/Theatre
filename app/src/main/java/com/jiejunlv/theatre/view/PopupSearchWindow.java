package com.jiejunlv.theatre.view;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.jiejunlv.theatre.R;
import com.jiejunlv.theatre.databinding.PopupSearchbarBinding;
import com.jiejunlv.theatre.util.UiUtil;
import com.jiejunlv.theatre.view.activities.MainActivity;

/**
 * A popup window view for providing a search interface which shows on the toolbar.
 * Created by jiejunlv on 2/2/2018.
 */

public class PopupSearchWindow extends PopupWindow {


    private View parent;
    private int[] location;
    private boolean focusable;
    private Activity mActivity;
    private PopupSearchbarBinding mBinding;


    PopupSearchWindow(Activity activity, PopupSearchbarBinding binding, View parent, int width, int height, int[] location, boolean focusable) {
        super(binding.getRoot(), width, height, focusable);

        this.mBinding = binding;
        this.parent = parent;
        this.location = location;
        this.focusable = focusable;
        this.mActivity = activity;
        setup();
    }

    private void setup() {
        setOutsideTouchable(true);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setFocusable(focusable);
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                dimOutside(false);
                if (mActivity instanceof MainActivity) {
                    MainActivity activity = (MainActivity) mActivity;
                    activity.rotateHamburgerIcon();
                }
            }
        });
    }


    public void show(){
        showAtLocation(parent, Gravity.TOP|Gravity.START, location[0], location[1]);
        UiUtil.showAnimation(mActivity, mBinding.backMain, R.anim.icon_rotate_backward);
        dimOutside(true);
    }

    private void dimOutside(boolean isDim){
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        if (isDim){
            lp.alpha = 0.7f;
            mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }else {
            lp.alpha = 1.0f;
            mActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }
        mActivity.getWindow().setAttributes(lp);

    }



    public static Builder from(Activity activity){
        return new Builder(activity);
    }


    public static class Builder{

        private Activity mActivity;
        private View parent;
        private int width;
        private int height;
        private int[] location;
        private boolean focusable;


        Builder(Activity activity) {
            mActivity = activity;
        }

        public Builder setParentView(View view){
            parent = view;
            return this;
        }

        public Builder setWidthAndHeight(int width, int height){
            this.height = height;
            this.width = width;
            return this;
        }

        public Builder setAnchorLocation(int[] location){
            this.location = location;
            return this;
        }

        public Builder setFocusable(Boolean focusable){
            this.focusable = focusable;
            return this;
        }

        public PopupSearchWindow build(){
            PopupSearchbarBinding popupBinding = DataBindingUtil.inflate(LayoutInflater.from(mActivity), R.layout.popup_searchbar, null, false);

            return new PopupSearchWindow(mActivity, popupBinding, parent, width, height, location, focusable);
        }


    }

}
