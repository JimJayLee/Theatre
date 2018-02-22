package com.jiejunlv.theatre.datamodel;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.jiejunlv.theatre.bean.DetailBean;
import com.jiejunlv.theatre.bean.ParamsBean;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;

/**
 * Methods needing implementing.
 * Created by jiejunlv on 31/1/2018.
 */

public interface IMoviesDataModel {

    @NonNull
    Observable<DataListResponse> getMovies(ParamsBean paramsBean);

    @Nullable
    List<String> getParamsFromType(int type);

    // Perform search action
    Observable<DataListResponse> searchQuery(ParamsBean paramsBean);

    @NonNull
    Observable<DetailBean> getDetail(ParamsBean paramsBean);
}
