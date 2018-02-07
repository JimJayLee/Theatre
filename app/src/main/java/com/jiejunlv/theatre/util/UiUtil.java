package com.jiejunlv.theatre.util;

import android.app.Activity;
import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.os.IBinder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jiejunlv.theatre.bean.ItemData;
import com.jiejunlv.theatre.view.adapter.ItemsAdapter;

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


    @BindingAdapter({"bind:imageUrl","bind:error"})
    public static void loadImage(ImageView view, String url, Drawable error){
        Glide.with(view.getContext()).load(UriUtil.completeImgUrl(url)).error(error).into(view);
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
