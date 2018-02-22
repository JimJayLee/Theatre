package com.jiejunlv.theatre.datamodel;

import com.jiejunlv.theatre.bean.DetailBean;
import com.jiejunlv.theatre.bean.Language;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Movie database api service.
 * Created by jiejunlv on 31/1/2018.
 */

public interface IMovieDataBaseService {

    @GET("{type}/{channel}?language=en-US")
    Observable<DataListResponse> listItems(@Path("type") String type,
                                            @Path("channel") String channel,
                                            @Query("page") int page,
                                            @Query("api_key") String apiKey);

    @GET("search/{media}?language=en-US")
    Observable<DataListResponse> searchItems(@Path("media") String mediaType,
                                             @Query("query") String query,
                                             @Query("page") int page,
                                             @Query("api_key") String apiKey);

    @GET("configuration/languages")
    Observable<List<Language>> getLanguages(@Query("api_key") String apiKey);

    @GET("{type}/{id}?language=en-US&append_to_response=videos")
    Observable<DetailBean> getDetail(@Path("type") String type,
                                     @Path("id") String itemId,
                                     @Query("api_key") String apiKey);
}
