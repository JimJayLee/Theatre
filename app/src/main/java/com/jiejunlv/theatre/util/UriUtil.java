package com.jiejunlv.theatre.util;

import android.os.Bundle;

import com.jiejunlv.theatre.bean.ParamsBean;

/**
 * Construct necessary url for use.
 * Created by jiejunlv on 3/2/2018.
 */

public class UriUtil {

    private static final String KEY_PARAMS_BUNDLE = "keyParamsBundle";

    static final String BASE_TMDB_URL = "https://api.themoviedb.org/3/";
    private static final String BASE_TMDB_IMG_URL = "https://image.tmdb.org/t/p/";
    private static final String[] IMG_LOGO_SIZE = {
            "w45",
            "w92",
            "w154",
            "w185",
            "w300",
            "w500",
            "original"
    };

    public static final String[] IMG_POSTER_SIZE = {
            "w92",
            "w154",
            "w185",
            "w342",
            "w500",
            "w780",
            "original"
    };

    public static final String[] IMG_BACKDROP_SIZE = {
            "w300",
            "w780",
            "w1280",
            "original"
    };

    public static final String[] IMG_STILL_SIZE = {
            "w92",
            "w185",
            "w300",
            "original"
    };

    public static final String[] IMG_PROFILE_SIZE = {
            "w45",
            "w185",
            "h632",
            "original"
    };


    public UriUtil() {
        throw new AssertionError();
    }

    /**
     *  The urls fetched from Internet is not completed, so you have to combine some necessary elements, e.g. base url and image size.
     */
    public static String completeLogoUrl(String url){
        return BASE_TMDB_IMG_URL + IMG_LOGO_SIZE[4] + url;
    }

    public static String completeBackdropUrl(String url){
        return BASE_TMDB_IMG_URL + IMG_BACKDROP_SIZE[1] + url;
    }

    /**
     *  Hold a bunch of params information, such as data type, requested channel, and page.
     */
    public static Bundle bundleWith(ParamsBean paramsBean){
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_PARAMS_BUNDLE, paramsBean);
        return bundle;
    }

    public static ParamsBean getParamsFromBundle(Bundle bundle){
        return bundle.getParcelable(KEY_PARAMS_BUNDLE);
    }
}
