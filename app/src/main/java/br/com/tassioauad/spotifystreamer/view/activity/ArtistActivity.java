package br.com.tassioauad.spotifystreamer.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.List;

import javax.inject.Inject;

import br.com.tassioauad.spotifystreamer.R;
import br.com.tassioauad.spotifystreamer.SpotifyStreamerApplication;
import br.com.tassioauad.spotifystreamer.model.entity.Artist;
import br.com.tassioauad.spotifystreamer.model.entity.Track;
import br.com.tassioauad.spotifystreamer.presenter.ArtistPresenter;
import br.com.tassioauad.spotifystreamer.utils.dagger.ArtistModule;
import br.com.tassioauad.spotifystreamer.view.ArtistView;
import br.com.tassioauad.spotifystreamer.view.fragment.ListArtistFragment;
import br.com.tassioauad.spotifystreamer.view.fragment.ListTopTrackFragment;
import br.com.tassioauad.spotifystreamer.view.fragment.PlayerFragment;
import butterknife.Bind;
import butterknife.ButterKnife;

public class ArtistActivity extends AppCompatActivity implements PlayerFragment.PlayerListener, ArtistView, ListArtistFragment.ListArtistListener, ListTopTrackFragment.ListTopTrackListener {

    private final String ARTIST_LIST_BUNDLE_KEY = "artistlistbundlekey";

    @Inject
    ArtistPresenter presenter;
    private Boolean twoPanes = true;
    @Bind(R.id.linearlayout_letssearch)
    LinearLayout linearLayoutLetsFindArtist;
    @Nullable
    @Bind(R.id.linearlayout_fragments)
    LinearLayout linearLayoutFragments;
    @Bind(R.id.framelayout_listartistfragment)
    FrameLayout frameLayoutListArtist;
    @Nullable
    @Bind(R.id.framelayout_listtoptrackfragment)
    FrameLayout frameLayoutListTopTrack;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    private MenuItem menuItemSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((SpotifyStreamerApplication) getApplication()).getObjectGraph().plus(new ArtistModule(this)).inject(this);
        setContentView(R.layout.activity_artist);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        if (frameLayoutListTopTrack == null) {
            twoPanes = false;
        }

        if(getSupportFragmentManager().findFragmentById(R.id.framelayout_listartistfragment) == null) {
            linearLayoutLetsFindArtist.setVisibility(View.VISIBLE);
            linearLayoutLetsFindArtist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MenuItemCompat.expandActionView(menuItemSearchView);
                }
            });
        } else {
            if(twoPanes) {
                linearLayoutFragments.setVisibility(View.VISIBLE);
            } else {
                frameLayoutListArtist.setVisibility(View.VISIBLE);
            }
        }

        presenter.init();
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
                linearLayoutLetsFindArtist.setVisibility(View.GONE);
                frameLayoutListArtist.setVisibility(View.VISIBLE);
                if (twoPanes) {
                    linearLayoutFragments.setVisibility(View.VISIBLE);
                    frameLayoutListTopTrack.setVisibility(View.GONE);
                }
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.framelayout_listartistfragment,
                                ListArtistFragment.newInstance(ArtistActivity.this, artistName))
                        .commit();
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
    public void onArtistSelected(Artist artist) {
        if (twoPanes) {
            frameLayoutListTopTrack.setVisibility(View.VISIBLE);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.framelayout_listtoptrackfragment, ListTopTrackFragment.newInstance(artist))
                    .commit();
        } else {
            startActivity(TopTrackActivity.newIntent(this, artist));
        }
    }

    @Override
    public void onTrackSelected(int position, List<Track> trackList) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        PlayerFragment playerFragment = PlayerFragment.newInstance(position, trackList);
        playerFragment.show(fragmentManager, "dialog");
    }

    @Override
    public void onPlay(Track track) {}
}
