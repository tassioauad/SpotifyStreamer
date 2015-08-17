package br.com.tassioauad.spotifystreamer.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import br.com.tassioauad.spotifystreamer.R;
import br.com.tassioauad.spotifystreamer.SpotifyStreamerApplication;
import br.com.tassioauad.spotifystreamer.model.entity.Artist;
import br.com.tassioauad.spotifystreamer.model.entity.Track;
import br.com.tassioauad.spotifystreamer.presenter.SearchArtistPresenter;
import br.com.tassioauad.spotifystreamer.utils.dagger.SearchArtistModule;
import br.com.tassioauad.spotifystreamer.view.SearchArtistView;
import br.com.tassioauad.spotifystreamer.view.fragment.ListArtistFragment;
import br.com.tassioauad.spotifystreamer.view.fragment.ListTopTrackFragment;
import butterknife.Bind;
import butterknife.ButterKnife;

public class SearchArtistActivity extends AppCompatActivity implements SearchArtistView, ListArtistFragment.ListArtistListener, ListTopTrackFragment.ListTopTrackListener {

    private final String ARTIST_LIST_BUNDLE_KEY = "artistlistbundlekey";

    @Inject
    SearchArtistPresenter presenter;
    private List<Artist> artistList = new ArrayList<>();
    private Boolean twoPanes = true;

    @Bind(R.id.linearlayout_lostconnection) LinearLayout linearLayoutLostConnection;
    @Bind(R.id.linearlayout_letssearch) LinearLayout linearLayoutLetsFindArtist;
    @Bind(R.id.progressbar) ProgressBar progressBar;
    @Bind(R.id.framelayout_listartistfragment) FrameLayout frameLayoutListArtist;
    @Nullable @Bind(R.id.framelayout_listtoptrackfragment) FrameLayout frameLayoutListTopTrack;
    @Bind(R.id.toolbar) Toolbar toolbar;
    private MenuItem menuItemSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((SpotifyStreamerApplication) getApplication()).getObjectGraph().plus(new SearchArtistModule(this)).inject(this);
        setContentView(R.layout.activity_searchartist);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        if(frameLayoutListTopTrack == null) {
            twoPanes = false;
        }
        linearLayoutLetsFindArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuItemCompat.expandActionView(menuItemSearchView);
            }
        });
        if (savedInstanceState != null) {
            Artist[] artistArray = (Artist[]) savedInstanceState.getParcelableArray(ARTIST_LIST_BUNDLE_KEY);
            if(artistArray != null) {
                showArtists(Arrays.asList(artistArray));
                artistList = Arrays.asList(artistArray);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchartist, menu);

        menuItemSearchView = menu.findItem(R.id.search);
        final SearchView searchView = (SearchView) menuItemSearchView.getActionView();
        searchView.setQueryHint(getString(R.string.artist_search_hint));
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
    protected void onSaveInstanceState(Bundle outState) {
        if (artistList != null && artistList.size() > 0) {
            outState.putParcelableArray(ARTIST_LIST_BUNDLE_KEY,
                    artistList.toArray(new Artist[artistList.size()]));
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void showArtists(List<Artist> artistList) {
        this.artistList = artistList;
        linearLayoutLostConnection.setVisibility(View.GONE);
        linearLayoutLetsFindArtist.setVisibility(View.GONE);
        if(twoPanes) {
            frameLayoutListTopTrack.setVisibility(View.GONE);
        }
        frameLayoutListArtist.setVisibility(View.VISIBLE);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.framelayout_listartistfragment, ListArtistFragment.newInstance(this, artistList))
                .commit();
    }

    @Override
    public void anyArtistFounded() {
        linearLayoutLostConnection.setVisibility(View.GONE);
        linearLayoutLetsFindArtist.setVisibility(View.GONE);
        if(twoPanes) {
            frameLayoutListTopTrack.setVisibility(View.GONE);
        }
        frameLayoutListArtist.setVisibility(View.VISIBLE);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.framelayout_listartistfragment, ListArtistFragment.newInstance(this))
                .commit();
    }

    @Override
    public void lostConnection() {
        linearLayoutLostConnection.setVisibility(View.VISIBLE);
        linearLayoutLetsFindArtist.setVisibility(View.GONE);
        if(twoPanes) {
            frameLayoutListTopTrack.setVisibility(View.GONE);
        }
        frameLayoutListArtist.setVisibility(View.GONE);
    }

    @Override
    public void showLoadingWarn() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingWarn() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onArtistSelected(Artist artist) {
        if(twoPanes) {
            frameLayoutListTopTrack.setVisibility(View.VISIBLE);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.framelayout_listtoptrackfragment, ListTopTrackFragment.newInstance(artist))
                    .commit();
        } else {
            startActivity(SearchTopTrackActivity.newIntent(this, artist));
        }
    }

    @Override
    public void onTrackSelected(Track track) {

    }
}
