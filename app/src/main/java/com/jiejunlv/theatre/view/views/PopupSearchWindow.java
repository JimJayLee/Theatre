package com.jiejunlv.theatre.view.views;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.LocalBroadcastManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import com.jiejunlv.theatre.R;
import com.jiejunlv.theatre.databinding.PopupSearchbarBinding;
import com.jiejunlv.theatre.util.UiUtil;

/**
 * A popup window view for providing a search interface which shows on the toolbar.
 * Created by jiejunlv on 2/2/2018.
 */

public class PopupSearchWindow extends PopupWindow {


    private View parent;
    private int[] location;
    private boolean focusable;
    private Context mContext;
    private PopupSearchbarBinding mBinding;

    PopupSearchWindow(Context context, PopupSearchbarBinding binding, View parent, int width, int height, int[] location, boolean focusable) {
        super(binding.getRoot(), width, height, focusable);

        this.mBinding = binding;
        this.parent = parent;
        this.location = location;
        this.focusable = focusable;
        this.mContext = context;
        setup();
    }

    private void setup() {
        setOutsideTouchable(true);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setFocusable(focusable);
    }


    public void show(){
        showAtLocation(parent, Gravity.TOP|Gravity.START, location[0], location[1]);
        UiUtil.showAnimation(mContext, mBinding.backMain, R.anim.icon_rotate_backward);
    }


    public static Builder from(Context context){
        return new Builder(context);
    }


    public static class Builder{

        private Context mContext;
        private View parent;
        private int width;
        private int height;
        private int[] location;
        private boolean focusable;


        Builder(Context context) {
            mContext = context;
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
            PopupSearchbarBinding popupBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.popup_searchbar, null, false);
            return new PopupSearchWindow(mContext, popupBinding, parent, width, height, location, focusable);
        }
    }

}
