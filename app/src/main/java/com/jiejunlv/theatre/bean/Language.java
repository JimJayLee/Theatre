package com.jiejunlv.theatre.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jiejunlv on 15/2/2018.
 */

public class Language {

    @SerializedName("iso_639_1")
    private String code;

    @SerializedName("english_name")
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
