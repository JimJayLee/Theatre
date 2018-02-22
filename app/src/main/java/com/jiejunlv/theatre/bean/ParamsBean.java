package com.jiejunlv.theatre.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jiejunlv on 15/2/2018.
 */

public class ParamsBean implements Parcelable {

    private String type; // Movie, TV or Both
    private String channel; // Top-rated, popular, upcoming etc...
    private String language; // en, ja, ch etc....
    private int page; // Default value is 1.
    private String queryText;
    private String ItemId; // To identify what item is.
    private int[] genreIds; // Classify the items.

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getQueryText() {
        return queryText;
    }

    public void setQueryText(String queryText) {
        this.queryText = queryText;
    }

    public String getItemId() {
        return ItemId;
    }

    public void setItemId(String itemId) {
        ItemId = itemId;
    }

    public int[] getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(int[] genreIds) {
        this.genreIds = genreIds;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.type);
        dest.writeString(this.channel);
        dest.writeString(this.language);
        dest.writeInt(this.page);
        dest.writeString(this.queryText);
        dest.writeString(this.ItemId);
        dest.writeIntArray(this.genreIds);
    }

    public ParamsBean() {
    }

    protected ParamsBean(Parcel in) {
        this.type = in.readString();
        this.channel = in.readString();
        this.language = in.readString();
        this.page = in.readInt();
        this.queryText = in.readString();
        this.ItemId = in.readString();
        this.genreIds = in.createIntArray();
    }

    public static final Parcelable.Creator<ParamsBean> CREATOR = new Parcelable.Creator<ParamsBean>() {
        @Override
        public ParamsBean createFromParcel(Parcel source) {
            return new ParamsBean(source);
        }

        @Override
        public ParamsBean[] newArray(int size) {
            return new ParamsBean[size];
        }
    };
}
