package com.innovaocean.lastfm.model;

import java.util.List;

public class SearchResponse {

    public AlbumMatches albummatches;

    public class AlbumMatches {

        public List<Album> album;

    }
}
