package com.innovaocean.lastfm.searchList;

import com.innovaocean.lastfm.LastfmRepository;
import com.innovaocean.lastfm.model.Album;

import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;

public class MainActivityPresenter {

    private View view;
    private CompositeDisposable disposables;
    private LastfmRepository repository;
    private Scheduler uiScheduler;
    private Scheduler ioScheduler;

    MainActivityPresenter(View view, LastfmRepository repository, Scheduler ioScheduler, Scheduler uiScheduler) {
        this.view = view;
        this.repository = repository;
        this.disposables = new CompositeDisposable();
        this.ioScheduler = ioScheduler;
        this.uiScheduler = uiScheduler;
    }

    void start(String searchText) {
        loadAlbums(searchText);
    }

    void stop() {
        disposables.dispose();
    }

    void albumClicked(Album album) {
        view.displayAlbumDetail(album);
    }

    private void loadAlbums(String searchText) {
        repository.getAlbums(searchText)
            .subscribeOn(ioScheduler)
            .observeOn(uiScheduler)
            .subscribe(albums -> {
                if (albums.size() > 0) {
                    view.showAlbums(albums);
                } else {
                    view.showError();
                }
            }, throwable -> {
                throwable.printStackTrace();
                view.showError();
            });
    }

    interface View {

        void showAlbums(List<Album> albums);

        void displayAlbumDetail(Album album);

        void showError();
    }
}
