package com.jiejunlv.theatre.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.jiejunlv.theatre.BR;

/**
 * Store the searched text/
 * Created by jiejunlv on 13/2/2018.
 */

public class HistoryItem extends BaseObservable {

    private String searchText1;
    private String searchText2;

    @Bindable
    public String getSearchText1() {
        return searchText1;
    }

    public void setSearchText1(String searchText1) {
        this.searchText1 = searchText1;
        notifyPropertyChanged(BR.searchText1);
    }

    @Bindable
    public String getSearchText2() {
        return searchText2;
    }

    public void setSearchText2(String searchText2) {
        this.searchText2 = searchText2;
        notifyPropertyChanged(BR.searchText2);
    }

    public void add(String text){
        if (searchText1 != null){
            setSearchText2(searchText1);
        }
        setSearchText1(text);
    }
}
