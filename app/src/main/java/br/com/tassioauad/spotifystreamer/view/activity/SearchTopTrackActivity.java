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
import br.com.tassioauad.spotifystreamer.view.fragment.ListTopTrackFragment;

public class SearchTopTrackActivity extends AppCompatActivity {

    private static final String ARTIST_BUNDLE_KEY = "trackbundlekey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_searchtoptrack);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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
}
