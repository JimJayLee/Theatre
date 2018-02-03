package com.jiejunlv.theatre.datamodel;

import android.support.annotation.NonNull;

import com.jiejunlv.theatre.bean.ItemData;
import com.jiejunlv.theatre.util.ApiKey;
import com.jiejunlv.theatre.util.RetrofitFactory;

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
    private static final String API_KEY = ApiKey.API_KEY; // You have to replace your own key.


    private final static int MOVIE = 0;  // Tab position
    private final static int TV = 1;


    @NonNull
    @Override
    public Observable<List<ItemData>> getMovies(int type) {
        return getFromMovieDatabase(type);
    }

    @NonNull
    private Observable<List<ItemData>> getFromMovieDatabase(int type){
        String dataType;
        switch (type){
            case MOVIE:
                dataType = "movie";
                break;
            case TV:
                dataType = "tv";
                break;
            default:
                dataType = "error";
        }

        Observable<DataListResponse> call = RetrofitFactory.getService().listItems(dataType, "top_rated",1, API_KEY);


        return call.map(new Function<DataListResponse, List<ItemData>>() {
            @Override
            public List<ItemData> apply(DataListResponse dataListResponse) throws Exception {
                return dataListResponse.getItemData();
            }
        });
    }

}
