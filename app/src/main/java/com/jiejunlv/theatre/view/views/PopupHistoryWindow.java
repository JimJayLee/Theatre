package com.jiejunlv.theatre.view.views;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import com.jiejunlv.theatre.R;
import com.jiejunlv.theatre.databinding.PopupHistoryBinding;

/**
 * Show a dropdown list below the search bar.
 * Created by jiejunlv on 13/2/2018.
 */

public class PopupHistoryWindow extends PopupWindow {

    private View parent;
    private PopupHistoryBinding mBinding;
    private Context mContext;

    PopupHistoryWindow(Context context, PopupHistoryBinding binding, View parent, int width, int height, boolean focusable) {
        super(binding.getRoot(), width, height, focusable);

        this.parent = parent;
        mBinding = binding;
        mContext = context;
        setUp();
    }

    private void setUp() {
        setOutsideTouchable(false);
        setTouchable(true);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setFocusable(false);
        setAnimationStyle(R.style.popup_history_pop_anim);
    }

    public void show(){
        showAsDropDown(parent);
    }

    public PopupHistoryBinding getBinding() {
        return mBinding;
    }

    public static Builder from(Context context){
        return new Builder(context);
    }


    public static class Builder {

        private Context mContext;
        private View parent;
        private int width;
        private int height;
        private boolean focusable;


        Builder(Context context) {
            mContext = context;
        }

        public Builder setParentView(View view) {
            parent = view;
            return this;
        }

        public Builder setWidthAndHeight(int width, int height) {
            this.height = height;
            this.width = width;
            return this;
        }


        public Builder setFocusable() {
            this.focusable = true;
            return this;
        }

        public PopupHistoryWindow build(){
            PopupHistoryBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.popup_history, null, false);
            return new PopupHistoryWindow(mContext, binding, parent, width, height, focusable);
        }

    }
}
