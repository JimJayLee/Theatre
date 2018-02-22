package com.jiejunlv.theatre.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * A basic recycler view adapter with binding feature.
 * Created by jiejunlv on 31/1/2018.
 */

public abstract class BaseBindingAdapter<M, B extends ViewDataBinding> extends RecyclerView.Adapter{

    private Context mContext;
    List<M> items;

    BaseBindingAdapter(Context context){
        mContext = context;
        this.items = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        B binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), getLayoutId(viewType), parent, false);
        return new BaseViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        B binding = DataBindingUtil.getBinding(holder.itemView);
        onBindItem(binding, items.get(position));
    }



    protected abstract @LayoutRes int getLayoutId(int viewType);

    protected abstract void onBindItem(B binding, M item);
}
