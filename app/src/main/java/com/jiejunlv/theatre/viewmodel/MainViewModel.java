package com.jiejunlv.theatre.viewmodel;

import android.support.annotation.NonNull;

import com.jiejunlv.theatre.bean.ItemData;
import com.jiejunlv.theatre.datamodel.IMoviesDataModel;
import com.jiejunlv.theatre.datamodel.MoviesDataModel;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Prepare data for UI.
 * Created by jiejunlv on 31/1/2018.
 */

public class MainViewModel {

    @NonNull
    private final IMoviesDataModel moviesDataModel;

    private final BehaviorSubject<Integer> behaviorSubject = BehaviorSubject.create();

    public MainViewModel() {
        this.moviesDataModel = new MoviesDataModel();
    }

    @NonNull
    public Observable<List<ItemData>> getMovieData(){
        return behaviorSubject
                .observeOn(Schedulers.io())
                .flatMap(new Function<Integer, Observable<List<ItemData>>>() {
                    @Override
                    public Observable<List<ItemData>> apply(Integer integer) throws Exception {
                        return moviesDataModel.getMovies(integer);
                    }
                });
    }

    public void typeChange(int type){
        behaviorSubject.onNext(type);
    }

}


