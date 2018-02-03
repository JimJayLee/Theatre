package com.jiejunlv.theatre.datamodel;


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

    public DataListResponse() {
        this.itemData = new ArrayList<>();
    }

    public static DataListResponse parseJSON(String response){
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(response, DataListResponse.class);
    }


    List<ItemData> getItemData() {
        return itemData;
    }
}
