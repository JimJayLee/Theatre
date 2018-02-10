package com.jiejunlv.theatre.view.adapter;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.jiejunlv.theatre.R;
import com.jiejunlv.theatre.bean.ItemData;
import com.jiejunlv.theatre.databinding.MovieItemBinding;
import com.jiejunlv.theatre.view.views.PopupDetailWindow;


import java.util.List;

/**
 * Recycler view adapter for showing data in the main page.
 * Created by jiejunlv on 31/1/2018.
 */

public class ItemsAdapter extends BaseBindingAdapter<ItemData, MovieItemBinding> {

    private PopupDetailWindow mPopupDetail;

    public ItemsAdapter(Context context, List<ItemData> itemData) {
        super(context);
        items.addAll(itemData);
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.movie_item;
    }

    @Override
    protected void onBindItem(MovieItemBinding binding, ItemData item) {
        binding.setItemData(item);
        // Bind listener to the root view.
        binding.setItemAdapter(this);
        binding.executePendingBindings();

        binding.getRoot().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        Log.i("ITEM", "ACTION DOWN");
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.i("ITEM", "ACTION UP");
                        if (mPopupDetail.isShowing()){
                            mPopupDetail.dismiss();
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.i("ITEM", "ACTION MOVE");
                        break;
                }
                return false;
            }
        });
    }

    /**
     * ItemView clicked is called.
     * Open a activity to a item detail by sending its own data.
     * @param view the root view.
     * @param itemData  Item data.
     */
    public boolean popDetail(View view, ItemData itemData){

        if (mPopupDetail == null) {
            mPopupDetail = PopupDetailWindow.from(view.getContext())
                            .setParentView(view)
                            .setWidthAndHeight(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
                            .setFocusable(true)
                            .build();
        }
        // Update the data
        mPopupDetail.getBinding().setItemData(itemData);

        if (!mPopupDetail.isShowing()){
            mPopupDetail.show();
        }
        return true;
    }
}
