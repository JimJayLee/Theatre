package com.jiejunlv.theatre.view.views;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.jiejunlv.theatre.R;
import com.jiejunlv.theatre.databinding.PopupDetailBinding;
import com.jiejunlv.theatre.util.ScreenUtil;
import com.jiejunlv.theatre.util.UiUtil;

/**
 * Popup a window displaying the detail of a item.
 * Created by jiejunlv on 2/7/18.
 */

public class PopupDetailWindow extends PopupWindow {

    private View parent;
    private boolean focusable;
    private Context mContext;
    private PopupDetailBinding mBinding;


    PopupDetailWindow(
            Context context,
            PopupDetailBinding binding,
            View parent,
            int width,
            int height,
            boolean focusable) {
        super(binding.getRoot(), width, height, focusable);

        this.mContext = context;
        this.mBinding = binding;
        this.parent = parent;
        this.focusable = focusable;
        setup();
    }

    private void setup() {
        setOutsideTouchable(true);
        setTouchable(true);
        setFocusable(focusable);
        ColorDrawable drawable = new ColorDrawable(Color.BLACK);
        drawable.setAlpha(180);
        setBackgroundDrawable(drawable);

        // Make the outer space of popup window clickable to exit
        mBinding.cardviewPopup.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });

        mBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        // Add exit animation when dismiss called
        setAnimationStyle(R.style.popup_detail_fade_out);
    }

    public PopupDetailBinding getBinding() {
        return mBinding;
    }

    public void show(){
        showAtLocation(parent, Gravity.CENTER, 0, 0);
        UiUtil.showAnimation(mContext, mBinding.cardviewPopup, R.anim.popup_detail_scale_in);
    }

    public static Builder from(Context context){
        return new Builder(context);
    }

    public static class Builder{

        private Context mContext;
        private View parent;
        private int width;
        private int height;
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

        public Builder setFocusable(Boolean focusable){
            this.focusable = focusable;
            return this;
        }


        public PopupDetailWindow build(){
            PopupDetailBinding binding = DataBindingUtil.inflate(
                    LayoutInflater.from(mContext),
                    R.layout.popup_detail,
                    null,
                    false);
            return new PopupDetailWindow(
                    mContext,
                    binding,
                    parent,
                    width,
                    height,
                    focusable
                    );
        }

    }

}
