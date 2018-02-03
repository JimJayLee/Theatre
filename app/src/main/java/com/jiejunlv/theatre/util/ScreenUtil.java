package com.jiejunlv.theatre.util;

import android.content.Context;

/**
 * Provide screen information.
 * Created by jiejunlv on 2/2/2018.
 */

public class ScreenUtil {

    public ScreenUtil() {
        throw new AssertionError();
    }

    public static int getStatusBarHeight(Context context){
        int height = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0){
            height = context.getResources().getDimensionPixelSize(resourceId);
        }
        return height;
    }


}
