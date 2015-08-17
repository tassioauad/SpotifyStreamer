package br.com.tassioauad.spotifystreamer.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import br.com.tassioauad.spotifystreamer.R;
import br.com.tassioauad.spotifystreamer.model.entity.Artist;
import br.com.tassioauad.spotifystreamer.model.entity.Track;
import br.com.tassioauad.spotifystreamer.view.fragment.ListTopTrackFragment;
import butterknife.Bind;
import butterknife.ButterKnife;

public class SearchTopTrackActivity extends AppCompatActivity implements ListTopTrackFragment.ListTopTrackListener{

    private static final String ARTIST_BUNDLE_KEY = "trackbundlekey";

    @Bind(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchtoptrack);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Artist artist = getIntent().getParcelableExtra(ARTIST_BUNDLE_KEY);
        getSupportActionBar().setSubtitle(artist.getName());

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_searchtoptrack, ListTopTrackFragment.newInstance(artist))
                    .commit();
        }
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

    public static Intent newIntent(Context context, Artist artist) {
        Intent intent = new Intent(context, SearchTopTrackActivity.class);
        intent.putExtra(ARTIST_BUNDLE_KEY, artist);

        return intent;
    }

    @Override
    public void onTrackSelected(Track track) {

    }
}
