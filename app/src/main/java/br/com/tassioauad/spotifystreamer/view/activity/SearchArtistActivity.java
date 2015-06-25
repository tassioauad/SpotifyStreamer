package br.com.tassioauad.spotifystreamer.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.List;

import javax.inject.Inject;

import br.com.tassioauad.spotifystreamer.R;
import br.com.tassioauad.spotifystreamer.SpotifyStreamerApplication;
import br.com.tassioauad.spotifystreamer.model.entity.Artist;
import br.com.tassioauad.spotifystreamer.presenter.SearchArtistPresenter;
import br.com.tassioauad.spotifystreamer.utils.dagger.SearchArtistModule;
import br.com.tassioauad.spotifystreamer.view.SearchArtistView;
import br.com.tassioauad.spotifystreamer.view.listviewadapter.ArtistListViewAdapter;

public class SearchArtistActivity extends AppCompatActivity implements SearchArtistView {

    @Inject
    SearchArtistPresenter presenter;

    private LinearLayout linearLayoutLostConnection;
    private LinearLayout linearLayoutNotFound;
    private ListView listViewArtist;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((SpotifyStreamerApplication) getApplication()).getObjectGraph().plus(new SearchArtistModule(this)).inject(this);
        setContentView(R.layout.activity_searchartist);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        linearLayoutLostConnection = (LinearLayout) findViewById(R.id.linearlayout_lostconnection);
        linearLayoutNotFound = (LinearLayout) findViewById(R.id.linearlayout_notfound);
        listViewArtist = (ListView) findViewById(R.id.listview_artist);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchartist, menu);

        final SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String artistName) {
                presenter.searchByName(artistName);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public void showArtists(List<Artist> artists) {
        linearLayoutNotFound.setVisibility(View.GONE);
        linearLayoutLostConnection.setVisibility(View.GONE);
        listViewArtist.setVisibility(View.VISIBLE);
        listViewArtist.setAdapter(new ArtistListViewAdapter(this, artists));
    }

    @Override
    public void anyArtistFounded() {
        linearLayoutNotFound.setVisibility(View.VISIBLE);
        linearLayoutLostConnection.setVisibility(View.GONE);
        listViewArtist.setVisibility(View.GONE);
    }

    @Override
    public void lostConnection() {
        linearLayoutNotFound.setVisibility(View.GONE);
        linearLayoutLostConnection.setVisibility(View.VISIBLE);
        listViewArtist.setVisibility(View.GONE);
    }

    @Override
    public void showLoadingWarn() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingWarn() {
        progressBar.setVisibility(View.GONE);
    }
}
