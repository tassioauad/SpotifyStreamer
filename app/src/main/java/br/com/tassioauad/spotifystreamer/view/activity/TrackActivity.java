package br.com.tassioauad.spotifystreamer.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Arrays;
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
    public static final String EXTRA_TRACKS = "extra_tracks";
    public static final String EXTRA_SELECTED_POSITION = "actual_position";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);
        ((SpotifyStreamerApplication) getApplication()).getObjectGraph().plus(new TrackModule(this)).inject(this);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayList<Track> trackArrayList = getIntent().getExtras().getParcelableArrayList(EXTRA_TRACKS);
        int actualPosition = getIntent().getExtras().getInt(EXTRA_SELECTED_POSITION, -1);
        trackPresenter.init(trackArrayList, actualPosition);

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
        intent.putExtra(EXTRA_SELECTED_POSITION, position);
        intent.putExtra(EXTRA_TRACKS, new ArrayList<Track>(trackList));

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