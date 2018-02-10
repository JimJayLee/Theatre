package com.jiejunlv.theatre.util;

import android.app.Activity;
import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.IBinder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jiejunlv.theatre.bean.ItemData;
import com.jiejunlv.theatre.view.adapter.ItemsAdapter;

import java.io.File;
import java.io.FileOutputStream;
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

    public static String upperCaseFirstCharacter(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }
    return new String(ch);
    }

    public static String completeLanguage(String code){
        switch (code){
            case "en" :
                return "English";
            case "ja" :
                return "Japanese";
            case "kr":
                return "Korea";
        }
        return null;
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
        activity = null;
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
        setHorizontalRVdata(recyclerView, data);
    }


    private static void setHorizontalRVdata(RecyclerView recyclerView, List<ItemData> data){
        if (data != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.HORIZONTAL, false));
            recyclerView.setAdapter(new ItemsAdapter(recyclerView.getContext(), data));
        }
    }
}
