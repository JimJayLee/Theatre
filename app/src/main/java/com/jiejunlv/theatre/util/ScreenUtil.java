package com.jiejunlv.theatre.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;

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

    /**
     * Get screen width and height in pixels
     * @param context
     * @return
     */
    public static int[] getScreenWidthAndHeight(Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return new int[]{metrics.widthPixels, metrics.heightPixels};
    }


    public static int getNavigationBarHeight(Context context){
        int height = 0;
        if (context.getResources().getIdentifier("config_showNavigationBar", "bool", "android") != 0){
            int resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
            height = context.getResources().getDimensionPixelSize(resourceId);
        }
        return height;
    }

    /**
     * Capture the current screen.
     */
    public static Bitmap captureScreen(View rootView){

        int width = rootView.getWidth();
        // Remove the status bar and navigation bar on the screenshot
        int height = rootView.getHeight() - getStatusBarHeight(rootView.getContext()) - getNavigationBarHeight(rootView.getContext());
        rootView.setDrawingCacheEnabled(true);
        rootView.buildDrawingCache();
        Bitmap screenshot = Bitmap.createBitmap(
                rootView.getDrawingCache(),
                0,
                getStatusBarHeight(rootView.getContext()), // Starting pixel y
                width,
                height);

        rootView.setDrawingCacheEnabled(false);
        rootView.destroyDrawingCache();

        return screenshot;
    }

}
