package com.jiejunlv.theatre.util;

import android.app.Activity;
import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.os.IBinder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jiejunlv.theatre.bean.ItemData;
import com.jiejunlv.theatre.view.adapter.MoviesAdapter;

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


    @BindingAdapter({"bind:imageUrl","bind:error"})
    public static void loadImage(ImageView view, String url, Drawable error){
        Glide.with(view.getContext()).load(UriUtil.completeImgUrl(url)).error(error).into(view);
    }

    @BindingAdapter("bind:upcomingData")
    public static void setUpcomingData(RecyclerView recyclerView, List<ItemData> data){
        setHorizontalRVdata(recyclerView, data);
    }

    @BindingAdapter("bind:topRateData")
    public static void setTopRatedData(RecyclerView recyclerView, List<ItemData> data){
        setHorizontalRVdata(recyclerView, data);
    }

    private static void setHorizontalRVdata(RecyclerView recyclerView, List<ItemData> data){
        if (data != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.HORIZONTAL, false));
            recyclerView.setAdapter(new MoviesAdapter(recyclerView.getContext(), data));
        }
    }
}
