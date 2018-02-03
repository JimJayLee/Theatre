package com.jiejunlv.theatre.view.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jiejunlv.theatre.R;
import com.jiejunlv.theatre.bean.ItemData;
import com.jiejunlv.theatre.databinding.FragmentTabBinding;
import com.jiejunlv.theatre.util.UiUtil;
import com.jiejunlv.theatre.viewmodel.MainViewModel;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Tab base fragment
 * Created by jiejunlv on 1/2/2018.
 */

public class BaseFragment extends Fragment {

    private static final String ARG_TAB_TYPE = "ARG_TAB_TYPE";

    private CompositeDisposable mCompositeDisposable;
    private FragmentTabBinding mBinding;
    private MainViewModel mViewModel;


    public static BaseFragment newInstance(int type){
        Bundle args = new Bundle();
        args.putInt(ARG_TAB_TYPE, type);
        BaseFragment fragment = new BaseFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int type = getArguments().getInt(ARG_TAB_TYPE);
        mCompositeDisposable = new CompositeDisposable();
        mViewModel = new MainViewModel();
        mViewModel.typeChange(type);

    }

    private void bind() {
        mCompositeDisposable.add(
                        mViewModel
                        .getMovieData()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                new Consumer<List<ItemData>>() {
                                       @Override
                                       public void accept(List<ItemData> itemData) throws Exception {
                                           setData(itemData);
                                       }
                                   },
                                new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) throws Exception {
                                        throwable.printStackTrace();
                                        UiUtil.showToast(getContext(), throwable.getMessage());
                                    }
                                }));
    }

    private void setData(List<ItemData> itemData) {
        mBinding.setUpcomingData(itemData);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_tab, container, false);
        bind();
        return mBinding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
    }
}
