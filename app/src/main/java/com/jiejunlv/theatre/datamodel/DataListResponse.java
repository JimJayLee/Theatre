package com.jiejunlv.theatre.datamodel;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.jiejunlv.theatre.bean.ItemData;
import com.jiejunlv.theatre.bean.ParamsBean;

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
    private ParamsBean params;


    public DataListResponse() {
        this.itemData = new ArrayList<>();
        this.params = new ParamsBean();
    }

    public static DataListResponse parseJSON(String response){
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(response, DataListResponse.class);
    }


    public List<ItemData> getItemData() {
        return itemData;
    }

    public void setParams(ParamsBean params){
        this.params = params;
    }

    public ParamsBean getParams(){
        return params;
    }

    public void setMediaType(String mediaType){
        for (ItemData item : itemData){
            item.setMedia_type(mediaType);
        }
    }

}
