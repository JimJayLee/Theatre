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
import com.jiejunlv.theatre.databinding.FragmentTabBinding;
import com.jiejunlv.theatre.datamodel.DataListResponse;
import com.jiejunlv.theatre.datamodel.MoviesDataModel;
import com.jiejunlv.theatre.util.UiUtil;
import com.jiejunlv.theatre.util.UriUtil;
import com.jiejunlv.theatre.viewmodel.MainViewModel;


import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Tab fragment
 * Created by jiejunlv on 1/2/2018.
 */

public class TabsFragment extends Fragment {

    private static final String ARG_TAB_TYPE = "ARG_TAB_TYPE";

    private final static String MOVIE_PARAMS = "movie";
    private final static String TV_PARAMS = "tv";

    private static final String MOVIE_CHANNEL_UPCOMING = "upcoming" ;
    private static final String MOVIE_CHANNEL_NOW_PLAYING = "now_playing";
    private static final String MOVIE_CHANNEL_POPULAR = "popular";
    private static final String MOVIE_CHANNEL_TOP_RATED = "top_rated";

    private static final String TV_CHANNEL_ON_AIR = "on_the_air";
    private static final String TV_CHANNEL_AIRING = "airing_today";
    private static final String TV_CHANNEL_POPULAR = "popular";
    private static final String TV_CHANNEL_TOP_RATED = "top_rated";

    private static final String[] TITLES_MOVIE_TAB = {"Upcoming", "Now Playing", "Trending", "Top Rated"};
    private static final String[] TITLES_TV_TAB = {"On the Air", "Now Airing", "People Love to Watch", "Top top top!"};

    private CompositeDisposable mCompositeDisposable;
    private FragmentTabBinding mBinding;
    private MainViewModel mViewModel;


    public static TabsFragment newInstance(int type){
        Bundle args = new Bundle();
        args.putInt(ARG_TAB_TYPE, type);
        TabsFragment fragment = new TabsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCompositeDisposable = new CompositeDisposable();
        mViewModel = getViewModel();
    }

    private void bind() {
        mCompositeDisposable.add(
                        mViewModel
                        .getMovieData()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                new Consumer<DataListResponse>() {
                                    @Override
                                    public void accept(DataListResponse dataListResponse) throws Exception {
                                        setData(dataListResponse.getItemData(),
                                                dataListResponse.getParams());
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

    private void setData(List<ItemData> itemData, ParamsBean params) {
        //mBinding.setUpcomingData(itemData);
        String type = params.getType();
        String channel = params.getChannel();
        // Bind the media type to the data as a token
        switch (type){
            case MOVIE_PARAMS:
                setMovieData(itemData,channel);

                break;
            case TV_PARAMS:
                setTvData(itemData, channel);
                break;
        }

    }

    private void setTvData(List<ItemData> itemData, String channel) {
        switch (channel){
            case TV_CHANNEL_ON_AIR:
                mBinding.cardviewUpcoming.setData(itemData);
                mBinding.cardviewUpcoming.setTitle(channel);
                break;
            case TV_CHANNEL_AIRING:
                mBinding.cardviewPlayAir.setData(itemData);
                mBinding.cardviewPlayAir.setTitle(channel);
                break;
            case TV_CHANNEL_POPULAR:
                mBinding.cardviewPopular.setData(itemData);
                mBinding.cardviewPopular.setTitle(channel);
                break;
            case TV_CHANNEL_TOP_RATED:
                mBinding.cardviewTopRated.setData(itemData);
                mBinding.cardviewTopRated.setTitle(channel);
                break;
            default:
                break;
        }

    }

    private void setMovieData(List<ItemData> itemData, String channel) {
        switch (channel){
            case MOVIE_CHANNEL_UPCOMING:
                mBinding.cardviewUpcoming.setData(itemData);
                mBinding.cardviewUpcoming.setTitle(channel);
                break;
            case MOVIE_CHANNEL_NOW_PLAYING:
                mBinding.cardviewPlayAir.setData(itemData);
                mBinding.cardviewPlayAir.setTitle(channel);
                break;
            case MOVIE_CHANNEL_POPULAR:
                mBinding.cardviewPopular.setData(itemData);
                mBinding.cardviewPopular.setTitle(channel);
                break;
            case MOVIE_CHANNEL_TOP_RATED:
                mBinding.cardviewTopRated.setData(itemData);
                mBinding.cardviewTopRated.setTitle(channel);
                break;
            default:
                break;
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_tab, container, false);

        int type = getArguments().getInt(ARG_TAB_TYPE);
        bind();
        mViewModel.initTabData(type);

        return mBinding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
    }

    private MainViewModel getViewModel(){
        return ((CustomApplication) getActivity().getApplication()).getViewModel();
    }

}
