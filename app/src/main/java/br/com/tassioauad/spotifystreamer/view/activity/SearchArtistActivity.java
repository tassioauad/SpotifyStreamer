package br.com.tassioauad.spotifystreamer.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import br.com.tassioauad.spotifystreamer.SpotifyStreamerApplication;
import br.com.tassioauad.spotifystreamer.dagger.SearchArtistModule;
import br.com.tassioauad.spotifystreamer.presenter.SearchArtistPresenter;
import br.com.tassioauad.spotifystreamer.view.SearchArtistView;

public class SearchArtistActivity extends AppCompatActivity implements SearchArtistView {

    @Inject
    SearchArtistPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((SpotifyStreamerApplication) getApplication()).getObjectGraph().plus(new SearchArtistModule(this)).inject(this);
    }
}
