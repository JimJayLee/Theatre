package com.jiejunlv.theatre.datamodel;

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

}
