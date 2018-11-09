package com.innovaocean.lastfm.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Album {

    public String name;
    public String artist;
    public String url;
    public List<Image> image;

    public class Image {
        @SerializedName("#text")
        public String text;
        public String size;
    }
}
