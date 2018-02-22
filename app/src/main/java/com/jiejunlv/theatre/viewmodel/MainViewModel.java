package com.jiejunlv.theatre.viewmodel;

import android.support.annotation.NonNull;
import android.util.Log;

import com.jiejunlv.theatre.bean.DetailBean;
import com.jiejunlv.theatre.bean.ParamsBean;
import com.jiejunlv.theatre.datamodel.DataListResponse;
import com.jiejunlv.theatre.datamodel.IMoviesDataModel;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.ReplaySubject;

/**
 * Prepare data for UI.
 * Created by jiejunlv on 31/1/2018.
 */

public class MainViewModel {

    @NonNull
    private final IMoviesDataModel moviesDataModel;

    private final ReplaySubject<ParamsBean> replaySubject = ReplaySubject.create();

    public MainViewModel(@NonNull IMoviesDataModel model) {
        this.moviesDataModel = model;
    }

    @NonNull
    public Observable<DataListResponse> getMovieData(){
        return replaySubject
                .observeOn(Schedulers.io())
                .flatMap(new Function<ParamsBean, Observable<DataListResponse>>() {
                    @Override
                    public Observable<DataListResponse> apply(ParamsBean requestData) throws Exception {
                        return moviesDataModel.getMovies(requestData);
                    }
                });
    }


    public void initTabData(int type){

        List<String> channels = moviesDataModel.getParamsFromType(type);
        if (channels != null && channels.size() == 5) {
            String dataType = channels.remove(channels.size() - 1);
            for (String channel : channels) {
                ParamsBean paramsBean = new ParamsBean();
                paramsBean.setType(dataType);
                paramsBean.setChannel(channel);
                paramsBean.setPage(1);
                replaySubject.onNext(
                        paramsBean);
            }
        }
    }

    @NonNull
    public Observable<DataListResponse> makeSearchQuery(){
        Log.i("MainView", "MAKE QUERY");
        return replaySubject
                .observeOn(Schedulers.io())
                .flatMap(new Function<ParamsBean, Observable<DataListResponse>>() {
                    @Override
                    public Observable<DataListResponse> apply(ParamsBean paramsBean) throws Exception {
                        return moviesDataModel.searchQuery(paramsBean);
                    }
                });
    }

    public void searchQuery(ParamsBean paramsBean){
        replaySubject.onNext(paramsBean);
    }

    public Observable<DetailBean> getDetail(){
        return replaySubject
                .observeOn(Schedulers.io())
                .flatMap(new Function<ParamsBean, Observable<DetailBean>>() {
                    @Override
                    public Observable<DetailBean> apply(ParamsBean paramsBean) throws Exception {
                        return moviesDataModel.getDetail(paramsBean);
                    }
                });
    }

    public void detailQuery(ParamsBean paramsBean){
        replaySubject.onNext(paramsBean);
    }
}


