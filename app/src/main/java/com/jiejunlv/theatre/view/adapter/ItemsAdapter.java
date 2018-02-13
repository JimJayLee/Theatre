package com.jiejunlv.theatre.view.adapter;

import android.content.Context;
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
        binding.setItemAdapter(this);
        binding.executePendingBindings();
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
                            .setFocusable()
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
