package com.jiejunlv.theatre.datamodel;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.jiejunlv.theatre.util.ApiKey;
import com.jiejunlv.theatre.util.RetrofitFactory;
import com.jiejunlv.theatre.util.UriUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Provide interfaces to ViewModels.
 * Created by jiejunlv on 31/1/2018.
 */

public class MoviesDataModel implements IMoviesDataModel  {


    /**
     * Place your api key here
     */
    private static final String API_KEY = ApiKey.API_KEY; // You have to replace with your own key.


    private final static int MOVIE = 0;  // Tab position
    private final static int TV = 1;
    private final static String MOVIE_PARAMS = "movie";
    private final static String TV_PARAMS = "tv";

    private String[] movieChannels = {"upcoming", "now_playing", "popular", "top_rated"};
    private String[] tvChannels = {"on_the_air", "airing_today", "popular", "top_rated"};



    @NonNull
    @Override
    public Observable<DataListResponse> getMovies(Bundle requestData) {
        return getFromMovieDatabase(requestData);
    }

    @NonNull
    private Observable<DataListResponse> getFromMovieDatabase(final Bundle requestData){

        Observable<DataListResponse> call = RetrofitFactory
                .getService()
                .listItems(
                        UriUtil.getTypeFromBundel(requestData),
                        UriUtil.getChannelFromBundle(requestData),
                        1,
                        API_KEY);


        return call.map(new Function<DataListResponse, DataListResponse>() {
            @Override
            public DataListResponse apply(DataListResponse dataListResponse) throws Exception {
                dataListResponse.setBundle(requestData);
                return dataListResponse;
            }
        });
    }

    @Nullable
    public List<String> getParamsFromType(int type){
        List<String> channels = null;
        // Append the type string to channel
        switch(type){
            case MOVIE:
                channels = new ArrayList<>(Arrays.asList(movieChannels));
                channels.add(MOVIE_PARAMS);
                break;
            case TV:
                channels =new ArrayList<>(Arrays.asList(tvChannels));
                channels.add(TV_PARAMS);
                break;
        }
        return channels;
    }

}
