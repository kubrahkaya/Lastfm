package com.innovaocean.lastfm.searchList;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.innovaocean.lastfm.App;
import com.innovaocean.lastfm.LastfmRepository;
import com.innovaocean.lastfm.R;
import com.innovaocean.lastfm.databinding.ActivityMainBinding;
import com.innovaocean.lastfm.databinding.ItemAlbumBinding;
import com.innovaocean.lastfm.model.Album;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements MainActivityPresenter.View {

    private ActivityMainBinding binding;
    private MainActivityPresenter presenter;
    private AlbumAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        adapter = new AlbumAdapter();
        binding.albums.setLayoutManager(new LinearLayoutManager(this));
        binding.albums.setAdapter(adapter);

        LastfmRepository repository = ((App) getApplicationContext()).getRepository();
        presenter = new MainActivityPresenter(this, repository, Schedulers.io(), AndroidSchedulers.mainThread());

        binding.searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                presenter.start(editable.toString());
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.stop();
    }

    @Override
    public void showAlbums(List<Album> albums) {
        binding.albums.setVisibility(View.VISIBLE);
        binding.errorView.setVisibility(View.GONE);
        adapter.setAlbumList(albums);
    }

    @Override
    public void displayAlbumDetail(Album album) {
        Intent webIntent = new Intent(Intent.ACTION_VIEW);
        webIntent.setData(Uri.parse(album.url));
        startActivity(webIntent);
    }

    @Override
    public void showError() {
        binding.albums.setVisibility(View.GONE);
        binding.errorView.setVisibility(View.VISIBLE);
    }

    private class AlbumAdapter extends RecyclerView.Adapter<ViewHolder> {

        List<Album> albumList = new ArrayList<>();

        void setAlbumList(List<Album> albumList) {
            this.albumList = albumList;
            notifyDataSetChanged();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext())
                                      .inflate(R.layout.item_album, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Album album = albumList.get(position);
            holder.binding.albumName.setText(album.name);
            holder.binding.albumArtist.setText(album.artist);

            if (album.image != null && album.image.get(2).text != null) {
                Glide.with(MainActivity.this)
                    .load(album.image.get(2).text)
                    .into(holder.binding.albumImage);
            }

            holder.itemView.setOnClickListener(view -> {
                presenter.albumClicked(album);
            });
        }

        @Override
        public int getItemCount() {
            return albumList.size();
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ItemAlbumBinding binding;

        ViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}
