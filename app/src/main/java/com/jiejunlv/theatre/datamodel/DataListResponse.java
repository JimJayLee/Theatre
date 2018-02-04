package com.jiejunlv.theatre.datamodel;


import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.jiejunlv.theatre.bean.ItemData;

import java.util.ArrayList;
import java.util.List;


/**
 * For Gson serialization in order to match the "results" .
 * Created by jiejunlv on 31/1/2018.
 */

public class DataListResponse {



    @SerializedName("results")
    private List<ItemData> itemData;

    // Bundle of holding information about the query detail.
    private Bundle detailBundle;


    public DataListResponse() {
        this.itemData = new ArrayList<>();
        this.detailBundle = new Bundle();
    }

    public static DataListResponse parseJSON(String response){
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(response, DataListResponse.class);
    }


    public List<ItemData> getItemData() {
        return itemData;
    }

    public void setBundle(Bundle bundle){
        this.detailBundle = bundle;
    }

    public Bundle getBundle(){
        return detailBundle;
    }



}
