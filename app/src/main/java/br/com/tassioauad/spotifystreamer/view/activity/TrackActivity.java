package br.com.tassioauad.spotifystreamer.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.tassioauad.spotifystreamer.R;
import br.com.tassioauad.spotifystreamer.SpotifyStreamerApplication;
import br.com.tassioauad.spotifystreamer.model.entity.Artist;
import br.com.tassioauad.spotifystreamer.model.entity.Track;
import br.com.tassioauad.spotifystreamer.presenter.TrackPresenter;
import br.com.tassioauad.spotifystreamer.utils.dagger.TrackModule;
import br.com.tassioauad.spotifystreamer.view.TrackView;
import br.com.tassioauad.spotifystreamer.view.fragment.PlayerFragment;
import butterknife.Bind;
import butterknife.ButterKnife;

public class TrackActivity extends AppCompatActivity implements TrackView, PlayerFragment.PlayerListener{

    @Inject
    TrackPresenter trackPresenter;
    @Bind(R.id.linearlayout_notfound)
    LinearLayout linearLayoutNotFound;
    @Bind(R.id.fragment_player)
    FrameLayout frameLayoutPlayer;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    public static final String BUNDLE_KEY_TRACKLIST = "extra_tracks";
    public static final String BUNDLE_KEY_SELECTEDPOSITION = "actual_position";
    private ArrayList<Track> trackArrayList;
    private int actualPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);
        ((SpotifyStreamerApplication) getApplication()).getObjectGraph().plus(new TrackModule(this)).inject(this);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(savedInstanceState != null && savedInstanceState.getParcelableArrayList(BUNDLE_KEY_TRACKLIST) != null) {
            trackArrayList =  savedInstanceState.getParcelableArrayList(BUNDLE_KEY_TRACKLIST);
            actualPosition = savedInstanceState.getInt(BUNDLE_KEY_SELECTEDPOSITION, 0);
        } else {
            trackArrayList = getIntent().getExtras().getParcelableArrayList(BUNDLE_KEY_TRACKLIST);
            actualPosition = getIntent().getExtras().getInt(BUNDLE_KEY_SELECTEDPOSITION, -1);
            trackPresenter.init(trackArrayList, actualPosition);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if(trackArrayList != null) {
            outState.putParcelableArrayList(BUNDLE_KEY_TRACKLIST, new ArrayList<Track>(trackArrayList));
            outState.putInt(BUNDLE_KEY_SELECTEDPOSITION, actualPosition);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static Intent newInstance(Context con, int position, List<Track> trackList) {
        Intent intent = new Intent(con, TrackActivity.class);
        intent.putExtra(BUNDLE_KEY_SELECTEDPOSITION, position);
        intent.putExtra(BUNDLE_KEY_TRACKLIST, new ArrayList<Track>(trackList));

        return intent;
    }

    @Override
    public void warnNoTracks() {
        linearLayoutNotFound.setVisibility(View.VISIBLE);
        frameLayoutPlayer.setVisibility(View.GONE);
    }

    @Override
    public void showPlayer(List<Track> trackList, int actualPosition) {
        linearLayoutNotFound.setVisibility(View.GONE);
        frameLayoutPlayer.setVisibility(View.VISIBLE);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_player, PlayerFragment.newInstance(actualPosition, trackList))
                .commit();
    }

    @Override
    public void onPlay(Track track) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Artist artist : track.getArtistList()) {
            stringBuilder.append(artist.getName());
            stringBuilder.append(" - ");
        }
        getSupportActionBar().setTitle(track.getName());
        getSupportActionBar().setSubtitle(stringBuilder.toString().substring(0, stringBuilder.toString().length() - 3));
    }

}