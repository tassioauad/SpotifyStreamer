package br.com.tassioauad.spotifystreamer.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import br.com.tassioauad.spotifystreamer.view.activity.SearchTopTrackActivity;
import br.com.tassioauad.spotifystreamer.view.listviewadapter.TrackListViewAdapter;

public class SearchTopTrackFragment extends Fragment implements SearchTopTrackView {

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((SpotifyStreamerApplication) getActivity().getApplication())
                .getObjectGraph().plus(new SearchTopTrackModule(this)).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_searchtoptrack, container, false);

        linearLayoutLostConnection = (LinearLayout) view.findViewById(R.id.linearlayout_lostconnection);
        linearLayoutNotFound = (LinearLayout) view.findViewById(R.id.linearlayout_notfound);
        listViewTrack = (ListView) view.findViewById(R.id.listview_track);
        progressBar = (ProgressBar) view.findViewById(R.id.progressbar);

        if (savedInstanceState != null) {
            Track[] trackArray = (Track[]) savedInstanceState.getParcelableArray(TRACK_LIST_BUNDLE_KEY);
            if (trackArray != null) {
                showTracks(Arrays.asList(trackArray));
                trackList = Arrays.asList(trackArray);
            }
        } else {
            Artist artist = getArguments().getParcelable(ARTIST_BUNDLE_KEY);
            if (artist == null) {
                anyTrackFounded();
            } else {
                presenter.searchByArtist(artist);
            }
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (trackList != null && trackList.size() > 0) {
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
        listViewTrack.setAdapter(new TrackListViewAdapter(getActivity(), trackList));
    }

    @Override
    public void anyTrackFounded() {
        linearLayoutNotFound.setVisibility(View.VISIBLE);
        linearLayoutLostConnection.setVisibility(View.GONE);
        listViewTrack.setVisibility(View.GONE);
        Toast toast = Toast.makeText(getActivity(), getString(R.string.searchtoptrack_toast_anytrackwasfound), Toast.LENGTH_SHORT);
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

    public static SearchTopTrackFragment newInstance(SearchTopTrackActivity searchTopTrackActivity, Artist artist) {
        SearchTopTrackFragment searchTopTrackFragment = new SearchTopTrackFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARTIST_BUNDLE_KEY, artist);
        searchTopTrackFragment.setArguments(bundle);

        return searchTopTrackFragment;
    }
}
