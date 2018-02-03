package com.jiejunlv.theatre.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Utility for network connectivity.
 * Created by jiejunlv on 29/1/2018.
 */

public class NetworkUtil {

    public static boolean isNetworkAvailable(Context context){
        if (context != null){
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            assert cm != null;
            NetworkInfo info = cm.getActiveNetworkInfo();
            if (info != null){
                return info.isAvailable();
            }
        }
        return false;
    }
}
