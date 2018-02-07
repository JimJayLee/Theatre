package com.jiejunlv.theatre.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jiejunlv.theatre.R;
import com.jiejunlv.theatre.bean.ItemData;
import com.jiejunlv.theatre.databinding.MovieItemBinding;
import com.jiejunlv.theatre.util.UiUtil;

import java.util.List;

/**
 * Recycler view adapter for showing data in the main page.
 * Created by jiejunlv on 31/1/2018.
 */

public class ItemsAdapter extends BaseBindingAdapter<ItemData, MovieItemBinding> {

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
    }

    /**
     * ItemView clicked is called.
     * Open a activity to a item detail by sending its own ID.
     * @param view the root view.
     * @param detailId  Item ID.
     */
    public void checkDetail(View view, String detailId){
        UiUtil.showToast(view.getContext(), "Id: " + detailId);
    }
}
