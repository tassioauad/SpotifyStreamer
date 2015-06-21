package br.com.tassioauad.spotifystreamer.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.List;

import javax.inject.Inject;

import br.com.tassioauad.spotifystreamer.R;
import br.com.tassioauad.spotifystreamer.SpotifyStreamerApplication;
import br.com.tassioauad.spotifystreamer.model.entity.Track;
import br.com.tassioauad.spotifystreamer.presenter.SearchTopTrackPresenter;
import br.com.tassioauad.spotifystreamer.utils.dagger.SearchTopTrackModule;
import br.com.tassioauad.spotifystreamer.view.SearchTopTrackView;

public class SearchTopTrackActivity extends AppCompatActivity implements SearchTopTrackView {

    @Inject
    SearchTopTrackPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((SpotifyStreamerApplication) getApplication()).getObjectGraph().plus(new SearchTopTrackModule(this)).inject(this);
        setContentView(R.layout.activity_searchtoptrack);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public void showTracks(List<Track> tracks) {

    }

    @Override
    public void anyTrackFounded() {

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
