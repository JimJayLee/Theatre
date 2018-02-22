package com.jiejunlv.theatre;

import android.app.Application;
import android.support.annotation.NonNull;
import android.util.Log;

import com.jiejunlv.theatre.bean.Language;
import com.jiejunlv.theatre.datamodel.IMoviesDataModel;
import com.jiejunlv.theatre.datamodel.MoviesDataModel;
import com.jiejunlv.theatre.util.ApiKey;
import com.jiejunlv.theatre.util.RetrofitFactory;
import com.jiejunlv.theatre.viewmodel.MainViewModel;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Prepare persistent data for app.
 * Created by jiejunlv on 19/2/2018.
 */

public class CustomApplication extends Application {

    private static CustomApplication instance;

    private HashMap<String, String> mLanguageMap;

    private IMoviesDataModel mDataModel;

    @Override
    public void onCreate() {
        instance = this;

        super.onCreate();
        mDataModel = new MoviesDataModel();
        prepareLanguage();
    }

    @NonNull
    public IMoviesDataModel getDataModel() {
        return mDataModel;
    }

    @NonNull
    public MainViewModel getViewModel(){
        return new MainViewModel(getDataModel());
    }

    @NonNull
    public HashMap<String, String> getLanguageMap(){
        return mLanguageMap;
    }

    private void prepareLanguage(){
        RetrofitFactory
                .getService()
                .getLanguages(ApiKey.API_KEY)
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<Language>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Language> languages) {
                        mLanguageMap = feedInMap(languages);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("CustomApplication", e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private HashMap<String, String> feedInMap(List<Language> languages) {
        HashMap<String, String> languageMap = new HashMap<>();
        for (Language language : languages) {
            languageMap.put(language.getCode(), language.getName());
        }
        return languageMap;
    }

    public static CustomApplication getApplicationInstance(){
        return instance;
    }
}
