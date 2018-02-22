package com.jiejunlv.theatre.util;

import android.app.Activity;
import android.content.Context;
import android.databinding.BindingAdapter;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.IBinder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jiejunlv.theatre.CustomApplication;
import com.jiejunlv.theatre.bean.ItemData;
import com.jiejunlv.theatre.view.adapter.ItemsAdapter;
import com.jiejunlv.theatre.view.adapter.QuerySearchAdapter;

import java.util.HashMap;
import java.util.List;

/**
 * Utility for user interface.
 * Created by jiejunlv on 1/2/2018.
 */

public class UiUtil {


    private UiUtil(){
        throw new AssertionError();
    }

    public static void hideKeyboard(Activity activity, IBinder windowToken){
        InputMethodManager mgr = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(windowToken, 0);
    }

    // View is instance of edit text view usually.
    public static void showKeyBoard(Activity activity, EditText editText){
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        InputMethodManager mgr = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.showSoftInput(editText, 0);
    }

    public static void showToast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showAnimation(Context context, View view, int animId){
        view.startAnimation(AnimationUtils.loadAnimation(context,animId));
    }

    // Bc the title is using the params from URL, it has to be clear from the underline between words.
    public static String textClear(String title){
        if (title != null) {
            return upperCaseFirstCharacter(title.replace('_', ' '));
        }
        return null;
    }

    private static String upperCaseFirstCharacter(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }
    return new String(ch);
    }

    public static String completeLanguage(String code){
        HashMap<String, String> languageMap = CustomApplication.getApplicationInstance().getLanguageMap();
        return !languageMap.isEmpty() ? languageMap.get(code) : upperCaseFirstCharacter(code);
    }

    /**
     * Adjust a view or drawable 's brightness
     * @param brightness RGB offset， negative for dim.
     * @return ColorFilter
     */
    public static ColorMatrixColorFilter setBrightness(int brightness){
        ColorMatrix matrix = new ColorMatrix();
        matrix.set(new float[]{
                1, 0, 0, 0,
                brightness, 0, 1, 0,
                0, brightness, 0, 0,
                1, 0, brightness, 0,
                0, 0, 1, 0});
        return new ColorMatrixColorFilter(matrix);
    }

    /**
     * Blur a bitmap using FastBlur.
     * @param bkg background bitmap needs blurring.
     * @param view the view where your bitmap embeds.
     * @return a blurred bitmap.
     */
    public static Bitmap blur(Bitmap bkg, View view) {

        float radius = 4; // the blur radius
        float scaleFactor = 8; // Quality is not necessary for a blurred bitmap, scaling it down to 1/8 for quick blurring.

        // Because popupWindow's root view calls getWidth()/getHeight() returns < 0, can't figure it out
        // But the width and height are for sure the screen's width and height.
        Bitmap overlay = Bitmap.createBitmap(bkg, 0, 0, (int)(bkg.getWidth() / scaleFactor), (int)(bkg.getHeight() / scaleFactor));
        Canvas canvas = new Canvas(overlay);
        // We just want canvas draw the whole bitmap, translation is no need.
        //canvas.translate(-view.getLeft()/scaleFactor, -view.getTop()/scaleFactor);
        canvas.scale(1 / scaleFactor, 1 / scaleFactor);
        Paint paint = new Paint();
        paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(bkg, 0, 0, paint);
        overlay = FastBlur.doBlur(overlay, (int)radius, true);
        return overlay;
    }

    /**
     * A helper method for popup window, dimming the outer space of the window.
     * @param activity Activity is necessary, not context.
     * @param isDim Dim or not.
     */
    public static void dimOutside(Activity activity, boolean isDim){
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        if (isDim){
            lp.alpha = 0.7f;
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }else {
            lp.alpha = 1.0f;
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }
        activity.getWindow().setAttributes(lp);
    }

    @BindingAdapter("bind:backdropUrl")
    public static void loadBackdrop(ImageView v, String url){
        Glide.with(v.getContext()).load(UriUtil.completeBackdropUrl(url)).into(v);
    }

    @BindingAdapter({"bind:logoUrl","bind:error"})
    public static void loadLogo(ImageView view, String url, Drawable error){
        Glide.with(view.getContext()).load(UriUtil.completeLogoUrl(url)).error(error).into(view);
    }

    @BindingAdapter("bind:data")
    public static void setData(RecyclerView recyclerView, List<ItemData> data){
        if (data != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.HORIZONTAL, false));
            recyclerView.setAdapter(new ItemsAdapter(recyclerView.getContext(), data));
    }}

    @BindingAdapter("bind:searchData")
    public static void setSearchData(RecyclerView recyclerView, List<ItemData> data){
        if (data != null){
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.VERTICAL, false));
            recyclerView.setAdapter(new QuerySearchAdapter(recyclerView.getContext(), data));
        }
    }


}
