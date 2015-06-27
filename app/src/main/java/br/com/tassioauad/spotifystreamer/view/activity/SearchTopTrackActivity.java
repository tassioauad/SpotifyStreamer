package br.com.tassioauad.spotifystreamer.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import br.com.tassioauad.spotifystreamer.R;
import br.com.tassioauad.spotifystreamer.SpotifyStreamerApplication;
import br.com.tassioauad.spotifystreamer.model.entity.Artist;
import br.com.tassioauad.spotifystreamer.model.entity.Track;
import br.com.tassioauad.spotifystreamer.presenter.SearchTopTrackPresenter;
import br.com.tassioauad.spotifystreamer.utils.dagger.SearchTopTrackModule;
import br.com.tassioauad.spotifystreamer.view.SearchTopTrackView;
import br.com.tassioauad.spotifystreamer.view.listviewadapter.TrackListViewAdapter;

public class SearchTopTrackActivity extends AppCompatActivity implements SearchTopTrackView {

    private final String TRACK_LIST_BUNDLE_KEY = "tracklistbundlekey";
    private static final String ARTIST_BUNDLE_KEY = "trackbundlekey";


    @Inject
    SearchTopTrackPresenter presenter;
    private List<Track> trackList = new ArrayList<>();

    private LinearLayout linearLayoutLostConnection;
    private LinearLayout linearLayoutNotFound;
    private ListView listViewTrack;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((SpotifyStreamerApplication) getApplication()).getObjectGraph().plus(new SearchTopTrackModule(this)).inject(this);
        setContentView(R.layout.activity_searchtoptrack);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        linearLayoutLostConnection = (LinearLayout) findViewById(R.id.linearlayout_lostconnection);
        linearLayoutNotFound = (LinearLayout) findViewById(R.id.linearlayout_notfound);
        listViewTrack = (ListView) findViewById(R.id.listview_track);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        if (savedInstanceState != null) {
            Track[] trackArray = (Track[]) savedInstanceState.getParcelableArray(TRACK_LIST_BUNDLE_KEY);
            if(trackArray != null) {
                showTracks(Arrays.asList(trackArray));
                trackList = Arrays.asList(trackArray);
            }
        } else {
            Artist artist = getIntent().getParcelableExtra(ARTIST_BUNDLE_KEY);
            if(artist == null) {
                anyTrackFounded();
            } else {
                getSupportActionBar().setSubtitle(artist.getName());
                presenter.searchByArtist(artist);
            }
        }
    }

    public static Intent newIntent(Context context, Artist artist) {
        Intent intent = new Intent(context, SearchTopTrackActivity.class);
        intent.putExtra(ARTIST_BUNDLE_KEY, artist);

        return intent;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if(trackList != null && trackList.size() > 0) {
            outState.putParcelableArray(TRACK_LIST_BUNDLE_KEY,
                    trackList.toArray(new Track[trackList.size()]));
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void showTracks(List<Track> trackList) {
        this.trackList = trackList;
        linearLayoutNotFound.setVisibility(View.GONE);
        linearLayoutLostConnection.setVisibility(View.GONE);
        listViewTrack.setVisibility(View.VISIBLE);
        listViewTrack.setAdapter(new TrackListViewAdapter(this, trackList));
    }

    @Override
    public void anyTrackFounded() {
        linearLayoutNotFound.setVisibility(View.VISIBLE);
        linearLayoutLostConnection.setVisibility(View.GONE);
        listViewTrack.setVisibility(View.GONE);
        Toast toast = Toast.makeText(this, getString(R.string.searchtoptrack_toast_anytrackwasfound), Toast.LENGTH_SHORT);
        toast.getView().setBackgroundColor(getResources().getColor(R.color.green));
        toast.show();
    }

    @Override
    public void lostConnection() {
        linearLayoutNotFound.setVisibility(View.GONE);
        linearLayoutLostConnection.setVisibility(View.VISIBLE);
        listViewTrack.setVisibility(View.GONE);
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
