package br.com.tassioauad.spotifystreamer.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import java.util.List;

import javax.inject.Inject;

import br.com.tassioauad.spotifystreamer.R;
import br.com.tassioauad.spotifystreamer.SpotifyStreamerApplication;
import br.com.tassioauad.spotifystreamer.utils.dagger.SearchArtistModule;
import br.com.tassioauad.spotifystreamer.model.entity.Artist;
import br.com.tassioauad.spotifystreamer.presenter.SearchArtistPresenter;
import br.com.tassioauad.spotifystreamer.view.SearchArtistView;
import br.com.tassioauad.spotifystreamer.view.listviewadapter.ArtistListViewAdapter;

public class SearchArtistActivity extends AppCompatActivity implements SearchArtistView {

    @Inject
    SearchArtistPresenter presenter;

    private ListView listViewArtist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((SpotifyStreamerApplication) getApplication()).getObjectGraph().plus(new SearchArtistModule(this)).inject(this);
        setContentView(R.layout.activity_searchartist);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listViewArtist = (ListView) findViewById(R.id.listview_artist);

        //presenter.searchByName("Dave");
    }

    @Override
    public void showArtists(List<Artist> artists) {
        listViewArtist.setAdapter(new ArtistListViewAdapter(this, artists));
    }

    @Override
    public void anyArtistFounded() {

    }

    @Override
    public void lostConnection() {

    }

    @Override
    public void showLoadingWarn() {

    }

    @Override
    public void hideLoadingWarn() {

    }
}
