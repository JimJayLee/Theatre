package com.jiejunlv.theatre.bean;

import android.databinding.BaseObservable;

import com.google.gson.annotations.SerializedName;

import retrofit2.http.PUT;

/**
 * A item data means a movie or a Tv show.
 * Created by jiejunlv on 29/1/2018.
 */

public class ItemData extends BaseObservable {



    // For TV
    private String name;
    private String first_air_date;


    // For movie
    private String title;
    private String release_data;


    private String poster_path;
    private String backdrop_path;
    private String overview;
    private String vote_average;
    private String language;
    private String vote_count;
    private String id;



    public String getVote_count() {
        return vote_count;
    }

    public void setVote_count(String vote_count) {
        this.vote_count = vote_count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public void setFirst_air_date(String first_air_date) {
        this.first_air_date = first_air_date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public String getRelease_data() {
        return release_data;
    }

    public void setRelease_data(String release_data) {
        this.release_data = release_data;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String toString() {
        return "ItemData{" +
                "name='" + name + '\'' +
                ", poster_path='" + poster_path + '\'' +
                ", backdrop_path='" + backdrop_path + '\'' +
                ", overview='" + overview + '\'' +
                ", vote_average='" + vote_average + '\'' +
                ", release_data='" + release_data + '\'' +
                ", language='" + language + '\'' +
                ", vote_count='" + vote_count + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}