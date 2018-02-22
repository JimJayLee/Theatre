package com.jiejunlv.theatre.view.adapter;

import android.content.Context;

import com.jiejunlv.theatre.R;
import com.jiejunlv.theatre.bean.ItemData;
import com.jiejunlv.theatre.databinding.SearchDataItemBinding;

import java.util.List;

/**
 * For the recyclerview in the SearchActivity.
 * Created by jiejunlv on 15/2/2018.
 */

public class QuerySearchAdapter extends BaseBindingAdapter<ItemData, SearchDataItemBinding> {
    public QuerySearchAdapter(Context context, List<ItemData> itemData) {
        super(context);
        items.addAll(itemData);
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.search_data_item;
    }

    @Override
    protected void onBindItem(SearchDataItemBinding binding, ItemData item) {
        binding.setResult(item);
        binding.executePendingBindings();
    }

}
