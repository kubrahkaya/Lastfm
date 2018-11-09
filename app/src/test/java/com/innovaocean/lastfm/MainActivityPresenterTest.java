package com.innovaocean.lastfm;

import com.innovaocean.lastfm.model.Album;
import com.innovaocean.lastfm.searchList.MainActivityPresenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MainActivityPresenterTest {

    @Mock
    MainActivityPresenter.View view;

    @Mock
    LastfmRepository repository;

    MainActivityPresenter presenter;

    @Before
    public void setUp() {
        presenter = new MainActivityPresenter(view, repository, Schedulers.trampoline(), Schedulers.trampoline());
    }

    @Test
    public void testShowAlbums() {
        Album album = new Album();

        List<Album> albumList = new ArrayList<>();
        albumList.add(album);

        when(repository.getAlbums("believe")).thenReturn(Single.just(albumList));
        presenter.start("believe");

        verify(view, times(1)).showAlbums(albumList);
    }

    @Test
    public void testDisplayAlbumDetail() {

        Album album = new Album();
        presenter.albumClicked(album);

        verify(view, times(1)).displayAlbumDetail(album);
    }

    @Test
    public void testShowError() {
        when(repository.getAlbums(any())).thenReturn(Single.error(new Exception()));
        presenter.start(any());

        verify(view, times(1)).showError();
    }
}
