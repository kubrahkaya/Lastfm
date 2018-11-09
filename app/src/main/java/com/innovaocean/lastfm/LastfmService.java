package com.innovaocean.lastfm;

import com.innovaocean.lastfm.model.ApiResponse;
import com.innovaocean.lastfm.model.SearchResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LastfmService {

    String API_KEY_PARAMETER = "&api_key=a18f8e37cebce589cab7cb90315aebec&format=json";

    @GET("/2.0/?method=album.search&" + API_KEY_PARAMETER)
    Single<ApiResponse<SearchResponse>> searchAlbums(@Query("album") String searchTerm);
}
