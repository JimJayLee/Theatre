package com.jiejunlv.theatre.view.adapter;

import android.content.Context;

import com.jiejunlv.theatre.R;
import com.jiejunlv.theatre.bean.ItemData;
import com.jiejunlv.theatre.databinding.MovieItemBinding;

import java.util.List;

/**
 * Recycler view adapter for showing data in the main page.
 * Created by jiejunlv on 31/1/2018.
 */

public class MoviesAdapter extends BaseBindingAdapter<ItemData, MovieItemBinding> {


    public MoviesAdapter(Context context, List<ItemData> itemData) {
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
        binding.executePendingBindings();
    }


}
