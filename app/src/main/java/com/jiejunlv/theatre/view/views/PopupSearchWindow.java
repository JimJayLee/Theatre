package com.jiejunlv.theatre.view.views;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jiejunlv.theatre.R;
import com.jiejunlv.theatre.databinding.PopupSearchbarBinding;
import com.jiejunlv.theatre.util.UiUtil;
import com.jiejunlv.theatre.view.activities.SearchActivity;

/**
 * A popup window view for providing a search interface which shows on the toolbar.
 * Created by jiejunlv on 2/2/2018.
 */

public class PopupSearchWindow extends PopupWindow implements View.OnClickListener{


    private View parent;
    private int[] location;
    private boolean focusable;
    private Context mContext;
    private PopupSearchbarBinding mBinding;
    private PopupHistoryWindow mPopupHistory;

    PopupSearchWindow(Context context, PopupSearchbarBinding binding, View parent, int width, int height, int[] location, boolean focusable) {
        super(binding.getRoot(), width, height, focusable);

        this.mBinding = binding;
        this.parent = parent;
        this.location = location;
        this.focusable = focusable;
        this.mContext = context;
        setup();
        //setUpHistory(width, height);
    }

    private void setup() {
        setOutsideTouchable(true);
        setTouchable(true);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setFocusable(focusable);

        // Clear button
        mBinding.clearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBinding.setUserInput("");
            }
        });

        // Back button
        mBinding.backMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        // Set up the EditText search action listener
        mBinding.searchbarEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                boolean handled = false;
                if (i == EditorInfo.IME_ACTION_SEARCH && mBinding.getUserInput().length() > 0){
                    // Make an intent bundled with the query text
                    Intent intent = SearchActivity.makeIntent(mContext, mBinding.getUserInput());

                    // The TransitionAnimation needs activity reference
                    if (mContext instanceof Activity){
                        Activity activity = (Activity) mContext;
                        mContext.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(activity, parent, "searchbar").toBundle());
                    }

                    handled = true;
                    dismiss();
                }
                return handled;
            }
        });
    }

    private void setUpHistory(int width, int height) {
        mPopupHistory = PopupHistoryWindow.from(mContext)
                                .setWidthAndHeight(width, height*2)
                                .setParentView(parent)
                                .setFocusable()
                                .build();
        mPopupHistory.getBinding().historyTv1.setOnClickListener(this);
        //mPopupHistory.getBinding().historyTv2.setOnClickListener(this);
        mPopupHistory.getBinding().historyDel1.setOnClickListener(this);
        //mPopupHistory.getBinding().historyDel2.setOnClickListener(this);

    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (mPopupHistory != null && mPopupHistory.isShowing()){
            mPopupHistory.dismiss();
        }
        if (mContext instanceof Activity){
            Activity activity = (Activity) mContext;
            UiUtil.dimOutside(activity, false);
        }
    }

    public void show(){
        showAtLocation(parent, Gravity.TOP|Gravity.START, location[0], location[1]);
        UiUtil.showAnimation(mContext, mBinding.backMain, R.anim.icon_rotate_backward);

        if (mPopupHistory != null && !mPopupHistory.isShowing()){
            mPopupHistory.show();
        }

        if (mContext instanceof Activity){
            Activity activity = (Activity) mContext;
            UiUtil.dimOutside(activity, true);
        }
    }

    /**
     * This is for users' continuous experience in case user pops up a search bar with no text in the bar.
     * @param text The last query text.
     */
    public void setEtText(String text){
        mBinding.setUserInput(text);
    }

    public static Builder from(Context context){
        return new Builder(context);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.history_tv1:
                UiUtil.showToast(view.getContext(), (String) ((TextView) view).getText());
                break;
            //case R.id.history_tv2:
            //    UiUtil.showToast(view.getContext(), "TV2");
            //    break;
            case R.id.history_del1:
                UiUtil.showToast(view.getContext(), "DEL1");
                break;
            //case R.id.history_del2:
             //   UiUtil.showToast(view.getContext(), "DEL2");
              //  break;
        }
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

        public Builder setFocusable(){
            this.focusable = true;
            return this;
        }

        public PopupSearchWindow build(){
            PopupSearchbarBinding popupBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.popup_searchbar, null, false);
            return new PopupSearchWindow(mContext, popupBinding, parent, width, height, location, focusable);
        }
    }

}
