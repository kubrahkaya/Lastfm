package com.innovaocean.lastfm;

import com.innovaocean.lastfm.model.Album;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class LastfmRepository {

    private LastfmService lastfmService;

    LastfmRepository() {
        lastfmService = new Retrofit.Builder()
            .baseUrl("http://ws.audioscrobbler.com")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(LastfmService.class);
    }

    public Single<List<Album>> getAlbums(String searchText) {
        return lastfmService.searchAlbums(searchText)
            .map(apiResponse -> apiResponse.results.albummatches.album);
    }
}
