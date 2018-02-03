package com.jiejunlv.theatre.datamodel;

import android.support.annotation.NonNull;

import com.jiejunlv.theatre.bean.ItemData;

import java.util.List;

import io.reactivex.Observable;

/**
 * Methods needing implementing.
 * Created by jiejunlv on 31/1/2018.
 */

public interface IMoviesDataModel {

    @NonNull
    Observable<List<ItemData>> getMovies(int type);
}
