package com.innovaocean.lastfm;

import android.app.Application;

public class App extends Application {

    private LastfmRepository repository;

    @Override
    public void onCreate() {
        super.onCreate();
        repository = new LastfmRepository();
    }

    public LastfmRepository getRepository() {
        return repository;
    }

    public void setRepository(LastfmRepository repository) {
        this.repository = repository;
    }
}
