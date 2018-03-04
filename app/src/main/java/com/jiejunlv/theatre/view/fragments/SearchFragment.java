package com.jiejunlv.theatre.view.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jiejunlv.theatre.CustomApplication;
import com.jiejunlv.theatre.R;
import com.jiejunlv.theatre.bean.ItemData;
import com.jiejunlv.theatre.bean.ParamsBean;
import com.jiejunlv.theatre.databinding.FragmentRecyclerviewBinding;
import com.jiejunlv.theatre.datamodel.DataListResponse;
import com.jiejunlv.theatre.viewmodel.MainViewModel;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * A fragment just shows a edit text in the toolbar.
 * Created by jiejunlv on 27/2/2018.
 */

public class SearchFragment extends Fragment {

    public static final String ARG_QUERY_TEXT = "ArgQueryText";

    private CompositeDisposable mCompositeDisposable;
    private MainViewModel mViewModel;
    private FragmentRecyclerviewBinding mBinding;

    public static SearchFragment newInstance(String queryText){
        Bundle bundle =  new Bundle();
        bundle.putString(ARG_QUERY_TEXT, queryText);
        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCompositeDisposable = new CompositeDisposable();
        mViewModel = getViewModel();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_recyclerview, container, false);
        return mBinding.getRoot();
    }

    private void setup() {
        String query = getArguments().getString(ARG_QUERY_TEXT);
        ParamsBean params = new ParamsBean();
        params.setType("multi");
        params.setQueryText(query);
        params.setPage(1);
        mViewModel.searchQuery(params);
    }

    @Override
    public void onStart() {
        super.onStart();
        bind();
        setup();
    }

    private void bind() {
        mCompositeDisposable.add(
                mViewModel
                .makeSearchQuery()
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DataListResponse>() {
                    @Override
                    public void accept(DataListResponse dataListResponse) throws Exception {
                        setData(dataListResponse.getItemData());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                })
        );
    }

    @Override
    public void onStop() {
        super.onStop();
        mCompositeDisposable.clear();
    }

    private void setData(List<ItemData> itemData) {
        mBinding.setData(itemData);
    }

    private MainViewModel getViewModel(){
        return ((CustomApplication) getActivity().getApplication()).getViewModel();
    }
}
