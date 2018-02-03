package com.jiejunlv.theatre.util;

import com.jiejunlv.theatre.datamodel.IMovieDataBaseService;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Factory class to Retrofit
 * Created by jiejunlv on 31/1/2018.
 */

public class RetrofitFactory {



    private static IMovieDataBaseService service = new Retrofit.Builder()
            .baseUrl(UriUtil.BASE_TMDB_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(IMovieDataBaseService.class);

    public static IMovieDataBaseService getService(){
        return service;
    }
}
