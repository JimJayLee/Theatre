package com.jiejunlv.theatre.viewmodel;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.jiejunlv.theatre.datamodel.DataListResponse;
import com.jiejunlv.theatre.datamodel.IMoviesDataModel;
import com.jiejunlv.theatre.datamodel.MoviesDataModel;
import com.jiejunlv.theatre.util.UriUtil;

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

    private final ReplaySubject<Bundle> replaySubject = ReplaySubject.create();

    public MainViewModel() {
        this.moviesDataModel = new MoviesDataModel();
    }

    @NonNull
    public Observable<DataListResponse> getMovieData(){
        return replaySubject
                .observeOn(Schedulers.io())
                .flatMap(new Function<Bundle, Observable<DataListResponse>>() {
                    @Override
                    public Observable<DataListResponse> apply(Bundle requestData) throws Exception {
                        return moviesDataModel.getMovies(requestData);
                    }
                });
    }


    public void initTabData(int type){

        List<String> channels = moviesDataModel.getParamsFromType(type);
        if (channels != null && channels.size() == 5) {
            String dataType = channels.remove(channels.size() - 1);
            for (String channel : channels) {
                replaySubject.onNext(
                        UriUtil.bundleWith(
                                dataType,
                                channel,
                                1));
            }
        }

    }

}


