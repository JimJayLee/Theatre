package com.jiejunlv.theatre.datamodel;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import io.reactivex.Observable;

/**
 * Methods needing implementing.
 * Created by jiejunlv on 31/1/2018.
 */

public interface IMoviesDataModel {

    @NonNull
    Observable<DataListResponse> getMovies(Bundle bundle);

    @Nullable
    List<String> getParamsFromType(int type);
}
